package com.xq.config;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author hc
 * 微信配置类
 */
@Configuration
@PropertySource(value = "classpath:bootstrap.yml")
public class WeChatConfig {
    @Value("${wxpay.appid}")
    private String appId;
    @Value("${wxpay.appsecret}")
    private String appSecret;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
