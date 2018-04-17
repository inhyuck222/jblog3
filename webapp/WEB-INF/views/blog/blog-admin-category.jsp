<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js" type="text/javascript"></script>
</head>

<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/blog-header.jsp"></c:import>
		<div id="wrapper">
			<div id="content" class="full-screen">
			
				<ul class="admin-menu">
					<li><a href="${pageContext.request.contextPath }/${authUser.id }/admin/basic">기본설정</a></li>
					<li class="selected"><a href="${pageContext.request.contextPath }/${authUser.id }/admin/category">카테고리</a></li>
					<li><a href="${pageContext.request.contextPath }/${authUser.id }/admin/write">글작성</a></li>
				</ul>
		      	<table id='category-table'class="admin-cat">
		      		<tr id='category-table-header'>
		      			<th>번호</th>
			      		<th>카테고리명</th>
			      		<th>포스트수</th>
			      		<th>설명</th>
			      		<td>삭제</td>
		      		</tr>
		      		
	      			<c:set var="count" value="${fn:length(list) }"/>
	      			<c:forEach items="${list }" var="category" varStatus="status">
	      				<tr id='categories'>
		      				<td id='category-num'>${count - status.index }</td>
		      				<td>${category.name }</td>
		      				<td>${category.postNo }</td>
		      				<td>${category.description }</td>
		      				<td data-no="${category.no }">
		      					<a href="#" id="delete-category" ><img src="${pageContext.request.contextPath}/assets/images/delete.jpg"></a>		      					
		      				</td>
		      			</tr>
	      			</c:forEach>
				</table>

      			<h4 class="n-c">새로운 카테고리 추가</h4>
		      	<table id="admin-cat-add">
		      		<tr>
		      			<td class="t">카테고리명</td>
		      			<td><input id="new-category-name" type="text" name="name"></td>
		      		</tr>
		      		<tr>
		      			<td class="t">설명</td>
		      			<td><input id="new-category-description" type="text" name="description"></td>
		      		</tr>
		      		<tr>
		      			<td class="s">&nbsp;</td>
		      			<td><input id="btn-add-category" type="submit" value="카테고리 추가"></td>
		      		</tr>      		      		
		      	</table>
		      	
			</div>
		</div>
		<div id="footer">
			<p>
				<strong>Spring 이야기</strong> is powered by JBlog (c)2016
			</p>
		</div>
	</div>
</body>

<script>

$(document).ready(function(){
	
	$(document).on("click", "#delete-category", function(){
		var th = $(this).closest('th');
		var no = th.data('no');
		console.log(no);
		$.ajax({
			url: "${pageContext.servletContext.contextPath }/api/category/delete", 
			type: "post", 
			data: {no : no}, 
			dataType: "json", 
			success: function(response) {
				console.log(response);
				if(response.result != "success"){
					console.log(response.message);
					return;
				}
				var tr = th.closest("tr");
				tr.remove();
				
				var length = $("#category-table tr").length;
				
				$("#category-table #category-num").each(function(index){
					$(this).text(length - index - 1);
				});
				
			}
		});
	});
});

</script>

<script>

$(function(){
	$("#btn-add-category").click(function(){
		var name = $("#new-category-name").val();
		var description = $("#new-category-description").val();
		var blogNo = "${blog.no }";
		
		if(name == "" || description == ""){
			return;
		}
		
		$.ajax({
			url: "${pageContext.servletContext.contextPath }/api/category/add",			
			type: "post", 
			data: {name : name, description : description, blogNo : blogNo }, 
			dataType: "json", 
			success: function(response){
				console.log(response);
				if(response.result != "success"){
					console.log(response.message);
					return;
				}
				var element = document.createElement('tr');
				var html = 
      				'<th>' + response.data.length + '</th>' + 
	      			'<th>' + response.data[0].name + '</th>' + 
	      			'<th>' + response.data[0].postNo + '</th>' + 
	      			'<th>' + response.data[0].description + '</th>' + 
	      			'<th data-no="' + response.data[0].no + '">' + 
	      				'<a href="#" id="delete-category" >' + 
	      					'<img src="${pageContext.request.contextPath}/assets/images/delete.jpg">' +  
	      				'</a>' + 
		      		'</th>';
	      			
      			
      			$(element).html(html);
      			$(element).insertAfter($("#category-table-header"));
      			
				var length = $("#category-table tr").length;
				
				$("#category-table #category-num").each(function(index){
					$(this).text(length - index - 1);
				});

      			$('#new-category-name').val("");
      			$('#new-category-description').val("");
			}
		});
	});
})

</script>

</html>