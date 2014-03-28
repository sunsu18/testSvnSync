package com.sfr.engage.util;

import com.sfr.engage.services.core.dao.factory.DAOFactory;
import com.sfr.engage.services.core.dao.factory.DAOFactory.*;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.OracleDriver;

public class ImageMapServlet extends HttpServlet{
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
    private static final long serialVersionUID = 1L;

    public ImageMapServlet() {
        super();
    }
    
    public void init(ServletConfig config)
      throws ServletException
    {
      super.init(config);
    }
    
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
      throws ServletException, IOException
    {
    
        OutputStream os = response.getOutputStream();
        Connection Connection1 = null;
        
        response.setContentType(CONTENT_TYPE);
        System.out.println("Request1 -----------------------> " + request.getParameter("param1"));
        System.out.println("Request2 -----------------------> " + request.getParameter("param2"));
        System.out.println("Request3 -----------------------> " + request.getParameter("param3"));
        System.out.println("Request4 -----------------------> " + request.getParameter("param4"));
        System.out.println("Request5 -----------------------> " + request.getParameter("param5"));
        
        String Feature_Name = request.getParameter("param1");
        String Feature_Value = request.getParameter("param2");
        String Control_Attr = request.getParameter("param3");
        String Attr_Value = request.getParameter("param4");
        String Control_Attr_Value;
        if(Feature_Name.equalsIgnoreCase("LISTPRICE"))
        {
            Control_Attr_Value = "41S5" + Attr_Value;
            System.out.println("Control_Attr_Value" + Control_Attr_Value);
        }
        else{
            Control_Attr_Value = null;
        }
        
        String lang = request.getParameter("param5");
        
        try {
                if (Feature_Name != null && Feature_Value!= null && Control_Attr_Value != null) 
                {
                    Connection1 = DAOFactory.getJNDIConnection();
                    PreparedStatement statement = Connection1.prepareStatement("SELECT imageid from PRT_GEN_IMAGE_MAP where feature_name=? and feature_value=? and control_attr_value=?");
                    statement.setString(1, Feature_Name);
                    statement.setString(2, Feature_Value);
                   // statement.setString(3, Control_Attr);
                    statement.setString(3, Control_Attr_Value);
                    //statement.setString(5, lang);

                    //statement.setInt(1, new Integer(imageId));
                    ResultSet rs = statement.executeQuery();
                    if (rs.next()) {
                        System.out.println("record found in image map table with image id " + rs.getString("imageid"));
                        String image_id = rs.getString("imageid");
                        
                        try {
                            
                            if (image_id != null) {
                                System.out.println("image id not null "+ image_id );
                                int img_id = Integer.parseInt(image_id);
                                System.out.println("image converted to integer");
                                PreparedStatement statement2 = Connection1.prepareStatement("SELECT image_id,prt_img from PRT_GEN_IMAGE where image_id=?");
                                statement2.setInt(1,img_id);

                                //statement.setInt(1, new Integer(imageId));
                                ResultSet rs2 = statement2.executeQuery();
                                if (rs2.next()) {
                                    System.out.println("record image found");
                                    Blob blob = rs2.getBlob("prt_img");
                                    BufferedInputStream in = new BufferedInputStream(blob.getBinaryStream());
                                    int b;
                                    byte[] buffer = new byte[10240];
                                    while ((b = in.read(buffer, 0, 10240)) != -1) {
                                        os.write(buffer, 0, b);
                                    }
                                    os.close();
                                    Connection1.close();
                                }
                                
                            }
                        }
                        catch(Exception e) {
                            System.out.println("internal query error");
                        }
                        
                        
                        Connection1.close();
                        //os.close();
                    }
                    else
                        System.out.println("No record found in image map table");
                    
                }
            }
        catch(Exception E)
        {
        System.out.println("Exception " + E);
        
        }
        
        
        
        

    }
    
   
}
