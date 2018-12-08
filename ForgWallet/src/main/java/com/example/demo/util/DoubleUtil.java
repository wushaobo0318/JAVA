package com.example.demo.util;


import java.math.BigDecimal;
import java.text.NumberFormat;
/**
 * double 的工具类
 * @author Administrator
 *
 */
public class DoubleUtil {
	/**
	 * double类型的值保留小数点后几位
	 * @param f 要改变的值
	 * @param several 要保留的小数后的位数
	 * @return
	 */
	public static double doubleRetainSeveral (double f ,int several ) {
		BigDecimal b = new BigDecimal(f); 
		double df = b.setScale(several, BigDecimal.ROUND_HALF_UP).doubleValue();
		return df;
	}
	/**
	 * 将大额的double值转换为String 避免被转换为科学计数法
	 * @param amount double值
	 * @return
	 */
	public static String double2Str(double amount) {
		NumberFormat nf=NumberFormat.getInstance();
		nf.setMaximumFractionDigits(10);
		nf.setGroupingUsed(false);
		return nf.format(amount);
	}
	public static void main(String[] args) {
//		System.out.println(doubleRetainSeveral(0.1274,10));
//		System.out.println(10/10.7);
//		Integer i=1;
//		System.out.println(i==1);
		System.out.println(DoubleUtil.double2Str(1999942243.0));
	}
}
