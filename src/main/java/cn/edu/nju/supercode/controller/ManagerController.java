package cn.edu.nju.supercode.controller;

import cn.edu.nju.supercode.exception.ForbiddenException;
import cn.edu.nju.supercode.po.User;
import cn.edu.nju.supercode.repository.UserRepository;
import cn.edu.nju.supercode.service.ProblemService;
import cn.edu.nju.supercode.service.UserService;
import cn.edu.nju.supercode.util.UserUtil;
import cn.edu.nju.supercode.vo.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/mgr")
public class ManagerController {
    @Autowired
    UserService userService;

    @Autowired
    ProblemService problemService;

    @Autowired
    UserUtil userUtil;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/user/create")
    public ResultVO<String> createUser(HttpServletRequest request,@RequestBody UserVO userVO) throws Exception {
        User operator=userUtil.getUser(request),operatee=userVO.toPO();
        if(!userUtil.canOperate(operator,operatee))
            throw ForbiddenException.noSuchPrivilege();
        userService.createUser(userVO);
        return ResultVO.buildSuccess("创建用户成功");
    }

    @PostMapping("/user/changeRole")
    public ResultVO<String> login(@RequestBody ChangeRoleVO changeRoleVO, HttpServletRequest request) throws Exception {
        User operator=userUtil.getUser(request),operatee=userRepository.findByUsername(changeRoleVO.getUsername());
        if(!userUtil.canOperate(operator,operatee))
            throw ForbiddenException.noSuchPrivilege();//只有root和admin有权改变用户的权限
        userService.changeRole(changeRoleVO);
        return ResultVO.buildSuccess("更新权限成功");
    }

    @PostMapping("/user/resetPassword/{username}")
    public ResultVO<String> resetPassword(@PathVariable String username, HttpServletRequest request) throws Exception {
        User operator=userUtil.getUser(request),operatee=userRepository.findByUsername(username);
        if(!userUtil.canOperate(operator,operatee))
            throw ForbiddenException.noSuchPrivilege();//只有root和admin有权重置密码
        userService.resetPassword(username);
        return ResultVO.buildSuccess("密码重置成功");
    }

    @PostMapping("/user/password")
    public ResultVO<String> changePassword(@RequestBody PasswordVO passwordVO, HttpServletRequest request) throws Exception {
        User user=userUtil.getUser(request);
        userService.changePassword(user,passwordVO);
        return ResultVO.buildSuccess("密码修改成功");
    }

    @GetMapping("/user/getUsers")
    public ResultVO<List<RetUserVO>> getUsers(HttpServletRequest request) throws Exception {
        User user=userUtil.getUser(request);
        if(!userUtil.isAdmin(user))
            throw ForbiddenException.noSuchPrivilege();//只有root和admin有权获取全部用户
        return ResultVO.buildSuccess(userService.getUsers());
    }

    @DeleteMapping("/user/{username}")
    public ResultVO<String> deleteUser(@PathVariable String username, HttpServletRequest request) throws Exception {
        User operator=userUtil.getUser(request),operatee=userRepository.findByUsername(username);
         if(!userUtil.canOperate(operator,operatee))
            throw ForbiddenException.noSuchPrivilege();//只有root和admin有权删除用户
        userService.deleteUser(username);
        return ResultVO.buildSuccess("删除用户成功");
    }

    @PutMapping("/user/updateUser")
    public ResultVO<String> updateUser(HttpServletRequest request, @RequestBody UpdateUserVO updateUserVO) throws Exception {
        User operator=userUtil.getUser(request),operatee=userRepository.findByUsername(updateUserVO.getUsername());
        if(!userUtil.canOperate(operator,operatee)&&!userUtil.isSelf(operator,operatee))
            throw ForbiddenException.noSuchPrivilege();//要不是更新自己的信息，要不是管理员在更新他人的信息
        userService.updateUser(updateUserVO);
        return ResultVO.buildSuccess("更新信息成功");
    }

    @PostMapping("/problem/create")
    public ResultVO<String> createProblem(HttpServletRequest request,@RequestBody FullProblemVO fullProblemVO) throws Exception{
        User operator=userUtil.getUser(request);
        if(!userUtil.isAdmin(operator))
            throw ForbiddenException.noSuchPrivilege();//只有root和admin有权创建题目
        problemService.createProblem(fullProblemVO);
        return ResultVO.buildSuccess("题目创建成功");
    }

    @DeleteMapping("/problem/{uuid}")
    public ResultVO<String>deleteProblem(@PathVariable String uuid,HttpServletRequest request) throws Exception{
        User operator=userUtil.getUser(request);
        if(!userUtil.isAdmin(operator))
            throw ForbiddenException.noSuchPrivilege();//只有root和admin有权删除题目
        problemService.deleteProblem(uuid);
        return ResultVO.buildSuccess("题目删除成功");
    }


    @PutMapping("/problem")
    public ResultVO<String>updateProblem(HttpServletRequest request,@RequestBody FullProblemVO fullProblemVO) throws Exception{
        User operator=userUtil.getUser(request);
        if(!userUtil.isAdmin(operator))
            throw ForbiddenException.noSuchPrivilege();//只有root和admin有权更新题目
        problemService.updateProblem(fullProblemVO);
        return ResultVO.buildSuccess("题目更新成功");
    }

    @GetMapping("/problem/{uuid}")
    public ResultVO<FullProblemVO>getSingleProblem(@PathVariable String uuid) throws Exception{
        return ResultVO.buildSuccess(problemService.getFullProblemDetail(uuid));
    }

}
