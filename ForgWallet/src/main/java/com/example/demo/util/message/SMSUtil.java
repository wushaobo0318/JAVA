package com.example.demo.util.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.util.HttpUtil;
import com.example.demo.util.PropertiesFile;

public class SMSUtil {
	private static Log log = LogFactory.getLog(SMSUtil.class);
	
	/***
	 * 
	 * @param areaCode
	 * @param phone
	 * @param length
	 * @param type 1注册，2找回密码，3修改/重置密码，4修改支付密码 ，5转账，6兑换HTT，7注册成功通知，8登录验证，9登录成功通知，10修改密码成功通知，11支付密码设置成功通知，12转账成功通知
	 * @return
	 */
	public static int sendSms(int areaCode,String phone,int length,int type)
	{
		
		int code=0;
		if(length==4)
		{
			code=(int)(Math.random()*9000+1000);
		}
		else if(length==5)
		{
			code=(int)(Math.random()*90000+10000);
		}
		else if(length==6)
		{
			code=(int)(Math.random()*900000+100000);
		}
		String url=PropertiesFile.getValue("sms_url");
		String account=PropertiesFile.getValue("sms_account");
		String password=PropertiesFile.getValue("sms_password");
		String msg=null;
		if(type==0) {
			msg="【HTT】您正登录，验证码是："+code+",该验证码3分钟有效";
		}else if(type==1) {
			msg="【HTT】您正在注册，验证码是："+code+",该验证码3分钟有效";
		}else if(type==2){
			msg="【HTT】您正在进行找回密码操作，验证码是："+code+",该验证码3分钟有效，如不是本人操作，请勿向他人透漏";
		}else if(type==3) {
			msg="【HTT】您正在进行修改密码操作，验证码是："+code+",该验证码3分钟有效，如不是本人操作，请勿向他人透漏";
		}else if(type==4){
			msg="【HTT】您正在进行修改支付密码操作，验证码是："+code+",该验证码3分钟有效，如不是本人操作，请勿向他人透漏";
		}else if(type==5) {
			msg="【HTT】您正在进行转账操作，验证码是："+code+",该验证码3分钟有效，如不是本人操作，请勿向他人透漏";
		}else if(type==6) {
			msg="【HTT】您正在进行兑换HTT操作，验证码是："+code+",该验证码3分钟有效，如不是本人操作，请勿向他人透漏";
		}else if(type==7) {
			msg="【HTT】恭喜您，已成功注册为HTT用户！（系统自动发送,请勿答复）";
		}else if(type==8) {
			msg="【HTT】您正在登录，验证码是："+code+",该验证码3分钟有效";
		}else if(type==9) {
			msg="【HTT】您正在登录HTT-wallet，如不是本人操作，请立刻修改密码";
		}else if(type==10) {
			msg="【HTT】登录密码修改成功";
		}else if(type==11) {
			msg="【HTT】交易密码修改成功";
		}else if(type==12) {
		}
		String mobile=areaCode+phone;
		if(phone.startsWith(areaCode+"")) {
			mobile=phone;
		}
		JSONObject map=new JSONObject();
		map.put("account", account);
		map.put("password", password);
		map.put("msg", msg);
		map.put("mobile", mobile);
		String params=map.toString();
		log.info("请求参数为:" + params);
		try {
			String result=HttpUtil.post(url, params);
			log.info("返回参数为:" + result);
			JSONObject jsonObject =  JSON.parseObject(result);
			String resCode = jsonObject.get("code").toString();
			String msgid = jsonObject.get("msgid").toString();
			String error = jsonObject.get("error").toString();
			log.info("状态码:" + resCode + ",状态码说明:" + error + ",消息id:" + msgid);
			if("0".equals(resCode))
			{
				//LocalCache.getInStance().setLocalCache(areaCode+phone+"_"+type,code,180000);  
				return code;
			}
			else
			{
				return code;
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("请求异常：" + e);
			return code;
		}
	}
	public static void main(String[] args) {
		String[] phones= {"8613712965088",
				"8613872332084",
				"8615888197890",
				"8618169358787",
				"8618088607008",
				"8615852333619",
				"8617051002401",
				"8615972901683",
				"8613789123279",
				"8613978019428",
				"8613875502255",
				"8613705788003",
				"8613264745010",
				"8617673298685"};
		System.out.println(phones.length);
		for(String phone:phones)
		{
			//SmsUtil.sendSms(phone, 4,12);
		}
	}
}
