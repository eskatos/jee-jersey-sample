package fmin362.resources;

import fmin362.model.Message;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path( "/messages" )
public class MessagesResource
{

    @GET
    @Produces( MediaType.APPLICATION_JSON )
    public List<Message> messages()
    {
        List<Message> messages = new ArrayList<Message>();
        messages.add( new Message( "Hello World!", new Date() ) );
        messages.add( new Message( "Bazar in the Cathedral!", new Date() ) );
        return messages;
    }

}
