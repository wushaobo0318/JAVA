package com.ghqkl.schedule.model;

public class CoinPriceLogBean {
    private Integer id;

    private String coin;

    private Double price;

    private Double change;

    private String createTime;
    
    private String from;
    
    private Integer createUser;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCoin() {
		return coin;
	}

	public void setCoin(String coin) {
		this.coin = coin;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getChange() {
		return change;
	}

	public void setChange(Double change) {
		this.change = change;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

}