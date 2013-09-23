package org.apache.jsp.WEB_002dINF.views.common;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class navbarList_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_c_url_value_nobody;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_c_url_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_c_url_value_nobody.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("  <title></title>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<ul class=\"nav\">\r\n");
      out.write("  <li class=\"divider-vertical\"></li>\r\n");
      out.write("  <li>\r\n");
      out.write("    <a href=\"");
      if (_jspx_meth_c_url_0(_jspx_page_context))
        return;
      out.write("\">CDOJ</a>\r\n");
      out.write("  </li>\r\n");
      out.write("  <li class=\"divider-vertical\"></li>\r\n");
      out.write("  <li>\r\n");
      out.write("    <a href=\"#\">Problems</a>\r\n");
      out.write("  </li>\r\n");
      out.write("  <li class=\"divider-vertical\"></li>\r\n");
      out.write("  <li>\r\n");
      out.write("    <a href=\"#\">Contests</a>\r\n");
      out.write("  </li>\r\n");
      out.write("  <li class=\"divider-vertical\"></li>\r\n");
      out.write("  <li>\r\n");
      out.write("    <a href=\"#\">Status</a>\r\n");
      out.write("  </li>\r\n");
      out.write("  <li class=\"divider-vertical\"></li>\r\n");
      out.write("  <li>\r\n");
      out.write("    <a href=\"");
      if (_jspx_meth_c_url_1(_jspx_page_context))
        return;
      out.write("\">Users</a>\r\n");
      out.write("  </li>\r\n");
      out.write("  <li class=\"divider-vertical\"></li>\r\n");
      out.write("\r\n");
      out.write("  <li class=\"dropdown\">\r\n");
      out.write("    <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">Links<b class=\"caret\"></b></a>\r\n");
      out.write("    <ul class=\"dropdown-menu\">\r\n");
      out.write("      <li class=\"disabled\"><a href=\"#\">BBS</a></li>\r\n");
      out.write("      <li class=\"disabled\"><a href=\"#\">Wiki</a></li>\r\n");
      out.write("      <li><a href=\"#\">Training</a></li>\r\n");
      out.write("      <li class=\"disabled\"><a href=\"#\">Download</a></li>\r\n");
      out.write("      <li class=\"disabled\"><a href=\"#\">Step-by-Step</a></li>\r\n");
      out.write("      <li class=\"disabled\"><a href=\"#\">Team Honors</a></li>\r\n");
      out.write("\r\n");
      out.write("      <li class=\"divider\"></li>\r\n");
      out.write("      <li class=\"nav-header\">About</li>\r\n");
      out.write("      <li><a href=\"#\">F.A.Q</a></li>\r\n");
      out.write("      <li><a href=\"#\">Markdown</a></li>\r\n");
      out.write("      <li><a href=\"#\">Training system</a></li>\r\n");
      out.write("      <li><a href=\"#\">About</a></li>\r\n");
      out.write("    </ul>\r\n");
      out.write("  </li>\r\n");
      out.write("  <li class=\"divider-vertical\"></li>\r\n");
      out.write("</ul>\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_url_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:url
    org.apache.taglibs.standard.tag.rt.core.UrlTag _jspx_th_c_url_0 = (org.apache.taglibs.standard.tag.rt.core.UrlTag) _jspx_tagPool_c_url_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.UrlTag.class);
    _jspx_th_c_url_0.setPageContext(_jspx_page_context);
    _jspx_th_c_url_0.setParent(null);
    _jspx_th_c_url_0.setValue("/");
    int _jspx_eval_c_url_0 = _jspx_th_c_url_0.doStartTag();
    if (_jspx_th_c_url_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_url_value_nobody.reuse(_jspx_th_c_url_0);
      return true;
    }
    _jspx_tagPool_c_url_value_nobody.reuse(_jspx_th_c_url_0);
    return false;
  }

  private boolean _jspx_meth_c_url_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:url
    org.apache.taglibs.standard.tag.rt.core.UrlTag _jspx_th_c_url_1 = (org.apache.taglibs.standard.tag.rt.core.UrlTag) _jspx_tagPool_c_url_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.UrlTag.class);
    _jspx_th_c_url_1.setPageContext(_jspx_page_context);
    _jspx_th_c_url_1.setParent(null);
    _jspx_th_c_url_1.setValue("/user/list");
    int _jspx_eval_c_url_1 = _jspx_th_c_url_1.doStartTag();
    if (_jspx_th_c_url_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_url_value_nobody.reuse(_jspx_th_c_url_1);
      return true;
    }
    _jspx_tagPool_c_url_value_nobody.reuse(_jspx_th_c_url_1);
    return false;
  }
}
