<%@page import="java.util.HashMap"%>
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="brand" href="main.jsp">Home</a>
            <div class="nav-collapse collapse">
                <ul class="pull-right nav">
                    <%
                        String userType = (String) session.getAttribute("userType");
                        System.out.println("usertype==="+userType);
                        String username = (String) session.getAttribute("username");
                        if (userType == null) {
                    %>
<!--                    <li class="active"><a href="Homepage.jsp">Home</a></li>
                    <li class="active"><a href="login.jsp">User Login </a></li>
                    <li class="active"><a href="userregistration.jsp">User Registration</a></li>
                    <li class="active"><a href="cloud_admin.jsp">Cloud Admin</a></li>-->
                    <% } else {
                        //for admin
                            
                        if (userType.equals("Admin")) {%>
                        <li><a href="#">Welcome Admin</a></li>
                               <li><a href="#">Main Storage</a></li>
                                      <li><a href="#">Secondary Storage</a></li>
                        
                        <li class="vertical"><a href="index.jsp">Logout</a></li>
                   
                    <%} else if (userType.equals("user")) {%>
                    <li><a href="#">Welcome <%=username%></a></li>
<!--                    <li class="vertical"><a onclick="loadFile()">File Upload</a></li>-->
                    <li class="vertical"><a onclick="loadimage()">Upload</a></li>
                   <li class="vertical"><a onclick="loaddata()" >View File</a></li>
                   
                    <li class="vertical"><a onclick="loaddata1()">View Images</a></li>
                    
                    <li class="vertical"><a onclick="recovery()">Recover Files</a></li>
                    <li><a href="index.jsp">Logout</a></li>
                    <% }%>
<!--                    <li><a href="index.jsp">Logout</a></li>-->
                    <% }%>
                </ul>

            </div>
        </div> 
    </div>