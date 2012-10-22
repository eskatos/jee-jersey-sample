package fmin362;

import fmin362.model.Message;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

public class SomeJPAServlet
        extends HttpServlet
{

    @Resource
    private UserTransaction utx;

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException
    {
        if ( emf == null ) {
            resp.sendError( 500, "JPA Persistence Unit is not properly setup!" );
            return;
        }

        EntityManager em = emf.createEntityManager();

        try {

            utx.begin();
            em.joinTransaction();

            Message msg = new Message( "Hello World!", new Date() );
            em.persist( msg );

            List<Message> messages = em.createQuery( "select m from Message m" ).getResultList();

            utx.commit();

            resp.getWriter().println( messages.toString() );

        } catch ( Exception ex ) {

            try {
                utx.setRollbackOnly();
            } catch ( Exception rollbackEx ) {
                // Impossible d'annuler les changements, vous devriez logguer une erreur,
                // voir envoyer un email à l'exploitant de l'application.
            }
            throw new ServletException( ex );

        } finally {

            em.close();

        }
    }

}
