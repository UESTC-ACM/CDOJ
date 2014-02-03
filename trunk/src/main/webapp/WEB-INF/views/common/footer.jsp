<%--
Footer part of all pages.

@author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
           prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <script src="<c:url value="/plugins/MathJax/MathJax.js?config=TeX-AMS-MML_HTMLorMML"/>"></script>
  <script src="<c:url value="/plugins/cdoj/dist/js/cdoj.dependencies.js"/>"></script>
  <script src="<c:url value="/plugins/cdoj/dist/js/cdoj.jquery.js"/>"></script>
  <script src="<c:url value="/plugins/cdoj/dist/js/cdoj.angular.js"/>"></script>
  <script>
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
      (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
        m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

    ga('create', 'UA-47741696-1', 'uestc.edu.cn');
    ga('send', 'pageview');

  </script>
</head>
<body>
</body>
</html>
