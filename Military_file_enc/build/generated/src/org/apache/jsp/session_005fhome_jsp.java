package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Collections;
import java.util.ArrayList;

public final class session_005fhome_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(4);
    _jspx_dependants.add("/header.jsp");
    _jspx_dependants.add("/header_1.jsp");
    _jspx_dependants.add("/navigation.jsp");
    _jspx_dependants.add("/flash.jsp");
  }

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
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
      response.setContentType("text/html");
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
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>File Storage System</title>\n");
      out.write("        <link href=\"css/bootstrap.css\" rel=\"stylesheet\">\n");
      out.write("        <link href=\"css/font-awesome.css\" rel=\"stylesheet\">\n");
      out.write("        <link href=\"css/validationEngine.css\" rel=\"stylesheet\">\n");
      out.write("        <link href=\"css/stye.css\" rel=\"stylesheet\">\n");
      out.write("        <script src=\"js/jquery-1.7.1.min.js\"></script>\n");
      out.write("        <script src=\"js/bootstrap.min.js\"></script>\n");
      out.write("        <script src=\"js/jquery.validationEngine-en.js\"></script>\n");
      out.write("        <script src=\"js/jquery.validationEngine.js\"></script>\n");
      out.write("        <script src=\"js/highcharts.js\"></script>\n");
      out.write("        <script src=\"js/data.js\"></script>\n");
      out.write("        <script src=\"js/script.js\"></script>\n");
      out.write("\n");
      out.write("        <script>\n");
      out.write("            function ClearFields() {\n");
      out.write("\n");
      out.write("                document.getElementById(\"textfield1\").value = \"\";\n");
      out.write("                document.getElementById(\"textfield2\").value = \"\";\n");
      out.write("            }\n");
      out.write("            function phonenumber(inputtxt)\n");
      out.write("            {\n");
      out.write("                var phoneno = /^\\d{10}$/;\n");
      out.write("                if (inputtxt.value.match(phoneno))\n");
      out.write("                {\n");
      out.write("                    return true;\n");
      out.write("                }\n");
      out.write("                else\n");
      out.write("                {\n");
      out.write("                    alert(\"Not a valid Phone Number\");\n");
      out.write("                    return false;\n");
      out.write("                }\n");
      out.write("            }\n");
      out.write("            function loadFile()\n");
      out.write("            {\n");
      out.write("                document.getElementById(\"dynamic\").innerHTML = \"<iframe src='file_upload.jsp' style='width: 100%; height:500px;'></iframe>\";\n");
      out.write("            }\n");
      out.write("            function loadimage()\n");
      out.write("            {\n");
      out.write("                document.getElementById(\"dynamic\").innerHTML = \"<iframe src='image_upload.jsp'  style='width: 100%; height:500px'></iframe>\";\n");
      out.write("            }\n");
      out.write("            function loaddata1()\n");
      out.write("            {\n");
      out.write("                document.getElementById(\"dynamic\").innerHTML = \"<iframe src='viewfile.jsp?data=img' style='width: 100%;height:500px'></iframe>\";\n");
      out.write("            }\n");
      out.write("            function loaddata()\n");
      out.write("            {\n");
      out.write("                document.getElementById(\"dynamic\").innerHTML = \"<iframe src='viewfile.jsp?data=file' style='width: 100%;height:500px'></iframe>\";\n");
      out.write("            }\n");
      out.write("            function loaddata_users()\n");
      out.write("            {\n");
      out.write("                document.getElementById(\"dynamic\").innerHTML = \"<iframe src='viewfile.jsp?data=file' style='width: 100%;height:500px'></iframe>\";\n");
      out.write("            }\n");
      out.write("            function recovery()\n");
      out.write("            {\n");
      out.write("                document.getElementById(\"dynamic\").innerHTML = \"<iframe src='recover.jsp?data=file' style='width: 100%;height:500px'></iframe>\";\n");
      out.write("            }\n");
      out.write("            var _validFileExtensions = [\".jpg\", \".jpeg\", \".bmp\", \".gif\", \".png\", \".pdf\", \".docx\", \".txt\"];\n");
      out.write("            function Validate(oForm) {\n");
      out.write("\n");
      out.write("                var arrInputs = oForm.getElementsByTagName(\"input\");\n");
      out.write("                for (var i = 0; i < arrInputs.length; i++) {\n");
      out.write("                    var oInput = arrInputs[i];\n");
      out.write("                    if (oInput.type == \"file\") {\n");
      out.write("                        var sFileName = oInput.value;\n");
      out.write("                        if (sFileName.length > 0) {\n");
      out.write("                            var blnValid = false;\n");
      out.write("                            alert(\"invalid file\" + blnValid);\n");
      out.write("                            for (var j = 0; j < _validFileExtensions.length; j++) {\n");
      out.write("                                var sCurExtension = _validFileExtensions[j];\n");
      out.write("                                if (sFileName.substr(sFileName.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {\n");
      out.write("                                    blnValid = true;\n");
      out.write("                                    alert(\"valid file\" + blnValid);\n");
      out.write("                                    break;\n");
      out.write("                                }\n");
      out.write("                            }\n");
      out.write("\n");
      out.write("                            if (!blnValid) {\n");
      out.write("                                alert(\"Sorry, \" + sFileName + \" is invalid, allowed extensions are: \" + _validFileExtensions.join(\", \"));\n");
      out.write("                                return false;\n");
      out.write("                            }\n");
      out.write("                        }\n");
      out.write("                    }\n");
      out.write("                }\n");
      out.write("\n");
      out.write("                return true;\n");
      out.write("            }\n");
      out.write("        </script>\n");
      out.write("    </head>\n");
      out.write("    <body>");
      out.write('\r');
      out.write('\n');
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Project Name</title>\n");
      out.write("           <link href=\"styles.css\" rel=\"stylesheet\" type=\"text/css\" />\n");
      out.write("        <link href=\"mobiscroll.css\" rel=\"stylesheet\" type=\"text/css\" />\n");
      out.write("\n");
      out.write("        <script src=\"jquery.js\" type=\"text/javascript\" ></script>\n");
      out.write("        <script src=\"mobiscroll.js\" type=\"text/javascript\" ></script>\n");
      out.write("        <script src=\"script.js\" type=\"text/javascript\" ></script>\n");
      out.write("        <link href=\"css/bootstrap.css\" rel=\"stylesheet\">\n");
      out.write("        <link href=\"css/bootstrap.min.css\" rel=\"stylesheet\">\n");
      out.write("        <link href=\"css/bootstrap-responsive.css\" rel=\"stylesheet\">\n");
      out.write("       \n");
      out.write("        <script src=\"js/jquery-1.9.1.js\"></script>\n");
      out.write("        <script src=\"js/jquery.js\"></script>\n");
      out.write("        <script src=\"js/bootstrap.min.js\"></script>\n");
      out.write("        <script src=\"js/jquery.validationEngine-en.js\"></script>\n");
      out.write("        <script src=\"js/jquery.validationEngine.js\"></script>\n");
      out.write("        <!--<script src=\"js/highcharts.js\"></script>-->\n");
      out.write("<!--        <script src=\"js/data.js\"></script>\n");
      out.write("        <script src=\"js/exporting.js\"></script>-->\n");
      out.write("        <!--<script src=\"js/script.js\"></script>-->\n");
      out.write("    </head>\n");
      out.write("    <body>");
      out.write('\r');
      out.write('\n');
      out.write("<div class=\"navbar navbar-inverse navbar-fixed-top\">\n");
      out.write("    <div class=\"navbar-inner\">\n");
      out.write("        <div class=\"container\">\n");
      out.write("            <!-- Responsive Navbar Part 1: Button for triggering responsive navbar (not covered in tutorial). Include responsive CSS to utilize. -->\n");
      out.write("            <button type=\"button\" class=\"btn btn-navbar\" data-toggle=\"collapse\" data-target=\".nav-collapse\">\n");
      out.write("                <span class=\"icon-bar\"></span>\n");
      out.write("                <span class=\"icon-bar\"></span>\n");
      out.write("                <span class=\"icon-bar\"></span>\n");
      out.write("            </button>\n");
      out.write("            <!--<a class=\"brand\" href=\"index.jsp\">Home</a>-->\n");
      out.write("            <!-- Responsive Navbar Part 2: Place all navbar contents you want collapsed withing .navbar-collapse.collapse. -->\n");
      out.write("            <div class=\"nav-collapse collapse\">\n");
      out.write("\n");
      out.write("                <ul class=\"nav\">\n");
      out.write("                    <li class=\"active\"><a href=\"Homepage.jsp\">Home</a></li>\n");
      out.write("                    <li class=\"active\"><a href=\"session_home.jsp\">User Login </a></li>\n");
      out.write("                    <li class=\"active\"><a href=\"userregistration.jsp\">User Registration</a></li>\n");
      out.write("                    <li class=\"active\"><a href=\"cloud_admin.jsp\"> Admin Login</a></li>\n");
      out.write("                </ul>     \n");
      out.write("                \n");
      out.write("            </div><!-- /.navbar-inner -->\n");
      out.write("           \n");
      out.write("        </div><!-- /.navbar -->\n");
      out.write("\n");
      out.write("    </div> <!-- /.container -->\n");
      out.write("</div>");
      out.write('\r');
      out.write('\n');

    //checking if flash message is set
    String flash_message = (String) session.getAttribute("flash_message");
    if (flash_message != null) {
        String flash_type = (String) session.getAttribute("flash_type");
        if (flash_type == null) {
            flash_type = "success";
        }
        session.removeAttribute("flash_message");
        session.removeAttribute("flash_type");

      out.write("\n");
      out.write("<div class=\"container\">\n");
      out.write("    <div class=\"alert alert-");
      out.print(flash_type);
      out.write("\">\n");
      out.write("        <button class=\"close\" data-dismiss=\"alert\"></button>\n");
      out.write("        <strong>");
      out.print(flash_type);
      out.write("!</strong> ");
      out.print(flash_message);
      out.write("\n");
      out.write("    </div>\n");
      out.write("</div>\n");

    }

      out.write("\r\n");
      out.write("<style>\r\n");
      out.write("    body {\r\n");
      out.write("        background-position: left top;\r\n");
      out.write("        /*background-image: url(img/login/graduation.jpg);*/\r\n");
      out.write("        background-repeat: no-repeat;\r\n");
      out.write("    }\r\n");
      out.write("</style>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("    <div class=\"container\" style=\"margin-top: 50px\">\r\n");
      out.write("\r\n");
      out.write("        <form id=\"loginForm\" action=\"Login\" onsubmit=\"return validateLoginForm(this.name.value,this.password.value);\" method=\"post\">\r\n");
      out.write("\r\n");
      out.write("            <center>");

                if ((String) request.getAttribute("login_failed") == "1") {
                    out.println("<br>Invalid username or password.");
                }
                String message = (String) session.getAttribute("resetPassword");
                if (message != null) {
      out.write("\r\n");
      out.write("                <tr><td style=\"font-weight:bold;font-size:20pt; color: green;\" align=\"center\">");
      out.print(message);
      out.write("</td></tr>");

                        session.setAttribute("resetPassword", null);
                    }
                
      out.write("<br/>\r\n");
      out.write("                <!-- <div style=\"background-image: img/login/question_img.jpg\">-->\r\n");
      out.write("                <div>\r\n");
      out.write("                    User Name:\r\n");
      out.write("                    <input name=\"name\" type=\"text\"  id=\"uname\">\r\n");
      out.write("\r\n");
      out.write("                    &nbsp;   <br>Text Password Matrix<br>\r\n");
      out.write("\r\n");
      out.write("                    <center>    \r\n");
      out.write("                        <table border=\"1\" cellspacing=\"0\">\r\n");
      out.write("                            ");

                                int rows = 6, columns = 6;
                                String textString = "abcdefghijklmnopqrstuvwxyz0123456789";
                                char[][] textMatrix = new char[rows][columns];
                                ArrayList<Integer> numbers = new ArrayList<Integer>();
                                for (int i = 0; i < rows * columns; i++) {
                                    numbers.add((int) textString.charAt(i));
                                }
                                Collections.shuffle(numbers);
                                System.out.println("numbers: " + numbers);
                                for (int i = 0; i < rows; i++) {
                                    out.print("<tr>");
                                    for (int j = 0; j < columns; j++) {
                                        textMatrix[i][j] = (char) numbers.get(rows * i + j).intValue();
                                        out.print("<td>" + textMatrix[i][j] + "</td>");
                                    }
                                }
                                System.out.println("textMatrix " + textMatrix);                                
                                session.setAttribute("textMatrix", textMatrix);
                            
      out.write("\r\n");
      out.write("                        </table>\r\n");
      out.write("                    </center>\r\n");
      out.write("                    <br>\r\n");
      out.write("                    Text Password:&nbsp;&nbsp;\r\n");
      out.write("                    <input name=\"password\" type=\"password\">\r\n");
      out.write("                    <br><br>\r\n");
      out.write("<!--                    <a href=\"sms.jsp\">Send sms</a>-->\r\n");
      out.write("                    <br>\r\n");
      out.write("                    <table border=\"1\" cellspacing=\"0\">\r\n");
      out.write("                        <br/>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("                        <input name=\"login\" type=\"Submit\"    value=\"  Login  \">\r\n");
      out.write("                        <a href=\"register.jsp\">Register</a>\r\n");
      out.write("                        <br/><a href=\"Recovery\">Forgot Password ?</a>\r\n");
      out.write("                    </table>\r\n");
      out.write("                </div>\r\n");
      out.write("            </center>\r\n");
      out.write("            <!--</div>-->    \r\n");
      out.write("        </form>\r\n");
      out.write("\r\n");
      out.write("    </div>\r\n");
      out.write("    <br class=\"clearfloat\" />\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("\r\n");
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
}
