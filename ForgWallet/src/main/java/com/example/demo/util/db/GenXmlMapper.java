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



/** 生成XmlMapper类工具 */
public class GenXmlMapper {
	static String tablenames = "collection";
	
	private ArrayList<String> colNames = new ArrayList<String>(); // 列名数组
	private ArrayList<String> colTypes = new ArrayList<String>();; // 列名类型数组
	private ArrayList<String> colComments = new ArrayList<String>(); // 列名注释数组
//	private boolean f_util = true; // 是否需要导入包java.util.*
//	private boolean f_sql = false; // 是否需要导入包java.sql.*
	String tablename = "";
	String tablecomment = "";
	
	/* 构造函数 */
	public GenXmlMapper(String tablenames) {
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
				System.out.println("路径：" + directory.getAbsolutePath() + "/src/" + GenCode.sql.replace(".", "/") + "/" + GenEntity.initcap(tablename) + "-mapper.xml");
				File xmlFile = new File(directory.getAbsolutePath() + "/src/" + GenCode.sql.replace(".", "/") + "/");
				if(!xmlFile.exists()){
					xmlFile.mkdirs();
				}
				FileWriter fw = new FileWriter(directory.getAbsolutePath() + "/src/" + GenCode.sql.replace(".", "/") + "/" +  GenEntity.captureName(initcap(tablename)) + "-mapper.xml");
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
			// try {
			// con.close();
			// } catch (SQLException e) {
			// e.printStackTrace();
			// }
		}
	}
