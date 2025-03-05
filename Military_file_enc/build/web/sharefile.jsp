
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="main.dbcon"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="main.decryption"%>
<!DOCTYPE html>
<html>
    <head>
        <Style>
            td,th
            {
                border: 2.5px solid goldenrod;
            }
            </Style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Select users for Sharing</h1>
        <%
       String file_id = request.getParameter("id");
         Connection con=dbcon.connectDB_con();
         Statement st=con.createStatement();
         String query="SELECT * FROM users";
         ResultSet rs= st.executeQuery(query);
         %>
  <form action="share" method="post">
      <input type="hidden" value="<%=file_id %>" name="file_id">
  <table style="border: 5px solid goldenrod">
        <tr>
                <th>User</th>
                <th>Select</th>
        </tr>
           <%
           while(rs.next())
           {
               int id= rs.getInt("id");
           %>
           <tr>
           <td><%=rs.getString("name")%></td>
           
           <td><input type="checkbox" name="share" id="<%=id%>" value="<%=id%>"></td>
           
           </tr>
         <%}%>
         <tr><td colspan="2" align="center"><input type="submit" value="Share" name="Share"></td></tr>         
         </table>  
           
  </form>
    </body>
</html>
