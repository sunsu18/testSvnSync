package com;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;


public class ExcelGenerator {
    
    private oracle.soa.common.util.Base64Decoder decoder;
    private oracle.soa.common.util.Base64Encoder encoder;
   
    private DocumentBuilderFactory dbf;
    private DocumentBuilder db;
    private Document doc;
    
    private NodeList parentNodeList, parentList, parentList1, children, childList;
    private Node node, child, parent, parent1; 
    
    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private HSSFRow rowInformation, rowhead, row;
    private HSSFCellStyle cs, ccs, acf;
    private HSSFFont f, cf, af;
    
    private FileOutputStream fileOut;
    private ByteArrayOutputStream baos;
   
    private String RD, RN, str, filename;
    private InputSource is;
    private Integer rownum;
    
    private boolean flag;
    private char c;
    private byte[] ab;
    
    public ExcelGenerator() {
        super();
    }
    
    public void generateExcel(ExcelReportInput in)throws Exception
    {           
//        try{
        rownum = 0;
        flag = true;
        c = 'c';
        filename = "/u01/SOA_DEV/SOAFilestore/HOME/DEV/NewExcelFile.xls";
        decoder = new oracle.soa.common.util.Base64Decoder();
        is = new InputSource();
        RD= decoder.decode(in.getRD());
//        RD= decoder.decode(this.getRD());
//        RD= (new String (RD.getBytes()));
        //Below line added for byte array usage
//        ab = this.getAb();
//        String Bstr = new String(ab);
//        RD= decoder.decode(Bstr);
        this.setRD(RD);
        
        
       
        
        //coding
        is.setCharacterStream(new StringReader(RD));
        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();
        doc = db.parse(is);  
        
            //new code not required further
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer)); 
            String st = writer.getBuffer().toString();
            this.setRD(st);
            //end new
        

        RN = doc.getDocumentElement().getNodeName();
        
        workbook=new HSSFWorkbook();
        sheet =  workbook.createSheet(RN);
        f = workbook.createFont();
        cf = workbook.createFont();
        af = workbook.createFont();
        f.setFontName("Arial");
        cf.setFontName("Lucida Calligraphy");
        af.setFontName("Arial");
        f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cs = workbook.createCellStyle();
        ccs = workbook.createCellStyle();
        acf = workbook.createCellStyle();
        cs.setFont(f);
        ccs.setFont(cf);
        acf.setFont(af);
        acf.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        
        parentNodeList = doc.getDocumentElement().getChildNodes();
        
        node = doc.getDocumentElement();
//previous line below
//        children = node.getChildNodes();
        
        //lines changed below
        parentList = node.getChildNodes();
        parent = parentList.item(0);
//        parentList1 = parent.getChildNodes();
//        parent1 = parentList1.item(0);
        children = node.getChildNodes();
        
        rowInformation = sheet.createRow(rownum);
        rownum = rownum + 1;
        rowhead = sheet.createRow(rownum);
        child = parentList.item(0);
        childList = child.getChildNodes();
        
        for(int j=0; j< childList.getLength(); j++){
            str = childList.item(j).getNodeName();
            if(str.contains(":"))
            {
                str = str.substring( (str.indexOf(":")+1),str.length());
                }
                    rowhead.createCell(j).setCellValue(str);
                    rowhead.getCell(j).setCellStyle(ccs);
            }
            
            
            if (children != null)
            {
                for(int i=0; i < children.getLength(); i++){
                    child = children.item(i);
                    childList = child.getChildNodes();
                    rownum = rownum + 1;
                    row = sheet.createRow(rownum);
                    for(int j=0; j< childList.getLength(); j++){
                        str = childList.item(j).getTextContent();
                        for(int k=0; k < str.length(); k++){
                            c = str.charAt(k);
                            if(Character.isDigit(c) || c == '.' || c == '-'){
                                flag = true;
                            }
                            else{
                                flag = false;
                                break;
                                }
                            }                        
                        if(flag){
                            row.createCell(j).setCellValue(childList.item(j).getTextContent());
                            row.getCell(j).setCellStyle(acf);    
                            }
                        else{                                
                                row.createCell(j).setCellValue(childList.item(j).getTextContent());
                            }
                            
                        
                        }
                }
            }
            baos = new ByteArrayOutputStream();
            workbook.write(baos);
            RD = encoder.encode(new String(baos.toByteArray()));
            setRD(RD);
//            RD = encoder.encode(workbook.toString());
            fileOut = new FileOutputStream(filename);
            workbook.write(fileOut);
            fileOut.close();
//        }catch(Exception e1)     
//        {
//            System.out.println("Exception : " + e1.getMessage());
//            }

        }

    public String getRD() {
        return RD;
    }

    public void setRD(String RD) {
        this.RD = RD;
    }

    public void setAb(byte[] ab) {
        this.ab = ab;
    }

    public byte[] getAb() {
        return ab;
    }
}
