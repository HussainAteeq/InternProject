package com.example.internproject.backend;
import com.amazonaws.services.sqs.model.Message;
import com.example.internproject.domain.OrderDetails;
import com.example.internproject.services.Queue;
import com.google.gson.Gson;
import com.example.internproject.services.Queue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.io.*;
import javax.management.Query;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;

import static java.lang.System.out;

@WebServlet(name = "Update_foodDetails", value = "/Update_foodDetails")
public class Update_foodDetails extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql://127.0.0.1/foodpoint";

            Connection con= DriverManager.getConnection(url, "root", "root");

            Statement st=con.createStatement();

            String query = "UPDATE food_details SET STATUS = 'Completed' WHERE STATUS = 'InProcess'";
            int rs = st.executeUpdate (query);

            Email.sendEmail("hussainattique1@gmail.com");

            String order = Queue.ReadFirstMessage();
            OrderDetails details = new Gson().fromJson(order, OrderDetails.class);
            System.out.println(details.name);
            System.out.println(details.category);
            System.out.println(details.itemCode);
            System.out.println(details.amount);



            String code = details.itemCode;
            String query1 = "UPDATE food_details SET STATUS = 'InProcess' WHERE item_code = "+code+" ";
            int rs1 = st.executeUpdate (query1);

           // System.out.println("Message of RS is;" +rs1);

            Queue.DeleteFirstMessage();
            PrintWriter out = response.getWriter();
            try {
                List<Message> messages = Queue.ReadMessage();
                for (Message m : messages) {
                    //OrderDetails details = new Gson().fromJson(String.valueOf(m), OrderDetails.class);
                    out.println("<p>" + m.getBody() + "</p><br></body></html>");
                }
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }

            out.println("<form name = 'ChefPortal' action= 'Update_foodDetails' method= 'POST'>");
            out.println("<br><br><input type='submit' value='PICK ORDER'><br></body></html>");
            out.println("</form>");
            out.println("</body></html>");


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}