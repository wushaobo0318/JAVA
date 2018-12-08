package com.ghqkl.schedule.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ghqkl.schedule.util.json.JSONUtil;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
/***
 * 极光推送
 * @author Administrator
 *
 */

public class JPush {
	
	private static Log log = LogFactory.getLog(JPush.class);
    
	public static boolean push(String title, String content, int type, String phone) {
		JPushClient jpushClient = new JPushClient(PropertiesFile.getValue("jpush_secret"),PropertiesFile.getValue("jpush_key"));
		Builder builder = PushPayload.newBuilder().setNotification(Notification.alert(title))
				.setMessage(Message.content(content));
		if (type == 1)
			builder.setPlatform(Platform.all());
		else if (type == 2)
			builder.setPlatform(Platform.ios());
		else if (type == 3)
			builder.setPlatform(Platform.android());
		if (StringUtils.isNotEmpty(phone)) {
			builder.setAudience(Audience.tag(phone));
		}
		else
		{
			builder.setAudience(Audience.all());
		}
		PushPayload payload = builder.build();
		try {
			PushResult result = jpushClient.sendPush(payload);
			log.info(JSONUtil.toJson(result));
			if (result.getResponseCode() == 200)
				return true;
			return false;
		} catch (APIConnectionException e) {
			log.info("connection error");
			e.printStackTrace();
			return false;
		} catch (APIRequestException e) {
			log.info("request error");
			e.printStackTrace();
			return false;
		}
	}
    
 /***
     * 全部推送
     * @author Administrator
     *
     */
	public void jpushAndroidAll(String title,String content) {
		JPushClient jpushClient = new JPushClient(PropertiesFile.getValue("jpush_secret"),PropertiesFile.getValue("jpush_key"));
	        PushPayload payload=PushPayload.newBuilder()
	        		.setPlatform(Platform.all())
	        		.setAudience(Audience.all())
	        		.setNotification(Notification.alert(title))
	        		.setMessage(Message.content(content))
	        		.build();
	        try {
				PushResult result= jpushClient.sendPush(payload);
				System.out.println(JSONUtil.toJson(result));
				System.out.println("成功");
				System.out.println(result.msg_id);
				System.out.println(result.sendno);
			} catch (APIConnectionException e) {
				System.out.println("connection error");
				e.printStackTrace();
			} catch (APIRequestException e) {
				System.out.println("request error");
				e.printStackTrace();
			}
	}
}