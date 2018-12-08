package com.ghqkl.schedule.util;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.ghqkl.schedule.util.security.Base64;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

public class SPGUtil extends BtSystemBaseUtil{
	private static SPGUtil instance;
	/**
	 * TIuoQGcAsMOSimVxwI+AOA==
SPG_RPC_PORT=PJVW0qSz+yu1Y66UM7jfkQ==
SPG_RPC_USER=NGOjKIbq6mJp/ils+BCcDQ==
SPG_RPC_PASSWORD=NGOjKIbq6mJp/ils+BCcDQ==
	 */
	public static String SPG_RPC_USER = "spg2018Gh";
	public static String SPG_RPC_PASSWORD = "spg2019Gh";
	public static String SPG_RPC_ALLOWIP = "47.244.100.123";
	public static String SPG_RPC_PORT = "62350";
	public static String SPG_WALLET_PASSWORD;

	public SPGUtil() throws Throwable {
		// 身份认证
		String cred = Base64.encode((SPG_RPC_USER + ":" + SPG_RPC_PASSWORD).getBytes());
		Map<String, String> headers = new HashMap<String, String>(1);
		headers.put("Authorization", "Basic " + cred);
		this.client = new JsonRpcHttpClient(new URL("http://" + SPG_RPC_ALLOWIP + ":" + SPG_RPC_PORT), headers);
	}
	private static void init() throws Throwable {
		if (null == instance) {
			SPG_RPC_ALLOWIP=AESUtil.decrypt(PropertiesFile.getValue("SPG_RPC_ALLOWIP"), AESUtil.KEY);
			SPG_RPC_PORT=AESUtil.decrypt(PropertiesFile.getValue("SPG_RPC_PORT"),AESUtil.KEY);
			SPG_RPC_USER=AESUtil.decrypt(PropertiesFile.getValue("SPG_RPC_USER"),AESUtil.KEY);
			SPG_RPC_PASSWORD=AESUtil.decrypt(PropertiesFile.getValue("SPG_RPC_PASSWORD"),AESUtil.KEY);
			SPG_WALLET_PASSWORD=AESUtil.decrypt(PropertiesFile.getValue("SPG_WALLET_PASSWORD"),AESUtil.KEY);
			instance = new SPGUtil();
		}
	}
	public static SPGUtil getInstance() throws Throwable {
		init();
		return instance;
	}
	public static void main(String[] args) throws Throwable {
//		SPGUtil spg=new SPGUtil();
//		System.out.println(spg.getInfo());
		System.out.println(SPGUtil.getInstance().walletpassphase(SPGUtil.SPG_WALLET_PASSWORD,
				10));
	}
	
}
