<%--
 Footer part of all pages.

 @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
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
<div id="footer">
  <div class="container">
    <div class="row">
      <div class="span3 pull-left">
        <a id="logo-banner" href="<c:url value="/"/>"></a>
      </div>
      <div class="span9">
        <p class="muted credit">
          UESTC Online Judge
        </p>

        <p class="muted credit">
          Gnomovision version 69, Copyright (C) 2012 lyhypacm, mzry1992
          <br/>
          Gnomovision comes with ABSOLUTELY NO WARRANTY
          <br/>
          This is free software, and you are welcome to redistribute it under <a
            href="http://www.gnu.org/licenses/old-licenses/gpl-2.0.txt">certain conditions</a>
        </p>
      </div>
    </div>
  </div>
</div>

</body>
</html>