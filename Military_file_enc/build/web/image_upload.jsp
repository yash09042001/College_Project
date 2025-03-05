<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            th,td{
                border: 3px solid black; 
            }
        </style>
    </head> 
    <body> 
        <div class="container">
             <div class="row background space20">
        <div class="span12">
            <%@include file="flash.jsp" %>
            <table align="center" style="border: 5px solid goldenrod">
            <tr>
             <th>   <h3> Upload:</h3></th></tr>
          <tr>
             <th>  Select data to upload: </th></tr>
            <form action="FUpload" method="post"
                  name="fname"   enctype="multipart/form-data">
                <!--<input type="text" class="validate[required]">-->
              <tr><td>  <input type="file"   placeholder="Browse" required="required" class="validate[required]" name="file"  size="50" />
                </td></tr>
              <tr> <td>   <input type="submit"  value="Upload" /> </td></tr>
                <!--onclick="Validate(fname);"-->
            </form>
            </table>
        </div>
             </div>
             </div>
    </body>
</html>
