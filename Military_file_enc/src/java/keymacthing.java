/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import javax.mail.Session;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import main.Cryptography;
import main.SecretKeyDecoder;
import main.decryption;

/**
 *
 * @author eiosys
 */
public class keymacthing extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        OutputStream outStream = null;
        FileInputStream inStream = null;

        try {
            String keyvalue = request.getParameter("key");
            System.out.println("keyvalue"+keyvalue);

            Object attribute_fid = session.getAttribute("file_id");
            System.out.println("attribute_fid"+attribute_fid);
            String ss = (String) attribute_fid;

            Object attribute_filetype = session.getAttribute("filetype");
            
            String filetype = (String) attribute_filetype;
            System.out.println("filetype"+filetype);

            int fileid = Integer.parseInt(ss);
            System.out.println("field_id============"+fileid);
            String stringKey = Cryptography.getsecretkey(fileid);
            System.out.println("stringKey:" + stringKey);

            if (stringKey.equals(keyvalue)) {

                session.setAttribute("file_id", null);
                session.setAttribute("filetype", null);
                session.setAttribute("flash_message", "Downloading Started");
                session.setAttribute("flash_type", "success");
                response.sendRedirect("main.jsp");
            } else {
                session.setAttribute("flash_message", "Invalid key  entered");
                session.setAttribute("flash_type", "failure");
                response.sendRedirect("Matchkey.jsp");
            }

            SecretKey decodekey = SecretKeyDecoder.decodekey(stringKey);

            HashMap<String, byte[]> orgfile = decryption.getorgfile(fileid);
            String filname = null;
            byte[] orgfiledata = null;

            for (Map.Entry<String, byte[]> entry : orgfile.entrySet()) {
                filname = entry.getKey();
                orgfiledata = entry.getValue();
            }


            String source_absolutePath = request.getRealPath("/") + "temp";
            String destination_absolutePath = request.getRealPath("/") + "outputfiles";
            File org_file = decryption.bytearraytofile(orgfiledata, filetype, source_absolutePath);

            System.out.println("org_file:" + org_file.getName());

            String decryptfile;


            System.out.println("absolutePath:" + source_absolutePath + "\\" + org_file.getName());

            String filePath = decryption.decryptfile(source_absolutePath + "\\" + org_file.getName(), destination_absolutePath, decodekey, filname, filetype);


            File downloadFile = new File(filePath);
            inStream = new FileInputStream(downloadFile);
            System.out.println("downloadFile:" + downloadFile);
            System.out.println("downloadFile name=" + downloadFile.getName());

            // if you want to use a relative path to context root:
            String relativePath = getServletContext().getRealPath("");
            System.out.println("relativePath = " + relativePath);

            // obtains ServletContext
            ServletContext context = getServletContext();
            // gets MIME type of the file
            String mimeType = context.getMimeType(filePath);
            System.out.println("mimeType: " + mimeType);
            if (mimeType == null) {
                // set to binary type if MIME mapping not found
                mimeType = "application/octet-stream";
            }
            System.out.println("MIME type: " + mimeType);

            // modifies response
            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());

            // forces download
            String headerKey = "Content-Disposition";
            System.out.println("headerKey: " + headerKey);

            String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
            System.out.println("headerValue: " + headerValue);
            response.setHeader(headerKey, headerValue);

            
            // obtains response's output stream
            outStream = response.getOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);

            }
            outStream.flush();


            System.out.println("file downloaded");


        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("flash_message", "There is technical error.");
            session.setAttribute("flash_type", "failure");
            response.sendRedirect("Matchkey.jsp");
        } finally {
            out.close();
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
