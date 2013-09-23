package org.apache.jsp.WEB_002dINF.views.common;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class modal_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_c_out_value_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_c_forEach_var_items;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_c_out_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_c_forEach_var_items = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_c_out_value_nobody.release();
    _jspx_tagPool_c_forEach_var_items.release();
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

      out.write('\n');
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
      out.write("<!-- Login Modal -->\n");
      out.write("<div id=\"loginModal\" class=\"modal hide fade\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"loginModal\" aria-hidden=\"true\">\n");
      out.write("  <div class=\"modal-header\">\n");
      out.write("    <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">×</button>\n");
      out.write("    <h3 id=\"loginModalLabel\">Login</h3>\n");
      out.write("  </div>\n");
      out.write("  <div class=\"modal-body\">\n");
      out.write("    <form class=\"form-horizontal\">\n");
      out.write("      <fieldset>\n");
      out.write("        <div class=\"control-group \"><label class=\"control-label\" for=\"userName\">User Name</label><div class=\"controls\">\n");
      out.write("\n");
      out.write("          <input type=\"text\" name=\"userName\" maxlength=\"24\" value=\"\" id=\"userName\" class=\"span4\"></div>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("        <div class=\"control-group \"><label class=\"control-label\" for=\"password\">Password</label><div class=\"controls\">\n");
      out.write("          <input type=\"password\" name=\"password\" maxlength=\"20\" id=\"password\" class=\"span4\"></div>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("      </fieldset>\n");
      out.write("    </form>\n");
      out.write("  </div>\n");
      out.write("  <div class=\"modal-footer\">\n");
      out.write("    <a href=\"#\" class=\"btn btn-primary\">Login</a>\n");
      out.write("    <a href=\"#\" class=\"btn\" data-dismiss=\"modal\" aria-hidden=\"true\">Close</a>\n");
      out.write("  </div>\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("<!-- Register Modal -->\n");
      out.write("<div id=\"registerModal\" class=\"modal hide fade modal-large\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"registerModal\" aria-hidden=\"true\">\n");
      out.write("  <div class=\"modal-header\">\n");
      out.write("    <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">×</button>\n");
      out.write("    <h3 id=\"registerModalLabel\">Register</h3>\n");
      out.write("  </div>\n");
      out.write("  <div class=\"modal-body\" style=\"max-height: 450px; \">\n");
      out.write("    <form class=\"form-horizontal\">\n");
      out.write("      <fieldset>\n");
      out.write("        <div class=\"control-group \"><label class=\"control-label\" for=\"userName\">User Name</label><div class=\"controls\">\n");
      out.write("\n");
      out.write("          <input type=\"text\" name=\"userName\" maxlength=\"24\" value=\"\" id=\"userName\" class=\"span4\"></div>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("        <div class=\"control-group \"><label class=\"control-label\" for=\"password\">Password</label><div class=\"controls\">\n");
      out.write("          <input type=\"password\" name=\"password\" maxlength=\"20\" id=\"password\" class=\"span4\"></div>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("        <div class=\"control-group \"><label class=\"control-label\" for=\"passwordRepeat\">Repeat your password</label><div class=\"controls\">\n");
      out.write("          <input type=\"password\" name=\"passwordRepeat\" maxlength=\"20\" id=\"passwordRepeat\" class=\"span4\"></div>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("        <div class=\"control-group \"><label class=\"control-label\" for=\"nickName\">Nick name</label><div class=\"controls\">\n");
      out.write("\n");
      out.write("          <input type=\"text\" name=\"nickName\" maxlength=\"20\" value=\"\" id=\"nickName\" class=\"span4\"></div>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("        <div class=\"control-group \"><label class=\"control-label\" for=\"email\">Email</label><div class=\"controls\">\n");
      out.write("\n");
      out.write("          <input type=\"text\" name=\"email\" maxlength=\"100\" value=\"\" id=\"email\" class=\"span4\"></div>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("        <div class=\"control-group \">\n");
      out.write("          <label class=\"control-label\"></label>\n");
      out.write("          <div class=\"controls\">\n");
      out.write("            Your email will be used for <a href=\"http://en.gravatar.com/\">Gravatar</a> server and get back your password.\n");
      out.write("          </div>\n");
      out.write("        </div>\n");
      out.write("        <div class=\"control-group \"><label class=\"control-label\" for=\"school\">School</label><div class=\"controls\">\n");
      out.write("\n");
      out.write("          <input type=\"text\" name=\"school\" maxlength=\"50\" value=\"UESTC\" id=\"school\" class=\"span4\"></div>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("        <div class=\"control-group \"><label class=\"control-label\" for=\"departmentId\">Department</label><div class=\"controls\">\n");
      out.write("          <select name=\"departmentId\" id=\"departmentId\" class=\"span4\">\n");
      out.write("            ");
      if (_jspx_meth_c_forEach_0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("          </select>\n");
      out.write("        </div>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("        <div class=\"control-group \"><label class=\"control-label\" for=\"studentId\">Student ID</label><div class=\"controls\">\n");
      out.write("\n");
      out.write("          <input type=\"text\" name=\"studentId\" maxlength=\"20\" value=\"\" id=\"studentId\" class=\"span4\"></div>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("      </fieldset>\n");
      out.write("    </form>\n");
      out.write("  </div>\n");
      out.write("\n");
      out.write("  <div class=\"modal-footer\">\n");
      out.write("    <a href=\"#\" class=\"btn btn-primary\">Register</a>\n");
      out.write("    <a href=\"#\" class=\"btn\" data-dismiss=\"modal\" aria-hidden=\"true\">Close</a>\n");
      out.write("  </div>\n");
      out.write("</div>\n");
      out.write("\n");
      out.write("<!-- User activate Modal -->\n");
      out.write("<div id=\"activateModal\" class=\"modal hide fade\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"activateModal\" aria-hidden=\"true\">\n");
      out.write("  <div class=\"modal-header\">\n");
      out.write("    <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">×</button>\n");
      out.write("    <h3 id=\"activateModalLabel\">Forget password</h3>\n");
      out.write("  </div>\n");
      out.write("  <div class=\"modal-body\">\n");
      out.write("    <form class=\"form-horizontal\">\n");
      out.write("      <fieldset>\n");
      out.write("        <div class=\"control-group \"><label class=\"control-label\" for=\"userName\">User Name</label><div class=\"controls\">\n");
      out.write("          <input type=\"text\" name=\"userName\" maxlength=\"24\" value=\"\" id=\"userName\" class=\"span4\"></div>\n");
      out.write("        </div>\n");
      out.write("      </fieldset>\n");
      out.write("    </form>\n");
      out.write("  </div>\n");
      out.write("  <div class=\"modal-footer\">\n");
      out.write("    <a href=\"#\" class=\"btn btn-primary\">Send Email</a>\n");
      out.write("    <a href=\"#\" class=\"btn\" data-dismiss=\"modal\" aria-hidden=\"true\">Close</a>\n");
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

  private boolean _jspx_meth_c_forEach_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_forEach_0 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _jspx_tagPool_c_forEach_var_items.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_forEach_0.setPageContext(_jspx_page_context);
    _jspx_th_c_forEach_0.setParent(null);
    _jspx_th_c_forEach_0.setVar("department");
    _jspx_th_c_forEach_0.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${departmentList}", java.lang.Object.class, (PageContext)_jspx_page_context, null));
    int[] _jspx_push_body_count_c_forEach_0 = new int[] { 0 };
    try {
      int _jspx_eval_c_forEach_0 = _jspx_th_c_forEach_0.doStartTag();
      if (_jspx_eval_c_forEach_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\n");
          out.write("              <option value=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${department.departmentId}", java.lang.String.class, (PageContext)_jspx_page_context, null));
          out.write('"');
          out.write('>');
          if (_jspx_meth_c_out_0((javax.servlet.jsp.tagext.JspTag) _jspx_th_c_forEach_0, _jspx_page_context, _jspx_push_body_count_c_forEach_0))
            return true;
          out.write("</option>\n");
          out.write("            ");
          int evalDoAfterBody = _jspx_th_c_forEach_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_forEach_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_forEach_0[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_forEach_0.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_forEach_0.doFinally();
      _jspx_tagPool_c_forEach_var_items.reuse(_jspx_th_c_forEach_0);
    }
    return false;
  }

  private boolean _jspx_meth_c_out_0(javax.servlet.jsp.tagext.JspTag _jspx_th_c_forEach_0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_forEach_0)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:out
    org.apache.taglibs.standard.tag.rt.core.OutTag _jspx_th_c_out_0 = (org.apache.taglibs.standard.tag.rt.core.OutTag) _jspx_tagPool_c_out_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.OutTag.class);
    _jspx_th_c_out_0.setPageContext(_jspx_page_context);
    _jspx_th_c_out_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_forEach_0);
    _jspx_th_c_out_0.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${department.name}", java.lang.Object.class, (PageContext)_jspx_page_context, null));
    int _jspx_eval_c_out_0 = _jspx_th_c_out_0.doStartTag();
    if (_jspx_th_c_out_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_out_value_nobody.reuse(_jspx_th_c_out_0);
      return true;
    }
    _jspx_tagPool_c_out_value_nobody.reuse(_jspx_th_c_out_0);
    return false;
  }
}
