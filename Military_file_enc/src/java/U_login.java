import de.flexiprovider.core.FlexiCoreProvider;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Security;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import main.Cryptography;
import main.dbcon;

/**
 *
 * @author eiosys
 */

public class U_login extends HttpServlet {
    
    static {
        System.out.println("addprovider " + Security.addProvider(new FlexiCoreProvider()));

    }
    public static Date lastlogin() {
       // Instantiate a Date object
       Date date;
        date = new Date();
        
       // display time and date using toString()
       System.out.println(date.toString());
        return date;
   }

    public static String getlastlogin( HttpSession session) throws ClassNotFoundException, SQLException {
        String r=null;
         Statement st3 = dbcon.connectDB();
                String select="select * from log_time where User_name='"+session.getAttribute("username")+"'";
                System.out.println("select="+select);
                ResultSet rs1=st3.executeQuery(select);
                while(rs1.next()){
                   r=rs1.getString("Login_Time");
                    
                }
                return r;
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        try {
            String uname=null;
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            System.out.println("username" + username + "password:" + password);
            boolean valid = Cryptography.userauth(username, password);

            System.out.println("user login sucees:" + valid);
            if (valid) {
                session.setAttribute("username", username);
                session.setAttribute("password", password);
                session.setAttribute("id", Cryptography.id);
                session.setAttribute("userType", "user");
                String last=getlastlogin(session);
                session.setAttribute("flash_message", "" + username + "Logged in successfully and Your Last Login time is :"+last);
                session.setAttribute("flash_type", "success");
                Statement st2 = dbcon.connectDB();
                String select="select * from log_time where User_name='"+username+"'";
                System.out.println("select="+select);
                ResultSet rs=st2.executeQuery(select);
                while(rs.next()){
                    uname=rs.getString("User_name");
                    
                }
                Date lastLogin=lastlogin();
                System.out.println("rs=="+rs);
                if (uname==null) {
                    Statement st = dbcon.connectDB();
                    String insert = "INSERT INTO log_time(User_name,Login_Time) VALUES('" + username + "','" + lastLogin + "')";
                    int query1 = st.executeUpdate(insert);
                } else {

                    Statement st1 = dbcon.connectDB();
                    String insert1 = "update log_time set  Login_time='" + lastLogin + "' where User_name='" + username + "'";
                    int executequery = st1.executeUpdate(insert1);
                }
                response.sendRedirect("main.jsp");
            } else {
                session.setAttribute("flash_message", "Invalid Username or password");
                session.setAttribute("flash_type", "failure");
                response.sendRedirect("login.jsp");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("flash_message", "There is technical error.");
            session.setAttribute("flash_type", "failure");
            response.sendRedirect("login.jsp");
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
