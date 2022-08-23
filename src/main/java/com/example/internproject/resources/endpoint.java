package com.example.internproject.resources;

import com.example.internproject.domain.OrderDetails;
import com.example.internproject.services.OrderTaker;
import com.google.gson.Gson;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/food-point")
public class endpoint {

    @POST
    @Path("/order")
    public Response orderDetails (String payload)
    {
        OrderDetails details = new Gson().fromJson(payload, OrderDetails.class);

        OrderTaker obj = new OrderTaker();
        obj.setOrder(details);
        obj.add_inDB();
        obj.payload = payload;
        obj.estimatedTime();
        obj.add_in_queue();

        return Response.ok("Delivery time is:" +obj.estimatedTime()).build();
    }
}

