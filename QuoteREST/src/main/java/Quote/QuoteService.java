package Quote;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;


@Produces("application/json")
public class QuoteService {
	
	@XmlRootElement
	public static class QuoteObject {
		@XmlElement(name ="id")
		int id;
		@XmlElement(name="quote")
		String quote;
	}
	
	static ArrayList<QuoteObject> list = new ArrayList<>();
	static int currentID = 0;
	
	@Path("/quotes")
	public static class RestService{
		
		@GET
		@Path("/get/{id}")
		public Response getQuoteById(@PathParam("id") int id) {
			QuoteObject qo = get(id);
			if (qo == null) {
				return Response.status(Response.Status.OK).entity("Quote Object " + id + " not in list").build();
			}
			return Response.status(Response.Status.OK).entity(qo).build();
		}
		
		@GET
		@Path("/add/{quote}")
		public Response addQuote(@PathParam("quote") String quote) {
			QuoteObject qo = new QuoteObject();
			int id = currentID + 1;
			qo.id = id;
			qo.quote = quote;
			list.add(qo);
			currentID++;
			return Response.status(Response.Status.OK).entity("Quote Object added: " + qo.id + ": " + qo.quote).build();
		}
		
		@GET
		@Path("/delete/{id}")
		public Response deleteQuote(@PathParam("id") int id) {
			if (get(id) == null) {
				return Response.status(Response.Status.OK).entity("Quote Object " + id + " not deleted").build();
			}
			remove(id);
			return Response.status(Response.Status.OK).entity("Quote Object " + id + " deleted").build();
		}
		
		
		public QuoteObject get(int id) {
			QuoteObject qo = null;
			
			for (QuoteObject q : list) {
				if (q.id == id)
					qo = q;
			}
			return qo;
		}
		
		public void remove(int id) {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).id == id)
					list.remove(i);
			}
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
		
		currentID = list.get(list.size()-1).id;
		
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
		sf.setResourceClasses(RestService.class);
		sf.setResourceProvider(RestService.class, new SingletonResourceProvider(new RestService()));
		sf.setAddress("http://localhost:8888/");
		sf.create();
	}

}