package com.ghqkl.schedule.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpUtil {

	private static final Logger logger = LogManager.getLogger(HttpUtil.class);
	private static final String CHARSET = "UTF-8";

	/**
	 * http get请求
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @return
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static String get(String url) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = httpClient.execute(httpGet);
		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String str = EntityUtils.toString(entity, CHARSET);
				return str;
			}
		} finally {
			response.close();
			httpClient.close();
		}
		return null;
	}
	
	public String Get(String url) {
		CloseableHttpClient client=HttpClientUtil.getHttpClient();
    	HttpGet request = new HttpGet(url);
    	CloseableHttpResponse response=null;
    	RequestConfig requestConfig = RequestConfig.custom()
    	      //.setSocketTimeout(30000)//数据传输过程中数据包之间间隔的最大时间
    	      .setConnectTimeout(20000)//连接建立时间，三次握手完成时间
    	      .setExpectContinueEnabled(true)//重点参数 
    	      .setConnectionRequestTimeout(30000)
    	      .setStaleConnectionCheckEnabled(true)//重点参数，在请求之前校验链接是否有效
    	      .build();
    	request.setConfig(requestConfig);
    	try {
    	   response = client.execute(request);
    	   if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
    		  logger.info("请求失败");
    	      return  null;
    	   }
    	   HttpEntity resEntity =  response.getEntity();
    	   if(resEntity==null){
    	         return  null;
    	   }
    	   String result=EntityUtils.toString(resEntity, "UTF-8");
    	   return result;
    	} catch (UnsupportedEncodingException e) {
    		logger.info(e.getMessage());
    	   return null;
    	} catch (ClientProtocolException e) {
    		logger.info(e.getMessage());
    	   return null;
    	} catch (IOException e) {
    		logger.info(e.getMessage());
    	}finally {
    	   if(response != null)
    	   {
    	      try {
    	         EntityUtils.consume(response.getEntity());//此处高能，通过源码分析，由EntityUtils是否回收HttpEntity
    	         response.close();
    	      } catch (IOException e) {
    	    	  logger.info("关闭response失败:"+ e);
    	      }
    	}
    	}
    	return null;
	}
	
	public String Post(String url,Map<String, Object> params) throws UnsupportedEncodingException {
		CloseableHttpClient client=HttpClientUtil.getHttpClient();
    	HttpPost request = new HttpPost(url);
    	List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			parameters.add(new BasicNameValuePair(key, params.get(key).toString()));
		}
		UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(parameters, CHARSET);
		request.setEntity(uefEntity);
    	CloseableHttpResponse response=null;
    	RequestConfig requestConfig = RequestConfig.custom()
    	      //.setSocketTimeout(30000)//数据传输过程中数据包之间间隔的最大时间
    	      .setConnectTimeout(20000)//连接建立时间，三次握手完成时间
    	      .setExpectContinueEnabled(true)//重点参数 
    	      .setConnectionRequestTimeout(30000)
    	      .setStaleConnectionCheckEnabled(true)//重点参数，在请求之前校验链接是否有效
    	      .build();
    	request.setConfig(requestConfig);
    	try {
    	   response = client.execute(request);
    	   if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
    		  logger.info("请求失败");
    	      return  null;
    	   }
    	   HttpEntity resEntity =  response.getEntity();
    	   if(resEntity==null){
    	         return  null;
    	   }
    	   String result=EntityUtils.toString(resEntity, "UTF-8");
    	   return result;
    	} catch (UnsupportedEncodingException e) {
    		logger.info(e.getMessage());
    	   return null;
    	} catch (ClientProtocolException e) {
    		logger.info(e.getMessage());
    	   return null;
    	} catch (IOException e) {
    		logger.info(e.getMessage());
    	}finally {
    	   if(response != null)
    	   {
    	      try {
    	         EntityUtils.consume(response.getEntity());//此处高能，通过源码分析，由EntityUtils是否回收HttpEntity
    	         response.close();
    	      } catch (IOException e) {
    	    	  logger.info("关闭response失败:"+ e);
    	      }
    	}
    	}
    	return null;
	}


	/**
	 * http get请求
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @return
	 */
	public static String get(String url, Map<String, Object> params) {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			url = url + "?";
			for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext();) {
				String key = iterator.next();
				String temp = key + "=" + params.get(key) + "&";
				url = url + temp;
			}
			url = url.substring(0, url.length() - 1);
			HttpGet httpGet = new HttpGet(url);
			CloseableHttpResponse response = httpClient.execute(httpGet);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String str = EntityUtils.toString(entity, CHARSET);
					return str;
				}
			} finally {
				response.close();
				httpClient.close();
			}
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return null;
	}

	/**
	 * http post请求
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @return
	 */
	public static String post(String url, Map<String, Object> params) {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext();) {
				String key = iterator.next();
				parameters.add(new BasicNameValuePair(key, params.get(key).toString()));
			}
			UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(parameters, CHARSET);
			httpPost.setEntity(uefEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String str = EntityUtils.toString(entity, CHARSET);
					return str;
				}
			} finally {
				response.close();
				httpClient.close();
			}
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return null;
	}

	/**
	 * http post请求
	 * 
	 * @param url
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @return
	 */
	public static String post(String url, String params) {
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			StringEntity sEntity = new StringEntity(params, CHARSET);
			httpPost.setEntity(sEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					return EntityUtils.toString(entity, CHARSET);
				}
			} finally {
				response.close();
				httpClient.close();
			}
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return null;
	}
	public static void main(String[] args) {
		String url="https://www.bcex.top/Api_Market/getCoinTrade";
		Map<String,Object> map=new HashMap<>();
		map.put("part", "eth");
		map.put("coin", "spg");
		System.out.println(HttpUtil.post(url, map));
	}

}
