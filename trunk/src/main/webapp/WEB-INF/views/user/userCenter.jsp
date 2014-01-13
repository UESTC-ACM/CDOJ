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
  <div id="cdoj-user-center">
    <div class="row">
      <div class="col-md-12">
        <div class="media" id="cdoj-user-center-summary">
          <a class="pull-left" href="#"> <img
            class="media-object img-thumbnail"
            id="cdoj-user-avatar-large"
            email="<c:out value="${targetUser.email}"/>" type="avatar">
          </a>
          <div class="media-body">
            <h2 class="media-heading">
              <c:out value="${targetUser.userName}" />
            </h2>
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
      <div class="col-md-12">
        <h3>Problems</h3>
        <hr />
        <c:forEach var="item" items="${problemStatus}">
          <span
            <c:choose>
            <c:when test="${item.value == 'NONE'}">
              class="label cdoj-user-status-label label-default"
            </c:when>
            <c:when test="${item.value == 'PASS'}">
              class="label cdoj-user-status-label label-success"
            </c:when>
            <c:otherwise>
              class="label cdoj-user-status-label label-danger"
            </c:otherwise>
          </c:choose>>
            <a href="/problem/show/${item.key}">${item.key}</a></span>
        </c:forEach>
      </div>
    </div>
  </div>
</body>
</html>
