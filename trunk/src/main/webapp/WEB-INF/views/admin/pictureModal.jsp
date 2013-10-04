<%--
 Modal use for select and upload pictures.
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
<!-- Picture Modal -->
<div id="pictureModal" class="modal hide fade modal-picture-dialog" tabindex="-1" role="dialog" aria-labelledby="pictureModal" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="pictureModalLabel">Insert picture</h3>
  </div>
  <div class="modal-body">
    <div>
      <ul id="pictureSelector" class="thumbnails">
      </ul>
    </div>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn btn-success" id="btnInsert">Insert picture</a>
    <a href="#" class="btn" data-dismiss="modal" aria-hidden="true">Close</a>
  </div>
</div>

<!-- Result Modal -->
<div id="resultModal" class="modal hide fade modal-picture-dialog" tabindex="-1" role="dialog" aria-labelledby="resultModal" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="resultModalLabel">Copy this code into your document.</h3>
  </div>
  <div class="modal-body">
    <div>
      <pre type="no-prettify" id="resultArea"></pre>
    </div>
  </div>
  <div class="modal-footer">
    <a href="#" class="btn" data-dismiss="modal" aria-hidden="true">Close</a>
  </div>
</div>

</body>
</html>