<%@include file='header_1.jsp'%>
<%@include file="navigation.jsp" %>
<div class="mainContent">
    
    <center>
        <form width="70%" action="Register" onsubmit="return validateRegistration();" method="post">
            <table><tr>
                    <td>User Name:
                    <td><input name="name" type="text" value="">
                </tr>
                <tr>
                    <td>Password:
                    <td><input name="password" type="password" value="">
                </tr>
                <tr>
                    <td>Confirm Password:
                    <td><input name="cpassword" type="password" value="">
                </tr>
                <tr>
                    <td>Date of Birth:
                    <td><input name="date_of_birth" type="text" value="" class="datepicker" size="8">
                </tr>
                <tr>
                    <td>Gender:
                    <td><input name="gender" type="radio" value="m">Male<input name="gender" type="radio" value="f">Female
                </tr>
                <tr>
                    <td>Secret Question:
                    <td><select name="secret_question" >
                            <option value="What is your favourite food ?">What is your favourite food ?</option>
                            <option value="What is your favourite sport ?">What is your favourite sport ?</option>
                            <option value="What is your first school ?">What is your first school ?</option>
                            <option value="What is your fathers middle name ?">What is your fathers middle name ?</option>
                        </select>
                </tr>
                <tr>
                    <td>Secret Answer:
                    <td><input name="secret_answer" type="text">
                </tr><td><td><input type="submit" value="Register"><input type="reset" value="Clear">
            </table>
        </form></center>
</div>
<br class="clearfloat" />
<%@include file='footer.jsp'%>