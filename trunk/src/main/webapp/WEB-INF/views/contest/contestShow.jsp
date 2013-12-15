<%--
 Contest page

 @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 @version 1
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
  prefix="decorator"%>
<%@ taglib prefix='fn' uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title><c:out value="${targetContest.title}" /></title>
</head>
<body>

  <div id="contest-show">
    <div id="mzry1992-container">
      <div class="row">
        <div class="col-md-12" id="contest-title"
          value="<c:out value="${targetContest.contestId}"/>">
          <h1>
            <c:out value="${targetContest.title}" />
          </h1>
          <div class="progress progress-striped">
            <div class="progress-bar" id="contest-progress-bar"
              style="width: 10%"></div>
          </div>
        </div>
        <div class="col-md-12">
          <div id="contest-show-tab" style="margin: 16px 0;">
            <ul class="nav nav-pills">
              <li class="active"><a href="#tab-contest-overview"
                data-toggle="tab">Overview</a></li>
              <li class="dropdown"><a class="dropdown-toggle"
                data-toggle="dropdown" href="#"> Problem <span
                  class="caret"></span>
              </a>
                <ul class="dropdown-menu">
                  <c:forEach items="${contestProblems}" var="problem">
                    <li><a role="menuitem" tabindex="-1"
                      href="#tab-contest-problem-<c:out value="${problem.orderCharacter}"/>"
                      data-toggle="tab">Problem
                        ${problem.orderCharacter} - ${problem.title}</a></li>
                  </c:forEach>
                </ul></li>
              <c:if test="${currentUser != null}">
                <li><a href="#tab-contest-submit" data-toggle="tab">Submit</a></li>
              </c:if>
              <c:if test="${currentUser == null}">
                <li class="disabled"><a href="#">Please Login
                    before submit</a></li>
              </c:if>
              <li><a href="#tab-contest-status" data-toggle="tab">Status</a></li>
              <li class="disabled"><a href="#">Rank</a></li>
            </ul>
          </div>
        </div>

        <div class="tab-content">
          <div class="tab-pane active" id="tab-contest-overview">
            <div class="col-md-12">
              <dl class="dl-horizontal">
                <dt>Current time</dt>
                <dd id="contest-current-time"
                  value="<c:out value="${targetContest.currentTime.time}"/>">
                  <fmt:formatDate value="${targetContest.currentTime}"
                    type="date" pattern="yyyy-MM-dd HH:mm:ss" />
                </dd>
                <dt>Start time</dt>
                <dd id="contest-start-time"
                  value="<c:out value="${targetContest.startTime.time}"/>">
                  <fmt:formatDate value="${targetContest.startTime}"
                    type="date" pattern="yyyy-MM-dd HH:mm:ss" />
                </dd>
                <dt>End time</dt>
                <dd id="contest-end-time"
                  value="<c:out value="${targetContest.endTime.time}"/>">
                  <fmt:formatDate value="${targetContest.endTime}"
                    type="date" pattern="yyyy-MM-dd HH:mm:ss" />
                </dd>
                <dt>Contest type</dt>
                <dd id="contest-type"
                  value="<c:out value="${targetContest.type}"/>">
                  <c:out value="${targetContest.typeName}" />
                </dd>
                <dt>Contest status</dt>
                <dd id="contest-status"
                  value="<c:out value="${targetContest.status}"/>">
                  <c:out value="${targetContest.status}" />
                </dd>
              </dl>
            </div>

            <c:if test="${targetContest.status == 'Pending'}">
              <div class="col-md-12">
                <div class="jumbotron">
                  <p>This contest will start in</p>
                  <h1 class="contest-countdown">5:00:00</h1>
                </div>
              </div>
            </c:if>
            <c:if test="${targetContest.status == 'Running'}">
              <div class="col-md-12" id="contest-problem-summary">
                <table class="table table-bordered table-striped">
                  <thead>
                    <tr>
                      <th></th>
                      <th>ID</th>
                      <th>Title</th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach items="${contestProblems}" var="problem">
                      <tr>
                        <td></td>
                        <td>Problem <c:out
                            value="${problem.orderCharacter}" /></td>
                        <td><c:out value="${problem.title}" /></td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </div>
            </c:if>
            <c:if test="${targetContest.status == 'Ended'}">
              <div class="col-md-12" id="contest-problem-summary">
                <table class="table table-bordered table-striped">
                  <thead>
                    <tr>
                      <th></th>
                      <th>ID</th>
                      <th>Source</th>
                      <th>Title</th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach items="${contestProblems}" var="problem">
                      <tr>
                        <td></td>
                        <td>Problem <c:out
                            value="${problem.orderCharacter}" /></td>
                        <td><a
                          href="/problem/show/<c:out value="${problem.problemId}"/>">Prob
                            <i class="fa fa-puzzle-piece"></i> <c:out
                              value="${problem.problemId}" />
                        </a></td>
                        <td><a
                          href="#tab-contest-problem-<c:out value="${problem.orderCharacter}"/>">
                            <c:out value="${problem.title}" />
                        </a></td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </div>
            </c:if>

            <c:if test="${targetContest.description != ''}">
              <div class="col-md-12" type="markdown">
                <textarea>${targetContest.description}</textarea>
              </div>
            </c:if>

          </div>

          <c:forEach items="${contestProblems}" var="targetProblem">
            <div class="tab-pane"
              id="tab-contest-problem-<c:out value="${targetProblem.orderCharacter}"/>">
              <div class="col-md-12" id="problem-title"
                value="<c:out value="${targetProblem.problemId}"/>">
                <h1>
                  <c:out value="${targetProblem.title}" />
                  <c:if test="${targetProblem.isSpj == true}">
                    <sup> <span class="label label-danger tags"
                      style="margin: 0 0 0 8px; font-size: 16px;">SPJ</span>
                    </sup>
                  </c:if>
                </h1>
              </div>
              <div class="col-md-12">
                <dl class="dl-horizontal">
                  <dt>Time limit</dt>
                  <dd>
                    <span>${targetProblem.javaTimeLimit} /
                      ${targetProblem.timeLimit} ms (Java / others)</span>
                  </dd>
                  <dt>Memory limit</dt>
                  <dd>
                    <span>${targetProblem.javaMemoryLimit} /
                      ${targetProblem.memoryLimit} kb (Java / others)</span>
                  </dd>
                  <dt>Total accepted</dt>
                  <dd>
                    <span>${targetProblem.solved}</span>
                  </dd>
                  <dt>Total submissions</dt>
                  <dd>
                    <span>${targetProblem.tried}</span>
                  </dd>
                </dl>
              </div>

              <div class="col-md-12" id="problem-description"
                type="markdown">
                <textarea>${targetProblem.description}</textarea>
              </div>

              <div class="col-md-12">
                <h2>Input</h2>
                <div id="problem-input" type="markdown">
                  <textarea>${targetProblem.input}</textarea>
                </div>
              </div>

              <div class="col-md-12">
                <h2>Output</h2>
                <div id="problem-output" type="markdown">
                  <textarea>${targetProblem.output}</textarea>
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
                        <div class="sample" type="no-prettify">
                          ${fn:replace(targetProblem.sampleInput, brToken, '<br/>')}
                        </div>
                      </td>
                      <td>
                        <div class="sample" type="no-prettify">
                          ${fn:replace(targetProblem.sampleOutput, brToken, '<br/>')}
                        </div>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
              <c:if test="${targetProblem.hint != ''}">
                <div class="col-md-12">
                  <h2>Hint</h2>

                  <div class="" id="problem-hint" type="markdown">
                    <textarea>${targetProblem.hint}</textarea>
                  </div>
                </div>
              </c:if>
              <c:if test="${targetProblem.source != ''}">
                <div class="col-md-12">
                  <h2>Source</h2>

                  <div class="" id="problem-source">
                    ${targetProblem.source}</div>
                </div>
              </c:if>
            </div>
          </c:forEach>

          <c:if test="${currentUser != null}">
            <div class="tab-pane" id="tab-contest-submit">
              <div class="col-md-12">
                <div class="panel panel-default">
                  <div class="panel-body">
                    <form>
                      <textarea class="cdoj-submit-area"
                        id="code-content"></textarea>
                    </form>
                  </div>
                  <div class="panel-footer">
                    <form class="form-inline" role="form">
                      <div class="btn-group" data-toggle="buttons">
                        <c:forEach var="language"
                          items="${languageList}">
                          <label class="btn btn-default"> <input
                            type="radio" name="language"
                            value="<c:out value="${language.languageId}"/>" />
                            <c:out value="${language.name}" />
                          </label>
                        </c:forEach>
                      </div>
                      <div class="form-group">
                        <select class="form-control" id="problem-selector">
                          <c:forEach items="${contestProblems}"
                            var="problem">
                            <option value="${problem.problemId}">Problem
                              ${problem.orderCharacter} -
                              ${problem.title}</option>
                          </c:forEach>
                        </select>
                      </div>
                      <button type="button"
                        class="pull-right btn btn-danger"
                        id="submit-code"
                        data-loading-text="Submitting...">Submit
                      </button>
                    </form>
                  </div>
                </div>
              </div>
            </div>
          </c:if>

          <div class="tab-pane" id="tab-contest-status">
            <div id="contest-status-list">
                <div id="list-container"></div>
            </div>
          </div>

          <div class="tab-pane" id="tab-contest-rank"></div>
        </div>
      </div>
    </div>
  </div>

  <page:applyDecorator name="body"
    page="/WEB-INF/views/status/statusModal.jsp" />

</body>
</html>