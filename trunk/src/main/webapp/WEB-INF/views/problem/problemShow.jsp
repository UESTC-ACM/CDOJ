<%--
Problem statement
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib prefix='fn' uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title><c:out value="${targetProblem.title}"/></title>
	</head>
	<body>

		<div id="problem-show" class="pure-g">
			<div class="pure-u-4-5" id="problem_title" value="<c:out value="${targetProblem.problemId}"/>">
				<h1 class="pull-left">
					<c:out value="${targetProblem.title}"/>
				</h1>
				<c:if test="${targetProblem.isSpj == true}">
				<div style="margin: 18px 0 0 0;">
					<span class="label label-danger tags" style="margin: 0 0 0 8px;">SPJ</span>
				</div>
				</c:if>
			</div>
			<div class="pure-u-1-5">
				<c:if test="${currentUser.type == 1}">
				<div class="pull-right" style="margin: 18px 0;">
					<a href="<c:url value="/admin/problem/editor/${targetProblem.problemId}"/>">
						<i class="icon-pencil"></i>
						Edit problem statement
					</a>
					<br/>
					<a href="<c:url value="/admin/problem/data/${targetProblem.problemId}"/>">
						<i class="icon-cog"></i>
						Edit problem data
					</a>
				</div>
				</c:if>
			</div>

			<div class="pure-u-1">
				<dl class="dl-horizontal">
					<dt>Time limit</dt>
					<dd><span>${targetProblem.javaTimeLimit} / ${targetProblem.timeLimit} ms (Java / others)</span>
					</dd>
					<dt>Memory limit</dt>
					<dd>
					<span>${targetProblem.javaMemoryLimit} / ${targetProblem.memoryLimit} kb (Java / others)</span>
					</dd>
					<dt>Total accepted</dt>
					<dd><span>${targetProblem.solved}</span></dd>
					<dt>Total submissions</dt>
					<dd><span>${targetProblem.tried}</span></dd>
				</dl>
			</div>

			<div class="pure-u-1" id="problem_description" type="markdown">
				<textarea>${targetProblem.description}</textarea>
			</div>

			<div class="pure-u-1">
				<h2>Input</h2>
				<div id="problem_input" type="markdown">
					<textarea>${targetProblem.input}</textarea>
				</div>
			</div>

			<div class="pure-u-1">
				<h2>Output</h2>
				<div id="problem_output" type="markdown">
					<textarea>${targetProblem.output}</textarea>
				</div>
			</div>
			<div class="pure-u-1">
				<h2>Sample input and output</h2>
				<table class="pure-table">
					<thead>
						<tr>
							<th>Sample Input</th>
							<th>Sample Output</th>
						</tr>
					</thead>
					<tbody class="font-code">
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
			<div class="pure-u-1">
				<h2>Hint</h2>

				<div class="" id="problem_hint" type="markdown">
					<textarea>${targetProblem.hint}</textarea>
				</div>
			</div>
			</c:if>
			<c:if test="${targetProblem.source != ''}">
			<div class="pure-u-1">
				<h2>Source</h2>

				<div class="" id="problem_source">
					${targetProblem.source}
				</div>
			</div>
			</c:if>

		</div>
	</div>

	<%--
	<div class="tab-pane fade" id="tab-problem-submit">
		<form class="form-horizontal">
			<textarea class="contest-submit-area" id="codeContent"></textarea>
		</form>

		<div class="contest-submit-action">
			<div id="language-selector" class="pull-left" style="margin-right: 20px;">
				<div class="btn-group" data-toggle="buttons-radio" id="languageSelector">
					<c:forEach var="language" items="${languageList}">
					<button class="btn btn-info <c:if test="${language.languageId == 2}">active</c:if>"
						value="<c:out value="${language.languageId}"/>"><c:out value="${language.name}"/>
					</button>
					</c:forEach>
				</div>
			</div>
			<a href="#" id="submitCode" class="pull-right btn btn-primary">Submit</a>
		</div>
	</div>

	<div class="tab-pane fade" id="tab-problem-status">
		<div id="pageInfo">
		</div>

		<table class="table table-striped table-bordered">
			<thead>
				<tr>
					<th style="width: 60px;" class="orderButton" field="id">Id</th>
					<th>User</th>
					<th style="width: 60px;" class="orderButton" field="problemByProblemId">Problem</th>
					<th style="width: 260px;" class="orderButton" field="result">Judge's Response</th>
					<th style="width: 100px;" class="orderButton" field="length">Length</th>
					<th style="width: 70px;" class="orderButton" field="timeCost">Time</th>
					<th style="width: 80px;" class="orderButton" field="memoryCost">Memory</th>
					<th style="width: 140px;" class="orderButton" field="time">Submit Time</th>
				</tr>
			</thead>
			<tbody id="statusList">
			</tbody>
		</table>
	</div>
	<div class="tab-pane fade" id="tab-problem-discus">
	</div>
</div>
--%>

<%--<page:applyDecorator name="body" page="/WEB-INF/views/status/statusModal.jsp"/>--%>

</body>
</html>
