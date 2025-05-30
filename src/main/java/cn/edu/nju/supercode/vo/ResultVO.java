package cn.edu.nju.supercode.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResultVO<T> implements Serializable {
    private Integer code;
    private T data;
    private String msg;

    public static <T> ResultVO<T> buildSuccess(T data) {
        return new ResultVO<T>(200, data, null);
    }

    public static <T> ResultVO<String> notLogin(String msg) {
        return new ResultVO<String>(401, null, msg);
    }

    public static <T> ResultVO<String> forbidden(String msg) {
        return new ResultVO<String>(403, null, msg);
    }

    public static <T> ResultVO<String> notFound(String msg) {
        return new ResultVO<String>(404, null, msg);
    }

    public static <T> ResultVO<String> conflict(String msg) {
        return new ResultVO<String>(409, null, msg);
    }
}