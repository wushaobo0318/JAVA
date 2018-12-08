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

/** 生成实体类工具 */
public class GenEntity {
	static String tablenames = "collection";
	private String zhujie = "@Component";// 类注解
	private String importpackage = "\r\nimport org.springframework.stereotype.Component;\r\n" + // 要导入的包
			"\r\nimport java.util.*;\r\n";
	private ArrayList<String> colNames = new ArrayList<String>(); // 列名数组
	private ArrayList<String> colTypes = new ArrayList<String>();; // 列名类型数组
	private ArrayList<String> colComments = new ArrayList<String>(); // 列名注释数组
	private boolean f_util = true; // 是否需要导入包java.util.*
	private boolean f_sql = false; // 是否需要导入包java.sql.*
	String tablename = "";
	String tablecomment = "";

	/* 构造函数 */
	public GenEntity(String tablenames) {
		System.out.println("表名" + tablenames);
		String[] split = tablenames.split(",");
		tablename = split[0];
		tablecomment = split[1];

		// 创建连接
		Connection con = null;
		// 查要生成实体类的表
		// String sql = "select * from " + tablename;
		String sql = "select column_name,data_type,column_comment from columns where TABLE_SCHEMA='" + GenCode.DATABASE
				+ "' and table_name = '" + tablename + "'";
		System.out.println("表sql："+sql);
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
				System.out.println(columnname + "\t" + datatype + "\t" + columncomment);
			} // 显示数据

			String content = parse();

			try {
				File directory = new File("");
				File entityFile = new File(
						directory.getAbsolutePath() + "/src/" + GenCode.pojobackage.replace(".", "/") + "/");
				if (!entityFile.exists()) {
					entityFile.mkdirs();
				}
				System.out.println("路径：" + directory.getAbsolutePath() + "/src/" + GenCode.pojobackage.replace(".", "/")
				+ "/" + captureName(initcap(tablename)) + ".java");

				FileWriter fw = new FileWriter(directory.getAbsolutePath() + "/src/"
						+ GenCode.pojobackage.replace(".", "/") + "/" + captureName(initcap(tablename)) + ".java");
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

	/**
	 * 功能：生成实体类主体代码
	 * 
	 * @param colnames
	 * @param colTypes
	 * @param colSizes
	 * @return
	 */
	private String parse() {
		StringBuffer sb = new StringBuffer();
		sb.append("package " + GenCode.pojobackagesub + ";\r\n");
		// 判断是否导入工具包
		if (f_util) {
			sb.append("import java.util.Date;\r\n");
		}
		if (f_sql) {
			sb.append("import java.sql.*;\r\n");
		}
		sb.append(importpackage);
		// 注释部分
		sb.append("   /**\r\n");
		sb.append("    * " + tablename + " 实体类\r\n");
		sb.append("    */ \r\n");
		// 实体部分
		sb.append(zhujie + "\r\n");
		sb.append("public class " + captureName(initcap(tablename)) + "{\r\n");
		sb.append("	public String comment = \"" + tablecomment + "\";\r\n");
		sb.append("	public LinkedHashMap<String, HashMap> commentMap = new LinkedHashMap<String, HashMap>();\r\n");
		
		processAllAttrs(sb);// 属性
//		processConstruct(sb);// 属性
		processAllMethod(sb);// get set方法
		sb.append("}\r\n");
		// System.out.println(sb.toString());
		return sb.toString();
	}

	//
//	/**
//	 * 功能：生成构造方法
//	 * 
//	 * @param sb
//	 */
//	private void processConstruct(StringBuffer sb) {
//		sb.append("	public " + captureName(initcap(tablename)) + "(){ \r\n");
//		sb.append("		super();\r\n");
//		sb.append("		HashMap<String,Object> m = null;\r\n");
//		for (int i = 0; i < colNames.size(); i++) {
//			String string = colNames.get(i);
//			if (string.equalsIgnoreCase("id") || string.equalsIgnoreCase("cdate") || string.equalsIgnoreCase("udate")
//					|| string.equalsIgnoreCase("isdel")) {
//				continue;
//			}
//			String comments = colComments.get(i);
//			String[] commentArr = comments.split(";");
//			for (int j = 0; j < commentArr.length; j++) {
//				if (j == 0) {
////					sb.append("		m = new HashMap<String,Object>();\r\n");
//				}
//				String[] sArr = commentArr[j].split("=");
////				if (sArr.length == 1) {
////					sb.append("		m.put(\"comment\",\"" + commentArr[0] + "\");\r\n");
////				} else {
////					sb.append("		m.put(\"" + sArr[0] + "\",\"" + sArr[1] + "\");\r\n");
////				}
//			}
//			//sb.append("		commentMap.put(\"" + initcap(string) + "\",m);\r\n");
//		}
//		sb.append("	}\r\n");
//
//	}

	/**
	 * 功能：生成所有属性及对应关系
	 * 
	 * @param sb
	 */
	private void processAllAttrs(StringBuffer sb) {

		for (int i = 0; i < colNames.size(); i++) {
			String comments = colComments.get(i);
			System.out.println();
			sb.append("\t//"+comments+"\n");
			sb.append("\tprivate " + sqlType2JavaType(colTypes.get(i)) + " " + initcap(colNames.get(i)));
			if (colNames.get(i).equalsIgnoreCase("isdel")) {
				sb.append(" = \"0\"");
			}
			sb.append(";\r\n\n");

			String[] commentArr = comments.split(";");
			for (int j = 0; j < commentArr.length; j++) {
				String[] sArr = commentArr[j].split("=");
				if (sArr.length == 2 && sArr[0].equalsIgnoreCase("relation")) {
					String string2 = sArr[1];
					if (string2.startsWith("121")) {
						String t = string2.substring(3);
						sb.append("\tprivate	" + initcap(t) + " " + t + "Class;\r\n\r\n");
						sb.append("\tpublic void set" + initcap(t) + "Class(" + initcap(t) + " " + t + "Class){\r\n");
						sb.append("\t\tthis." + t + "Class=" + t + "Class;\r\n");
						sb.append("\t}\r\n\r\n");
						sb.append("\tpublic " + initcap(t) + "  get" + initcap(t) + "Class(){\r\n");
						sb.append("\t\treturn " + t + "Class;\r\n");
						sb.append("\t}\r\n\r\n\n");
					}
				}
			}
		}
	}

	//
	/**
	 * 功能：生成所有方法
	 * 
	 * @param sb
	 */
	private void processAllMethod(StringBuffer sb) {

		for (int i = 0; i < colNames.size(); i++) {
			sb.append("\tpublic void set" + captureName(initcap(colNames.get(i))) + "(" + sqlType2JavaType(colTypes.get(i)) + " "
					+ initcap(colNames.get(i)) + "){\r\n");
			sb.append("\tthis." + initcap(colNames.get(i)) + "=" +initcap(colNames.get(i)) + ";\r\n");
			sb.append("\t}\r\n");
			sb.append("\tpublic " + sqlType2JavaType(colTypes.get(i)) + " get" + captureName(initcap(colNames.get(i))) + "(){\r\n");
			sb.append("\t\treturn " + initcap(colNames.get(i)) + ";\r\n");
			sb.append("\t}\r\n\n");
		}

		//sb.append("\tpublic void setComment(String comment){\r\n");
		//sb.append("\tthis.comment=comment;\r\n");
		//sb.append("\t}\r\n");
		//sb.append("\tpublic String  getComment(){\r\n");
		//sb.append("\t\treturn comment;\r\n");
		//sb.append("\t}\r\n");

//		sb.append("\tpublic void setCommentMap(LinkedHashMap<String, HashMap> commentMap) {\r\n");
//		sb.append("\tthis.commentMap = commentMap;\r\n");
//		sb.append("\t}\r\n");
//		sb.append("\tpublic LinkedHashMap<String, HashMap> getCommentMap() {\r\n");
//		sb.append("\t\treturn commentMap;\r\n");
//		sb.append("\t}\r\n");

	}
	 //首字母大写
    public static String captureName(String name) {
    	if(name.startsWith("cjhj")) {
    		name=name.substring(4);
    		return name;
    	}else if (name.startsWith("xs")) {
    		name=name.substring(2);
    		return name;
    	}
   //     name = name.substring(0, 1).toUpperCase() + name.substring(1);
//        return  name;
    	System.out.println(name);
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
        
    }
	//
	/**
	 * 功能：将输入字符串的首字母改成大写
	 * 
	 * @param str
	 * @return
	 */
	public static String initcap(String str) {
		str = str.trim();
		String[] strs = str.split("_");
		StringBuffer sb = new StringBuffer();
		if(str.startsWith("jg")) {
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
					sb.append(strs[i].toLowerCase());
				}

			}
		}

