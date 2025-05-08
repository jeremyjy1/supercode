package cn.edu.nju.supercode.controller;

import cn.edu.nju.supercode.exception.SuperCodeException;
import cn.edu.nju.supercode.po.User;
import cn.edu.nju.supercode.service.ProblemService;
import cn.edu.nju.supercode.util.UserUtil;
import cn.edu.nju.supercode.vo.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/problem")
public class ProblemController {

    @Autowired
    UserUtil userUtil;

    @Autowired
    ProblemService problemService;

    @PostMapping("")
    public ResultVO<String> createProblem(HttpServletRequest request,@RequestBody ProblemVO problemVO) throws Exception{
        User operator=userUtil.getUser(request);
        if(!userUtil.isAdmin(operator))
            throw SuperCodeException.noSuchPrivilege();//只有root和admin有权创建题目
        problemService.createProblem(problemVO);
        return ResultVO.buildSuccess("题目创建成功");
    }

    @GetMapping("")
    public ResultVO<List<SimpleProblemVO>>getProblems(){
        return ResultVO.buildSuccess(problemService.getProblems());
    }

    @GetMapping("/{uuid}")
    public ResultVO<ProblemVO>getSingleProblem(@PathVariable String uuid) throws Exception{
        return ResultVO.buildSuccess(problemService.getProblemDetail(uuid));
    }

    @DeleteMapping("/{uuid}")
    public ResultVO<String>deleteProblem(@PathVariable String uuid,HttpServletRequest request) throws Exception{
        User operator=userUtil.getUser(request);
        if(!userUtil.isAdmin(operator))
            throw SuperCodeException.noSuchPrivilege();//只有root和admin有权删除题目
        problemService.deleteProblem(uuid);
        return ResultVO.buildSuccess("题目删除成功");
    }

    @PutMapping("")
    public ResultVO<String>updateProblem(HttpServletRequest request,@RequestBody ProblemVO problemVO) throws Exception{
        User operator=userUtil.getUser(request);
        if(!userUtil.isAdmin(operator))
            throw SuperCodeException.noSuchPrivilege();//只有root和admin有权更新题目
        problemService.updateProblem(problemVO);
        return ResultVO.buildSuccess("题目更新成功");
    }
}
