<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<jsp:include page="../layout/header.jsp"/>

<h1>detail page</h1>
<form action="/board/modify" method="post" enctype="multipart/form-data">
bNo: <input type="text" name="bno" value="${bvo.bno }" readonly="readonly"><br>
title: <input type="text" name="title" value="${bvo.title }"><br>
writer: <input type="text" name="writer" value="${bvo.writer }" readonly="readonly"><br>
content:<br>
<textarea rows="3" cols="30" name="content">${bvo.content }</textarea>
<!-- 파일 추가하는 라인 -->
 <div class="col-12 d-grid">
	<input class="form-control" type="file" style="display: none;"
				 id="files" name="files" multiple>
	<button type="button" id="trigger" class="btn btn-outline-primary btn-block d-block">Files Upload</button>
</div>		
<div class="col-12" id="fileZone">
			
</div>
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
				<button type="button" data-uuid="${fvo.uuid }" class="btn btn-sm btn-danger py-0 file-x">X</button>
			</li>
		</c:forEach>
	</ul>
</div>
<!-- 파일 수정에 따른 등록라인 -->

<button type="submit" id="regBtn">수정</button><br>
<a href="/board/detail?bno=${bvo.bno }"><button type="button">취소</button></a>
</form>
<script type="text/javascript" src="/resources/js/boardRegister.js"></script>

<jsp:include page="../layout/footer.jsp"/>