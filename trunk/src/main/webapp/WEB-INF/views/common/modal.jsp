<%--suppress XmlDuplicatedId --%>
<%--
All modal will used on every page
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
  prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title></title>
</head>
<body>

  <c:choose>
    <c:when test="${sessionScope.currentUser == null}">
      <div class="modal fade" id="cdoj-register-modal" tabindex="-1"
        role="dialog">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true">&times;</button>
              <h4 class="modal-title">Register</h4>
            </div>
            <div class="modal-body">
              <form class="form-horizontal" id="cdoj-register-form">
                <fieldset>
                  <div class="form-group">
                    <label class="control-label col-sm-4 "
                      for="userName">User Name</label>
                    <div class="col-sm-8">
                      <input type="text" name="userName" maxlength="24"
                        value="" id="userName"
                        class="form-control input-sm" />
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="control-label col-sm-4 "
                      for="password">Password</label>
                    <div class="col-sm-8">
                      <input type="password" name="password"
                        maxlength="20" id="password"
                        class="form-control input-sm" />
                    </div>
                  </div>
                  <div class="form-group ">
                    <label class="control-label col-sm-4 "
                      for="passwordRepeat">Repeat your password</label>
                    <div class="col-sm-8">
                      <input type="password" name="passwordRepeat"
                        maxlength="20" id="passwordRepeat"
                        class="form-control input-sm" />
                    </div>
                  </div>
                  <div class="form-group ">
                    <label class="control-label col-sm-4 "
                      for="nickName">Nick name</label>
                    <div class="col-sm-8">
                      <input type="text" name="nickName" maxlength="20"
                        value="" id="nickName"
                        class="form-control input-sm" />
                    </div>
                  </div>
                  <div class="form-group ">
                    <label class="control-label col-sm-4 " for="email">Email</label>
                    <div class="col-sm-8">
                      <input type="text" name="email" maxlength="100"
                        value="" id="email"
                        class="form-control input-sm" />
                      <spanp class="help-block"> Your email will
                      be used for <a href="http://en.gravatar.com/">Gravatar</a>
                      service. </spanp>
                    </div>
                  </div>
                  <div class="form-group ">
                    <label class="control-label col-sm-4 " for="school">School</label>
                    <div class="col-sm-8">
                      <input type="text" name="school" maxlength="50"
                        value="UESTC" id="school"
                        class="form-control input-sm" />
                    </div>
                  </div>
                  <div class="form-group ">
                    <label class="control-label col-sm-4 "
                      for="departmentId">Department</label>
                    <div class="col-sm-8">
                      <select name="departmentId" id="departmentId"
                        class="form-control input-sm">
                        <c:forEach var="department"
                          items="${departmentList}">
                          <option value="${department.departmentId}"><c:out
                              value="${department.name}" /></option>
                        </c:forEach>
                      </select>
                    </div>
                  </div>
                  <div class="form-group ">
                    <label class="control-label col-sm-4 "
                      for="studentId">Student ID</label>
                    <div class="col-sm-8">
                      <input type="text" name="studentId" maxlength="20"
                        value="" id="studentId"
                        class="form-control input-sm" />
                    </div>
                  </div>
                  <div class="form-group ">
                    <label class="control-label col-sm-4 " for="motto">Motto</label>
                    <div class="col-sm-8">
                      <textarea class="form-control" rows="3"
                        name="motto" id="motto"></textarea>
                    </div>
                  </div>
                </fieldset>
              </form>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default"
                data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary"
                id="cdoj-register-button">Register</button>
            </div>
          </div>
        </div>
      </div>

      <div class="modal fade" id="cdoj-activate-modal" tabindex="-1"
        role="dialog">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true">&times;</button>
              <h4 class="modal-title">Forget password</h4>
            </div>
            <div class="modal-body">
              <form class="form-horizontal" id="cdoj-activate-form">
                <div class="form-group">
                  <label class="control-label col-sm-4 " for="userName">User
                    Name</label>
                  <div class="col-sm-8">
                    <input type="text" name="userName" maxlength="24"
                      value="" id="userName"
                      class="form-control input-sm" />
                  </div>
                </div>
              </form>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default"
                data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary"
                id="cdoj-activate-button">Send Email</button>
            </div>
          </div>
        </div>
      </div>
    </c:when>
    <c:otherwise>
      <div class="modal fade" id="cdoj-profile-edit-modal" tabindex="-1"
        role="dialog">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal"
                aria-hidden="true">&times;</button>
              <h4 class="modal-title">Edit profile</h4>
            </div>
            <div class="modal-body">
              <form class="form-horizontal" id="cdoj-profile-edit-form">
                <fieldset>
                  <div class="form-group">
                    <label class="control-label col-sm-4 "
                      for="userName">User Name</label>
                    <div class="col-sm-8">
                      <input type="text" name="userName" maxlength="24"
                        value="<c:out value="${currentUser.userName}"/>"
                        readonly="readonly" id="userName"
                        class="form-control input-sm" />
                    </div>
                  </div>
                  <div class="form-group ">
                    <label class="control-label col-sm-4 "
                      for="newPassword">New password</label>
                    <div class="col-sm-8">
                      <input type="password" name="newPassword"
                        maxlength="20" id="newPassword" 
                        class="form-control input-sm" />
                    </div>
                  </div>
                  <div class="form-group ">
                    <label class="control-label col-sm-4 "
                      for="newPasswordRepeat">Repeat new
                      password</label>
                    <div class="col-sm-8">
                      <input type="password" name="newPasswordRepeat"
                        maxlength="20" id="newPasswordRepeat"
                        class="form-control input-sm" />
                    </div>
                  </div>
                  <div class="form-group ">
                    <label class="control-label col-sm-4 "
                      for="nickName">Nick name</label>
                    <div class="col-sm-8">
                      <input type="text" name="nickName" maxlength="20"
                        value="<c:out value="${currentUser.nickName}"/>"
                        id="nickName" class="form-control input-sm" />
                    </div>
                  </div>
                  <div class="form-group ">
                    <label class="control-label col-sm-4 " for="email">Email</label>
                    <div class="col-sm-8">
                      <input type="text" name="email" maxlength="100"
                        value="<c:out value="${currentUser.email}"/>"
                        id="email" readonly="readonly"
                        class="form-control input-sm" />
                    </div>
                  </div>
                  <div class="form-group ">
                    <label class="control-label col-sm-4 " for="school">School</label>
                    <div class="col-sm-8">
                      <input type="text" name="school" maxlength="50"
                        value="<c:out value="${currentUser.school}"/>"
                        id="school" class="form-control input-sm" />
                    </div>
                  </div>
                  <div class="form-group ">
                    <label class="control-label col-sm-4 "
                      for="departmentId">Department</label>
                    <div class="col-sm-8">
                      <select name="departmentId" id="departmentId"
                        class="form-control input-sm" value="3">
                        <c:forEach var="department"
                          items="${departmentList}">
                          <c:choose>
                            <c:when
                              test="${currentUser.departmentId == department.departmentId}">
                              <option value="${department.departmentId}"
                                selected="selected"><c:out
                                  value="${department.name}" /></option>
                            </c:when>
                            <c:otherwise>
                              <option value="${department.departmentId}"><c:out
                                  value="${department.name}" /></option>
                            </c:otherwise>
                          </c:choose>
                        </c:forEach>
                      </select>
                    </div>
                  </div>
                  <div class="form-group ">
                    <label class="control-label col-sm-4 "
                      for="studentId">Student ID</label>
                    <div class="col-sm-8">
                      <input type="text" name="studentId" maxlength="20"
                        value="<c:out value="${currentUser.studentId}"/>"
                        id="studentId" class="form-control input-sm" />
                    </div>
                  </div>
                  <div class="form-group ">
                    <label class="control-label col-sm-4 " for="motto">Motto</label>
                    <div class="col-sm-8">
                      <textarea class="form-control" rows="3"
                        name="motto" id="motto"><c:out
                          value="${currentUser.motto}" escapeXml="true" /></textarea>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="control-label col-sm-4 "
                      for="oldPassword">Password</label>
                    <div class="col-sm-8">
                      <input type="password" name="oldPassword"
                        maxlength="20" id="oldPassword"
                        class="form-control input-sm" />
                    </div>
                  </div>
                </fieldset>
              </form>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default"
                data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary"
                id="cdoj-profile-edit-button">Edit</button>
            </div>
          </div>
        </div>
      </div>
    </c:otherwise>
  </c:choose>
</body>
</html>
