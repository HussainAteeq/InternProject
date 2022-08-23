package com.example.internproject.services;

import com.example.internproject.domain.OrderDetails;
import com.google.gson.Gson;

import java.sql.*;


public class OrderTaker {

    public String name;
    public String item_code;
    public String category;
    public String amount;
    public int delivery_time;

    public String status;

    //expected time to return to customer
    public int time = 0;

    public String payload;
    public void setOrder(OrderDetails obj)
    {
        // Setting estimated time
        if(obj.category.equals("Burger") )
        {
            obj.estimated_time = 5;
        }
        else if(obj.category.equals("Shawarma") )
        {
            obj.estimated_time = 10;
        }
        else if(obj.category.equals("Pizza"))
        {
            obj.estimated_time = 15;
        }

        obj.status = "Pending";

        name=obj.name;
        item_code=obj.itemCode;
        category=obj.category;
        amount=obj.amount;
        delivery_time= obj.estimated_time;
        status = obj.status;
    }
    public boolean add_inDB()
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/foodpoint";
            Connection con = DriverManager.getConnection(url, "root", "root");
            Statement st = con.createStatement();


            String query = "INSERT INTO food_details(name,item_code,category,amount,delivery_time,Status)VALUES('"+name+"','"+item_code+"','"+category+"', '"+amount+"',"+delivery_time+",'"+status+"') ";


            System.out.println(query);
            int rs = st.executeUpdate(query);

            if(rs==1)
            {
                System.out.println("Insertion Successful");
            }
            else
            {
                System.out.println("Insertion not Successful");
            }
            return true;

        } catch (SQLException e) {
            System.out.println("SQL");
            return false;
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
            return false;
        }
    }
    public void add_in_queue()
    {
        //Queue obj = new Queue();

        Queue.SendMessage(payload);
        try {
            //Queue.ReadMessage();
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
        }

    }
    public int estimatedTime()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/foodpoint";
            Connection con = DriverManager.getConnection(url, "root", "root");
            Statement st = con.createStatement();

            String query = "SELECT delivery_time FROM food_details WHERE Status = \'Pending\'";

            ResultSet rs = st.executeQuery(query);

            while (rs.next())
            {
                time = time + rs.getInt(1);

            }

            return time/2;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}

