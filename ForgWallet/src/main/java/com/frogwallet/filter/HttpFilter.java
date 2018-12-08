package com.frogwallet.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by: Yumira.
 * Created on: 2018/8/22-下午4:41.
 * Description:
 */
@Component
public class HttpFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //设置允许跨域的配置
        // 这里填写你允许进行跨域的主机ip（正式上线时可以动态配置具体允许的域名和IP）
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许的访问方法
        response.setHeader("Access-Control-Allow-Methods","POST, GET, PUT, OPTIONS, DELETE, PATCH");
        // Access-Control-Max-Age 用于 CORS 相关配置的缓存
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers","token,Origin, X-Requested-With, Content-Type, Accept");

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
//        String token = req.getHeader("token");//header方式 token校验

        //白名单校验
        //如果使用localhost请求，拿到的是ipv6格式的ip地址  如 0:0:0:0:0:0:0:1
        //如果需要精确匹配path，用request.getServletPath()
        // TODO: 2018/12/8 正式版本，如果有必要再配置白名单
//        String realIP = getRealIP(request);
//        LogUtil.info(HttpFilter.class,"requestIp:"+realIP+" requestPath:"+request.getServletPath());
//        if (realIP.startsWith("192.168.0.")
//                || ArrayUtil.contains(App.WRITE_IP,realIP)) {
            filterChain.doFilter(request, response);
//            return;
//        }
//        response.getOutputStream().write(JsonUtil.toJSONString(ResultGenerator.genFailResult("身份校验失败，请求地址不合法")).getBytes());
    }

    @Override
    public void destroy() {

    }


    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr()的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值
     */
    public static String getRealIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
//            LogUtil.info(HttpFilter.class,"Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
//            LogUtil.info(HttpFilter.class,"WL-Proxy-Client-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
//            LogUtil.info(HttpFilter.class,"HTTP_CLIENT_IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//            LogUtil.info(HttpFilter.class,"HTTP_X_FORWARDED_FOR ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
//            LogUtil.info(HttpFilter.class,"X-Real-IP ip: " + ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();//如果使用localhost请求本地，拿到的是ipv6格式的ip地址  如 0.0.0.0.0.0.0.1
//            LogUtil.info(HttpFilter.class,"getRemoteAddr ip: " + ip);
        }
        return ip==null ? "" : ip;
    }
    
}

