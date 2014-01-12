<%--
User menu on navbar
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
  <header id="cdoj-navbar-top">
    <div class="container">
      <nav>
        <ul class="nav navbar-nav navbar-right">
          <!-- Search -->
          <li><div class="input-group input-group-sm" id="cdoj-search">
              <input type="text" class="form-control">
              <div class="input-group-btn">
                <button type="button" class="btn btn-default"
                  tabindex="-1">
                  <span id="cdoj-search-icon"><i class="fa fa-search"></i></span>
                  <span id="cdoj-search-text">Search</span>
                  </button>
              </div>
            </div></li>
          <!-- User -->
          <li><c:choose>
              <c:when test="${sessionScope.currentUser == null}">
                <a href="#" class="dropdown-toggle"
                  data-toggle="dropdown">Sign in</a>
                <ul class="dropdown-menu cdoj-form-menu">
                  <li>
                    <form id="cdoj-login-form">
                      <div class="input-group form-group input-group-sm">
                        <span class="input-group-addon"><i
                          class="fa fa-user"></i></span> <input type="text"
                          name="userName" maxlength="24" value=""
                          id="userName" class="form-control"
                          placeholder="Username">
                      </div>
                      <div class="input-group form-group input-group-sm">
                        <span class="input-group-addon"><i
                          class="fa fa-key"></i></span> <input type="password"
                          name="password" maxlength="20" id="password"
                          class="form-control" placeholder="Password">
                        <span class="input-group-btn">
                          <button type="submit" id="cdoj-login-button"
                            class="btn btn-default">Login</button>
                        </span>
                      </div>
                    </form>
                  </li>
                  <li role="presentation" class="divider"></li>
                  <li><a href="#" data-toggle="modal"
                    data-target="#cdoj-register-modal"><i
                      class="fa fa-arrow-circle-right"></i>Register </a> <a
                    href="#" data-toggle="modal"
                    data-target="#cdoj-activate-modal"><i
                      class="fa fa-arrow-circle-right"></i>Forget
                      password? </a></li>
                </ul>
              </c:when>
              <c:otherwise>
                <div id="cdoj-user">
                  <img id="cdoj-user-avatar"
                    email="<c:out value="${sessionScope.currentUser.email}"/>"
                    type="avatar" data-toggle="dropdown" />
                  <ul class="dropdown-menu" role="menu"
                    aria-labelledby="user-menu">
                    <li role="presentation"
                      class="dropdown-header text-center"><span
                      id="currentUser"
                      type="<c:out value="${sessionScope.currentUser.type}"/>">
                        <c:out
                          value="${sessionScope.currentUser.userName}" />
                    </span></li>
                    <li role="presentation"><a
                      href="<c:url value="/user/center/${sessionScope.currentUser.userName}"/>"><i
                        class="fa fa-home"></i>User center</a></li>
                    <li role="presentation"><a href="#" data-toggle="modal"
                    data-target="#cdoj-profile-edit-modal"><i class="fa fa-wrench"></i>Edit profile</a></li>
                    <li role="presentation" class="divider"></li>
                    <li role="presentation"><a href="#"
                      id="cdoj-logout-button"><i
                        class="fa fa-power-off"></i>Logout</a></li>
                  </ul>
                </div>
              </c:otherwise>
            </c:choose></li>
        </ul>
      </nav>
    </div>
  </header>
</body>
</html>
