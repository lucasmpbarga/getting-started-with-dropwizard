
package com.mycompany.dropbooks.resources;


import com.mycompany.dropbooks.core.User;
import io.dropwizard.auth.Auth;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class HelloResource {
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getGereeting(){
        return "Hello World";
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/secured")
    public String getSecuredGreeting(@Auth User user){
        return "hello secured world";
    }
}
