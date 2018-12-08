package com.ghqkl.schedule.task;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ghqkl.schedule.model.UserAssetsBean;
import com.ghqkl.schedule.service.UserAssetsService;
import com.ghqkl.schedule.util.BSTUtil;
import com.ghqkl.schedule.util.SPGUtil;

@Component
public class SyncSpgTask implements Runnable {
	
	private final static int rows=500;
	
	@Autowired
	private UserAssetsService userAssetsService;

	private Log log = LogFactory.getLog(SyncSpgTask.class);

	public void run() {
		log.info("SyncSpgTask Begin");
		UserAssetsBean userAsset = new UserAssetsBean();
		userAsset.setCoin("SPG");
		int count=0;
		List<Object> list=null;
		try {
			list=SPGUtil.getInstance().getTxids();
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
				new Thread(new SyncSPGThread(list.subList(i, i+endNum))).start();;
			}
			else
			{
				new Thread(new SyncSPGThread(list.subList(i, i+rows))).start();;
			}
		}
	}
	
	class SyncSPGThread implements Runnable
	{
		private Log log = LogFactory.getLog(SyncSPGThread.class);
		
		List<Object> list;
		
		private int currentThreadNum;
		
		public SyncSPGThread(List<Object> list)
		{
			this.list=list;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (list == null || list.size() == 0)return;
			log.info("SyncSPGThread start tx:"+list.get(0).toString());
			currentThreadNum=list.size();
			Integer uid=null;String address=null;
			for (Object object : list) {
				currentThreadNum--;
				log.info("currentThreadNum left:"+currentThreadNum);
				try {
					if(userAssetsService.sycnSPGAssets(object.toString()))
					{
						log.info("userId:"+uid+" ,address:"+address+" ,syncSPG success");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.info("userId:"+uid+" ,TX:"+object+" ,syncBST fail");
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			log.info("SyncSPGThread End");
		}
	}
}
