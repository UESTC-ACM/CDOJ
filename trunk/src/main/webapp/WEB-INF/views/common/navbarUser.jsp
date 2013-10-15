<%--
User menu on navbar
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
  <c:choose>
    <c:when test="${sessionScope.currentUser == null}">
      <div class="row">
        <div class="col-md-12">
          <form id="cdoj-login-form" class="pure-form pure-form-aligned">
            <div class="input-group form-group">
              <span class="input-group-addon"><i class="icon-user"></i></span>
              <input type="text" name="userName" maxlength="24" value="" id="userName" class="form-control" placeholder="Username">
            </div>
            <div class="input-group form-group">
              <span class="input-group-addon"><i class="icon-key"></i></span>
              <input type="password" name="password" maxlength="20" id="password" class="form-control" placeholder="Password">
                <span class="input-group-btn">
                  <button type="submit" id="cdoj-login-button" class="btn btn-default">Login</button>
                </span>
            </div>
          </form>
        </div>
        <div class="col-md-12">
          <a href="#" data-toggle="modal" data-target="#cdoj-register-modal">
            <span class="label muted"><i class="icon-circle-arrow-right"></i>Register</span>
          </a>
        </div>
        <div class="col-md-12">
          <a href="#" data-toggle="modal" data-target="#cdoj-activate-modal">
            <span class="label muted"><i class="icon-circle-arrow-right"></i>Forget password?</span>
          </a>
        </div>
      </div>
    </c:when>
    <c:otherwise>
      <div class="row">
        <div class="col-md-6">
          <img id="cdoj-user-avatar" class="img-rounded img-responsive" email="<c:out value="${sessionScope.currentUser.email}"/>" type="avatar"/>
        </div>
        <div class="col-md-6">
          <div id="cdoj-user-menu" class="cdoj-menu">
            <ul class="nav nav-pills nav-stacked">
              <li class="cdoj-menu-heading">
                <span id="currentUser" type="<c:out value="${sessionScope.currentUser.type}"/>">
					        <c:out value="${sessionScope.currentUser.userName}"/>
				        </span>
              </li>
              <li><a href="<c:url value="/admin/index"/>"><i class="icon-wrench"></i>Admin</a></li>
              <li><a href="<c:url value="/user/center/${sessionScope.currentUser.userName}"/>"><i class="icon-home"></i>User center</a></li>
              <li><a href="#" id="cdoj-logout-button"><i class="icon-off"></i>Logout</a></li>
            </ul>
          </div>
        </div>
      </div>
    </c:otherwise>
  </c:choose>
</div>
</body>
</html>
