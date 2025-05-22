package cn.edu.nju.supercode.serviceImpl;

import cn.edu.nju.supercode.exception.*;
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
            throw UnauthorizedException.noSuchPrivilege();//只能允许创建类型为user的用户，后期再进行授权
        if(userRepository.findByUsername(user.getUsername())!=null)
            throw ConflictException.userExisted();//用户不可以和已有用户重名
        userRepository.save(user.toPO());
    }

    @Override
    public String login(String username,String password) {
            User user=userRepository.findByUsername(username);
            if(user!=null&& Objects.equals(user.getPassword(), DigestUtils.sha512Hex(password + user.getSalt())))//验证密码
                return tokenUtil.getToken(user);
            throw LoginException.wrongUsernameOrPassword();
    }

    @Override
    public void changeRole(ChangeRoleVO changeRoleVO) throws Exception {
        User user=userRepository.findByUsername(changeRoleVO.getUsername());
        if(user!=null&& !Objects.equals(user.getRole(), "root"))//不可以修改root用户的用户类型
        {
            if(Objects.equals(changeRoleVO.getRole(), "user") || Objects.equals(changeRoleVO.getRole(), "admin"))//不可以把用户修改为root
            {
                user.setRole(changeRoleVO.getRole());
                userRepository.save(user);
                return;
            }
        }
        throw UnauthorizedException.noSuchPrivilege();
    }

    @Override
    public void resetPassword(String username) throws Exception {
        User user=userRepository.findByUsername(username);
        if(user!=null)
        {
            user.setSalt(RandomStringUtils.randomAlphanumeric(128));//创建新盐来确保安全性
            user.setPassword(DigestUtils.sha512Hex(username+user.getSalt()));
            userRepository.save(user);
            return;
        }
        throw NotFoundException.userNotExisted();
    }

    @Override
    public void changePassword(User user, PasswordVO passwordVO) throws Exception {
        if(!DigestUtils.sha512Hex(passwordVO.getOldPassword() + user.getSalt()).equals(user.getPassword()))
            throw LoginException.wrongPassword();//密码验证通过才可以修改
        user.setSalt(RandomStringUtils.randomAlphanumeric(128));//创建新盐来确保安全性
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
            throw NotFoundException.userNotExisted();
        user.setNickname(updateUserVO.getName());
        user.setEmail(updateUserVO.getEmail());//部分修改字段
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String username) throws Exception {
        User user=userRepository.findByUsername(username);
        if(user==null)
            throw NotFoundException.userNotExisted();
        userRepository.delete(user);
    }


    @Override
    public RetUserVO getProfile(User user) {
        return user.toRetVO();
    }
}
