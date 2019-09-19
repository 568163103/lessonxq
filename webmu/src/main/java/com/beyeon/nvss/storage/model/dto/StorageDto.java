package com.beyeon.nvss.storage.model.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageDto {
	private String id;
	private String serverId;
	private int result;
	
	private List<StorageItem> itemList = new ArrayList<StorageItem>();
	private Map<String, List<StorageItem>> itemMap = new HashMap<String, List<StorageItem>>();

	public void makeStorageGroup() {
		if (itemList == null)
			return;
		
		// make group
		for (int idx = 0; idx < itemList.size(); ++idx) {
			StorageItem e = itemList.get(idx);
			String key = e.getBeginTime().substring(0, 10);
			
			List<StorageItem> items = null;
			if (itemMap.containsKey(key)) {
				items = itemMap.get(key);
			} else {
				items = new ArrayList<StorageItem>();
				itemMap.put(key, items);
			}
			items.add(e);
		}
		
		// calculate time
		for (List<StorageItem> item : itemMap.values()) {
			int totalLength = 0;
			for (int idx = 0; idx < item.size(); ++idx) {
				totalLength += item.get(idx).getLength();
			}
			
			for (int idx = 0; idx < item.size(); ++idx) {
				item.get(idx).setTotalLength(totalLength);
			}
		}
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public List<StorageItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<StorageItem> itemList) {
		this.itemList = itemList;
	}

	public Map<String, List<StorageItem>> getItemMap() {
		return itemMap;
	}

	public void setItemMap(Map<String, List<StorageItem>> itemMap) {
		this.itemMap = itemMap;
	}
}
