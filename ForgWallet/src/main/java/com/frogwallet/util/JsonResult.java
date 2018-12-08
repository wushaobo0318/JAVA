package com.frogwallet.util;

//定义一个json对象，用于返回
public class JsonResult {

	private Integer code;	//状态码
	private Boolean success;	//	是否成功
	private String message;	//错误信息
	private Object data;	//返回数据
	public JsonResult()
	{
		
	}
	public JsonResult(Integer code,Boolean success,String message,Object data)
	{
		this.code = code;
		this.success = success;
		this.message = message;
		this.data = data;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	

	

	

}
