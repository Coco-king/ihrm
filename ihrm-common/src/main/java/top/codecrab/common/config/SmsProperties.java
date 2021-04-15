package top.codecrab.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author codecrab
 * @since 2021年04月15日 17:20
 */
@Data
@ConfigurationProperties("ihrm.sms")
public class SmsProperties {

    private String accessKeyId;

    private String accessKeySecret;

    private String signName;

    private String verifyCodeTemplate;
}
