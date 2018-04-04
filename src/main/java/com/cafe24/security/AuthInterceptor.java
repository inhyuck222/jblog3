package com.cafe24.security;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cafe24.jblog.service.BlogService;
import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.UserVo;
import com.cafe24.security.Auth.Role;

public class AuthInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// 1. Handler 종류 확인
		if(handler instanceof HandlerMethod == false) {
			return true;
		}
		
		// 2. casting
		// HandlerMethod는 Controller에 선언된 Method들의 정보를 갖고있다
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		
		// 3. @Auth 받아오기
		Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
		
		// 4. Method에 @Auth가 없는 경우
		if(auth == null) {
			return true;
		}
		
		if(auth.role() == Role.GUEST) {
			return true;
		}
		
		// 5. @Auth가 붙어 있는 경우, 인증여부 체크
		HttpSession session = request.getSession();
		if(session == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			response.sendRedirect(request.getContextPath() + "/user/login");
			return false;
		}
		
		Map pathVariables = (Map)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		if(pathVariables.size() == 0) {
			response.sendRedirect(request.getContextPath() + "/");
			return false;
		}
		
		String id = (String)pathVariables.get("id");
		WebApplicationContext wc = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		BlogService blogService = wc.getBean(BlogService.class);
		BlogVo thisBlog = blogService.getBlog(id);
		if(thisBlog == null) {
			response.sendRedirect(request.getContextPath() + "/");
			return false;
		}
		if(authUser.getNo() != thisBlog.getUserNo()) {
			response.sendRedirect(request.getContextPath() + "/" + id);
			return false;
		}
		
		// 6. 접근 허가		
		return true;
	}
	
}
