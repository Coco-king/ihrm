package top.codecrab.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

/**
 * EntityScan注解：配置jpa注解扫描
 *
 * @author codecrab
 * @since 2021年04月09日 9:56
 */
@SpringBootApplication(scanBasePackages = "top.codecrab")
@EntityScan("top.codecrab.common.entity.system")
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }

    /**
     * 用到了jpa，这里需要解决no session问题
     *
     * @return OpenEntityManagerInViewFilter实例
     */
    @Bean
    public OpenEntityManagerInViewFilter openEntityManagerInViewFilter() {
        return new OpenEntityManagerInViewFilter();
    }

}
