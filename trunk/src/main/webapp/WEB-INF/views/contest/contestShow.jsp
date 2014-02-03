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
        <tab heading="Problems" active="showProblem">
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
              <button class="btn btn-default">Submit</button>
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
          Status
        </tab>
        <tab heading="Rank">
          Rank
        </tab>
      </tabset>
    </div>
  </div>
</div>
</body>
</html>