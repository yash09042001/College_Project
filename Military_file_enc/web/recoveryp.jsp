
<%@page import="main.dbcon"%>

<%@page language="java" import="java.util.*,java.sql.*,sessPass.*" session="true" %>

<% %>
<%@include file='header.jsp'%>
<div class="mainContent"><center>
    <%
    boolean valid = false;
    String uname=null;
    if(request.getParameter("uname")!=null){

               uname=request.getParameter("uname");
        Connection con =dbcon.connectDB_con();
        Statement st = con.createStatement();
        String query="select * from user where username='"+uname+"';";
        ResultSet  rs = st.executeQuery(query);
        if(rs.next()){
        valid = true ;
        String secret_question = rs.getString("secret_question");
    %>
    <form action="ResetPassword" method="post" onsubmit="return validateSecretAnswer(this.secret_answer);" >
        <input type="hidden" name="username" value="<%= uname %>" /><br>
        <input type="text" name="password" placeholder="New Password"  />
        Secret Question: <%=secret_question %><br/>
        Secret Answer &nbsp;: <input type="text" name="secret_answer"/>
        <br><input type="submit" value="Submit">
    </form>
    <% }}
       if(!valid){
    if(uname!=null) out.println("Invalid username.");%>
    <form method="post" onsubmit="return validateRecoveryUsername(this.uname);" >
        Username: <input type="text" name="uname" />
        <br><input type="submit" value="Next">
    </form>
    <% } %>
    </center>
</div>
<%@include file='footer.jsp'%>