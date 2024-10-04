package org.alvarowau.webservicerestfull;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/hello-world")
public class HelloResource {
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getHtml() {
        return "<html><body>Hello World webService!</body></html>";
    }

    @PUT
    @Consumes(MediaType.TEXT_HTML)
    public void putHtml(String content) {

    }
}