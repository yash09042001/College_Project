<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="main.Cryptography"%>
<!DOCTYPE html>
<html>
    <head>
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
       
    </head>
    <body>

        <%
            HashMap<Integer, String> filedetails = new HashMap<Integer, String>();
            ArrayList<Integer> uploadedfid = new ArrayList<Integer>();
            
            String[] file_extension = {"docx", "txt", "doc","pdf"};
            String[] image_extension = {"jpg", "jpeg", "bmp", "gif", "png"};

            session.setAttribute("file_extension", file_extension);
//            session.setAttribute("image_extension", image_extension);

            String unm = (String) session.getAttribute("username");
            String pwd = (String) session.getAttribute("password");
            int sid = Cryptography.getsid_users(unm, pwd);  
            System.out.println("sid"+sid);
            int oid = Cryptography.getownerid(sid);
            String data = request.getParameter("data");
            System.out.println("data:" + data);
            if (data.equals("img")) {
                uploadedfid = Cryptography.getuploadedfid(image_extension, oid);
            } else if (data.equals("file")) {
                uploadedfid = Cryptography.getuploadedfid(file_extension, oid);               
            }
            filedetails = Cryptography.getfiledetails(uploadedfid);
            System.out.println("size:" + uploadedfid.size());
        %> 

        <div class="container">
                        <table align="center" class="" style="border: 5px solid goldenrod">
                        <tr>
                            <td><b>File id</b></td> <td><b>File Name</b></td><td><b>File Type</b></td><td><b>Download</b></td><td><b>Remove File</b></td><td><b>Share File</b></td>
                        </tr><tr>
                        <%
                                for (Entry<Integer, String> entry : filedetails.entrySet()) {
                                Integer integer = entry.getKey();
                                String filename = entry.getValue(); 
                                System.out.println("id:" + integer + "filename" + filename);
                                String extension = filename.substring(filename.indexOf(".") + 1, filename.length());
                                System.out.println("filename:" + filename.subSequence(0, filename.indexOf(".") - 1));
                                                                                                                                                                            
                        %>                                      
                        
                        <td><%=integer%></td> 
                        <td><%=filename%></td> 
                        <td><%=extension%></td>              
                        <td><a href="Matchkey.jsp?id=<%=integer%>&data=<%=extension%>">Click</a></td>
                       
                        <td><a href="deletefile.jsp?id=<%=integer%>&data=<%=extension%>">Delete</a></td>
                        
                         <td><a href="sharefile.jsp?id=<%=integer%>&data=<%=extension%>">Share</a></td>
                        </tr>   
                        <%}%> </tr>                 
                </table>
        </div>

    </body>
</html>
