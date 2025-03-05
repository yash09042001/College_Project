<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="main.dbcon"%>
<%@include file="header.jsp" %>
<%@include file="manage_nav.jsp" %>
<%@include file="flash.jsp" %>


<div class="container">
    <div class="row background space20">
         <h2>Present files</h2>
        <div class="span6">
            <%
                String s = request.getParameter("vd");
                Statement stmt = dbcon.connectDB();
                String query = "select * from filedetailbackup WHERE owner_id='" + s + "'";
                System.out.println("Qurry" + query);
                ResultSet rs = stmt.executeQuery(query);

            %>


            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>UFID</th>
                        <th>Your_id</th>
                        <th>File Name</th>
                        <th>Secrete key</th>            
                    </tr>
                </thead>
                <% while (rs.next()) {

                %>

                <tbody>
                    <tr>
                        <td><%=rs.getString(1)%></td>
                        <td><%=rs.getString(2)%></td>
                        <td><%=rs.getString(4)%></td>
                        <td><%=rs.getString(5)%></td>


                        <!--            <td><a href="viewdetail.jsp"> View Detail</a></td>-->

                    </tr>


                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </div>
</div>
                

<div class="container">
    <div class="row background space20">
        <h2>backup data files</h2>
        <div class="span6">
            <%

                Statement stmt1 = dbcon.connectDB();
                String query1 = "select * from backupdata where id not in (select UFID from filedetailbackup) and user_id='" + s + "'  ";
                System.out.println("Qurry1" + query1);
                ResultSet rs1 = stmt1.executeQuery(query1);

            %>


            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Backup File</th>
                        <th>Your_id</th>
                        <th>id</th>            
                    </tr>
                </thead>
                <% 
                while (rs1.next()) {

                %>

                <tbody>
                    <tr>
                      
                        <td><%=rs1.getString(2)%></td>
                        <td><%=rs1.getString(3)%></td>
                        <td><%=rs1.getString(4)%></td>


                        <!--            <td><a href="viewdetail.jsp"> View Detail</a></td>-->

                    </tr>


                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>


    </div>
</div>

<%@include file="footer.jsp" %>