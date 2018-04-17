<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="header">
	<h1 id="blog-name"><a href="${pageContext.servletContext.contextPath }/${authUser.no }">${blog.title }</a></h1>
	<ul>
		<c:if test="${empty authUser }">
			<li><a href="${pageContext.request.contextPath}/user/login">로그인</a></li>
		</c:if>
		<c:if test="${not empty authUser }">
			<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
		</c:if>
		<!-- blog.userNo와 authUser.no가 같을 경우에 보여주는 로직 추가 -->
		<c:if test="${not empty authUser }">
			<li><a href="${pageContext.request.contextPath}/${id }/admin/basic">블로그 관리</a></li>
		</c:if>
	</ul>
</div>