/*package com.example.demo.util.message;

import com.example.demo.service.impl.RedisService;

public class SMSThread implements Runnable{
	
	private String phone;
	private int type;
	private String areaCode;
	private int length;
	private RedisService redisService ;
	public SMSThread(String phone,int type,String areaCode,int length ,RedisService redisService ) {
		this.phone=phone;
		this.type=type;
		this.areaCode=areaCode;
		this.length=length;
		this.redisService=redisService;
	}
	
	@Override
	public void run() {
		int code=SMSUtil.sendSms(Integer.parseInt(areaCode), phone, length, type);
		if(code!=0) {
			redisService.setSMSVerificationCode(areaCode+phone+"_"+type, code+"", 180);
		}
	}

}
*/