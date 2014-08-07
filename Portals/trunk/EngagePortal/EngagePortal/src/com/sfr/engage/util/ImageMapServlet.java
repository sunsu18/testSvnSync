package com.sfr.engage.util;


import com.sfr.engage.services.core.dao.factory.DAOFactory;
import com.sfr.util.AccessDataControl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.adf.share.logging.ADFLogger;


/**
 * TODO : ASHTHA - 02, May, 2014 :
 *  1. ADD Class level and complete method level JAVA DOC
 *  2. Override toString() method

 */
public class ImageMapServlet extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;

    AccessDataControl accessDC = new AccessDataControl();

    public static final ADFLogger log = AccessDataControl.getSFRLogger();

    /**
     */
    public ImageMapServlet() {
        super();
    }

    /**
     * @param config
     * @throws ServletException
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        log.info(accessDC.getDisplayRecord()+ this.getClass() + " Inside doGET method of ImageServlet");
        OutputStream os = response.getOutputStream();
        Connection connection1 = null;
        response.setContentType(CONTENT_TYPE);

        log.info(accessDC.getDisplayRecord()+ this.getClass() + "Param1 = " + request.getParameter("param1") + "Param2 = " + request.getParameter("param2") + "Param3 = " + request.getParameter("param3") + "Param4 = " + request.getParameter("param4") + "Param5 = " + request.getParameter("param5"));
        String featureName = request.getParameter("param1");
        String featureValue = request.getParameter("param2");
        String controlAttr = request.getParameter("param3");
        String attrValue = request.getParameter("param4");
        String controlAttrValue;
        if (featureName.equalsIgnoreCase("LISTPRICE")) { 
            controlAttrValue = "41S5" + attrValue;
            
        } else {
            controlAttrValue = null;
        }
        
        //TODO : Hiten - 09, May, 2014 : This is kept for future use.
        String lang = request.getParameter("param5"); 
        

        try {
            if (featureName != null && featureValue != null && controlAttrValue != null) {
                connection1 = DAOFactory.getJNDIConnection();
                PreparedStatement statement =
                    connection1.prepareStatement("SELECT imageid from PRT_GEN_IMAGE_MAP where feature_name=? and feature_value=? and control_attr_value=?");
                statement.setString(1, featureName);
                statement.setString(2, featureValue);
              
                statement.setString(3, controlAttrValue);
              
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {


                    String image_id = rs.getString("imageid");

                    try {

                        if (image_id != null) {
                            log.info(accessDC.getDisplayRecord()+ this.getClass() + " Record found in image map table with image id " + image_id);

                            int img_id = Integer.parseInt(image_id);

                            PreparedStatement statement2 = connection1.prepareStatement("SELECT image_id,prt_img from PRT_GEN_IMAGE where image_id=?");
                            statement2.setInt(1, img_id);

                           
                            ResultSet rs2 = statement2.executeQuery();
                            if (rs2.next()) {
                                log.info(accessDC.getDisplayRecord()+ this.getClass() + " Record image found for " + img_id);

                                Blob blob = rs2.getBlob("prt_img");
                                BufferedInputStream in = new BufferedInputStream(blob.getBinaryStream());
                                int b;
                                byte[] buffer = new byte[10240];
                                while ((b = in.read(buffer, 0, 10240)) != -1) {
                                    os.write(buffer, 0, b);
                                }

                            }

                        }
                        else
                        {
                            image_id = "102";
                            //if imageId is null, then to fetch default imageId?
                            int img_id = Integer.parseInt(image_id);

                            PreparedStatement statement2 = connection1.prepareStatement("SELECT image_id,prt_img from PRT_GEN_IMAGE where image_id=?");
                            statement2.setInt(1, img_id);

                            
                            ResultSet rs2 = statement2.executeQuery();
                            if (rs2.next()) {
                                log.info(accessDC.getDisplayRecord()+ this.getClass() + " Record image found for " + img_id);

                                Blob blob = rs2.getBlob("prt_img");
                                BufferedInputStream in = new BufferedInputStream(blob.getBinaryStream());
                                int b;
                                byte[] buffer = new byte[10240];
                                while ((b = in.read(buffer, 0, 10240)) != -1) {
                                    os.write(buffer, 0, b);
                                }

                            }

                        }
                    } catch (Exception e) {
                        log.severe(accessDC.getDisplayRecord()+ this.getClass() + " Error : Internal Query Error for PRT_GEN_IMAGE table " + e.getMessage());

                    }
                    finally {
                        os.close();
                        connection1.close();

                    }



                } else
                {
                    log.severe(accessDC.getDisplayRecord()+ this.getClass() + " Error : No record found in image map table");

                }

            }
        } catch (Exception e) {
            log.severe(accessDC.getDisplayRecord()+ this.getClass() + "External Query Error for PRT_GEN_IMAGE_MAP table " + e.getMessage());

        }


        log.fine(accessDC.getDisplayRecord()+ this.getClass() + "Exiting from doGET method of ImageServlet");
    }


}
