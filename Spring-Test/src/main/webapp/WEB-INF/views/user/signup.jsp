<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<jsp:include page="../layout/header.jsp"/>

<h1>Sign up Page</h1>
<form action="/member/signup" method="post">
   ID : <input type="text" name="id"><br>
   Password : <input type="password" name="pw"><br>
   age: <input type="number" name="age"><br>
   name: <input type="text" name="name"><br>
   email: <input type="email" name="email"><br>
   home: <input type="text" name="home"><br>
   <button type="submit">sign up</button>
</form>

<jsp:include page="../layout/footer.jsp"/>
	<script type="text/javascript">
		const msg='<c:out value="${msg}"/>';
		if(msg == "0"){
			alert("이미가입된 회원입니다.");
		}
	</script>
</body>
</html>