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

/** 生成Controller类工具 */
public class GenController {
	static String tablenames = "collection";
	private String zhujie = "@Controller";// 类注解
	private String importpackage = 	"\r\nimport org.springframework.beans.factory.annotation.Autowired; "+
									"\r\nimport java.util.*;               "+
									"\r\nimport org.springframework.stereotype.Controller;               "+
									"\r\nimport org.springframework.web.bind.annotation.PathVariable;               "+
									"\r\nimport org.springframework.web.bind.annotation.RequestMapping;               "+
									"\r\nimport org.springframework.web.servlet.ModelAndView;               "+
									"\r\nimport  com.gh.shops.util.page.Page;               "+
									"\r\nimport  com.gh.shops.util.page.SearchPageUtil;               ";
	
	private ArrayList<String> colNames = new ArrayList<String>(); // 列名数组
	private ArrayList<String> colTypes = new ArrayList<String>();; // 列名类型数组
	private ArrayList<String> colComments = new ArrayList<String>(); // 列名注释数组
//	private boolean f_util = true; // 是否需要导入包java.util.*
//	private boolean f_sql = false; // 是否需要导入包java.sql.*
	String tablename = "";
	String tablecomment = "";

	/* 构造函数 */
	public GenController(String tablenames) {
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
				System.out.println("路径：" + directory.getAbsolutePath() + "/src/" + GenCode.controllerbackage.replace(".", "/") + "/" + initcap(tablename) + "Controller.java");
				File controllerFile = new File(directory.getAbsolutePath() + "/src/" + GenCode.controllerbackage.replace(".", "/") + "/");
				if(!controllerFile.exists()){
					controllerFile.mkdirs();
				}
				
				FileWriter fw = new FileWriter(directory.getAbsolutePath() + "/src/" + GenCode.controllerbackage.replace(".", "/") + "/" + initcap(tablename) + "Controller.java");
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
	private String parse() {
		StringBuffer sb = new StringBuffer();
		sb.append("package " + GenCode.controllerbackagesub + ";\r\n");

		sb.append(importpackage+"\r\n");
		sb.append("import "+GenCode.servicebackagesub+"."+initcap(tablename)+"Service;\r\n");
		sb.append("import "+GenCode.pojobackagesub+"."+initcap(tablename)+";\r\n");
		sb.append("import org.springframework.ui.Model;\r\n");
		// 注释部分
		sb.append("   /**\r\n");
		sb.append("    * " + tablename + " Controller类\r\n");
		sb.append("    */ \r\n");
		// 实体部分
		sb.append(zhujie + "\r\n");
		sb.append("public class " + initcap(tablename) + "Controller{\r\n");
		sb.append("	@Autowired\r\n");
		sb.append("	private "+initcap(tablename)+"Service "+tablename+"service;\r\n");
		
		controllerAdd(sb);
		controllerDel(sb);
		controllerUpdate(sb);
		controllerSelect(sb);


		sb.append("}\r\n");

		// System.out.println(sb.toString());
		return sb.toString();
	}
	public void controllerAdd(StringBuffer sb){
		
		sb.append("\r\n//增加"+tablecomment+"\r\n");
		sb.append("@RequestMapping(\"/page/{pageIndex}/{pageSize}/add"+initcap(tablename)+".do\")\r\n");
		sb.append("public String add"+initcap(tablename)+"("+initcap(tablename)+" "+tablename+",@PathVariable Integer pageIndex,@PathVariable Integer pageSize){\r\n");
		//sb.append("	"+tablename+".setCdate(new Date());\r\n");
		//sb.append("	"+tablename+".setId(UUID.randomUUID().toString());\r\n");
		sb.append("	"+tablename+"service.add"+initcap(tablename)+"("+tablename+");\r\n");
		sb.append("	return \"redirect:/page/\"+pageIndex+\"/\"+pageSize+\"/select"+initcap(tablename)+".do\";\r\n");
		sb.append("}\r\n\r\n");
	}
	public void controllerDel(StringBuffer sb){
		
		sb.append("\r\n//删除"+tablecomment+"\r\n");
		sb.append("@RequestMapping(\"/page/{Id}/{pageIndex}/{pageSize}/del"+initcap(tablename)+".do\")\r\n");
		sb.append("public String del"+initcap(tablename)+"(@PathVariable String Id,@PathVariable Integer pageIndex,@PathVariable Integer pageSize){\r\n");
		sb.append("	"+tablename+"service.del"+initcap(tablename)+"(Id);\r\n");
		sb.append("	return \"redirect:/page/\"+pageIndex+\"/\"+pageSize+\"/select"+initcap(tablename)+".do\";\r\n");
		sb.append("}\r\n\r\n");
		
	}
	public void controllerUpdate(StringBuffer sb){
		sb.append("\r\n//修改"+tablecomment+"\r\n");
		sb.append("@RequestMapping(\"/page/{pageIndex}/{pageSize}/update"+initcap(tablename)+".do\")\r\n");
		sb.append("public String update"+initcap(tablename)+"("+initcap(tablename)+" "+tablename+",@PathVariable Integer pageIndex,@PathVariable Integer pageSize){\r\n");
		//sb.append("	"+tablename+".setUdate(new Date());\r\n");
		sb.append("	"+tablename+"service.update"+initcap(tablename)+"("+tablename+");\r\n");
		sb.append("	return \"redirect:/page/\"+pageIndex+\"/\"+pageSize+\"/select"+initcap(tablename)+".do\";\r\n");
		sb.append("}\r\n\r\n");
		
	}
	public void controllerSelect(StringBuffer sb){
		sb.append("\r\n//查询"+tablecomment+"\r\n");
		sb.append("@RequestMapping(\"/page/{pageIndex}/{pageSize}/select"+initcap(tablename)+".do\")\r\n");
		sb.append("public String select"+initcap(tablename)+"s("+initcap(tablename)+" "+tablename+",@PathVariable Integer pageIndex,@PathVariable Integer pageSize,Model model){\r\n");
		sb.append("	ModelAndView modelAndView = new ModelAndView();\r\n"); 
		sb.append("	int select"+initcap(tablename)+"Count = "+tablename+"service.select"+initcap(tablename)+"sByPOJOCount("+tablename+");\r\n");
		sb.append("	Page page = new Page(pageIndex, pageSize, select"+initcap(tablename)+"Count);\r\n");
		sb.append("	SearchPageUtil spu = new SearchPageUtil();\r\n");
		sb.append("	spu.setPage(page);\r\n");
		sb.append("	model.addAttribute(\"data\", "+tablename+"service.select"+initcap(tablename)+"sByPojoPage(spu, "+tablename+"));\r\n");  
		sb.append("	model.addAttribute(\"page\", page);\r\n"); 
		sb.append("	model.addAttribute(\"s\", 1);\r\n");
		sb.append("	model.addAttribute(\"po\", new "+initcap(tablename)+"());\r\n");
		//sb.append("	model.addAttribute(\"/WEB-INF/page/"+tablename+".jsp\");\r\n");
		sb.append("	return \""+tablename+"\";\r\n");
		sb.append("}\r\n\r\n");
		
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
		
			//new GenController("collection,"+tablecomment+"");

	}

}
