package cn.edu.nju.supercode.util;

import cn.edu.nju.supercode.exception.SuperCodeException;
import cn.edu.nju.supercode.po.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserUtil {
    @Autowired
    TokenUtil tokenUtil;

    public User getUser(HttpServletRequest request) throws SuperCodeException {
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            throw SuperCodeException.loginRequired();
        String token = null;
        for (Cookie cookie : cookies) {
            if (Objects.equals(cookie.getName(), "token")) {
                token = cookie.getValue();
                break;
            }
        }
        if (!tokenUtil.verifyToken(token))
            throw SuperCodeException.loginRequired();
        return tokenUtil.getUser(token);
    }
}