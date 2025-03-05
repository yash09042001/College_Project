<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="main.dbcon"%>
<%@include file="header.jsp" %>
<%@include file="manage_nav.jsp" %>
<%@include file="flash.jsp" %>

<div class="container">
    <div class="row background space20">
        <div class="span6">
            <%--
         <%
            Statement stmt = dbcon.connectDB();
            String query = "select id,name from users";
            System.out.println();
            ResultSet rs = stmt.executeQuery(query);
           
       %>
       <form action="viewdetail.jsp" method="post">      
<table class="table table-bordered">
    <thead>
        <tr>
            <th>id</th>
            <th>Username</th>
            <th>View detail</a></th>
        </tr>
    </thead>
    <% while (rs.next()) {
                
                String unm = rs.getString(1);
                String pw = rs.getString(2);
         
         %>
    <tbody>
        <tr>
            <td><%=rs.getString(1)%></td>
            <td><%=rs.getString(2)%></td>
<!--            <td><a href="viewdetail.jsp"> View Detail</a></td>-->
            <td><input type="radio" value="<%=rs.getString(1)%>" name="vd"> </td>
        </tr>
       
    
         <%
                }
         %>
         </tbody>
</table>
         <input type="submit" value="submit">
         </form>
        </div>
    </div>
    </div>
            --%>
        <%@include file="footer.jsp" %>