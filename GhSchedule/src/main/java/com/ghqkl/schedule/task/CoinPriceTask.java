package com.ghqkl.schedule.task;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.math.ec.ECCurve.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ghqkl.schedule.dao.ConfigDao;
import com.ghqkl.schedule.model.CoinPriceBean;
import com.ghqkl.schedule.model.CoinPriceLogBean;
import com.ghqkl.schedule.model.ConfigBean;
import com.ghqkl.schedule.service.CoinPriceLogService;
import com.ghqkl.schedule.service.CoinPriceService;
import com.ghqkl.schedule.util.HttpUtil;
import com.ghqkl.schedule.util.PropertiesFile;
import com.ghqkl.schedule.util.date.DateTimeUtil;
import com.ghqkl.schedule.util.sms.SmsUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class CoinPriceTask implements Runnable{
	private Log log = LogFactory.getLog(CoinPriceTask.class);
	
	private static int failNum=0;
	@Resource
	private ConfigDao configDao;
	@Autowired
	private CoinPriceService coinPriceService;
	
	@Autowired
	private CoinPriceLogService coinPriceLogService;

    public void run(){
    	// TODO Auto-generated method stub
		log.info("GetCoinPriceTask Begin");
		try {
			String resonseStr=HttpUtil.get(PropertiesFile.getValue("coin_price_query_url"));
			JSONObject json = JSONObject.fromObject(resonseStr);
			JSONArray list=json.getJSONObject("data").getJSONArray("menu").getJSONObject(0).getJSONArray("list");
			for (int i = 0; i < list.size(); i++) {
				JSONObject d=list.getJSONObject(i);
/*				if("BST".equals(d.getString("coin")))
				{
					continue;
				}*/
				CoinPriceBean coinPrice=new CoinPriceBean();
				coinPrice.setCoin(d.getString("coin"));
				if(coinPrice.getCoin().toUpperCase().equals("BST")) {
					ConfigBean config=configDao.getConfigByKey("bst_add_price");
					double addPrice=0.2;
					if(config!=null) {
						addPrice=Double.parseDouble(config.getValue());
					}
					log.info("bst原价：" +Double.parseDouble(d.getString("price")));
					coinPrice.setPrice(Double.parseDouble(d.getString("price"))+addPrice);
					log.info("bst修改后：" +coinPrice.getPrice());
				}else {
					coinPrice.setPrice(Double.parseDouble(d.getString("price")));
				}
				coinPrice.setChange(Double.parseDouble(d.getString("change")));
				coinPrice.setIcon(PropertiesFile.getValue("coin_icon_url")+d.getString("img"));
				coinPrice.setFrom(PropertiesFile.getValue("coin_price_query_url"));
				coinPrice.setUpdateTime(DateTimeUtil.getCurrentDateTimeStr());
				
				CoinPriceLogBean logc=new CoinPriceLogBean();
				logc.setCoin(coinPrice.getCoin());
				logc.setPrice(coinPrice.getPrice());
				logc.setChange(coinPrice.getChange());
				logc.setCreateTime(coinPrice.getUpdateTime());
				logc.setFrom(coinPrice.getFrom());
				logc.setCreateUser(-1);
				List<CoinPriceBean> oldCoin=coinPriceService.queryList(coinPrice);
				if(oldCoin!=null&&oldCoin.size()>0)
				{
					if(oldCoin.get(0).getPrice().equals(coinPrice.getPrice()))
					{
						continue;
					}
					coinPrice.setId(oldCoin.get(0).getId());
					coinPriceService.update(coinPrice);
					coinPriceLogService.insert(logc);
					log.info(coinPrice.getCoin()+" price update："+coinPrice.getPrice());
				}
				else
				{
					coinPriceService.insert(coinPrice);
					coinPriceLogService.insert(logc);
				}
				
			}
			resonseStr=HttpUtil.get(PropertiesFile.getValue("coin_hash_url"));
			JSONObject spgjson = JSONObject.fromObject(resonseStr).getJSONObject("info");
			CoinPriceBean coinPrice=new CoinPriceBean();
			coinPrice.setCoin("SPG");
			coinPrice.setPrice(spgjson.getDouble("new_price_huilv"));
			coinPrice.setChange(spgjson.getDouble("change"));
			coinPrice.setUpdateTime(DateTimeUtil.getCurrentDateTimeStr());
			if(coinPrice.getPrice()<=0) {
				log.info("暂不同步SPG币价");
				return ;
			}
			CoinPriceLogBean logc=new CoinPriceLogBean();
			logc.setCoin(coinPrice.getCoin());
			logc.setPrice(coinPrice.getPrice());
			logc.setChange(coinPrice.getChange());
			logc.setCreateTime(coinPrice.getUpdateTime());
			logc.setFrom(coinPrice.getFrom());
			logc.setCreateUser(-1);
			List<CoinPriceBean> oldCoin=coinPriceService.queryList(coinPrice);
			if(oldCoin!=null&&oldCoin.size()>0){
				if(!oldCoin.get(0).getPrice().equals(coinPrice.getPrice()))
				{
					coinPrice.setId(oldCoin.get(0).getId());
					coinPriceService.update(coinPrice);
					coinPriceLogService.insert(logc);
					log.info(coinPrice.getCoin()+" price update："+coinPrice.getPrice());
				}
			}
			else
			{
				coinPriceService.insert(coinPrice);
				coinPriceLogService.insert(logc);
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//发送警告
			failNum++;
			if(failNum%10==0)
			{
//				SmsUtil.sendSms(PropertiesFile.getValue("admin_phone"), "爬取币价异常，请登录服务器查看！");
			}
		}
		log.info("GetCoinPriceTask End");
    	
    	/*new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				log.info("GetBSTPriceTask Begin");
				try {
					String resonseStr = HttpUtil.get(PropertiesFile.getValue("coin_hash_url"));
					JSONObject json = JSONObject.fromObject(resonseStr);
					JSONObject info = json.getJSONObject("info");
					Double newPrice = info.getDouble("new_price_huilv");
					String change = info.getString("change");

					CoinPriceBean coinPrice = new CoinPriceBean();
					coinPrice.setCoin("BST");
					coinPrice.setPrice(newPrice);
					coinPrice.setChange(Double.parseDouble(change));
					coinPrice.setFrom(PropertiesFile.getValue("coin_hash_url"));
					coinPrice.setUpdateTime(DateTimeUtil.getCurrentDateTimeStr());

					CoinPriceLogBean logc = new CoinPriceLogBean();
					logc.setCoin(coinPrice.getCoin());
					logc.setPrice(coinPrice.getPrice());
					logc.setChange(coinPrice.getChange());
					logc.setCreateTime(coinPrice.getUpdateTime());
					logc.setFrom(coinPrice.getFrom());
					logc.setCreateUser(-1);
					List<CoinPriceBean> oldCoin = coinPriceService.queryList(coinPrice);
					if (oldCoin != null && oldCoin.size() > 0) {
						if (oldCoin.get(0).getPrice().equals(coinPrice.getPrice())) {
							return;
						}
						coinPrice.setId(oldCoin.get(0).getId());
						coinPriceService.update(coinPrice);
						coinPriceLogService.insert(logc);
						log.info(coinPrice.getCoin() + " price update：" + coinPrice.getPrice());
					} else {
						coinPriceService.insert(coinPrice);
						coinPriceLogService.insert(logc);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//发送警告
					SmsUtil.sendSms(PropertiesFile.getValue("admin_phone"), "爬取BST币价异常，请登录服务器查看！");
				}
				log.info("GetBSTPriceTask End");
			}
    		
    	}).start();*/
    	
    }
}
