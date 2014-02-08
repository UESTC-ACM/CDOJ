<%--
 Contest page
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib prefix='fn' uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>{{$root.title}}</title>
</head>
<body>

<div ng-controller="ContestController"
     ng-init="contestId=${contestId};">
<div class="row">
<div class="col-md-12">
  <h1 ng-bind="contest.title" class="text-center"></h1>
</div>

<div class="col-md-12">
<tabset>
<tab heading="Overview">
  <div class="row">
    <div class="col-md-12">
      <dl class="dl-horizontal">
        <dt>Current Time:</dt>
        <dd ui-time
            time="contest.currentTime"
            change="false"
            show="real"></dd>
        <dt>Start Time:</dt>
        <dd ui-time
            time="contest.startTime"
            change="false"
            show="real"></dd>
        <dt>End Time:</dt>
        <dd ui-time
            time="contest.endTime"
            change="false"
            show="real"></dd>
        <dt>Contest Type:</dt>
        <dd ng-bind="contest.typeName"></dd>
        <dt>Contest Status:</dt>
        <dd ng-bind="contest.status"></dd>
      </dl>
    </div>
    <div class="col-md-12">
      <table class="table table-striped table-condensed">
        <thead>
        <tr>
          <th style="width: 12em;"></th>
          <th style="width: 12em;">Id</th>
          <th>Title</th>
        </tr>
        </thead>
        <tbody>
        <tr ng-repeat="problem in problemList">
          <td class="text-right">
            <span ng-bind="problem.solved"></span> / <span ng-bind="problem.tried"></span>
          </td>
          <td>Problem <span ng-bind="problem.orderCharacter"></span></td>
          <td><a href="#" ng-click="chooseProblem(problem.order)"
                 ng-bind="problem.title"></a></td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</tab>
<tab heading="Problems">
  <div class="row">
    <div class="col-md-12 text-center">
      <ul class="pagination" style="margin-bottom: 0;">
        <li ng-repeat="problem in problemList"
            ng-class="{active: currentProblem.order == problem.order}">
          <a href="#" ng-click="chooseProblem(problem.order)"
             ng-bind="problem.orderCharacter"></a>
        </li>
      </ul>
    </div>
    <div class="col-md-12 text-center">
      <h1 style="margin-top: 6px;">
        <span ng-bind="currentProblem.orderCharacter"></span> - <span
          ng-bind="currentProblem.title"></span>
      </h1>
      <h5>
        <span>Time Limit: </span>
        <span ng-bind="currentProblem.javaTimeLimit"></span>
        <span>/</span>
        <span ng-bind="currentProblem.timeLimit"></span>
        <span>MS (Java/Others)</span>
        <span>&nbsp;&nbsp;&nbsp;</span>
        <span>Memory Limit: </span>
        <span ng-bind="currentProblem.javaMemoryLimit"></span>
        <span>/</span>
        <span ng-bind="currentProblem.memoryLimit"></span>
        <span>KB (Java/Others)</span>
      </h5>
    </div>
    <div class="col-md-12 text-center">
      <button class="btn btn-default" ng-click="openSubmitModal()">Submit</button>
      <button class="btn btn-default">Status</button>
      <button class="btn btn-default">Clarification</button>
    </div>
    <div class="col-md-12" ui-markdown content="currentProblem.description">
    </div>
    <div class="col-md-12">
      <h2>Input</h2>

      <div ui-markdown content="currentProblem.input">
      </div>
    </div>
    <div class="col-md-12">
      <h2>Output</h2>

      <div ui-markdown content="currentProblem.output">
      </div>
    </div>
    <div class="col-md-12">
      <h2>Sample input and output</h2>
      <table class="table table-bordered">
        <thead>
        <tr>
          <th style="width: 50%;">Sample Input</th>
          <th style="width: 50%;">Sample Output</th>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td>
              <pre class="sample" ng-bind="currentProblem.sampleInput">
              </pre>
          </td>
          <td>
              <pre class="sample" ng-bind="currentProblem.sampleOutput">
              </pre>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <div class="col-md-12" ng-show="currentProblem.hint != ''">
      <h2>Hint</h2>

      <div ui-markdown content="currentProblem.hint">
      </div>
    </div>
    <div class="col-md-12" ng-show="currentProblem.source != ''">
      <h2>Source</h2>

      <div ng-bind="currentProblem.source">
      </div>
    </div>
  </div>
