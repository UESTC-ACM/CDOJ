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
  Date: 13-1-19
  Time: 下午11:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
</head>
<body>
    <div class="row">
        <div class="span12">
            <form class="form-horizontal">
                <fieldset>
                    <legend>Register</legend>
                    <div class="control-group">
                        <label class="control-label" for="userName">User name</label>
                        <div class="controls">
                            <input type="text" class="input-xlarge" id="userName">
                            <small class="help-inline">字母，数字，汉字皆可</small>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="password">Password</label>
                        <div class="controls">
                            <input type="text" class="input-xlarge" id="password">
                            <small class="help-inline">字母，数字，汉字皆可</small>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="passwordRepeat">Repeat your password</label>
                        <div class="controls">
                            <input type="text" class="input-xlarge" id="passwordRepeat">
                            <small class="help-inline">字母，数字，汉字皆可</small>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label" for="email">Email</label>
                        <div class="controls">
                            <input type="text" class="input-xlarge" id="email">
                            <small class="help-inline">字母，数字，汉字皆可</small>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</body>
</html>