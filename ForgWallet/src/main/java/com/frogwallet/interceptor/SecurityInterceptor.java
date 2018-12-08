//package com.frogwallet.interceptor;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.TimeUnit;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.frogwallet.util.JSONUtil;
//import com.frogwallet.util.JsonResult;
//import com.frogwallet.util.MapUtil;
//import com.frogwallet.util.PropertiesFile;
//import com.frogwallet.util.security.ExplosionproofUtil;
//import com.frogwallet.util.security.MD5;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//
//
//
//@Component
//public class SecurityInterceptor implements HandlerInterceptor {
//
//	private Log log = LogFactory.getLog(SecurityInterceptor.class);
//	@Autowired
//	private ValueOperations<String, String> valOpsStr;
//	@Value("${isdebug.type}")
//	private int  isdebug;
//
//
//	public SecurityInterceptor() {
//		// TODO Auto-generated constructor stub
//	}
//
//	private String mappingURL;// 利用正则映射到需要拦截的路径
//
//	public void setMappingURL(String mappingURL) {
//		this.mappingURL = mappingURL;
//	}
//	/**
//	 * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
//	 * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
//	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
//	 */
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//		boolean isExp = ExplosionproofUtil.isExplosionproof(request, valOpsStr);
//		if (isExp) {
//			log.info("暴击请求，5秒后可以访问");
//			response.getWriter().write(JSONUtil
//					.toJson(new JsonResult(3333, false, "Too often request! Cannot access again in 5 seconds!", null)));
//			return false;
//		}
//		if("H5".equals(request.getParameter("device_type"))) {//H5则拒绝请求
//			return false;
//		}
//		log.info("request url:" + request.getRequestURL());
//		String url = request.getRequestURL().toString();
//		Map<String, String> requestParam = getParameterMap(request);
//		requestParam = MapUtil.sortMapByKey(requestParam);
//		log.info("request param:" + JSONUtil.toJson(requestParam));
//		System.out.println(">>>>"+isdebug);
//		if (1==isdebug) {
//			log.info("调试模式，通信不加密");
//			return true;
//		}
//		if (url != null && (url.contains("/heartbeat") || url.contains("/error") || url.contains("/swagger")
//				|| url.contains("/webjars") || url.contains("/api-docs") || url.contains("users/register") || url.contains("users/login")
//				|| url.contains("users/retrievePassword") || url.contains("code/getCode") || url.contains("code/getImgCode"))) {
//			log.info("特殊url，不拦截加密");
//			return true;
//		}
//		//非法请求！没有迹象
//		Object sign = request.getParameter("sign");
//		if (sign == null) {
//			log.info("no sign!");
//			response.getWriter().write(JSONUtil.toJson(new JsonResult(2222, false, "Illegal request! No sign!", null)));
//			return false;
//		}
//		String uid = request.getParameter("userId");
//		String SECRET_KEY = PropertiesFile.getValue("default_secretKey");
//		if (uid != null && !"".equals(uid)) {
//			String token = null;
//			if (valOpsStr.get("userId:" + uid) != null && !"".equals(valOpsStr.get("userId:" + uid))) {
//				token = valOpsStr.get("userId:" + uid);
//			}
//			//非法请求！找不到用户或令牌
//			if (token == null) {
//				log.info("not find user or token!");
//				response.getWriter().write(JSONUtil.toJson(new JsonResult(222, false, "Illegal request! not find user or token!", null)));
//				return false;
//			}
//			SECRET_KEY = token;
//		}
//		String signStr = "";
//		Set<String> keySet = requestParam.keySet();
//		Iterator<String> iter = keySet.iterator();
//		while (iter.hasNext()) {
//			String key = iter.next();
//			if ("sign".equals(key) || "file".equals(key))
//				continue;
//			signStr += key + "=" + requestParam.get(key) + "&";
//		}
//		log.info("sign param:" + signStr + SECRET_KEY);
//		String mySign = MD5.getMD5(signStr + SECRET_KEY);
//		log.info("serverSign:" + mySign + ",clientSign:" + sign.toString());
//		//非法请求
//		if (!sign.equals(mySign)) {
//			log.info("check sign error!");
//			response.getWriter().write(JSONUtil.toJson(new JsonResult(2222, false, "Illegal request!", null)));
//			return false;
//		}
//		return true;
//	}
//	// 在业务处理器处理请求执行完成后,生成视图之前执行的动作
//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//		// TODO Auto-generated method stub
//		//     System.out.println("==============执行顺序: 2、postHandle================");
//	}
//
//	public static Map getParameterMap(HttpServletRequest request) throws IOException {
//		// 参数Map
//		Map properties = request.getParameterMap();
//		// 返回值Map
//		Map returnMap = new HashMap();
//		Iterator entries = properties.entrySet().iterator();
//		Map.Entry entry;
//		String name = "";
//		String value = "";
//		while (entries.hasNext()) {
//			entry = (Map.Entry) entries.next();
//			name = (String) entry.getKey();
//			Object valueObj = entry.getValue();
//			if (null == valueObj) {
//				value = "";
//			} else if (valueObj instanceof String[]) {
//				String[] values = (String[]) valueObj;
//				for (int i = 0; i < values.length; i++) {
//					value = values[i] + ",";
//				}
//				value = value.substring(0, value.length() - 1);
//			} else {
//				value = valueObj.toString();
//			}
//			returnMap.put(name, value);
//		}
//		return returnMap;
//	}
//	/**
//	 * 在DispatcherServlet完全处理完请求后被调用
//	 *
//	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
//	 */
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//		// TODO Auto-generated method stub
//		// System.out.println("==============执行顺序: 3、afterCompletion================");
//	}
//	public static void main(String[] args) {
//		System.out.println(MD5.getMD5("device_type=Android&language=1&userId=79&c01375c7a1071f6a"));
//	}
//}