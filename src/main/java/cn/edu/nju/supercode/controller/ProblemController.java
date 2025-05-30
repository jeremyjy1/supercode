package cn.edu.nju.supercode.controller;

import cn.edu.nju.supercode.po.User;
import cn.edu.nju.supercode.service.ProblemService;
import cn.edu.nju.supercode.service.SubmissionService;
import cn.edu.nju.supercode.util.UserUtil;
import cn.edu.nju.supercode.vo.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/problem")
public class ProblemController {

    @Autowired
    UserUtil userUtil;

    @Autowired
    ProblemService problemService;

    @Autowired
    SubmissionService submissionService;

    @GetMapping("")
    public ResultVO<List<SimpleProblemVO>> getProblems() {
        return ResultVO.buildSuccess(problemService.getProblems());
    }

    @GetMapping("/{uuid}")
    public ResultVO<ProblemVO> getSingleProblem(@PathVariable String uuid) throws Exception {
        return ResultVO.buildSuccess(problemService.getProblemDetail(uuid));
    }

    @PostMapping("/{uuid}")
    public ResultVO<String> submit(@PathVariable String uuid, @RequestBody SubmissionVO submissionVO, HttpServletRequest request) {
        User user = userUtil.getUser(request);
        submissionService.submit(user, uuid, submissionVO);
        return ResultVO.buildSuccess("提交代码成功");
    }

    @GetMapping("/submit/{submitId}")
    public ResultVO<SubmissionResultVO> getSingleSubmission(@PathVariable String submitId, HttpServletRequest request) {
        User user = userUtil.getUser(request);
        return ResultVO.buildSuccess(submissionService.getSingleSubmission(user, submitId));
    }

    @GetMapping("/submit")
    public ResultVO<List<SimpleSubmissionResultVO>> getSimpleSubmissionsOfUser(HttpServletRequest request) {
        User user = userUtil.getUser(request);
        return ResultVO.buildSuccess(submissionService.getResultsOfUser(user));
    }


}
