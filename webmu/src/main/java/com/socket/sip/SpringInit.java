package com.socket.sip;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class SpringInit implements  ApplicationContextAware{  
    private static ApplicationContext applicationContext;  
  
    @Override  
    public void setApplicationContext(ApplicationContext applicationContext){  
    	SpringInit.applicationContext = applicationContext;  
    }  
  
    public static ApplicationContext getApplicationContext(){  
        return applicationContext;  
    }  
  
    public static Object getBean(String name) throws BeansException{  
        return applicationContext.getBean(name);  
    }  

    
}
