package com.beyeon.nvss.bussiness.model.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.beyeon.nvss.common.model.dto.MduResultDto;

/**
 * User: Administrator
 * Date: 2015/11/17
 * Time: 9:25
 */
public class DisplayResultDto extends MduResultDto<DisplayDto> {
	private List<Map<String,Object>> videoInfos = new ArrayList<Map<String, Object>>();

	public List<Map<String,Object>> getVideoInfos() {
		return videoInfos;
	}
}
