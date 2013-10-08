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
		<c:choose>
		<c:when test="${sessionScope.currentUser == null}">
		<div class="cdoj-login-form">
			<form class="pure-form pure-form-aligned">
				<fieldset>
					<div class="input-group">
						<span class="input-group-addon"><i class="icon-user"></i></span>
						<input type="text" name="userName" maxlength="24" value="" id="userName" class="form-control" placeholder="Username">
					</div>
					<div class="input-group">
						<span class="input-group-addon"><i class="icon-key"></i></span>
						<input type="password" name="password" maxlength="20" id="password" class="form-control" placeholder="Password">
					</div>

					<button class="action blue pull-left"><span class="label">Login</span></button>
					<button class="action green pull-right"><span class="label">Register</span></button>

					<%--
					<div class="pull-right"><span class="label"><i class="icon-question-sign"></i>Forget your password?</span></div>
					--%>
				</fieldset>
			</form>
		</div>
  </c:when>
  <c:otherwise>
    <ul class="nav pull-right">
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
          <!-- TODO src missing? -->
          <img id="userAvatar" email="<c:out value="${sessionScope.currentUser.email}"/>" type="avatar"/>
          <span id="currentUser" type="<c:out value="${sessionScope.currentUser.type}"/>">
            <c:out value="${sessionScope.currentUser.userName}"/>
          </span>
          <b class="caret"></b>
        </a>
        <ul class="dropdown-menu">
          <c:if test="${sessionScope.currentUser.type == 1}">
            <li>
              <a href="<c:url value="/admin/index"/>">
                <i class="icon-lock"></i>
                Admin
              </a>
            </li>
          </c:if>
          <li>
            <a href="<c:url value="/user/center/${sessionScope.currentUser.userName}"/>">
              <i class="icon-home"></i>
              User center
            </a>
          </li>
          <li class="disabled">
            <a href="#">
              <i class="icon-envelope"></i>
              Message
              <span class="badge badge-success">2</span>
            </a>
          </li>
          <li class="disabled">
            <a href="#">
              <i class="icon-folder-open"></i>
              Bookmark
            </a>
          </li>
          <li>
            <a href="#" id="logoutButton">
              <i class="icon-off"></i>
              Logout
            </a>
          </li>
        </ul>
      </li>
    </ul>
  </c:otherwise>
</c:choose>
</body>
</html>