//	/**
//	 * 生成公用查询 sql
//	 */
//	private String base_col() {
//		StringBuffer sb=new StringBuffer();
//		sb.append("<sql id=\"Base_Column_List\">\t\n");
//		for(int i=0;i<colNames.size();i++){
//			if(i==0) {
//				
//				sb.append(colNames.get(i));
//			}else {
//				sb.append(" , "+colNames.get(i));
//			}
//		}
//		sb.append("</sql>\t\n");
//		return sb.toString();
//	}
	/** 功能：生成XmlMapper代码
	 * @param colnames
	 * @param colTypes
	 * @param colSizes
	 * @return */
	private String parse() {
		System.out.println(colNames);
		StringBuffer sb = new StringBuffer();
		sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\r\n");
		sb.append("\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n");
		sb.append("<mapper namespace=\""+GenCode.daobackagesub+"."+ GenEntity.captureName(initcap(tablename))+"Dao\">\r\n\r\n");
		
		String cols1 = "";
		String cols2 = "";
		String cols3 = "";
		String cols4 = "";
		String cols5 = "";
		String cols6 = "";

		for(int i=0;i<colNames.size();i++){
			if(i==0){ 
				cols1 += "\r\r	<id property  = \""+initcap(colNames.get(i))+"\" column= \""+colNames.get(i)+"\"/>\r\n";
			}else{
				cols1 +="\r\r	<result property  = \""+initcap(colNames.get(i))+"\" column= \""+colNames.get(i)+"\"/>\r\n";
			}
			cols2 +="\r\r	<if test=\""+initcap(colNames.get(i))+" != null and "+initcap(colNames.get(i))+" != '' \"> \r\n and "+colNames.get(i)+" = #{"+initcap(colNames.get(i))+"} \r\n</if>\r\n";
			cols3 +="\r\r	<if test=\"pojo."+initcap(colNames.get(i))+" != null and pojo."+initcap(colNames.get(i))+" != ''\"> \r\n and "+colNames.get(i)+" = #{pojo."+initcap(colNames.get(i))+"} \r\n</if>\r\n";
			
			cols4 += colNames.get(i)+",";
			cols5 += "#{"+initcap(colNames.get(i))+"},";
			if(i>0){
				cols6 +=colNames.get(i)+" = #{"+initcap(colNames.get(i))+"},";
			}
		}
		
		//resultMap
		
		sb.append("\r\r<resultMap type=\""+GenCode.pojobackagesub+"."+ GenEntity.captureName(initcap(tablename))+"\" id=\""+tablename+"s\">\r\n");
		sb.append(cols1);
		sb.append("\r\r</resultMap>\r\n\r\n");
		sb.append("<sql id=\"Base_Column_List\">\t\n");
		for(int i=0;i<colNames.size();i++){
			if(i==0) {
				
				sb.append(colNames.get(i));
			}else {
				sb.append(" , "+colNames.get(i));
			}
		}
		sb.append("\t\n</sql>\t\n");
		sb.append("\r\r<!--添加"+tablename+"-->\r\n");
		sb.append("\r\r <insert id=\"add"+ GenEntity.captureName(initcap(tablename))+"\" parameterType=\""+GenCode.pojobackagesub+"."+ GenEntity.captureName(initcap(tablename))+"\">                                                                                                                      \r\n");
		sb.append("\r\r 		insert into "+tablename+"("+cols4.replaceFirst(",$", "")+") values("+cols5.replaceFirst(",$", "")+")     \r\n");
	    sb.append("\r\r </insert>                                                                                                                                                                                                                        \r\n");
		
	    sb.append("\r\r<!--删除"+tablename+"-->\r\n");
		sb.append("\r\r <delete id=\"del"+ GenEntity.captureName(initcap(tablename))+"True\"  parameterType=\"String\">                                                                                                                                                                       \r\n");
		sb.append(" \r\r    delete from "+tablename+" where "+colNames.get(0)+" = #{"+colNames.get(0)+"}"+"                                                                                                                                                                                          \r\n");
		sb.append(" \r\r</delete>                                                                                                                                                                                                                        \r\n");
		
//		sb.append("\r\r<!--假删除"+tablename+"-->\r\n");
//		sb.append(" <update id=\"del"+initcap(tablename)+"\" parameterType=\"String\">                                                                                                                                                                            \r\n");
//	    sb.append(" 	update  "+tablename+" set isdel='1' where id=#{id}                                                                                                                                                                               \r\n");
//		sb.append(" </update>                                                                                                                                                                                                                        \r\n");
		
		
		sb.append("\r\r<!--修改"+tablename+"-->\r\n");
		sb.append("\r\r <update id=\"update"+ GenEntity.captureName(initcap(tablename))+"\" parameterType=\""+GenCode.pojobackagesub+"."+ GenEntity.captureName(initcap(tablename))+"\">                                                                                                                                           \r\n");
		sb.append("\r\r     update "+tablename+" set "+cols6.replaceFirst(",$", "")+" where "+colNames.get(0)+" = #{"+initcap(colNames.get(0))+"}"+" \r\n");
		sb.append("\r\r </update>                                                                                                                                                                                                                        \r\n");
		
		sb.append("<!--查询根据主键"+tablename+"-->\r\n");
		sb.append(" \r\r<select id=\"select"+ GenEntity.captureName(initcap(tablename))+"ByID\" parameterType=\"String\" resultMap=\""+tablename+"s\">                                                                                                                                       \r\n");
		sb.append("\r\r     select  <include refid=\"Base_Column_List\" /> from "+tablename+" where  "+colNames.get(0)+" = #{"+initcap(colNames.get(0))+"}"+"                                                                                                                                                                                    \r\n");
		sb.append("\r\r </select>                                                                                                                                                                                                                        \r\n");
		
		
		sb.append("<!--查询所有未删除数据的总行数"+tablename+"-->\r\n");
		sb.append(" \r\r<select id=\"select"+ GenEntity.captureName(initcap(tablename))+"Count\" resultType=\"Integer\">                                                                                                                                                                      \r\n");
		////where isdel='0'  
		sb.append("  \r\r   select count('1') from "+tablename+"                                                                                                                                                                              \r\n");
		sb.append(" \r\r</select>                                                                                                                                                                                                                        \r\n");
		
		sb.append("\r\r<!--查询所有数据的行数"+tablename+"-->\r\n");
		sb.append(" \r\r<select id=\"select"+ GenEntity.captureName(initcap(tablename))+"CountAll\" parameterType=\"String\" resultMap=\""+tablename+"s\">                                                                                                                                   \r\n");
		sb.append("     \r\r\r\rselect count('1') from "+tablename+"                                                                                                                                                                                             \r\n");
		sb.append(" \r\r</select>                                                                                                                                                                                                                        \r\n");
		
//		sb.append("\r\r<!--查询所有数据未删除数据的行数"+tablename+"-->\r\n");
//		sb.append(" <select id=\"select"+initcap(tablename)+"sByPage\" parameterType=\"String\" resultMap=\""+tablename+"s\">                                                                                                                                    \r\n");
//		sb.append("     select * from "+tablename+" where isdel = '0' order by cdate desc                                                                                                                                                                \r\n");
//		sb.append(" </select>\r\n");
		
		//select
		sb.append("\r\r<!--查询根据条件"+tablename+"-->\r\n");
		sb.append("\r\r<select id=\"select"+ GenEntity.captureName(initcap(tablename))+"sByPOJO\" parameterType=\""+GenCode.pojobackagesub+"."+ GenEntity.captureName(initcap(tablename))+"\"  resultMap=\""+tablename+"s\">\r\n");
		sb.append("\r\r\r	select \n <include refid=\"Base_Column_List\" /> \n from "+tablename+" where 1=1 \r\n");
		sb.append(cols2);
		sb.append("\r\n");
		sb.append("\r\r\r</select>\r\n");
		
		sb.append("<!--查询根据条件查询的行数"+tablename+"-->\r\n");
		sb.append("\r\r<select id=\"select"+ GenEntity.captureName(initcap(tablename))+"sByPOJOCount\" parameterType=\""+GenCode.pojobackagesub+"."+ GenEntity.captureName(initcap(tablename))+"\"  resultType=\"Integer\">\r\n");
		sb.append("	\r\rselect count('1') from "+tablename+" where 1=1 \r\n");
		sb.append(cols2);
		sb.append("\r\r</select>\r\n");
		
		//select
		sb.append("\r\r<!--查询根据条件分页查询"+tablename+"-->\r\n");
		sb.append("\r\r<select id=\"select"+ GenEntity.captureName(initcap(tablename))+"sByPojoPage\" parameterType=\"java.util.Map\"  resultMap=\""+tablename+"s\">\r\n");
		sb.append("\r\r\r	select <include refid=\"Base_Column_List\" /> from "+tablename+" where 1=1 \r\n");
		sb.append(cols3);
		sb.append("\r\n");
		sb.append(" \r\r\rlimit #{spu.startRow} , #{spu.pageSize}\r\n");
		sb.append("\r\r</select>\r\n");
		
		for(int i=0;i<colNames.size();i++){
			String comments = colComments.get(i);
			String[] commentArr = comments.split(";");
			for (int j = 0; j < commentArr.length; j++) {
				String[] sArr = commentArr[j].split("=");
				if (sArr.length==2&&sArr[0].equalsIgnoreCase("relation")) {
					String string2 = sArr[1];
					if (string2.startsWith("121")) {
						String t = string2.substring(3);
						sb.append("<resultMap type=\""+GenCode.pojobackagesub+"."+ GenEntity.captureName(initcap(tablename))+"\" id=\""+tablename+"sAndClass\">\r\n");
						sb.append(cols1);
						sb.append("	<association property  = \""+ t + "Class\" column=\""+initcap(colNames.get(i))+"\" select=\""+GenCode.pojobackagesub+"."+initcap(t)+"Dao.select"+initcap(t)+"ByID\"/>");
						sb.append("</resultMap>\r\n\r\n");

					}
				}
			}
		}
		
		sb.append("</mapper>\r\n");
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
		if (str.startsWith("gh")) {
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
		new GenXmlMapper("balamainpost,主贴表");
	}

}
