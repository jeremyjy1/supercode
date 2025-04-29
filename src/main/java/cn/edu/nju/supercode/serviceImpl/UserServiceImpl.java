package cn.edu.nju.supercode.serviceImpl;

import cn.edu.nju.supercode.exception.SuperCodeException;
import cn.edu.nju.supercode.po.User;
import cn.edu.nju.supercode.repository.UserRepository;
import cn.edu.nju.supercode.service.UserService;
import cn.edu.nju.supercode.util.TokenUtil;
import cn.edu.nju.supercode.vo.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;
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

    @Override
    public void changeRole(ChangeRoleVO changeRoleVO) throws Exception {
        User user=userRepository.findByUsername(changeRoleVO.getUsername());
        if(user!=null&& !Objects.equals(user.getRole(), "root"))
        {
            if(Objects.equals(changeRoleVO.getRole(), "user") || Objects.equals(changeRoleVO.getRole(), "admin"))
            {
                user.setRole(changeRoleVO.getRole());
                userRepository.save(user);
                return;
            }
        }
        throw SuperCodeException.updateFailed();
    }

    @Override
    public void resetPassword(String username) throws Exception {
        User user=userRepository.findByUsername(username);
        if(user!=null)
        {
            user.setSalt(RandomStringUtils.randomAlphanumeric(128));
            user.setPassword(DigestUtils.sha512Hex(username+user.getSalt()));
            userRepository.save(user);
            return;
        }
        throw SuperCodeException.userNotExisted();
    }

    @Override
    public void changePassword(User user, PasswordVO passwordVO) throws Exception {
        if(Objects.equals(passwordVO.getOldPassword(), passwordVO.getNewPassword()))
            throw SuperCodeException.samePassword();
        if(!DigestUtils.sha512Hex(passwordVO.getOldPassword() + user.getSalt()).equals(user.getPassword()))
            throw SuperCodeException.wrongPassword();
        user.setSalt(RandomStringUtils.randomAlphanumeric(128));
        user.setPassword(DigestUtils.sha512Hex(passwordVO.getNewPassword()+user.getSalt()));
        userRepository.save(user);
    }

    @Override
    public List<RetUserVO> getUsers() {
        List<User>users=userRepository.findAll();
        List<RetUserVO>retUserVOList=new ArrayList<>();
        for(User i:users)
            retUserVOList.add(i.toRetVO());
        return retUserVOList;
    }

    @Override
    public void updateUser(UpdateUserVO updateUserVO) throws Exception {
        User user=userRepository.findByUsername(updateUserVO.getUsername());
        if(user==null)
            throw SuperCodeException.userNotExisted();
        user.setName(updateUserVO.getName());
        user.setEmail(updateUserVO.getEmail());
        userRepository.save(user);
    }

    @Override
    public RetUserVO getProfile(User user) {
        return user.toRetVO();
    }
}
