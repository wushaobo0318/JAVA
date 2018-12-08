package com.ghqkl.schedule.util;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;


import com.googlecode.jsonrpc4j.Base64;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

public class BTCUtil extends BtSystemBaseUtil{
	public static String BTC_RPC_USER = null;
	public static String BTC_RPC_PASSWORD = null;
	public static String BTC_RPC_ALLOWIP = null;
	public static String BTC_RPC_PORT = null;
	public static String BTC_WALLET_PASSWORD;
	private static BTCUtil instance;
	public BTCUtil() throws Throwable {
		// 身份认证
		String cred = Base64.encodeBytes((BTC_RPC_USER + ":" + BTC_RPC_PASSWORD).getBytes());
		Map<String, String> headers = new HashMap<String, String>(1);
		headers.put("Authorization", "Basic " + cred);
		client = new JsonRpcHttpClient(new URL("http://" + BTC_RPC_ALLOWIP + ":" + BTC_RPC_PORT), headers);
	}
	private static void init() throws Throwable {
		if (null == instance) {
			BTC_RPC_ALLOWIP=AESUtil.decrypt(PropertiesFile.getValue("BTC_RPC_ALLOWIP"), AESUtil.KEY);
			BTC_RPC_PORT=AESUtil.decrypt(PropertiesFile.getValue("BTC_RPC_PORT"),AESUtil.KEY);
			BTC_RPC_USER=AESUtil.decrypt(PropertiesFile.getValue("BTC_RPC_USER"),AESUtil.KEY);
			BTC_RPC_PASSWORD=AESUtil.decrypt(PropertiesFile.getValue("BTC_RPC_PASSWORD"),AESUtil.KEY);
			BTC_WALLET_PASSWORD=AESUtil.decrypt(PropertiesFile.getValue("BTC_WALLET_PASSWORD"),AESUtil.KEY);
			instance = new BTCUtil();
		}
	}
	public static BTCUtil getInstance() throws Throwable {
		init();
		return instance;
	}

	
	public static void main(String[] args) throws Throwable {
		BTCUtil.getInstance().walletpassphase(BTCUtil.BTC_WALLET_PASSWORD, 10);
	}
}