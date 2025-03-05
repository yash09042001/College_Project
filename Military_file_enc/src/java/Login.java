/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import main.dbcon;

/**
 *
 * @author Gulshan
 */
public class Login extends HttpServlet {
static final long serialVersionUID = 1L;
    PrintWriter out = null;
    String header = null;
    public static int trows = 6, tcolumns = 6, crows = 1, ccolumns = 4;
    private int user_id = 0;

    public Login() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("name");
        String upassword = request.getParameter("password");
//        String colorPassword = request.getParameter("colorPassword");
        System.out.println("name: " + name + " password: " + upassword);
        String sql = "select id from user where username='" + name + "'";
        System.out.println("sql: " + sql);
        Statement st = null;
        try {
            st = dbcon.connectDB();
        } catch (Exception ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                int uid = rs.getInt(1);
//                sql = "insert into sessions(user_id,session_password,session_password_color) values (" + uid + ",'" + password + "','" + colorPassword + "')";
//                System.out.println("sql: " + sql);
//                Statement stmt = DBConn.connect();
//                stmt.executeUpdate(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Statement stmt = null;
        out = response.getWriter();
        try {
            Connection con = dbcon.connectDB_con();
            stmt = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        String nextJSP = "/home.jsp";
        if (name.equals("") || upassword.equals("")) {
            nextJSP = "/Login";
            request.setAttribute("login_failed", "1");
            return;
        }

        HttpSession session = request.getSession();
        char[][] textMatrix = (char[][]) session.getAttribute("textMatrix");
//        String[] colorMatrix = (String[]) session.getAttribute("colorMatrix");
//        int[][] colorNumMatrix = (int[][]) session.getAttribute("colorNumMatrix");
        System.out.println("textMatrix");

        if (!processLogin(stmt, name, upassword, textMatrix)) {
            nextJSP = "/Login";
            request.setAttribute("login_failed", "1");
        } else {
            session.setAttribute("is_logged", "1");
            session.setAttribute("userid", name);
            session.setAttribute("user_id", "" + this.user_id);
            System.out.println("home.jsp");
            nextJSP = "/login.jsp";
        }
        System.out.println("nextJSP:" + nextJSP);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
        dispatcher.forward(request, response);
    }

    private boolean processLogin(Statement stmt, String name,
            String password, char[][] textMatrix) {
        if (name != null && password != null) {
            try {

                String query = "select * from user where username='" + name + "';";
                ResultSet rs = stmt.executeQuery(query);
                String dbpassword = ""; // dbcolorpassword = "";
                while (rs.next()) {
                    dbpassword = rs.getString("password");
                    System.out.println("dbpass == " + dbpassword);

                    this.user_id = rs.getInt("id");
                }
                if (validateUser(dbpassword, name, password, textMatrix)) {
//                    return validateUserColor(dbcolorpassword, name, colorPassword, colorMatrix, colorNumMatrix);
                    return true;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }
        return false;
    }

    private boolean validateUser(String dbpassword, String name, String pwd, char[][] textMatrix) {
        if (dbpassword != null && !dbpassword.equals("")) {
            char[] password = dbpassword.toCharArray(); //Password retrieved from db
            out.print(new String(password) + " pp <br/>");
            System.out.println("password"+password);
            String sessionPassword = "";

            for (int k = 0; k < password.length; k++) {
                for (int i = 0; i < 6; i++) {
                    for (int j = 0; j < 6; j++) {
                        System.out.println("k"+password[k]);
                        if (textMatrix[i][j] == password[k]) {
                            System.out.println(textMatrix[i][j] + " == i :" + i + " j :" + j);
                            sessionPassword += i + "" + j;
                            System.out.println("s password: " + sessionPassword);
                        }
                    }
                }

            }


//          //  int[] pindex = new int[password.length];
//            // int k = 0;
//            for (char pchar : password) {
//                // System.out.println(" k = "+ k);
//                for (int i = 1; i <= 6; i++) {
//                    for (int j = 1; j <= 6; j++) {
//
//                        if (textMatrix[i][j] == pchar) {
//                            System.out.println(textMatrix[i][j] + " == i :" + i + " j :" + j);
//                            sessionPassword += i + "" + j;
//                            System.out.println("s password: " + sessionPassword);
////                            pindex[k] = (k % 2 == 0) ? i : j;
////                            System.out.println(" pindex[k] = "+ pindex[k] );
//                            System.out.println(" i :" + i + " j :" + j);
//                        }
//                    }
//                }
//                if (k % 2 != 0) {
//                    sessionPassword += textMatrix[pindex[k - 1]][pindex[k]];
//                   System.out.println("s password: " + sessionPassword);
//                }
//                k++;
            //}
            System.out.println("session password:" + sessionPassword);
            out.print(sessionPassword + "<br/> ::: " + pwd + "<br/>");
            System.out.println("session password:" + sessionPassword);
            if (pwd.equalsIgnoreCase(sessionPassword)) {
                System.out.println("hellllll");
                out.print("Login Successful using text<br>");
                return true;
            }
        }
        return false;
    }
//    private boolean validateUserColor(String dbcolorpassword, String name, String pwd, String[] colorMatrix, int[][] cNumMatrix) {
//        if (dbcolorpassword != null && !dbcolorpassword.equals("")) {
//            String sessionPassword = "";
//            int[] pindex = new int[dbcolorpassword.length()];
//            int k = 0;
//            String[] colors = new String[ccolumns];
//            for (char pchar : dbcolorpassword.toCharArray()) {
//                switch (pchar) {
//                    case 'r':
//                        colors[k] = "#ff0000";
//                        break;
//                    case 'g':
//                        colors[k] = "#00ff00";
//                        break;
//                    case 'b':
//                        colors[k] = "#0000ff";
//                        break;
//                    case 'y':
//                        colors[k] = "#ffff00";
//                        break;
//                }
//                for (int j = 0; j < ccolumns; j++) {
//                    if (colorMatrix[j].equalsIgnoreCase(colors[k])) {
//                        pindex[j] = k;
//                    }
//                }
//                k++;
//            }
//            for (int l = 0; l < ccolumns; l++) {
//                if (l % 2 != 0) {
//                    sessionPassword += "" + cNumMatrix[pindex[l - 1]][pindex[l]];
//                }
//            }
//            out.print(sessionPassword + "<br/>" + pwd + "<br/>");
//            System.out.println("sessionpassword12:" + sessionPassword);
//
//            if (pwd.equalsIgnoreCase(sessionPassword)) {
//                out.print("Login Successful using color");
//                return true;
//            }
//        }
//        return false;
//    }
}
