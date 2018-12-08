package com.frogwallet.util.db;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/** 生成Dao类工具 */
public class GenDao {
	static String tablenames = "collection";
	private String zhujie = "@Repository";// 类注解
	private String importpackage =  "\r\nimport org.springframework.stereotype.Repository;"+
//									"\r\nimport org.apache.ibatis.annotations.Delete;   "+
//									"\r\nimport org.apache.ibatis.annotations.Insert;   "+
//									"\r\nimport org.apache.ibatis.annotations.Options;  "+
//									"\r\nimport org.apache.ibatis.annotations.Result;   "+
//									"\r\nimport org.apache.ibatis.annotations.ResultMap;"+
//									"\r\nimport org.apache.ibatis.annotations.Results;  "+
//									"\r\nimport org.apache.ibatis.annotations.Select;   "+
//									"\r\nimport org.apache.ibatis.annotations.Update;   "+
									"\r\nimport com.gh.xiangsu.util.db.SearchPageUtil;     "+
									"\r\nimport java.util.*;     ";

	private ArrayList<String> colNames = new ArrayList<String>(); // 列名数组
	private ArrayList<String> colTypes = new ArrayList<String>();; // 列名类型数组
	private ArrayList<String> colComments = new ArrayList<String>(); // 列名注释数组
	private boolean f_util = true; // 是否需要导入包java.util.*
	private boolean f_sql = false; // 是否需要导入包java.sql.*
	String tablename = "";
	String tablecomment = "";

	/* 构造函数 */
	public GenDao(String tablenames) {
		String[] split = tablenames.split(",");
		tablename = split[0];
		tablecomment = split[1];
		// 创建连接
		Connection con = null;
		// 查要生成实体类的表
		String sql = "select column_name,data_type,column_comment from columns where TABLE_SCHEMA='"+GenCode.DATABASE+"' and table_name = '"+tablename+"'";
		PreparedStatement pStemt = null;
		try {
			try {
				Class.forName(GenCode.DRIVER);
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
			con = DriverManager.getConnection(GenCode.URL, GenCode.NAME, GenCode.PASS);
			pStemt = con.prepareStatement(sql);

			ResultSet executeQuery = pStemt.executeQuery();
			while (executeQuery.next()) {
				String columnname = executeQuery.getString(1);
				String datatype = executeQuery.getString(2);
				String columncomment = executeQuery.getString(3);
				colNames.add(columnname);
				colTypes.add(datatype);
				colComments.add(columncomment);
				System.out.println(columnname + "\t" + datatype+"\t"+columncomment);
			}// 显示数据

			String content = parse();

			try {
				File directory = new File("");
				System.out.println("路径：" + directory.getAbsolutePath() + "/src/" + GenCode.daobackage.replace(".", "/") + "/" +  GenEntity.captureName(initcap(tablename)) + "Dao.java");
				File daoFile = new File(directory.getAbsolutePath() + "/src/" + GenCode.daobackage.replace(".", "/") + "/");
				if(!daoFile.exists()){
					daoFile.mkdirs();
				}
				
				FileWriter fw = new FileWriter(directory.getAbsolutePath() + "/src/" + GenCode.daobackage.replace(".", "/") + "/" +  GenEntity.captureName(initcap(tablename)) + "Dao.java");
				PrintWriter pw = new PrintWriter(fw);
				pw.println(content);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pStemt.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/** 功能：生成实体类主体代码
	 * @return */
	private String parse() {
		StringBuffer sb = new StringBuffer();
		sb.append("package " + GenCode.daobackagesub + ";\r\n");
		// 判断是否导入工具包
		if (f_util) {
//			sb.append("import java.util.Date;\r\n");
		}
		if (f_sql) {
			sb.append("import java.sql.*;\r\n");
		}
		sb.append(importpackage+"\r\n");
		sb.append("import "+GenCode.pojobackagesub+"."+ GenEntity.captureName(initcap(tablename))+";\r\n");
		// 注释部分
		sb.append("   /**\r\n");
		sb.append("    * " + tablename + " Dao类\r\n");
		sb.append("    */ \r\n");
		// 实体部分
		sb.append(zhujie + "\r\n");
		sb.append("public interface " + GenEntity.captureName(initcap(tablename)) + "Dao{\r\n");
		

		//增
		sb.append("	public int add"+ GenEntity.captureName(initcap(tablename))+"("+ GenEntity.captureName(initcap(tablename))+" "+tablename+");\r\n\r\n");
		//删
		sb.append("	public int del"+ GenEntity.captureName(initcap(tablename))+"(String id);\r\n\r\n");
		sb.append("	public int del"+ GenEntity.captureName(initcap(tablename))+"True(String id);\r\n\r\n");
		//改
		sb.append("	public int update"+ GenEntity.captureName(initcap(tablename))+"("+ GenEntity.captureName(initcap(tablename))+" "+tablename+");\r\n\r\n");
		//查
		sb.append("	public "+ GenEntity.captureName(initcap(tablename))+" select"+GenEntity.captureName(initcap(tablename))+"ByID(String id);\r\n\r\n");
		
		sb.append("	public int select"+ GenEntity.captureName(initcap(tablename))+"Count();\r\n\r\n");
		
		sb.append("	public int select"+ GenEntity.captureName(initcap(tablename))+"CountAll();\r\n\r\n");
		
		sb.append("	public List<"+ GenEntity.captureName(initcap(tablename))+"> select"+GenEntity.captureName(initcap(tablename))+"sByPage(SearchPageUtil spu);\r\n\r\n");
		
		sb.append("	public int select"+ GenEntity.captureName(initcap(tablename))+"sByPOJOCount("+ GenEntity.captureName(initcap(tablename))+" "+tablename+");\r\n\r\n");
		
		sb.append("	public List<"+ GenEntity.captureName(initcap(tablename))+"> select"+GenEntity.captureName(initcap(tablename))+"sByPOJO("+ GenEntity.captureName(initcap(tablename))+" "+tablename+");\r\n\r\n");
		
		sb.append("	public List<"+ GenEntity.captureName(initcap(tablename))+"> select"+GenEntity.captureName(initcap(tablename))+"sByPojoPage(Map<String,Object> map);\r\n\r\n");
		
		sb.append("}\r\n");

		// System.out.println(sb.toString());
		return sb.toString();
	}


	/**
	 * 功能：将输入字符串的首字母改成大写
	 * 
	 * @param str
	 * @return
	 */
	private static String initcap(String str) {
		str = str.trim();
		String[] strs = str.split("_");
		StringBuffer sb = new StringBuffer();
		if (str.startsWith("jg_")) {
			for (int i = 1; i < strs.length; i++) {
				String frist = strs[i].substring(0, 1).toUpperCase();
				sb.append(frist + strs[i].substring(1).toLowerCase());

			}
		}else {
			for (int i = 0; i < strs.length; i++) {
				if(i>0) {
					
					String frist = strs[i].substring(0, 1).toUpperCase();
					sb.append(frist + strs[i].substring(1).toLowerCase());
				}else {
					sb.append(strs[i]);
				}

			}
		}

		return new String(sb.toString());
	}

	/** 功能：获得列的数据类型
	 * @param sqlType
	 * @return */
	public String sqlType2JavaType(String sqlType) {

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
		
			new GenDao("collection,收藏");

	}

}
