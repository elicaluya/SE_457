package Quote;

import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;



/******************************* Main Class that holds all info for REST service, QuoteObjects, and list of Quotes ******************************/
// Need to set the Produces annotation to JSON as specified in the instructions
@Produces(MediaType.APPLICATION_JSON)
public class QuoteService {
	
	
	// Quote Object with specified RootName and Property so that we can serialize the data and send it to client as a Response
	@JsonRootName("QuoteObject")
	public static class QuoteObject {
		@JsonProperty
		int id;
		@JsonProperty
		String quote;
	}
	
	// List of Quotes
	static ArrayList<QuoteObject> quote_list = new ArrayList<>();
	// Keep track of the current ID
	static int currentID = 0;
	
	
	
/***************************************** Class with Functions for specific HTTP requests ****************************************/
	
	// Set path for REST service class with URI versioning as the API versioning strategy
	@Path("/quotes/v1")
	public static class RestService{
		
		// Method for making a get request for one specific quote via id.
		// Put id as a PathParam because we cannot make a call for no specific quote.
		// Use the GET annotation since this method will be called via a HTTP GET request
		@GET
		@Path("/get/{id}")
		public Response getQuoteById(@PathParam("id") int id) throws IOException{
			QuoteObject qo = get(id);
			// If the quote is not in the list send back to client a response that the quote does not exist
			if (qo == null) {
				// Output in the server and client
				System.out.println("Quote Object " + id + " not in list.");
				return Response.status(Response.Status.OK).entity("Quote Object " + id + " not in list").build();
			}
			System.out.println("Quote Object returned: " + qo.id + ": " + qo.quote);
			
			// Need to serialize the JSON Quote Object so that we can send it as an entity in the response back to the client
			ObjectMapper om = new ObjectMapper();
			om.enable(SerializationFeature.WRAP_ROOT_VALUE);
			// Set the JSON object as a string so we can put it in Response
			String payload = om.writeValueAsString(qo);
			
			// Respond back to client the serialized Quote Object so that it is easy to read and see the data
			return Response.status(Response.Status.OK).entity(payload).build();
		}
		
		
		
		// Method for making a get request for all quotes with index pagination.
		// Use QueryParam annotation so that it is optional to use index pagination.
		// Use DefaultValue annotation to specify if ?page= or ?per_page= are not specified
		// Use the GET annotation since this method will be called via a HTTP GET request 
		@GET
		@Path("/getAll")
		public Response getQAllQuotes(@DefaultValue("1")@QueryParam("page") int page, 
									@DefaultValue("-1")@QueryParam("per_page") int per_page) throws IOException{
			
			// Check if default value and if so set it to the size of the quote list
			if (per_page == -1) per_page = quote_list.size();
			
			// Get the starting and ending indexes to loop through the quote list
			int start = (page * per_page) - per_page;
			int end  = (start + per_page);
			
			// If there is an overlap between the end index and size of the list, just get the remaining objects that do not fill a whole page
			if (end > quote_list.size()) end = quote_list.size();
			
			// Need to serialize the JSON objects so that we can put it in Response
			ObjectMapper om = new ObjectMapper();
			om.enable(SerializationFeature.WRAP_ROOT_VALUE);
			String payload = "";
			
			System.out.println("Returning the following Quote Objects on page: " + page + " with per_page: " + per_page);
			// Loop through from the starting and ending index to get the Quote objects that are on the page and in the range specified
			for (int i = start; i < end; i++) {
				// Add the JSON Quote Objects as a string so we can put it in the entity of the Response
				payload += om.writeValueAsString(quote_list.get(i)) + "\n";
				System.out.println("QuoteObject id: " + quote_list.get(i).id + ": " + quote_list.get(i).quote);
			}
			
			// Send to the client the Quote Objects in the specified range as a string in the Response entity so that it will be easy to see the data
			return Response.status(Response.Status.OK).entity(payload).build(); 
		}
				
		
		
		// Method for adding a new Quote Object to the quote list
		// Use PathParam so we can create a specific path in which we need a quote to add to the list (Cannot leave quote empty in path or call will fail)
		// Use POST Annotation because we are creating new resource at this specific path
		@POST
		@Path("/add/{quote}")
		public Response addQuote(@PathParam("quote") String quote) throws IOException {
			// Create new Quote Object and fill in its data with the given quote and updated ID in the list
			QuoteObject qo = new QuoteObject();
			int id = currentID + 1;
			qo.id = id;
			qo.quote = quote;
			quote_list.add(qo);
			currentID++;
			System.out.println("Quote Object added: " + qo.id + ": " + qo.quote);
			
			// Serialize Quote object so we can put it in entity of Response 
			ObjectMapper om = new ObjectMapper();
			om.enable(SerializationFeature.WRAP_ROOT_VALUE);
			String payload = om.writeValueAsString(qo); 
			
			// Send back to client successfully added new Quote Object with its JSON data in the entity of the Response
			return Response.status(Response.Status.OK).entity("Successfully added Quote Object: " + "\n" + payload).build();
		}
		
		
		
