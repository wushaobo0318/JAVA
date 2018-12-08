package com.ghqkl.schedule.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ghqkl.schedule.service.MiningLogService;
import com.ghqkl.schedule.service.MiningServerService;
import com.ghqkl.schedule.util.PropertiesFile;
import com.ghqkl.schedule.util.date.DateUtil;
import com.ghqkl.schedule.util.sms.SmsUtil;

@Component
public class GoodMorningTask implements Runnable{
	private Log log = LogFactory.getLog(GoodMorningTask.class);
	
	@Autowired
	private MiningLogService miningLogService;
	
	@Autowired
	private MiningServerService miningServerService;

    public void run(){
    	log.info("GoodMorningTask Begin");
		try {
			miningLogService.clearLogNotSaveWallet(DateUtil.getCurrentDateStr());
			miningServerService.resetMiningUserStatus();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//发送警告
			SmsUtil.sendSms(PropertiesFile.getValue("admin_phone"), "隔夜数据清理异常，请登录服务器查看！");
		}
		log.info("GoodMorningTask End");
    }
}
