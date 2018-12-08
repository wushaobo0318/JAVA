package com.ghqkl.schedule.model;
/**
 * 用户资产表
 * @author Administrator
 *
 */
public class UserAssetsBean {
	private Integer id;
	private Integer userId;
	private String coin;
	private Double num;
	private String address;
	private Double original;
	private Double released;
	private Double originalReleased;
	private String updateTime;
	private Integer status;
	private Double percentage;
	private Integer level;
	/**总释放量*/
	private Double totalReleased;
	
	
	public Double getTotalReleased() {
		return totalReleased;
	}

	public void setTotalReleased(Double totalReleased) {
		this.totalReleased = totalReleased;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	public Double getPercentage() {
		return percentage;
	}
	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getCoin() {
		return coin;
	}
	public void setCoin(String coin) {
		this.coin = coin;
	}
	public Double getNum() {
		return num;
	}
	public void setNum(Double num) {
		this.num = num;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getOriginal() {
		return original;
	}
	public void setOriginal(Double original) {
		this.original = original;
	}
	public Double getReleased() {
		return released;
	}
	public void setReleased(Double released) {
		this.released = released;
	}
	public Double getOriginalReleased() {
		return originalReleased;
	}
	public void setOriginalReleased(Double originalReleased) {
		this.originalReleased = originalReleased;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
   
}