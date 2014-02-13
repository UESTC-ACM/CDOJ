<%--
  Admin contest editor page
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <c:if test="${action eq 'new'}">
    <title>New contest</title>
  </c:if>
  <c:if test="${not (action eq 'edit')}">
    <title>Edit contest - contest${targetContest.contestId}</title>
  </c:if>
</head>
<body>

<div id="contest-editor">
  <div class="row"
       ng-controller="ContestEditorController"
       ng-init="contest={
        action:'${action}',
        contestId:'${targetContest.contestId}',
        title:'${targetContest.title}',
        type:${targetContest.type},
        time:'<fmt:formatDate value="${targetContest.time}"
                    type="date" pattern="yyyy-MM-dd HH:mm" />',
        lengthDays:${targetContest.lengthDays},
        lengthHours:${targetContest.lengthHours},
        lengthMinutes:${targetContest.lengthMinutes},
        description:'<c:out value="${targetContest.description}" escapeXml="true"/>'
       };
       problemList = [
                <c:forEach var="problem" items="${contestProblems}" varStatus="status">
                  {problemId:${problem.problemId}, title:'${problem.title}'}
                  <c:if test="${status.last == false}">,</c:if>
                </c:forEach>
               ];">
    <c:if test="${action eq 'new'}">
      <div class="col-md-12">
        <h1>New contest</h1>
      </div>
    </c:if>
    <c:if test="${not (action eq 'edit')}">
      <div class="col-md-12">
        <h1>Edit contest ${targetContest.contestId}</h1>
      </div>
    </c:if>

    <div class="col-sm-12">
      <div class="form-group">
        <input type="text"
               ng-model="contest.title"
               required
               ng-minlength="1"
               ng-maxlength="50"
               ng-trim="true"
               class="form-control"
               placeholder="Enter title here"/>
      </div>
    </div>

    <div class="col-sm-12">
      <form class="form-horizontal" role="form">
        <div class="form-group">
          <label class="col-sm-2 control-label">
            Type
          </label>

          <div class="col-sm-10">
            <div class="btn-group">
              <button type="button"
                      class="btn btn-default"
                      ng-repeat="contestType in $root.contestTypeList"
                      ng-model="contest.type"
                      btn-radio="contestType.contestTypeId"
                      ng-bind="contestType.description"></button>
            </div>
          </div>
        </div>
        <div class="form-group">
          <label class="col-sm-2 control-label">
            Begin time
          </label>

          <div class="col-sm-10">
            <input type="text"
                   ng-model="contest.time"
                   data-date-format="yyyy-mm-dd hh:ii"
                   class="form-control"
                   ui-datetimepicker
                   style="width: 150px;"/>
          </div>
        </div>

        <div class="form-group">
          <label class="col-sm-2 control-label">
            Length
          </label>

          <div class="col-sm-10">
            <div class="input-group" style="width: 400px;">
              <input type="number"
                     ng-model="contest.lengthDays"
                     class="form-control"
                     min="0"
                     max="30"/>
              <span class="input-group-addon">Days</span>
              <input type="number"
                     ng-model="contest.lengthHours"
                     class="form-control"
                     min="0"
                     max="23"/>
              <span class="input-group-addon">Hours</span>
              <input type="number"
                     ng-model="contest.lengthMinutes"
                     class="form-control"
                     min="0"
                     max="59"/>
              <span class="input-group-addon">Minutes</span>
            </div>
          </div>
        </div>

        <div class="form-group">
          <label class="col-sm-2 control-label">
            Problem list
          </label>

          <div class="col-sm-10">
            <table class="table table-striped table-bordered">
              <thead>
              <tr>
                <th style="width: 14px;">
                  <a href="" ng-click="addProblem()">
                    <i class="fa fa-plus"></i>
                  </a>
                </th>
                <th style="width: 120px;">Id</th>
                <th>Title</th>
              </tr>
              </thead>
              <tbody id="problem-list">
              <tr ng-repeat="problem in problemList">
                <td><a href="" ng-click="removeProblem($index)"><i class="fa fa-times"></i></a></td>
                <td>
                  <input type="text"
                         class="cdoj-input"
                         style="width: 100px;"
                         ng-model="problem.problemId"
                         ng-pattern="/^[0-9]+$/"
                         ng-change="updateProblemTitle(problem)"
                      />
                </td>
                <td ng-bind="problem.title"></td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </form>
    </div>

    <div class="col-md-12">
      <div ng-model="contest.description"
           ui-flandre
           upload-url="/picture/uploadPicture/contest/${action}">
      </div>
    </div>

    <div class="col-md-12 text-center">
      <button type="button" class="btn btn-primary" ng-click="submit()">Submit</button>
    </div>
  </div>
</div>
</body>
</html>