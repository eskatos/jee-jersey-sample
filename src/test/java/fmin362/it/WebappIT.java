package fmin362.it;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import junit.framework.TestCase;

import java.net.URL;
import java.net.HttpURLConnection;
import org.junit.Test;

public class WebappIT extends TestCase
{
    private String baseUrl;

    public void setUp() throws Exception
    {
        super.setUp();
        String port = System.getProperty("servlet.port");
        this.baseUrl = "http://localhost:" + port + "/cargo-webapp";
    }

    public void testCallIndexPage() throws Exception
    {
        URL url = new URL(this.baseUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        assertEquals(200, connection.getResponseCode());
    }

    @Test
    public void testSomeServlet() throws Exception
    {
        URL url = new URL( this.baseUrl + "/some" );
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        assertEquals(200, connection.getResponseCode());
        InputStream inputStream = connection.getInputStream();
        try {
            BufferedReader buffer = new BufferedReader( new InputStreamReader( inputStream, "UTF-8" ) );
            StringBuilder content = new StringBuilder();
            String line;
            while ( ( line = buffer.readLine() ) != null ) {
                content.append( line );
            }
            String result = content.toString();
            assertEquals( "Hello World!", result.trim() );

        } finally {
            inputStream.close();
        }
    }

}
