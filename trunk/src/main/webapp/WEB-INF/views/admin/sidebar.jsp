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
<div class="well" style="padding: 8px 0;">
  <ul class="nav nav-list">
    <li class="nav-header"><i class="icon-home"></i>Dashboard</li>
    <li><a href="<c:url value="/admin/index"/>">OJ status</a></li>
    <li class="disabled"><a href="#">Backup</a></li>
    <li class="disabled"><a href="#">Help</a></li>

    <li class="divider"></li>

    <li class="nav-header"><i class="icon-pencil"></i>Article</li>
    <li><a href="<c:url value="/admin/article/list"/>">Article list</a><li>
    <li><a href="<c:url value="/admin/article/editor/0"/>">Add article</a></li>

    <li class="divider"></li>

    <li class="nav-header"><i class="icon-user"></i>User</li>
    <li><a href="<c:url value="/admin/user/list"/> ">User list</a></li>

    <li class="divider"></li>

    <li class="nav-header"><i class="icon-file"></i>Problem</li>
    <li><a href="<c:url value="/admin/problem/list"/>">Problem list</a></li>
    <li><a href="<c:url value="/admin/problem/editor/0"/>">Add problem</a></li>

    <li class="divider"></li>

    <li class="nav-header"><i class="icon-screenshot"></i>Contest</li>
    <li><s:a action="list" namespace="/admin/contest">Contest list</s:a></li>
    <li><s:a action="editor/" namespace="/admin/contest">Add contest</s:a></li>

    <li class="divider"></li>

    <li class="nav-header"><i class="icon-refresh"></i>Status</li>
    <li><s:a action="list" namespace="/admin/status">Status list</s:a></li>

    <li class="divider"></li>
    <li class="nav-header"><i class="icon-cog"></i>Training</li>
    <li><s:a action="index" namespace="/training/admin">Summary</s:a></li>
    <li><s:a action="contest/index" namespace="/training/admin">Contest list</s:a> </li>
    <li><s:a action="contest/editor/" namespace="/training/admin">Add contest</s:a></li>

  </ul>
</div>
</body>
</html>