package top.codecrab.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import top.codecrab.common.entity.system.User;

/**
 * @author codecrab
 * @since 2021年04月13日 9:10
 */
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    /**
     * 根据手机号查询用户
     *
     * @param mobile 手机号
     * @return 用户
     */
    User findByMobile(String mobile);
}
