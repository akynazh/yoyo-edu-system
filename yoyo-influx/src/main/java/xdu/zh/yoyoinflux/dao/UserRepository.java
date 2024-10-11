package xdu.zh.yoyoinflux.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import xdu.zh.yoyoinflux.pojo.User;

import java.util.Optional;

/**
 * @author jiangzhh
 * @date 2024/4/17
 */
@Component
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByCardId(String cardId);
}
