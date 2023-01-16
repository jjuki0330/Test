<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!-- <body onsubmit=getCommentList(${bvo.bno})> -->
<jsp:include page="../layout/header.jsp"/>
<h1>detail page</h1>
bNo: ${bvo.bno }<br>
title: ${bvo.title }<br>
writer: ${bvo.writer }<br>
content: ${bvo.content}<br>
<!-- file 표현라인 -->
<div class="form-group">
	<ul>
		<c:forEach items="${fList }" var="fvo">
			<!-- 파일이 여러 개일 때 반복적으로 li추가 -->
			<li class="list-group-item d-flex justify-content-between align-items-start">
			<c:choose>
				<c:when test="${fvo.file_type>0 }">
					<div>
					<!-- D:...fileupload/2022/12/28/dog.jpg -->
					<!-- D:\_javaweb\_java\fileUpload\2022\12\28 -->
						<img src="/upload/${fn:replace(fvo.save_dir,'\\','/') }/${fvo.uuid}_th_${fvo.file_name}">
					</div>
				</c:when>
				<c:otherwise>
					<div>
						<!-- 파일 모양 아이콘을 넣어서 일반 파일임을 표현 -->
					</div>
				</c:otherwise>
			</c:choose>
				<!-- 파일이름, 등록일, 사이즈 -->
				<div class="ms-2 me-auto">
				<div class="fw-bold">${fvo.file_name }</div>
				${fvo.reg_at }
				</div>
				<span class="badge bg-secondary rounded-pill">${fvo.file_size } Byte</span>
			</li>
		</c:forEach>
	</ul>
</div>
reg_date: ${bvo.registerDate }<br>
read_count: ${bvo.read_count }<br>

<c:if test="${ses.id ne null }">
<a href="/board/modify?bno=${bvo.bno }"><button type="button">수정</button></a><br>
<a href="/board/remove?bno=${bvo.bno }"><button type="button">삭제</button></a>
</c:if>
<a href="/board/list"><button type="button">List</button></a>
<a href="/"><button type="button">HOME</button></a>

<!-- comment line -->

	<div class="container">
		<div class="input-group my-3">
			<span class="input-group-text" id="cmtWriter">${ses.id }</span>
			<input type="text" class="form-control" id="cmtText" placeholder="Test Add Comment ">
			<button class="btn btn-success" id="cmtPostBtn" type="button">Post</button>
		</div>
		<ul class="list-group list-group-flush" id="cmtListArea">
		  <li class="list-group-item d-flex justify-content-between align-items-start">
		    <div class="ms-2 me-auto">
		      <div class="fw-bold">Writer</div>
		      Content for Comment
		    </div>
		    <span class="badge bg-dark rounded-pill">modAt</span>
		  </li>
		</ul>
	</div>
	

	<script type="text/javascript">
	const bnoVal = '<c:out value="${bvo.bno}" />';
	console.log(bnoVal);
	</script>
	<script type="text/javascript" src="/resources/js/boardComment.js"></script>
	<script type="text/javascript">
	getCommentList(bnoVal);
	</script>
<jsp:include page="../layout/footer.jsp"/>