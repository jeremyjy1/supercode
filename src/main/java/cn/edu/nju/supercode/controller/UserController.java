package cn.edu.nju.supercode.controller;

import cn.edu.nju.supercode.service.UserService;
import cn.edu.nju.supercode.vo.LoginVO;
import cn.edu.nju.supercode.vo.ResultVO;
import cn.edu.nju.supercode.vo.UserVO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/accounts")
public class UserController {
    @Autowired
    UserService userService;

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

}
