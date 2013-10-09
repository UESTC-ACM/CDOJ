<%--
Admin problem list page
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>Status</title>
	</head>
	<body>
		<div class="pure-g" id="status-list">
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
		</div>

		<%--<page:applyDecorator name="body" page="/WEB-INF/views/status/statusModal.jsp"/>--%>

	</body>
</html>
