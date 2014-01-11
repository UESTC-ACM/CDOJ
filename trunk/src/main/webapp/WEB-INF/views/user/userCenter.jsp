<%--
 User center page
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
  prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title><c:out value="${targetUser.userName}" /></title>
</head>
<body>
  <div id="user-show">
    <div class="row">
      <div class="col-md-12">
        <h1>
          <c:out value="${targetUser.userName}" />
        </h1>
      </div>
      <div class="col-md-12">
        <dl class="dl-horizontal">
          <dt>Nick name</dt>
          <dd>
            <c:out value="${targetUser.nickName}" />
          </dd>
          <dt>School</dt>
          <dd>
            <c:out value="${targetUser.school}" />
          </dd>
          <dt>Department</dt>
          <dd>
            <c:out value="${targetUser.department}" />
          </dd>
          <c:if
            test="${sessionScope.currentUser.userId == targetUser.userId}">
            <dt>Student ID</dt>
            <dd>
              <c:out value="${targetUser.studentId}" />
            </dd>
            <dt>Email</dt>
            <dd>
              <c:out value="${targetUser.email}" />
            </dd>
          </c:if>
          <dt>Motto</dt>
          <dd>
            <c:out value="${targetUser.motto}" />
          </dd>
          <dt>Last login</dt>
          <dd class="cdoj-time" type="milliseconds" isUTC="true">
          <fmt:formatDate value="${targetUser.lastLogin}"
                    type="date" pattern="yyyy-MM-dd HH:mm:ss" />
          </dd>
        </dl>
      </div>
    </div>
  </div>
</body>
</html>
