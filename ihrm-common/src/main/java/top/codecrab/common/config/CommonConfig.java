package top.codecrab.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.codecrab.common.utils.IdWorker;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * @author codecrab
 * @since 2021年04月13日 8:51
 */
@Configuration
public class CommonConfig {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }

}
