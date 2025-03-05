package main;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import main.MailSend;


public class share extends HttpServlet {

    String Systememail = "testproject201@gmail.com";
    private String s;
    public String key2, fname;
    String key1;
    
    private String email;
    private String file_id;
    private String id;
    private String mail;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        int user_id = Integer.parseInt(session.getAttribute("id").toString());
        try {
//            Statement stmt =dbcon.connectDB();
            Statement stmt = dbcon.connectDB_con().createStatement();
            String file_id = request.getParameter("file_id");
              String fname = request.getParameter("fname");
            //int id = Integer.parseInt(id);
            System.out.println("FileidServlet:" + file_id);
            String sql = "select * from privatekey where file_id='" + file_id + "'";
                  System.out.println("inserted");
            System.out.println("sql "+sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) 
            {
                key1 = rs.getString("otp");
                System.out.println("key1:" + key1);

               
                       }
            
            
            
           ArrayList<String> to = new ArrayList<String>();
            String name[] = request.getParameterValues("share");
            System.out.println("aname"+name[0]);
            for (int i = 0; i < name.length; i++) {
//            to.add(name[i]);
            String k = name[i];
                System.out.println("dd"+name[i]);
            try {
            String sql2 = "select * from users where id='" + k + "'";
            System.out.println("sql2 "+sql2);
//          static final  String mail;
              ResultSet rs1 = stmt.executeQuery(sql2);
//              final String mail=rs1.getString("email");
            if (rs1.next())
//            System.out.println("sgl2:" + sql2);
                mail=rs1.getString("email");
              try {
                String sql3 = "insert into share_file(`fileid`,`secretkey`) values('" + file_id+ "','" + key1 + "')";
                  System.out.println("sql3 "+sql3);
                int rs2 = stmt.executeUpdate(sql3);
                if (rs2 > 0) {
                    System.out.println("inserted");

                }
                System.out.println("to:" + to);
            String from="testproject201@gmail.com";
            
           
            String key = " Secret keys is " + key1 + " for file " + fname;
            System.out.println("key:" + key1);
            System.out.println("Systememail:" + Systememail);
       //   MailSend.sendMail(from, to, "Secret Keys", key1);
//          MailSend.sendMail(from, to, "Secret Keys", key1);
          SendEmail.mailSnd(mail, "Secret Keys"+key1);
            session.setAttribute("flash_message", "File Shared Successfully !!!");
            session.setAttribute("flash_type", "success");
            response.sendRedirect("sharefile.jsp");
                }catch(Exception e1){
                e1.printStackTrace();
                }
            }catch(Exception e){
            e.printStackTrace();
            }
             

            }
      


           
        } catch(Exception e2){
            e2.printStackTrace();
        }
        
    }

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
    }}


// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  
            
            
            
            
            
            
            
