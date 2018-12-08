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

/** 生成Jsp类工具 */
public class GenJsp {
//	private String zhujie = "@Controller";// 类注解
//	private String importpackage = 	"\r\nimport org.springframework.beans.factory.annotation.Autowired; "+
//									"\r\nimport org.springframework.stereotype.Controller;               ";
	private ArrayList<String> colNames = new ArrayList<String>(); // 列名数组
	private ArrayList<String> colTypes = new ArrayList<String>();; // 列名类型数组
	private ArrayList<String> colComments = new ArrayList<String>(); // 列名注释数组
//	private boolean f_util = true; // 是否需要导入包java.util.*
//	private boolean f_sql = false; // 是否需要导入包java.sql.*
	String tablename = "";
	String tablecomment = "";

	/* 构造函数 */
	public GenJsp(String tablenames) {
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
				System.out.println("路径：" + directory.getAbsolutePath() + "/src/main/webapp/WEB-INF/page/" + tablename + ".jsp");
				File jspFile = new File( directory.getAbsolutePath() + "/src/main/webapp/WEB-INF/page/");
				if(!jspFile.exists()){
					jspFile.mkdirs();
				}
				FileWriter fw = new FileWriter(directory.getAbsolutePath() + "/src/main/webapp/WEB-INF/page/" + tablename + ".jsp");
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

	/** 功能：生成Jsp主体代码
	 * @param colnames
	 * @param colTypes
	 * @param colSizes
	 * @return */
	private String parse() {
		StringBuffer sb = new StringBuffer();
		sb.append("<%@ taglib prefix=\"c\" uri=\"http://java.sun.com/jsp/jstl/core\"%>                                                                                                                                 \r\n");
		sb.append("<%@ page language=\"java\" contentType=\"text/html; charset=utf-8\" pageEncoding=\"utf-8\"%>                                                                                                        \r\n");
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">                                                                                          \r\n");
		sb.append("<html>                                                                                                                                                                                              \r\n");
		sb.append("<head>                                                                                                                                                                                              \r\n");
		sb.append("<title>${po.comment}管理</title>                                                                                                                                                                    \r\n");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">                                                                                                                             \r\n");
		sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">                                                                                                                          \r\n");
		sb.append("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge\">                                                                                                                                           \r\n");
		sb.append("<link href=\"http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.css\" rel=\"stylesheet\">                                                                                                     \r\n");
		sb.append("<link href=\"http://apps.bdimg.com/libs/bootstrap-glyphicons/1.0/css/bootstrap-glyphicons.css\" rel=\"stylesheet\">                                                                                 \r\n");
		sb.append("</head>                                                                                                                                                                                             \r\n");
		sb.append("<body>                                                                                                                                                                                              \r\n");
		sb.append("		<jsp:include page=\"header.jsp\"></jsp:include>    \r\n");
		sb.append("<div class=\"container-fluid\">                                                                                                                                                                                              \r\n");
		sb.append("	<div class=\"row\">                                                                                                                                                                                              \r\n");
		sb.append("		<div class=\"col-sm-2\">                                                                                                                                                                                              \r\n");
		sb.append("		<jsp:include page=\"catalog.jsp\"></jsp:include>                                                                                                                                                                                              \r\n");
		sb.append("		</div>                                                                                                                                                                                              \r\n");
		
		sb.append("		<div class=\"col-sm-10\">                                                                                                                                                                                              \r\n");
		sb.append("	<H3 class=\" col-sm-12\">${po.comment}管理:</H3>                                                                                                                                    \r\n");
		sb.append("	<div class=\"col-sm-12\">                                                                                                                                                          \r\n");
		sb.append("		<form id=\""+initcap(tablename)+"Form\" method=\"post\">                                                                                                                                                                   \r\n");
		sb.append("			<input type=\"hidden\" name=\"id\" id=\"id\" value=\"${po.id}\" />                                                                                                                         \r\n");
		sb.append("			<c:forEach var=\"entry\" items=\"${po.commentMap}\" varStatus=\"status\">                                                                                                                  \r\n");
		sb.append("				<div class=\"form-group\">                                                                                                                                                             \r\n");
		sb.append("					<label for=\"inputEmail3\" class=\"col-sm-1 control-label text-right\" style=\"padding: 0px;\">${entry.value.comment}:</label>                                                             \r\n");
		sb.append("					<div class=\"col-sm-2\">                                                                                                                                                           \r\n");
		sb.append("						<input type=\"text\" class=\"form-control\" style=\"padding: 0px; width: 100%; margin-bottom: 10px;\" name=\"${entry.key}\" id=\"${entry.key}\" placeholder=\"${entry.value.comment}\">\r\n");
		sb.append("					</div>                                                                                                                                                                             \r\n");
		sb.append("				</div>                                                                                                                                                                                 \r\n");
		sb.append("			</c:forEach>                                                                                                                                                                               \r\n");
		sb.append("			<div class=\"form-group\">                                                                                                                                                                 \r\n");
		sb.append("				<div class=\"col-sm-offset-9 col-sm-1 text-right\">                                                                                                                                    \r\n");
		sb.append("					<button id=\"add"+initcap(tablename)+"\" type=\"button\" class=\"btn btn-default\">增加</button>                                                                                               \r\n");
		sb.append("				</div>                                                                                                                                                                                 \r\n");
		sb.append("			</div>                                                                                                                                                                                     \r\n");
		sb.append("			<div class=\"form-group\">                                                                                                                                                                 \r\n");
		sb.append("				<div class=\"col-sm-1 text-right\">                                                                                                                                                    \r\n");
		sb.append("					<button id=\"select"+initcap(tablename)+"\" type=\"button\" class=\"btn btn-default\">查询</button>                                                                                            \r\n");
		sb.append("				</div>                                                                                                                                                                                 \r\n");
		sb.append("			</div>                                                                                                                                                                                     \r\n");
		sb.append("			<div class=\"form-group\">                                                                                                                                                                 \r\n");
		sb.append("				<div class=\"col-sm-1 text-right\">                                                                                                                                                    \r\n");
		sb.append("					<button id=\"update"+initcap(tablename)+"\" type=\"button\" class=\"btn btn-default\">修改</button>                                                                                            \r\n");
		sb.append("				</div>                                                                                                                                                                                 \r\n");
		sb.append("			</div>                                                                                                                                                                                     \r\n");
		sb.append("		</form>                                                                                                                                                                                        \r\n");
		sb.append("	</div>                                                                                                                                                                                             \r\n");
        sb.append("                                                                                                                                                                                                    \r\n");
        sb.append("                                                                                                                                                                                                    \r\n");
		sb.append("	<div class=\"col-sm-12\">查询结果：</div>                                                                                                                                          \r\n");
		sb.append("	<div class=\"col-sm-12\">                                                                                                                                                          \r\n");
		sb.append("		<table class=\"table table-striped table-bordered\">                                                                                                                                           \r\n");
		sb.append("			<tr>                                                                                                                                                                                       \r\n");
		sb.append("				<c:forEach var=\"entry\" items=\"${po.commentMap}\" varStatus=\"status\">                                                                                                              \r\n");
		sb.append("					<c:if test=\"${status.first}\">                                                                                                                                                    \r\n");
		sb.append("						<td align=\"center\">编号</td>                                                                                                                                                 \r\n");
		sb.append("					</c:if>                                                                                                                                                                            \r\n");
		sb.append("					<td align=\"center\">${entry.value.comment}</td>                                                                                                                                           \r\n");
		sb.append("					<c:if test=\"${status.last}\">                                                                                                                                                     \r\n");
		sb.append("						<td align=\"center\">删除</td>                                                                                                                                                 \r\n");
		sb.append("					</c:if>                                                                                                                                                                            \r\n");
		sb.append("				</c:forEach>                                                                                                                                                                           \r\n");
		sb.append("			</tr>                                                                                                                                                                                      \r\n");
        sb.append("                                                                                                                                                                                                    \r\n");
		sb.append("			<c:forEach var=\"pojo\" items=\"${data}\" varStatus=\"postatus\">                                                                                                                          \r\n");
		sb.append("				<tr>                                                                                                                                                                                   \r\n");
		sb.append("					<c:forEach var=\"entry\" items=\"${po.commentMap}\" varStatus=\"status\">                                                                                                          \r\n");
		sb.append("						<c:if test=\"${status.first}\">                                                                                                                                                \r\n");
		sb.append("							<td align=\"center\" id=\""+initcap(tablename)+"data!!!${postatus.count}!!!no\">${postatus.count}</td>                                                                    \r\n");
		sb.append("						</c:if>                                                                                                                                                                        \r\n");
		sb.append("						<c:set var='entrykey' value=\"${entry.key}\" scope=\"page\" />                                                                                                                 \r\n");
		sb.append("						<td align=\"center\" id=\""+initcap(tablename)+"data!!!${postatus.count}!!!${entrykey}\">${pojo[entrykey]}</td>                                                               \r\n");
		sb.append("						<c:if test=\"${status.last}\">                                                                                                                                                 \r\n");
		sb.append("							<td align=\"center\"><span id=\"del"+initcap(tablename)+"!!!${postatus.count}!!!${pojo.id}\" class=\"glyphicon glyphicon-remove\" style=\"cursor: pointer;\"></span></td>              \r\n");
		sb.append("						</c:if>                                                                                                                                                                        \r\n");
		sb.append("					</c:forEach>                                                                                                                                                                       \r\n");
		sb.append("				</tr>                                                                                                                                                                                  \r\n");
		sb.append("			</c:forEach>                                                                                                                                                                               \r\n");
		sb.append("		</table>                                                                                                                                                                                       \r\n");
		sb.append("	</div> \r\n");                            
		
		sb.append("	<div class=\"col-sm-12\">                                                                                                 \r\n");
		sb.append("	<c:if test=\"${page.pageTotal>1}\">                                                                                       \r\n");
		sb.append("		<nav>                                                                                                                 \r\n");
		sb.append("		  <ul class=\"pagination\">                                                                                           \r\n");
		sb.append("		  <c:if test=\"${page.pageIndex>1}\">                                                                                 \r\n");
		sb.append("		    <li>                                                                                                              \r\n");
		sb.append("		      <a href=\"javascript:gotoPrePage()\" aria-label=\"Previous\">                                                   \r\n");
		sb.append("		        <span aria-hidden=\"true\">&laquo;</span>                                                                     \r\n");
		sb.append("		      </a>                                                                                                            \r\n");
		sb.append("		    </li>                                                                                                             \r\n");
		sb.append("		    </c:if>                                                                                                           \r\n");
		sb.append("		    <c:forEach var=\"i\" begin=\"${(page.pageIndex-5)<2?1:(page.pageIndex-5)}\" end=\"${page.pageIndex+5}\">          \r\n");
		sb.append("		    	<c:if test=\"${i>0 && i<=page.pageTotal}\">                                                                   \r\n");
		sb.append("	            <li><a href=\"javascript:gotoPage(${i})\">${i}</a></li>                                                       \r\n");
		sb.append("	            </c:if>                                                                                                       \r\n");
		sb.append("	        </c:forEach>                                                                                                      \r\n");
		sb.append("		    <c:if test=\"${page.pageIndex<page.pageTotal}\">                                                                  \r\n");
		sb.append("		    <li>                                                                                                              \r\n");
		sb.append("		      <a href=\"javascript:gotoNextPage()\" aria-label=\"Next\">                                                      \r\n");
		sb.append("		        <span aria-hidden=\"true\">&raquo;</span>                                                                     \r\n");
		sb.append("		      </a>                                                                                                            \r\n");
		sb.append("		    </li>                                                                                                             \r\n");
		sb.append("		    </c:if>                                                                                                           \r\n");
		sb.append("		  </ul>                                                                                                               \r\n");
		sb.append("		</nav>                                                                                                                \r\n");
		sb.append("		</c:if>                                                                                                               \r\n");
		sb.append("	</div>                                                                                                                    \r\n");
		sb.append("		</div>                                                                                                                                                                                              \r\n");
		sb.append("	</div>                                                                                                                                                                                              \r\n");
		sb.append("</div>                                                                                                                                                                                              \r\n");
		sb.append("		<jsp:include page=\"footer.jsp\"></jsp:include>    \r\n");
		sb.append("</body>                                                                                                                                                                                             \r\n");
		sb.append("<script src=\"http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js\"></script>                                                                                                                     \r\n");
		sb.append("<script src=\"http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js\"></script>                                                                                                            \r\n");
		sb.append("<script src=\"http://apps.bdimg.com/libs/angular.js/1.3.9/angular.min.js\"></script>                                                                                                                \r\n");
		sb.append("<script type=\"text/javascript\">                                                                                                                                                                   \r\n");
		sb.append("	var pageIndex = ${page.pageIndex}; \r\n");
		sb.append("	var pageSize = ${page.pageSize};    \r\n");
		sb.append("$(document).ready(function() {                                                                                                                                                                      \r\n");
		sb.append("    $(\"#add"+initcap(tablename)+"\").click(function() {                                                                                                                                                        \r\n");
		sb.append("        $(\"#"+initcap(tablename)+"Form\").attr(\"action\", \"${pageContext.request.contextPath}/page/\"+pageIndex+\"/\"+pageSize+\"/add"+initcap(tablename)+".do\");                                                                                                       \r\n");
		sb.append("        $(\"#"+initcap(tablename)+"Form\").submit();                                                                                                                                                            \r\n");
		sb.append("    });                                                                                                                                                                                             \r\n");
        sb.append("                                                                                                                                                                                                    \r\n");
		sb.append("    $(\"span[id^='del"+initcap(tablename)+"!!!']\").click(function() {                                                                                                                                          \r\n");
		sb.append("    	var id = $(this).attr('id').replace(/^del"+initcap(tablename)+"!!!\\d+!!!/,'');\r\n");
		sb.append("    	window.location.href = \"${pageContext.request.contextPath}/page/\"+id+\"/\"+pageIndex+\"/\"+pageSize+\"/del"+initcap(tablename)+".do\";\r\n");
		sb.append("    });                                                                                                                                                                                             \r\n");
		sb.append("                                                                                                                                                                                                    \r\n");
		sb.append("    $(\"td[id^='"+initcap(tablename)+"data!!!']\").click(function() {                                                                                                                                         \r\n");
		sb.append("    	var n = $(this).attr(\"id\").replace(/^"+initcap(tablename)+"data!!!(\\d+)!!![^!]+$/,\"$1\");                                                                                                               \r\n");
		sb.append("    	$(\"td[id^='"+initcap(tablename)+"data!!!\"+n+\"!!!']\").each(function(){                                                                                                                                \r\n");
		sb.append("    		var s = $(this).attr(\"id\").replace(/^"+initcap(tablename)+"data!!!\\d+!!!([^!]+)$/,\"$1\");                                                                                                           \r\n");
		sb.append("    		$(\"#"+initcap(tablename)+"Form\").find(\"input[name='\"+s+\"']\").val($(this).text());                                                                                                                \r\n");
		sb.append("    	});                                                                                                                                                                                            \r\n");
		sb.append("    	$(\"#"+initcap(tablename)+"Form\").find(\"input[id='id']\").val($(\"span[id^='del"+initcap(tablename)+"!!!\"+n+\"!!!']\").attr(\"id\").replace(/^del"+initcap(tablename)+"!!!\\d+!!!/,\"\"));                                       \r\n");
		sb.append("    });                                                                                                                                                                                             \r\n");
		sb.append("                                                                                                                                                                                                    \r\n");
		sb.append("    $(\"#update"+initcap(tablename)+"\").click(function() {                                                                                                                                                     \r\n");
		sb.append("        $(\"#"+initcap(tablename)+"Form\").attr(\"action\", \"${pageContext.request.contextPath}/page/\"+pageIndex+\"/\"+pageSize+\"/update"+initcap(tablename)+".do\");                                                                                                    \r\n");
		sb.append("        $(\"#"+initcap(tablename)+"Form\").submit();                                                                                                                                                            \r\n");
		sb.append("    });                                                                                                                                                                                             \r\n");
		sb.append("                                                                                                                                                                                                    \r\n");
		sb.append("    $(\"#select"+initcap(tablename)+"\").click(function() {                                                                                                                                                     \r\n");
		sb.append("        $(\"#"+initcap(tablename)+"Form\").attr(\"action\", \"${pageContext.request.contextPath}/page/\"+pageIndex+\"/\"+pageSize+\"/select"+initcap(tablename)+".do\");                                                                                                    \r\n");
		sb.append("        $(\"#"+initcap(tablename)+"Form\").submit();                                                                                                                                                            \r\n");
		sb.append("    });                                                                                                                                                                                             \r\n");
		sb.append("});                                                                                                                                                                                                 \r\n");
		sb.append(" function gotoPage(pindex){                                                                                                    \r\n");
		sb.append(" 	pageIndex = pindex;                                                                                                       \r\n");
		sb.append("     $(\"#"+initcap(tablename)+"Form\").attr(\"action\", \"${pageContext.request.contextPath}/page/\"+pageIndex+\"/\"+pageSize+\"/select"+initcap(tablename)+".do\");      \r\n");                                                                                                
		sb.append("     $(\"#"+initcap(tablename)+"Form\").submit();                                                                                          \r\n");
		sb.append(" }                                                                                                                             \r\n");
		sb.append(" function gotoPrePage(){                                                                                                       \r\n");
		sb.append(" 	pageIndex--;                                                                                                              \r\n");
		sb.append("     $(\"#"+initcap(tablename)+"Form\").attr(\"action\", \"${pageContext.request.contextPath}/page/\"+pageIndex+\"/\"+pageSize+\"/select"+initcap(tablename)+".do\");      \r\n");                                                                                                
		sb.append("     $(\"#"+initcap(tablename)+"Form\").submit();                                                                                          \r\n");
		sb.append(" }                                                                                                                             \r\n");
		sb.append(" function gotoNextPage(pindex,psize){                                                                                          \r\n");
		sb.append(" 	pageIndex++;                                                                                                              \r\n");
		sb.append("     $(\"#"+initcap(tablename)+"Form\").attr(\"action\", \"${pageContext.request.contextPath}/page/\"+pageIndex+\"/\"+pageSize+\"/select"+initcap(tablename)+".do\");      \r\n");                                                                                                
		sb.append("     $(\"#"+initcap(tablename)+"Form\").submit();                                                                                          \r\n");
		sb.append(" }                                                                                                                             \r\n");
		sb.append("</script>                                                                                                          \r\n");
		sb.append("</html>                                                                                                                                                                                             \r\n");

		// System.out.println(sb.toString());
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
		
			new GenJsp("collection,收藏");

	}

}
