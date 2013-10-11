<%--
Admin problem list page
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
	<head>
		<title>Article</title>
	</head>
	<body>
		<div id="article-admin-list">
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
									<legend>Article ID</legend>
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
											<span class="add-on">Title</span>
										</div>
										<div class="pure-u-1">
											<input type="text" name="title" maxlength="100" value="" id="title" class="pure-input-1">
										</div>
										<div class="pure-u-1">
											<span class="add-on">Keyword</span>
										</div>
										<div class="pure-u-1">
											<input type="text" name="keyword" maxlength="100" value="" id="keyword" class="pure-input-1">
										</div>
										<div class="pure-u-1">
											<span class="add-on">Is visible</span>
										</div>							
										<div class="pure-u-1-3">
											<label class="radio inline">
												<input type="radio" name="isVisible" value="all" checked="">
												All
											</label>
										</div>
										<div class="pure-u-1-3">
											<label class="radio inline">
												<input type="radio" name="isVisible" value="true">
												Yes
											</label>
										</div>
										<div class="pure-u-1-3">
											<label class="radio inline">
												<input type="radio" name="isVisible" value="false">
												No
											</label>
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
								<th class="orderButton" field="title">Title</th>
								<th style="width: 136px;" class="orderButton" field="time">Last updated</th>
								<th style="width: 200px;">Owner</th>
							</tr>
						</thead>
						<tbody id="articleList">
						</tbody>
					</table>
				</div>			
			</div>
		</div>

	</body>
</html>
