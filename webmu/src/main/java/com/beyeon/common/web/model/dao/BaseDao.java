package com.beyeon.common.web.model.dao;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
/*
*
* 基础数据库访问对象
* */
public abstract class BaseDao {
	private FacadeDao facadeDao;

    public void setFacadeDao(FacadeDao facadeDao) {
        this.facadeDao = facadeDao;
    }

	/**************************数据库访问对象******************************/

    public HibernateBaseDao getFivs_r() {
        return facadeDao.getFivs_r();
    }

    public HibernateBaseDao getFivs_w() {
        return facadeDao.getFivs_w();
    }
    public HibernateBaseDao getZabbix_r() {
        return facadeDao.getZabbix_r();
    }

    public HibernateBaseDao getZabbix_w() {
        return facadeDao.getZabbix_w();
    }

	public HibernateBaseDao getTmodb_r() {
		return facadeDao.getTmodb_r();
	}

	public HibernateBaseDao getTmodb_w() {
		return facadeDao.getTmodb_w();
	}

	/************************基础数据库操作方法***************************/

	public <T> List<T> getAllFivs(Class<T> entityClass) {
		return this.getFivs_r().getAll(entityClass);
	}

	public <T> T getFivs(Class<T> entityClass, Serializable id) {
		return this.getFivs_r().get(entityClass, id);
	}

	public void saveFivs(Object object) {
		this.getFivs_w().save(object);
	}

	public void saveOrUpdateFivs(Object object) {
		this.getFivs_w().saveOrUpdate(object);
	}

	public void updateFivs(Object object) {
		this.getFivs_w().update(object);
	}

	public <T> void saveAllFivs(Collection<T> objects) {
		this.getFivs_w().saveAll(objects);
	}

	public void deleteFivs(Object object) {
		this.getFivs_w().delete(object);
	}

	public void freshFivs(Object object) {
		this.getFivs_w().fresh(object);
	}

	/*************************基础业务实现方法***************************/

	public String createId(String id,String codetype,String channelnum,String position){
		return (String) this.getFivs_w().findUniqueSql("select tb_make_union_code(?,?,?,?)", new String[]{id,codetype,channelnum,position});
	}
}
