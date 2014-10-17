package com.sfr.engage.util;


import com.sfr.engage.services.core.dao.factory.DAOFactory;
import com.sfr.util.AccessDataControl;
import com.sfr.util.constants.Constants;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.adf.share.logging.ADFLogger;


/**
 * TODO : ASHTHA - 02, May, 2014 :
 *  1. ADD Class level and complete method level JAVA DOC
 *  2. Override toString() method
 *  3. Class name first letter to be capitalized
 */
public class ImageServlet extends HttpServlet {

    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
    private AccessDataControl accessDC = new AccessDataControl();
    public static final ADFLogger LOGGER = AccessDataControl.getSFRLogger();

    /**
     */
    public ImageServlet() {
        super();
    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Request -----------------------> " + request.getParameter("id"));
        LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + "Request -----------------------> " + request.getParameter("categId"));
        String imageId = "";
        String categId = "";
        int imageId2 = 0;
        int categId2 = Constants.NINETYNINE;
        if (request.getParameter("id") != null) {
            imageId = request.getParameter("id").trim();
        }
        if (request.getParameter("categId") != null) {
            categId = request.getParameter("categId").trim();
        }

        if (imageId != null && !imageId.equals("")) {
            imageId2 = Integer.parseInt(imageId); // TODO : ASHTHA - 02, May, 2014 : Confusing names.imageID & imageIdAsString is better
        }

        if (categId != null && !categId.trim().equals("")) {
            categId2 = Integer.parseInt(categId); // TODO : ASHTHA - 02, May, 2014 : Confusing names.categID & categIdAsString is better
        }
        OutputStream os = response.getOutputStream();
        Connection connection1 = null; // TODO : ASHTHA - 02, May, 2014 : variable name with first letter lowercase
        Connection connection2 = null; // TODO : ASHTHA - 02, May, 2014 : why do we need 2 Connections? variable name with first letter lowercase.

        try {
            if (imageId2 > 0) {
                connection1 = DAOFactory.getJNDIConnection();
                PreparedStatement statement = connection1.prepareStatement("SELECT image_id,prt_img from PRT_GEN_IMAGE where image_id=?");
                statement.setInt(1, imageId2);


                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    Blob blob = rs.getBlob(Constants.PTRIMGLITERAL);
                    BufferedInputStream in = new BufferedInputStream(blob.getBinaryStream());
                    int b;
                    byte[] buffer = new byte[Constants.TENKB];
                    while ((b = in.read(buffer, 0, Constants.TENKB)) != -1) {
                        os.write(buffer, 0, b);
                    }
                    os.close();
                } else {
                    PreparedStatement statementDefault = connection1.prepareStatement("SELECT image_id,prt_img from PRT_GEN_IMAGE where  image_id=?");
                    if (categId2 != Constants.NINETYNINE) {
                        statementDefault.setInt(1, categId2);
                    } else {
                        statementDefault.setInt(1, Constants.DEFAULTIMGCATID99999);
                    }

                    ResultSet rsDefault = statementDefault.executeQuery();
                    if (rsDefault.next()) {
                        Blob blob = rsDefault.getBlob("prt_img");
                        BufferedInputStream in = new BufferedInputStream(blob.getBinaryStream());
                        int b;
                        byte[] buffer = new byte[Constants.TENKB];
                        while ((b = in.read(buffer, 0, Constants.TENKB)) != -1) {
                            os.write(buffer, 0, b);
                        }
                        os.close();
                    } else { // TODO : ASHTHA - 02, May, 2014 : why is this needed?
                        PreparedStatement statementDefault1 = connection1.prepareStatement("SELECT image_id,prt_img from PRT_GEN_IMAGE where image_id=?");
                        statementDefault1.setInt(1, Constants.DEFAULTIMGCATID99999);

                        ResultSet rsDefault1 = statementDefault1.executeQuery();
                        if (rsDefault1.next()) {
                            Blob blob = rsDefault1.getBlob("prt_img");
                            BufferedInputStream in = new BufferedInputStream(blob.getBinaryStream());
                            int b;
                            byte[] buffer = new byte[Constants.TENKB];
                            while ((b = in.read(buffer, 0, Constants.TENKB)) != -1) {
                                os.write(buffer, 0, b);
                            }
                            os.close();
                        }
                    }
                }
            } else {
                connection2 = DAOFactory.getJNDIConnection();
                PreparedStatement statementDefault2 = connection2.prepareStatement("SELECT image_id,prt_img from PRT_GEN_IMAGE where image_id=?");
                statementDefault2.setInt(1, Constants.DEFAULTIMGCATID88888);

                ResultSet rsDefault2 = statementDefault2.executeQuery();
                if (rsDefault2.next()) {
                    Blob blob = rsDefault2.getBlob("prt_img");
                    BufferedInputStream in = new BufferedInputStream(blob.getBinaryStream());
                    int b;
                    byte[] buffer = new byte[Constants.TENKB];
                    while ((b = in.read(buffer, 0, Constants.TENKB)) != -1) {
                        os.write(buffer, 0, b);
                    }
                    os.close();
                }
            }
        } catch (Exception e) {
            LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + ".doGet :EXCEPTION OCCURED. Cause:" + e.getCause() + ":Message" + e.getMessage());
            LOGGER.severe(e);
        } finally {
            try {
                if (connection1 != null) {
                    connection1.close();
                }
                if (connection2 != null) {
                    connection2.close();
                }
            } catch (SQLException e) {
                LOGGER.severe(e);
                LOGGER.info(accessDC.getDisplayRecord() + this.getClass() + ".doGet : :EXCEPTION OCCURED. Cause:" + e.getCause() + ":Message" +
                            e.getMessage());
            }
        }

    }

    public void setAccessDC(AccessDataControl accessDC) {
        this.accessDC = accessDC;
    }

    public AccessDataControl getAccessDC() {
        return accessDC;
    }
}
