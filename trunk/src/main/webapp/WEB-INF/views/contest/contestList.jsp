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
        <ul ui-dropdown-menu class="dropdown-menu cdoj-form-menu" role="menu"
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
              <div class="col-md-12" ng-show="$root.isAdmin">
                <div class="form-group">
                  <label>Is Visible</label>
                  <ui-yes-no-radio ng-model="condition.isVisible"></ui-yes-no-radio>
                </div>
              </div>
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
        <thead>
        <tr>
          <th style="width: 4em; text-align: right;">#</th>
          <th><a href="/contest/editor/new">Add new contest</a></th>
          <th style="width: 12em; text-align: right;">Start time</th>
          <th style="width: 9em; text-align: right;">Length</th>
          <th style="width: 55px;" ng-show="$root.isAdmin"></th>
        </tr>
        </thead>
        <tbody>
        <tr ng-repeat="contest in list">
          <td style="text-align: right;" ng-bind="contest.contestId"></td>
          <td>
            <a href="/contest/show/{{contest.contestId}}"
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
              style="padding: 4px;"
              ng-show="$root.isAdmin">
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>
</body>
</html>