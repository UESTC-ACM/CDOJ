<%--
 Admin problem data page
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <page:applyDecorator name="head" page="/WEB-INF/views/common/fileUploaderHeader.jsp"/>
  <script src="<c:url value="/scripts/cdoj/cdoj.admin.problemDataAdmin.js"/>"></script>
  <title>Problem</title>
</head>
<body>
<div class="row" id="problemDataEditor">
  <div class="span10">
    <form class="form-horizontal">
      <fieldset>
        <legend>Problem <span id="problemId">${targetProblem.problemId}</span></legend>
        <div class="row">
          <div class="span4">
            <div class="control-group">
              <label class="control-label">Time limit</label>
              <div class="controls">
                <div class="input-append">
                  <input type="text"
                         name="timeLimit"
                         maxlength="6"
                         value="${targetProblem.timeLimit}"
                         id="timeLimit"
                         class="span1">
                  <span class="add-on">ms</span>
                </div>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">Memory limit</label>
              <div class="controls">
                <div class="input-append">
                  <input type="text"
                         name="memoryLimit"
                         maxlength="10"
                         value="${targetProblem.memoryLimit}"
                         id="memoryLimit"
                         class="span2">
                  <span class="add-on">KB</span>
                </div>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">Output limit</label>
              <div class="controls">
                <div class="input-append">
                  <input type="text"
                         name="outputLimit"
                         maxlength="10"
                         value="${targetProblem.outputLimit}"
                         id="outputLimit"
                         class="span2">
                  <span class="add-on">KB</span>
                </div>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">Is SPJ</label>
              <div class="controls">
                <label class="radio inline">
                  <input type="radio"
                         name="isSpj"
                         value="true"
                  <c:if test="${targetProblem.isSpj == true}">checked="true"</c:if>>
                  Yes
                </label>
                <label class="radio inline">
                  <input type="radio"
                         name="isSpj"
                         value="false"
                  <c:if test="${targetProblem.isSpj == false}">checked="true"</c:if>>
                  No
                </label>
              </div>
            </div>
          </div>
          <div class="span4">
            <div class="control-group">
              <label class="control-label">Java time limit</label>
              <div class="controls">
                <div class="input-append">
                  <input type="text"
                         name="javaTimeLimit"
                         maxlength="6"
                         value="${targetProblem.javaTimeLimit}"
                         id="javaTimeLimit"
                         class="span1">
                  <span class="add-on">ms</span>
                </div>
              </div>
            </div>
            <div class="control-group">
              <label class="control-label">Java memory limit</label>
              <div class="controls">
                <div class="input-append">
                  <input type="text"
                         name="javaMemoryLimit"
                         maxlength="6"
                         value="${targetProblem.javaMemoryLimit}"
                         id="javaMemoryLimit"
                         class="span2">
                  <span class="add-on">KB</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </fieldset>
      <fieldset>
        <legend>Problem data</legend>
        <div class="control-group">
          <label class="control-label">Current data count</label>
          <div class="controls">
            <input type="text"
                   name="dataCount"
                   maxlength="6"
                   value="${targetProblem.dataCount}"
                   id="dataCount"
                   class="span1"
                   readonly="true">
          </div>
        </div>
        <div class="control-group">
          <label class="control-label">Upload data file</label>
          <div class="controls">
            <div id="fileUploader"></div>
            <span id="fileUploaderAttention" class="help-inline">Please use zip to package your files.</span>
          </div>
        </div>

        <div class="form-actions">
          <input type="submit" id="submit" name="submit" value="Submit" class="btn btn-primary">
        </div>
      </fieldset>
    </form>
  </div>
</div>
</body>
</html>