		// Method for deleting a specific quote via id
		// Use PathParam to create specific path to delete a specific quote
		// Use DELETE annotation since we want to call the method with a DELETE HTTP request too delete a specified resource
		@DELETE
		@Path("/delete/{id}")
		public Response deleteQuote(@PathParam("id") int id) {
			// Check if specified id is in the quote list
			if (get(id) == null) {
				// If not in list send back to client a response saying it doesn't exist
				System.out.println("Quote Object " + id + " not in list.");
				return Response.status(Response.Status.OK).entity("Quote Object " + id + " not in list.").build();
			}
			
			// Remove the Quote Object from the quote list and respond back to the client with message saying it was successfully deleted 
			remove(id);
			System.out.println("Quote Object " + id + " deleted");
			return Response.status(Response.Status.OK).entity("Quote Object " + id + " successfully deleted").build();
		}
		
		
		
		// Method for updating a specific quote
		// Use PathParams for the id and quote so we can get a specific path for this request
		// Use PUT method because it is idempotent and so we will not update an id with an identical quote multiple times 
		@PUT
		@Path("/update/{id}/{quote}")
		public Response updateQuote(@PathParam("id") int id, @PathParam("quote")String quote) throws IOException {
			// Check if specified Quote Object is in the list
			if (get(id) == null) {
				// If not send back response to client that it doesn't exist
				System.out.println("Quote Object " + id + " not in list.");
				return Response.status(Response.Status.OK).entity("Quote Object " + id + " not in list.").build();
			}
			// Set the Quote Object's new quote
			get(id).quote = quote;
			
			// Need to serialize JSON Quote Object as a string so we can send it in the entity of the Response
			ObjectMapper om = new ObjectMapper();
			om.enable(SerializationFeature.WRAP_ROOT_VALUE);
			String payload = om.writeValueAsString(get(id));
			
			System.out.println("Quote Object Updated: " + get(id).id + ": " + get(id).quote);
			return Response.status(Response.Status.OK).entity("Quote Object Successfully Updated: " + "\n" + payload).build();
		}
		
		
/********************************************** Helper Functions *********************************************/
		// Returning the Quote Object from the list via id
		public QuoteObject get(int id) {
			QuoteObject qo = null;
			
			for (QuoteObject q : quote_list) {
				if (q.id == id)
					qo = q;
			}
			return qo;
		}
		
		// Removing Quote Object from the list via id
		public void remove(int id) {
			for (int i = 0; i < quote_list.size(); i++) {
				if (quote_list.get(i).id == id)
					quote_list.remove(i);
			}
		}
	}
	
	
/********************************************** Main Method to start REST Service *********************************************/
	public static void main(String[] args) {
		
		// Create initial Quote Objects and add them to the list
		QuoteObject q1 = new QuoteObject(), 
					q2 = new QuoteObject(), 
					q3 = new QuoteObject(), 
					q4 = new QuoteObject(), 
					q5 = new QuoteObject();
		
		q1.id = 1; q1.quote = "Life is what happens when you're busy making other plans. -John Lennon";
		q2.id = 2; q2.quote = "Those who dare to fail miserably can achieve greatly. -John F. Kennedy";
		q3.id = 3; q3.quote = "Let us always meet each other with a smile, for the smile is the beginning of love. -Mother Theresa";
		q4.id = 4; q4.quote = "It's our choices, that show what we truly are, far more than our abilities. -J.K. Rowling";
		q5.id = 5; q5.quote = "If you want to live a happy life, tie it to a goal, not to people or things. -Albert Einstein";
		
		quote_list.add(q1);
		quote_list.add(q2);
		quote_list.add(q3);
		quote_list.add(q4);
		quote_list.add(q5);
		
		// Update the current ID according to the number of Quote Objects in the list
		currentID = quote_list.get(quote_list.size()-1).id;
		
		// Start up the REST Service on specified address
		// Used the Jetty Server so that I can start up the service without the use of Tomcat or Glassfish
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
		sf.setResourceClasses(RestService.class);
		sf.setResourceProvider(RestService.class, new SingletonResourceProvider(new RestService()));
		sf.setAddress("http://localhost:8888/");
		sf.create();
	}

}