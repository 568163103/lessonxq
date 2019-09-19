package com.beyeon.nvss.disk.model.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.beyeon.hibernate.fivsdb.EncoderDisk;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

@Component("encoderDiskDao")
public class EncoderDiskDao extends NvsBaseDao {
	
	public EncoderDisk find(String id) {
		String sql = "select ed from EncoderDisk ed where ed.id=?";
		List resList = this.getFivs_r().find(sql, new Object[]{id});
		if (resList.size() > 0)
			return (EncoderDisk)resList.get(0);
		return null;
	}
	
	public List findAllEncoderDisk() {
		String hql = "select ed from  EncoderDisk ed";
		return this.getFivs_r().find(hql, null);
	}
}
