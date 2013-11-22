<%--
 redirect page
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
  prefix="decorator"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
  (function() {
    var url = "<c:out value="${url}"/>";
    url ? location.href = url : history.back();
  })();
</script>
<title>Redirect</title>
</head>
<body>
</body>
</html>