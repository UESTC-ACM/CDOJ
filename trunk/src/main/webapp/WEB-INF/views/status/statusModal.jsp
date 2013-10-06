<%--
  Modals used in status list
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
<!-- Code Modal -->
<div id="codeModal" class="modal hide fade modal-large" tabindex="-1" role="dialog" aria-labelledby="codeModal" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="codeModalLabel"></h3>
    </div>
    <div class="modal-body-long" id="codeViewer">
    </div>
</div>

<!-- Compile Info Modal -->
<div id="compileInfoModal" class="modal hide fade modal-large" tabindex="-1" role="dialog" aria-labelledby="compileInfoModal" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="compileInfoModalLabel"></h3>
    </div>
    <div class="modal-body-long">
        <div>
            <pre id="compileInfoViewer"></pre>
        </div>
    </div>
</div>

</body>
</html>