<%--
Admin problem editor page
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <c:if test="${action eq 'new'}">
    <title>New problem</title>
  </c:if>
  <c:if test="${not(action eq 'new')}">
    <title>Edit problem - problem${action}</title>
  </c:if>
</head>
<body>
<div id="problem-editor"
     ng-controller="ProblemEditorController"
     ng-init="
       action= '${action}';
     ">
<div class="row">
<c:if test="${action eq 'new'}">
  <div class="col-md-12">
    <h1>New problem</h1>
  </div>
</c:if>
<c:if test="${not(action eq 'new')}">
  <div class="col-md-12">
    <h1>Edit problem ${action}</h1>
  </div>
</c:if>

<div class="form-group">
  <div class="col-sm-12">
    <input type="text"
           ng-model="problem.title"
           ng-require="true"
           ng-maxlength="50"
           class="form-control" placeholder="Enter title here"/>
    <ui-validate-info value="fieldInfo" for="title"></ui-validate-info>
  </div>
</div>

<div class="col-md-12">
  <form class="form-horizontal" role="form" id="problem-data-form">
    <fieldset>
      <div class="col-lg-6">
        <div class="form-group">
          <label for="timeLimit"
                 class="col-sm-4 col-lg-6 control-label">Time
            Limit</label>

          <div class="col-sm-6">
            <div class="input-group input-group-sm">
              <input type="number"
                     ng-model="problem.timeLimit"
                     ng-min="0"
                     ng-max="60000"
                     ng-require
                     id="timeLimit"
                     class="form-control"/><span
                class="input-group-addon">ms</span>
            </div>
            <ui-validate-info value="fieldInfo" for="timeLimit"></ui-validate-info>
          </div>
        </div>
      </div>
      <div class="col-lg-6">
        <div class="form-group">
          <label for="javaTimeLimit"
                 class="col-sm-4 col-lg-6 control-label">Java Time Limit</label>

          <div class="col-sm-6">
            <div class="input-group input-group-sm">
              <input type="number"
                     ng-model="problem.javaTimeLimit"
                     ng-min="0"
                     ng-max="60000"
                     ng-require
                     id="javaTimeLimit" class="form-control"/><span
                class="input-group-addon">ms</span>
            </div>
            <ui-validate-info value="fieldInfo" for="javaTimeLimit"></ui-validate-info>
          </div>
        </div>
      </div>
      <div class="col-lg-6">
        <div class="form-group">
          <label for="memoryLimit"
                 class="col-sm-4 col-lg-6 control-label">Memory Limit</label>

          <div class="col-sm-6">
            <div class="input-group input-group-sm">
              <input type="number"
                     ng-model="problem.memoryLimit"
                     ng-min="0"
                     ng-max="262144"
                     ng-require
                     id="memoryLimit" class="form-control"/><span
                class="input-group-addon">kb</span>
            </div>
            <ui-validate-info value="fieldInfo" for="memoryLimit"></ui-validate-info>
          </div>
        </div>
      </div>
      <div class="col-lg-6">
        <div class="form-group">
          <label for="javaMemoryLimit"
                 class="col-sm-4 col-lg-6 control-label">Java Memory Limit</label>

          <div class="col-sm-6">
            <div class="input-group input-group-sm">
              <input type="number"
                     ng-model="problem.javaMemoryLimit"
                     ng-min="0"
                     ng-max="262144"
                     ng-require
                     id="javaMemoryLimit" class="form-control"/><span
                class="input-group-addon">kb</span>
            </div>
            <ui-validate-info value="fieldInfo" for="javaMemoryLimit"></ui-validate-info>
          </div>
        </div>
      </div>
      <div class="col-lg-6">
        <div class="form-group">
          <label for="outputLimit"
                 class="col-sm-4 col-lg-6 control-label">Output Limit</label>

          <div class="col-sm-6">
            <div class="input-group input-group-sm">
              <input type="number"
                     ng-model="problem.outputLimit"
                     ng-min="0"
                     ng-max="262144"
                     ng-require
                     id="outputLimit" class="form-control"/><span
                class="input-group-addon">kb</span>
            </div>
            <ui-validate-info value="fieldInfo" for="outputLimit"></ui-validate-info>
          </div>
        </div>
      </div>
      <div class="col-lg-6">
        <div class="form-group">
          <label class="col-sm-4 col-lg-6 control-label">Is SPJ</label>

          <div class="col-sm-6">
            <div class="btn-group">
              <button type="button" class="btn btn-default btn-sm" ng-model="problem.isSpj"
                      btn-radio="true">Yes
              </button>
              <button type="button" class="btn btn-default btn-sm" ng-model="problem.isSpj"
                      btn-radio="false">No
              </button>
            </div>
          </div>
        </div>
      </div>

      <div class="col-lg-6">
        <div class="form-group">
          <label for="dataCount"
                 class="col-sm-4 col-lg-6 control-label">Total Data</label>

          <div class="col-sm-6">
            <div class="input-group input-group-sm">
              <input type="number"
                     ng-model="problem.dataCount"
                     id="dataCount"
                     class="form-control"
                     readonly="true"/>
              <span class="input-group-btn">
                <ui-problem-data-uploader
                    upload-url="/problem/uploadProblemDataFile/${action}"></ui-problem-data-uploader>
              </span>
            </div>
          </div>
        </div>
      </div>

      <div class="col-lg-6">
        <div class="form-group">
          <label class="col-sm-4 col-lg-6 control-label">Is visible</label>

          <div class="col-sm-6">
            <div class="btn-group">
              <button type="button" class="btn btn-default btn-sm" ng-model="problem.isVisible"
                      btn-radio="true">Yes
              </button>
              <button type="button" class="btn btn-default btn-sm" ng-model="problem.isVisible"
                      btn-radio="false">No
              </button>
            </div>
          </div>
        </div>
      </div>

      <%--
      <div class="col-lg-12">
        <label class="col-sm-4 col-lg-3 control-label"></label>

        <div class="col-sm-8 col-lg-9">
            <span class="help-block">
              <div id="uploader-info"></div>
              <div class="alert alert-info">
                <p>Please use zip file like this:</p>
                <pre type="no-prettify">foo.zip
