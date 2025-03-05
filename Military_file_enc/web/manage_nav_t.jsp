<%@page import="java.lang.String"%>
<%@page import="java.util.HashMap"%>
<ul class="pull-right nav">
    <%
        String userType = (String) session.getAttribute("UserType");
        System.out.println("userType: " + userType);
        if (userType == null) {
    %>
    <li class="active"><a href="Homepage.jsp">Home</a></li>
    <li class="active"><a href="login.jsp">User Login </a></li>
    <li class="active"><a href="userregistration.jsp">User Registration</a></li>
    <li class="active"><a href="cloud_admin.jsp">Admin Login</a></li>
    <li class="active"><a href="cloud_admin.jsp">Admin Login</a></li>
        <% } else {
            if (userType.equals("Admin")) {
        %>
    
    <li><a href="index.jsp">Logout</a></li>
        <%} else {
        %>
    <li class="vertical"><a onclick="loadFile()">File Upload</a></li>
    <li class="vertical"><a onclick="loadimage()">Image Upload</a></li>
    <li class="vertical"><a onclick="loaddata()" >View File</a></li>
    <li class="vertical"><a onclick="loaddata1()">View Images</a></li>
    <li class="vertical"><a>Recover Files</a></li>
    <li><a href="index.jsp">Logout</a></li>
        <% }
        }%>
</ul>