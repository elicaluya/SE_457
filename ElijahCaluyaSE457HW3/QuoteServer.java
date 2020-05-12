package quoteServer;

import java.util.ArrayList;
import java.util.Random;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

// Need to make the server a WebService so that client can connect to it at the endpoint
@WebService 
public class QuoteServer {
	
	// ArrayList to hold all of the quotes
	public ArrayList<String> list = new ArrayList<String>();
	
	// Initialize the list with 5 different quotes
	QuoteServer (){
		list.add("Life is what happens when you're busy making other plans. -John Lennon");
		list.add("Those who dare to fail miserably can achieve greatly. -John F. Kennedy");
		list.add("Let us always meet each other with a smile, for the smile is the beginning of love. -Mother Theresa");
		list.add("It's our choices, that show what we truly are, far more than our abilities. -J.K. Rowling");
		list.add("If you want to live a happy life, tie it to a goal, not to people or things. -Albert Einstein");
	}
	
    public static void main(String[] args) {
    	// Publish the endpoint at the given address and make the implementor this QuoteServer class 
    	// because the QuoteServer class has the WebService annotation
        Endpoint.publish("http://localhost:8888/quotes", new QuoteServer());
        System.out.println("Quotes Server Started!");
    }
    
    // Make this method a WebMethod because it is a WebService operation that client can call
    @WebMethod
	public String getQuote() {
    	// Get random quote from the list and return it
		Random random = new Random();
		int index = random.nextInt(list.size());
		System.out.println("Sending to client: " + list.get(index));
		return list.get(index);
	} 
	
    // 	Make this method a WebMethod because it is a WebService operation that client can call
	@WebMethod
	public void addQuote(String quote) {
		// Add the parameter to the quote list
		list.add(quote);
		System.out.println("Added to quote list: " + quote);
	}
}