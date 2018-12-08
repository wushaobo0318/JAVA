package com.example.demo.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//自动生成代码工具类
public class GenCode {

	// -------------自动生成代码如下参数要修改-----------------------
	public static final String databaseurl = "39.104.16.197"; // 数据库地址
	public static final String port = "3306"; // 数据库端口
	public static final String NAME = "root";// 数据数登录用户名
	public static final String PASS = "Ghqkl2018.";// 数据库登录密码
	public static final String DATABASE = "gh_xiangsu";// 数据库名称
	public static final String bsebackage = "com.gh.xiangsu";// 基础包名

	public static final String pojobackage = "main.java.com.gh.xiangsu.entity"; // pojo包名
	public static final String pojobackagesub = "com.gh.xiangsu.entity"; // pojo包名

	public static final String daobackage = "main.java.com.gh.xiangsu.dao";// dao包名
	public static final String sql = "main.resources.mappers";// sql路径
	public static final String daobackagesub = "com.gh.xiangsu.dao";// dao包名
	public static final String servicebackage = "main.java.com.gh.xiangsu.service";// service包名
	public static final String servicebackagesub = "com.gh.xiangsu.service";// service包名
	public static final String controllerbackage = "main.java.com.gh.xiangsu.controller";// controller包名
	public static final String controllerbackagesub = "com.gh.xiangsu.controller";// controller包名
	// -------------------------------------------------------------

	public static final String DRIVER = "com.mysql.jdbc.Driver";
	static ArrayList<String> tablenames = new ArrayList<String>();
	public static final String URL = "jdbc:mysql://" + databaseurl + ":" + port
			+ "/information_schema?useUnicode=true&characterEncoding=UTF-8";

	public static void main(String[] args) {
		// 创建连接
		Connection con = null;
		// 查要生成实体类的表
		String sql = "select table_name,table_comment from TABLES where TABLE_SCHEMA = '" + GenCode.DATABASE + "'";
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
				String tablename = executeQuery.getString(1);
				String tablecomment = executeQuery.getString(2);
				if (tablecomment == null || "".equals(tablecomment)) {
					tablecomment = tablename;
				}
				tablenames.add(tablename + "," + tablecomment);
				System.out.println(tablename + "\t" + tablecomment);
			} // 显示数据
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < tablenames.size(); i++) {

			String tablename = tablenames.get(i).trim();
			System.out.println(tablename);
			if ("cjhj_record_transfer".equals(tablename.split(",")[0])) {
				new GenXmlMapper(tablename); // 生成xmlMapper文件
				new GenEntity(tablename); // 生成实体
				new GenDao(tablename); // 生成dao
//			new GenService(tablename);   //生成service
//			new GenController(tablename);//生成controller
//			new GenJsp(tablename);       //生成jsp
			}
		}
//		new GenIndexJsp(tablenames);//生成目录
//		new GenApplicationContext();//生成配置文件

	}

	public static void creatCode() {
		// 创建连接
		Connection con = null;
		// 查要生成实体类的表
		String sql = "select table_name,table_comment from TABLES where TABLE_SCHEMA = '" + GenCode.DATABASE + "'";
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
				String tablename = executeQuery.getString(1);
				String tablecomment = executeQuery.getString(2);
				if (tablecomment == null || "".equals(tablecomment)) {
					tablecomment = tablename;
				}
				tablenames.add(tablename + "," + tablecomment);
				System.out.println(tablename + "\t" + tablecomment);
			} // 显示数据
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < tablenames.size(); i++) {
			String tablename = tablenames.get(i).trim();
			new GenEntity(tablename); // 生成实体
			new GenDao(tablename); // 生成dao
			new GenXmlMapper(tablename); // 生成xmlMapper文件
//					new GenService(tablename);   //生成service
//					new GenController(tablename);//生成controller
//					new GenJsp(tablename);       //生成jsp
		}
//				new GenIndexJsp(tablenames);//生成目录
//				new GenApplicationContext();//生成配置文件
	}
}
