
package com.mycompany.dropbookmarks.resources;


import com.mycompany.dropbookmarks.core.User;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
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
    @UnitOfWork
    public String getSecuredGreeting(@Auth User user){
        return "hello secured world";
    }
}
