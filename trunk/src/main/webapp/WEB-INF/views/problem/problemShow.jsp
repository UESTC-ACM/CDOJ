<%--
Problem statement
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib prefix='fn' uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>{{$root.title}}</title>
</head>
<body>

<div id="problem-show"
     ng-controller="ProblemController"
     ng-init="problemId=${problemId};">
  <div class="row">

    <div class="col-md-12">
      <h1>
        <span ng-bind="problem.title"></span>
        <sup ng-show="problem.isSpj">
          <span class="label label-danger tags"
                style="margin: 0 0 0 8px; font-size: 16px;">SPJ</span>
        </sup>
      </h1>
    </div>
    <div class="col-md-12" ng-show="$root.isAdmin">
      <a href="/problem/editor/{{problem.problemId}}">
        <i class="fa fa-pencil no-margin-right"></i> Edit
      </a>
    </div>

    <div class="col-md-12">
      <div id="problem-show-tab" style="margin: 16px 0;">
        <ul class="nav nav-pills">
          <li class="active"><a href="#tab-problem-show" data-toggle="tab">Problem</a></li>
          <li><a href="#tab-problem-submit" data-toggle="tab">Submit</a></li>
          <li><a href="#tab-problem-status" data-toggle="tab">Status</a></li>
          <li class="disabled"><a href="#">Discus</a></li>
        </ul>
      </div>
    </div>

    <div class="tab-content">
      <div class="tab-pane active" id="tab-problem-show">

        <div class="col-md-12">
          <dl class="dl-horizontal">
            <dt>Time limit</dt>
            <dd>
              <span ng-bind="problem.javaTimeLimit"></span> / <span
                ng-bind="problem.timeLimit"></span> ms (Java / others)
            </dd>
            <dt>Memory limit</dt>
            <dd>
              <span ng-bind="problem.javaMemoryLimit"></span> / <span
                ng-bind="problem.memoryLimit"></span> kb (Java / others)
            </dd>
            <dt>Total accepted</dt>
            <dd>
              <span ng-bind="problem.solved"></span>
            </dd>
            <dt>Total submissions</dt>
            <dd>
              <span ng-bind="problem.tried"></span>
            </dd>
          </dl>
        </div>

        <div class="col-md-12" ui-markdown content="problem.description">
        </div>
        <div class="col-md-12">
          <h2>Input</h2>

          <div ui-markdown content="problem.input">
          </div>
        </div>
        <div class="col-md-12">
          <h2>Output</h2>

          <div ui-markdown content="problem.output">
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
              <pre class="sample" ng-bind="problem.sampleInput">
              </pre>
              </td>
              <td>
              <pre class="sample" ng-bind="problem.sampleOutput">
              </pre>
              </td>
            </tr>
            </tbody>
          </table>
        </div>
        <div class="col-md-12" ng-show="problem.hint != ''">
          <h2>Hint</h2>

          <div ui-markdown content="problem.hint">
          </div>
        </div>
        <div class="col-md-12" ng-show="problem.source != ''">
          <h2>Source</h2>

          <div ng-bind="problem.source">
          </div>
        </div>
      </div>

      <div class="tab-pane" id="tab-problem-submit">
        <div class="col-md-12">
          <div class="panel panel-default">
            <div class="panel-body">
              <textarea class="cdoj-submit-area"
                        ng-model="submitDTO.codeContent"></textarea>
              <ui-validate-info value="fieldInfo" for="codeContent"></ui-validate-info>
            </div>
            <div class="panel-footer">
              <div class="btn-group">
                <c:forEach var="language" items="${languageList}">
                  <button type="button" class="btn btn-default"
                          ng-model="submitDTO.languageId"
                          btn-radio="${language.languageId}">${language.name}</button>
                </c:forEach>
              </div>
              <button type="button"
                      class="btn btn-danger pull-right"
                      ng-click="submitCode()">Submit
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <%--
    <c:if test="${currentUser != null}">
    </c:if>

    <div class="tab-pane" id="tab-problem-status">
      <div class="col-md-12">
        <div class="media">

          <div class="pull-left">
            <table class="table table-bordered">
              </tbody>
              <c:forEach var="statistic" items="${problemStatistic}">
                <tr>
                  <td style="width: 180px; text-align: right;">${statistic.key.description}</td>
                  <td style="width: 70px;"><c:choose>
                    <c:when test="${statistic.value == 0}">
                      ${statistic.value}
                    </c:when>
                    <c:otherwise>
                      <a target="_blank"
                         href="/status/list?problemId=${targetProblem.problemId}&result=${statistic.key}">
                          ${statistic.value} </a>
                    </c:otherwise>
                  </c:choose></td>
                </tr>
              </c:forEach>
              </tbody>
            </table>
          </div>
          <div class="media-body" id="cdoj-best-solutions">
            <div class="row">
              <div class="col-md-12">
                <table class="table">
                  <thead>
                  <tr>
                    <th style="text-align: center;">#</th>
                    <th style="text-align: center;">User</th>
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
                  <tbody id="list-container">
                  </tbody>
                </table>
              </div>
            </div>
            <div class="row">
              <div class="col-md-12">
                <div id="page-info"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="tab-pane" id="tab-problem-discus"></div>
  </div>
  </div>
  </div>
                         --%>
</div>
</body>
</html>

