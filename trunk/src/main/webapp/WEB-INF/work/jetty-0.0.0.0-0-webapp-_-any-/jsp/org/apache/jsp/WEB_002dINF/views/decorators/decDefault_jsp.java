package org.apache.jsp.WEB_002dINF.views.decorators;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class decDefault_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_page_applyDecorator_page_name_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_decorator_body_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_decorator_head_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_decorator_title_nobody;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_page_applyDecorator_page_name_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_decorator_body_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_decorator_head_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_decorator_title_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_page_applyDecorator_page_name_nobody.release();
    _jspx_tagPool_decorator_body_nobody.release();
    _jspx_tagPool_decorator_head_nobody.release();
    _jspx_tagPool_decorator_title_nobody.release();
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
      out.write("<!DOCTYPE html>\n");
      out.write("<html lang=\"en\">\n");
      out.write("<head>\n");
      out.write("  ");
      if (_jspx_meth_page_applyDecorator_0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("  ");
      if (_jspx_meth_decorator_head_0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\n");
      out.write("  <title>");
      if (_jspx_meth_decorator_title_0(_jspx_page_context))
        return;
      out.write(" - UESTC Online Judge</title>\n");
      out.write("</head>\n");
      out.write("\n");
      out.write("<body>\n");
      out.write("\n");
      if (_jspx_meth_page_applyDecorator_1(_jspx_page_context))
        return;
      out.write('\n');
      if (_jspx_meth_page_applyDecorator_2(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\n");
      out.write("<div id=\"wrap\">\n");
      out.write("  <div class=\"mzry1992\">\n");
      out.write("    <div class=\"container\">\n");
      out.write("\n");
      out.write("      ");
      if (_jspx_meth_page_applyDecorator_3(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\n");
      out.write("      ");
      if (_jspx_meth_decorator_body_0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("    </div>\n");
      out.write("  </div>\n");
      out.write("</div>\n");
      out.write("\n");
      if (_jspx_meth_page_applyDecorator_4(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\n");
      out.write("</body>\n");
      out.write("</html>\n");
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
    _jspx_th_page_applyDecorator_0.setName("head");
    _jspx_th_page_applyDecorator_0.setPage("/WEB-INF/views/common/header.jsp");
    int _jspx_eval_page_applyDecorator_0 = _jspx_th_page_applyDecorator_0.doStartTag();
    if (_jspx_th_page_applyDecorator_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_page_applyDecorator_page_name_nobody.reuse(_jspx_th_page_applyDecorator_0);
      return true;
    }
    _jspx_tagPool_page_applyDecorator_page_name_nobody.reuse(_jspx_th_page_applyDecorator_0);
    return false;
  }

  private boolean _jspx_meth_decorator_head_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  decorator:head
    com.opensymphony.module.sitemesh.taglib.decorator.HeadTag _jspx_th_decorator_head_0 = (com.opensymphony.module.sitemesh.taglib.decorator.HeadTag) _jspx_tagPool_decorator_head_nobody.get(com.opensymphony.module.sitemesh.taglib.decorator.HeadTag.class);
    _jspx_th_decorator_head_0.setPageContext(_jspx_page_context);
    _jspx_th_decorator_head_0.setParent(null);
    int _jspx_eval_decorator_head_0 = _jspx_th_decorator_head_0.doStartTag();
    if (_jspx_th_decorator_head_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_decorator_head_nobody.reuse(_jspx_th_decorator_head_0);
      return true;
    }
    _jspx_tagPool_decorator_head_nobody.reuse(_jspx_th_decorator_head_0);
    return false;
  }

  private boolean _jspx_meth_decorator_title_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  decorator:title
    com.opensymphony.module.sitemesh.taglib.decorator.TitleTag _jspx_th_decorator_title_0 = (com.opensymphony.module.sitemesh.taglib.decorator.TitleTag) _jspx_tagPool_decorator_title_nobody.get(com.opensymphony.module.sitemesh.taglib.decorator.TitleTag.class);
    _jspx_th_decorator_title_0.setPageContext(_jspx_page_context);
    _jspx_th_decorator_title_0.setParent(null);
    int _jspx_eval_decorator_title_0 = _jspx_th_decorator_title_0.doStartTag();
    if (_jspx_th_decorator_title_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_decorator_title_nobody.reuse(_jspx_th_decorator_title_0);
      return true;
    }
    _jspx_tagPool_decorator_title_nobody.reuse(_jspx_th_decorator_title_0);
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
    _jspx_th_page_applyDecorator_1.setPage("/WEB-INF/views/common/navbar.jsp");
    int _jspx_eval_page_applyDecorator_1 = _jspx_th_page_applyDecorator_1.doStartTag();
    if (_jspx_th_page_applyDecorator_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_page_applyDecorator_page_name_nobody.reuse(_jspx_th_page_applyDecorator_1);
      return true;
    }
    _jspx_tagPool_page_applyDecorator_page_name_nobody.reuse(_jspx_th_page_applyDecorator_1);
    return false;
  }

  private boolean _jspx_meth_page_applyDecorator_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  page:applyDecorator
    com.opensymphony.module.sitemesh.taglib.page.ApplyDecoratorTag _jspx_th_page_applyDecorator_2 = (com.opensymphony.module.sitemesh.taglib.page.ApplyDecoratorTag) _jspx_tagPool_page_applyDecorator_page_name_nobody.get(com.opensymphony.module.sitemesh.taglib.page.ApplyDecoratorTag.class);
    _jspx_th_page_applyDecorator_2.setPageContext(_jspx_page_context);
    _jspx_th_page_applyDecorator_2.setParent(null);
    _jspx_th_page_applyDecorator_2.setName("body");
    _jspx_th_page_applyDecorator_2.setPage("/WEB-INF/views/common/modal.jsp");
    int _jspx_eval_page_applyDecorator_2 = _jspx_th_page_applyDecorator_2.doStartTag();
    if (_jspx_th_page_applyDecorator_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_page_applyDecorator_page_name_nobody.reuse(_jspx_th_page_applyDecorator_2);
      return true;
    }
    _jspx_tagPool_page_applyDecorator_page_name_nobody.reuse(_jspx_th_page_applyDecorator_2);
    return false;
  }

  private boolean _jspx_meth_page_applyDecorator_3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  page:applyDecorator
    com.opensymphony.module.sitemesh.taglib.page.ApplyDecoratorTag _jspx_th_page_applyDecorator_3 = (com.opensymphony.module.sitemesh.taglib.page.ApplyDecoratorTag) _jspx_tagPool_page_applyDecorator_page_name_nobody.get(com.opensymphony.module.sitemesh.taglib.page.ApplyDecoratorTag.class);
    _jspx_th_page_applyDecorator_3.setPageContext(_jspx_page_context);
    _jspx_th_page_applyDecorator_3.setParent(null);
    _jspx_th_page_applyDecorator_3.setName("body");
    _jspx_th_page_applyDecorator_3.setPage("/WEB-INF/views/common/debug.jsp");
    int _jspx_eval_page_applyDecorator_3 = _jspx_th_page_applyDecorator_3.doStartTag();
    if (_jspx_th_page_applyDecorator_3.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_page_applyDecorator_page_name_nobody.reuse(_jspx_th_page_applyDecorator_3);
      return true;
    }
    _jspx_tagPool_page_applyDecorator_page_name_nobody.reuse(_jspx_th_page_applyDecorator_3);
    return false;
  }

  private boolean _jspx_meth_decorator_body_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  decorator:body
    com.opensymphony.module.sitemesh.taglib.decorator.BodyTag _jspx_th_decorator_body_0 = (com.opensymphony.module.sitemesh.taglib.decorator.BodyTag) _jspx_tagPool_decorator_body_nobody.get(com.opensymphony.module.sitemesh.taglib.decorator.BodyTag.class);
    _jspx_th_decorator_body_0.setPageContext(_jspx_page_context);
    _jspx_th_decorator_body_0.setParent(null);
    int _jspx_eval_decorator_body_0 = _jspx_th_decorator_body_0.doStartTag();
    if (_jspx_th_decorator_body_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_decorator_body_nobody.reuse(_jspx_th_decorator_body_0);
      return true;
    }
    _jspx_tagPool_decorator_body_nobody.reuse(_jspx_th_decorator_body_0);
    return false;
  }

  private boolean _jspx_meth_page_applyDecorator_4(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  page:applyDecorator
    com.opensymphony.module.sitemesh.taglib.page.ApplyDecoratorTag _jspx_th_page_applyDecorator_4 = (com.opensymphony.module.sitemesh.taglib.page.ApplyDecoratorTag) _jspx_tagPool_page_applyDecorator_page_name_nobody.get(com.opensymphony.module.sitemesh.taglib.page.ApplyDecoratorTag.class);
    _jspx_th_page_applyDecorator_4.setPageContext(_jspx_page_context);
    _jspx_th_page_applyDecorator_4.setParent(null);
    _jspx_th_page_applyDecorator_4.setName("body");
    _jspx_th_page_applyDecorator_4.setPage("/WEB-INF/views/common/footer.jsp");
    int _jspx_eval_page_applyDecorator_4 = _jspx_th_page_applyDecorator_4.doStartTag();
    if (_jspx_th_page_applyDecorator_4.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_page_applyDecorator_page_name_nobody.reuse(_jspx_th_page_applyDecorator_4);
      return true;
    }
    _jspx_tagPool_page_applyDecorator_page_name_nobody.reuse(_jspx_th_page_applyDecorator_4);
    return false;
  }
}
