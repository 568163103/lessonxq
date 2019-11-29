package com.mingsoft.nvssauthor.shiro;

import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName ShiroConfig
 * @Description Shiro配置类
 * @Auror xq
 * @Date
 * @Version
 **/
@Configuration
public class ShiroConfig {


    //权限管理，配置主要是Realm的管理认证
    @Bean
    public DefaultSecurityManager getSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(getUserRealm());
        return securityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(getSecurityManager());
        Map<String,String> map = new LinkedHashMap<>();
        map.put("/plugins/**","anon");
        map.put("/static/**", "anon");
        map.put("/login/to_login", "anon");
        map.put("/login/goindex", "anon");
        //临时访问权限
        map.put("/api/v1/channel/**", "anon");
        map.put("/to_index", "anon");
        //上传视频需要对应的权限
        map.put("/upload","perms[user:upload]");
        //登出
        map.put("/logout","logout");
        //对所有用户认证
        map.put("/**","authc");
        //登录
        shiroFilterFactoryBean.setLoginUrl("/login/to_login");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("index/to_index");
        //设置没有授权跳转页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    //加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(getSecurityManager());
        return authorizationAttributeSourceAdvisor;
    }
    @Bean
    public UserRealm getUserRealm() {
        return new UserRealm();
    }

}
