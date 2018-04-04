<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<h1 class="logo">JBlog</h1>
<ul class="menu">
	<c:if test="${empty authUser }">
		<li><a href="${pageContext.servletContext.contextPath }/user/login">로그인</a></li>
	</c:if>
	<c:if test="${empty authUser}">
		<li><a href="${pageContext.servletContext.contextPath }/user/join">회원가입</a></li>
	</c:if>
	<c:if test="${not empty authUser }">
		<li><a href="${pageContext.servletContext.contextPath }/user/logout">로그아웃</a></li>
	</c:if>
	<c:if test="${not empty authUser}">
		<li><a href="${pageContext.servletContext.contextPath }/${authUser.id }">내블로그</a></li>
	</c:if>
</ul>