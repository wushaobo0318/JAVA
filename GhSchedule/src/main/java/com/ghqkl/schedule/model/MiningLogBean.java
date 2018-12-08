package com.ghqkl.schedule.model;

public class MiningLogBean {
	private Integer logId;
	
	private Integer userId;
	
	private Double miningNum;
	
	private Integer miningServerId;
	
	private String miningTime;

	public Integer getLogId() {
		return logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Double getMiningNum() {
		return miningNum;
	}

	public void setMiningNum(Double miningNum) {
		this.miningNum = miningNum;
	}

	public Integer getMiningServerId() {
		return miningServerId;
	}

	public void setMiningServerId(Integer miningServerId) {
		this.miningServerId = miningServerId;
	}

	public String getMiningTime() {
		return miningTime;
	}

	public void setMiningTime(String miningTime) {
		this.miningTime = miningTime;
	}
	
	
}
