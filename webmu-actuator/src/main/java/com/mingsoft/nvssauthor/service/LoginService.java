package com.mingsoft.nvssauthor.service;

import com.mingsoft.nvssauthor.domain.User;

/**
 * @author mac-xq
 * @ClassName
 * @Description
 * @Date 2019-11-27 16:27
 * @Version
 **/
public interface LoginService {
    /**
     * 根据用户名查询 用户
     * @param userName 用户名
     * @return
     */
    User getUserNameAndPWd(String userName);
}
