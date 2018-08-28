package kz.ya.service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import kz.ya.DbConnection;
import kz.ya.Main;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class CommonServiceTest {

    private static HttpServer server;
    protected static WebTarget target;
    
    @BeforeClass
    public static void setUpClass() {
        // start the server
        server = Main.startServer();
        
        // create new client
        Client client = ClientBuilder.newClient();
        target = client.target(Main.BASE_URI);
    }
    
    @AfterClass
    public static void tearDownClass() {
        // close connections and shutdown the server
        DbConnection.closeEntityManagerFactory();
        server.shutdown();
    }
}
