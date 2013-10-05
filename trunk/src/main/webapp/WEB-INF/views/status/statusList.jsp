<%--
 Admin problem list page
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <script src="<c:url value="/scripts/cdoj/cdoj.status.js"/>"></script>
  <title>Status</title>
</head>
<body>

<ul id="TabMenu" class="nav nav-pills">
  <li class="active">
    <a href="#tab-status-list" data-toggle="tab">Status</a>
  </li>
  <li><a href="#tab-status-search" data-toggle="tab">Search</a></li>
</ul>

<div id="TabContent" class="tab-content">
  <div class="tab-pane fade active in" id="tab-status-list">
    <div id="pageInfo">
    </div>

    <table class="table table-striped table-bordered">
      <thead>
      <tr>
        <th style="width: 60px;" class="orderButton" field="id">Id</th>
        <th>User</th>
        <th style="width: 60px;" class="orderButton" field="problemByProblemId">Problem</th>
        <th style="width: 260px;" class="orderButton" field="result">Judge's Response</th>
        <th style="width: 100px;" class="orderButton" field="length">Length</th>
        <th style="width: 70px;" class="orderButton" field="timeCost">Time</th>
        <th style="width: 80px;" class="orderButton" field="memoryCost">Memory</th>
        <th style="width: 140px;" class="orderButton" field="time">Submit Time</th>
      </tr>
      </thead>
      <tbody id="statusList">
      </tbody>
    </table>
  </div>

  <div class="tab-pane fade" id="tab-status-search">
    <div id="statusCondition">
      <form class="form-horizontal">
        <div class="control-group">
          <label class="control-label" for="startId">Status ID</label>
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
          <label class="control-label" for="userName">User Name</label>
          <div class="controls">
            <input type="text" name="userName" maxlength="100" value="" id="userName" class="span6">
          </div>
        </div>
        <div class="control-group">
          <label class="control-label" for="problemId">Problem ID</label>
          <div class="controls">
            <input type="text" name="problemId" maxlength="100" value="" id="problemId" class="span6">
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

<page:applyDecorator name="body" page="/WEB-INF/views/status/modal.jsp"/>

</body>
</html>