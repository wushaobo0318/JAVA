package com.ghqkl.schedule.task;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ghqkl.schedule.service.RedisService;
import com.ghqkl.schedule.util.AESUtil;
import com.ghqkl.schedule.util.BSTUtil;
import com.ghqkl.schedule.util.BTCUtil;
import com.ghqkl.schedule.util.HttpUtil;
import com.ghqkl.schedule.util.LTCUtil;
import com.ghqkl.schedule.util.SPGUtil;
import com.ghqkl.schedule.util.security.Base64;
import com.mysql.jdbc.StringUtils;

import net.sf.json.JSONObject;

@Component
public class CoinAddressTask implements Runnable {
	private Log log = LogFactory.getLog(CoinAddressTask.class);

	@Autowired
	private RedisService redisService;

	public void run() {
		log.info("CoinAddressTask Begin");
		try {
			new Thread(new BstAddrThread()).start();
			new Thread(new EthAddrThread()).start();
			new Thread(new SPGAddrThread()).start();
			new Thread(new LtcAddrThread()).start();
			new Thread(new BtcAddrThread()).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// 发送警告
		}
		log.info("CoinAddressTask End");
	}

	class BstAddrThread implements Runnable {
		private Log log = LogFactory.getLog(BstAddrThread.class);

		@Override
		public void run() {
			// TODO Auto-generated method stub
			log.info("BstAddrThread Begin");
			try {
				long size = redisService.listSize("bstAddr");
				log.info("bstAddr have:" + size);
				if (size < 100) {
					if (!StringUtils.isNullOrEmpty(BSTUtil.WALLET_PASSWORD)) {
						
						try {

							BSTUtil.getInstance().walletpassphase(BSTUtil.WALLET_PASSWORD,10);
						} catch (Exception e) {
							log.info("BST解锁失败");
						}
					}
				}
				while (size < 100) {
					String address = BSTUtil.getInstance().getNewaddress("");
					if (StringUtils.isNullOrEmpty(address))
						continue;
					redisService.listPush("bstAddr", address);
					size = redisService.listSize("bstAddr");
					log.info("bstAddr have:" + size);
					Thread.sleep(500);// 慢点，服务器承受不住
				}
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			log.info("BstAddrThread End");
		}

	}

	class BtcAddrThread implements Runnable {
		private Log log = LogFactory.getLog(BtcAddrThread.class);

		@Override
		public void run() {
			// TODO Auto-generated method stub
			log.info("BtcAddrThread Begin");
			try {
				long size = redisService.listSize("btcAddr");
				log.info("btcAddr have:" + size);
				if (size < 100) {

					if (!StringUtils.isNullOrEmpty(BTCUtil.BTC_WALLET_PASSWORD)) {
						try {

							BTCUtil.getInstance()
									.walletpassphase(BTCUtil.BTC_WALLET_PASSWORD, 10);
						} catch (Exception e) {
							log.info("BTC解锁失败");
						}
					}
				}
				while (size < 100) {
					String address = BTCUtil.getInstance().getNewaddress("");
					if (StringUtils.isNullOrEmpty(address))
						continue;
					redisService.listPush("btcAddr", address);
					size = redisService.listSize("btcAddr");
					log.info("btcAddr have:" + size);
					Thread.sleep(50);// 慢点，服务器承受不住
				}
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			log.info("BtcAddrThread End");
		}

	}

	class LtcAddrThread implements Runnable {
		private Log log = LogFactory.getLog(LtcAddrThread.class);

		@Override
		public void run() {
			// TODO Auto-generated method stub
			log.info("LtcAddrThread Begin");
			try {
				long size = redisService.listSize("ltcAddr");
				log.info("ltcAddr have:" + size);
				if (size < 100) {
					if (!StringUtils.isNullOrEmpty(LTCUtil.LTC_WALLET_PASSWORD)) {
						
						try {

							LTCUtil.getInstance().walletpassphase(LTCUtil.LTC_WALLET_PASSWORD,
									10);
						} catch (Exception e) {
							log.info("LTC解锁失败");
						}
					}
				}
				while (size < 100) {
					String address = LTCUtil.getInstance().getNewaddress("");
					if (StringUtils.isNullOrEmpty(address))
						continue;
					redisService.listPush("ltcAddr", address);
					size = redisService.listSize("ltcAddr");
					log.info("ltcAddr have:" + size);
					Thread.sleep(50);// 慢点，服务器承受不住
				}
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			log.info("LtcAddrThread End");
		}

	}

	class SPGAddrThread implements Runnable {
		private Log log = LogFactory.getLog(SPGAddrThread.class);

		@Override
		public void run() {
			// TODO Auto-generated method stub
			log.info("SPGAddrThread Begin");
			try {
				long size = redisService.listSize("spgAddr");
				log.info("spgAddr have:" + size);
				if (size < 100) {
					if (!StringUtils.isEmptyOrWhitespaceOnly(SPGUtil.SPG_WALLET_PASSWORD)) {
						
						try {
							SPGUtil.getInstance().walletpassphase(SPGUtil.SPG_WALLET_PASSWORD,
									10);
						} catch (Exception e) {
							log.info("SPG解锁失败");
						}
					}
				}
				while (size < 100) {
					String address = SPGUtil.getInstance().getNewaddress("");
					if (StringUtils.isNullOrEmpty(address))
						continue;
					redisService.listPush("spgAddr", address);
					size = redisService.listSize("spgAddr");
					log.info("spgAddr have:" + size);
					Thread.sleep(50);// 慢点，服务器承受不住
				}
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			log.info("SPGAddrThread End");
		}

	}

	class EthAddrThread implements Runnable {
		private Log log = LogFactory.getLog(EthAddrThread.class);

		@Override
		public void run() {
			// TODO Auto-generated method stub
			log.info("EthAddrThread Begin");
			try {
				long size = redisService.listSize("ethAddr");
				log.info("ethAddr have:" + size);
				while (size < 100) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("token", Base64.encode(("47.75.209.115/" + getUTCTimeStr()).getBytes()));
					String res = HttpUtil.post("http://104.171.174.126/createAddress", map);
					JSONObject data = JSONObject.fromObject(res);
					if (data.getInt("status") != 200 || StringUtils.isNullOrEmpty(data.getString("address")))
						continue;
					redisService.listPush("ethAddr", data.getString("address"));
					size = redisService.listSize("ethAddr");
					log.info("ethAddr have:" + size);
					Thread.sleep(50);// 慢点，服务器承受不住
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			log.info("EthAddrThread End");
		}
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
}