		return new String(sb.toString());
	}

	/**
	 * 功能：获得列的数据类型
	 * 
	 * @param sqlType
	 * @return
	 */
	private String sqlType2JavaType(String sqlType) {

		if (sqlType.equalsIgnoreCase("bit")) {
			return "Boolean";
		} else if (sqlType.equalsIgnoreCase("tinyint")) {
			return "Integer";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			return "Integer";
		} else if (sqlType.equalsIgnoreCase("int")) {
			return "Integer";
		} else if (sqlType.equalsIgnoreCase("bigint")) {
			return "Long";
		} else if (sqlType.equalsIgnoreCase("float")) {
			return "Float";
		} else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
				|| sqlType.equalsIgnoreCase("real") || sqlType.equalsIgnoreCase("money")
				|| sqlType.equalsIgnoreCase("smallmoney")) {
			return "Double";
		} else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
				|| sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")
				|| sqlType.equalsIgnoreCase("text")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime")) {
			return "Date";
		} else if (sqlType.equalsIgnoreCase("image")) {
			return "Blod";
		} else if (sqlType.equalsIgnoreCase("date")) {
			return "Date";
		} else if (sqlType.equalsIgnoreCase("double")) {
			return "double";
		} else if (sqlType.equalsIgnoreCase("timestamp")) {
			return "Date";
		}else if(sqlType.equalsIgnoreCase("mediumint")) {
			return "Integer";
		}

		return null;
	}

	/**
	 * 出口 TODO
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// new GenEntity("balamainpost,主贴表");
		System.out.println(initcap("admin_id"));
	}

}
