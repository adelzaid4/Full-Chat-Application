//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.02.05 at 05:21:24 PM EET 
//


package mySchema;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mySchema package. 
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

    private final static QName _MyMsg_QNAME = new QName("", "MyMsg");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mySchema
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MyMsgType }
     * 
     */
    public MyMsgType createMyMsgType() {
        return new MyMsgType();
    }

    /**
     * Create an instance of {@link MsgsType }
     * 
     */
    public MsgsType createMsgsType() {
        return new MsgsType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MyMsgType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "MyMsg")
    public JAXBElement<MyMsgType> createMyMsg(MyMsgType value) {
        return new JAXBElement<MyMsgType>(_MyMsg_QNAME, MyMsgType.class, null, value);
    }

}
