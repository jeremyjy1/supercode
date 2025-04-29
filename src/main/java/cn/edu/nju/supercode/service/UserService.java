package cn.edu.nju.supercode.service;

import cn.edu.nju.supercode.po.User;
import cn.edu.nju.supercode.vo.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    void createUser(UserVO user) throws Exception;

    String login(String username,String password);

    void changeRole(ChangeRoleVO changeRoleVO) throws Exception;

    void resetPassword(String username) throws Exception;

    void changePassword(User user, PasswordVO passwordVO) throws Exception;

    List<RetUserVO> getUsers();

    void updateUser(UpdateUserVO updateUserVO) throws Exception;

    RetUserVO getProfile(User user);
}
