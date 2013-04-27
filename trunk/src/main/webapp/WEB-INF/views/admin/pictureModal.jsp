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
 Modal use for select and upload pictures.

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