</tab>
<tab heading="Clarification">
  Clarification
</tab>
<tab heading="Status">
  <div id="status-list"
       ng-controller="ListController"
       ng-init="condition={
        currentPage: null,
        startId: undefined,
        endId: undefined,
        userName: undefined,
        problemId: undefined,
        languageId: undefined,
        contestId: ${contestId},
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
          <ul ui-dropdown-menu class="dropdown-menu cdoj-form-menu" role="menu"
              aria-labelledby="advance-menu">
            <li role="presentation" id="condition">
              <form class="form">
                <fieldset>
                  <div class="row">
                    <div class="col-md-12" ng-show="$root.isAdmin">
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
                    <div class="col-md-12"
                         ng-show="$root.isAdmin">
                      <div class="form-group">
                        <label for="contestId">Contest ID</label>
                        <input type="number"
                               ng-model="condition.contestId"
                               min="1"
                               id="contestId"
                               class="form-control input-sm"/>
                      </div>
                    </div>
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
                <p class="pull-left"
                   ui-rejudge-button
                   ng-show="$root.isAdmin"
                   condition="condition">
                </p>

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
            <th style="text-align: center;" ng-show="$root.isAdmin">User</th>
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
            <td style="text-align: center;" ng-show="$root.isAdmin">
              <a href="/user/center/{{status.userName}}"
                 target="_blank"
                 ng-bind="status.userName"></a>
            </td>
            <td style="text-align: center;">
              <a href="/problem/show/{{status.problemId}}"
                 target="_blank"
                 ng-bind="status.problemId"></a>
            </td>
            <td style="text-align: center;"
                ui-status
                status="status"
                ng-class="{
                'status-info': [0, 16, 17, 18].some(status.returnTypeId),
                'status-success': [1].some(status.returnTypeId),
                'status-danger': [0, 1, 16, 17, 18].none(status.returnTypeId)
              }">
            </td>

            <td style="text-align: center;">
              <span ng-bind="status.memoryCost"></span>
              <span ng-hide="status.memoryCost == undefined"> KB</span>
            </td>
            <td style="text-align: center;">
              <span ng-bind="status.timeCost"></span>
              <span ng-hide="status.memoryCost == undefined"> MS</span>
            </td>

            <td style="text-align: center;" ng-bind="status.language"></td>
            <td style="text-align: center;" ui-code-href status="status">
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

</tab>
<tab heading="Rank">
  Rank
</tab>
</tabset>
</div>
</div>
</div>

<page:applyDecorator name="body"
                     page="/WEB-INF/views/status/statusModal.jsp"/>

<script type="text/ng-template" id="submitModal.html">
  <div class="modal-header">
    <h4 ng-bind="title"></h4>
  </div>
  <div class="modal-body">
    <form>
      <div class="form-group">
        <textarea class="cdoj-submit-area"
                  ng-model="submitDTO.codeContent"></textarea>
        <ui-validate-info value="fieldInfo" for="codeContent"></ui-validate-info>
      </div>
    </form>
    <div class="btn-group">
      <c:forEach var="language" items="${languageList}">

      <button type="button" class="btn btn-default"
              ng-model="submitDTO.languageId"
              btn-radio="${language.languageId}">${language.name}</button>

            </c:forEach>
          </div>
  </div>
  <div class="modal-footer">
    <button class="btn btn-default" ng-click="submit()">Submit</button>
    <button class="btn btn-default" ng-click="close()">Cancel</button>
  </div>
</script>
</body>
</html>