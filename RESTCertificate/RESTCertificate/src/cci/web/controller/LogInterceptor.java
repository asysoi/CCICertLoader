package cci.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LogInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
    	System.out.println("Stsrt preHandler: " + startTime);
		Authentication aut = SecurityContextHolder.getContext()
				.getAuthentication();
		String action = request.getRequestURI().substring(request.getContextPath().length()+1);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
   	    long executeTime = System.currentTimeMillis() - (Long) request.getAttribute("startTime");
   	    System.out.println("After handling: " + executeTime);
		// LOG.info("ExecuteTime of action: " + executeTime + "ms");
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("After complition");
		// super.afterCompletion(request, response, handler, ex);
	}

}