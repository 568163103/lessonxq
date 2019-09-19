package com.beyeon.common.web.model.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分页对象. 包含当前页数据及分页信息如总记录数.
 */

public class PageObject implements Serializable {
	private static final long serialVersionUID = -1289317936728849804L;

	private static int DEFAULT_PAGE_SIZE = 18;
	
	private int pageSize = DEFAULT_PAGE_SIZE; // 每页的记录数
	private long currentPageNum = 1; // 当前页
	private long totalNum = 0; // 总记录数
	private int pageClick = 0;
	
	//传参对象 存储一些条件参数
	private Map<String,String> params = new HashMap<String,String>();
	private List resultList = new ArrayList(); // 当前页中存放的记录,类型一般为List
	private String sortName;//排序字段
	/**
	 * 排序的顺序  desc asc
	 */
	private String sortOrder;
	/**
	 * 构造方法，只构造空页.
	 */
	public PageObject() {
	}

	public PageObject(int currentPageNum, int pageSize) {
		this(currentPageNum, 100000, pageSize);
	}
	
	public PageObject(int pageSize) {
		this(1, 0, pageSize);
	}

	/**
	 * 默认构造方法.
	 * 
	 * @param currentPageNum  本页数
	 * @param totalNum       数据库中总记录条数
	 * @param pageSize        本页容量
	 */
	public PageObject(int currentPageNum, int totalNum, int pageSize) {
		this.pageSize = pageSize;
		this.currentPageNum = currentPageNum<1?1:currentPageNum;
		this.totalNum = totalNum;
	}

	/**
	 * 取总记录数.
	 */
	public long getTotalNum() {
		return this.totalNum;
	}

	public void setTotalNum(long totalNum) {
		if(!(totalNum < 0))
			this.totalNum = totalNum;
	}

	/**
	 * 取总页数.
	 */
	public long getPageNum() {
		if (totalNum % pageSize == 0)
			return totalNum / pageSize;
		else
			return totalNum / pageSize + 1;
	}

	/**
	 * 取每页数据容量.
	 */
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		if(0 < pageSize)
			this.pageSize = pageSize;
	}

	/**
	 * 取当前页中的记录.
	 */
	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

	/**
	 * 取该页当前页码,页码从1开始.
	 */
	public long getCurrentPageNum() {
		return currentPageNum;
	}

	public void setCurrentPageNum(long currentPageNum) {
		this.currentPageNum = currentPageNum < 1 ? 1 : currentPageNum;
	}

	/**
	 * 该页是否有下一页.
	 */
	public boolean hasNextPage() {
		return this.getCurrentPageNum() < this.getPageNum();
	}

	/**
	 * 该页是否有上一页.
	 */
	public boolean hasPreviousPage() {
		return this.getCurrentPageNum() > 1;
	}

	/**
	 * 该页是否有下一页；返回1表示有，返回0表示无
	 */
	public int getNextPage() {
		if (this.hasNextPage()) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 该页是否有上一页；返回1表示有，返回0表示无
	 */
	public int getPreviousPage() {
		if (this.hasPreviousPage()) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 获取任一页第一条数据在数据集的位置.
	 * 
	 * @param PageNum
	 *            从1开始的页号
	 * @param pageSize
	 *            每页记录条数
	 * @return 该页第一条数据
	 */
	public long getStartLineNum() {
		if (getPageNum() < getCurrentPageNum()) {
			setCurrentPageNum(getPageNum());
		}
		return (currentPageNum - 1) * pageSize;
	}

	public int getPageClick() {
		return pageClick;
	}

	public void setPageClick(int pageClick) {
		if (pageClick < 1) {
			this.setTotalNum(0);
			this.setCurrentPageNum(1);
			this.pageClick = 0;
			return;
		}
		this.pageClick = pageClick;
	}

	public Map<String, String> getParams() {
		return this.params;
	}

	public Map<String, String> setParams(Map<String, String> params){
		return this.params = params;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

}