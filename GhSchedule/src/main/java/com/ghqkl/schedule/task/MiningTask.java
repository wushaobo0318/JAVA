package com.ghqkl.schedule.task;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ghqkl.schedule.model.ConfigBean;
import com.ghqkl.schedule.model.MiningConfigBean;
import com.ghqkl.schedule.model.MiningLogBean;
import com.ghqkl.schedule.model.MiningServerBean;
import com.ghqkl.schedule.model.UsersBean;
import com.ghqkl.schedule.service.ConfigService;
import com.ghqkl.schedule.service.MiningConfigService;
import com.ghqkl.schedule.service.MiningLogService;
import com.ghqkl.schedule.service.MiningServerService;
import com.ghqkl.schedule.service.UsersService;
import com.ghqkl.schedule.util.date.DateUtil;

@Component
public class MiningTask implements Runnable{
	
	private Log log = LogFactory.getLog(MiningTask.class);
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private MiningServerService miningServerService;
	
	@Autowired
	private MiningConfigService miningConfigService;
	
	@Autowired
	private MiningLogService miningLogService;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		log.info("MiningTask Begin");
		try {
			ConfigBean config=new ConfigBean();
			config.setKey("is_mining");
			List<ConfigBean> configs=configService.queryList(config);
			if(configs==null||configs.size()==0||"0".equals(configs.get(0).getValue()))
			{
				return;
			}
			List<MiningServerBean> servers=miningServerService.queryList(null);
			if(servers==null||servers.size()==0)
			{
				return;
			}
			for (MiningServerBean miningServerBean : servers) {
				new Thread(new miningThread(miningServerBean)).start();;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class miningThread implements Runnable
	{
		private Log log = LogFactory.getLog(miningThread.class);
		
		MiningServerBean server;
		
		public miningThread(MiningServerBean server)
		{
			this.server=server;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			log.info("server:"+server.getServerZh()+"begin miningThread!");
			List<MiningConfigBean> miningConfigs=miningConfigService.queryList(null);
			if(miningConfigs==null||miningConfigs.size()==0)return;
			for (MiningConfigBean miningConfigBean : miningConfigs) {
				List<UsersBean> users=usersService.queryMiningList(miningConfigBean.getSpoceNum(), miningConfigBean.getLastNum());
				if(users==null||users.size()==0)continue;
				MiningLogBean miningLog=null;
				for (UsersBean user : users) {
					//每次70%几率可以挖到
					int flag=(int)(Math.random()*10);
					if(flag<5)
					{
						continue;
					}
					//查看用户今天一共挖了多少矿,超过限制不能再挖
					MiningLogBean toDayMining=new MiningLogBean();
					toDayMining.setUserId(user.getId());
					toDayMining.setMiningTime(DateUtil.getCurrentDateStr());
					Double miningNum=Math.random()/10+miningConfigBean.getNumEnd()/12/4;
					Double toDayMiningSum=miningLogService.queryTodayMiningSumByUser(toDayMining);
					if(toDayMiningSum!=null&&(toDayMiningSum+miningNum)>miningConfigBean.getNumEnd())
					{
						continue;
					}
					miningLog = new MiningLogBean();
					miningLog.setUserId(user.getId());
					miningLog.setMiningServerId(this.server.getServerId());
					miningLog.setMiningNum(miningNum);
					miningLog.setMiningTime(System.currentTimeMillis()/1000+"");
					miningLogService.insert(miningLog);
					
					log.info("server:"+server.getServerZh()+" userId:"+user.getId()+" get "+miningNum+"spg!");
				}
			}
		}
		
	}
	public static void main(String[] args) {
		System.out.println(Math.random()/10+0.9/12/4);
	}

}
