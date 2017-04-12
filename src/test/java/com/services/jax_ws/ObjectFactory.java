
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GetTemperatureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://jax_ws.services.com/", name = "getTemperatureResponse")
    public JAXBElement<GetTemperatureResponse> createGetTemperatureResponse(GetTemperatureResponse value) {
        return new JAXBElement<GetTemperatureResponse>(_GetTemperatureResponse_QNAME, GetTemperatureResponse.class, null, value);
    }

}
