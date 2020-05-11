 package quoteClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class QuoteClient {
	public static void main(String[] args) {
		QuoteService client = new QuoteService();
		Quote quote = client.getQuotePort();
		
		Scanner in = new Scanner(System.in);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Enter 1 to get a random quote or 2 to add a quote");
		
		
		int entry = in.nextInt();
		
		if (entry == 1) {
			String s = quote.getQuote();
			System.out.println("Random Quote: " + s);
		}
		else {
			String q = "";
			try {
				q = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			quote.addQuote(q);
			System.out.println("Added to the list: " + q);
		}
	}
}