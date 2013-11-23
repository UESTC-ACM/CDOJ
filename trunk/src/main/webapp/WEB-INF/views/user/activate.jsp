<%--
 Account activate page.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
  prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<script src="<c:url value="/scripts/cdoj/cdoj.user.activate.js"/>"></script>
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
          <div class="control-group ">
            <label class="control-label" for="userName">User
              Name</label>
            <div class="controls">

              <input type="text" name="userName" maxlength="24"
                value="<c:out value="${userName}"/>" readonly="readonly"
                id="userName" class="span4">
            </div>
          </div>

          <div class="control-group ">
            <label class="control-label" for="serialKey">Serial
              Key</label>
            <div class="controls">

              <input type="text" name="serialKey" maxlength="48"
                value="<c:out value="${serialKey}"/>"
                readonly="readonly" id="serialKey" class="span4">
            </div>
          </div>

          <div class="control-group ">
            <label class="control-label" for="password">New
              password</label>
            <div class="controls">
              <input type="password" name="password" maxlength="20"
                id="password" class="span4">
            </div>
          </div>

          <div class="control-group ">
            <label class="control-label" for="passwordRepeat">Repeat
              your password</label>
            <div class="controls">
              <input type="password" name="passwordRepeat"
                maxlength="20" id="passwordRepeat" class="span4">
            </div>
          </div>

          <input type="submit" id="accountActivationSubmit"
            name="accountActivationSubmit" value="Submit"
            class="btn btn-primary pull-right">

        </fieldset>
      </form>
    </div>
  </div>
</body>
</html>