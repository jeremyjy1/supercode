package cn.edu.nju.supercode.serviceImpl;

import cn.edu.nju.supercode.exception.SuperCodeException;
import cn.edu.nju.supercode.po.User;
import cn.edu.nju.supercode.repository.UserRepository;
import cn.edu.nju.supercode.service.UserService;
import cn.edu.nju.supercode.util.TokenUtil;
import cn.edu.nju.supercode.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenUtil tokenUtil;

    @Override
    public void createUser(UserVO user) throws Exception{
        if(!Objects.equals(user.getRole(), "user"))
            throw SuperCodeException.createFail();
        if(userRepository.findByUsername(user.getUsername())!=null)
            throw SuperCodeException.userExisted();
        userRepository.save(user.toPO());
    }

    @Override
    public String login(String username,String password) {
            User user=userRepository.findByUsername(username);
            if(user!=null&& Objects.equals(user.getPassword(), DigestUtils.sha512Hex(password + user.getSalt())))
                return tokenUtil.getToken(user);
            throw SuperCodeException.loginFailure();
    }
}
