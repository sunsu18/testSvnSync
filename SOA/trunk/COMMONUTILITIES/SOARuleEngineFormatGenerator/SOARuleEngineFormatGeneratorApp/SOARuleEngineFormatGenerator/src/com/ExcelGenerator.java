package com;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.eclipse.persistence.internal.oxm.conversion.Base64;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;

import org.w3c.dom.Element;


public class ExcelGenerator {

    private oracle.soa.common.util.Base64Decoder decoder;
    private oracle.soa.common.util.Base64Encoder encoder;

    private DocumentBuilderFactory dbf;
    private DocumentBuilder db;
    private Document doc, docHeader, docTrailor;

    private NodeList parentNodeList, parentList, children, childList;
    private Node node, child, parent;

    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private HSSFRow rowInformation, rowhead, row;
    private HSSFCellStyle cs, ccs, acf;
    private HSSFFont f, cf, af;

    private ByteArrayOutputStream baos;

    private String RD, RN, str, RowHeader, rowTrailor, reportName;
    private InputSource is;
    private Integer rownum;

    private boolean flag;
    private char c;
    private Element e;

    private int headerValue, trailerValue;
    private boolean isHeader, isTrailer, dataFlag;

    public ExcelGenerator() {
        super();
    }

    public void generateExcel() throws Exception {

        rownum = 0;
        flag = true;
        dataFlag = true;
        c = 'c';
        decoder = new oracle.soa.common.util.Base64Decoder();
        
        /*is: Input Stream*/
        is = new InputSource(); 
        
        /*Initialisation of report section*/
        dbf = DocumentBuilderFactory.newInstance();
        db = dbf.newDocumentBuilder();

        /*RD: Rule Data decoded*/
        RD = decoder.decode(RD);        
        is.setCharacterStream(new StringReader(RD));
        doc = db.parse(is);

        /*RN: Root Node*/
        RN = doc.getDocumentElement().getNodeName();

        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet(this.getReportName());


        /*Cell Style-Font definitions*/
        f = workbook.createFont();
        cf = workbook.createFont();
        af = workbook.createFont();

        /*Cell Style-Font for Header of Report and Trailor of Report*/
        cs = workbook.createCellStyle();
        f.setFontName("Arial");
        f.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cs.setFont(f);

        /*Cell Style-Font for Header Row of report Data*/
        ccs = workbook.createCellStyle();
        cf.setFontName("Arial");
        cf.setItalic(true);
        ccs.setFont(cf);

        /*Cell Style-Font for Report Data*/
        acf = workbook.createCellStyle();
        af.setFontName("Arial");
        acf.setFont(af);
        acf.setAlignment(HSSFCellStyle.ALIGN_RIGHT);


        for (int count = 0;
             count < doc.getDocumentElement().getChildNodes().getLength();
             count++) {
            if ((docHeader.getDocumentElement().getChildNodes().item(count).getNodeName().toString()).equalsIgnoreCase("Header")) {
                isHeader = true;
                headerValue = count;

            } else if ((docHeader.getDocumentElement().getChildNodes().item(count).getNodeName().toString()).equalsIgnoreCase("Trailor")) {
                isTrailer = true;
                trailerValue = count;
            }
        }

        for (int count = 0;
             count < doc.getDocumentElement().getChildNodes().getLength();
             count++) {
            if (isHeader && (headerValue == count)) {
                /*Creation of Header.........................................................................................................*/
                docHeader = doc;
                parentNodeList =
                        docHeader.getDocumentElement().getChildNodes().item(count).getChildNodes();
                for (int k = 0; k < parentNodeList.getLength(); k++) {
                    rowInformation = sheet.createRow(rownum);
                    rowInformation.createCell(0).setCellValue(parentNodeList.item(k).getTextContent());
                    rowInformation.getCell(0).setCellStyle(cs);
                    rownum = rownum + 1;
                }
            } else if (isTrailer && (trailerValue == count)) {
            } else {
                
                /* Report Data generation*/

                parentNodeList = doc.getDocumentElement().getChildNodes();

                node = doc.getDocumentElement();


                /* Creation of Report.........................................................................................................*/
                parentList = node.getChildNodes();
                parent = parentList.item(count);
                children = parent.getChildNodes();
                rowhead = sheet.createRow(rownum);
                child = children.item(0);
                childList = child.getChildNodes();

                if (dataFlag) {
                    for (int j = 0; j < childList.getLength(); j++) {
                        str = childList.item(j).getNodeName();
                        if (str.contains(":")) {
                            str =
                str.substring((str.indexOf(":") + 1), str.length());
                        }
                        rowhead.createCell((j + 1)).setCellValue(str);
                        rowhead.getCell((j + 1)).setCellStyle(ccs);
                    }
                    dataFlag = false;
                }


                if (children != null) {
                    for (int i = 0; i < children.getLength(); i++) {
                        child = children.item(i);
                        e = (Element)child;
                        childList = child.getChildNodes();
                        rownum = rownum + 1;
                        row = sheet.createRow(rownum);
                        if (!e.getAttribute("aggregation").toString().isEmpty()) {

                            row.createCell((0)).setCellValue(e.getAttribute("aggregation"));
                            row.getCell(0).setCellStyle(cs);

                            for (int j = 0; j < childList.getLength(); j++) {
                                str = childList.item(j).getTextContent();
                                for (int k = 0; k < str.length(); k++) {
                                    c = str.charAt(k);
                                    if (Character.isDigit(c) || c == '.' ||
                                        c == '-') {
                                        flag = true;
                                    } else {
                                        flag = false;
                                        break;
                                    }
                                }
                                if (flag) {
                                    row.createCell((j +
                                                    1)).setCellValue(childList.item(j).getTextContent());
                                    row.getCell((j + 1)).setCellStyle(cs);
                                } else {
                                    row.createCell((j +
                                                    1)).setCellValue(childList.item(j).getTextContent());
                                    row.getCell((j + 1)).setCellStyle(cs);
                                }
                            }
                        } else {

                            for (int j = 0; j < childList.getLength(); j++) {
                                str = childList.item(j).getTextContent();
                                for (int k = 0; k < str.length(); k++) {
                                    c = str.charAt(k);
                                    if (Character.isDigit(c) || c == '.' ||
                                        c == '-') {
                                        flag = true;
                                    } else {
                                        flag = false;
                                        break;
                                    }
                                }
                                if (flag) {
                                    row.createCell((j +
                                                    1)).setCellValue(childList.item(j).getTextContent());
                                    row.getCell((j + 1)).setCellStyle(acf);
                                } else {
                                    row.createCell((j +
                                                    1)).setCellValue(childList.item(j).getTextContent());
                                    row.getCell((j + 1)).setCellStyle(acf);
                                }
                            }

                        }
                    }
                }
            }
        }
        if (isTrailer) {
            /*Trailor part of the report generation*/

            rownum = rownum + 1;

            /* Creation of Report Trailer.........................................................*/
            docTrailor = doc;

            parentNodeList =
                    docTrailor.getDocumentElement().getChildNodes().item(trailerValue).getChildNodes();

            for (int k = 0; k < parentNodeList.getLength(); k++) {


                rowInformation = sheet.createRow(rownum);
                str = parentNodeList.item(k).getTextContent();
                rowInformation.createCell(0).setCellValue(parentNodeList.item(k).getTextContent());
                rowInformation.getCell(0).setCellStyle(cs);
                rownum = rownum + 1;
            }
            /*end of Trailor part code*/
        }


        baos = new ByteArrayOutputStream();
        workbook.write(baos);
        RD = encoder.encode(new String(baos.toByteArray()));
        setRD(RD);

        byte[] encodedBytes = Base64.base64Encode(baos.toByteArray());
        String base64 = new String(encodedBytes);

        setRD(base64);
    }

    public String getRD() {
        return RD;
    }

    public void setRD(String RD) {
        this.RD = RD;
    }

    public void setRowHeader(String RowHeader) {
        this.RowHeader = RowHeader;
    }

    public String getRowHeader() {
        return RowHeader;
    }

    public void setRowTrailor(String rowTrailor) {
        this.rowTrailor = rowTrailor;
    }

    public String getRowTrailor() {
        return rowTrailor;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportName() {
        return reportName;
    }
}
