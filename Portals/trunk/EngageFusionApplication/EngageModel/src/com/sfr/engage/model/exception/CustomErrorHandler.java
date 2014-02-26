package com.sfr.engage.model.exception;

import java.sql.SQLException;

import oracle.adf.model.binding.DCErrorHandlerImpl;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;

import oracle.jbo.DMLConstraintException;

public class CustomErrorHandler extends DCErrorHandlerImpl {
    public CustomErrorHandler(boolean b) {
        super(b);
    }

    //Override to change or suppress (null value) the displayed message

    @Override
    public String getDisplayMessage(BindingContext ctx, Exception ex) {


        String error_msg;

        if (ex instanceof oracle.jbo.ValidationException) {

            String msg = ex.getMessage();
            int i = msg.indexOf("JBO25013"); //When JBO25013 Too many object match primary key exception occur.
            if (i > 0) {
                error_msg = "Duplicate Employee Id Found.";

            }
            error_msg = getDisplayMessage(ctx, ex);
        } else
            error_msg = getDisplayMessage(ctx, ex);
        return error_msg;
    }

    //Called by the framework to check if an exception should be ignored

    /* @Override
    public Boolean skipException(Exception ex) {
        if (ex instanceof DMLConstraintException) {
            return false;
            //don’t show details of SQL exception
        } else if (ex instanceof SQLException) {
            return true;
        } else {

            return super.skipException(ex);

        }

    }
*/

}
