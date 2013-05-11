<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib prefix="cdoj" uri="/WEB-INF/cdoj.tld" %>
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
 Admin contest editor page

 @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 @version 1
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="<s:url value="/scripts/cdoj/cdoj.admin.contestEditor.js"/>"></script>
    <script src="<s:url value="/scripts/bootstrap-datepicker.js"/>"></script>
    <link href="<s:url value="/styles/datepicker.css"/>" rel=stylesheet>
    <title>Contest</title>
</head>
<body>

<div class="row" id="contestEditor">
    <div class="span10">
        <form class="form-horizontal">
            <fieldset>
                <legend id="editorFlag" value="${editorFlag}">
                    <s:if test='editorFlag=="new"'>
                        Add new contest
                    </s:if>
                    <s:else>
                        Edit contest <span id="problemId">${targetContest.contestId}</span>
                    </s:else>
                </legend>

                <div class="row">
                    <div class="span10">
                        <div class="control-group">
                            <label class="control-label">Title</label>
                            <div class="controls">
                                <input type="text"
                                       name="contestDTO.title"
                                       maxlength="50"
                                       value="${contestDTO.title}"
                                       id="contestDTO_title"
                                       class="span6">
                            </div>
                        </div>
                    </div>
                    <div class="span10">
                        <div class="control-group">
                            <label class="control-label">Type</label>
                            <div class="controls">
                            </div>
                        </div>
                    </div>
                    <div class="span10">
                        <div class="control-group">
                            <label class="control-label">Begin time</label>
                            <div class="controls">
                                <input type="text"
                                       maxlength="10"
                                       name="contestDTO.time.days"
                                       id="contestDTO_time_days"
                                       style="width: 80px;">
                                <input type="text"
                                       maxlength="2"
                                       name="contestDTO.time.hours"
                                       id="contestDTO_time_hours"
                                       value="00"
                                       style="width: 20px;">
                                <span>:</span>
                                <input type="text"
                                       maxlength="2"
                                       name="contestDTO.time.minutes"
                                       id="contestDTO_time_minutes"
                                       value="00"
                                       style="width: 20px;">
                                <span>:</span>
                                <input type="text"
                                       maxlength="2"
                                       name="contestDTO.time.seconds"
                                       id="contestDTO_time_seconds"
                                       readonly="true"
                                       value="00"
                                       style="width: 20px;">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">Length</label>
                            <div class="controls">
                                <input type="text"
                                       maxlength="10"
                                       name="contestDTO.length.days"
                                       id="contestDTO_length_days"
                                       style="width: 80px;">
                                <span>days</span>
                                <input type="text"
                                       maxlength="2"
                                       name="contestDTO.length.hours"
                                       id="contestDTO_length_hours"
                                       value="00"
                                       style="width: 20px;">
                                <span>:</span>
                                <input type="text"
                                       maxlength="2"
                                       name="contestDTO.length.minutes"
                                       id="contestDTO_length_minutes"
                                       value="00"
                                       style="width: 20px;">
                                <span>:</span>
                                <input type="text"
                                       maxlength="2"
                                       name="contestDTO.length.seconds"
                                       id="contestDTO_length_seconds"
                                       readonly="true"
                                       value="00"
                                       style="width: 20px;">
                            </div>
                        </div>
                    </div>
                    <div class="span10">
                        <div class="control-group">
                            <label class="control-label">Description</label>
                            <div class="controls">
                                <textarea class="span6"
                                          rows="8"
                                          name="contestDTO.description"
                                          maxlength="200"
                                          value="${contestDTO.description}"
                                          id="contestDTO_description"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="span10">
                        <div class="control-group">
                            <label class="control-label">Problem list</label>
                            <div class="controls">
                                <div class="row">
                                    <div class="span6">
                                        <table class="table table-striped table-bordered">
                                            <thead>
                                            <tr>
                                                <th style="width: 14px;"><a href="#" id="add_problem"><i class="icon-plus"></i></a></th>
                                                <th style="width: 60px;">Id</th>
                                                <th>Title</th>
                                                <th style="width: 70px;">Difficulty</th>
                                            </tr>
                                            </thead>
                                            <tbody id="problemList">
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

                <div class="form-actions">
                    <s:submit name="submit"
                              cssClass="btn btn-primary"
                              value="Submit"
                              theme="bootstrap"/>
                </div>
            </fieldset>
        </form>
    </div>
</div>

</body>
</html>