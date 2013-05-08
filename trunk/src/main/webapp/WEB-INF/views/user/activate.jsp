<%--
  ~ cdoj, UESTC ACMICPC Online Judge
  ~
  ~ Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
  ~ mzry1992 <@link muziriyun@gmail.com>
  ~
  ~ This program is free software; you can redistribute it and/or
  ~ modify it under the terms of the GNU General Public License
  ~ as published by the Free Software Foundation; either version 2
  ~ of the License, or (at your option) any later version.
  ~
  ~ This program is distributed in the hope that it will be useful,
  ~ but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~ MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~ GNU General Public License for more details.
  ~
  ~ You should have received a copy of the GNU General Public License
  ~ along with this program; if not, write to the Free Software
  ~ Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
  --%>

<%--
 Account activate page.

 @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 @version 1
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib prefix="cdoj" uri="/WEB-INF/cdoj.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="<s:url value="/scripts/cdoj/cdoj.user.activate.js"/>"></script>
    <title>Account activation</title>
</head>
<body>
<div class="row">
    <div class="span6 offset3">
        <h1>Reset your password</h1>
    </div>
    <div class="span6 offset3 well">
        <form id="accountActivationForm" class="form-horizontal">
            <fieldset>
                <s:textfield name="userDTO.userName"
                             maxLength="24"
                             cssClass="span4"
                             label="User Name"
                             readonly="true"
                             value="%{targetUserName}"
                             theme="bootstrap"/>
                <s:textfield name="targetSerialKey"
                             maxLength="48"
                             cssClass="span4"
                             label="Serial Key"
                             readonly="true"
                             value="%{targetSerialKey}"
                             theme="bootstrap" />
                <s:password name="userDTO.password"
                            maxLength="20"
                            cssClass="span4"
                            label="New password"
                            theme="bootstrap"/>
                <s:password name="userDTO.passwordRepeat"
                            maxLength="20"
                            cssClass="span4"
                            label="Repeat your password"
                            theme="bootstrap"/>
                <s:submit name="accountActivationSubmit"
                          cssClass="btn btn-primary pull-right"
                          theme="bootstrap"/>
            </fieldset>
        </form>
    </div>
</div>
</body>
</html>