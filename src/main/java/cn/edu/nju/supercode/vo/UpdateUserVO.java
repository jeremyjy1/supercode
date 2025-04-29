package cn.edu.nju.supercode.vo;

import cn.edu.nju.supercode.po.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

@Getter
@NoArgsConstructor
public class UpdateUserVO {
    private String username;
    private String name;
    private String email;
}
