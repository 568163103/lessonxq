package com.beyeon.general.baseinfo.model.bpo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.beyeon.common.aop.annotation.Cache;
import com.beyeon.common.web.model.dto.AutoCompleteDto;
import com.beyeon.common.web.model.dto.TreeNode;
import com.beyeon.general.baseinfo.model.dao.DictDao;
import com.beyeon.hibernate.fivsdb.TDict;

/**
 * User: lwf Date: 12-5-23 Time: 上午9:59
 */
@Cache(cacheName = "字典管理")
@Component("dictBpo")
public class DictBpo {
	private static Map<String, TDict> dictMap = new HashMap<String, TDict>();
	private static Map<String, Map<String,AutoCompleteDto>> parentDictMap = new HashMap<String, Map<String,AutoCompleteDto>>();

	public static TDict getDict(String did) {
		return dictMap.get(did);
	}

	public static TDict getDict(String pid,String value) {
		if (null == parentDictMap.get(pid)) {
			return null;
		}
		return dictMap.get(parentDictMap.get(pid).get(value).getDesc());
	}

	public static void setDictName(String pid,String value,String name) {
		if (null == parentDictMap.get(pid)) {
			return ;
		}
		parentDictMap.get(pid).get(value).setLabel(name);
		dictMap.get(parentDictMap.get(pid).get(value).getDesc());
	}

	public static String getDictName(String did) {
		if (null == dictMap.get(did)) {
			return "";
		}
		return dictMap.get(did).getName();
	}

	public static String getDictName(String pid,String value) {
		if (null == parentDictMap.get(pid)) {
			return "";
		}
		return parentDictMap.get(pid).get(value).getLabel();
	}

	/**
	 * @return
	 */
	public static Collection<AutoCompleteDto> find(String key) {
		return parentDictMap.get(key).values();
	}

	private static void putDict(Map<String, Map<String,AutoCompleteDto>> currParentDictMap,TDict dict) {
		Map<String,AutoCompleteDto> autoCompleteDtos = currParentDictMap.get(dict.getPreId().toString());
		if (null == autoCompleteDtos) {
			autoCompleteDtos = new HashMap<String,AutoCompleteDto>();
			currParentDictMap.put(dict.getPreId().toString(), autoCompleteDtos);
		}
		AutoCompleteDto autoCompleteDto = new AutoCompleteDto(dict.getName(), dict.getValue());
		autoCompleteDto.setDesc(dict.getDid().toString());
		autoCompleteDtos.put(dict.getValue(), autoCompleteDto);
	}

	private DictDao dictDao;
	public void setDictDao(DictDao dictDao) {
		this.dictDao = dictDao;
	}

	@Cache(type = Cache.REFRESH, cacheName = "字典", refreshExpre = Cache.Bs)
	public void initDict() {
		Map<String, TDict> currDictMap = new HashMap<String, TDict>();
		Map<String, Map<String,AutoCompleteDto>> currParentDictMap = new HashMap<String, Map<String,AutoCompleteDto>>();
		List<TDict> dicts = dictDao.find();
		for (TDict dict : dicts) {
			currDictMap.put(dict.getDid().toString(), dict);
			putDict(currParentDictMap,dict);
		}
		Map<String, TDict> beforeDictMap = dictMap;
		Map<String, Map<String,AutoCompleteDto>> beforeParentDictMap = parentDictMap;
		dictMap = currDictMap;
		parentDictMap = currParentDictMap;
		beforeDictMap.clear();
		beforeParentDictMap.clear();
	}

	public List<TreeNode> findDictTree(String did) {
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		List<TDict> parentDicts = this.dictDao.findByPid(did);
		for (TDict dict : parentDicts) {
			TreeNode treeNode = new TreeNode(dict.getDid().toString(), dict.getName(),(short)1);
			treeNode.setIcon(false);
			treeNode.setExpand(false);
			treeNodes.add(treeNode);
		}

		for (int i = 0; i < treeNodes.size(); i++) {
			List<TDict> childDicts = this.dictDao.findByPid(treeNodes.get(i).getKey());
			for (TDict dict : childDicts) {
				TreeNode treeNode = new TreeNode(dict.getDid().toString(), dict.getName(),(short)2);
				treeNode.setIcon(false);
				treeNodes.get(i).getChildren().add(treeNode);
			}
		}
		return  treeNodes;
	}


	public TDict get(Integer mid) {
		return this.dictDao.getFivs(TDict.class, mid);
	}

	public void save(TDict dict) {
		dict.setDmltime(new Date());
		if (0 == dict.getPreId()){
			int did = this.dictDao.findMaxDidByPreId(0);
			dict.setDid(did+1);
			this.dictDao.save(dict);
		} else {
			this.dictDao.saveFivs(dict);
			this.dictDao.freshFivs(dict);
		}
		dictMap.put(dict.getDid().toString(),dict);
		putDict(parentDictMap, dict);
	}

	public void update(TDict dict) {
		this.dictDao.updateFivs(dict);
		dictMap.put(dict.getDid().toString(),dict);
		putDict(parentDictMap, dict);
	}

	public void deleteByDid(String id) {
		this.dictDao.deleteByDid(id);
	}

}
