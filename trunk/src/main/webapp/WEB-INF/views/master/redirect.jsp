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
  User: fish
  Date: 13-1-20
  Time: 下午1:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="UTF-8"%><%@ taglib prefix="s" uri="/struts-tags"%><!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="text/javascript">
        (function(){
            var msg = "${msg}";
            var url = "${url}";
            if(msg) alert(msg);
            url ? location.href = url : history.back();
        })();
    </script>
    <style type="text/css">
        body {
            font-size: 12px;
        }

        .info {
            border: 2px solid #eee;
            margin: 16px 8px;
            padding: 8px 16px;
        }

        h1 {
            font-size: 20px;
            color: #ab1b27;
        }

        p {
            font-size: 14px;
            padding-left: 24px;
        }
    </style>
</head>
<body>
<noscript>
    <div class="info">
        <h1>正在跳转：</h1>
        <p class="info">页面正在跳转，如果您的浏览器不支持自动跳转，请手动<a href="${url}">点击这里</a>。</p>
    </div>
</noscript>
</body>
</html>tml>