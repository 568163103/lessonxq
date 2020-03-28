package com.mingsoft.nvssauthor.entiry;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mingsoft.nvssauthor.constant.Constant;

/**
 * @author mac-xq
 * @ClassName
 * @Description
 * @Date 2020-03-21 15:53
 * @Version
 **/
public class MicroServiceEntity {
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private String key;
    private JSONObject tempJsonObject = new JSONObject();

    private int running_list = 0;
    private int terminated_list = 0;
    private int waiting_list = 0;

    private int count = 0;

    public MicroServiceEntity() {
    }

    public MicroServiceEntity(JSONObject jsonObject, JSONArray jsonArray, String key) {
        this.jsonObject = jsonObject;
        this.jsonArray = jsonArray;
        this.key = key;
    }

    public MicroServiceEntity(JSONObject jsonObject, String key) {
        this.jsonObject = jsonObject;
        this.key = key;
    }

    public MicroServiceEntity(JSONArray jsonArray, String key) {
        this.jsonArray = jsonArray;
        this.key = key;
    }

    public MicroServiceEntity(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        getJsonArraySize();
    }


    public void getJsonArraySize() {
        this.running_list = jsonObject.getJSONArray(Constant.RUNNING_LIST).size();
        this.waiting_list = jsonObject.getJSONArray(Constant.WAITING_LIST).size();
        this.terminated_list = jsonObject.getJSONArray(Constant.TERMINATED_LIST).size();
        this.count = this.running_list + this.waiting_list + this.terminated_list;
        tempJsonObject.put("running_list",running_list);
        tempJsonObject.put("terminated_list",terminated_list);
        tempJsonObject.put("count",count);
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getRunning_list() {
        return running_list;
    }

    public void setRunning_list(int running_list) {
        this.running_list = running_list;
    }

    public int getTerminated_list() {
        return terminated_list;
    }

    public void setTerminated_list(int terminated_list) {
        this.terminated_list = terminated_list;
    }

    public int getWaiting_list() {
        return waiting_list;
    }

    public void setWaiting_list(int waiting_list) {
        this.waiting_list = waiting_list;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public JSONObject getTempJsonObject() {
        return tempJsonObject;
    }

    public void setTempJsonObject(JSONObject tempJsonObject) {
        this.tempJsonObject = tempJsonObject;
    }
}
