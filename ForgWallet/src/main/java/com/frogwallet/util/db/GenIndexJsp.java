package com.frogwallet.util.db;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/** 生成Jsp类工具 */
public class GenIndexJsp {
//	private String zhujie = "@Controller";// 类注解
//	private String importpackage = "\r\nimport org.springframework.beans.factory.annotation.Autowired; " + "\r\nimport org.springframework.stereotype.Controller;               ";
//	private ArrayList<String> colNames = new ArrayList<String>(); // 列名数组
//	private ArrayList<String> colTypes = new ArrayList<String>();; // 列名类型数组
//	private ArrayList<String> colComments = new ArrayList<String>(); // 列名注释数组
//	private boolean f_util = true; // 是否需要导入包java.util.*
//	private boolean f_sql = false; // 是否需要导入包java.sql.*
	String tablename = "";
	String tablecomment = "";
	ArrayList<String> tablenames;

	/* 构造函数 */
	public GenIndexJsp(ArrayList<String> tablenames) {
		this.tablenames = tablenames;
		String content = parse();

		try {
			File directory = new File("");
			System.out.println("路径：" + directory.getAbsolutePath() + "/src/main/webapp/WEB-INF/page/atalog.jsp");
			File jspFile = new File(directory.getAbsolutePath() + "/src/main/webapp/WEB-INF/page/");
			if (!jspFile.exists()) {
				jspFile.mkdirs();
			}
			FileWriter fw = new FileWriter(directory.getAbsolutePath() + "/src/main/webapp/WEB-INF/page/catalog.jsp");
			PrintWriter pw = new PrintWriter(fw);
			pw.println(content);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/** 功能：生成Jsp主体代码
	 * @return */
	private String parse() {
		StringBuffer sb = new StringBuffer();
		sb.append(" <%@ page language=\"java\" contentType=\"text/html; charset=utf-8\" pageEncoding=\"utf-8\"%>\r\n");
		sb.append(" <html>                                                                                      \r\n");
		sb.append(" <head>                                                                                      \r\n");
		sb.append(" <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">                     \r\n");
		sb.append(" </head>                                                                                     \r\n");
		sb.append(" <body>                                                              \r\n");
		sb.append(" <ul class=\"list-group\">                                          \r\n");
		for(int i=0;i<tablenames.size();i++){
			String[] split = tablenames.get(i).split(",");
			sb.append(" 	<li class=\"list-group-item\"><a href=\"${pageContext.request.contextPath}/page/1/20/select"+initcap(split[0])+".do\">"+split[1]+"管理</a></li>             \r\n");
		}
		sb.append(" </ul>                                                              \r\n");
		sb.append(" </body>                                                              \r\n");
		sb.append(" </html>                                                              \r\n");
		return sb.toString();
	}

	/** 功能：将输入字符串的首字母改成大写
	 * @param str
	 * @return */
	private String initcap(String str) {
		str = str.trim();
		String[] strs=str.split("_");
		StringBuffer sb=new StringBuffer();
		for (int i = 1; i < strs.length; i++) {
			String frist=strs[i].substring(0, 1).toUpperCase();
			sb.append(frist+strs[i].substring(1));
			
		}

		return new String(sb.toString());
	}

//
//	/** 功能：获得列的数据类型
//	 * @param sqlType
//	 * @return */
	private String sqlType2JavaType(String sqlType) {

		if (sqlType.equalsIgnoreCase("bit")) {
			return "boolean";
		} else if (sqlType.equalsIgnoreCase("tinyint")) {
			return "byte";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			return "short";
		} else if (sqlType.equalsIgnoreCase("int")) {
			return "int";
		} else if (sqlType.equalsIgnoreCase("bigint")) {
			return "long";
		} else if (sqlType.equalsIgnoreCase("float")) {
			return "float";
		} else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric") || sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money") || sqlType.equalsIgnoreCase("smallmoney")) {
			return "double";
		} else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char") || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar") || sqlType.equalsIgnoreCase("text")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime")) {
			return "Date";
		} else if (sqlType.equalsIgnoreCase("image")) {
			return "Blod";
		} else if (sqlType.equalsIgnoreCase("date")) {
			return "Date";
		}

		return null;
	}

	/** 出口 TODO
	 * @param args */
	public static void main(String[] args) {
		ArrayList<String> tablenames = new ArrayList<String>();
		tablenames.add("tag,标签");
		new GenIndexJsp(tablenames);

	}

}
