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
  Date: 13-3-13
  Time: 下午3:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<!-- Problem submit Modal -->
<div id="problemSubmitModal" class="modal hide fade modal-large" tabindex="-1" role="dialog" aria-labelledby="loginModal" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="loginModalLabel">Submit ${targetProblem.title}</h3>
        <span style="display: none" id="submitProblemId" value="${targetProblem.problemId}"/>
    </div>
    <div class="modal-body">
        <form class="form-horizontal" style="height: 380px;">
            <fieldset>
                <textarea class="submit-area" id="codeContent"></textarea>
            </fieldset>
        </form>
    </div>
    <div class="modal-footer">
        <div id="language-selector" class="pull-left">
            <div class="btn-group" data-toggle="buttons-radio" id="languageSelector">
                <s:iterator value="global.languageList">
                    <button class="btn btn-info <s:if test="languageId == 2">active</s:if>" value="${languageId}">${name}</button>
                </s:iterator>
            </div>
        </div>

        <a href="#" class="btn" data-dismiss="modal" aria-hidden="true">Close</a>
        <a href="#" class="btn btn-primary">Submit</a>
    </div>
</div>

</body>
</html>