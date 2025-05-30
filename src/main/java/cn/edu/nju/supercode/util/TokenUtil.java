package cn.edu.nju.supercode.util;

import cn.edu.nju.supercode.po.User;
import cn.edu.nju.supercode.repository.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtil {

    // 过期时间，单位：毫秒
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;

    @Autowired
    UserRepository userRepository;

    public String getToken(User user) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        return JWT.create()
                .withAudience(String.valueOf(user.getUuid()))
                .withExpiresAt(date)
                .sign(Algorithm.HMAC256(user.getPassword()));
    }

    public boolean verifyToken(String token) {
        try {
            String userUuid = JWT.decode(token).getAudience().getFirst();
            User user = userRepository.findById(userUuid).get();
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
            jwtVerifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public User getUser(String token) {
        String userUuid = JWT.decode(token).getAudience().getFirst();
        return userRepository.findById(userUuid).get();
    }
}