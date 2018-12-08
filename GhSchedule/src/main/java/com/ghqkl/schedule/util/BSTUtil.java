package com.ghqkl.schedule.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.AEADBadTagException;

import org.apache.commons.lang.StringUtils;

import com.googlecode.jsonrpc4j.Base64;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BSTUtil extends BtSystemBaseUtil{
	public static String RPC_USER = null;
	public static String RPC_PASSWORD = null;
	public static String RPC_ALLOWIP = null;
	public static String RPC_PORT = null;
	public static String WALLET_PASSWORD;
	private static BSTUtil instance;
	public BSTUtil() throws Throwable {
		// 身份认证
		String cred = Base64.encodeBytes((RPC_USER + ":" + RPC_PASSWORD).getBytes());
		Map<String, String> headers = new HashMap<String, String>(1);
		headers.put("Authorization", "Basic " + cred);
		client = new JsonRpcHttpClient(new URL("http://" + RPC_ALLOWIP + ":" + RPC_PORT), headers);
	}
	private static void init() throws Throwable {
		if (null == instance) {
			RPC_ALLOWIP=AESUtil.decrypt(PropertiesFile.getValue("RPC_ALLOWIP"), AESUtil.KEY);
			RPC_PORT=AESUtil.decrypt(PropertiesFile.getValue("RPC_PORT"),AESUtil.KEY);
			RPC_USER=AESUtil.decrypt(PropertiesFile.getValue("RPC_USER"),AESUtil.KEY);
			RPC_PASSWORD=AESUtil.decrypt(PropertiesFile.getValue("RPC_PASSWORD"),AESUtil.KEY);
			WALLET_PASSWORD=AESUtil.decrypt(PropertiesFile.getValue("WALLET_PASSWORD"),AESUtil.KEY);
			instance = new BSTUtil();
		}
	}
	public static BSTUtil getInstance() throws Throwable {
		init();
		return instance;
	}

	
	public static void main(String[] args) throws Throwable {
		BSTUtil.getInstance().getTxids();
//		BSTUtil.getInstance().walletpassphase(AESUtil.decrypt("kkIIHDagRM23SmavwZgHWnIy3MjalYoXQzICg1FoeUI=", AESUtil.KEY), 10);
	}
}