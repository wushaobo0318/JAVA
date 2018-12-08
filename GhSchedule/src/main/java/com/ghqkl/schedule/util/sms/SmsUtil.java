package com.ghqkl.schedule.util.sms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ghqkl.schedule.util.HttpUtil;
import com.ghqkl.schedule.util.PropertiesFile;

public class SmsUtil {
	private static Log log = LogFactory.getLog(SmsUtil.class);

	public static void sendSms(String phone,String msg)
	{
		String url=PropertiesFile.getValue("sms_url");
		String account=PropertiesFile.getValue("sms_account");
		String password=PropertiesFile.getValue("sms_password");
		JSONObject map=new JSONObject();
		map.put("account", account);
		map.put("password", password);
		map.put("msg", msg);
		map.put("mobile", phone);
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
		} catch (Exception e) {
			// TODO: handle exception
			log.error("请求异常：" + e);
		}
	}
	public static void main(String[] args) {
		SmsUtil.sendSms("8613264745010", "爬取币价异常，请登录服务器查看！");
	}
}