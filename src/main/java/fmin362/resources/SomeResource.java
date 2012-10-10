package fmin362.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path( "/some" )
public class SomeResource
{

    @GET
    public String someResource()
    {
        return "Hello World!";
    }

}
