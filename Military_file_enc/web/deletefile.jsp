<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="main.decryption"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <%

            String[] file_extension = {"docx", "txt", "pdf"};
            String[] image_extension = {"jpg", "jpeg", "bmp", "gif", "png"};

            String getid = request.getParameter("id");
            String getdata = request.getParameter("data");
            System.out.println("getid:" + getid);
            System.out.println("getdata:" + getdata);
//            getid = getid.substring(0, 1);
            System.out.println(" updated  id  :" + getid);
            int id = Integer.parseInt(getid);
            System.out.println("click id:" + id);
            boolean status = decryption.delete_file(id);
            System.out.println("status :" + status);
            
            if (status) {
                session.setAttribute("flash_message", "File with id " + id + "deleted successfully");
                session.setAttribute("flash_type", "success");
                
                for (int i = 0; i < file_extension.length; i++) {
                    String ex = file_extension[i];
                    if (ex.equals(getdata)) {
                        System.out.println("");
                        response.sendRedirect("viewfile.jsp?data=file");
                        return;
                    }
                }
                for (int i = 0; i < image_extension.length; i++) {
                    String ex = image_extension[i];
                    if (ex.equals(getdata)) {
                        System.out.println("");
                        response.sendRedirect("viewfile.jsp?data=img");
                        return;
                    }
                }
                response.sendRedirect("viewfile.jsp?data=img");
            } else {
                session.setAttribute("flash_message", "File Can not be deleted");
                session.setAttribute("flash_type", "failure");
                response.sendRedirect("viewfile.jsp?data=img");
            }
        %>
    </body>
</html>
