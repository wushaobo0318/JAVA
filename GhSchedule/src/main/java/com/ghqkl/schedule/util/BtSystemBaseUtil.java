package com.ghqkl.schedule.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.googlecode.jsonrpc4j.Base64;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class BtSystemBaseUtil {
	protected JsonRpcHttpClient client;
	
	
	

	/**
	 * 验证地址是否存在
	 * 
	 * @param address
	 * @return
	 * @throws Throwable
	 */
	public String validateaddress(String address) throws Throwable {
		return (String) client.invoke("validateaddress", new Object[] { address }, Object.class).toString();
	}

	/**
	 * 如果钱包加密需要临时解锁钱包
	 * 
	 * @param password
	 * @param time
	 * @return
	 * @throws Throwable
	 */
	public Object walletpassphase(String password, int time) throws Throwable {
		return  client.invoke("walletpassphrase", new Object[] { password, time }, Object.class);
	}

	/**
	 * 转账到制定的账户中
	 * 
	 * @param address
	 * @param amount
	 * @return
	 * @throws Throwable
	 */
	public String sendtoaddress(String address, double amount) throws Throwable {
		return (String) client.invoke("sendtoaddress", new Object[] { address, amount }, Object.class).toString();
	}

	/**
	 * 查询账户下的交易记录
	 * 
	 * @param account
	 * @param count
	 * @param offset
	 * @return
	 * @throws Throwable
	 */
	public String listtransactions(String account, int count, int offset) throws Throwable {
		return (String) client.invoke("listtransactions", new Object[] { account, count, offset }, Object.class)
				.toString();
	}

	/**
	 * 获取地址下未花费的币量
	 * 
	 * @param account
	 * @param count
	 * @param offset
	 * @return
	 * @throws Throwable
	 */
	public String listunspent(int minconf, int maxconf, String address) throws Throwable {
		String[] addresss = new String[] { address };
		return (String) client.invoke("listunspent", new Object[] { minconf, maxconf, addresss }, Object.class)
				.toString();
	}

	/**
	 * 生成新的接收地址
	 * 
	 * @return
	 * @throws Throwable
	 */
	public String getNewaddress(String account) throws Throwable {
		Object[] objs;
		if(!StringUtils.isEmpty(account)) {
			objs=new Object[] {account};
		}else {
			objs=new Object[] {};
		}
		return (String) client.invoke("getnewaddress", objs, Object.class).toString();
	}

	/**
	 * 获取钱包信息
	 * 
	 * @return
	 * @throws Throwable
	 */
	public String getInfo() throws Throwable {
		return client.invoke("getinfo", new Object[] {}, Object.class).toString();
	}
	
	/**
	 * 获取所有交易的txid集合
	 * @return
	 * @throws Throwable
	 */
	public List<Object> getTxids( ) throws Throwable{
		List<Object> list=new ArrayList<>();
		Object res=this.client.invoke("listtransactions", new Object[] {}, Object.class);
		JSONArray arr=JSONArray.fromObject(res);
		Object[] objs=arr.toArray();
		for (Object object : objs) {
			JSONObject js=JSONObject.fromObject(object);
			String category=js.getString("category");
			if(category!=null&&!"send".equals(category)) {
				String txid=js.getString("txid");
				list.add(txid);
			}
			//System.out.println(js);
		}
		return list;
	}
	
	/**
	 * 根据txid获取交易信息
	 * @param txid
	 * @return
	 * @throws Throwable
	 */
	public JSONObject getPayByTxid(String txid) throws Throwable{
		Object test=this.client.invoke("gettransaction", new Object[] {txid}, Object.class);
		return JSONObject.fromObject(test);
	}

}
