package com.ghqkl.schedule.util;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;


import com.googlecode.jsonrpc4j.Base64;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

public class LTCUtil extends BtSystemBaseUtil{
	public static String LTC_RPC_USER = null;
	public static String LTC_RPC_PASSWORD = null;
	public static String LTC_RPC_ALLOWIP = null;
	public static String LTC_RPC_PORT = null;
	public static String LTC_WALLET_PASSWORD;
	private static LTCUtil instance;
	public LTCUtil() throws Throwable {
		// 身份认证
		String cred = Base64.encodeBytes((LTC_RPC_USER + ":" + LTC_RPC_PASSWORD).getBytes());
		Map<String, String> headers = new HashMap<String, String>(1);
		headers.put("Authorization", "Basic " + cred);
		client = new JsonRpcHttpClient(new URL("http://" + LTC_RPC_ALLOWIP + ":" + LTC_RPC_PORT), headers);
	}
	private static void init() throws Throwable {
		if (null == instance) {
			LTC_RPC_ALLOWIP=AESUtil.decrypt(PropertiesFile.getValue("LTC_RPC_ALLOWIP"), AESUtil.KEY);
			LTC_RPC_PORT=AESUtil.decrypt(PropertiesFile.getValue("LTC_RPC_PORT"),AESUtil.KEY);
			LTC_RPC_USER=AESUtil.decrypt(PropertiesFile.getValue("LTC_RPC_USER"),AESUtil.KEY);
			LTC_RPC_PASSWORD=AESUtil.decrypt(PropertiesFile.getValue("LTC_RPC_PASSWORD"),AESUtil.KEY);
			LTC_WALLET_PASSWORD=AESUtil.decrypt(PropertiesFile.getValue("LTC_WALLET_PASSWORD"),AESUtil.KEY);
			instance = new LTCUtil();
		}
	}
	public static LTCUtil getInstance() throws Throwable {
		init();
		return instance;
	}

	
	public static void main(String[] args) throws Throwable {
		LTCUtil.getInstance();
	}
}