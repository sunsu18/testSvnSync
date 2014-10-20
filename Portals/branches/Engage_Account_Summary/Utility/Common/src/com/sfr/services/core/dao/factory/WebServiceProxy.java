package com.sfr.services.core.dao.factory;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceFeature;

public class WebServiceProxy extends Service {
    private String targetNamespace;

    /**
     * @param wsdlUrl
     * @param serviceName
     * @param targetNamespace
     * @throws MalformedURLException
     */
    public WebServiceProxy(String wsdlUrl, String serviceName,
                           String targetNamespace) throws MalformedURLException {
        super(new URL(wsdlUrl), new QName(targetNamespace, serviceName));
        this.targetNamespace = targetNamespace;
    }

    public <T> T getServicePort(String portName, Class<T> clazz) {
        return super.getPort(new QName(this.targetNamespace, portName), clazz);
    }

    public <T> T getServicePortWithFeatures(String portName, Class<T> clazz,
                                            WebServiceFeature... features) {
        return super.getPort(new QName(this.targetNamespace, portName), clazz,
                             features);
    }

}
