package top.codecrab.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import top.codecrab.common.utils.IdWorker;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * EntityScan注解：配置jpa注解扫描
 *
 * @author codecrab
 * @since 2021年04月09日 9:56
 */
@SpringBootApplication(scanBasePackages = "top.codecrab")
@EntityScan("top.codecrab.common.entity.company")
public class CompanyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompanyApplication.class, args);
    }

}
