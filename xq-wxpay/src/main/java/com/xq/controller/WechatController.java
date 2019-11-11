package com.xq.controller;


import com.xq.config.WeChatConfig;
import com.xq.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/api/v1/wechat")
public class WechatController {
    @Autowired
    private WeChatConfig weChatConfig;

    /**
     * 微信扫一扫页面
     * @param accessPage
     * @return
     * @throws UnsupportedEncodingException
     */
    @GetMapping("login_url")
    public JsonData loginUrl(@RequestParam(value = "access_page", required = true) String accessPage) throws UnsupportedEncodingException {
        String redirectUrl = weChatConfig.getOpenRedirectUrl();
        String callbackUrl = URLEncoder.encode(redirectUrl, "GBK");
        String qrcodeUrl = String.format(WeChatConfig.getOpenQrcodeUrl(), weChatConfig.getAppId(), callbackUrl, accessPage);
        return JsonData.buildSuccess(qrcodeUrl);
    }
}
