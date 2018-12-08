package com.example.demo.aop;
  
import org.aspectj.lang.JoinPoint;  
import org.aspectj.lang.ProceedingJoinPoint;  
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;  
import org.springframework.web.context.request.RequestContextHolder;  
import org.springframework.web.context.request.ServletRequestAttributes;


import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

  
/** 
 * Created by wuwf on 17/4/27. 
 * 日志切面 
 */  
@Aspect  
@Component  
public class LogAspect {  
	private static Logger log = LoggerFactory.getLogger(LogAspect.class);
    @Pointcut("execution(public * com.gh.xiangsu.controller.*.*(..))")  
    public void webLog(){}  
  
//    @Before("webLog()")  
//    public void deBefore(JoinPoint joinPoint) throws Throwable {  
//        // 接收到请求，记录请求内容  
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();  
//        HttpServletRequest request = attributes.getRequest();  
//        // 记录下请求内容  
//        log.info("URL : " + request.getRequestURL().toString());  
//        log.info("HTTP_METHOD : " + request.getMethod());  
//        log.info("IP : " + request.getRemoteAddr());  
//        log.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());  
//        log.info("请求参数 : " + Arrays.toString(joinPoint.getArgs()));  
//  
//    }  
//  
//    @AfterReturning(returning = "ret", pointcut = "webLog()")  
//    public void doAfterReturning(Object ret) throws Throwable {  
//        // 处理完请求，返回内容  
//        log.info("方法的返回值 : " + ret);  
//    }  
//  
    //后置异常通知  
    @AfterThrowing("webLog()")  
    public void throwss(JoinPoint jp){  
        log.info("方法异常时执行.....");  
    }  
//  
//    //后置最终通知,final增强，不管是抛出异常或者正常退出都会执行  
//    @After("webLog()")  
//    public void after(JoinPoint jp){  
//        log.info("方法最后执行.....");  
//    }  
  
    //环绕通知,环绕增强，相当于MethodInterceptor  
    @Around("webLog()")  
    public Object arround(ProceedingJoinPoint pjp) throws Throwable {  
    	ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    	 HttpServletRequest request = attributes.getRequest();  
       // 记录下请求内容  
        	String url=request.getRequestURL().toString();
            Object o =  pjp.proceed();  

            if(url.indexOf("heartbeat")==-1) {
            	log.info("URL : {}" ,url ); 
            	log.info("HTTP_METHOD : {}" , request.getMethod());  
            	log.info("IP : {}" + request.getRemoteAddr());  
            	log.info("请求参数:{}",JSONObject.fromObject(LogAspect.getParameterMap(request)));  
            	log.info("响应数据 :{}" , JSONObject.fromObject(o));   
            }
            return o;  
    } 
    public static Map getParameterMap(HttpServletRequest request) throws IOException {
		// 参数Map
		Map properties = request.getParameterMap();
		// 返回值Map
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}
} 