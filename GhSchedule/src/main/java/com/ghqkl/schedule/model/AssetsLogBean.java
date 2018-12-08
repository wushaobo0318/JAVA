package com.ghqkl.schedule.model;
/**
 * 用户资产变更日志
 * @author Administrator
 *
 */
public class AssetsLogBean {
	private Integer id;
	private Integer userId;
	private String coin;
	private Double change;
	private String time;
	private Integer type;
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
	public Double getChange() {
		return change;
	}
	public void setChange(Double change) {
		this.change = change;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
}