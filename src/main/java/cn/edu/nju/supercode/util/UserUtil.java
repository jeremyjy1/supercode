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
        }//从cookie当中取出token
        if (!tokenUtil.verifyToken(token))
            throw SuperCodeException.loginRequired();
        return tokenUtil.getUser(token);
    }

    public boolean isAdmin(User user){
        return Objects.equals(user.getRole(), "admin") || Objects.equals(user.getRole(), "root");
    }

    public boolean canOperate(User operator,User operatee){
        if(operator==null||operatee==null)
            return false;
        if(Objects.equals(operator.getRole(), "root") && !Objects.equals(operatee.getRole(), "root"))
            return true;
        return Objects.equals(operator.getRole(), "admin") && Objects.equals(operatee.getRole(), "user");
    }

    public boolean isSelf(User operator,User operatee){
        return Objects.equals(operator.getUuid(), operatee.getUuid());
    }
}