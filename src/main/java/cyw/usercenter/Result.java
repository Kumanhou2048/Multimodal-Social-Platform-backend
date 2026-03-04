package cyw.usercenter;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    private Integer code;    // 状态码 (如 0 成功, 1 失败)
    private String message;  // 提示信息
    private T data;          // 返回的数据

    // 构造私有化，通过静态方法调用
    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(0, "ok", data);
    }

    /**
     * 失败返回
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(1, message, null);
    }

    /**
     * 更多自定义错误的重载
     */
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }
}
