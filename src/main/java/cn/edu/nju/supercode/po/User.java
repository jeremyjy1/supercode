package cn.edu.nju.supercode.po;

import cn.edu.nju.supercode.vo.RetUserVO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Id
    @Column(name = "uuid")
    private String uuid;

    @Basic
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Basic
    @Column(name = "password", nullable = false)
    private String password;

    @Basic
    @Column(name = "salt", nullable = false)
    private String salt;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    @Basic
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "role", nullable = false)
    private String role;

    public RetUserVO toRetVO(){
        RetUserVO retUserVO=new RetUserVO();
        retUserVO.setUsername(username);
        retUserVO.setName(name);
        retUserVO.setEmail(email);
        retUserVO.setRole(role);
        return retUserVO;
    }
}
