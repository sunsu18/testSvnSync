package com.sfr.engage.model.exception;

import oracle.adf.model.binding.DCErrorHandlerImpl;

public class CustomErrorHandler extends DCErrorHandlerImpl {

    public CustomErrorHandler() {
        super(true);
    }

}
