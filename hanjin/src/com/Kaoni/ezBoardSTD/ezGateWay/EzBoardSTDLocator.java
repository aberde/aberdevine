/**
 * EzBoardSTDLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.Kaoni.ezBoardSTD.ezGateWay;

public class EzBoardSTDLocator extends org.apache.axis.client.Service implements com.Kaoni.ezBoardSTD.ezGateWay.EzBoardSTD {

    public EzBoardSTDLocator() {
    }


    public EzBoardSTDLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EzBoardSTDLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ezBoardSTDSoap
    private java.lang.String ezBoardSTDSoap_address = "http://gw.hanjin.com/ezGateWayM/ezBoardSTD.asmx";

    public java.lang.String getezBoardSTDSoapAddress() {
        return ezBoardSTDSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ezBoardSTDSoapWSDDServiceName = "ezBoardSTDSoap";

    public java.lang.String getezBoardSTDSoapWSDDServiceName() {
        return ezBoardSTDSoapWSDDServiceName;
    }

    public void setezBoardSTDSoapWSDDServiceName(java.lang.String name) {
        ezBoardSTDSoapWSDDServiceName = name;
    }

    public com.Kaoni.ezBoardSTD.ezGateWay.EzBoardSTDSoap getezBoardSTDSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ezBoardSTDSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getezBoardSTDSoap(endpoint);
    }

    public com.Kaoni.ezBoardSTD.ezGateWay.EzBoardSTDSoap getezBoardSTDSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.Kaoni.ezBoardSTD.ezGateWay.EzBoardSTDSoapStub _stub = new com.Kaoni.ezBoardSTD.ezGateWay.EzBoardSTDSoapStub(portAddress, this);
            _stub.setPortName(getezBoardSTDSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setezBoardSTDSoapEndpointAddress(java.lang.String address) {
        ezBoardSTDSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.Kaoni.ezBoardSTD.ezGateWay.EzBoardSTDSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.Kaoni.ezBoardSTD.ezGateWay.EzBoardSTDSoapStub _stub = new com.Kaoni.ezBoardSTD.ezGateWay.EzBoardSTDSoapStub(new java.net.URL(ezBoardSTDSoap_address), this);
                _stub.setPortName(getezBoardSTDSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ezBoardSTDSoap".equals(inputPortName)) {
            return getezBoardSTDSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ezGateWay.ezBoardSTD.Kaoni.com/", "ezBoardSTD");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ezGateWay.ezBoardSTD.Kaoni.com/", "ezBoardSTDSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ezBoardSTDSoap".equals(portName)) {
            setezBoardSTDSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
