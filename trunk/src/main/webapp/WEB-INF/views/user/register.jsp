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
    <s:div cssClass="row">
        <s:div cssClass="span12">
            <s:form action="register" enctype="multipart/form-data" theme="bootstrap" cssClass="form-horizontal">
                <fieldset>
                    <legend>Register</legend>
                    <s:textfield name="userDTO.userName"
                                 size="24"
                                 cssClass="span3"
                                 label="User Name"/>
                    <s:password name="userDTO.password"
                                 size="24"
                                 cssClass="span3"
                                 label="Password"/>
                    <s:password name="userDTO.passwordRepeat"
                                 size="24"
                                 cssClass="span3"
                                 label="Repeat your password"/>
                </fieldset>
                <fieldset>
                    <legend>Information</legend>
                    <s:textfield name="userDTO.nickName"
                                 size="24"
                                 cssClass="span3"
                                 label="Nick name"/>
                    <s:textfield name="userDTO.email"
                                 size="24"
                                 cssClass="span3"
                                 label="Email"/>
                    <s:textfield name="userDTO.school"
                                 size="24"
                                 cssClass="span3"
                                 value="UESTC"
                                 label="School"/>
                    <s:select name="userDTO.departmentId"
                              list="global.departmentList"
                              listKey="departmentId"
                              listValue="name"
                              cssClass="span3"
                              label="Department"/>
                    <s:textfield name="userDTO.studentId"
                                 size="24"
                                 cssClass="span3"
                                 label="Student ID"/>
                </fieldset>
                <fieldset>
                    <s:div cssClass="form-actions">
                        <s:submit method="toRegister"
                                  cssClass="btn btn-primary"
                                  value="Submit"/>
                    </s:div>
                </fieldset>
            </s:form>
        </s:div>
    </s:div>
</body>
</html>