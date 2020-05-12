 package quoteClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class QuoteClient {
	public static void main(String[] args) {
		// Use the classes generated from wsimport:
		// Create an instance of the webservice client generated from the QuoteServer web service
		QuoteServerService client = new QuoteServerService();
		// Create an instance of the webservice server by using the getPort() method that returns a QuoteServer object at a specific address.
		// This is so we can connect and call the methods of the QuoteServer webservice.
		QuoteServer quote = client.getQuoteServerPort();
		
		// Initialize input streams
		Scanner in = new Scanner(System.in);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Enter 1 to get a random quote, 2 to add a quote, or 3 to exit");
		
		int entry = in.nextInt();
		
		// Continuously get input from client until quit command
		while (entry != 3) {
			if (entry == 1) {
				// Call the getQuote command using the QuoteServer Object and print it to user
				String s = quote.getQuote();
				System.out.println("Random quote from server: ");
				System.out.println(s);
			}
			else if (entry == 2){
				// read in input from user to add to list
				System.out.println("Enter quote to add to list: ");
				String q = "";
				try {
					q = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				// call the addQuote command using the QuoteServer object and pass it the user's input
				quote.addQuote(q);
				System.out.println("Added to the list: " + q);
			}
			else if (entry > 3 || entry < 1) {
				// Notify user of invalid entry
				System.out.println("Invalid entry!");
			}
			System.out.println("Enter 1 to get a random quote, 2 to add a quote, or 3 to exit");
			entry = in.nextInt();
		}
		System.out.println("Exiting client...");
	}
}