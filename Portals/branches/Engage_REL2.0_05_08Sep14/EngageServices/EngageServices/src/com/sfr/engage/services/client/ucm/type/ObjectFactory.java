
package com.sfr.engage.services.client.ucm.type;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sfr.engage.services.client.ucm.type package. 
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

    private final static QName _SearchDocument_QNAME = new QName("http://ws.wcc.lnt.com/", "searchDocument");
    private final static QName _EditDocument_QNAME = new QName("http://ws.wcc.lnt.com/", "editDocument");
    private final static QName _EditDocumentResponse_QNAME = new QName("http://ws.wcc.lnt.com/", "editDocumentResponse");
    private final static QName _CheckInDocumentResponse_QNAME = new QName("http://ws.wcc.lnt.com/", "checkInDocumentResponse");
    private final static QName _GetFileFromUCMResponse_QNAME = new QName("http://ws.wcc.lnt.com/", "getFileFromUCMResponse");
    private final static QName _Exception_QNAME = new QName("http://ws.wcc.lnt.com/", "Exception");
    private final static QName _SearchDocumentResponse_QNAME = new QName("http://ws.wcc.lnt.com/", "searchDocumentResponse");
    private final static QName _CheckInDocument_QNAME = new QName("http://ws.wcc.lnt.com/", "checkInDocument");
    private final static QName _GetFileFromUCM_QNAME = new QName("http://ws.wcc.lnt.com/", "getFileFromUCM");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sfr.engage.services.client.ucm.type
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SearchDocumentResponse }
     * 
     */
    public SearchDocumentResponse createSearchDocumentResponse() {
        return new SearchDocumentResponse();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link GetFileFromUCM }
     * 
     */
    public GetFileFromUCM createGetFileFromUCM() {
        return new GetFileFromUCM();
    }

    /**
     * Create an instance of {@link CheckInDocument }
     * 
     */
    public CheckInDocument createCheckInDocument() {
        return new CheckInDocument();
    }

    /**
     * Create an instance of {@link GetFileFromUCMResponse }
     * 
     */
    public GetFileFromUCMResponse createGetFileFromUCMResponse() {
        return new GetFileFromUCMResponse();
    }

    /**
     * Create an instance of {@link EditDocumentResponse }
     * 
     */
    public EditDocumentResponse createEditDocumentResponse() {
        return new EditDocumentResponse();
    }

    /**
     * Create an instance of {@link CheckInDocumentResponse }
     * 
     */
    public CheckInDocumentResponse createCheckInDocumentResponse() {
        return new CheckInDocumentResponse();
    }

    /**
     * Create an instance of {@link SearchDocument }
     * 
     */
    public SearchDocument createSearchDocument() {
        return new SearchDocument();
    }

    /**
     * Create an instance of {@link EditDocument }
     * 
     */
    public EditDocument createEditDocument() {
        return new EditDocument();
    }

    /**
     * Create an instance of {@link IdcPropertyList }
     * 
     */
    public IdcPropertyList createIdcPropertyList() {
        return new IdcPropertyList();
    }

    /**
     * Create an instance of {@link CheckInResultVO }
     * 
     */
    public CheckInResultVO createCheckInResultVO() {
        return new CheckInResultVO();
    }

    /**
     * Create an instance of {@link Property }
     * 
     */
    public Property createProperty() {
        return new Property();
    }

    /**
     * Create an instance of {@link SearchInputVO }
     * 
     */
    public SearchInputVO createSearchInputVO() {
        return new SearchInputVO();
    }

    /**
     * Create an instance of {@link IdcFile }
     * 
     */
    public IdcFile createIdcFile() {
        return new IdcFile();
    }

    /**
     * Create an instance of {@link SearchResultVO }
     * 
     */
    public SearchResultVO createSearchResultVO() {
        return new SearchResultVO();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchDocument }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.wcc.lnt.com/", name = "searchDocument")
    public JAXBElement<SearchDocument> createSearchDocument(SearchDocument value) {
        return new JAXBElement<SearchDocument>(_SearchDocument_QNAME, SearchDocument.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EditDocument }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.wcc.lnt.com/", name = "editDocument")
    public JAXBElement<EditDocument> createEditDocument(EditDocument value) {
        return new JAXBElement<EditDocument>(_EditDocument_QNAME, EditDocument.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EditDocumentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.wcc.lnt.com/", name = "editDocumentResponse")
    public JAXBElement<EditDocumentResponse> createEditDocumentResponse(EditDocumentResponse value) {
        return new JAXBElement<EditDocumentResponse>(_EditDocumentResponse_QNAME, EditDocumentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckInDocumentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.wcc.lnt.com/", name = "checkInDocumentResponse")
    public JAXBElement<CheckInDocumentResponse> createCheckInDocumentResponse(CheckInDocumentResponse value) {
        return new JAXBElement<CheckInDocumentResponse>(_CheckInDocumentResponse_QNAME, CheckInDocumentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFileFromUCMResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.wcc.lnt.com/", name = "getFileFromUCMResponse")
    public JAXBElement<GetFileFromUCMResponse> createGetFileFromUCMResponse(GetFileFromUCMResponse value) {
        return new JAXBElement<GetFileFromUCMResponse>(_GetFileFromUCMResponse_QNAME, GetFileFromUCMResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.wcc.lnt.com/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchDocumentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.wcc.lnt.com/", name = "searchDocumentResponse")
    public JAXBElement<SearchDocumentResponse> createSearchDocumentResponse(SearchDocumentResponse value) {
        return new JAXBElement<SearchDocumentResponse>(_SearchDocumentResponse_QNAME, SearchDocumentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckInDocument }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.wcc.lnt.com/", name = "checkInDocument")
    public JAXBElement<CheckInDocument> createCheckInDocument(CheckInDocument value) {
        return new JAXBElement<CheckInDocument>(_CheckInDocument_QNAME, CheckInDocument.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFileFromUCM }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.wcc.lnt.com/", name = "getFileFromUCM")
    public JAXBElement<GetFileFromUCM> createGetFileFromUCM(GetFileFromUCM value) {
        return new JAXBElement<GetFileFromUCM>(_GetFileFromUCM_QNAME, GetFileFromUCM.class, null, value);
    }

}
