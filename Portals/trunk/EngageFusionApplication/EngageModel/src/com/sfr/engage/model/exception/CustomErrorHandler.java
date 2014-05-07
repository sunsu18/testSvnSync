package com.sfr.engage.model.exception;

import java.sql.SQLException;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCErrorHandlerImpl;

import oracle.jbo.DMLConstraintException;

public class CustomErrorHandler extends DCErrorHandlerImpl {
//    public CustomErrorHandler(boolean b) {
//        super(b);
//    }
    public CustomErrorHandler() {
        super(true);
    }
    
    @Override
    public String getDisplayMessage(BindingContext ctx, Exception ex) {


        String error_msg;
        error_msg = "";

        if (ex instanceof oracle.jbo.ValidationException) {

            String msg = ex.getMessage();
            int i = msg.indexOf("JBO25013"); //When JBO25013 Too many object match primary key exception occur.
            if (i > 0) {
                error_msg = "Duplicate Employee Id Found.";

            }
            error_msg = getDisplayMessage(ctx, ex);
        } else
            //error_msg = getDisplayMessage(ctx, ex);
        { System.out.println("Exception ----->");
            ex.printStackTrace();}
        return error_msg;
    }

    //Called by the framework to check if an exception should be ignored

//     @Override
//    public Boolean skipException(Exception ex) {
//        if (ex instanceof DMLConstraintException) {
//            System.out.println("DML Exception");
//            return false;
//            //don�t show details of SQL exception
//        } else if (ex instanceof SQLException) {
//        System.out.println("SQL Exception");
//            return true;
//        } else {
//                System.out.println("Some unknown exception");
//            //return super.skipException(ex);
//            return false;
//
//        }
//
//    }
    
}
