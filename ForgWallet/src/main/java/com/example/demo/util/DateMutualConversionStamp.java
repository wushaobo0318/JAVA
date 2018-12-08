package com.example.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
/**
 * 时间与时间戳互相转换工具类（静态）
 * @author Administrator
 *
 */
public class DateMutualConversionStamp {
	
	/**
	 * 获取当前时间，格式（yyyy-MM-dd HH:mm:ss）
	 * @param d
	 * @return
	 */
	public static String DateFormat() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(d);
	}
	/** 
     * 将时间转换为时间戳
     */    
    public static String dateToStamp(String s) throws ParseException{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
		try {
			date = simpleDateFormat.parse(s);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        long ts = (date.getTime())/1000;
        res = String.valueOf(ts);
        return res;
    }
    /** 
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    /**
     *  ！获取时间的当天的开始与结束时间
     * @param now
     * @return
     */
    public static Map<String,Object> taDay( Date now) {
    	SimpleDateFormat fmt1= new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    	SimpleDateFormat fmt2= new SimpleDateFormat("yyyy-MM-dd 23:59:59");
    	
    	Map<String,Object> map=new HashMap<>();
    	System.out.println("今天的开始时间："+fmt1.format(now));
    	System.out.println("今天的结束时间："+fmt2.format(now));
    	map.put("startTime", fmt1.format(now));
    	map.put("startTime", fmt1.format(now));

    	return map;
    }
}
