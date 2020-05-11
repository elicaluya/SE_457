package quoteServer;

import javax.xml.ws.Endpoint;

import quote.Hello;


 
public class Server {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/quotes", new Hello());
        System.out.println("Quote Started!");
    }
}
