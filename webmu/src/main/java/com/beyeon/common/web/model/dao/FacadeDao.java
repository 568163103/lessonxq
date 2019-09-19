/*
 * 文件名： GenericDAOImpl.java 创建日期： 2009-2-23 Copyright(C) 2009, by conntsing. 原始作者: <a
 * href="mailto:sun128837@126.com">conntsing</a>
 */
package com.beyeon.common.web.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * DAO超类，主要提供hibernate操作方法
 * 
 * @version $Revision: 1.3 $ *
 * @since 2009-2-23
 */
public class FacadeDao implements InitializingBean {

    private Map<String, List<HibernateBaseDao>> readDaos  = new HashMap<String, List<HibernateBaseDao>>();
    private Map<String, HibernateBaseDao>       writeDaos = new HashMap<String, HibernateBaseDao>();
    private Map<String, SessionFactory>         sessionFactorys;
    private Random                              random    = new Random();

    public void setSessionFactorys(Map<String, SessionFactory> sessionFactorys) {
        this.sessionFactorys = sessionFactorys;
    }

    private HibernateBaseDao getRandomHibernateBaseDAO(String dataBaseName) {
        List<HibernateBaseDao> list = readDaos.get(dataBaseName);
        int rand = random.nextInt(list.size());
        return list.get(rand);
    }

    public void afterPropertiesSet() throws Exception {
        for (Iterator<Entry<String, SessionFactory>> iterator = sessionFactorys.entrySet().iterator(); iterator.hasNext();) {
            Entry<String, SessionFactory> entry = iterator.next();
            String key = entry.getKey();
            if (key.contains("_r")) {
                String databaseName = key.substring(0, key.indexOf("_r"));
                HibernateBaseDao HibernateBaseDao = new HibernateBaseDao(entry.getValue());
                List<HibernateBaseDao> list = readDaos.get(databaseName);
                if (null == list) {
                    list = new ArrayList<HibernateBaseDao>();
                    readDaos.put(databaseName, list);
                }
                list.add(HibernateBaseDao);
            } else if (key.contains("_w")) {
                String databaseName = key.substring(0, key.indexOf("_w"));
                HibernateBaseDao HibernateBaseDao = new HibernateBaseDao(entry.getValue());
                writeDaos.put(databaseName, HibernateBaseDao);
            }

        }
    }

    public HibernateBaseDao getFivs_r() {
		return getRandomHibernateBaseDAO("fivs");
	}
    
    public HibernateBaseDao getFivs_w() {
        return writeDaos.get("fivs");
    }

	public HibernateBaseDao getTmodb_r() {
		return getRandomHibernateBaseDAO("tmodb");
	}

	public HibernateBaseDao getTmodb_w() {
		return writeDaos.get("tmodb");
	}
	
	public HibernateBaseDao getZabbix_r() {
		return getRandomHibernateBaseDAO("zabbix");
	}
	public HibernateBaseDao getZabbix_w() {
	    return writeDaos.get("zabbix");
    }
}
