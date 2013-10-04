<%--suppress ALL --%>
<%--
 Admin user list page
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <script src="<c:url value="/scripts/cdoj/cdoj.admin.user.js"/>"></script>
  <title>User</title>
</head>
<body>
<ul id="TabMenu" class="nav nav-pills">
  <li class="active">
    <a href="#tab-user-list" data-toggle="tab">User list</a>
  </li>
  <li><a href="#tab-user-search" data-toggle="tab">Search</a></li>
</ul>

<div id="TabContent" class="tab-content">
  <div class="tab-pane fade active in" id="tab-user-list">
    <div id="pageInfo">
    </div>

    <table class="table table-striped table-bordered">
      <thead>
      <tr>
        <th style="width: 30px;" class="orderButton" field="id">Id</th>
        <th class="orderButton" field="userName">User name</th>
        <th class="orderButton" field="nickName">Nick name</th>
        <th class="orderButton" field="email">Email</th>
        <th style="width: 90px;" class="orderButton" field="type">Type</th>
        <th class="orderButton" field="lastLogin">Last login</th>
        <th style="width: 14px;"></th>
      </tr>
      </thead>
      <tbody id="userList">
      </tbody>
    </table>
  </div>

  <div class="tab-pane fade" id="tab-user-search">
    <div id="userCondition">
      <form class="form-horizontal">
        <div class="control-group">
          <label class="control-label" for="startId">User ID</label>

          <div class="controls">
            <div class="input-prepend inline">
              <span class="add-on">Form</span>
              <input type="text" name="startId" maxlength="6" value="" id="startId" class="input-small">
            </div>
            <div class="input-prepend">
              <span class="add-on">To</span>
              <input type="text" name="endId" maxlength="6" value="" id="endId" class="input-small">
            </div>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="userName">User name</label>
          <div class="controls">
            <input type="text" name="userName" maxlength="24" value="" id="userName" class="span6">
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="type">Type</label>
          <div class="controls">
            <select name="type" id="type" class="span6">
              <c:forEach var="authenticationType" items="${authenticationTypeList}">
                <option value="${authenticationType.ordinal()}"><c:out value="${authenticationType.description}"/></option>
              </c:forEach>
            </select>
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="school">School</label>
          <div class="controls">
            <input type="text" name="school" maxlength="50" value="" id="school" class="span6">
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="departmentId">Department</label>
          <div class="controls">
            <select name="departmentId" id="departmentId" class="span6">
              <c:forEach var="department" items="${departmentList}">
                <option value="${department.departmentId}"><c:out value="${department.name}"/></option>
              </c:forEach>
            </select>
          </div>
        </div>
        <div class="form-actions">
          <input type="submit" id="search" name="search" value="Search" class="btn btn-primary">
          <input type="submit" id="reset" name="reset" value="Reset" class="btn btn-danger">
        </div>
      </form>
    </div>
  </div>
</div>

<!-- User edit Modal -->
<div id="userEditModal" class="modal hide fade modal-large" tabindex="-1" role="dialog"
     aria-labelledby="userEditModal" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="userEditModalLabel"></h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal">
      <fieldset>
        <div class="control-group "><label class="control-label" for="userId">User ID</label><div class="controls">
          <input type="text" name="userId" maxlength="20" value="" readonly="readonly" id="userId" class="span4"></div>
        </div>
        <div class="control-group "><label class="control-label" for="nickName">Nick name</label><div class="controls">
          <input type="text" name="nickName" maxlength="20" value="" id="nickName" class="span4"></div>
        </div>
        <div class="control-group "><label class="control-label" for="email">Email</label><div class="controls">
          <input type="text" name="email" maxlength="100" value="" readonly="readonly" id="email" class="span4"></div>
        </div>
        <div class="control-group "><label class="control-label" for="school">School</label><div class="controls">
          <input type="text" name="school" maxlength="50" value="" id="school" class="span4"></div>
        </div>
        <div class="control-group "><label class="control-label" for="departmentId">Department</label><div class="controls">
          <select name="departmentId" id="departmentId" class="span4">
            <c:forEach var="department" items="${departmentList}">
              <option value="${department.departmentId}"><c:out value="${department.name}"/></option>
            </c:forEach>
          </select>
        </div>
        </div>
        <div class="control-group "><label class="control-label" for="studentId">Student ID</label><div class="controls">
          <input type="text" name="studentId" maxlength="20" value="" id="studentId" class="span4"></div>
        </div>
        <div class="control-group "><label class="control-label" for="type">Type</label><div class="controls">
          <select name="type" id="type" class="span4">
            <c:forEach var="authenticationType" items="${authenticationTypeList}">
              <option value="${authenticationType.ordinal()}"><c:out value="${authenticationType.description}"/></option>
            </c:forEach>
          </select>
        </div>
        </div>
      </fieldset>
    </form>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal" aria-hidden="true">Cancel</a>
    <a href="#" class="btn btn-primary">Update</a>
  </div>
</div>

</body>
</html>