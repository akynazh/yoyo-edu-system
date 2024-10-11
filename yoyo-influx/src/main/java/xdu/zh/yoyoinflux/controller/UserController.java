package xdu.zh.yoyoinflux.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xdu.zh.yoyoinflux.dao.UserRepository;
import xdu.zh.yoyoinflux.pojo.Result;
import xdu.zh.yoyoinflux.pojo.User;
import xdu.zh.yoyoinflux.type.Status;

import javax.annotation.Resource;

/**
 * @author jiangzhh
 * @date 2024/4/17
 */
@RestController
@RequestMapping("/u")
public class UserController {
    @Resource
    private UserRepository userRepository;

    @RequestMapping("/add")
    public Result add(@RequestBody User user) {
        userRepository.save(user);
        return Result.success(user);
    }

    @RequestMapping("/{cardId}")
    public Result get(@PathVariable String cardId) {
        return userRepository.findByCardId(cardId)
                .map(Result::success)
                .orElseGet(() -> Result.result(Status.NOT_FOUND));
    }
}
