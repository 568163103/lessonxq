package com.beyeon.general.baseinfo.model;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beyeon.common.aop.annotation.Cache;
import com.beyeon.common.config.ResourceUtil;
import com.beyeon.general.baseinfo.model.bpo.CorpBpo;
import com.beyeon.general.baseinfo.model.bpo.DeptBpo;
import com.beyeon.general.baseinfo.model.bpo.DictBpo;
import com.beyeon.security.util.SystemSnUtils;

/**
 * User: Administrator
 * Date: 2016/4/22
 * Time: 11:16
 */
@Cache(cacheName = "基础Facade")
@Component("baseinfoFacade")
public class BaseinfoFacade {
	private static Logger logger = LoggerFactory.getLogger(BaseinfoFacade.class);
	private DeptBpo deptBpo;
	private DictBpo dictBpo;
	private CorpBpo corpBpo;

	public DeptBpo getDeptBpo() {
		return deptBpo;
	}

	public void setDeptBpo(DeptBpo deptBpo) {
		this.deptBpo = deptBpo;
	}

	public DictBpo getDictBpo() {
		return dictBpo;
	}

	public void setDictBpo(DictBpo dictBpo) {
		this.dictBpo = dictBpo;
	}

	public CorpBpo getCorpBpo() {
		return corpBpo;
	}

	public void setCorpBpo(CorpBpo corpBpo) {
		this.corpBpo = corpBpo;
	}

	@Cache(cacheName = "更新系统配制",refreshExpre = Cache.Bs,exclusive = true,startRun = false)
	public void refreshSn(){
		getDictBpo().update(SystemSnUtils.refresh(60 * 1000));
	}

	private String[] getStorePath(){
		int sd = 0;
		String path = "";
		File[] root = File.listRoots();
		for (int i = 1;i < root.length;i++){
			try {
				if (FileSystemUtils.freeSpaceKb(root[i].getAbsolutePath()) > 100000) {
					path = root[i].getAbsolutePath();
					sd = i;
					break;
				}
			} catch (IOException e) {
				logger.debug("读取磁盘出错：{}",root[i].getName());
			}
		}
		logger.debug(path);
		if (StringUtils.isBlank(path) && root.length > 0){
			path = root[0].getAbsolutePath();
		}
		if (!path.endsWith(File.separator)){
			path += File.separator;
		}
		path += ResourceUtil.getServerConf("store.path");
		return new String[]{"SD__"+sd+"__",path};
	}

	@Cache(cacheName = "获取上传文件真实存储路径",refreshExpre = Cache.Bs,startRun = true)
	public String getRealStorePath(){
		return getStorePath()[1];
	}

	@Cache(cacheName = "获取上传文件虚拟存储路径",refreshExpre = Cache.Bs,startRun = true)
	public String getVirtualStorePath(){
		return getStorePath()[0];
	}

	public String getAbsolutePath(String imagePath){
		String [] imagePaths = imagePath.split("__");
		File[] root = File.listRoots();
		String path = root[Integer.valueOf(imagePaths[1])].getAbsolutePath();
		if (!path.endsWith(File.separator)){
			path += File.separator;
		}
		path += ResourceUtil.getServerConf("store.path");
		return path+imagePaths[2];
	}
}
