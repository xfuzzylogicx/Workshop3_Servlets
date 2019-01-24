<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New Google</title>
</head>
<body>

<%@include file="/Header.jsp" %>

<c:forEach items="${solutions}" var="s">
    ${s.id}
</c:forEach>

</body>
</html>
