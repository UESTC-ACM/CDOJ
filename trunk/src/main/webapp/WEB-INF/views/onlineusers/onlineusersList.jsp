<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>OnlineUsers</title>
</head>
<body>
<h4>Online Users：${onlineNumber}</h4>
    <c:forEach items="${onlineList}" var="entry" >
      <li><a href="/user/center/${entry}"><c:out value="${entry}"/></a></li>
    </c:forEach>
</body>
</html>
