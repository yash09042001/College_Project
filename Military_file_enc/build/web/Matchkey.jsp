<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <%
            String getid = request.getParameter("id");
            System.out.println("getid==========="+getid);    
            String f_type = request.getParameter("data");
            System.out.println("f_type=========="+f_type);
            session.setAttribute("file_id", getid);
            session.setAttribute("filetype", f_type);

        %>
        <div class="container">
            <div> 

                <div class="right">
                    <form class="form-signin" action="Otpprocess" method="post">

                        <center><h3 class="form-signin-heading">Please Enter OTP</h3></center>
                        <center>  <input type="text"  id="textfield1" class="input-block-level validate[required]" name="key" placeholder="Enter Key"></center>
                        <!--<input type="password" id="textfield2"  class="input-block-level validate[required] " name="password" placeholder="Password">-->
                        <center><button class="btn btn-large btn-primary inline" type="submit">submit</button> &nbsp;
                            <!--<button class="btn btn-large btn-primary inline" type=button  onclick="ClearFields();" >Reset</button> </center>-->
                            <!--<a href="userregistration.jsp"></a>-->

                    </form>
                </div>
            </div>
        </div> 
    </body>
</html>
