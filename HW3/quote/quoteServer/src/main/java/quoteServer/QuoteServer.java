package quoteServer;

import javax.xml.ws.Endpoint;

 
public class QuoteServer {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/quotes", new Quote());
        System.out.println("Quotes Server Started!");
    }
}