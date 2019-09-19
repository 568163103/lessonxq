package com.beyeon.nvss.dmu.control.action;

import com.alibaba.fastjson.JSONObject;
import com.beyeon.bean.xml.common.JsonData;
import com.beyeon.common.web.control.action.BaseAction;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: Charlie
 * @CreateDate 2019/4/24 10:53
 * @Version: 1.0
 **/
public class AlarmReportAction extends BaseAction {

    private JsonData jsonData;
    public String json(){
        JSONObject jsonRequest = JSONObject.parseObject(this.getStrResponse());
        String type = jsonRequest.getString("type");
        String start_time = jsonRequest.getString("start_time");
        String end_time = jsonRequest.getString("end_time");
        String ip = jsonRequest.getString("ip");
        String state = jsonRequest.getString("state");
        String description = jsonRequest.getString("description");
        jsonData.setCode(200);
        jsonData.setMsg("success");
        return "success";
    }

    private String getStrResponse(){
        ActionContext ctx = ActionContext.getContext();
        HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        InputStream inputStream;
        String strResponse = "";
        try {
            inputStream = request.getInputStream();
            String strMessage = "";
            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
            while ((strMessage = reader.readLine()) != null) {
                strResponse += strMessage;
            }
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strResponse;
    }

    public JsonData getJsonData() {
        return jsonData;
    }

    public void setJsonData(JsonData jsonData) {
        this.jsonData = jsonData;
    }
}
