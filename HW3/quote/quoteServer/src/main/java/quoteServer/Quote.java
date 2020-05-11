package quoteServer;


import javax.jws.WebService;

import java.util.ArrayList;
import java.util.Random;

import javax.jws.WebMethod;
import javax.jws.WebParam;


@WebService
public class Quote 
{
	public ArrayList<String> list = new ArrayList<String>();
	
	Quote (){
		list.add("Quote 1");
		list.add("Quote 2");
		list.add("Quote 3");
		list.add("Quote 4");
	}
	
	@WebMethod
	public String getQuote() {
		Random random = new Random();
		int index = random.nextInt(list.size()-1);
		return list.get(index);
	} 
	
	@WebMethod
	public void addQuote(@WebParam(name = "new_quote")String quote) {
		 list.add(quote);
		 System.out.println("Added to quote list: " + quote);
	}
}