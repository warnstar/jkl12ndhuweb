package com.xingluo.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jeecgframework.core.util.ResourceUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 创建拦截器类，如果未登录则强制到登录界面
 * @author fy
 *
 */
public class CommonInterceptor extends HandlerInterceptorAdapter {

	private List<String> excludeUrls;
	

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}
	
	/**
	 * 在controller后拦截
	 */
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception) throws Exception {
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {

	}
	
	
	/**
	 * 在controller前拦截
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String requestPath = ResourceUtil.getRequestPath(request);// 用户访问的资源地址
		
//		System.out.println(requestPath);
		if (excludeUrls.contains(requestPath)) {
			//如果该请求不在拦截范围内，直接返回true
			return true;
		} 
		String username =  (String)request.getSession().getAttribute("user"); 
		if(username==null){
			forward(request, response);
			return false;
		}else{
				return true;
			}

		}
	
	private void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("/xingluo/admin/xlUserController.do?login");
	}

}
