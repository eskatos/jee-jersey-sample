package fmin362.it;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import junit.framework.TestCase;

import java.net.URL;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
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

    @Test
    public void testSomeResource() throws Exception
    {
        URL url = new URL( this.baseUrl + "/resources/some" );
        Client client = Client.create();
        WebResource webResource = client.resource( url.toURI() );
        String result = webResource.get( String.class );
        assertEquals( "Hello World!", result.trim() );
    }

    @Test
    public void testMessagesResource() throws Exception
    {
        URL url = new URL( this.baseUrl + "/resources/messages" );
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put( JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE );
        Client client = Client.create( clientConfig );
        WebResource webResource = client.resource( url.toURI() );
        List<Map<String,?>> result = webResource.get( List.class );
        System.out.println( result );
        assertEquals( 2, result.size() );
        assertEquals( "Hello World!", result.get( 0 ).get( "text" ) );
    }

    @Test
    public void testJDBCServlet() throws Exception
    {
        URL url = new URL( this.baseUrl + "/some-jdbc" );
        HttpURLConnection connection = ( HttpURLConnection ) url.openConnection();
        connection.connect();
        assertEquals( 200, connection.getResponseCode() );
    }

}
