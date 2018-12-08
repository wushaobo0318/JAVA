package com.frogwallet.util.db;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/** 生成Application.xml类工具 */
public class GenApplicationContext {

	/* 构造函数 */
	public GenApplicationContext() {
		String content = parse();
		try {
			File directory = new File("");
			File jspFile = new File(directory.getAbsolutePath() + "/WebRoot/WEB-INF/");
			if (!jspFile.exists()) {
				jspFile.mkdirs();
			}
			FileWriter fw = new FileWriter(directory.getAbsolutePath() + "/WebRoot/WEB-INF/applicationContext.xml");
			PrintWriter pw = new PrintWriter(fw);
			pw.println(content);
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String parse() {
		StringBuffer sb = new StringBuffer();
		sb.append("<beans                                                                                                                      \r\n");
		sb.append("	xmlns=\"http://www.springframework.org/schema/beans\"                                                                      \r\n");
		sb.append("	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"                                                                    \r\n");
		sb.append("	xmlns:context=\"http://www.springframework.org/schema/context\"                                                            \r\n");
		sb.append("	xmlns:mvc=\"http://www.springframework.org/schema/mvc\"                                                                    \r\n");
		sb.append("	xmlns:aop=\"http://www.springframework.org/schema/aop\"                                                                    \r\n");
		sb.append("	xmlns:tx=\"http://www.springframework.org/schema/tx\"                                                                      \r\n");
		sb.append("	xsi:schemaLocation=\"http://www.springframework.org/schema/beans                                                           \r\n");
		sb.append("	http://www.springframework.org/schema/beans/spring-beans-3.2.xsd                                                           \r\n");
		sb.append("	http://www.springframework.org/schema/context                                                                              \r\n");
		sb.append("	http://www.springframework.org/schema/context/spring-context-3.2.xsd                                                       \r\n");
		sb.append("	http://www.springframework.org/schema/mvc                                                                                  \r\n");
		sb.append("	http://www.springframework.org/schema/mvc/spring-mvc-3.2.xsd                                                               \r\n");
		sb.append("	http://www.springframework.org/schema/aop                                                                                  \r\n");
		sb.append("	http://www.springframework.org/schema/aop/spring-aop-3.2.xsd                                                               \r\n");
		sb.append("	http://www.springframework.org/schema/tx                                                                                   \r\n");
		sb.append("	http://www.springframework.org/schema/tx/spring-tx-3.2.xsd                                                                 \r\n");
		sb.append("	\">                                                                                                                        \r\n");
		sb.append("	<context:component-scan base-package=\""+GenCode.bsebackage+"\"></context:component-scan>                                                     \r\n");
		sb.append("	<mvc:annotation-driven>                                                                                                    \r\n");
		sb.append("	    <mvc:message-converters>                                                                                               \r\n");
		sb.append("            <bean class=\"org.springframework.http.converter.json.MappingJacksonHttpMessageConverter\">                     \r\n");
		sb.append("                <property name=\"objectMapper\" ref=\"customObjectMapper\"></property>                                      \r\n");
		sb.append("            </bean>                                                                                                         \r\n");
		sb.append("        </mvc:message-converters>                                                                                           \r\n");
		sb.append("    </mvc:annotation-driven>                                                                                                \r\n");
		sb.append("    <bean id=\"customObjectMapper\" class=\"com.balakitbbs.util.CustomObjectMapper\"></bean>                                \r\n");
		sb.append("                                                                                                                            \r\n");
		sb.append("	<mvc:interceptors>                                                                                                         \r\n");
		sb.append("		<!-- 使用bean定义一个Interceptor，直接定义在mvc:interceptors根下面的Interceptor将拦截所有的请求 -->                        \r\n");
		sb.append("			<bean class=\"com.balakitbbs.intercept.SpringMVCInterceptor\" />                                                   \r\n");
		sb.append("	</mvc:interceptors>                                                                                                        \r\n");
		sb.append("	<bean                                                                                                                      \r\n");
		sb.append("		id=\"ssf\"                                                                                                             \r\n");
		sb.append("		class=\"org.mybatis.spring.SqlSessionFactoryBean\">                                                                    \r\n");
		sb.append("		<!-- mybatis配置文件 -->                                                                                               \r\n");
		sb.append("		<property                                                                                                              \r\n");
		sb.append("			name=\"configLocation\"                                                                                            \r\n");
		sb.append("			value=\"classpath:mybatis-config.xml\" />                                                                          \r\n");
		sb.append("		<property                                                                                                              \r\n");
		sb.append("			name=\"mapperLocations\"                                                                                           \r\n");
		sb.append("			value=\"classpath*:"+GenCode.daobackage.replace(".", "/")+"/*mapper.xml\"></property>                              \r\n");
		sb.append("		<property                                                                                                              \r\n");
		sb.append("			name=\"dataSource\"                                                                                                \r\n");
		sb.append("			ref=\"ds\"></property>                                                                                             \r\n");
		sb.append("	</bean>                                                                                                                    \r\n");
		sb.append("	<bean                                                                                                                      \r\n");
		sb.append("		id=\"ds\"                                                                                                              \r\n");
		sb.append("		class=\"org.springframework.jdbc.datasource.DriverManagerDataSource\">                                                 \r\n");
		sb.append("		<property                                                                                                              \r\n");
		sb.append("			name=\"driverClassName\"                                                                                           \r\n");
		sb.append("			value=\"com.mysql.jdbc.Driver\"></property>                                                                        \r\n");
		sb.append("		<property                                                                                                              \r\n");
		sb.append("			name=\"url\"                                                                                                       \r\n");
		sb.append("			value=\"jdbc:mysql://"+GenCode.databaseurl+":"+GenCode.port+"/"+GenCode.DATABASE+"?useUnicode=true&amp;characterEncoding=UTF-8\"></property>              \r\n");
		sb.append("		<property                                                                                                              \r\n");
		sb.append("			name=\"username\"                                                                                                  \r\n");
		sb.append("			value=\""+GenCode.NAME+"\"></property>                                                                                         \r\n");
		sb.append("		<property                                                                                                              \r\n");
		sb.append("			name=\"password\"                                                                                                  \r\n");
		sb.append("			value=\""+GenCode.PASS+"\"></property>                                                                                             \r\n");
		sb.append("                                                                                                                            \r\n");
		sb.append("	</bean>                                                                                                                    \r\n");
		sb.append("                                                                                                                            \r\n");
		sb.append("	<bean class=\"org.mybatis.spring.mapper.MapperScannerConfigurer\">                                                         \r\n");
		sb.append("		<property                                                                                                              \r\n");
		sb.append("			name=\"basePackage\"                                                                                               \r\n");
		sb.append("			value=\""+GenCode.daobackage+"\" />                                                                                \r\n");
		sb.append("		<property                                                                                                              \r\n");
		sb.append("			name=\"sqlSessionFactoryBeanName\"                                                                                 \r\n");
		sb.append("			value=\"ssf\"></property>                                                                                          \r\n");
		sb.append("	</bean>                                                                                                                    \r\n");
		sb.append("                                                                                                                            \r\n");
		sb.append("	<tx:advice                                                                                                                 \r\n");
		sb.append("		id=\"tran\"                                                                                                            \r\n");
		sb.append("		transaction-manager=\"tm\">                                                                                            \r\n");
		sb.append("		<tx:attributes>                                                                                                        \r\n");
		sb.append("			<tx:method                                                                                                         \r\n");
		sb.append("				name=\"add*\"                                                                                                  \r\n");
		sb.append("				isolation=\"DEFAULT\"                                                                                          \r\n");
		sb.append("				propagation=\"REQUIRED\"                                                                                       \r\n");
		sb.append("				read-only=\"false\"                                                                                            \r\n");
		sb.append("				timeout=\"-1\"                                                                                                 \r\n");
		sb.append("				rollback-for=\"Exception\" />                                               \r\n");
		sb.append("			<tx:method name=\"del*\" />                                                                                        \r\n");
		sb.append("			<tx:method                                                                                                         \r\n");
		sb.append("				name=\"sel*\"                                                                                                  \r\n");
		sb.append("				read-only=\"true\" />                                                                                          \r\n");
		sb.append("			<tx:method                                                                                                         \r\n");
		sb.append("				name=\"update*\"                                                                                               \r\n");
		sb.append("				timeout=\"10\" />                                                                                              \r\n");
		sb.append("		</tx:attributes>                                                                                                       \r\n");
		sb.append("	</tx:advice>                                                                                                               \r\n");
		sb.append("                                                                                                                            \r\n");
		sb.append("	<bean                                                                                                                      \r\n");
		sb.append("		id=\"tm\"                                                                                                              \r\n");
		sb.append("		class=\"org.springframework.jdbc.datasource.DataSourceTransactionManager\">                                            \r\n");
		sb.append("		<property                                                                                                              \r\n");
		sb.append("			name=\"dataSource\"                                                                                                \r\n");
		sb.append("			ref=\"ds\"></property>                                                                                             \r\n");
		sb.append("	</bean>                                                                                                                    \r\n");
		sb.append("                                                                                                                            \r\n");
		sb.append("	<aop:config>                                                                                                               \r\n");
		sb.append("		<aop:pointcut                                                                                                          \r\n");
		sb.append("			expression=\"execution(* "+GenCode.servicebackage+".*.*(..))\"                                                         \r\n");
		sb.append("			id=\"pc\" />                                                                                                       \r\n");
		sb.append("		<aop:advisor                                                                                                           \r\n");
		sb.append("			advice-ref=\"tran\"                                                                                                \r\n");
		sb.append("			pointcut-ref=\"pc\" />                                                                                             \r\n");
		sb.append("	</aop:config>                                                                                                              \r\n");
		sb.append("                                                                                                                            \r\n");
		sb.append("	<bean                                                                                                                      \r\n");
		sb.append("		id=\"multipartResolver\"                                                                                               \r\n");
		sb.append("		class=\"org.springframework.web.multipart.commons.CommonsMultipartResolver\"></bean>                                   \r\n");
		sb.append("                                                                                                                            \r\n");
		sb.append("                                                                                                                            \r\n");
		sb.append("                                                                                                                            \r\n");
		sb.append("	<bean                                                                                                                      \r\n");
		sb.append("		id=\"configProperties\"                                                                                                \r\n");
		sb.append("		class=\"org.springframework.beans.factory.config.PropertiesFactoryBean\">                                              \r\n");
		sb.append("		<property name=\"locations\">                                                                                          \r\n");
		sb.append("			<list>                                                                                                             \r\n");
		sb.append("				<value>classpath*:sysconfig.properties</value>                                                                 \r\n");
		sb.append("			</list>                                                                                                            \r\n");
		sb.append("		</property>                                                                                                            \r\n");
		sb.append("	</bean>                                                                                                                    \r\n");
		sb.append("                                                                                                                            \r\n");
		sb.append("	<bean id=\"propertyConfigurer\" class=\"org.springframework.beans.factory.config.PreferencesPlaceholderConfigurer\">       \r\n");
		sb.append("		<property name=\"properties\" ref=\"configProperties\" />                                                              \r\n");
		sb.append("	</bean>                                                                                                                    \r\n");
		sb.append("                                                                                                                            \r\n");
		sb.append("</beans>                                                                                                                    \r\n");
		return sb.toString();
	}

	/** 出口 TODO
	 * @param args */
	public static void main(String[] args) {
		new GenApplicationContext();

	}

}
