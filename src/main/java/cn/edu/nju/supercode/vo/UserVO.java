package cn.edu.nju.supercode.vo;

import cn.edu.nju.supercode.po.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

@Getter
@Setter
@NoArgsConstructor
public class UserVO {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String role;

    public User toPO() {
        User user = new User();
        user.setUsername(username);
        user.setSalt(RandomStringUtils.randomAlphanumeric(128));//生成盐
        user.setPassword(DigestUtils.sha512Hex(password + user.getSalt()));//生成处理后的密码
        user.setNickname(nickname);
        user.setEmail(email);
        user.setRole(role);
        return user;
    }
}
