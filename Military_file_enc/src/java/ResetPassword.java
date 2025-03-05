/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import main.dbcon;

/**
 *
 * @author mauli
 */
public class ResetPassword extends HttpServlet {

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
          String uname = request.getParameter("username");
        HttpSession session=request.getSession();
        System.out.println("uname: " + uname);
//        String colorPassword[] = {
//            request.getParameter("colorp1"), request.getParameter("colorp2"),
//            request.getParameter("colorp3"), request.getParameter("colorp4")};
//        char colorPasswords[] = new char[Login.ccolumns];
//        for (int i = 0; i < Login.ccolumns; i++) {
//            colorPasswords[Integer.parseInt(colorPassword[i]) - 1] = Register.colors[i];
//        }
//        String colorPwd = new String(colorPasswords);
        String password = request.getParameter("password");
          String ans = request.getParameter("secret_answer");
        Statement stmt = null;
        try {
           Connection con = dbcon.connectDB_con();
            stmt = con.createStatement();
            String query = "Update `user` set password='" + password + "'  where username='" + uname + "' and secret_answer='"+ans+"'";
            System.out.println("query: "+query);
            int rs = stmt.executeUpdate(query);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Password not reset successfully.");
            return;
        }
        session.setAttribute("resetPassword","Password updated successfully.");
        response.sendRedirect("session_home.jsp");
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
