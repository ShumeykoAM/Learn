
package com.services.jax_ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.services.jax_ws package. 
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

    private final static QName _GetTemperature_QNAME = new QName("http://jax_ws.services.com/", "getTemperature");
    private final static QName _GetLikeBook_QNAME = new QName("http://jax_ws.services.com/", "getLikeBook");
    private final static QName _GetLikeBookResponse_QNAME = new QName("http://jax_ws.services.com/", "getLikeBookResponse");
    private final static QName _Book_QNAME = new QName("http://jax_ws.services.com/", "book");
    private final static QName _GetTemperatureResponse_QNAME = new QName("http://jax_ws.services.com/", "getTemperatureResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.services.jax_ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetTemperatureResponse }
     * 
     */
    public GetTemperatureResponse createGetTemperatureResponse() {
        return new GetTemperatureResponse();
    }

    /**
     * Create an instance of {@link Book }
     * 
     */
    public Book createBook() {
        return new Book();
    }

    /**
     * Create an instance of {@link GetLikeBookResponse }
     * 
     */
    public GetLikeBookResponse createGetLikeBookResponse() {
        return new GetLikeBookResponse();
    }

    /**
     * Create an instance of {@link GetLikeBook }
     * 
     */
    public GetLikeBook createGetLikeBook() {
        return new GetLikeBook();
    }

    /**
     * Create an instance of {@link GetTemperature }
     * 
     */
    public GetTemperature createGetTemperature() {
        return new GetTemperature();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTemperature }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jax_ws.services.com/", name = "getTemperature")
    public JAXBElement<GetTemperature> createGetTemperature(GetTemperature value) {
        return new JAXBElement<GetTemperature>(_GetTemperature_QNAME, GetTemperature.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLikeBook }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jax_ws.services.com/", name = "getLikeBook")
    public JAXBElement<GetLikeBook> createGetLikeBook(GetLikeBook value) {
        return new JAXBElement<GetLikeBook>(_GetLikeBook_QNAME, GetLikeBook.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLikeBookResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jax_ws.services.com/", name = "getLikeBookResponse")
    public JAXBElement<GetLikeBookResponse> createGetLikeBookResponse(GetLikeBookResponse value) {
        return new JAXBElement<GetLikeBookResponse>(_GetLikeBookResponse_QNAME, GetLikeBookResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Book }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jax_ws.services.com/", name = "book")
    public JAXBElement<Book> createBook(Book value) {
        return new JAXBElement<Book>(_Book_QNAME, Book.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTemperatureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jax_ws.services.com/", name = "getTemperatureResponse")
    public JAXBElement<GetTemperatureResponse> createGetTemperatureResponse(GetTemperatureResponse value) {
        return new JAXBElement<GetTemperatureResponse>(_GetTemperatureResponse_QNAME, GetTemperatureResponse.class, null, value);
    }

}
