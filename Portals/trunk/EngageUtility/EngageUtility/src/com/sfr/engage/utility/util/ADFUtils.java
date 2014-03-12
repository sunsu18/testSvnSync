package com.sfr.engage.utility.util;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.binding.BindingContainer;

import oracle.jbo.ViewObject;

public class ADFUtils {
   
    /**
     * Return the current page's binding container.
     * @return the current page's binding container
     */
    public static BindingContainer getBindingContainer() {
        return (BindingContainer)JSFUtils.resolveExpression("#{bindings}");
    }
    
    
    public static ViewObject getViewObject(String iteratorName){      
        DCBindingContainer bindings =(DCBindingContainer)getBindingContainer();
        DCIteratorBinding iter =
            bindings.findIteratorBinding(iteratorName);
        ViewObject vo=iter.getViewObject();
        return vo;      
    }
}
