package com.ghqkl.schedule.task;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ghqkl.schedule.model.UserAssetsBean;
import com.ghqkl.schedule.service.UserAssetsService;
import com.ghqkl.schedule.util.BTCUtil;
import com.ghqkl.schedule.util.LTCUtil;

@Component
public class SyncLtcTask implements Runnable {
	
	private final static int rows=500;
	
	@Autowired
	private UserAssetsService userAssetsService;

	private Log log = LogFactory.getLog(SyncLtcTask.class);

	public void run() {
		log.info("SyncLtcTask Begin");
		UserAssetsBean userAsset = new UserAssetsBean();
		userAsset.setCoin("LTC");
		int count=0;
		List<Object> list=null;
		try {
			list=LTCUtil.getInstance().getTxids();
			count = list.size();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int endNum=count%rows;
		for(int i=0;i<count;i+=500)
		{
			if(endNum>0&&i+endNum==count)
			{
				new Thread(new SyncLTCThread(list.subList(i, i+endNum))).start();;
			}
			else
			{
				new Thread(new SyncLTCThread(list.subList(i, i+rows))).start();;
			}
		}
	}
	
	class SyncLTCThread implements Runnable
	{
		private Log log = LogFactory.getLog(SyncLTCThread.class);
		
		List<Object> list;
		
		private int currentThreadNum;
		
		public SyncLTCThread(List<Object> list)
		{
			this.list=list;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (list == null || list.size() == 0)return;
			log.info("SyncLTCThread start tx:"+list.get(0).toString());
			currentThreadNum=list.size();
			Integer uid=null;
			for (Object object : list) {
				currentThreadNum--;
				log.info("currentThreadNum left:"+currentThreadNum);
				try {
					if(userAssetsService.sycnBTSystemAssets(object.toString(),"LTC"))
					{
						log.info("userId:"+uid+" ,TX:"+object+" ,syncLTC success");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.info("userId:"+uid+" ,TX:"+object+" ,syncLTC fail");
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			log.info("SyncLTCThread End");
		}
	}
}
