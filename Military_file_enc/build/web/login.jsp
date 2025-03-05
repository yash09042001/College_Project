<%@include file="header.jsp" %>
<%@include file="navigation.jsp" %>
<%@include file="flash.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>


<body>
    <div class="container">
        <div> 

            <div class="right">
                <form class="form-signin" action="U_login" method="post">

                    <center><h3 class="form-signin-heading">User Login</h3></center>
                    <input type="text"  id="textfield1" class="input-block-level validate[required]" name="username" placeholder="Username">
                    <input type="password" id="textfield2"  class="input-block-level validate[required,,minSize[6]] " name="password" placeholder="Password">
                    <center><button class="btn btn-large btn-primary inline" type="submit">submit</button> &nbsp;
                        <button class="btn btn-large btn-primary inline" type=button  onclick="ClearFields();" >Reset</button> </center>
                    

                </form>
            </div>
        </div>
    </div></body>
    <%@include file="footer.jsp" %>
</html>
