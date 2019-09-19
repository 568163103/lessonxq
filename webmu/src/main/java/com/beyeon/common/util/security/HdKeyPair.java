package com.beyeon.common.util.security;

public class HdKeyPair {
	public static int DEFAULT_REMAIN_TIME = 2*60*1000;
	public static int DEFAULT_SHORT_TIME = 10*60*1000;
	public static int DEFAULT_MIDDLE_TIME = 30*60*1000;
	public static int DEFAULT_LONG_TIME = 60*60*1000;
	public static int DEFAULT_HUGE_TIME = 24*60*60*1000;
	private long createTime = System.currentTimeMillis();
	private String publicKey = "";
	private String beforePrivateKey = "";
	private String privateKey = "";
	private String afterPrivateKey = "";
	private int validTime = DEFAULT_MIDDLE_TIME;
	private int remainTime = DEFAULT_REMAIN_TIME;
	private String remoteIp = "";
	private String isused = "0";
	
	public HdKeyPair() {
	}

	public HdKeyPair(String publicKey, String privateKey) {
		this.publicKey = publicKey;
		this.privateKey = privateKey;
	}
	
	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getBeforePrivateKey() {
		return beforePrivateKey;
	}

	public void setBeforePrivateKey(String beforePrivateKey) {
		this.beforePrivateKey = beforePrivateKey;
	}

	public String getAfterPrivateKey() {
		return afterPrivateKey;
	}

	public void setAfterPrivateKey(String afterPrivateKey) {
		this.afterPrivateKey = afterPrivateKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public int getValidTime() {
		return validTime;
	}

	public void setValidTime(int validTime) {
		this.validTime = validTime;
	}

	public int getRemainTime() {
		return remainTime;
	}

	public void setRemainTime(int remainTime) {
		this.remainTime = remainTime;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public String getIsused() {
		return isused;
	}

	public void setIsused(String isused) {
		this.isused = isused;
	}

	public boolean isValid() {
		return (System.currentTimeMillis()-createTime) < this.validTime;
	}

	public boolean isSleep() {
		return (System.currentTimeMillis()-createTime) < this.remainTime;
	}

	public boolean isDie() {
		return (System.currentTimeMillis()-createTime) > (this.validTime + this.remainTime);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		HdKeyPair hdKeyPair = (HdKeyPair) o;

		return publicKey.equals(hdKeyPair.publicKey);

	}

}
