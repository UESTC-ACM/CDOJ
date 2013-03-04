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
 redirect page

 @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 @version 1
--%>
<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="s" uri="/struts-tags"%><!DOCTYPE html>
<html>
<head>
    <script type="text/javascript">
        (function(){
            var url = "${url}";
            url ? location.href = url : history.back();
        })();
    </script>
    <title>Redirect</title>
</head>
<body>
<div class="row">
    <div class="span8 offset2">
        <div class="alert alert-success" style="margin-top: 100px">
            <h3>
                ${msg}
            </h3>
            <p>
                If your browser does not support automatically redirect, please click <a class="btn btn-small btn-primary" href="${url}">here</a>
            </p>
        </div>
    </div>
</div>
</body>
</html>