package com.sfr.engage.services.core.dao.factory;

import java.io.Serializable;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

public class DAOFactory implements Serializable{

    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    
    public DAOFactory() {
        super();
    }

    /**
     * This method is used to get JNDI connection for processing business logic
     * @return
     * @throws SQLException
     * @throws NamingException
     */
    public static Connection getJNDIConnection() throws SQLException, NamingException {
        // initially no connection
        Connection connection = null;
        InitialContext objinitialContext = null;
        DataSource datasource = null;

        //              The WLInitialContextFactory creates initial contexts for accessing
        //              the WebLogic naming service. It can also be used to
        //              create a multitier connection to another naming service
        //              through a WebLogic Server.
        //
        //               use hashtable to map specific keys into table

        try {
            Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");

            // env.put(Context.PROVIDER_URL, "t3://10.101.53.66:8001");
            // Below property is required only if you are running as stand-alone app
            // env.put(Context.PROVIDER_URL, getPropertyValue("JNDI_URI"));


            //to obtain the initial context that contains
            //the resource (a Java object).
            objinitialContext = new InitialContext(env);

            // use datasource for connection to physical data source.
            // Give JNDI name under lookup.
            datasource = (DataSource)objinitialContext.lookup("jdbc/wcpcustom");

            if (datasource != null) {
                connection = datasource.getConnection();
                System.out.println("datasource found");
            } else
                System.out.println("No data source");

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            throw sqle;
        } catch (NamingException ne) {
            ne.printStackTrace();
            throw ne;
        } finally {
            try {

                if (objinitialContext != null) {
                    objinitialContext.close();
                }
            } catch (NamingException ne) {
                ne.printStackTrace();
                throw ne;
            }
        }
        return connection;
    }
}
