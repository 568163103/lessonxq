package com.mingsoft.nvssauthor.service.impl;

import com.alibaba.fastjson.JSON;
import com.mingsoft.nvssauthor.domain.TDict;
import com.mingsoft.nvssauthor.domain.User;
import com.mingsoft.nvssauthor.domain.UserInfo;
import com.mingsoft.nvssauthor.mapper.TDictMapper;
import com.mingsoft.nvssauthor.mapper.UserInfoMapper;
import com.mingsoft.nvssauthor.mapper.UserMapper;
import com.mingsoft.nvssauthor.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mac-xq
 * @ClassName
 * @Description
 * @Date 2020-03-21 23:00
 * @Version
 **/
@Service
public class SystemUserServiceImpl implements SystemUserService {
    @Autowired
    private UserInfoMapper userInfoMapper;


    public Map<String, Object> getSystemUserInfo() {
        int count = 0;
        int userOnlineCount = 0;
        int userOffOnlineCount = 0;
        int publicSecurityCount = 0;
        int dispatchCount = 0;
        int otherCount = 0;

        HashMap<String, Object> userMap = new HashMap<>();
        List<UserInfo> userInfos = userInfoMapper.userInfoAndUser();
        System.out.println(JSON.toJSONString(userInfos));
        if (userInfos != null) {
            for (UserInfo userInfo : userInfos) {
                if ("08".equals(userInfo.getTbDept())) {
                    //公安
                    publicSecurityCount++;
                } else if ("02".equals(userInfo.getTbDept())) {
                    //调度所
                    dispatchCount++;
                } else {
                    otherCount++;
                }

                if (userInfo.getStatus()) {
                    userOnlineCount++;
                } else {
                    userOffOnlineCount++;
                }
            }
            count = userInfos.size();
        }

        userMap.put("count", count);
        userMap.put("dispatchCount", dispatchCount);
        userMap.put("publicSecurityCount", publicSecurityCount);
        userMap.put("otherCount", otherCount);
        userMap.put("userOnlineCount", userOnlineCount);
        userMap.put("userOffOnlineCount", userOffOnlineCount);
        return userMap;
    }
}
