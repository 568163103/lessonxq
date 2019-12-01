package com.mingsoft.nvssauthor.controller.server;

import com.mingsoft.nvssauthor.constant.Constant;
import com.mingsoft.nvssauthor.domain.Server;
import com.mingsoft.nvssauthor.service.ServerService;
import com.mingsoft.nvssauthor.tempentiry.ServerStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mac-xq
 * @ClassName
 * @Description
 * @Date 2019-11-29 16:48
 * @Version
 **/

@RestController
@RequestMapping(value = "api/v1/server")
public class ServerController {

    @Autowired
    private ServerService serverService;

    @PostMapping(value = "/findServerList")
    public Map<String, Object> findServerList() {
        HashMap<String, Object> result = new HashMap<>();
        List<Server> serverList = serverService.findServerList();
        result.put("data", serverList);
        result.put("code", 200);
        return result;
    }

    @PostMapping("/getServerType")
    public Map<String, Object> getServerType() {
        Map<String, Object> result = new HashMap<>();
        List<Server> serverList = serverService.findServerAndType();

        int mssCount = 0;
        int dmsCount = 0;
        int cmsCount = 0;
        int mssOnlineCount = 0;
        int dmsOnlineCount = 0;
        int cmsOnlineCount = 0;
        for (Server server : serverList) {
            if (Constant.SERVER_TYPE_MSS.equals(server.getServerTypeName())){
                mssCount++;
                if (server.getStatus()){
                    mssOnlineCount++;
                }
            }else if (Constant.SERVER_TYPE_DMS.equals(server.getServerTypeName())){
                dmsCount++;
                if (server.getStatus()){
                    dmsOnlineCount++;
                }
            }else if (Constant.SERVER_TYPE_CMS.equals(server.getServerTypeName())){
                cmsCount++;
                if (server.getStatus()){
                    cmsOnlineCount++;
                }
            }
        }
        ServerStatistics serverStatistics = new ServerStatistics(mssCount,dmsCount,cmsCount,mssOnlineCount,dmsOnlineCount,cmsOnlineCount);
        result.put("code", 200);
        result.put("serverStatistics",serverStatistics);
        return result;
    }




}
