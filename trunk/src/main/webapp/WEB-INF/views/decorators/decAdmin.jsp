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
 Decorator for admin part.

 @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 @version 1
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib prefix="cdoj" uri="/WEB-INF/cdoj.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <page:applyDecorator name="head" page="/WEB-INF/views/common/header.jsp"/>
    <decorator:head/>

    <title>Admin - <decorator:title/> - UESTC Online Judge</title>

</head>

<body>

<page:applyDecorator name="body" page="/WEB-INF/views/common/navbar.jsp"/>
<page:applyDecorator name="body" page="/WEB-INF/views/common/modal.jsp"/>

<div id="wrap">
    <div class="mzry1992">
        <div class="container">

            <page:applyDecorator name="body" page="/WEB-INF/views/common/debug.jsp"/>

            <div class="row">
                <div class="span2">
                    <page:applyDecorator name="body" page="/WEB-INF/views/admin/sidebar.jsp"/>
                </div>
                <div class="span10">
                    <decorator:body/>
                </div>
            </div>

        </div>
    </div>
</div>

<page:applyDecorator name="body" page="/WEB-INF/views/common/footer.jsp"/>

</body>
</html>
