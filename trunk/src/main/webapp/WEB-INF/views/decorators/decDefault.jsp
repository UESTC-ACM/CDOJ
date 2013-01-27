<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib prefix="cdoj" uri="/WEB-INF/cdoj.tld" %>
<%--
  ~ /*
  ~  * cdoj, UESTC ACMICPC Online Judge
  ~  * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
  ~  * 	mzry1992 <@link muziriyun@gmail.com>
  ~  *
  ~  * This program is free software; you can redistribute it and/or
  ~  * modify it under the terms of the GNU General Public License
  ~  * as published by the Free Software Foundation; either version 2
  ~  * of the License, or (at your option) any later version.
  ~  *
  ~  * This program is distributed in the hope that it will be useful,
  ~  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~  * GNU General Public License for more details.
  ~  *
  ~  * You should have received a copy of the GNU General Public License
  ~  * along with this program; if not, write to the Free Software
  ~  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
  ~  */
  --%>

<%--
  Created by IntelliJ IDEA.
  User: mzry1992
  Date: 13-1-6
  Time: 上午11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <page:applyDecorator name="empty" page="/WEB-INF/views/common/header.jsp"/>
    <decorator:head/>

    <!-- 网站标题，暂时先这样 -->
    <title><decorator:title/> - UESTC Online Judge</title>

</head>

<body>

<page:applyDecorator name="empty" page="/WEB-INF/views/common/navbar.jsp"/>
<page:applyDecorator name="empty" page="/WEB-INF/views/common/modal.jsp"/>

<s:div id="wrap">
    <s:div cssClass="mzry1992">
        <s:div cssClass="container">

            <page:applyDecorator name="empty" page="/WEB-INF/views/common/debug.jsp"/>

            <decorator:body/>
        </s:div>
    </s:div>
</s:div>

<page:applyDecorator name="empty" page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>
