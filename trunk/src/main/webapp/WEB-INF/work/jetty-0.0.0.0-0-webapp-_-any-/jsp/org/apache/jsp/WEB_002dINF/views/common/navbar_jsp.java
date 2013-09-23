package org.apache.jsp.WEB_002dINF.views.common;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class navbar_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_page_applyDecorator_page_name_nobody;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_page_applyDecorator_page_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_page_applyDecorator_page_name_nobody.release();
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

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("  <title></title>\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("<div class=\"navbar navbar-fixed-top navbar-inverse\">\n");
      out.write("  <div class=\"navbar-inner\">\n");
      out.write("    <div class=\"container\">\n");
      out.write("        ");
      if (_jspx_meth_page_applyDecorator_0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("      <form class=\"navbar-search pull-right\" action=\"\">\n");
      out.write("        <!-- TODO please check idea warning -->\n");
      out.write("        <input type=\"text\" class=\"search-query span2\" placeholder=\"Search\">\n");
      out.write("      </form>\n");
      out.write("\n");
      out.write("      ");
      if (_jspx_meth_page_applyDecorator_1(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\n");
      out.write("    </div>\n");
      out.write("  </div>\n");
      out.write("</div>\n");
      out.write("</body>\n");
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

  private boolean _jspx_meth_page_applyDecorator_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  page:applyDecorator
    com.opensymphony.module.sitemesh.taglib.page.ApplyDecoratorTag _jspx_th_page_applyDecorator_0 = (com.opensymphony.module.sitemesh.taglib.page.ApplyDecoratorTag) _jspx_tagPool_page_applyDecorator_page_name_nobody.get(com.opensymphony.module.sitemesh.taglib.page.ApplyDecoratorTag.class);
    _jspx_th_page_applyDecorator_0.setPageContext(_jspx_page_context);
    _jspx_th_page_applyDecorator_0.setParent(null);
    _jspx_th_page_applyDecorator_0.setName("body");
    _jspx_th_page_applyDecorator_0.setPage("/WEB-INF/views/common/navbarList.jsp");
    int _jspx_eval_page_applyDecorator_0 = _jspx_th_page_applyDecorator_0.doStartTag();
    if (_jspx_th_page_applyDecorator_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_page_applyDecorator_page_name_nobody.reuse(_jspx_th_page_applyDecorator_0);
      return true;
    }
    _jspx_tagPool_page_applyDecorator_page_name_nobody.reuse(_jspx_th_page_applyDecorator_0);
    return false;
  }

  private boolean _jspx_meth_page_applyDecorator_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  page:applyDecorator
    com.opensymphony.module.sitemesh.taglib.page.ApplyDecoratorTag _jspx_th_page_applyDecorator_1 = (com.opensymphony.module.sitemesh.taglib.page.ApplyDecoratorTag) _jspx_tagPool_page_applyDecorator_page_name_nobody.get(com.opensymphony.module.sitemesh.taglib.page.ApplyDecoratorTag.class);
    _jspx_th_page_applyDecorator_1.setPageContext(_jspx_page_context);
    _jspx_th_page_applyDecorator_1.setParent(null);
    _jspx_th_page_applyDecorator_1.setName("body");
    _jspx_th_page_applyDecorator_1.setPage("/WEB-INF/views/common/navbarUser.jsp");
    int _jspx_eval_page_applyDecorator_1 = _jspx_th_page_applyDecorator_1.doStartTag();
    if (_jspx_th_page_applyDecorator_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_page_applyDecorator_page_name_nobody.reuse(_jspx_th_page_applyDecorator_1);
      return true;
    }
    _jspx_tagPool_page_applyDecorator_page_name_nobody.reuse(_jspx_th_page_applyDecorator_1);
    return false;
  }
}
