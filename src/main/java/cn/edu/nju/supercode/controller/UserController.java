package cn.edu.nju.supercode.controller;

import cn.edu.nju.supercode.exception.ForbiddenException;
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

    @PostMapping("/login")
    public ResultVO<String> login(HttpServletResponse request, @RequestBody LoginVO loginVO) {
        String result = userService.login(loginVO.getUsername(), loginVO.getPassword());
        Cookie cookie = new Cookie("token", result);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);
        request.addCookie(cookie);
        request.addHeader("token", result);//设定cookie
        return ResultVO.buildSuccess(result);
    }

    @PostMapping("/password")
    public ResultVO<String> changePassword(@RequestBody PasswordVO passwordVO, HttpServletRequest request) throws Exception {
        User user = userUtil.getUser(request);
        userService.changePassword(user, passwordVO);
        return ResultVO.buildSuccess("密码修改成功");
    }

    @PutMapping("/updateUser")
    public ResultVO<String> updateUser(HttpServletRequest request, @RequestBody UpdateUserVO updateUserVO) throws Exception {
        User operator = userUtil.getUser(request), operatee = userRepository.findByUsername(updateUserVO.getUsername());
        if (!userUtil.canOperate(operator, operatee) && !userUtil.isSelf(operator, operatee))
            throw ForbiddenException.noSuchPrivilege();//要不是更新自己的信息，要不是管理员在更新他人的信息
        userService.updateUser(updateUserVO);
        return ResultVO.buildSuccess("更新信息成功");
    }

    @GetMapping("")
    public ResultVO<RetUserVO> getProfile(HttpServletRequest request) {//获取个人信息
        User user = userUtil.getUser(request);
        return ResultVO.buildSuccess(userService.getProfile(user));
    }
}
