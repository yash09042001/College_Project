<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="main.Cryptography"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="main.dbcon"%>
<style>

    td{
        border: 1px solid black;   
        padding: 5px;
    }
    table
    {

        padding: 5px;
    }
</style> 
<div class="container">
    <div class="row background space20">
        <center><h2>backup data files</h2></center>
        <div class="span6">
            <%
                String unm = (String) session.getAttribute("username");
                String pwd = (String) session.getAttribute("password");
                int sid = Cryptography.getsid_users(unm, pwd);
                System.out.println("sid" + sid);
                int oid = Cryptography.getownerid(sid);


                String data = request.getParameter("data");
                System.out.println("data:" + data);



                System.out.println("oid===" + oid);
                Statement stmt1 = dbcon.connectDB();
                String query1 = "select * from backupdata where id not in (select UFID from filedetailbackup) and user_id='" + oid + "'  ";
                System.out.println("Qurry1" + query1);
                ResultSet rs1 = stmt1.executeQuery(query1);

            %>

            <div class="container">
                <table align="center" class="" style="border: 5px solid goldenrod">
                    <thead>
                        <tr>
                            <th>File Name</th>
                            <th>User ID</th>
                            <th>id</th> 
                            <th>Download</th> 
                        </tr>
                    </thead>
                    <%

                        while (rs1.next()) {
                            String id = rs1.getString(1);
                            String filename = rs1.getString(5);
                            String filetype = filename.substring(filename.indexOf(".") + 1);
                    %>

                    <body>
                        <tr>

                            <td><%=filename%></td>
                            <td><%=rs1.getString(3)%></td>
                            <td><%=rs1.getString(4)%></td>
                            <td>
                                <form action="Matchkey.jsp?id=<%=id%>"method="post">
                                    <input type="hidden" name="file_id" value="<%=id%>"/>
                                    <input type="hidden" name="data" value="<%=filetype%>"/>
                                    <input type="submit" value="download">
                                </form>
                            </td>


                        </tr>


                        <%
                            }
                        %>
                    </body>

                </table>
            </div>


        </div>
    </div>

    <%@include file="footer.jsp" %>