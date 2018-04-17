<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<%
	pageContext.setAttribute("newLine", "\n");
%>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/blog-header.jsp"></c:import>
		<div id="wrapper">
			<div id="content">
				<div class="blog-content">
					<h4>${mainPost.title }</h4>
					<p>${fn:replace(mainPost.content, newLine, "<br>") }</p>
					<!--p>${mainPost.content }<p-->
				</div>
				<ul class="blog-list">
					<c:forEach items="${posts }" var="post">
						<li><a href="${pageContext.request.contextPath }/${id }/${post.categoryNo }/${post.no }">${post.title }</a> <span>${post.regDate }</span>	</li>	
					</c:forEach>					
				</ul>
			</div>
		</div>
		
		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath }${blog.logoPath }">
			</div>
		</div>

		<div id="navigation">
			<h2>카테고리</h2>
			<ul>
				<c:forEach items="${categories }" var="category">
					<li><a href="${pageContext.request.contextPath }/${id }/${category.no }">${category.name }</a></li>
				</c:forEach>				
			</ul>
		</div>
		
		<div id="footer">
			<p>
				<strong>Spring 이야기</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>
</html>