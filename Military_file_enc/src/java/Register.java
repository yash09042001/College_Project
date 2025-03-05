/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
//import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.io.PrintWriter;
//import java.sql.Connection;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import main.dbcon;

/**
 *
 * @author mauli
 */
public class Register extends HttpServlet {
 PrintWriter out = null;
  public static char colors[] = {'r','g','b','y'}; 
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
      String name = request.getParameter("name");
    String password = request.getParameter("password");
//    String colorPassword[] = { request.getParameter("colorp1"),request.getParameter("colorp2"),
//                            request.getParameter("colorp3"),request.getParameter("colorp4") };

    String dob = request.getParameter("date_of_birth");
    String gender = request.getParameter("gender");
    String secretQuestion = request.getParameter("secret_question");
    String secretAnswer = request.getParameter("secret_answer");


    char colorPasswords[] = new char[Login.ccolumns];
//    for(int i=0;i < Login.ccolumns; i++){
//        colorPasswords[Integer.parseInt(colorPassword[i])-1]=colors[i];
//    }
    String colorPwd = new String(colorPasswords);
      System.out.println("colorPwd:"+colorPwd);
    Statement stmt = null;
    out = response.getWriter();
    int res=0;
        try {

         Connection con = dbcon.connectDB_con();
        stmt = con.createStatement();
//        out.print("hi <br>");
                  String query = "INSERT INTO `user` (`id` ,`username` ,`password` ,`date_of_birth` ,"
                          + "`gender` ,`secret_question` , `secret_answer` ) "
                          + "VALUES (NULL , '"+name+"', '"+password+"', '"+dob+"', '"+gender+"', '"+secretQuestion+"', "
                          + "'"+secretAnswer+"');";
                  out.print("<pre>"+query+"</pre>");
                  System.out.println("insert query:"+query);
                  res=stmt.executeUpdate(query);
                  System.out.println("after q");
    }catch(Exception e){
        out.println("Error 1: " + e);
	return;
    }
    String nextJSP = "Register";
    if(res>0){
        nextJSP = "session_home.jsp";////////////remove comments
        out.println("query executed correctly " );

    }
//    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
//    dispatcher.forward(request,response);
    response.sendRedirect(nextJSP);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
