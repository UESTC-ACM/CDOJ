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
 User center page

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
    <!--[if lte IE 8]>
    <script src="<s:url value="/scripts/r2d3.js"/>"></script>
    <![endif]-->
    <!--[if gte IE 9]><!-->
    <script src="<s:url value="/scripts/d3.js"/>"></script>
    <!--<![endif]-->

    <script src="<s:url value="/scripts/cdoj/cdoj.user.center.js"/>"></script>
    <title><s:property value="targetUser.userName"/></title>
</head>
<body>
<div id="userInfoWrap" class="row">
    <div id="userInfoLeft" class="span9">
        <div class="row">
            <div class="span9">
                <div id="userInfo">
                    <dl class="dl-userInfo">
                        <dt>Nick name</dt>
                        <dd>
                            <s:property escape="false" value="targetUser.nickName"/>
                            <s:if test="currentUser.userName == targetUser.userName">
                                <div class="pull-right" style="margin-right: 20px;">
                                    <a href="#" onclick="return editUserDialog(<s:property value="targetUser.userId"/>)">
                                        <i class="icon-pencil"></i>
                                        Edit Your Profile
                                    </a>
                                    <s:a action="editor/%{targetProblem.problemId}" namespace="/admin/problem">
                                    </s:a>
                                </div>
                            </s:if>
                        </dd>
                        <dt>School</dt>
                        <dd><s:property value="targetUser.school"/></dd>
                        <dt>Department</dt>
                        <dd><s:property value="targetUser.department"/></dd>
                        <s:if test="currentUser.userName == targetUser.userName">
                            <dt>Student ID</dt>
                            <dd><s:property value="targetUser.studentId"/></dd>
                            <dt>Email</dt>
                            <dd><s:property value="targetUser.email"/></dd>
                        </s:if>
                        <dt>Last login</dt>
                        <dd class="cdoj-time" type="milliseconds" isUTC="true"><s:property value="targetUser.lastLogin.time"/></dd>
                    </dl>
                </div>
            </div>
            <div class="span9">
                <div id="userSolvedList">
                    <div id="chart">
                    </div>
                </div>
            </div>
        </div>

    </div>
    <div id="userInfoRight" class="span3">
        <div id="userInfoSummary">
            <a id="userAvatarWrap"
               class="thumbnail"
               href="#"
               rel="tooltip"
               data-original-title="Change your avatar at gravatar.com">
                <img id="userAvatar-large" email="<s:property value="targetUser.email"/>" type="avatar" size="220"/>
            </a>
            <span class="userName-type<s:property value="targetUser.type"/>">
                <h4>
                    <s:property escape="false" value="targetUser.nickName"/>
                </h4>
                <h4 id="currentUserPageUser" value="<s:property value="targetUser.userName"/>">
                    <s:property value="targetUser.userName"/>
                </h4>
            </span>
            <span>
                <ul class="userStates">
                    <li>
                        <a href="#"><strong><s:property value="targetUser.tried"/></strong>Tried</a>
                    </li>
                    <li>
                        <a href="#"><strong><s:property value="targetUser.solved"/></strong>Solved</a>
                    </li>
                </ul>
            </span>
        </div>
    </div>
</div>

<s:if test="currentUser.userName == targetUser.userName">
    <!-- User edit Modal -->
    <div id="userEditModal" class="modal hide fade modal-large" tabindex="-1" role="dialog"
         aria-labelledby="userEditModal" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3 id="userEditModalLabel"></h3>
        </div>
        <div class="modal-body">
            <form class="form-horizontal">
                <fieldset>
                    <s:textfield name="userDTO.userId"
                                 maxLength="20"
                                 cssClass="span4"
                                 label="User ID"
                                 readonly="true"
                                 theme="bootstrap"/>
                    <s:textfield name="userDTO.nickName"
                                 maxLength="20"
                                 cssClass="span4"
                                 label="Nick name"
                                 readonly="false"
                                 theme="bootstrap"/>
                    <s:textfield name="userDTO.email"
                                 maxLength="100"
                                 cssClass="span4"
                                 label="Email"
                                 readonly="true"
                                 theme="bootstrap"/>
                    <s:textfield name="userDTO.school"
                                 maxLength="50"
                                 cssClass="span4"
                                 value="UESTC"
                                 label="School"
                                 theme="bootstrap"/>
                    <s:select name="userDTO.departmentId"
                              list="global.departmentList"
                              listKey="departmentId"
                              listValue="name"
                              cssClass="span4"
                              label="Department"
                              theme="bootstrap"/>
                    <s:textfield name="userDTO.studentId"
                                 maxLength="20"
                                 cssClass="span4"
                                 label="Student ID"
                                 theme="bootstrap"/>
                    <s:password name="userDTO.password"
                                maxLength="20"
                                cssClass="span4"
                                label="New password"
                                theme="bootstrap"/>
                    <s:password name="userDTO.passwordRepeat"
                                maxLength="20"
                                cssClass="span4"
                                label="Repeat new password"
                                theme="bootstrap"/>
                    <s:password name="userDTO.oldPassword"
                                maxLength="20"
                                cssClass="span4"
                                label="Your password"
                                theme="bootstrap"/>

                </fieldset>
            </form>
        </div>
        <div class="modal-footer">
            <a href="#" class="btn" data-dismiss="modal" aria-hidden="true">Cancel</a>
            <a href="#" class="btn btn-primary">Update</a>
        </div>
    </div>
</s:if>

</body>
</html>