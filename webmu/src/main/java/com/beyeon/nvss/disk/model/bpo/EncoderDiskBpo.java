package com.beyeon.nvss.disk.model.bpo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beyeon.hibernate.fivsdb.EncoderDisk;
import com.beyeon.nvss.disk.model.dao.EncoderDiskDao;

@Component("encoderDiskBpo")
public class EncoderDiskBpo {
	private Logger logger =LoggerFactory.getLogger(EncoderDiskBpo.class);
	private EncoderDiskDao encoderDiskDao;
	
	public EncoderDiskDao getEncoderDiskDao() {
		return encoderDiskDao;
	}
	
	public void setEncoderDiskDao(EncoderDiskDao encoderDiskDao) {
		this.encoderDiskDao = encoderDiskDao;
	}
	
	public EncoderDisk find(String id) {
		return this.encoderDiskDao.find(id);
	}
	
	public List findAllEncoderDisk() {
		return this.encoderDiskDao.findAllEncoderDisk();
	}
}
