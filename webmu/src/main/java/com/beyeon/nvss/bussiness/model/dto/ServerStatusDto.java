package com.beyeon.nvss.bussiness.model.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: Administrator
 * Date: 2016/1/5
 * Time: 11:49
 */
public class ServerStatusDto {
	public static int XSIZE = 13;
	private String id;
	private String name;
	private Object ymin;
	private Object ymax;
	private String yunit;
	private String[] xaxis = new String[XSIZE];
	private List childNames = new ArrayList();
	private List<List> childDataes = new ArrayList<List>();

	public ServerStatusDto(String id,String name,Object ymin,Object ymax,String yunit,String [] childNames) {
		this(id,name,ymin,ymax,yunit,childNames,true,XSIZE);
	}
	public ServerStatusDto(String id,String name,Object ymin,Object ymax,String yunit,String [] childNames,boolean initXaxis,int xsize) {
		this.id = id;
		this.name = name;
		this.ymin = ymin;
		this.ymax = ymax;
		this.yunit = yunit;
		this.childNames = Arrays.asList(childNames);
		for (int i = 0; i < this.childNames.size(); i++) {
			childDataes.add(new ArrayList(xaxis.length));
		}
		xaxis = new String[xsize];
		if (initXaxis) {
			for (int i = xaxis.length - 1; i >= 0; i--) {
				xaxis[xaxis.length - 1 - i] = (String.valueOf(i * 5) + "秒");
				for (int j = 0; j < this.childNames.size(); j++) {
					childDataes.get(j).add(0);
				}
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getYmin() {
		return ymin;
	}

	public void setYmin(Object ymin) {
		this.ymin = ymin;
	}

	public Object getYmax() {
		return ymax;
	}

	public void setYmax(Object ymax) {
		this.ymax = ymax;
	}

	public String getYunit() {
		return yunit;
	}

	public void setYunit(String yunit) {
		this.yunit = yunit;
	}

	public String[] getXaxis() {
		return xaxis;
	}

	public void setXaxis(String[] xaxis) {
		this.xaxis = xaxis;
	}

	public List getChildNames() {
		return childNames;
	}

	public void setChildNames(List childNames) {
		this.childNames = childNames;
	}

	public List<List> getChildDataes() {
		return childDataes;
	}

	public void setChildDataes(List<List> childDataes) {
		this.childDataes = childDataes;
	}
}
