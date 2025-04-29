package cn.edu.nju.supercode.service;

import cn.edu.nju.supercode.vo.UserVO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void createUser(UserVO user) throws Exception;

    String login(String username,String password);
}
