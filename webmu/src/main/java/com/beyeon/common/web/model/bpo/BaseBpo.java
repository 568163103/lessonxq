package com.beyeon.common.web.model.bpo;

/**
 * Created by Administrator on 2016/8/5.
 */
public abstract class BaseBpo {
	public abstract boolean checkUnique(String attrName, String value, String id);
}
