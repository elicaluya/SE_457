package Quote;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;


@Produces(MediaType.APPLICATION_JSON)
public class QuoteService {
	
	@XmlRootElement
	public static class QuoteObject {
		@XmlElement(name ="id")
		int id;
		@XmlElement(name="quote")
		String quote;
	}
	
	@XmlElement
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
		
//		@GET
//		@Path("/getAll")
//		public List<QuoteObject> getQAllQuotes() {
//			return list;
//		}
		
		@GET
		@Path("/getAll?page={page}&?per_page={per}")
		public List<QuoteObject> getQAllQuotesPage(@PathParam("page") int page, @PathParam("per") int per) {
			System.out.println("page: " + page + " per: " + per);
			
			return list;
		}
		
		@POST
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
		
		@DELETE
		@Path("/delete/{id}")
		public Response deleteQuote(@PathParam("id") int id) {
			if (get(id) == null) {
				return Response.status(Response.Status.OK).entity("Quote Object " + id + " not in list.").build();
			}
			remove(id);
			return Response.status(Response.Status.OK).entity("Quote Object " + id + " deleted").build();
		}
		
		@PUT
		@Path("/update/{id}/{quote}")
		public Response updateQuote(@PathParam("id") int id, @PathParam("quote")String quote) {
			if (get(id) == null) {
				return Response.status(Response.Status.OK).entity("Quote Object " + id + " not in list.").build();
			}
			get(id).quote = quote;
			return Response.status(Response.Status.OK).entity("Quote Object Updated: " + get(id).id + ": " + get(id).quote).build();
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
		QuoteObject q1 = new QuoteObject(), q2 = new QuoteObject(), q3 = new QuoteObject(), q4 = new QuoteObject(), q5 = new QuoteObject();
		q1.id = 1; q1.quote = "q1";
		q2.id = 2; q2.quote = "q2";
		q3.id = 3; q3.quote = "q3";
		q4.id = 4; q4.quote = "q4";
		q5.id = 5; q5.quote = "q5";
		list.add(q1);
		list.add(q2);
		list.add(q3);
		list.add(q4);
		list.add(q5);
		
		currentID = list.get(list.size()-1).id;
		
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
		sf.setResourceClasses(RestService.class);
		sf.setResourceProvider(RestService.class, new SingletonResourceProvider(new RestService()));
		sf.setAddress("http://localhost:8888/");
		sf.create();
	}

}