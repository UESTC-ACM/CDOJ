<%--
 Contest list page
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Contest</title>
</head>
<body>
<div id="contest-list"
     ng-controller="ListController"
     ng-init="condition={
        currentPage: null,
        startId: undefined,
        endId: undefined,
        keyword: undefined,
        title: undefined,
        orderFields: 'id',
        orderAsc: 'false'
    };
    requestUrl='/contest/search'">
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
                <legend>Contest Id</legend>
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
                      <label for="title">Title</label>
                      <input type="text"
                             ng-model="condition.title"
                             maxlength="100"
                             id="title"
                             class="form-control input-sm"/>
                    </div>
                  </div>
                </div>
              </fieldset>
              <c:if
                  test="${sessionScope.currentUser != null && sessionScope.currentUser.type == 1}">
                <fieldset>
                  <legend>Is Visible</legend>
                  <div class="row">
                    <div class="col-md-12">
                      <div class="form-group">
                        <label class="radio-inline">
                          <input type="radio"
                                 ng-model="condition.isVisible"
                                 checked=""/> All
                        </label>
                        <label class="radio-inline">
                          <input type="radio"
                                 ng-model="condition.isVisible"
                                 value="true"/> Yes
                        </label>
                        <label class="radio-inline">
                          <input type="radio"
                                 ng-model="condition.isVisible"
                                 value="false"/> No
                        </label>
                      </div>
                    </div>
                  </div>
                </fieldset>
              </c:if>
              <p class="pull-right">
                <button type="button" class="btn btn-danger btn-sm" ng-click="reset()">Reset
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
        <c:choose>
          <c:when
              test="${sessionScope.currentUser != null && sessionScope.currentUser.type == 1}">
            <thead>
            <tr>
              <th style="width: 4em; text-align: right;">#</th>
              <th><a href="/contest/editor/new">Add new contest</a></th>
              <th style="width: 12em; text-align: right;">Start time</th>
              <th style="width: 9em; text-align: right;">Length</th>
              <th style="width: 55px;"></th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="contest in list">
              <td style="text-align: right;" ng-bind="contest.contestId"></td>
              <td>
                <a href="/contest/show/{{contest.contestId}}"
                   target="_blank"
                   ng-bind="contest.title"></a>
              </td>
              <td style="text-align: right;"
                  ui-time
                  time="contest.time">
              </td>
              <td style="text-align: right;"
                  ui-time-length
                  length="contest.length">
              </td>
              <td ui-contest-admin-span
                  contest-id="contest.contestId"
                  is-visible="contest.isVisible"
                  style="padding: 4px;">
              </td>
            </tr>
            </tbody>
          </c:when>
          <c:otherwise>
            <thead>
            <tr>
              <th style="width: 4em; text-align: right;">#</th>
              <th><a href="/contest/editor/new">Add new contest</a></th>
              <th style="width: 12em; text-align: right;">Start time</th>
              <th style="width: 9em; text-align: right;">Length</th>
            </tr>
            </thead>
            <tbody>
            <tr ng-repeat="contest in list">
              <td style="text-align: right;" ng-bind="contest.contestId"></td>
              <td>
                <a href="/contest/show/{{contest.contestId}}"
                   target="_blank"
                   ng-bind="contest.title"></a>
              </td>
              <td style="text-align: right;"
                  ui-time
                  time="contest.time">
              </td>
              <td style="text-align: right;"
                  ui-time-length
                  length="contest.length">
              </td>
            </tr>
            </tbody>
          </c:otherwise>
        </c:choose>
      </table>
    </div>
  </div>
</div>
</body>
</html>