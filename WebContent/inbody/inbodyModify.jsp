<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="conPath" value="${pageContext.request.contextPath }" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="${conPath}/css/main.css" rel="stylesheet" type="text/css">
	<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
	<script>
		$(function(){
			
		});
	</script>
</head>
<body>
	<c:if test="${empty member }">
		<script>
			location.href='${conPath}/loginView.do?next=inbodyModifyView.do';
		</script>
	</c:if>
	<jsp:include page="../main/header.jsp"/>
	<div id="content">
		<form action="inbodyModify.do" method="get">
			<table>
				<caption>${modifyInbody}</caption>
				<tr>
					<th>몸무게(kg)</th><th>체지방률(kg)</th><th>골격근량(kg)</th><th>날짜</th>
				</tr>
				<tr>
					<td>
						<input type="number" name="iweight" value="${inbody.iweight }">
					</td>
					<td>${inbody.ifat }kg</td>
					<td>${inbody.imuscle }kg</td>
					<td>${inbody.idate }</td>
				</tr>
				<tr>
					<td colspan="5">
						<input type="button" value="수정완료" class="btn" onclick="location.href='${conPath}/inbodyModify.do'">
						<input type="button" value="수정취소" class="btn" onclick="location.href='${conPath}/inbodyList.do'">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<jsp:include page="../main/footer.jsp"/>	
</body>
</html>