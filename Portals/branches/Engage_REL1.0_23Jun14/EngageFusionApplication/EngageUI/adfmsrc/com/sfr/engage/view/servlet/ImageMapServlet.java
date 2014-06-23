package com.sfr.engage.view.servlet;

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
            Control_Attr_Value = "41S2" + Attr_Value;
            System.out.println("Control_Attr_Value" + Control_Attr_Value);
        }
        else{
            Control_Attr_Value = null;
        }
        
        String lang = request.getParameter("param5");
        
        try {
                if (Feature_Name != null && Feature_Value!= null && Control_Attr_Value != null) 
                {
                  
                    
                }
            }
        catch(Exception E)
        {
        System.out.println("Exception " + E);
        
        }
        
        
        
        

    }
    
   
}
