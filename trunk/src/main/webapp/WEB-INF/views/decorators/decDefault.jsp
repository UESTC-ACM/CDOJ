<%--
 Default decorator
--%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <page:applyDecorator name="head" page="/WEB-INF/views/common/header.jsp"/>
  <decorator:head/>

  <title><decorator:title/> - UESTC Online Judge</title>
</head>

<body>
	
	<div class="pure-g-r" id="cdoj-layout">
		<div class="pure-u" id="cdoj-navbar">
			<page:applyDecorator name="body" page="/WEB-INF/views/common/navbar.jsp"/>
		</div>
		<div class="pure-u-1" id="mzry1992">
			<decorator:body/>
		</div>
	</div>

</body>
</html>
