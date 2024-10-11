package xdu.zh.yoyoinflux.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Author jiangzhh
 * @Description:
 * @Date: Create in 00:00 2024/1/28
 */
@Getter
@AllArgsConstructor
public enum Status {
    SUCCESS(200, "success"),
    FAIL(400, "fail"),
    NOT_FOUND(404, "not found"),
    SERVER_ERROR(500, "server error"),
    UNAUTHORIZED(401, "unauthorized");

    private final Integer code;
    private final String msg;

    public static Status parseFromCode(Integer code) {
        return Arrays.stream(Status.values())
                .filter(status -> code.equals(status.getCode()))
                .findFirst()
                .orElse(Status.SERVER_ERROR);
    }
}
