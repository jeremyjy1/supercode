package cn.edu.nju.supercode.exception;

public class SuperCodeException extends RuntimeException {
    public SuperCodeException(String message) {
        super(message);
    }

    public static SuperCodeException createFail() {
        throw new SuperCodeException("创建用户失败");
    }

    public static SuperCodeException userExisted() {
        throw new SuperCodeException("用户名已存在");
    }

    public static SuperCodeException loginFailure() {
        throw new SuperCodeException("用户不存在/用户密码错误");
    }

    public static SuperCodeException updateFailed() {
        throw new SuperCodeException("更新用户信息失败");
    }

    public static SuperCodeException userNotExisted() {
        throw new SuperCodeException("用户不存在");
    }

    public static SuperCodeException noSuchPrivilege() {
        throw new SuperCodeException("用户无权执行本操作");
    }

    public static SuperCodeException wrongPassword(){throw new SuperCodeException("旧密码不正确");}

    public static SuperCodeException samePassword(){throw new SuperCodeException("新旧密码不可以相同");}

    public static SuperCodeException problemNotFound(){throw new SuperCodeException("题目不存在");}

}