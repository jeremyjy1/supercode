package cn.edu.nju.supercode.vo;

import cn.edu.nju.supercode.po.Submission;
import cn.edu.nju.supercode.po.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PendingVO {
    public User user;
    private String submitId;
    private String problemId;
    private String lang;
    private String code;
    private LocalDateTime submissionTime;

    public SubmissionResultVO toSubmissionResultVO() {
        SubmissionResultVO submissionResultVO = new SubmissionResultVO();
        submissionResultVO.setSubmitId(submitId);
        submissionResultVO.setProblemId(problemId);
        submissionResultVO.setLang(lang);
        submissionResultVO.setCode(code);
        submissionResultVO.setResult("正在评测");
        submissionResultVO.setSubmissionTime(submissionTime);
        return submissionResultVO;
    }

    public SimpleSubmissionResultVO toSimpleSubmissionResultVO() {
        SimpleSubmissionResultVO simpleSubmissionResultVO = new SimpleSubmissionResultVO();
        simpleSubmissionResultVO.setSubmitId(submitId);
        simpleSubmissionResultVO.setProblemId(problemId);
        simpleSubmissionResultVO.setLang(lang);
        simpleSubmissionResultVO.setResult("正在评测");
        simpleSubmissionResultVO.setSubmissionTime(submissionTime);
        return simpleSubmissionResultVO;
    }

    public Submission toSubmission(Integer time, Integer memory, String result, Integer score, List<StdioVO> stdioVOList) {
        Submission submission = new Submission();
        submission.setSubmissionId(submitId);
        submission.setProblemId(problemId);
        submission.setUserId(user.getUuid());
        submission.setLang(lang);
        submission.setCode(code);
        submission.setTime(time);
        submission.setMemory(memory);
        submission.setResult(result);
        submission.setScore(score);
        submission.setStdio(stdioVOList);
        submission.setSubmissionTime(submissionTime);
        return submission;
    }
}
