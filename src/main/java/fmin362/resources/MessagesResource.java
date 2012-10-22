package fmin362.resources;

import fmin362.model.Message;
import java.util.Date;
import java.util.List;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
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
            throws Exception
    {
        UserTransaction utx = null;
        try {

            // Lookup
            InitialContext ic = new InitialContext();
            utx = ( UserTransaction ) ic.lookup( "java:comp/UserTransaction" );
            EntityManager em = ( EntityManager ) ic.lookup( "java:comp/env/persistence/EntityManager" );

            // Transaciton begin
            utx.begin();
            em.joinTransaction();

            Message msg = new Message( "Hello World!", new Date() );
            em.persist( msg );

            List<Message> messages = em.createQuery( "select m from Message m" ).getResultList();

            utx.commit();

            return messages;

        } catch ( Exception ex ) {

            try {
                if ( utx != null ) {
                    utx.setRollbackOnly();
                }
            } catch ( Exception rollbackEx ) {
                // Impossible d'annuler les changements, vous devriez logguer une erreur,
                // voir envoyer un email à l'exploitant de l'application.
            }
            throw new Exception( ex );

        }

    }

}
