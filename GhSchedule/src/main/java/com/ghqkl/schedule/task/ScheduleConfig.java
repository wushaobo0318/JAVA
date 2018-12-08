package com.ghqkl.schedule.task;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
public class ScheduleConfig {
	
	 	@Autowired
	    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
	    
	    private Map<String,ScheduledFuture<?>> futures = new HashMap<String,ScheduledFuture<?>>();

	    public void stopCron(String key) {
	           //取消定时任务
	    	ScheduledFuture<?> f=futures.get(key);
			if (f != null) {
				f.cancel(true);
			}
	    }
	    
	    public void stopAllCron() {
	           //取消所有定时任务
	    	Iterator<String> keys=futures.keySet().iterator();
	    	while (keys.hasNext()) {
				String key=keys.next();
				ScheduledFuture<?> f=futures.get(key);
				if (f != null) {
					f.cancel(true);
				}
			}
	    }
	    
	    public void setCron(Runnable r,String key,String cron) {
	        stopCron(key);
	        ScheduledFuture<?> future = threadPoolTaskScheduler.schedule(r, new CronTrigger(cron));
	        futures.put(key, future);
	    }
}
