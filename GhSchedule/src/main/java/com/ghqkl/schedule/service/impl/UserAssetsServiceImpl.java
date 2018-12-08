package com.ghqkl.schedule.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghqkl.schedule.dao.UserAssetsDao;
import com.ghqkl.schedule.model.AssetsLogBean;
import com.ghqkl.schedule.model.ConfigBean;
import com.ghqkl.schedule.model.RecordRechargeBean;
import com.ghqkl.schedule.model.RecordReleaseBean;
import com.ghqkl.schedule.model.UserAssetsBean;
import com.ghqkl.schedule.model.UsersBean;
import com.ghqkl.schedule.service.AssetsLogService;
import com.ghqkl.schedule.service.ConfigService;
import com.ghqkl.schedule.service.RecordRechargeService;
import com.ghqkl.schedule.service.RecordReleaseService;
import com.ghqkl.schedule.service.UserAssetsService;
import com.ghqkl.schedule.service.UsersService;
import com.ghqkl.schedule.util.BSTUtil;
import com.ghqkl.schedule.util.BTCUtil;
import com.ghqkl.schedule.util.HttpUtil;
import com.ghqkl.schedule.util.LTCUtil;
import com.ghqkl.schedule.util.PropertiesFile;
import com.ghqkl.schedule.util.SPGUtil;
import com.ghqkl.schedule.util.StringUtils;
import com.ghqkl.schedule.util.date.DateTimeUtil;
import com.ghqkl.schedule.util.security.Base64;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class UserAssetsServiceImpl implements UserAssetsService {
	private static Logger log = LoggerFactory.getLogger(UserAssetsServiceImpl.class);

	@Autowired
	private RecordRechargeService recordRechargeService;
	@Autowired
	private AssetsLogService assetsLogService;
	@Autowired
	private ConfigService configService;
	@Autowired
	private RecordReleaseService recordReleaseService;

	@Autowired
	private UserAssetsDao dao;

	@Override
	public int insert(UserAssetsBean t) {
		// TODO Auto-generated method stub
		return dao.insert(t);
	}

	@Override
	public int delete(UserAssetsBean t) {
		// TODO Auto-generated method stub
		return dao.delete(t);
	}

	@Override
	public int update(UserAssetsBean t) {
		// TODO Auto-generated method stub
		return dao.update(t);
	}

	@Override
	public UserAssetsBean getById(Integer Id) {
		// TODO Auto-generated method stub
		return dao.getById(Id);
	}

	@Override
	public List<UserAssetsBean> queryList(UserAssetsBean t) {
		// TODO Auto-generated method stub
		return dao.queryList(t);
	}

	@Override
	public int count(UserAssetsBean t) {
		// TODO Auto-generated method stub
		return dao.count(t);
	}

	@Override
	public PageInfo<UserAssetsBean> pageList(UserAssetsBean t, int pageNum, int pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<UserAssetsBean> list = dao.queryList(t);
		PageInfo<UserAssetsBean> pageInfo = new PageInfo<UserAssetsBean>(list);
		return pageInfo;
	}

	/**
	 * 用户释放普通和原始
	 * 
	 * @param userAssets   用户
	 * @param relese_1     合伙人释放比例
	 * @param userAmount   持币量
	 * @param relese_2     股东商释放比例
	 * @param releaseRatio 超过持币量的释放比例
	 * @return
	 */

	@Transactional
	public int release(UserAssetsBean userAssets, double relese_1, double userAmount, double relese_2,
			double releaseRatio) {
		if(userAssets.getNum()+userAssets.getOriginal()<=0){
			log.info(userAssets.getUserId() + "的资产已经释放完啦！");
			return 0;
		}
		UserAssetsBean newUa=dao.selectByUserAssetsId(userAssets.getId());
		newUa.setLevel(userAssets.getLevel());
		userAssets=newUa;
		if (userAssets.getTotalReleased() == null) {
			userAssets.setTotalReleased(0.0);
		}
		double totalAmount = (double) (userAssets.getNum() + userAssets.getOriginal() + userAssets.getTotalReleased());// 总资产
		double relese = 0.0;
		double originalRelese = 0.0;
		double addRelesed = 0.0;
		
		if (totalAmount >= userAmount) {// 总资产大于持币量
			if(userAssets.getNum()>0) {
				relese = (userAssets.getNum()+
						(userAssets.getTotalReleased()-userAssets.getOriginalReleased()))
						* releaseRatio;// 普通释放量
			}
			if(userAssets.getOriginal()>0) {
				
				originalRelese = (userAssets.getOriginal()+userAssets.getOriginalReleased()) * releaseRatio;// 原始释放量
			}
			if(relese>userAssets.getNum()) {
				relese=userAssets.getNum();
			}
			if(originalRelese>userAssets.getOriginal()) {
				originalRelese=userAssets.getOriginal();
			}
			addRelesed = relese + originalRelese;
		} else if ((int)userAssets.getLevel() == 1) {// 合伙人
			if(userAssets.getNum()>0) {
				relese = (userAssets.getNum()+
						(userAssets.getTotalReleased()-userAssets.getOriginalReleased())) * relese_1;// 普通释放量
			}
			if(userAssets.getOriginal()>0) {
				
				originalRelese = (userAssets.getOriginal()+userAssets.getOriginalReleased()) * relese_1;// 原始释放量
			}
			if(relese>userAssets.getNum()) {
				relese=userAssets.getNum();
			}
			if(originalRelese>userAssets.getOriginal()) {
				originalRelese=userAssets.getOriginal();
			}
			addRelesed = relese + originalRelese;
		} else if ((int)userAssets.getLevel() == 2) {// 股东商
			if(userAssets.getNum()>0) {
				relese = (userAssets.getNum()+
						(userAssets.getTotalReleased()-userAssets.getOriginalReleased())) * relese_2;// 普通释放量
			}
			if(userAssets.getOriginal()>0) {
				originalRelese = (userAssets.getOriginal()+userAssets.getOriginalReleased()) * relese_2;// 原始释放量
			}
			if(relese>userAssets.getNum()) {
				relese=userAssets.getNum();
			}
			if(originalRelese>userAssets.getOriginal()) {
				originalRelese=userAssets.getOriginal();
			}
			addRelesed = relese + originalRelese;
		}
		if(addRelesed<=0) {
			log.info(userAssets.getUserId() + "的资产已经释放完啦！");
			return 0;
		}
		// 当释放的资金加总释放量大于剩余资产时变更释放量
		if ((addRelesed + ((double) (userAssets.getTotalReleased()))) > totalAmount
				&& totalAmount > ((double) (userAssets.getTotalReleased()))) {
			addRelesed = totalAmount - ((double) (userAssets.getTotalReleased()));
		} else if( (addRelesed + ((double) (userAssets.getTotalReleased()))) >= totalAmount
				&& totalAmount <= ((double) (userAssets.getTotalReleased()))) {
			log.info(userAssets.getUserId() + "的资产已经释放完啦！");
			return 0;
		}
		userAssets.setReleased(userAssets.getReleased() + addRelesed);// 释放的币的余额增加本次释放的金额
		userAssets.setOriginalReleased(userAssets.getOriginalReleased() + originalRelese);// 保存总的原始释放数量
		userAssets.setTotalReleased(userAssets.getTotalReleased() + addRelesed);// 增加总释放量
		userAssets.setOriginal(userAssets.getOriginal() - originalRelese);// 保存释放后的原始余额
		userAssets.setNum(userAssets.getNum() - relese);// 保存释放后的余额
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		userAssets.setUpdateTime(sdf.format(date));
		dao.updateUserAsse(userAssets);
		RecordReleaseBean recorRelease = new RecordReleaseBean();
		recorRelease.setSpgOriginalReleased(originalRelese);
		recorRelease.setSpgReleased(addRelesed);
		recorRelease.setTimestarmp(sdf.format(date));
		recorRelease.setUserId(userAssets.getUserId());
		recordReleaseService.insert(recorRelease);
		AssetsLogBean assetsLog=new AssetsLogBean();
		assetsLog.setChange(addRelesed);
		assetsLog.setCoin("SPG");
		assetsLog.setTime(sdf.format(date));
		assetsLog.setType(10);
		assetsLog.setUserId(userAssets.getUserId());
		assetsLogService.insert(assetsLog);
		log.info(userAssets.getUserId() + "释放spg:" + addRelesed + "枚");
		return 1;
	}

	/***
	 * 释放普通和原始
	 */
	public void release() {
		// 持币量配置
		ConfigBean userAmountConf = configService.getConfigByKey("user_amount_of_money_holding");
		ConfigBean releaseRatioConf = configService.getConfigByKey("user_release_ratio");
		ConfigBean release1Conf = configService.getConfigByKey("release_1");
		ConfigBean release2Conf = configService.getConfigByKey("release_2");
		if (userAmountConf == null || releaseRatioConf == null || release1Conf == null || release2Conf == null)
			return;
		//
		Double userAmount = Double.parseDouble(userAmountConf.getValue());
		// 大于这个持币量的释放比例
		Double releaseRatio = Double.parseDouble(releaseRatioConf.getValue()) / 100;
		// 合伙人释放比例
		Double release1 = Double.parseDouble(release1Conf.getValue()) / 100;
		// 股东商释放比例
		Double release2 = Double.parseDouble(release2Conf.getValue()) / 100;
		UserAssetsBean userAssets = new UserAssetsBean();
		userAssets.setCoin("SPG");
		List<UserAssetsBean> users = dao.queryUsersList(userAssets);
		for (UserAssetsBean userAssetsBean : users) {
			try {
				this.release(userAssetsBean, release1, userAmount, release2, releaseRatio);
//				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
				log.info(userAssetsBean.getUserId()+"释放失败！");
			}
			
		}
//		//修改合伙人的释放资产
//		UserAssetsBean userAssets = new UserAssetsBean();
//		userAssets.setLevel(1);
//		userAssets.setNum(userAmount);
//		List<UserAssetsBean> users=dao.queryNeedReleaseList1(userAssets);
//		userAssets.setPercentage(release1);
//		for (UserAssetsBean usersBean : users) {
//			if(usersBean.getReleased()>=usersBean.getNum()+usersBean.getOriginal())continue;
//			userAssets.setId(usersBean.getId());
//			dao.release(userAssets);
//			//insert释放记录
//			recordReleaseService.insert(new RecordReleaseBean(null,usersBean.getUserId(),usersBean.getNum()==0?0:usersBean.getNum()*release1,usersBean.getOriginal()==0?0:usersBean.getOriginal()*release1,DateTimeUtil.getCurrentDateTimeStr()));
//		}
//		
//		userAssets.setLevel(2);
//		users=dao.queryNeedReleaseList1(userAssets);
//		userAssets.setPercentage(release2);
//		for (UserAssetsBean usersBean : users) {
//			if(usersBean.getReleased()>=usersBean.getNum()+usersBean.getOriginal())continue;
//			userAssets.setUserId(usersBean.getUserId());
//			dao.release(userAssets);
//			//insert释放记录
//			recordReleaseService.insert(new RecordReleaseBean(null,usersBean.getUserId(),usersBean.getNum()==0?0:usersBean.getNum()*release2,usersBean.getOriginal()==0?0:usersBean.getOriginal()*release2,DateTimeUtil.getCurrentDateTimeStr()));
//		}
//		
//		userAssets.setLevel(null);
//		users=dao.queryNeedReleaseList2(userAssets);
//		userAssets.setPercentage(releaseRatio);
//		for (UserAssetsBean usersBean : users) {
//			userAssets.setUserId(usersBean.getUserId());
//			dao.release(userAssets);
//			//insert释放记录
//			recordReleaseService.insert(new RecordReleaseBean(null,usersBean.getUserId(),usersBean.getNum()==0?0:usersBean.getNum()*releaseRatio,usersBean.getOriginal()==0?0:usersBean.getOriginal()*releaseRatio,DateTimeUtil.getCurrentDateTimeStr()));
//		}
	}

	@Transactional
	public boolean sycnBSTAssets(String txd) {
		RecordRechargeBean recordRechargeBean = new RecordRechargeBean();
		recordRechargeBean.setTx(txd);
		List<RecordRechargeBean> rrs = recordRechargeService.queryList(recordRechargeBean);
		if (rrs != null && rrs.size() > 0) {
			return false;
		}
		JSONObject data = null;
		try {
			data = BSTUtil.getInstance().getPayByTxid(txd.toString());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		JSONArray details = data.getJSONArray("details");
		if (details == null || details.size() == 0) {
			return false;
		}
		JSONObject record = details.getJSONObject(0);
		String address = record.getString("address");
		UserAssetsBean userAssets = new UserAssetsBean();
		userAssets.setAddress(address);
		List<UserAssetsBean> localAssets = this.queryList(userAssets);
		if (localAssets == null || localAssets.size() == 0) {
			return false;
		}
		Integer uid = localAssets.get(0).getUserId();
		recordRechargeBean.setCoin("BST");
		recordRechargeBean.setTime(DateTimeUtil.getCurrentDateTimeStr());
		recordRechargeBean.setValue(record.getDouble("amount"));
		recordRechargeBean.setUserId(uid);
		recordRechargeBean.setAddress(address);
		recordRechargeService.insert(recordRechargeBean);
		AssetsLogBean assetsLogBean = new AssetsLogBean();
		assetsLogBean.setCoin("BST");
		assetsLogBean.setUserId(uid);
		assetsLogBean.setChange(recordRechargeBean.getValue());
		assetsLogBean.setType(1);
		assetsLogBean.setTime(DateTimeUtil.getCurrentDateTimeStr());
		assetsLogService.insert(assetsLogBean);
		UserAssetsBean userUpdateAssets = new UserAssetsBean();
		userUpdateAssets.setAddress(address);
		userUpdateAssets.setNum(recordRechargeBean.getValue());
		userUpdateAssets.setUpdateTime(DateTimeUtil.getCurrentDateTimeStr());
		this.update(userUpdateAssets);
		return true;
	}

	@Transactional
	public boolean sycnSPGAssets(String txd) {
		RecordRechargeBean recordRechargeBean = new RecordRechargeBean();
		recordRechargeBean.setTx(txd);
		List<RecordRechargeBean> rrs = recordRechargeService.queryList(recordRechargeBean);
		if (rrs != null && rrs.size() > 0) {
			return false;
		}
		JSONObject data = null;
		try {
			data = SPGUtil.getInstance().getPayByTxid(txd.toString());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		JSONArray details = data.getJSONArray("details");
		if (details == null || details.size() == 0) {
			return false;
		}
		JSONObject record = details.getJSONObject(0);
		if("send".equals(record.getString("category"))) {
			return false;
		}
		if(record.getDouble("amount")<0) {
			return false;
		}
		String address = record.getString("address");
		UserAssetsBean userAssets = new UserAssetsBean();
		userAssets.setAddress(address);
		List<UserAssetsBean> localAssets = this.queryList(userAssets);
		if (localAssets == null || localAssets.size() == 0) {

			return false;
		}
		Integer uid = localAssets.get(0).getUserId();
		recordRechargeBean.setCoin("SPG");
		recordRechargeBean.setTime(DateTimeUtil.getCurrentDateTimeStr());
		recordRechargeBean.setValue(record.getDouble("amount"));
		recordRechargeBean.setUserId(uid);
		recordRechargeBean.setAddress(address);
		AssetsLogBean assetsLogBean = new AssetsLogBean();
		assetsLogBean.setCoin("SPG");
		assetsLogBean.setUserId(uid);
		assetsLogBean.setChange(recordRechargeBean.getValue());
		assetsLogBean.setType(1);
		assetsLogBean.setTime(DateTimeUtil.getCurrentDateTimeStr());
		int r = assetsLogService.insert(assetsLogBean);
		if (r == 0) {
			log.info("地址没有用户使用:{}", address);
			return false;
		}
		UserAssetsBean userUpdateAssets = localAssets.get(0);
		userUpdateAssets.setAddress(address);
		userUpdateAssets.setReleased(userUpdateAssets.getReleased()+recordRechargeBean.getValue());
		userUpdateAssets.setUpdateTime(DateTimeUtil.getCurrentDateTimeStr());
		dao.updateUserAsse(userUpdateAssets);

		recordRechargeService.insert(recordRechargeBean);

		return true;
	}

	@Transactional
	public boolean sycnBTSystemAssets(String txd, String coin) {
		RecordRechargeBean recordRechargeBean = new RecordRechargeBean();
		recordRechargeBean.setTx(txd);
		List<RecordRechargeBean> rrs = recordRechargeService.queryList(recordRechargeBean);
		if (rrs != null && rrs.size() > 0) {
			return false;
		}
		JSONObject data = null;
		try {
			if ("LTC".equals(coin)) {

				data = LTCUtil.getInstance().getPayByTxid(txd.toString());
			} else if ("BTC".equals(coin)) {
				data = BTCUtil.getInstance().getPayByTxid(txd.toString());
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		JSONArray details = data.getJSONArray("details");
		if (details == null || details.size() == 0) {
			return false;
		}
		JSONObject record = details.getJSONObject(0);
		String address = record.getString("address");
		UserAssetsBean userAssets = new UserAssetsBean();
		userAssets.setAddress(address);
		List<UserAssetsBean> localAssets = this.queryList(userAssets);
		if (localAssets == null || localAssets.size() == 0) {

			return false;
		}
		Integer uid = localAssets.get(0).getUserId();
		recordRechargeBean.setCoin(coin);
		recordRechargeBean.setTime(DateTimeUtil.getCurrentDateTimeStr());
		recordRechargeBean.setValue(record.getDouble("amount"));
		recordRechargeBean.setUserId(uid);
		recordRechargeBean.setAddress(address);
		AssetsLogBean assetsLogBean = new AssetsLogBean();
		assetsLogBean.setCoin(coin);
		assetsLogBean.setUserId(uid);
		assetsLogBean.setChange(recordRechargeBean.getValue());
		assetsLogBean.setType(1);
		assetsLogBean.setTime(DateTimeUtil.getCurrentDateTimeStr());
		int r = assetsLogService.insert(assetsLogBean);
		if (r == 0) {
			log.info("地址没有用户使用:{}", address);
			return false;
		}

		recordRechargeService.insert(recordRechargeBean);

		UserAssetsBean userUpdateAssets = new UserAssetsBean();
		userUpdateAssets.setAddress(address);
		userUpdateAssets.setNum(recordRechargeBean.getValue());
		userUpdateAssets.setUpdateTime(DateTimeUtil.getCurrentDateTimeStr());
		this.update(userUpdateAssets);
		return true;
	}

	@Transactional
	public boolean sycnETHAssets(UserAssetsBean userAssetsBean) {
		HttpUtil httpUtil = new HttpUtil();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("token", Base64.encode(("47.75.209.115/" + getUTCTimeStr()).getBytes()));
		params.put("address", StringUtils.replaceBlank(userAssetsBean.getAddress()));
		String resonseStr = null;
		try {
			resonseStr = httpUtil.Post(PropertiesFile.getValue("eth_url") + "/getTransactionLog", params);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		if (resonseStr == null || "".equals(resonseStr))
			return false;
		JSONObject json = JSONObject.fromObject(resonseStr);
		if (json.getInt("status") != 200) {
			return false;
		}
		JSONArray list = json.getJSONArray("transactionLog");
		if (list == null || list.size() == 0)
			return false;
		RecordRechargeBean recordRecharge;
		for (int i = 0; i < list.size(); i++) {
			recordRecharge = new RecordRechargeBean();
			JSONObject data = list.getJSONObject(i);
			if (!data.getString("to").equals(userAssetsBean.getAddress()))
				continue;
			recordRecharge.setTx(data.getString("hash"));
			int count = recordRechargeService.count(recordRecharge);
			if (count > 0)
				continue;
			recordRecharge.setAddress(userAssetsBean.getAddress());
			recordRecharge.setCoin("ETH");
			recordRecharge.setTime(DateTimeUtil.getCurrentDateTimeStr());
			double value = 0.0;
			if (data.getString("value").contains("0x")) {
				BigInteger bigInteger = new BigInteger(data.getString("value").substring(2), 16);
				value = Double.parseDouble(bigInteger.toString()) / 1000000000000000000L;
			} else {
				BigInteger bigInteger = new BigInteger(data.getString("value"), 16);
				value = Double.parseDouble(bigInteger.toString()) / 1000000000000000000L;
			}
			recordRecharge.setValue(value);
			recordRecharge.setUserId(userAssetsBean.getUserId());
			recordRechargeService.insert(recordRecharge);

			AssetsLogBean assetsLogBean = new AssetsLogBean();
			assetsLogBean.setCoin("ETH");
			assetsLogBean.setUserId(userAssetsBean.getUserId());
			assetsLogBean.setChange(value);
			assetsLogBean.setType(1);
			assetsLogBean.setTime(DateTimeUtil.getCurrentDateTimeStr());
			assetsLogService.insert(assetsLogBean);

			UserAssetsBean userUpdateAssets = new UserAssetsBean();
			userUpdateAssets.setAddress(userAssetsBean.getAddress());
			userUpdateAssets.setNum(recordRecharge.getValue());
			userUpdateAssets.setUpdateTime(DateTimeUtil.getCurrentDateTimeStr());
			this.update(userUpdateAssets);
		}
		return true;
	}

	public static Long getUTCTimeStr() {
		// 1、取得本地时间：
		Calendar cal = Calendar.getInstance();
		// 2、取得时间偏移量：
		int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
		// 3、取得夏令时差：
		int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
		// 4、从本地时间里扣除这些差量，即可以取得UTC时间：
		cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		return cal.getTimeInMillis();
	}

	@Override
	public void releaseBST() {
				// 持币量配置
				ConfigBean releaseConf = configService.getConfigByKey("release_bst");
				if (releaseConf == null )
					return;
				// 释放比例
				Double release1 = Double.parseDouble(releaseConf.getValue()) / 100;
				UserAssetsBean userAssets = new UserAssetsBean();
				userAssets.setCoin("BST");
				List<UserAssetsBean> users = dao.queryUsersList(userAssets);
				for (UserAssetsBean userAssetsBean : users) {
					try {
						this.releaseBst(userAssetsBean, release1);
//						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
						log.info(userAssetsBean.getUserId()+"释放失败！");
					}
					
				}
	}

	private void releaseBst(UserAssetsBean userAssetsBean, double release) {
		if(userAssetsBean.getOriginal()==null||userAssetsBean.getOriginal()<=0) {
			return ;
		}
		userAssetsBean=dao.selectByUserAssetsId(userAssetsBean.getId());
		if(userAssetsBean.getOriginal()==null) {
			userAssetsBean.setOriginal(0.0);
		}
		if(userAssetsBean.getTotalReleased()==null) {
			userAssetsBean.setTotalReleased(0.0);
		}
		double originalRelese=0.0;
			if((double)userAssetsBean.getOriginal()>0) {
				
				originalRelese = (userAssetsBean.getOriginal()+userAssetsBean.getOriginalReleased()) * release;// 原始释放量
			}
			if(originalRelese>(double)userAssetsBean.getOriginal()) {
				originalRelese=userAssetsBean.getOriginal();
			}
		if(originalRelese<=0) {
			log.info(userAssetsBean.getUserId() + "的资产已经释放完啦！");
			return ;
		}
		
		userAssetsBean.setOriginalReleased(userAssetsBean.getOriginalReleased() + originalRelese);// 保存总的原始释放数量
		userAssetsBean.setTotalReleased(userAssetsBean.getTotalReleased() + originalRelese);// 增加总释放量
		userAssetsBean.setOriginal(userAssetsBean.getOriginal() - originalRelese);// 保存释放后的原始余额
		userAssetsBean.setNum(userAssetsBean.getNum() +originalRelese);// 保存释放后的余额
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		userAssetsBean.setUpdateTime(sdf.format(date));
		dao.updateUserAsse(userAssetsBean);
		RecordReleaseBean recorRelease = new RecordReleaseBean();
		recorRelease.setSpgOriginalReleased(originalRelese);
		recorRelease.setSpgReleased(originalRelese);
		recorRelease.setTimestarmp(sdf.format(date));
		recorRelease.setUserId(userAssetsBean.getUserId());
		recorRelease.setCoin("BST");
		recordReleaseService.insert(recorRelease);
		AssetsLogBean assetsLog=new AssetsLogBean();
		assetsLog.setChange(originalRelese);
		assetsLog.setCoin("BST");
		assetsLog.setTime(sdf.format(date));
		assetsLog.setType(10);
		assetsLog.setUserId(userAssetsBean.getUserId());
		assetsLogService.insert(assetsLog);
		log.info(userAssetsBean.getUserId() + "释放spg:" + originalRelese + "枚");
	}

}
