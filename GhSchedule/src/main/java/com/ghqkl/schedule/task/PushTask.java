package com.ghqkl.schedule.task;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ghqkl.schedule.model.PushLogBean;
import com.ghqkl.schedule.model.UsersBean;
import com.ghqkl.schedule.service.PushLogService;
import com.ghqkl.schedule.service.UsersService;
import com.ghqkl.schedule.util.JPush;
import com.ghqkl.schedule.util.date.DateTimeUtil;

@Component
public class PushTask implements Runnable{
	
	private Log log = LogFactory.getLog(PushTask.class);
	
	@Autowired
	private PushLogService pushLogService;
	
	@Autowired
	private UsersService usersService;
	
	public void run()
	{
		log.info("PushTask Begin");
		PushLogBean logBean=new PushLogBean();
		logBean.setTime(DateTimeUtil.getCurrentDateTimeStr());
		List<PushLogBean> needPush=pushLogService.queryNeedPushList(logBean);
		for (PushLogBean  pushLogBean: needPush) {
			if(pushLogBean.getPhoneType()!=null&&StringUtils.isEmpty(pushLogBean.getCondition()))
			{
				if(JPush.push(pushLogBean.getTitle(), pushLogBean.getContent(), pushLogBean.getPhoneType(), null))
				{
					pushLogBean.setStatus(1);
					pushLogService.update(pushLogBean);
				}
			}
			if(StringUtils.isNotEmpty(pushLogBean.getCondition()))
			{
				UsersBean needPushUsers=new UsersBean();
				needPushUsers.setCondition(pushLogBean.getCondition());
				List<UsersBean> users=usersService.queryList(needPushUsers);
				int num=0;
				for (UsersBean u : users) {
					if(JPush.push(pushLogBean.getTitle(), pushLogBean.getContent(), pushLogBean.getPhoneType(), u.getPhone()))num++;
				}
				pushLogBean.setStatus(1);
				pushLogBean.setNum(num);
				pushLogService.update(pushLogBean);
			}
		}
		log.info("PushTask End");
	}	
}