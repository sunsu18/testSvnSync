package com.sfr.engage.utility.util;

import java.io.Serializable;

import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;

import oracle.binding.BindingContainer;

import oracle.jbo.ViewObject;

public class ADFUtils implements Serializable{

    @SuppressWarnings("compatibility")
    private static final long serialVersionUID = 1L;
    
    
    public ADFUtils() {
        super();
    }
   
    /**
     * Return the current page's binding container.
     * @return the current page's binding container
     */
    public static BindingContainer getBindingContainer() {
        return (BindingContainer)JSFUtils.resolveExpression("#{bindings}");
    }


    /**
     * This is method returns viewobject for the passed iterator.
     * @param iteratorName
     * @return
     */
    public static ViewObject getViewObject(String iteratorName){      
        DCBindingContainer bindings =(DCBindingContainer)getBindingContainer();
        DCIteratorBinding iter =
            bindings.findIteratorBinding(iteratorName);
        return iter.getViewObject();              
    }
}
