package com.ghqkl.schedule.task;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ghqkl.schedule.model.UserAssetsBean;
import com.ghqkl.schedule.service.UserAssetsService;
import com.ghqkl.schedule.util.StringUtils;

@Component
public class SyncEthTask implements Runnable {
	private final static int rows=500;
	
	@Autowired
	private UserAssetsService userAssetsService;

	private Log log = LogFactory.getLog(SyncEthTask.class);

	public void run() {
		log.info("SyncEthTask Begin");
		UserAssetsBean userAsset = new UserAssetsBean();
		userAsset.setCoin("ETH");
		int count = userAssetsService.count(userAsset);
		int page=count/rows;
		for(int i=1;i<page+2;i++)
		{
			List<UserAssetsBean> userAssets=userAssetsService.pageList(userAsset, i, rows).getList();
			new Thread(new SyncEthThread(userAssets)).start();
		}
	}
	
	class SyncEthThread implements Runnable
	{
		private Log log = LogFactory.getLog(SyncEthThread.class);
		
		List<UserAssetsBean> list;
		
		private int currentThreadNum;
		
		public SyncEthThread(List<UserAssetsBean> list)
		{
			this.list=list;
		}
		
		@Override
		public void run() {
			if (list == null || list.size() == 0)return;
			log.info("SyncEthThread start userId:"+list.get(0).getUserId());
			currentThreadNum=list.size();
			Integer uid = null;String address=null;
			for (UserAssetsBean userAssetsBean : list) {
				uid = userAssetsBean.getUserId();
				address = userAssetsBean.getAddress();
				currentThreadNum--;
				log.info("currentThreadNum left:"+currentThreadNum);
				if(StringUtils.isEmpty(address))continue;
				try {
					if(userAssetsService.sycnETHAssets(userAssetsBean))
					{
						log.info("userId:"+uid+" ,address:"+address+" ,syncETH success");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.info("userId:" + uid + " ,address:"+address+" ,syncETH fail!");
					continue;
				}
			}
			log.info("SyncEthTask End");
		}
		
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
	}
}
