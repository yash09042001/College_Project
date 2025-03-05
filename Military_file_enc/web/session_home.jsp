
<%@page import="java.util.Collections"%>
<%@page language="java" import="java.util.ArrayList" %>
<%@include file="header.jsp" %>
<%@include file="header_1.jsp" %>
<%@include file="navigation.jsp" %>
<%@include file="flash.jsp" %>
<style>
    body {
        background-position: left top;
        /*background-image: url(img/login/graduation.jpg);*/
        background-repeat: no-repeat;
    }
</style>

<body>
    <div class="container" style="margin-top: 50px">

        <form id="loginForm" action="Login" onsubmit="return validateLoginForm(this.name.value,this.password.value);" method="post">

            <center><%
                if ((String) request.getAttribute("login_failed") == "1") {
                    out.println("<br>Invalid username or password.");
                }
                String message = (String) session.getAttribute("resetPassword");
                if (message != null) {%>
                <tr><td style="font-weight:bold;font-size:20pt; color: green;" align="center"><%=message%></td></tr><%
                        session.setAttribute("resetPassword", null);
                    }
                %><br/>
                <!-- <div style="background-image: img/login/question_img.jpg">-->
                <div>
                    User Name:
                    <input name="name" type="text"  id="uname">

                    &nbsp;   <br>Text Password Matrix<br>

                    <center>    
                        <table border="1" cellspacing="0">
                            <%
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
                            %>
                        </table>
                    </center>
                    <br>
                    Text Password:&nbsp;&nbsp;
                    <input name="password" type="password">
                    <br><br>
<!--                    <a href="sms.jsp">Send sms</a>-->
                    <br>
                    <table border="1" cellspacing="0">
                        <br/>


                        <input name="login" type="Submit"    value="  Login  ">
                        <a href="register.jsp">Register</a>
                        <br/><a href="recoveryp.jsp">Forgot Password ?</a>
                    </table>
                </div>
            </center>
            <!--</div>-->    
        </form>

    </div>
    <br class="clearfloat" />

</body>

