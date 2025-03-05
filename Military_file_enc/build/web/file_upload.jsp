<%-- 
    Document   : file_upload
    Created on : 11 Oct, 2014, 11:16:02 AM
    Author     : eiosys
--%>
<%--<%@include file="header.jsp" %>
<%@include file="navigation.jsp" %>
<%@include file="flash.jsp" %>--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<body>
    <div class="container">
        <%@include file="flash.jsp" %>
        <h3>File Upload:</h3>
        Select a file to upload: <br />
        <form action="FUpload" method="post"
              name="fname" enctype="multipart/form-data">
            <!--<input type="text" class="validate[required]">-->
            <input type="file"  required="required" class="validate[required]"name="file" size="50" />
            <br />
            <input type="submit"  value="Upload File" /> 
            <!--onclick="Validate(fname);"-->
        </form>
    </div>
</body> 
<%--<%@include file="footer.jsp" %>--%>
</html>
