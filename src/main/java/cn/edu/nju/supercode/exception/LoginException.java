package cn.edu.nju.supercode.exception;

public class LoginException extends RuntimeException {
    public LoginException(String message) {
        super(message);
    }

    public static LoginException loginRequired() {
        throw new LoginException("请登录");
    }

    public static LoginException wrongUsernameOrPassword() {
        throw new LoginException("用户名或密码错误");
    }

    public static LoginException wrongPassword() {
        throw new LoginException("旧密码不正确");
    }

}