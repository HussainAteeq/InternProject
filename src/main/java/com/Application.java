package com;
import org.glassfish.jersey.server.ResourceConfig;
import javax.ws.rs.ApplicationPath;
@ApplicationPath("/test")
public class Application extends ResourceConfig {
  public  Application()
    {
        this.packages("com.example.internproject.resources");
    }
}
