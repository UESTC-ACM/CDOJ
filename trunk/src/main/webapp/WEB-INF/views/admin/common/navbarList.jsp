<%--
 Sidebar of all admin page.
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
						<li class="pure-menu-heading">Dashboard</li>						
						<li id="menu-item-home"><a href="/admin/index"><i class="icon-cog"></i>Summary</a></li>
						<li id="menu-item-problem"><a href="<c:url value="/admin/problem/list"/>"><i class="icon-file"></i>Problems</a></li>
						<li id="menu-item-contest"><a href="#"><i class="icon-screenshot"></i>Contests</a></li>
						<li id="menu-item-status"><a href="#"><i class="icon-refresh"></i>Status</a></li>
						<li id="menu-item-user"><a href="<c:url value="/admin/user/list"/>"><i class="icon-user"></i>Users</a></li>
						<li id="menu-item-article"><a href="<c:url value="/admin/article/list"/>"><i class="icon-pencil"></i>Article</a><li>    
					</ul>
				</div>
			</div>
		
			<div class="pure-u-1">
				<div class="pure-menu pure-menu-open">
					<ul>
						<li class="pure-menu-heading"></li>
						<li><a href="/"><i class="icon-home"></i>Return CDOJ</a></li>
					</ul>
				</div>
			</div>
			<%--
	<div class="pure-u-1-2">
		<div class="pure-menu pure-menu-open">
			<ul>
				<li class="pure-menu-heading"><i class="icon-home"></i>Dashboard</li>
			  <li><a href="<c:url value="/admin/index"/>">OJ status</a></li>
				<li class="disabled"><a href="#">Backup</a></li>
				<li class="disabled"><a href="#">Help</a></li>
			</ul>
		</div>
	</div>
	--%>
	<%--
	<div class="pure-u-1">
		<div class="pure-menu pure-menu-open">
			<ul>
		<li class="pure-menu-heading"><i class="icon-cog"></i>ADMIN</li>
		<li><a href="<c:url value="/admin/article/editor/new"/>">Add article</a></li>		<li><a href="<c:url value="/admin/user/list"/> "><i class="icon-user"></i>User</a></li>

    <li><a href="<c:url value="/admin/problem/list"/>"><i class="icon-file"></i>Problem</a></li>
    <li><a href="<c:url value="/admin/problem/editor/new"/>">Add problem</a></li>
    <li><a href="#" action="list" namespace="/admin/contest"><i class="icon-screenshot"></i>Contest</a></li>
			<li><s:a action="editor/" namespace="/admin/contest">Add contest</s:a></li>
    <li><a href="#" action="list" namespace="/admin/status"><i class="icon-refresh"></i>Status</a></li>


			</ul>
		</div>
	</div>
	--%>

</div>
<%--

<li class="nav-header"><i class="icon-cog"></i>Training</li>
    <li><s:a action="index" namespace="/training/admin">Summary</s:a></li>
    <li><s:a action="contest/index" namespace="/training/admin">Contest list</s:a> </li>
    <li><s:a action="contest/editor/" namespace="/training/admin">Add contest</s:a></li>
--%>
</body>
</html>
