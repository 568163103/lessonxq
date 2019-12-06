package com.mingsoft.nvssauthor.service.impl;

import com.mingsoft.nvssauthor.domain.User;
import com.mingsoft.nvssauthor.mapper.UserMapper;
import com.mingsoft.nvssauthor.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mac-xq
 * @ClassName
 * @Description
 * @Date 2019-11-27 16:28
 * @Version
 **/
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserNameAndPWd(String userName) {

        return userMapper.findByUserName(userName);
    }


}
