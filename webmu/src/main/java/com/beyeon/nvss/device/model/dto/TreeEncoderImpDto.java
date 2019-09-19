package com.beyeon.nvss.device.model.dto;

/**
 * User: Administrator
 * Date: 2015/11/3
 * Time: 15:29
 */
public class TreeEncoderImpDto extends EncoderImpDto {
	String oneNo;
	String oneName;
	String twoNo;
	String twoName;
	String encoderParNo;

	public String getOneNo() {
		if (null == oneNo){
			return "";
			//return Server.getObjectMap(ServerType.CMU.toString()).keySet().iterator().next();
		}
		return oneNo;
	}

	public void setOneNo(Object oneNo) {
		if (null != oneNo)
			this.oneNo = oneNo.toString();
	}

	public String getOneName() {
		if (null == oneName){
			return "";
			//return Server.getObjectMap(ServerType.CMU.toString()).values().iterator().next();
		}
		return oneName;
	}

	public void setOneName(String oneName) {
		this.oneName = oneName;
	}

	public String getTwoNo() {
		if (null == twoNo){
			return "";
			//return Server.getObjectMap(ServerType.MDU.toString()).keySet().iterator().next();
		}
		return twoNo;
	}

	public void setTwoNo(Object twoNo) {
		if (null != twoNo)
			this.twoNo = twoNo.toString();
	}

	public String getTwoName() {
		if (null == twoName){
			return "";
			//return Server.getObjectMap(ServerType.MDU.toString()).values().iterator().next();
		}
		return twoName;
	}

	public void setTwoName(String twoName) {
		this.twoName = twoName;
	}

	public String getEncoderParNo() {
		if (null == encoderParNo){
			return getEncoderNo();
		}
		return encoderParNo;
	}

	public void setEncoderParNo(Object encoderParNo) {
		if (null != encoderParNo)
			this.encoderParNo = encoderParNo.toString();
	}

	public String getEncoderName() {
		if (null == encoderName)
			return encoderParNo+"-车载设备-"+encoderNo;
		return encoderName;
	}

}
