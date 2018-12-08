package com.ghqkl.schedule.model;
/***
 * 记录表
 * @author Administrator
 *
 */
public class PushLogBean {
	//id
	 private Integer  id ;
	 //平台id
	 private Integer  platformId ;
	 //平台名称
	 private String  name;
	 //标题
	 private String  title ;
	 //推送类型(版本，其他)
	 private Integer  type ;
	 //推送内容
	 private String  content ;
	 //时间
	 private String  time ;
	 //推送状态()
	 private Integer   status;
	 //推送人数
	 private Integer  num ;
	 //设备类型（安卓，ios）
	 private Integer   phoneType;
	 //条件（查询条件）
	 private String   condition;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPlatformId() {
		return platformId;
	}
	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getPhoneType() {
		return phoneType;
	}
	public void setPhoneType(Integer phoneType) {
		this.phoneType = phoneType;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
}
