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
		<div id="status-list">
			<div class="row" id="mzry1992-header">
				<div class="col-md-12">
					<div id="page-info">
						<ul class="pagination pagination-centere">
							<li class="disabled"><a>← First</a></li>
							<li class="disabled"><a>«</a></li>
							<li class="active"><a href="1">1</a></li>
							<li class="disabled"><a>»</a></li>
							<li class="disabled"><a>Last →</a></li>
						</ul>
					</div>
					<div id="search-group">
						<input type="text" name="search-keyword" maxlength="24" value="" id="search-keyword" class="pull-left form-control"/>
						<button id="search" class="btn btn-success"><i class="icon-search"></i></button>

						<a href="#" id="advanced"><i class="icon-chevron-down"></i></a>
						<div id="condition">
							<form class="form">
								<fieldset>
									<legend>Status ID</legend>
									<div class="row">
										<div class="col-sm-6">
											<div class="form-group">
												<label for="startId">Form</label>
												<input type="text" name="startId" maxlength="6" value="" id="startId" class="form-control input-sm"/>
											</div>
										</div>										
										<div class="col-sm-6">
											<div class="form-group">
												<label for="endId">To</label>
												<input type="text" name="endId" maxlength="6" value="" id="endId" class="form-control input-sm"/>
											</div>
										</div>
									</div>
								</fieldset>
								<fieldset>
									<div class="row">
										<div class="col-md-12">
											<div class="form-group">
												<label for="userName">User name</label>
												<input type="text" name="userName" maxlength="24" value="" id="userName" class="form-control input-sm"/>
											</div>
										</div>
										<div class="col-md-12">
											<div class="form-group">
												<label for="problemId">Problem ID</label>
												<input type="text" name="problemId" maxlength="6" value="" id="problemId" class="form-control input-sm"/>
											</div>
										</div>
										<div class="col-md-12">
											<div class="form-group">
												<label for="keyword">Keyword</label>
												<input type="text" name="keyword" maxlength="100" value="" id="keyword" class="form-control input-sm"/>
											</div>
										</div>
									</div>
								</fieldset>
								<p class="pull-right">
									<button type="submit" class="btn btn-primary btn-sm" id="search-button">Search</button>
									<button type="button" class="btn btn-danger btn-sm" id="reset-button">Reset</button>
								</p>
							</form>
						</div>
					</div>
				</div>
			</div>

			<div class="row" id="mzry1992-container">
				<div class="col-md-12" id="list-container">
				</div>
			</div>
		</div>
		<%--<page:applyDecorator name="body" page="/WEB-INF/views/status/statusModal.jsp"/>--%>

	</body>
</html>
