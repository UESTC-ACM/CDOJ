<%--
Navbar
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title></title>
	</head>
	<body>
		<div id="cdoj-user">
			<page:applyDecorator name="body" page="/WEB-INF/views/common/navbarUser.jsp"/>
		</div>
		<div id="cdoj-menu">
			<page:applyDecorator name="body" page="/WEB-INF/views/admin/common/navbarList.jsp"/>
		</div>
	</body>
</html>
