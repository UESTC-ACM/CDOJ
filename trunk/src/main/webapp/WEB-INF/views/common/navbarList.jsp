<%--
Nav list on navbar
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
  prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title></title>
</head>
<body>
  <div id="cdoj-navbar-menu" class="row">
    <div class="col-md-12">
      <div class="cdoj-menu">
        <ul class="nav nav-pills nav-stacked">
          <li class="cdoj-menu-heading">CDOJ</li>

          <li id="menu-item-home"><a href="/"><i
              class="fa fa-home"></i>Home</a></li>
          <li id="menu-item-problem"><a
            href="<c:url value="/problem/list"/>"><i
              class="fa fa-puzzle-piece"></i>Problems</a></li>
          <li id="menu-item-contest"><a
            href="<c:url value="/contest/list"/>"><i
              class="fa fa-trophy"></i>Contests</a></li>
          <li id="menu-item-status"><a
            href="<c:url value="/status/list"/>"><i
              class="fa fa-refresh"></i>Status</a></li>
          <li id="menu-item-user"><a
            href="<c:url value="/user/list"/>"><i class="fa fa-user"></i>Users</a></li>
          <c:if
            test="${sessionScope.currentUser != null && sessionScope.currentUser.type == 1}">
            <li id="menu-item-admin"><a
              href="<c:url value="/admin/"/>"><i
                class="fa fa-wrench"></i>Admin</a></li>
          </c:if>
        </ul>
      </div>
      <div class="panel-group" id="cdoj-extend-link">
        <div class="panel panel-default">
          <div class="panel-heading">
            <div class="cdoj-menu">
              <ul class="nav nav-pills nav-stacked">
                <li class="cdoj-menu-heading"><a
                  data-toggle="collapse" data-parent="#cdoj-extend-link"
                  href="#cdoj-links"> Links </a></li>
              </ul>
            </div>
          </div>
          <div id="cdoj-links" class="panel-collapse cdoj-menu">
            <ul class="nav nav-pills nav-stacked">
              <li><a href="/bbs/">BBS</a></li>
              <!-- <li><a href="#">Wiki</a></li>
              <li><a href="#">Training</a></li>
              <li><a href="#">Download</a></li>
              <li><a href="#">Step-by-Step</a></li>
              <li><a href="#">Team Honors</a></li> -->
            </ul>
          </div>
        </div>
        <div class="panel panel-default">
          <div class="panel-heading">
            <div class="cdoj-menu">
              <ul class="nav nav-pills nav-stacked">
                <li class="cdoj-menu-heading"><a
                  data-toggle="collapse" data-parent="#cdoj-extend-link"
                  href="#cdoj-about"> About </a></li>
              </ul>
            </div>
          </div>
          <div id="cdoj-about" class="panel-collapse cdoj-menu">
            <ul class="nav nav-pills nav-stacked">
              <li><a href="<c:url value="/article/show/1"/>">F.A.Q</a></li>
              <!-- <li><a href="<c:url value="/article/show/2"/>">Markdown</a></li> -->
              <!-- <li><a href="<c:url value="/article/show/3"/>">Training
                  system</a></li> -->
              <li><a href="<c:url value="/article/show/4"/>">About</a></li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>

</body>
</html>
