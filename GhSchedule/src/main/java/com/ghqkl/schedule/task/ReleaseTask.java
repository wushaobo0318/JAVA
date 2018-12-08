
package com.ghqkl.schedule.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ghqkl.schedule.service.UserAssetsService;

@Component
public class ReleaseTask implements Runnable{
	private Log log = LogFactory.getLog(ReleaseTask.class);
	@Autowired
	private UserAssetsService userAssetsService;
    public void run(){
    	log.info("release Begin");
		try {
			userAssetsService.release();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//发送警告
		}
		log.info("release End");
    }
}
