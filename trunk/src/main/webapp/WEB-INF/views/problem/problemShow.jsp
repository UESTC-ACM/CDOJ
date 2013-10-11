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

		<div id="problem-show">
			<div class="pure-g mzry1992-header">
				<div class="pure-u-4-5" id="problem_title" value="<c:out value="${targetProblem.problemId}"/>">
					<h1>
						<c:out value="${targetProblem.title}"/>
						<c:if test="${targetProblem.isSpj == true}">
						<sup>
							<span class="label label-danger tags" style="margin: 0 0 0 8px; font-size: 16px;">SPJ</span>
						</sup>
						</c:if>
					</h1>
				</div>

				<div class="pure-u-1-5">
					<c:if test="${currentUser.type == 1}">
					<a href="<c:url value="/admin/problem/editor/${targetProblem.problemId}"/>">
						<i class="icon-pencil"></i>
						Edit problem statement
					</a>
					<br/>
					<a href="<c:url value="/admin/problem/data/${targetProblem.problemId}"/>">
						<i class="icon-cog"></i>
						Edit problem data
					</a>
					</c:if>
				</div>
				<div class="pure-u-1">
					<div class="pure-menu pure-menu-open pure-menu-horizontal" id="problem-show-tab">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#tab-problem-show" data-toggle="tab">Problem</a></li>
							<li><a href="#tab-problem-submit" data-toggle="tab">Submit</a></li>
							<li><a href="#tab-problem-status" data-toggle="tab">Status</a></li>
							<li class="pure-menu-disabled"><a href="#tab-problem-discus">Discus</a></li>
						</ul>
					</div>
				</div>
			</div>

			<div class="pure-g mzry1992-content tab-pane active" id="tab-problem-show">
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

			<div class="pure-g mzry1992-content tab-pane" id="tab-problem-submit">
				<div class="pure-u-1">
					<form>
						<textarea class="cdoj-submit-area" id="codeContent"></textarea>
					</form>
				</div>
				<div class="pure-u-1">
					<div class="cdoj-submit-action">
							<div id="languageSelector" class="pull-left" style="margin-right: 20px;">
								<c:forEach var="language" items="${languageList}">
									<button class="button <c:if test="${language.languageId == 2}">active</c:if>"
										value="<c:out value="${language.languageId}"/>">
										<span class="label"><c:out value="${language.name}"/></span>
									</button>
								</c:forEach>
							</div>
						<button class="action blue pull-right" id="submitCode"><span class="label">Submit</span></button>
					</div>
				</div>
			</div>

			<div class="pure-g mzry1992-content tab-pane" id="tab-problem-status">
				<div id="status-list">
						<div id="pageInfo" class="pure-u-3-4">
							<div class="pagination pagination-centered">
								<ul>
									<li class="disabled"><a>← First</a></li>
									<li class="disabled"><a>«</a></li>
									<li class="active"><a href="1">1</a></li>
									<li class="disabled"><a>»</a></li>
									<li class="disabled"><a>Last →</a></li>
								</ul>
							</div>
						</div>
						<div class="pure-u-1-4">
							<div id="search-group">
								<input type="text" name="search-keyword" maxlength="24" value="" id="search-keyword" class="pull-left">
								<button id="search" class="action blue pull-right"><span class="label"><i class="icon-search"></i></span></button>
								<a href="#" id="advanced"><i class="icon-chevron-down"></i></a>
								<div id="condition">
									<form class="pure-form pure-form-stacked">
										<fieldset>
											<legend>Status ID</legend>
											<div class="pure-g">
												<div class="pure-u-1-2">
													<span class="add-on">Form</span>
													<input type="text" name="startId" maxlength="6" value="" id="startId" class="pure-input-1">
												</div>
												<div class="pure-u-1-2">
													<span class="add-on">To</span>
													<input type="text" name="endId" maxlength="6" value="" id="endId" class="pure-input-1">
												</div>							
											</div>
										</fieldset>
										<fieldset>
											<div class="pure-g">
												<div class="pure-u-1">
													<span class="add-on">User Name</span>
												</div>
												<div class="pure-u-1">
													<input type="text" name="userName" maxlength="24" value="" id="userName" class="pure-input-1">
												</div>
												<div class="pure-u-1">
													<span class="add-on">Problem ID</span>
												</div>
												<div class="pure-u-1">
													<input type="text" name="problemId" maxlength="6" value="" id="problemId" class="pure-input-1">
												</div>
												<div class="pure-u-1">
													<span class="add-on">Keyword</span>
												</div>
												<div class="pure-u-1">
													<input type="text" name="keyword" maxlength="100" value="" id="keyword" class="pure-input-1">
												</div>
											</div>
										</fieldset>
										<div class="form-actions">
											<button class="action blue" id="search-button"><span class="label">Search</span></button>
											<button class="action red" id="reset-button"><span class="label">Reset</span></button>							
										</div>
									</form>
								</div>
							</div>
						</div>

						<div class="pure-u-1">
							<table class="pure-table pure-table-bordered">
								<thead>
									<tr>
										<th style="width: 30px;" class="orderButton" field="id">#</th>
										<th>User</th>
										<th style="width: 30px;" class="orderButton" field="problemByProblemId">Prob</th>
										<th style="width: 250px;" class="orderButton" field="result">Judge's Response</th>
										<th style="width: 100px;" class="orderButton" field="length">Length</th>
										<th style="width: 70px;" class="orderButton" field="timeCost">Time</th>
										<th style="width: 80px;" class="orderButton" field="memoryCost">Memory</th>
										<th style="width: 136px;" class="orderButton" field="time">Submit Time</th>
									</tr>
								</thead>
								<tbody id="statusList">
								</tbody>
							</table>
						</div>
					</div>
			</div>

			<div class="pure-g mzry1992-content tab-pane" id="tab-problem-discus">
			</div>
		</div>
	<%--<page:applyDecorator name="body" page="/WEB-INF/views/status/statusModal.jsp"/>--%>

	</body>
</html>
	