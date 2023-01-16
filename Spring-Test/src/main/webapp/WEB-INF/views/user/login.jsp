<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    
<jsp:include page="../layout/header.jsp"/>

<h1>Log in Page</h1>
<form action="/member/login" method="post">
   ID : <input type="text" name="id"><br>
   Password : <input type="password" name="pw"><br>
   <button type="submit">Log in</button>
</form>

<jsp:include page="../layout/footer.jsp"/>
<script type="text/javascript">
const msg='<c:out value="${msg}"/>';
console.log(msg);
if(msg==="0"){
	alert("로그인 실패!!!");
}
</script>