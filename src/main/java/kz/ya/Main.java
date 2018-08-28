package kz.ya;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 *
 * @author Yerlan
 */
public class Main {

    public static final String BASE_URI = "http://localhost:8080/rest-jersey/";

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * 
     * @return new instance of HttpServer
     */
    public static HttpServer startServer() {
        // scan for JAX-RS resources and providers
        final ResourceConfig rc = new ResourceConfig().packages("kz.ya.service");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method
     * 
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        
        System.in.read();
        
        // close connections and shutdown the server
        DbConnection.closeEntityManagerFactory();
        server.shutdown();
    }
}

