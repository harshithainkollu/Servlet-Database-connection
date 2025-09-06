package database;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int age = Integer.parseInt(request.getParameter("age"));
        String email = request.getParameter("email");

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@LAPTOP-ACDV98FL:1521:xe", "system", "123456");

            String query = "INSERT INTO studentinfo (id, name, age, email) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.setString(4, email);
            
            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                out.println("<h2>Student Added Successfully!</h2>");
            } else {
                out.println("<h2>Error: Data not inserted.</h2>");
            }
            conn.close();
        } catch (Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@LAPTOP-ACDV98FL:1521:xe", "system", "123456");

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ID, NAME, AGE, EMAIL FROM studentinfo ORDER BY ID ASC");

            out.println("<h2>Student Records</h2>");
            out.println("<table border='1'><tr><th>ID</th><th>Name</th><th>Age</th><th>Email</th></tr>");
            while (rs.next()) {
                out.println("<tr><td>" + rs.getInt("id") + "</td><td>" +
                            rs.getString("name") + "</td><td>" +
                            rs.getInt("age") + "</td><td>" +
                            rs.getString("email") + "</td></tr>");
            }
            out.println("</table>");
            conn.close();
        } catch (Exception e) {
            out.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }
}

