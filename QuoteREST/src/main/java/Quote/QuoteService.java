package Quote;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;


@Produces("application/json")
public class QuoteService {
	
	@XmlRootElement
	public static class QuoteObject {
		int id;
		String quote;
	}
	
	static ArrayList<QuoteObject> list = new ArrayList<>();
	
	@Path("/quotes")
	public static class RestService{
		
		@GET
		@Path("/get/{id}")
		public QuoteObject getQuoteById(@PathParam("id") int id) {
			QuoteObject qo = null;
			for (QuoteObject q : list) {
				if (q.id == id)
					qo = q;
			}
			if (qo == null)
				System.out.println("QuoteObject not found");
			else
				System.out.println("QuoteObject requested: " + qo.id + " " + qo.quote);
			return qo;
		}
	}
	
	public static void main(String[] args) {
		QuoteObject q1 = new QuoteObject(), q2 = new QuoteObject(), q3 = new QuoteObject(), q4 = new QuoteObject();
		q1.id = 1; q1.quote = "q1";
		q2.id = 2; q2.quote = "q2";
		q3.id = 3; q3.quote = "q3";
		q4.id = 4; q4.quote = "q4";
		list.add(q1);
		list.add(q2);
		list.add(q3);
		list.add(q4);
		
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
		sf.setResourceClasses(RestService.class);
		sf.setResourceProvider(RestService.class, new SingletonResourceProvider(new RestService()));
		sf.setAddress("http://localhost:8888/");
		sf.create();
	}

}