<%--
Admin problem list page
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Problem</title>
</head>
<body>
<div id="problem-list"
     ng-controller="ListController"
     ng-init="condition={
        currentPage: null,
        startId: undefined,
        endId: undefined,
        title: undefined,
        source: undefined,
        isSpj: undefined,
        startDifficulty: undefined,
        endDifficulty: undefined,
        keyword: undefined,
        orderFields: undefined,
        orderAsc: undefined
    };
    requestUrl='/problem/search'">
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
        <ul ui-dropdown-menu class="dropdown-menu cdoj-form-menu"
            role="menu"
            aria-labelledby="advance-menu">
          <li role="presentation" id="condition">
            <form class="form">
              <fieldset>
                <legend>Problem Id</legend>
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
                <legend>Difficulty</legend>
                <div class="row">
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="startDifficulty">Form</label>
                      <input type="number"
                             ng-model="condition.startDifficulty"
                             min="1"
                             max="5"
                             id="startDifficulty"
                             class="form-control input-sm"/>
                    </div>
                  </div>
                  <div class="col-sm-6">
                    <div class="form-group">
                      <label for="endDifficulty">To</label>
                      <input type="number"
                             ng-model="condition.endDifficulty"
                             min="1"
                             max="5"
                             id="endDifficulty"
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
                  <div class="col-md-12">
                    <div class="form-group">
                      <label for="source">Source</label>
                      <input type="text"
                             ng-model="condition.source"
                             maxlength="100"
                             id="source"
                             class="form-control input-sm"/>
                    </div>
                  </div>
                  <div class="col-md-12">
                    <div class="form-group">
                      <label>Is SPJ</label>
                      <ui-yes-no-radio ng-model="condition.isSpj"></ui-yes-no-radio>
                    </div>
                  </div>
                  <div class="col-md-12" ng-show="$root.isAdmin">
                    <div class="form-group">
                      <label>Is Visible</label>
                      <ui-yes-no-radio ng-model="condition.isVisible"></ui-yes-no-radio>
                    </div>
                  </div>
                </div>
              </fieldset>
              <p class="pull-right">
                <button type="button" class="btn btn-danger btn-sm" ng-click="reset()">Reset
                </button>
              </p>
            </form>
          </li>
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
          <th><a href="/problem/editor/new">Add new
            problem</a></th>
          <th style="width: 5em; text-align: right;">Solved</th>
          <th style="width: 55px;" ng-show="$root.isAdmin"></th>
        </tr>
        </thead>
        <tbody>
        <tr ng-repeat="problem in list">
          <td style="text-align: right;" ng-bind="problem.problemId"></td>
          <td>
            <a href="/problem/show/{{problem.problemId}}"
               ng-bind="problem.title"></a>
            <small>&nbsp- <span ng-bind="problem.source"></span></small>
          </td>
          <td ng-class="{
                panelAC: data.status == AuthorStatusType.PASS,
                panelWA: data.status == AuthorStatusType.FAIL
                         }" style="text-align: right;">
            <a href="/status/list?problemId={{problem.problemId}}">x
              <span ng-bind="problem.solved"></span>
            </a>
          </td>
          <td ui-problem-admin-span
              problem-id="problem.problemId"
              is-visible="problem.isVisible"
              ng-show="$root.isAdmin"
              style="padding: 4px;">
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</div>

</body>
</html>
