<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib prefix="cdoj" uri="/WEB-INF/cdoj.tld" %>
<%--
  ~ /*
  ~  * cdoj, UESTC ACMICPC Online Judge
  ~  * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
  ~  * 	mzry1992 <@link muziriyun@gmail.com>
  ~  *
  ~  * This program is free software; you can redistribute it and/or
  ~  * modify it under the terms of the GNU General Public License
  ~  * as published by the Free Software Foundation; either version 2
  ~  * of the License, or (at your option) any later version.
  ~  *
  ~  * This program is distributed in the hope that it will be useful,
  ~  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~  * GNU General Public License for more details.
  ~  *
  ~  * You should have received a copy of the GNU General Public License
  ~  * along with this program; if not, write to the Free Software
  ~  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
  ~  */
  --%>

<%--
 Footer part of all pages.

 @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 @version 1
--%>
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
                <s:a id="logo-banner" action="index" namespace="/"/>
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