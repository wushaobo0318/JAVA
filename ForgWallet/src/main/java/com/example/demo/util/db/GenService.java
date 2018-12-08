package com.example.demo.util.db;

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

/** 生成Service类工具 */
public class GenService {
	//static String tablenames = "collection,files,freind,mainpost,mainpost_files,mainpost_tag,message,person,person_tag,rebackpost,rebackpost_files,sysconfig,tag,userconfig";
	static String tablenames = "collection";
	private String zhujie = "@Service";// 类注解
	private String importpackage = 	"\r\nimport org.springframework.beans.factory.annotation.Autowired; "+
									"\r\nimport org.springframework.stereotype.Service;                 "+
									"\r\nimport  com.gh.shops.util.page.SearchPageUtil;     "+
									"\r\nimport java.util.*;     ";
	
	private ArrayList<String> colNames = new ArrayList<String>(); // 列名数组
	private ArrayList<String> colTypes = new ArrayList<String>();; // 列名类型数组
	private ArrayList<String> colComments = new ArrayList<String>(); // 列名注释数组
	String tablename = "";
	String tablecomment = "";

	/* 构造函数 */
	public GenService(String tablenames) {
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
				System.out.println("路径：" + directory.getAbsolutePath() + "/src/" + GenCode.servicebackage.replace(".", "/") + "/" + initcap(tablename) + "Service.java");
				File serviceFile = new File(directory.getAbsolutePath() + "/src/" + GenCode.servicebackage.replace(".", "/") + "/");
				if(!serviceFile.exists()){
					serviceFile.mkdirs();
				}
				
				FileWriter fw = new FileWriter(directory.getAbsolutePath() + "/src/" + GenCode.servicebackage.replace(".", "/") + "/" + initcap(tablename) + "Service.java");
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
	 * @param colnames
	 * @param colTypes
	 * @param colSizes
	 * @return */
	public String parse() {
		StringBuffer sb = new StringBuffer();
		sb.append("package " + GenCode.servicebackagesub + ";\r\n");

		sb.append(importpackage+"\r\n");
		sb.append("import "+GenCode.pojobackagesub+"."+initcap(tablename)+";\r\n");
		sb.append("import "+GenCode.daobackagesub+"."+initcap(tablename)+"Dao;\r\n");
		// 注释部分
		sb.append("   /**\r\n");
		sb.append("    * " + tablename + " Service类\r\n");
		sb.append("    */ \r\n");
		// 实体部分
		sb.append(zhujie + "\r\n");
		sb.append("public class " + initcap(tablename) + "Service{\r\n");
		sb.append("@Autowired\r\n");
		sb.append("private "+initcap(tablename)+"Dao dao;\r\n");
		
//		String cols1 = "";
//		String cols2 = "";
//		String cols3 = "";
//		String cols4 = "";
//		for(int i=0;i<colNames.size();i++){
//			cols1+=(colNames.get(i)+",");
//			cols2+=("#{"+colNames.get(i)+"},");
//			if(!colNames.get(i).equalsIgnoreCase("id")){
//				cols3 += (colNames.get(i) +" = #{"+colNames.get(i)+"},");
//			}
//			cols4 +="@Result(column =\""+colNames.get(i)+"\",property=\""+colNames.get(i)+"\"),";
//		}
		//增
		sb.append("	public void add"+initcap(tablename)+"("+initcap(tablename)+" "+tablename+"){\r\n");
		sb.append("		dao.add"+initcap(tablename)+"("+tablename+");\r\n");
		sb.append("	}\r\n\r\n");
		
		//删
		sb.append("	public void del"+initcap(tablename)+"(String id){\r\n");
		sb.append("		dao.del"+initcap(tablename)+"(id);\r\n");
		sb.append("	}\r\n\r\n");
		
		sb.append("	public void del"+initcap(tablename)+"True(String id){\r\n");
		sb.append("		dao.del"+initcap(tablename)+"True(id);\r\n");
		sb.append("	}\r\n\r\n");
		
		
		//改
		sb.append("	public void update"+initcap(tablename)+"("+initcap(tablename)+" "+tablename+"){\r\n");
		sb.append("		dao.update"+initcap(tablename)+"("+tablename+");\r\n");
		sb.append("	}\r\n\r\n");
		//查
		sb.append("	public "+initcap(tablename)+" select"+initcap(tablename)+"ById(String id){\r\n");
		sb.append("		return dao.select"+initcap(tablename)+"ByID(id);\r\n");
		sb.append("	}\r\n\r\n");
		
		sb.append("	public int select"+initcap(tablename)+"Count(){\r\n");
		sb.append("		return dao.select"+initcap(tablename)+"Count();\r\n");
		sb.append("	}\r\n\r\n");
		
		sb.append("	public int select"+initcap(tablename)+"CountAll(){\r\n");
		sb.append("		return dao.select"+initcap(tablename)+"CountAll();\r\n");
		sb.append("	}\r\n\r\n");
		
		sb.append("	public List<"+initcap(tablename)+"> select"+initcap(tablename)+"sByPage(SearchPageUtil spu){\r\n");
		sb.append("		return dao.select"+initcap(tablename)+"sByPage(spu);\r\n");
		sb.append("	}\r\n\r\n");
		
		sb.append("	public int select"+initcap(tablename)+"sByPOJOCount("+initcap(tablename)+" "+tablename+"){\r\n");
		sb.append("		return dao.select"+initcap(tablename)+"sByPOJOCount("+tablename+");\r\n");
		sb.append("	}\r\n\r\n");
		
		sb.append("	public List<"+initcap(tablename)+"> select"+initcap(tablename)+"sByPOJO("+initcap(tablename)+" "+tablename+"){\r\n");
		sb.append("		return dao.select"+initcap(tablename)+"sByPOJO("+tablename+");\r\n");
		sb.append("	}\r\n\r\n");
		
		sb.append("	public List<"+initcap(tablename)+"> select"+initcap(tablename)+"sByPojoPage(SearchPageUtil spu,"+initcap(tablename)+" "+tablename+"){\r\n");
		sb.append("		Map map = new HashMap();\r\n");
		sb.append("		map.put(\"spu\",spu);\r\n");
		sb.append("		map.put(\"pojo\","+tablename+");\r\n");
		sb.append("		return dao.select"+initcap(tablename)+"sByPojoPage(map);\r\n");
		sb.append("	}\r\n\r\n");

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
	private String initcap(String str) {
		str = str.trim();
		String[] strs = str.split("_");
		StringBuffer sb = new StringBuffer();
		if (str.startsWith("jg_")) {
			for (int i = 1; i < strs.length; i++) {
				String frist = strs[i].substring(0, 1).toUpperCase();
				sb.append(frist + strs[i].substring(1));

			}
		}else {
			for (int i = 0; i < strs.length; i++) {
				if(i>0) {
					
					String frist = strs[i].substring(0, 1).toUpperCase();
					sb.append(frist + strs[i].substring(1));
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
		
			new GenService("collection,收藏");

	}

}
