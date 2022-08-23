package com.example.internproject.backend;
import com.amazonaws.services.sqs.model.Message;
import com.example.internproject.domain.OrderDetails;
import com.example.internproject.services.Queue;
import com.google.gson.Gson;

import java.util.*;
import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;

@WebServlet(name = "ChefPortal", value = "/ChefPortal")
public class ChefPortal extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PrintWriter out = response.getWriter();


        out.println("<html><body>");
        List<Message> messages= null;
        try {
            messages = Queue.ReadMessage();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (Message m:messages)
        {
            //OrderDetails details = new Gson().fromJson(String.valueOf(m), OrderDetails.class);
            out.println("<p>"+m.getBody()+"</p><br></body></html>");
        }

        out.println("<html><body>iuy<form name = 'ChefPortal' action= 'Update_foodDetails' method= 'POST'>");
        out.println("<br><br><input type='submit' value='PICK ORDER'><br></body></html>");
        out.println("</form>");
        out.println("</body></html>");
    }
}

