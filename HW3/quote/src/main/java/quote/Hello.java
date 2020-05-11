package quote;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;


@WebService
public class Hello 
{
	 @WebMethod
	 public String hello(@WebParam(name = "name") String name) {
		 return "Hello " + name + "!";
	 }
}