├── 1.in
├── 1.out
├── a.in
├── a.out
└── spj.cc (if you want use special judge on this problem.)</pre>
              </div>
            </span>
        </div>
      </div>
      --%>
    </fieldset>
  </form>
</div>
<div class="col-md-12">
  <div ng-model="problem.description"
       ui-flandre
       upload-url="/picture/uploadPicture/problem/${action}">
  </div>
</div>
<div class="col-md-12">
  <h2>Input</h2>

  <div ng-model="problem.input"
       ui-flandre
       upload-url="/picture/uploadPicture/problem/${action}">
  </div>
</div>
<div class="col-md-12">
  <h2>Output</h2>

  <div ng-model="problem.output"
       ui-flandre
       upload-url="/picture/uploadPicture/problem/${action}">
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
          <textarea ng-model="problem.sampleInput"
                    class="form-control"
                    style="height: 300px"></textarea>
        </div>
      </td>
      <td>
        <div class="sample" type="no-prettify">
          <textarea ng-model="problem.sampleOutput"
                    class="form-control"
                    style="height: 300px"></textarea>
        </div>
      </td>
    </tr>
    </tbody>
  </table>
</div>
<div class="col-md-12">
  <h2>Hint</h2>

  <div ng-model="problem.hint"
       ui-flandre
       upload-url="/picture/uploadPicture/problem/${action}">
  </div>
</div>
<div class="form-group">
  <div class="col-md-12">
    <h2>Source</h2>
    <input type="text"
           ng-model="problem.source"
           ng-maxlength="100"
           class="form-control" placeholder="Enter source here"/>
  </div>
</div>
<div class="col-md-12 text-center">
  <button type="button" class="btn btn-primary"
          ng-click="submit()">Submit
  </button>
</div>
</div>
</div>

</body>
</html>
