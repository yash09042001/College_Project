 
import de.flexiprovider.api.exceptions.InvalidKeySpecException;
import de.flexiprovider.core.rsa.RSAKeyFactory;
import de.flexiprovider.core.rsa.RSAPrivateKeySpec;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import main.Cryptography;
import main.EncDec;
import main.dbcon;

public class Otpprocess extends HttpServlet {

    byte[] data = null;
    int fid;
    String Filename;
    String d,n;//byte[] d,n;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public static void byteArrToFile(byte[] arr,String filepath) throws FileNotFoundException, IOException {
//        String path = System.getProperty("user.dir");
//        System.out.println("path==" + path);
        FileOutputStream fileOuputStream =
                new FileOutputStream(filepath);
        fileOuputStream.write(arr);
        fileOuputStream.close();

        System.out.println("Done");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        HttpSession session = request.getSession();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String otp = request.getParameter("key");
            Statement st = dbcon.connectDB();
            String s = "SELECT * FROM privatekey a,backupdata b where a.file_id=b.file_id and a.otp='" + otp + "'";
            
            System.out.println(s);
            ResultSet rs = st.executeQuery(s);
            while (rs.next()) {
                data = rs.getBytes("backupfile");
                fid = rs.getInt("file_id");
                Filename = rs.getString("file_name");
                d = rs.getString("d");
                n = rs.getString("n");

            }
            System.out.println("backupfile==" + data);
            System.out.println("fid==" + fid);
            System.out.println("Filename==" + Filename);
//            System.out.println("d==" + d);
//            System.out.println("n==" + n);
            BigInteger dval = new BigInteger(d);
            BigInteger nval = new BigInteger(n);
            BigInteger msg = new BigInteger(data);
            System.out.println("d=" + dval);
            System.out.println("n=" + nval);
//            System.out.println("data=" + new String(data));
            String username = (String) session.getAttribute("username");
            System.out.println("username==" + username);
            String password = (String) session.getAttribute("password");
            int sid_users = Cryptography.getsid_users(username, password); //2nd  seed id calcaulted inside this fun
            System.out.println("seed id :" + sid_users);
            byte[] cipherBytes = Cryptography.performXOR(data,sid_users);//msg.toByteArray(), sid_users);
//            System.out.println("XOR sid_users "+sid_users);
//            BigInteger Xor = new BigInteger(XORop);
//            System.out.println("XORop " + new String(XORop));
//            Decrypt obj = new Decrypt();
//            BigInteger DecryptedBytes = obj.decrypt(Xor, dval, nval);
            PrivateKey privKey = 
                new RSAKeyFactory().generatePrivate(new RSAPrivateKeySpec(nval,dval));
            System.out.println("privKey "+privKey);
            String cleartextAgainFile = getServletContext().getRealPath("/")+"/"+Filename;
            System.out.println("cleartextAgainFile "+cleartextAgainFile);
            EncDec.decryptBlocks(cipherBytes, privKey, cleartextAgainFile);
            
            
//            System.out.println("DecryptedBytes " + DecryptedBytes);
//            byteArrToFile(DecryptedBytes.toByteArray(),
//                    cleartextAgainFile);//getServletContext().getRealPath("/")+"outputfiles/"+Filename);
            
            System.out.println("DownloadFileServlet?fname="+Filename);
            response.sendRedirect("DownloadFileServlet?fname="+Filename);
        } catch (Exception ex) {
            Logger.getLogger(Otpprocess.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Otpprocess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Otpprocess.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Otpprocess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Otpprocess.class.getName()).log(Level.SEVERE, null, ex);
        }
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
