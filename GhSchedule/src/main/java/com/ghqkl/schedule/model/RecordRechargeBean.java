package com.ghqkl.schedule.model;

public class RecordRechargeBean {
    private Integer id;

    private Integer userId;

    private String coin;

    private String address;

    private String tx;

    private Double value;

    private String time;

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
        this.coin = coin == null ? null : coin.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getTx() {
        return tx;
    }

    public void setTx(String tx) {
        this.tx = tx == null ? null : tx.trim();
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
    
}