package com.mingsoft.nvssauthor.controller.login;

import com.mingsoft.nvssauthor.domain.User;
import com.mingsoft.nvssauthor.service.LoginService;
import com.mingsoft.nvssauthor.utils.MD5;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author mac-xq
 * @ClassName
 * @Description
 * @Date 2019-11-27 15:33
 * @Version
 **/

@Controller
@RequestMapping("login")
public class loginController {
    @Autowired
    private LoginService loginService;

    /**
     * 跳转到登录页面
     * @return
     */
    @RequestMapping(value = "to_login")
    public String toLogin() {
        return "login";
    }

    /**
     * 登录校验
     * @param username
     * @param password
     * @param modelMap
     * @return
     */
    @PostMapping(value = "goindex")
    public String login(@RequestParam(value = "username", required = false) String username,
                        @RequestParam(value = "password", required = false) String password, ModelMap modelMap) {

        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            Subject subject = SecurityUtils.getSubject();
            String pwd = MD5.md5(password);
            User user = loginService.getUserNameAndPWd(username);

            if (user != null) {
                UsernamePasswordToken token = new UsernamePasswordToken(username, pwd);
                try {
                    subject.login(token);
                    modelMap.put("username", username);
                    return "redirect:/index/to_index?username=" + username;
                } catch (UnknownAccountException e) {
                    modelMap.put("msg", "用户不存在");
                    return "login";
                } catch (IncorrectCredentialsException e) {
                    modelMap.put("msg", "用户名或密码错误");
                    return "login/login";
                }
            } else {

                modelMap.put("msg", "用户名或密码错误");
                return "login";
            }

        }
        return "login";
    }

}
