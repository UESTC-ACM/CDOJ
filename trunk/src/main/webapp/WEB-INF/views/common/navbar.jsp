<%--
 Navbar

 @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
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
<div class="navbar navbar-fixed-top navbar-inverse">
  <div class="navbar-inner">
    <div class="container">
        <page:applyDecorator name="body" page="/WEB-INF/views/common/navbarList.jsp"/>
      <form class="navbar-search pull-right" action="">
        <!-- TODO please check idea warning -->
        <input type="text" class="search-query span2" placeholder="Search">
      </form>

      <page:applyDecorator name="body" page="/WEB-INF/views/common/navbarUser.jsp"/>

    </div>
  </div>
</div>
</body>
</html>