<%--
 User center page
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <!--[if lte IE 8]>
  <script src="<c:url value="/scripts/r2d3.js"/>"></script>
  <![endif]-->
  <!--[if gte IE 9]><!-->
  <script src="<c:url value="/scripts/d3.js"/>"></script>
  <!--<![endif]-->

  <script src="<c:url value="/scripts/cdoj/cdoj.user.center.js"/>"></script>
  <title><c:out value="${targetUser.userName}"/></title>
</head>
<body>
<div id="userInfoWrap" class="row">
  <div id="userInfoLeft" class="span9">
    <div class="row">
      <div class="span9">
        <div id="userInfo">
          <dl class="dl-userInfo">
            <dt>Nick name</dt>
            <dd>
              <c:out value="${targetUser.nickName}"/>
              <c:if test="${sessionScope.currentUser.userId== targetUser.userId}">
                <div class="pull-right" style="margin-right: 20px;">
                  <a href="#userEditModal" role="button" data-toggle="modal">
                    <i class="icon-pencil"></i>
                    Edit Your Profile
                  </a>
                </div>
              </c:if>
            </dd>
            <dt>School</dt>
            <dd><c:out value="${targetUser.school}"/></dd>
            <dt>Department</dt>
            <dd><c:out value="${targetUser.department}"/></dd>
            <c:if test="${sessionScope.currentUser.userId == targetUser.userId}">
              <dt>Student ID</dt>
              <dd><c:out value="${targetUser.studentId}"/></dd>
              <dt>Email</dt>
              <dd><c:out value="${targetUser.email}"/></dd>
            </c:if>
            <dt>Last login</dt>
            <dd class="cdoj-time" type="milliseconds" isUTC="true"><c:out value="${targetUser.lastLogin.time}"/></dd>
          </dl>
        </div>
      </div>
      <div class="span9">
        <div id="userSolvedList">
          <div id="chart">
          </div>
        </div>
      </div>
    </div>

  </div>
  <div id="userInfoRight" class="span3">
    <div id="userInfoSummary">
      <a id="userAvatarWrap"
         class="thumbnail"
         href="#"
         rel="tooltip"
         data-original-title="Change your avatar at gravatar.com">
        <img id="userAvatar-large" email="<c:out value="${targetUser.email}"/>" type="avatar" size="220"/>
      </a>
      <div class="userName-type<c:out value="${targetUser.type}"/>">
        <h4>
          <c:out value="${targetUser.nickName}"/>
        </h4>
        <h4 id="currentUserPageUser" value="<c:out value="${targetUser.userName}"/>">
          <c:out value="${targetUser.userName}"/>
        </h4>
      </div>

      <div>
        <ul class="userStates">
          <li>
            <a href="#"><strong><c:out value="${targetUser.tried}"/></strong>Tried</a>
          </li>
          <li>
            <a href="#"><strong><c:out value="${targetUser.solved}"/></strong>Solved</a>
          </li>
        </ul>
      </div>
    </div>
  </div>
</div>

<c:if test="${sessionScope.currentUser.userId == targetUser.userId}">
  <!-- User edit Modal -->
  <div id="userEditModal" class="modal hide fade modal-large" tabindex="-1" role="dialog"
       aria-labelledby="userEditModal" aria-hidden="true">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
      <h3 id="userEditModalLabel">Edit <c:out value="${targetUser.userName}"/></h3>
    </div>
    <div class="modal-body">
      <form class="form-horizontal">
        <fieldset>
          <div class="control-group "><label class="control-label" for="userId">User ID</label><div class="controls">
            <input type="text" name="userId" maxlength="20" value="<c:out value="${targetUser.userId}"/>" readonly="readonly" id="userId" class="span4"></div>
          </div>

          <div class="control-group "><label class="control-label" for="userName">User Name</label><div class="controls">
            <input type="text" name="userName" maxlength="20" value="<c:out value="${targetUser.userName}"/>" readonly="readonly" id="userName" class="span4"></div>
          </div>

          <div class="control-group "><label class="control-label" for="nickName">Nick name</label><div class="controls">
            <input type="text" name="nickName" maxlength="20" value="<c:out value="${targetUser.nickName}"/>" id="nickName" class="span4"></div>
          </div>

          <div class="control-group "><label class="control-label" for="email">Email</label><div class="controls">
            <input type="text" name="email" maxlength="100" value="<c:out value="${targetUser.email}"/>" readonly="readonly" id="email" class="span4"></div>
          </div>

          <div class="control-group "><label class="control-label" for="school">School</label><div class="controls">
            <input type="text" name="school" maxlength="50" value="<c:out value="${targetUser.school}"/>" id="school" class="span4"></div>
          </div>

          <div class="control-group "><label class="control-label" for="departmentId">Department</label><div class="controls">
            <select name="departmentId" id="departmentId" class="span4">
              <c:forEach var="department" items="${departmentList}">
                <option value="${department.departmentId}"
                    <c:if test="${department.departmentId == targetUser.departmentId}">
                      selected="selected"
                    </c:if>>
                <c:out value="${department.name}"/></option>
              </c:forEach>
            </select>
          </div>
          </div>

          <div class="control-group "><label class="control-label" for="studentId">Student ID</label><div class="controls">
            <input type="text" name="studentId" maxlength="20" value="<c:out value="${targetUser.studentId}"/>" id="studentId" class="span4"></div>
          </div>
          <div class="control-group "><label class="control-label" for="newPassword">New password</label><div class="controls">
            <input type="password" name="newPassword" maxlength="20" id="newPassword" class="span4"></div>
          </div>
          <div class="control-group "><label class="control-label" for="newPasswordRepeat">Repeat new password</label><div class="controls">
            <input type="password" name="newPasswordRepeat" maxlength="20" id="newPasswordRepeat" class="span4"></div>
          </div>
          <div class="control-group "><label class="control-label" for="oldPassword">Your current password</label><div class="controls">
            <input type="password" name="oldPassword" maxlength="20" id="oldPassword" class="span4"></div>
          </div>
        </fieldset>
      </form>
    </div>
    <div class="modal-footer">
      <a href="#" class="btn" data-dismiss="modal" aria-hidden="true">Cancel</a>
      <a href="#" class="btn btn-primary">Update</a>
    </div>
  </div>
</c:if>

</body>
</html>