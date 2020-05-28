package Quote;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;



@Path("quotes")
public class QuoteService {
	
	public static void main(String[] args) throws Exception {
		String hello = "hello";
		HttpServer server = HttpServer.create(new InetSocketAddress(8888),0);
		server.createContext("/quotes", new GetHandler());
		server.setExecutor(null);
		server.start();
	}
	
	// This method is called if TEXT_PLAIN is request
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public static String getIt() {
		return "Got It!";
	}

}

class GetHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange he) throws IOException {
		String hello = "hello";
		he.sendResponseHeaders(200, hello.getBytes().length);
		final OutputStream output = he.getResponseBody();
		output.write(hello.getBytes());
		output.flush();
		he.close();
	}
	
}
