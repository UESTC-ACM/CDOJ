<%--
 Admin problem data page
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
  prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Edit problem data - problem${targetProblem.problemId}</title>
</head>
<body>
  <div id="problem-data-editor">
    <div id="mzry1992-container">
      <div class="row">
        <div class="col-md-12" id="problem-data-editor-title"
          value="${targetProblem.problemId}">
          <h1>Edit problem ${targetProblem.problemId}</h1>
        </div>
        <div class="col-md-12">
          <form class="form-horizontal" role="form" id="problem-data-form">
            <fieldset>
              <legend>Limit</legend>
              <div class="col-lg-6">
                <div class="form-group">
                  <label for="timeLimit"
                    class="col-sm-4 col-lg-6 control-label">Time
                    Limit</label>
                  <div class="col-sm-6">
                    <div class="input-group">
                      <input type="text" name="timeLimit" maxlength="6"
                        value="${targetProblem.timeLimit}"
                        id="timeLimit" class="form-control" /><span
                        class="input-group-addon">ms</span>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-lg-6">
                <div class="form-group">
                  <label for="javaTimeLimit"
                    class="col-sm-4 col-lg-6 control-label">Java
                    Time Limit</label>
                  <div class="col-sm-6">
                    <div class="input-group">
                      <input type="text" name="javaTimeLimit"
                        maxlength="6"
                        value="${targetProblem.javaTimeLimit}"
                        id="javaTimeLimit" class="form-control" /><span
                        class="input-group-addon">ms</span>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-lg-6">
                <div class="form-group">
                  <label for="memoryLimit"
                    class="col-sm-4 col-lg-6 control-label">Memory
                    Limit</label>
                  <div class="col-sm-6">
                    <div class="input-group">
                      <input type="text" name="memoryLimit"
                        maxlength="6"
                        value="${targetProblem.memoryLimit}"
                        id="memoryLimit" class="form-control" /><span
                        class="input-group-addon">kb</span>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-lg-6">
                <div class="form-group">
                  <label for="javaMemoryLimit"
                    class="col-sm-4 col-lg-6 control-label">Java
                    Memory Limit</label>
                  <div class="col-sm-6">
                    <div class="input-group">
                      <input type="text" name="javaMemoryLimit"
                        maxlength="6"
                        value="${targetProblem.javaMemoryLimit}"
                        id="javaMemoryLimit" class="form-control" /><span
                        class="input-group-addon">kb</span>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-lg-6">
                <div class="form-group">
                  <label for="outputLimit"
                    class="col-sm-4 col-lg-6 control-label">Output Limit</label>
                  <div class="col-sm-6">
                    <div class="input-group">
                      <input type="text" name="outputLimit"
                        maxlength="6"
                        value="${targetProblem.outputLimit}"
                        id="outputLimit" class="form-control" /><span
                        class="input-group-addon">kb</span>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-lg-6">
                <div class="form-group">
                  <label for="isSpj"
                    class="col-sm-4 col-lg-6 control-label">Is
                    SPJ</label>
                  <div class="col-sm-6">
                    <label class="radio-inline"> <input
                      type="radio" name="isSpj" value="true"
                      <c:if test="${targetProblem.isSpj == true}">checked="true"</c:if> />
                      Yes
                    </label> <label class="radio-inline"> <input
                      type="radio" name="isSpj" value="false"
                      <c:if test="${targetProblem.isSpj == false}">checked="true"</c:if> />
                      No
                    </label>
                  </div>
                </div>
              </div>
            </fieldset>
            <fieldset>
              <legend>Problem data</legend>
              <div class="col-lg-12">
                <div class="form-group">
                  <label for="dataCount"
                    class="col-sm-4 col-lg-3 control-label">Current
                    data count</label>
                  <div class="col-sm-6 col-lg-3">
                    <input type="text" name="dataCount" maxlength="6"
                      value="${targetProblem.dataCount}" id="dataCount"
                      class="form-control" readonly="true" />
                  </div>
                </div>
              </div>
              <div class="col-lg-12">
                <div class="form-group">
                  <label class="col-sm-4 col-lg-3 control-label">Upload
                    data file</label>
                  <div class="col-sm-8 col-lg-9">
                    <a class="btn btn-success"
                      id="problem-data-uploader"> <i
                      class="fa fa-upload"></i>Upload
                    </a> <span class="help-block">
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
              </div>
            </fieldset>
            <div class="col-md-12 text-center">
              <button type="button" class="btn btn-primary" id="submit">Submit</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>

</body>
</html>