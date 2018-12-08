package com.ghqkl.schedule;

import java.util.Properties;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.pagehelper.PageHelper;

@SpringBootApplication
@ComponentScan
@EnableTransactionManagement
@MapperScan("com.ghqkl.schedule.dao")	//自动扫描配置文件注解
public class Application {
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
	
	@Bean
	public PageHelper pageHelper() {
		PageHelper pageHelper = new PageHelper();
		Properties properties = new Properties();
		properties.setProperty("offsetAsPageNum", "true");
		properties.setProperty("rowBoundsWithCount", "true");
		properties.setProperty("reasonable", "true");
		properties.setProperty("dialect", "mysql"); // 配置mysql数据库的方言
		pageHelper.setProperties(properties);
		return pageHelper;
	}
	
	 @Bean
	 public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
	    ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
	    scheduler.setPoolSize(20);
	    scheduler.setThreadNamePrefix("task-");
	    scheduler.setAwaitTerminationSeconds(60);
	    scheduler.setWaitForTasksToCompleteOnShutdown(true);
	    scheduler.setRemoveOnCancelPolicy(true);
	     return scheduler;
	 }
}
