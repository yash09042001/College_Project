
import com.oreilly.servlet.MultipartRequest;
import de.flexiprovider.core.FlexiCoreProvider;
import de.flexiprovider.core.rsa.RSAKeyFactory;
import de.flexiprovider.core.rsa.RSAPrivateKeySpec;
import de.flexiprovider.core.rsa.RSAPublicKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import main.Cryptography;
import main.EncDec;
//import main.SendMail;


public class FUpload extends HttpServlet {

    String Systememail = "testproject201@gmail.com";
    PrivateKey secretKey;
    byte[] encryptedbyte = null;
//    int fileid = 0;
//    boolean isMultipart;
    
//    String[] filetype = {"docx", "txt", "pdf", "jpg", "jpeg", "bmp", "gif", "png"};
    String[] filetype = {"txt","docx", "doc", "jpg", "jpeg", "bmp", "gif", "png"};


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        try {
            String username = (String) session.getAttribute("username");
            System.out.println("1username==" + username);
            String password = (String) session.getAttribute("password");
            System.out.println("password "+password);
//            isMultipart = ServletFileUpload.isMultipartContent(request);
            System.out.println("hiiii ... hello");
//            String parameter = request.getParameter("file");
//            System.out.println("parameter" + parameter);
            ArrayList<String> fileextensions = new ArrayList<String>();
            for (int i = 0; i < filetype.length; i++) {
                fileextensions.add(filetype[i]);
                System.out.println("heyy");
            }
            MultipartRequest multipartRequest = new MultipartRequest(request, getServletContext().getRealPath("/"));
            File file = multipartRequest.getFile("file");
            String absolutePath = file.getAbsolutePath();
            System.out.println("absolutePath: " + absolutePath);
            String fname = file.getName();
            fname = fname.toLowerCase();
            int index = fname.lastIndexOf(".");
            String ftype = fname.substring(index + 1);
            System.out.println("type:" + ftype);
            System.out.println("fileextensions:" + fileextensions);
//            ftype = ftype.toLowerCase();
            String[] extensions = new String[fileextensions.size()];
            System.out.println("hello ..."+fileextensions.toArray(extensions));
            if (!checkExtensions(ftype,extensions)){//fileextensions.contains(ftype)) {
                System.out.println("wrong file type");
                session.setAttribute("flash_message", "This file type is not allowed");
                session.setAttribute("flash_type", "failure");
                response.sendRedirect("file_upload.jsp");
            } else {

                File f = null;
                byte[] getbytes = null; // encrypted bytes
                Security.addProvider(new FlexiCoreProvider());
//                Thread.sleep(1000);
                System.out.println("hello ..0");
                HashMap<String, BigInteger> generateKeys = EncDec.generateKeys();
                BigInteger n = generateKeys.get("RSA_N");
                BigInteger d = generateKeys.get("RSA_D");
                BigInteger e = generateKeys.get("RSA_E");
                System.out.println("hello ..1");
                PrivateKey privKey =
                        new RSAKeyFactory().generatePrivate(new RSAPrivateKeySpec(n, d));
                System.out.println("n " + n);
                System.out.println("e " + e);
                System.out.println("2absolutePath " + absolutePath);
                System.out.println("hello ..2");
                PublicKey pubKey =
                        new RSAKeyFactory().generatePublic(new RSAPublicKeySpec(n, e));
                System.out.println("pubKey "+pubKey);
                HashMap<PrivateKey, byte[]> encryptfile =
                        Cryptography.encryptRSA(absolutePath, pubKey, privKey); //encrypt file using AES
                System.out.println("hello ..3");
                for (Entry<PrivateKey, byte[]> entry : encryptfile.entrySet()) {
                    secretKey = entry.getKey();  //mail secret key to user with whome file is shared ....
                    getbytes = entry.getValue();
                }

                f = file;//new File(getbytes);
                System.out.println("orgbytes len " + f.length());
                FileInputStream fis = new FileInputStream(f);
                int available = fis.available();
                byte[] orgbytes = new byte[available];//(int)f.length()];
                int read = fis.read(orgbytes);
                System.out.println("bytes read " + read + " available " + available);
                fis.close();

                encryptedbyte = getbytes;//Cryptography.readbytes(f);

                if (encryptedbyte == null) {
                    System.out.println("encryptedbyte null:");
                }
                int sid_users = Cryptography.getsid_users(username, password); //2nd  seed id calcaulted inside this fun
                System.out.println("seed id :" + sid_users);
//                fileid = AESCrypt.getnewfileid();
//                System.out.println("fileid:" + fileid);
                int fileid = Cryptography.insert_filedata(sid_users, null, fname,
                        ftype, orgbytes);

//                System.out.println("fileinsertion:" + fileinsertion);

                byte[] XORop = Cryptography.performXOR(encryptedbyte, sid_users);
                System.out.println("XOR done : sid_users "+sid_users);


                int ownerid = Cryptography.getownerid(sid_users);
                System.out.println("ownerid "+ownerid);
                boolean insert_filedetails = Cryptography.insert_filedetails(ownerid, fileid,
                        fname, secretKey, d + "", n + "");
                System.out.println("insert_filedetails "+insert_filedetails);
//                byte[] data = secretKey.getEncoded();
//                SecretKey key2 = new SecretKeySpec(data, 0, data.length, "AES");

//                System.out.println("key2" + key2.toString());
                System.out.println("----------------");


                boolean backup_insertion;
                backup_insertion = Cryptography.insert_backup(fileid, XORop, ownerid, fname, secretKey);
                System.out.println("----------------------" + backup_insertion);

                if (backup_insertion && insert_filedetails) {// if (fileinsertion && backup_insertion && insert_filedetails) {
                    session.setAttribute("flash_message", "File Uploaded successfully");
                    session.setAttribute("flash_type", "success");
                    response.sendRedirect("file_upload.jsp");
                } else {
                    session.setAttribute("flash_message", "Error in uploading file");
                    session.setAttribute("flash_type", "failure");
                    response.sendRedirect("file_upload.jsp");
                }

                String email_id = Cryptography.getuser_eid(username, password);
                ArrayList<String> eid = new ArrayList<String>();
                eid.add(email_id);
//                String KeyToString = Cryptography.KeyToString(secretKey);
                String subject = fname + "key";
//
//                boolean sendMail = SendMail.sendMail(Systememail, eid, subject, Cryptography.otp);
//
//                if (sendMail) {
//                    System.out.println("   mail  not sent  :");
//                } else {
//
//                    System.out.println("   mail sent  :");
//                }
//                System.out.println("-------------------");

            }

        } catch (Exception ee) {
            ee.printStackTrace();
            session.setAttribute("flash_message", "There is technical error.");
            session.setAttribute("flash_type", "failure");
            response.sendRedirect("file_upload.jsp");
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

    private boolean checkExtensions(String ftype,String[] extensions) {
        System.out.println("checkExtensions");
        for (int i = 0; i < extensions.length; i++) {
            String ext = extensions[i];
            System.out.println(" "+ext);
            if(ext.equals(ftype))
                return true;
        }
        return false;
    }
}
