<%@include file="header.jsp" %>
<%@include file="navigation.jsp" %>
<%@include file="flash.jsp" %>
<div class="container">
        <div>
            <div class="right">
                <form class="form-signin" action="fileUpload" method="post">

                    <center><h3 class="form-signin-heading">User Login</h3></center>
                    <input type="file"  id="textfield1" class="input-block-level validate[required]" name="file" placeholder="Browse">
                    
                    <center><button class="btn btn-large btn-primary inline" type="submit">submit</button> 
                        
                   

                </form>
            </div>
        </div>
    </div>