package com.ghqkl.schedule.model;



public class RecordTransferBean {
    private Integer id;
	private Integer userId;
	private Integer userDi;
    private String coin;
    private Double value;
    private String time;
    private Double surplus_spg;
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
	public Integer getUserDi() {
		return userDi;
	}
	public void setUserDi(Integer userDi) {
		this.userDi = userDi;
	}
	public String getCoin() {
		return coin;
	}
	public void setCoin(String coin) {
		this.coin = coin;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Double getSurplus_spg() {
		return surplus_spg;
	}
	public void setSurplus_spg(Double surplus_spg) {
		this.surplus_spg = surplus_spg;
	}

  
}