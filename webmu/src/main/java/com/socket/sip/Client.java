package com.socket.sip;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.socket.sip.bean.SIPRequestBean;
import com.socket.sip.bean.SIPRequestHead;

public class Client extends Thread{
	private Socket socket; 
	private BufferedReader  dis = null;
	private PrintWriter   dos = null;
    private String str;
    private static List<String> msg = new ArrayList<String>();
    
    public Client(){ 
    	try {
			socket = new Socket("127.0.0.1", 8888);
			dis = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			dos = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true); 
		} catch (UnknownHostException e) {
			System.out.println("--------连接不到服务器");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    } 

	public void send(String str) {
		dos.write(str);
		dos.flush();
	}
    public void run() {
       try{ 
    	   while (true){
        	   if(msg.size()>0){
        		   String msgtr = msg.get(0);
        		   this.send(msgtr);
        		   boolean f = true;
                   int len = 0;
                   String re = "";
                   char buf[] = new char[4096];    
                   while (f){
                	   len = dis.read(buf);
                	   if (len>0){
                		   re += new String(buf,0,len);
                		   msg.remove(0);
                		   f = false;
                		   System.out.println("---------received"+re);
                	   }
                   }
        	   }
           }
       } catch(IOException e){
    	   e.printStackTrace();
       }
    }
 
    public static void main(String[] args){ 
    	try {
			String textFromFile2 = FileUtils.readFileToString(new File("E:/workspace/keepAlive.xml"),"UTF-8");
			String textFromFile = FileUtils.readFileToString(new File("E:/workspace/login.xml"),"UTF-8");
			SIPRequestBean head = SIPSocketUtil.createSIPRequest("127.0.0.1", "127.0.0.1", new HashMap<String,Object>());
			
			 Client client = new Client();
			 client.msg.add(head.getHead()+textFromFile);
			 client.msg.add(head.getHead()+textFromFile2);
			 client.start();
			 
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
       
    } 
}