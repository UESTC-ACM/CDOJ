<%--suppress XmlDuplicatedId --%>
<%--
 All modal will used on every page
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title></title>
</head>
<body>
<!-- Login Modal -->
<div id="loginModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="loginModal" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="loginModalLabel">Login</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal">
      <fieldset>
        <div class="control-group "><label class="control-label" for="userName">User Name</label><div class="controls">

          <input type="text" name="userName" maxlength="24" value="" id="userName" class="span4"></div>
        </div>

        <div class="control-group "><label class="control-label" for="password">Password</label><div class="controls">
          <input type="password" name="password" maxlength="20" id="password" class="span4"></div>
        </div>

      </fieldset>
    </form>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn btn-primary">Login</a>
    <a href="#" class="btn" data-dismiss="modal" aria-hidden="true">Close</a>
  </div>
</div>

<!-- Register Modal -->
<div id="registerModal" class="modal hide fade modal-large" tabindex="-1" role="dialog" aria-labelledby="registerModal" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="registerModalLabel">Register</h3>
  </div>
  <div class="modal-body" style="max-height: 450px; ">
    <form class="form-horizontal">
      <fieldset>
        <div class="control-group "><label class="control-label" for="userName">User Name</label><div class="controls">

          <input type="text" name="userName" maxlength="24" value="" id="userName" class="span4"></div>
        </div>

        <div class="control-group "><label class="control-label" for="password">Password</label><div class="controls">
          <input type="password" name="password" maxlength="20" id="password" class="span4"></div>
        </div>

        <div class="control-group "><label class="control-label" for="passwordRepeat">Repeat your password</label><div class="controls">
          <input type="password" name="passwordRepeat" maxlength="20" id="passwordRepeat" class="span4"></div>
        </div>

        <div class="control-group "><label class="control-label" for="nickName">Nick name</label><div class="controls">

          <input type="text" name="nickName" maxlength="20" value="" id="nickName" class="span4"></div>
        </div>

        <div class="control-group "><label class="control-label" for="email">Email</label><div class="controls">

          <input type="text" name="email" maxlength="100" value="" id="email" class="span4"></div>
        </div>

        <div class="control-group ">
          <label class="control-label"></label>
          <div class="controls">
            Your email will be used for <a href="http://en.gravatar.com/">Gravatar</a> server and get back your password.
          </div>
        </div>
        <div class="control-group "><label class="control-label" for="school">School</label><div class="controls">

          <input type="text" name="school" maxlength="50" value="UESTC" id="school" class="span4"></div>
        </div>

        <div class="control-group "><label class="control-label" for="departmentId">Department</label><div class="controls">
          <select name="departmentId" id="departmentId" class="span4">
            <c:forEach var="department" items="${departmentList}">
              <option value="${department.departmentId}"><c:out value="${department.name}"/></option>
            </c:forEach>
          </select>
        </div>
        </div>

        <div class="control-group "><label class="control-label" for="studentId">Student ID</label><div class="controls">

          <input type="text" name="studentId" maxlength="20" value="" id="studentId" class="span4"></div>
        </div>

      </fieldset>
    </form>
  </div>

  <div class="modal-footer">
    <a href="#" class="btn btn-primary">Register</a>
    <a href="#" class="btn" data-dismiss="modal" aria-hidden="true">Close</a>
  </div>
</div>

<!-- User activate Modal -->
<div id="activateModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="activateModal" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="activateModalLabel">Forget password</h3>
  </div>
  <div class="modal-body">
    <form class="form-horizontal">
      <fieldset>
        <div class="control-group "><label class="control-label" for="userName">User Name</label><div class="controls">
          <input type="text" name="userName" maxlength="24" value="" id="userName" class="span4"></div>
        </div>
      </fieldset>
    </form>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn btn-primary">Send Email</a>
    <a href="#" class="btn" data-dismiss="modal" aria-hidden="true">Close</a>
  </div>
</div>
</body>
</html>