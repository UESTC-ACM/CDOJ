<%--suppress ALL --%>
<%--
Admin user list page
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>User</title>
	</head>
	<body>
		<div id="user-admin-list">
			<div class="pure-g mzry1992-header">
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
									<legend>User ID</legend>
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
											<span class="add-on">Type</span>
										</div>
										<div class="pure-u-1">
											<select name="type" id="type" class="pure-u-1">
												<c:forEach var="authenticationType" items="${authenticationTypeList}">
												<option value="${authenticationType.ordinal()}"><c:out value="${authenticationType.description}"/></option>
												</c:forEach>
											</select>
										</div>
										<div class="pure-u-1">
											<span class="add-on">Keyword</span>
										</div>
										<div class="pure-u-1">
											<input type="text" name="keyword" maxlength="100" value="" id="keyword" class="pure-input-1">
										</div>
										<div class="pure-u-1">
											<span class="add-on">School</span>
										</div>
										<div class="pure-u-1">
											<input type="text" name="school" maxlength="100" value="" id="school" class="pure-input-1">
										</div>
										<div class="pure-u-1">
											<span class="add-on">Department</span>
										</div>
										<div class="pure-u-1">
											<select name="departmentId" id="departmentId" class="pure-u-1">
												<c:forEach var="department" items="${departmentList}">
												<option value="${department.departmentId}"><c:out value="${department.name}"/></option>
												</c:forEach>
											</select>
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
			</div>

			<div class="pure-g mzry1992-content">
				<div class="pure-u-1">
					<table class="pure-table pure-table-bordered">
						<thead>
							<tr>
								<th style="width: 30px;" class="orderButton" field="id">#</th>
								<th class="orderButton" field="userName">User name</th>
								<th class="orderButton" field="nickName">Nick name</th>
								<th class="orderButton" field="email">Email</th>
								<th style="width: 90px;" class="orderButton" field="type">Type</th>
								<th style="width: 136px;" class="orderButton" field="lastLogin">Last login</th>
								<th style="width: 14px;"></th>
							</tr>
						</thead>
						<tbody id="userList">
						</tbody>
					</table>
				</div>
			</div>
		</div>
		
	<%--
	<!-- User edit Modal -->
	<div id="userEditModal" class="modal hide fade modal-large" tabindex="-1" role="dialog"
		aria-labelledby="userEditModal" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3 id="userEditModalLabel"></h3>
		</div>
		<div class="modal-body">
			<form class="form-horizontal">
				<fieldset>
					<div class="control-group "><label class="control-label" for="userId">User ID</label><div class="controls">
							<input type="text" name="userId" maxlength="20" value="" readonly="readonly" id="userId" class="span4"></div>
					</div>
					<div class="control-group "><label class="control-label" for="nickName">Nick name</label><div class="controls">
							<input type="text" name="nickName" maxlength="20" value="" id="nickName" class="span4"></div>
					</div>
					<div class="control-group "><label class="control-label" for="email">Email</label><div class="controls">
							<input type="text" name="email" maxlength="100" value="" readonly="readonly" id="email" class="span4"></div>
					</div>
					<div class="control-group "><label class="control-label" for="school">School</label><div class="controls">
							<input type="text" name="school" maxlength="50" value="" id="school" class="span4"></div>
					</div>
					<div class="control-group "><label class="control-label" for="departmentId">Department</label><div class="controls">
							<select name="departmentId" id="departmentId" class="span4">
								<c:forEach var="department" items="${departmentList}">
								<option value="${department.departmentId}"><c:out value="${department.name}"/></option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="control-group "><label class="control-label" for="studentId">Student ID</label><div class="controls">
							<input type="text" name="studentId" maxlength="20" value="" id="studentId" class="span4"></div>
					</div>
					<div class="control-group "><label class="control-label" for="type">Type</label><div class="controls">
							<select name="type" id="type" class="span4">
								<c:forEach var="authenticationType" items="${authenticationTypeList}">
								<option value="${authenticationType.ordinal()}"><c:out value="${authenticationType.description}"/></option>
								</c:forEach>
							</select>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal" aria-hidden="true">Cancel</a>
			<a href="#" class="btn btn-primary">Update</a>
		</div>
	</div>
	--%>
</body>
</html>
