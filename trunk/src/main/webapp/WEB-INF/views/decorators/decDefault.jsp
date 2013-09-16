<%--
 Default decorator

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

  <title><decorator:title/> - UESTC Online Judge</title>
</head>

<body>

<page:applyDecorator name="body" page="/WEB-INF/views/common/navbar.jsp"/>
<page:applyDecorator name="body" page="/WEB-INF/views/common/modal.jsp"/>

<div id="wrap">
  <div class="mzry1992">
    <div class="container">

      <page:applyDecorator name="body" page="/WEB-INF/views/common/debug.jsp"/>

      <decorator:body/>
    </div>
  </div>
</div>

<page:applyDecorator name="body" page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>
