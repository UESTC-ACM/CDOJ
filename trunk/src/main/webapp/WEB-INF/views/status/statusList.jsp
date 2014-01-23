<%--
Admin problem list page
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Status</title>
</head>
<body>
<div id="status-list"
     ng-controller="ListController"
     ng-init="condition={
        currentPage: null,
        startId: undefined,
        endId: undefined,
        userName: undefined,
        problemId: undefined,
        languageId: undefined,
        contestId: undefined,
        result: 'OJ_ALL',
        orderFields: 'statusId',
        orderAsc: 'false'
     };
     requestUrl='/status/search'">
  <div class="row">
    <div class="col-md-12">
      <div ui-page-info
           page-info="pageInfo"
           condition="condition"
           id="page-info">
      </div>
      <div id="advance-search">
        <a href="#" id="advanced" data-toggle="dropdown"><i
            class="fa fa-caret-square-o-down"></i></a>
        <ul class="dropdown-menu cdoj-form-menu" role="menu"
            aria-labelledby="advance-menu">
          <li role="presentation" id="condition">
            <form class="form">
              <fieldset>
                <legend>Status ID</legend>
                <div class="row">
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="startId">Form</label>
                      <input type="number"
                             ng-model="condition.startId"
                             min="1"
                             id="startId"
                             class="form-control input-sm"/>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="endId">To</label>
                      <input type="number"
                             ng-model="condition.endId"
                             min="1"
                             id="endId"
                             class="form-control input-sm"/>
                    </div>
                  </div>
                </div>
              </fieldset>
              <fieldset>
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <label for="userName">User name</label>
                      <input type="text"
                             ng-model="condition.userName"
                             maxlength="24"
                             id="userName"
                             class="form-control input-sm"/>
                    </div>
                  </div>
                  <div class="col-md-12">
                    <div class="form-group">
                      <label for="problemId">Problem ID</label>
                      <input type="number"
                             ng-model="condition.problemId"
                             min="1"
                             id="problemId"
                             class="form-control input-sm"/>
                    </div>
                  </div>
                  <c:if
                      test="${sessionScope.currentUser != null && sessionScope.currentUser.type == 1}">
                    <div class="col-md-12">
                      <div class="form-group">
                        <label for="contestId">Contest ID</label>
                        <input type="number"
                               ng-model="condition.contestId"
                               min="1"
                               id="contestId"
                               class="form-control input-sm"/>
                      </div>
                    </div>
                  </c:if>
                  <div class="col-md-12">
                    <div class="form-group">
                      <label for="keyword">Keyword</label>
                      <input type="text"
                             ng-model="condition.keyword"
                             maxlength="100"
                             id="keyword"
                             class="form-control input-sm"/>
                    </div>
                  </div>
                </div>
              </fieldset>
              <c:if
                  test="${sessionScope.currentUser != null && sessionScope.currentUser.type == 1}">
              <p class="pull-left"
                 ui-rejudge-button
                 condition="condition">
              </p>
              </c:if>
              <p class="pull-right">
                <button type="button" class="btn btn-danger btn-sm"
                        ng-click="reset()">Reset
                </button>
              </p>
            </form>
        </ul>
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col-md-12">
      <table class="table table-condensed">
        <thead>
        <tr>
          <th style="text-align: center;">#</th>
          <th style="text-align: center;">User</th>
          <th style="text-align: center;">Prob</th>
          <th style="width: 19em; text-align: center;">Result <a
              id="status-refresh-button" href="#"> <i
              class="fa fa-refresh"></i>
          </a></th>
          <th style="text-align: center;">Memory</th>
          <th style="text-align: center;">Time</th>
          <th style="text-align: center;">Language</th>
          <th style="text-align: center;">Length</th>
          <th style="width: 11em; text-align: center;">Submit
            Time
          </th>
          <th></th>
        </tr>
        </thead>
        <tbody>
        <tr ng-repeat="status in list">
          <td style="text-align: center;">{{status.statusId}}</td>
          <td style="text-align: center;"><a href="/user/center/{{status.userName}}"
                                             target="_blank">{{status.userName}}</a></td>
          <td style="text-align: center;"><a href="/problem/show/{{status.problemId}}"
                                             target="_blank">{{status.problemId}}</a></td>
          <td style="text-align: center;"
              ui-status
              status="status"
              ng-class="{
                'status-info': [0, 16, 17, 18].some(status.returnTypeId),
                'status-success': [1].some(status.returnTypeId),
                'status-danger': [0, 1, 16, 17, 18].none(status.returnTypeId)
              }">
          </td>

          <td style="text-align: center;">{{status.memoryCost}}
            <span ng-hide="status.memoryCost == undefined"> KB</span>
          </td>
          <td style="text-align: center;">{{status.timeCost}}
            <span ng-hide="status.memoryCost == undefined"> MS</span>
          </td>

          <td style="text-align: center;">{{status.language}}</td>
          <td style="text-align: center;">
          </td>
          <td style="text-align: center;"
              ui-time
              time="status.time"></td>
          <td></td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>

<page:applyDecorator name="body"
                     page="/WEB-INF/views/status/statusModal.jsp"/>

</body>
</html>
