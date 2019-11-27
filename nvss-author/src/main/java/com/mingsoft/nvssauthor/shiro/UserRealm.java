package com.mingsoft.nvssauthor.shiro;

import com.mingsoft.nvssauthor.domain.User;
import com.mingsoft.nvssauthor.service.LoginService;
import com.mingsoft.nvssauthor.utils.MD5;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName
 * @Description
 * @Auror
 * @Date
 * @Version
 **/
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private LoginService loginService;
    /**
     * 执行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        Map<String,Object> map = new HashMap<>();
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        Subject subject = SecurityUtils.getSubject();
//        SysUser sysUser =(SysUser)subject.getPrincipal();
//        map.put("userid",sysUser.getUser_id());
//        SysUser roleUser =restTemplate.getForObject("localhost:9000/live/findById",SysUser.class,map);
//        info.addStringPermission(roleUser.getRole_id());
//        return info;
         return null;
    }

    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //数据库的用户名和密码
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = loginService.getUserNameAndPWd(token.getUsername());
        return new SimpleAuthenticationInfo(user, MD5.md5(user.getPassword()),"");

    }
}
