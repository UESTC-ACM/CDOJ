<%--
 Decorator for admin part.

 @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 @version 1
--%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <page:applyDecorator name="head" page="/WEB-INF/views/common/header.jsp"/>
  <decorator:head/>

  <title>Admin - <decorator:title/> - UESTC Online Judge</title>

</head>

<body>

	<div class="pure-g-r" id="cdoj-layout">
		<div class="pure-u" id="cdoj-navbar">
	    <page:applyDecorator name="body" page="/WEB-INF/views/admin/common/navbar.jsp"/>
		</div>
		<div class="pure-u-1" id="mzry1992">
			<decorator:body/>
		</div>
	</div>
</body>
</html>
