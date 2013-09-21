<%--
 Nav list on navbar

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
<ul class="nav">
  <li class="divider-vertical"></li>
  <li>
    <a href="<c:url value="/"/>">CDOJ</a>
  </li>
  <li class="divider-vertical"></li>
  <li>
    <a href="#">Problems</a>
  </li>
  <li class="divider-vertical"></li>
  <li>
    <a href="#">Contests</a>
  </li>
  <li class="divider-vertical"></li>
  <li>
    <a href="#">Status</a>
  </li>
  <li class="divider-vertical"></li>
  <li>
    <a href="<c:url value="/user/list"/>">Users</a>
  </li>
  <li class="divider-vertical"></li>

  <li class="dropdown">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Links<b class="caret"></b></a>
    <ul class="dropdown-menu">
      <li class="disabled"><a href="#">BBS</a></li>
      <li class="disabled"><a href="#">Wiki</a></li>
      <li><a href="#">Training</a></li>
      <li class="disabled"><a href="#">Download</a></li>
      <li class="disabled"><a href="#">Step-by-Step</a></li>
      <li class="disabled"><a href="#">Team Honors</a></li>

      <li class="divider"></li>
      <li class="nav-header">About</li>
      <li><a href="#">F.A.Q</a></li>
      <li><a href="#">Markdown</a></li>
      <li><a href="#">Training system</a></li>
      <li><a href="#">About</a></li>
    </ul>
  </li>
  <li class="divider-vertical"></li>
</ul>

</body>
</html>