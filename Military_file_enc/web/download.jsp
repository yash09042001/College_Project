
<%@page import="de.flexiprovider.api.keys.SecretKeySpec"%>
<%@page import="org.apache.catalina.util.Base64"%>
<%@page import="javax.crypto.SecretKey"%>
<%@page import="java.util.Map"%>
<%@page import="java.io.File"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="main.AESCrypt"%>
<%@page import="main.decryption"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>

    <body>
        <%
            SecretKey secretKey = null;
            byte[] stringKey = null;
            byte[] byte_val = null;
            String getid = request.getParameter("id");
            String f_type = request.getParameter("data");
            System.out.println("id" + getid);
            System.out.println("f_type:" + f_type);

            int id = Integer.parseInt(getid);
            System.out.println("parseint" + id);
            stringKey = AESCrypt.getsecretkey(id);
            System.out.println("getbytes" + stringKey.length);
            byte_val = decryption.getorgfile(id);

            SecretKey originalKey = new SecretKeySpec(byte_val, 0, byte_val.length, "AES");

//            Object obj = seckey;
//            secretKey = (SecretKey) obj;
            File orgfile = decryption.bytearraytofile(byte_val, f_type);
            decryption.decryptfile(orgfile.getName(), originalKey, f_type);
            System.out.println("file downloaded");

        %>
    </body>
</html>
