package cn.edu.nju.supercode.serviceImpl;

import cn.edu.nju.supercode.component.LastSubmission;
import cn.edu.nju.supercode.component.PendingSubmissions;
import cn.edu.nju.supercode.exception.ForbiddenException;
import cn.edu.nju.supercode.exception.NotFoundException;
import cn.edu.nju.supercode.po.Problem;
import cn.edu.nju.supercode.po.Submission;
import cn.edu.nju.supercode.po.User;
import cn.edu.nju.supercode.repository.ProblemRepository;
import cn.edu.nju.supercode.repository.SubmissionRepository;
import cn.edu.nju.supercode.service.SubmissionService;
import cn.edu.nju.supercode.stream.CMD;
import cn.edu.nju.supercode.stream.Config;
import cn.edu.nju.supercode.stream.Received;
import cn.edu.nju.supercode.stream.Sent;
import cn.edu.nju.supercode.util.OutputUtil;
import cn.edu.nju.supercode.vo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.rabbitmq.stream.ByteCapacity;
import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.OffsetSpecification;
import com.rabbitmq.stream.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class SubmissionServiceImpl implements SubmissionService {
    ProblemRepository problemRepository;

    SubmissionRepository submissionRepository;

    @Autowired
    SubmissionServiceImpl(ProblemRepository problemRepository, SubmissionRepository submissionRepository) {
        this.problemRepository = problemRepository;
        this.submissionRepository = submissionRepository;
        Environment environment = Environment.builder().build();
        String stream = "Runner2Server";
        environment.streamCreator().stream(stream).maxLengthBytes(ByteCapacity.GB(1)).create();
        environment.consumerBuilder()
                .stream(stream)
                .offset(OffsetSpecification.next())
                .messageHandler((unused, message) -> {
                    ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                    try {
                        Received received = mapper.readValue(new String(message.getBodyAsBinary()), Received.class);
                        PendingVO pendingVO = PendingSubmissions.pending.get(received.getSubmit_id());
                        Problem problem = problemRepository.findById(pendingVO.getProblemId()).orElseThrow(NotFoundException::problemNotFound);
                        List<StdioVO> stdioVOList = problem.getStdio();
                        int passed = 0, sum = stdioVOList.size(), time = -1, memory = -1;
                        String state = "答案正确";
                        if (!Objects.equals(received.getSandbox_results().get(1).getState(), "Success")) {
                            state = "编译错误";
                            stdioVOList = new ArrayList<>();
                            time = 0;
                            memory = 0;
                        } else {
                            for (int i = 2; i <= sum + 1; i++) {
                                if (received.getSandbox_results().get(i).getTime() > time)
                                    time = received.getSandbox_results().get(i).getTime();
                                if (received.getSandbox_results().get(i).getMemory() > memory)
                                    memory = received.getSandbox_results().get(i).getMemory();
                                if (received.getSandbox_results().get(i).getTime() > problem.getTimeLimit()) {
                                    state = "时间超限";
                                    continue;
                                }
                                if (Objects.equals(received.getSandbox_results().get(i).getState(), "Success")) {
                                    if (OutputUtil.judge(received.getSandbox_results().get(i).getStdout(), stdioVOList.get(i - 2).getStdout()) && OutputUtil.judge(received.getSandbox_results().get(i).getStderr(), stdioVOList.get(i - 2).getStderr()))
                                        passed++;
                                    else
                                        state = "答案错误";
                                } else {
                                    state = switch (received.getSandbox_results().get(i).getState()) {
                                        case "RuntimeError" -> "运行错误";
                                        case "TimeLimitExceeded" -> "时间超限";
                                        case "MemoryLimitExceeded" -> "空间超限";
                                        case null, default -> "未知错误";
                                    };
                                }
                                stdioVOList.get(i - 2).setStdout(received.getSandbox_results().get(i).getStdout());
                                stdioVOList.get(i - 2).setStderr(received.getSandbox_results().get(i).getStderr());
                            }
                        }
                        int score = 100 * passed / sum;
                        Submission submission = pendingVO.toSubmission(time, memory, state, score, stdioVOList);
                        submissionRepository.save(submission);
                        PendingSubmissions.pending.remove(pendingVO.getSubmitId());
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }).build();
    }

    @Override
    public void submit(User user, String problemId, SubmissionVO submissionVO) {
        LocalDateTime submissionTime = LocalDateTime.now();
        LocalDateTime lastSubmissionTime = LastSubmission.lastSubmission.get(user.getUuid());
        if (lastSubmissionTime != null) {
            Duration duration = Duration.between(lastSubmissionTime, submissionTime);
            if (duration.toSeconds() < 10)
                throw ForbiddenException.tooOften();
        }
        LastSubmission.lastSubmission.put(user.getUuid(), submissionTime);
        if (!Objects.equals(submissionVO.getLang(), "C") && !Objects.equals(submissionVO.getLang(), "C++") && !Objects.equals(submissionVO.getLang(), "Java"))
            throw NotFoundException.languageNotFound();
        if (problemRepository.findById(problemId).isEmpty())
            throw NotFoundException.problemNotFound();
        PendingVO pendingVO = submissionVO.toPendingVO(problemId, user, submissionTime);
        PendingSubmissions.pending.put(pendingVO.getSubmitId(), pendingVO);
        Environment environment = Environment.builder().build();
        String stream = "Server2Runner";
        environment.streamCreator().stream(stream).maxLengthBytes(ByteCapacity.GB(1)).create();
        Producer producer = environment.producerBuilder().stream(stream).build();
        Sent sent = new Sent();
        sent.setSubmit_id(pendingVO.getSubmitId());
        if (Objects.equals(submissionVO.getLang(), "C")) {
            sent.setImage("gcc:14.2");
            List<CMD> commands = new ArrayList<>();
            CMD save = new CMD("bash", List.of("-c", "echo '".concat(pendingVO.getCode()).concat("' > main.c")), "", new Config());
            commands.add(save);
            CMD compile = new CMD("gcc", List.of("main.c", "-o", "main"), "", new Config());
            commands.add(compile);
            Problem problem = problemRepository.findById(problemId).get();
            Config config = problem.toConfig();
            List<StdioVO> stdioVOList = problem.getStdio();
            for (StdioVO i : stdioVOList) {
                CMD run = new CMD("./main", new ArrayList<>(), i.getStdin(), config);
                commands.add(run);
            }
            sent.setCommands(commands);
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            try {
                String message = mapper.writeValueAsString(sent);
                producer.send(producer.messageBuilder().addData(message.getBytes()).build(), null);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (Objects.equals(submissionVO.getLang(), "C++")) {
            sent.setImage("gcc:14.2");
            List<CMD> commands = new ArrayList<>();
            CMD save = new CMD("bash", List.of("-c", "echo '".concat(pendingVO.getCode()).concat("' > main.cpp")), "", new Config());
            commands.add(save);
            CMD compile = new CMD("g++", List.of("main.cpp", "-o", "main"), "", new Config());
            commands.add(compile);
            Problem problem = problemRepository.findById(problemId).get();
            Config config = problem.toConfig();
            List<StdioVO> stdioVOList = problem.getStdio();
            for (StdioVO i : stdioVOList) {
                CMD run = new CMD("./main", new ArrayList<>(), i.getStdin(), config);
                commands.add(run);
            }
            sent.setCommands(commands);
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            try {
                String message = mapper.writeValueAsString(sent);
                producer.send(producer.messageBuilder().addData(message.getBytes()).build(), null);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if (Objects.equals(submissionVO.getLang(), "Java")) {
            sent.setImage("openjdk:21");
            List<CMD> commands = new ArrayList<>();
            CMD save = new CMD("bash", List.of("-c", "echo '".concat(pendingVO.getCode()).concat("' > Main.java")), "", new Config());
            commands.add(save);
            CMD compile = new CMD("javac", List.of("Main.java"), "", new Config());
            commands.add(compile);
            Problem problem = problemRepository.findById(problemId).get();
            Config config = problem.toConfig();
            List<StdioVO> stdioVOList = problem.getStdio();
            for (StdioVO i : stdioVOList) {
                CMD run = new CMD("java", List.of("Main"), i.getStdin(), config);
                commands.add(run);
            }
            sent.setCommands(commands);
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            try {
                String message = mapper.writeValueAsString(sent);
                producer.send(producer.messageBuilder().addData(message.getBytes()).build(), null);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public SubmissionResultVO getSingleSubmission(User user, String submitId) {
        PendingVO pendingVO = PendingSubmissions.pending.get(submitId);
        if (pendingVO != null) {
            if (!Objects.equals(pendingVO.getUser().getUuid(), user.getUuid()))
                throw ForbiddenException.noSuchPrivilege();
            else
                return pendingVO.toSubmissionResultVO();
        }
        Optional<Submission> submission = submissionRepository.findById(submitId);
        if (submission.isPresent()) {
            if (!Objects.equals(submission.get().getUserId(), user.getUuid()))
                throw ForbiddenException.noSuchPrivilege();
            else
                return submission.get().toSubmissionResultVO();
        }
        throw NotFoundException.submissionNotFound();
    }

    @Override
    public List<SimpleSubmissionResultVO> getResultsOfUser(User user) {
        List<SimpleSubmissionResultVO> simpleSubmissionResultVOList = new ArrayList<>();
        for (Map.Entry<String, PendingVO> i : PendingSubmissions.pending.entrySet()) {
            if (Objects.equals(i.getValue().getUser().getUuid(), user.getUuid()))
                simpleSubmissionResultVOList.add(i.getValue().toSimpleSubmissionResultVO());
        }
        List<Submission> submissions = submissionRepository.findByUserId(user.getUuid());
        for (Submission i : submissions) {
            simpleSubmissionResultVOList.add(i.toSimpleSubmissionResultVO());
        }
        simpleSubmissionResultVOList.sort((a,b)->{return b.getSubmissionTime().compareTo(a.getSubmissionTime());});
        return simpleSubmissionResultVOList;
    }
}
