package com.ghqkl.schedule.model;

public class RecordReleaseBean {
	private Integer id;
	private Integer userId;
	private Double spgReleased;
	private Double spgOriginalReleased;
	private String timestarmp;
	private String coin;
	
	public String getCoin() {
		return coin;
	}
	public void setCoin(String coin) {
		this.coin = coin;
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
	public Double getSpgReleased() {
		return spgReleased;
	}
	public void setSpgReleased(Double spgReleased) {
		this.spgReleased = spgReleased;
	}
	public Double getSpgOriginalReleased() {
		return spgOriginalReleased;
	}
	public void setSpgOriginalReleased(Double spgOriginalReleased) {
		this.spgOriginalReleased = spgOriginalReleased;
	}
	public String getTimestarmp() {
		return timestarmp;
	}
	public void setTimestarmp(String timestarmp) {
		this.timestarmp = timestarmp;
	}
	public RecordReleaseBean(Integer id, Integer userId, Double spgReleased, Double spgOriginalReleased,
			String timestarmp) {
		super();
		this.id = id;
		this.userId = userId;
		this.spgReleased = spgReleased;
		this.spgOriginalReleased = spgOriginalReleased;
		this.timestarmp = timestarmp;
	}
	public RecordReleaseBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "RecordRelease [id=" + id + ", userId=" + userId + ", spgReleased=" + spgReleased
				+ ", spgOriginalReleased=" + spgOriginalReleased + ", timestarmp=" + timestarmp + "]";
	}
	
}
