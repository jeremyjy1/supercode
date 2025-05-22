package cn.edu.nju.supercode.controller;

import cn.edu.nju.supercode.exception.UnauthorizedException;
import cn.edu.nju.supercode.po.User;
import cn.edu.nju.supercode.repository.UserRepository;
import cn.edu.nju.supercode.service.UserService;
import cn.edu.nju.supercode.util.UserUtil;
import cn.edu.nju.supercode.vo.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserUtil userUtil;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/create")
    public ResultVO<String> createUser(HttpServletRequest request,@RequestBody UserVO userVO) throws Exception {
        User user=userUtil.getUser(request);
        if(!userUtil.isAdmin(user))
            throw UnauthorizedException.noSuchPrivilege();
        userService.createUser(userVO);
        return ResultVO.buildSuccess("创建用户成功");
    }

    @PostMapping("/login")
    public ResultVO<String> login(HttpServletResponse request, @RequestBody LoginVO loginVO) {
        String result=userService.login(loginVO.getUsername(), loginVO.getPassword());
        Cookie cookie=new Cookie("token",result);
        cookie.setPath("/");
        cookie.setMaxAge(24*60*60);
        request.addCookie(cookie);
        request.addHeader("token",result);//设定cookie
        return ResultVO.buildSuccess("登录成功");
    }

    @PostMapping("/changeRole")
    public ResultVO<String> login(@RequestBody ChangeRoleVO changeRoleVO, HttpServletRequest request) throws Exception {
        User operator=userUtil.getUser(request),operatee=userRepository.findByUsername(changeRoleVO.getUsername());
        if(!userUtil.canOperate(operator,operatee))
            throw UnauthorizedException.noSuchPrivilege();//只有root和admin有权改变用户的权限
        userService.changeRole(changeRoleVO);
        return ResultVO.buildSuccess("更新权限成功");
    }

    @PostMapping("/resetPassword/{username}")
    public ResultVO<String> resetPassword(@PathVariable String username, HttpServletRequest request) throws Exception {
        User operator=userUtil.getUser(request),operatee=userRepository.findByUsername(username);
        if(!userUtil.canOperate(operator,operatee))
            throw UnauthorizedException.noSuchPrivilege();//只有root和admin有权重置密码
        userService.resetPassword(username);
        return ResultVO.buildSuccess("密码重置成功");
    }

    @PostMapping("/password")
    public ResultVO<String> changePassword(@RequestBody PasswordVO passwordVO, HttpServletRequest request) throws Exception {
        User user=userUtil.getUser(request);
        userService.changePassword(user,passwordVO);
        return ResultVO.buildSuccess("密码修改成功");
    }

    @GetMapping("/getUsers")
    public ResultVO<List<RetUserVO>> getUsers(HttpServletRequest request) throws Exception {
        User user=userUtil.getUser(request);
        if(!userUtil.isAdmin(user))
            throw UnauthorizedException.noSuchPrivilege();//只有root和admin有权获取全部用户
        return ResultVO.buildSuccess(userService.getUsers());
    }

    @DeleteMapping("/{username}")
    public ResultVO<String> deleteUser(@PathVariable String username, HttpServletRequest request) throws Exception {
        User operator=userUtil.getUser(request),operatee=userRepository.findByUsername(username);
         if(!userUtil.canOperate(operator,operatee))
            throw UnauthorizedException.noSuchPrivilege();//只有root和admin有权删除用户
        userService.deleteUser(username);
        return ResultVO.buildSuccess("删除用户成功");
    }

    @PutMapping("/updateUser")
    public ResultVO<String> updateUser(HttpServletRequest request, @RequestBody UpdateUserVO updateUserVO) throws Exception {
        User operator=userUtil.getUser(request),operatee=userRepository.findByUsername(updateUserVO.getUsername());
        if(!userUtil.canOperate(operator,operatee)&&!userUtil.isSelf(operator,operatee))
            throw UnauthorizedException.noSuchPrivilege();//要不是更新自己的信息，要不是管理员在更新他人的信息
        userService.updateUser(updateUserVO);
        return ResultVO.buildSuccess("更新信息成功");
    }

    @GetMapping("")
    public ResultVO<RetUserVO> getProfile(HttpServletRequest request){//获取个人信息
        User user=userUtil.getUser(request);
        return ResultVO.buildSuccess(userService.getProfile(user));
    }
}
