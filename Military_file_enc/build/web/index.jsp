<%-- 
    Document   : index
    Created on : 9 Oct, 2014, 3:35:03 PM
    Author     : eiosys
--%>
<%@include file="header.jsp" %>
<%@include file="navigation.jsp" %>
<%@include file="flash.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head> 
    <body>
        <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <!-- Responsive Navbar Part 1: Button for triggering responsive navbar (not covered in tutorial). Include responsive CSS to utilize. -->
                    <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <!--<a class="brand" href="index.jsp">Home</a>-->
                    <!-- Responsive Navbar Part 2: Place all navbar contents you want collapsed withing .navbar-collapse.collapse. -->
                    <div class="nav-collapse collapse">

                        <ul class="nav">
                            <li class="active"><a href="Homepage.jsp">Home</a></li>
                            <li class="active"><a href="login.jsp">User Login </a></li>
                            <li class="active"><a href="userregistration.jsp">User Registration</a></li>
                            <li class="active"><a href="cloud_admin.jsp">Admin Login</a></li>


                        </ul>
                        <%--<%@include file="manage_nav.jsp" %>--%>
                    </div><!--/.nav-collapse -->
                </div><!-- /.navbar-inner -->
            </div><!-- /.navbar -->


        </div>
        <!-- /.container -->
        <br>
        <br>
        <br> 
        <br>
<div id="myCarousel" class="carousel slide" data-interval="3000">
<!--    <ol class="carousel-indicators">
        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
        <li data-target="#myCarousel" data-slide-to="1"></li>
        <li data-target="#myCarousel" data-slide-to="2"></li>
    </ol>-->
    <div class="carousel-inner">
        <div class="item active">
            <img src="img/server.jpg" alt="">
            <div class="carousel-caption">
                
            </div>
        </div>
        <div class="item">
            <img src="img/server.jpg" alt="">
            <div class="carousel-caption">
               
            </div>
        </div>
        <div class="item">
            <img src="img/server.jpg" alt="">
            <div class="carousel-caption">
               
            
            </div>
        </div>
    </div>
    <a class="left carousel-control" href="#myCarousel" data-slide="prev">&lsaquo;</a>
    <a class="right carousel-control" href="#myCarousel" data-slide="next">&rsaquo;</a>
</div>
    </body>

    <%@include file="footer.jsp" %>
</html>
