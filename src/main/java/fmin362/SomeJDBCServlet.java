package fmin362;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class SomeJDBCServlet
        extends HttpServlet
{

    @Resource( name = "jdbc/DSfmin362" )
    private DataSource dataSource;

    @Override
    protected void doGet( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException
    {
        if ( dataSource == null ) {
            resp.sendError( 500, "DataSource is not properly setup!" );
            return;
        }

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit( false );

            // Ici vous pouvez faire quelque chose avec votre connection à la base de données
            // :
            // :
            // :

            connection.commit();

        } catch ( SQLException ex ) {

            if ( connection != null ) {
                try {
                    connection.rollback();
                } catch ( SQLException ex2 ) {
                    // Impossible d'annuler les changements, vous devriez logguer une erreur,
                    // voir envoyer un email à l'exploitant de l'application.
                }
            }
            resp.sendError( 500, ex.getMessage() );

        } finally {

            if ( connection != null ) {
                try {
                    connection.close();
                } catch ( SQLException ex ) {
                    // Impossible de fermer la connection, vous devriez logguer un warning
                    // voir envoyer un email à l'exploitant de l'application.
                }
            }

        }

    }

}
