<%--
 Admin problem editor page
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <page:applyDecorator name="head" page="/WEB-INF/views/common/fileUploaderHeader.jsp"/>
  <script src="<c:url value="/plugins/epiceditor/js/epiceditor.js"/>"></script>
  <script src="<c:url value="/scripts/cdoj/cdoj.util.picture.js"/>"></script>
  <script src="<c:url value="/scripts/cdoj/cdoj.admin.problemEditor.js"/>"></script>
  <title>Problem</title>
</head>
<body>
<div class="row" id="problemEditor">

  <div class="span10">
    <h3>
      Edit problem <span id="problemId">${targetProblem.problemId}</span>
    </h3>
    <div class="control-group">
      <div class="controls">
        <input type="text"
               name="problemDTO.title"
               maxlength="50"
               value="${targetProblem.title}"
               id="problemDTO_title"
               class="span10"
               placeholder="Enter title here">
      </div>
    </div>
  </div>

  <div class="span10">
    <div id="problemDTO_description" class="textarea-content textarea-large"><c:out value="${targetProblem.description}" escapeXml="true"/></div>
  </div>

  <div class="span10">
    <h2>Input</h2>
    <div id="problemDTO_input" class="textarea-content textarea-mini"><c:out value="${targetProblem.input}" escapeXml="true"/></div>
  </div>

  <div class="span10">
    <h2>Output</h2>
    <div id="problemDTO_output" class="textarea-content textarea-mini"><c:out value="${targetProblem.output}" escapeXml="true"/></div>
  </div>

  <div class="span10">
    <h2>Sample input and output</h2>
    <table class="table table-sample table-bordered table-striped">
      <thead>
      <tr>
        <th>Sample Input</th>
        <th>Sample Output</th>
      </tr>
      </thead>
      <tbody class="font-code">
      <tr>
        <td>
          <div id="problemDTO_sampleInput" class="textarea-content textarea-big"><c:out value="${targetProblem.sampleInput}" escapeXml="true"/></div>
        </td>
        <td>
          <div id="problemDTO_sampleOutput" class="textarea-content textarea-big"><c:out value="${targetProblem.sampleOutput}" escapeXml="true"/></div>
        </td>
      </tr>
      </tbody>
    </table>
  </div>

  <div class="span10">
    <h2>Hint</h2>
    <div id="problemDTO_hint" class="textarea-content textarea-mini"><c:out value="${targetProblem.hint}" escapeXml="true"/></div>
  </div>

  <div class="span10">
    <h2>Source</h2>
    <div class="control-group ">
      <div class="controls">
        <input type="text"
               name="problemDTO.source"
               maxlength="100"
               value="${targetProblem.source}"
               id="problemDTO_source"
               class="span10"
               placeholder="Enter source here">
      </div>
    </div>
  </div>

  <div class="span10">
    <input type="submit" id="submit" name="submit" value="Submit" class="btn btn-primary">
  </div>
</div>

<page:applyDecorator name="body" page="/WEB-INF/views/admin/pictureModal.jsp"/>

</body>
</html>