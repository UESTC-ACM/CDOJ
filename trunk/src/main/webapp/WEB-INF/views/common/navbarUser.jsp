<%--
 User menu on navbar

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
<ul class="nav pull-right">
  <li class="dropdown">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
      <i class="icon-white icon-pencil"></i>
      Login
      <b class="caret"></b>
    </a>
    <ul class="dropdown-menu">
      <li>
        <!-- Button to trigger modal -->
        <a href="#loginModal" role="button" data-toggle="modal">
          <i class="icon-ok-circle"></i>
          Login
        </a>
      </li>

      <li>
        <!-- Button to trigger modal -->
        <a href="#registerModal" role="button" data-toggle="modal">
          <i class="icon-plus-sign"></i>
          Register
        </a>
      </li>

      <li>
        <a href="#activateModal" role="button" data-toggle="modal">
          <i class="icon-refresh"></i>
          Forget password
        </a>
      </li>
    </ul>
  </li>
</ul>
<%--
    <ul class="nav pull-right">
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                <!-- TODO src missing? -->
                <img id="userAvatar" email="<s:property value="currentUser.email"/>" type="avatar"/>
                <span id="currentUser" type="<s:property value="currentUser.type"/>">
                    <s:property value="currentUser.userName"/>
                </span>
                <b class="caret"></b>
            </a>
            <ul class="dropdown-menu">
                <s:if test="currentUser.type == 1">
                    <li>
                        <s:a action="index" namespace="/admin">
                            <i class="icon-lock"></i>
                            Admin
                        </s:a>
                    </li>
                </s:if>
                <li>
                    <s:a action="center/%{currentUser.userName}" namespace="/user">
                        <i class="icon-home"></i>
                        User center
                    </s:a>
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
--%>
</body>
</html>