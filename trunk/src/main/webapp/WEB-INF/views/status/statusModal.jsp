<%--
  Modals used in status list
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title></title>
</head>
<body>
<script type="text/ng-template" id="codeModal.html">
  <div class="modal-body">
    <ui-code code="code"></ui-code>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-default">Copy</button>
    <button type="button" class="btn btn-default">Download</button>
  </div>
</script>

<script type="text/ng-template" id="compileInfoModal.html">
  <div class="modal-body">
    <pre ng-bind="compileInfo"></pre>
  </div>
</script>

</body>
</html>