
package quoteClient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the quoteClient.src.main.java.quoteClient package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetQuoteResponse_QNAME = new QName("http://quoteServer/", "getQuoteResponse");
    private final static QName _GetQuote_QNAME = new QName("http://quoteServer/", "getQuote");
    private final static QName _AddQuote_QNAME = new QName("http://quoteServer/", "addQuote");
    private final static QName _AddQuoteResponse_QNAME = new QName("http://quoteServer/", "addQuoteResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: quoteClient.src.main.java.quoteClient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddQuoteResponse }
     * 
     */
    public AddQuoteResponse createAddQuoteResponse() {
        return new AddQuoteResponse();
    }

    /**
     * Create an instance of {@link AddQuote }
     * 
     */
    public AddQuote createAddQuote() {
        return new AddQuote();
    }

    /**
     * Create an instance of {@link GetQuote }
     * 
     */
    public GetQuote createGetQuote() {
        return new GetQuote();
    }

    /**
     * Create an instance of {@link GetQuoteResponse }
     * 
     */
    public GetQuoteResponse createGetQuoteResponse() {
        return new GetQuoteResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetQuoteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://quoteServer/", name = "getQuoteResponse")
    public JAXBElement<GetQuoteResponse> createGetQuoteResponse(GetQuoteResponse value) {
        return new JAXBElement<GetQuoteResponse>(_GetQuoteResponse_QNAME, GetQuoteResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetQuote }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://quoteServer/", name = "getQuote")
    public JAXBElement<GetQuote> createGetQuote(GetQuote value) {
        return new JAXBElement<GetQuote>(_GetQuote_QNAME, GetQuote.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddQuote }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://quoteServer/", name = "addQuote")
    public JAXBElement<AddQuote> createAddQuote(AddQuote value) {
        return new JAXBElement<AddQuote>(_AddQuote_QNAME, AddQuote.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddQuoteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://quoteServer/", name = "addQuoteResponse")
    public JAXBElement<AddQuoteResponse> createAddQuoteResponse(AddQuoteResponse value) {
        return new JAXBElement<AddQuoteResponse>(_AddQuoteResponse_QNAME, AddQuoteResponse.class, null, value);
    }

}
