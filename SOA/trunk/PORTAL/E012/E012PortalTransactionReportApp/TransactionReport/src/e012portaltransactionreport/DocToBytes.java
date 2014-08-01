package e012portaltransactionreport;

import java.io.*;

import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;

import org.xml.sax.SAXException;

public class DocToBytes {
    public DocToBytes() {
        super();
    }

    public static void main(String[] args) throws ParserConfigurationException,
                                                  SAXException, IOException {
        try {
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                                DocumentBuilder db = dbf.newDocumentBuilder();
                                Document doc = db.parse(new File("D:\\file.xml"));  

                    //Source source = new DOMSource(doc);
                    /*ByteArrayOutputStream out = new ByteArrayOutputStream();
                    StringWriter stringWriter = new StringWriter();
                    Result result = new StreamResult(out);
                    TransformerFactory factory = TransformerFactory.newInstance();
                    Transformer transformer = factory.newTransformer();
                    transformer.transform(source, result);
                    System.out.println("Output: " + out);*/
                    
                    ByteArrayOutputStream outStream = new ByteArrayOutputStream();        
                    //Print data
                    
                            
                    // Save the document to stream.        
                    Source source = new DOMSource(doc);     
                    Result result = new StreamResult(outStream);     
                    TransformerFactory factory = TransformerFactory.newInstance();   
                    Transformer transformer = factory.newTransformer();  
                    transformer.transform(source, result);     
                         
                    // Encrypt the document to byte form.        
                    byte[] docBytes = outStream.toByteArray();
            
                    // Convert  byte form to string.
                    String byteArrayString = docBytes.toString();  
                    System.out.println("Output: " + byteArrayString);
                    
                    //String sent to other service
                    
                    //Convert String to byte
                   byte[] Input = byteArrayString.getBytes();
                    System.out.println("inputString: " + Input);
            
                    //Decrypt data from Byte Array to actual String/XML file
                    String s = new String(Input);
                    System.out.println("Text Decryted : " + s);
                    
                    //return out.toByteArray();
                } catch (TransformerConfigurationException e) {
                    e.printStackTrace();
                } catch (TransformerException e) {
                    e.printStackTrace();
                }
        
    }
    
}
