package cn.edu.nju.supercode.controller;

import cn.edu.nju.supercode.exception.SuperCodeException;
import cn.edu.nju.supercode.po.User;
import cn.edu.nju.supercode.service.UserService;
import cn.edu.nju.supercode.util.UserUtil;
import cn.edu.nju.supercode.vo.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/api/accounts")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserUtil userUtil;

    @PostMapping("")
    public ResultVO<String> createUser(@RequestBody UserVO userVO) throws Exception {
        userService.createUser(userVO);
        return ResultVO.buildSuccess("注册成功");
    }

    @PostMapping("/login")
    public ResultVO<String> login(HttpServletResponse request, @RequestBody LoginVO loginVO) {
        String result=userService.login(loginVO.getUsername(), loginVO.getPassword());
        Cookie cookie=new Cookie("token",result);
        cookie.setPath("/");
        cookie.setMaxAge(24*60*60);
        request.addCookie(cookie);
        request.addHeader("token",result);
        return ResultVO.buildSuccess(result);
    }

    @PostMapping("/changeRole")
    public ResultVO<String> login(@RequestBody ChangeRoleVO changeRoleVO, HttpServletRequest request) throws Exception {
        User user=userUtil.getUser(request);
        if(!Objects.equals(user.getRole(), "root"))
            throw SuperCodeException.noSuchPrivilege();
        userService.changeRole(changeRoleVO);
        return ResultVO.buildSuccess("更新权限成功");
    }

    @PostMapping("/resetPassword/{username}")
    public ResultVO<String> resetPassword(@PathVariable String username, HttpServletRequest request) throws Exception {
        User user=userUtil.getUser(request);
        if(!Objects.equals(user.getRole(), "root")&&!Objects.equals(user.getRole(), "admin"))
            throw SuperCodeException.noSuchPrivilege();
        userService.resetPassword(username);
        return ResultVO.buildSuccess("密码重置成功");
    }

    @PostMapping("/changePassword")
    public ResultVO<String> changePassword(@RequestBody PasswordVO passwordVO, HttpServletRequest request) throws Exception {
        User user=userUtil.getUser(request);
        userService.changePassword(user,passwordVO);
        return ResultVO.buildSuccess("密码修改成功");
    }

    @GetMapping("/getUsers")
    public ResultVO<List<RetUserVO>> getUsers(HttpServletRequest request) throws Exception {
        User user=userUtil.getUser(request);
        if(!Objects.equals(user.getRole(), "root")&&!Objects.equals(user.getRole(), "admin"))
            throw SuperCodeException.noSuchPrivilege();
        return ResultVO.buildSuccess(userService.getUsers());
    }

    @PostMapping("/updateUser")
    public ResultVO<String> updateUser(HttpServletRequest request, @RequestBody UpdateUserVO updateUserVO) throws Exception {
        User user=userUtil.getUser(request);
        if(!Objects.equals(user.getRole(), "root")&&!Objects.equals(user.getRole(), "admin")&& !Objects.equals(user.getUsername(), updateUserVO.getUsername()))
            throw SuperCodeException.noSuchPrivilege();
        userService.updateUser(updateUserVO);
        return ResultVO.buildSuccess("更新信息成功");
    }

    @GetMapping("/profile")
    public ResultVO<RetUserVO> getProfile(HttpServletRequest request){
        User user=userUtil.getUser(request);
        return ResultVO.buildSuccess(userService.getProfile(user));
    }
}
