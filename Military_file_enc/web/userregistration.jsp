<%--<%@page import="java.sql.ResultSet"%>
<%@page import="com.db.DBConnection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>--%>
<%@include file="header.jsp" %>
<%@include file="navigation.jsp" %>
<%@include file="flash.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html> 
<body>
    <div class="container">
        <form class="form-signin" action="user_registration" method="post">
            <center><h3 class="form-signin-heading">User Registration </h3></center>
            <input type="text" class="input-block-level validate[required] " name="username" placeholder="Username">
            <input type="password" class="input-block-level validate[required,minSize[6]]" name="password" placeholder="Password">
            <input type="email" class="input-block-level validate[required, custom[email]]" name="email" placeholder="Email">
            <!--<input type="email" name="email" placeholder="Email">-->
            <!--<input type="text"  class="input-block-level validate[required,custom[phone]]" name="contact" placeholder="Contact">-->
            <center><button class="btn btn-large btn-primary" type="submit">Register</button></center>
            <!--input-block-level validate[required, custom[phone]]-->
        </form>
    </div>
</body>
    <%@include file="footer.jsp" %>
