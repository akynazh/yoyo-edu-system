package xdu.zh.yoyoinflux.pojo;

import lombok.Data;
import lombok.ToString;
import xdu.zh.yoyoinflux.type.Status;

import java.io.Serializable;

/**
 * @Author jiangzhh
 * @Description: rest result
 * @Date: Create in 21:27 2024/1/24
 */
@Data
@ToString
public class Result implements Serializable {
    private Integer code;
    private String msg;
    private Object obj;

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, Object obj) {
        this.code = code;
        this.msg = msg;
        this.obj = obj;
    }

    public static Result result(Status status) {
        return new Result(status.getCode(), status.getMsg());
    }

    public static Result result(Status status, Object obj) {
        return new Result(status.getCode(), status.getMsg(), obj);
    }

    public static Result result(Status status, Object obj, String msg) {
        return new Result(status.getCode(), msg, obj);
    }

    public static Result success() {
        return Result.result(Status.SUCCESS);
    }

    public static Result success(Object obj) {
        return Result.result(Status.SUCCESS, obj);
    }
}