<%--
Admin problem editor page
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
  prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<c:if test="${action eq 'new'}">
  <title>New problem</title>
</c:if>
<c:if test="${action eq 'edit'}">
  <title>Edit problem - problem${targetProblem.problemId}</title>
</c:if>
</head>
<body>
  <div id="problem-editor">
      <div class="row">
        <c:if test="${action eq 'new'}">
          <div class="col-md-12" id="problem-editor-title" value="new">
            <h1>New problem</h1>
          </div>
        </c:if>
        <c:if test="${action eq 'edit'}">
          <div class="col-md-12" id="problem-editor-title"
            value="${targetProblem.problemId}">
            <h1>Edit problem ${targetProblem.problemId}</h1>
          </div>
        </c:if>
        <div class="form-group">
          <div class="col-sm-12">
            <input type="text" name="title" maxlength="50"
              value="${targetProblem.title}" id="title"
              class="form-control" placeholder="Enter title here" />
          </div>
        </div>
        <div class="col-md-12">
          <div id="description">
            <c:out value="${targetProblem.description}" escapeXml="true" />
          </div>
        </div>
        <div class="col-md-12">
          <h2>Input</h2>
          <div id="input">
            <c:out value="${targetProblem.input}" escapeXml="true" />
          </div>
        </div>
        <div class="col-md-12">
          <h2>Output</h2>
          <div id="output">
            <c:out value="${targetProblem.output}" escapeXml="true" />
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
                    <textarea id="sample-input" class="form-control"
                      style="height: 300px"><c:out
                        value="${targetProblem.sampleInput}"
                        escapeXml="true" /></textarea>
                  </div>
                </td>
                <td>
                  <div class="sample" type="no-prettify">
                    <textarea id="sample-output" class="form-control"
                      style="height: 300px"><c:out
                        value="${targetProblem.sampleOutput}"
                        escapeXml="true" /></textarea>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="col-md-12">
          <h2>Hint</h2>
          <div id="hint">
            <c:out value="${targetProblem.hint}" escapeXml="true" />
          </div>
        </div>
        <div class="form-group">
          <div class="col-md-12">
            <h2>Source</h2>
            <input type="text" name="source" maxlength="100"
              value="${targetProblem.source}" id="source"
              class="form-control" placeholder="Enter source here" />
          </div>
        </div>
        <div class="col-md-12 text-center">
          <button type="button" class="btn btn-primary" id="submit">Submit</button>
        </div>
      </div>
    </div>

</body>
</html>
