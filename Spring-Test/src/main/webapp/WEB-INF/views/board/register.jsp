<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="../layout/header.jsp"></jsp:include>
<form action="/board/register" method="post" enctype="multipart/form-data">
title: <input type="text" name="title"><br>
writer:<input type = "text" name="writer" value="${ses.id }" readonly><br>
content: <textarea rows="3" cols="30" name="content"></textarea>
 <div class="col-12 d-grid">
	<input class="form-control" type="file" style="display: none;"
				 id="files" name="files" multiple>
	<button type="button" id="trigger" class="btn btn-outline-primary btn-block d-block">Files Upload</button>
	</div>		
	<div class="col-12" id="fileZone">
				
	</div>

<button type="submit" id="regBtn">완료</button>
</form>
<script src="/resources/js/boardRegister.js"></script>
<jsp:include page="../layout/footer.jsp"/>
