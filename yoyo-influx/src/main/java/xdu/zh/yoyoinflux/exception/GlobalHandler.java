package xdu.zh.yoyoinflux.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xdu.zh.yoyoinflux.pojo.Result;
import xdu.zh.yoyoinflux.type.Status;

/**
 * @Author jiangzhh
 * @Description:
 * @Date: Create in 23:54 2024/1/27
 */
@RestControllerAdvice
@Slf4j
public class GlobalHandler {
    @ExceptionHandler(RuntimeException.class)
    public Result handler(RuntimeException e) {
        log.error("运行时异常: {}", e.getMessage());
        return Result.result(Status.SERVER_ERROR, null, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e) {
        log.error("Assert异常: {}", e.getMessage());
        return Result.result(Status.FAIL, null, e.getMessage());
    }
}
