<%--
Nav list on navbar
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title></title>
</head>
<body>
<div id="cdoj-navbar-menu" class="row">
  <div class="col-md-12">
    <div class="cdoj-menu">
      <ul class="nav nav-pills nav-stacked">
        <li class="cdoj-menu-heading"><span class="cdoj-menu-item">CDOJ</span></li>

        <li id="menu-item-home"><a href="/"><i
            class="fa fa-home"></i><span class="cdoj-menu-item">Home</span></a></li>
        <li id="menu-item-problem"><a
            href="<c:url value="/problem/list"/>"><i
            class="fa fa-puzzle-piece"></i><span class="cdoj-menu-item">Problems</span></a></li>
        <li id="menu-item-contest"><a
            href="<c:url value="/contest/list"/>"><i
            class="fa fa-trophy"></i><span class="cdoj-menu-item">Contests</span></a></li>
        <li id="menu-item-status"><a
            href="<c:url value="/status/list"/>"><i
            class="fa fa-refresh"></i><span class="cdoj-menu-item">Status</span></a></li>
        <li id="menu-item-user"><a
            href="<c:url value="/user/list"/>"><i class="fa fa-user"></i><span
            class="cdoj-menu-item">Users</span></a></li>
        <li><a
            href="<c:url value="/bbs/"/>"><i class="fa fa-comments"></i><span
            class="cdoj-menu-item">BBS</span></a></li>
        <c:if
            test="${sessionScope.currentUser != null && sessionScope.currentUser.type == 1}">
          <li id="menu-item-admin"><a
              href="<c:url value="/admin/"/>"><i
              class="fa fa-wrench"></i><span class="cdoj-menu-item">Admin</span></a></li>
        </c:if>
      </ul>
    </div>
  </div>
</div>

</body>
</html>
