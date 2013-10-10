<%--
Nav list on navbar
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title></title>
	</head>
	<body>
		<div class="pure-g">
			<div class="pure-u-1">
				<div class="pure-menu pure-menu-open">
					<ul>
						<li class="pure-menu-heading">CDOJ</li>
						<li id="menu-item-home"><a href="/"><i class="icon-home"></i>Home</a></li>
						<li id="menu-item-problem"><a href="<c:url value="/problem/list"/>"><i class="icon-file"></i>Problems</a></li>
						<li id="menu-item-contest"><a href="#"><i class="icon-screenshot"></i>Contests</a></li>
						<li id="menu-item-status"><a href="<c:url value="/status/list"/>"><i class="icon-refresh"></i>Status</a></li>
						<li id="menu-item-user"><a href="<c:url value="/user/list"/>"><i class="icon-user"></i>Users</a></li>
					</ul>
				</div>
			</div>
			<div class="pure-u-1-2">
				<div class="pure-menu pure-menu-open">
					<ul>
						<li class="pure-menu-heading">Links</li>
						<li><a href="#">BBS</a></li>
						<li><a href="#">Wiki</a></li>
						<li><a href="#">Training</a></li>
						<li><a href="#">Download</a></li>
						<li><a href="#">Step-by-Step</a></li>
						<li><a href="#">Team Honors</a></li>
					</ul>
				</div>
			</div>
			<div class="pure-u-1-2">
				<div class="pure-menu pure-menu-open">
					<ul>
						<li class="pure-menu-heading">About</li>
						<li><a href="<c:url value="/article/show/1"/>">F.A.Q</a></li>
						<li><a href="<c:url value="/article/show/2"/>">Markdown</a></li>
						<li><a href="<c:url value="/article/show/3"/>">Training system</a></li>
						<li><a href="<c:url value="/article/show/4"/>">About</a></li>
					</ul>
				</div>
			</div>
		</div>
	</body>
</html>
