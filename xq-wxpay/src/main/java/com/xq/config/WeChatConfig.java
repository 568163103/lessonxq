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
    /**
     * 公众号 appid
     */
    @Value("${wxpay.appid}")
    private String appId;
    /**
     * 公众号 appsecret
     */
    @Value("${wxpay.appsecret}")
    private String appSecret;
    /**
     * 开放平台 appid
     */
    @Value("${wxopen.appid}")
    private String openAppId;

    /**
     * 开放平台 appsecret
     */
    @Value("${wxopen.appsecret}")
    private String openAppsecret;



    /**
     * 开放平台 回调url
     */
    @Value("${wxopen.redirect_url}")
    private String openRedirectUrl;

    private final static String OPEN_QRCODE_URL= "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_login&state=%s#wechat_redirect";

    public static String getOpenQrcodeUrl() {
        return OPEN_QRCODE_URL;
    }

    public String getOpenAppId() {
        return openAppId;
    }

    public void setOpenAppId(String openAppId) {
        this.openAppId = openAppId;
    }

    public String getOpenAppsecret() {
        return openAppsecret;
    }

    public void setOpenAppsecret(String openAppsecret) {
        this.openAppsecret = openAppsecret;
    }

    public String getOpenRedirectUrl() {
        return openRedirectUrl;
    }

    public void setOpenRedirectUrl(String openRedirectUrl) {
        this.openRedirectUrl = openRedirectUrl;
    }

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
