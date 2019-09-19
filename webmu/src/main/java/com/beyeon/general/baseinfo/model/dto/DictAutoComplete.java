package com.beyeon.general.baseinfo.model.dto;

import com.beyeon.common.web.model.dto.AutoCompleteDto;
import com.beyeon.hibernate.fivsdb.TDict;

public class DictAutoComplete extends AutoCompleteDto {

	public DictAutoComplete() {
		super();
	}

	public DictAutoComplete(TDict city) {
		super(city.getName(), city.getDid().toString(), null, null);
	}

	public DictAutoComplete(String name, String value) {
		super(name, value, null, null);
	}
}
