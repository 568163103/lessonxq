package com.beyeon.common.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class HttpsClientUtil {
	@Autowired
	private static  HttpClient httpClient =  new SSLClient();
	
	
	public static String doGet(String url){
		return doGet(url,"utf-8");
	}
    public static String doGet(String url,String charset){
        if(null == charset){
            charset = "utf-8";
        }

        HttpGet httpGet= null;
        String result = null;

        try {

            httpGet = new HttpGet(url);

            HttpResponse response = httpClient.execute(httpGet);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String doGetWithHead(String url,String charset){
        if(null == charset){
            charset = "utf-8";
        }

        HttpGet httpGet= null;
        String result = null;

        try {

            httpGet = new HttpGet(url);
            httpGet.setHeader("Authorization","DH-Cloud-Inner system:RmZ4MANZ792yavTiz/2rdZzhYi4=");
            httpGet.setHeader("Date","Mon, 25 Mar 2019 12:33:57 GMT");
            HttpResponse response = httpClient.execute(httpGet);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}