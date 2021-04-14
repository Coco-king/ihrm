package top.codecrab.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

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

}
