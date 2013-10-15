/**
 * EzApprovalLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.Kaoni.ezApproval.ezGateWay;

public class EzApprovalLocator extends org.apache.axis.client.Service implements com.Kaoni.ezApproval.ezGateWay.EzApproval {

    public EzApprovalLocator() {
    }


    public EzApprovalLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public EzApprovalLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ezApprovalSoap
    private java.lang.String ezApprovalSoap_address = "http://gw.hanjin.com/ezGateWayM/ezApproval.asmx";

    public java.lang.String getezApprovalSoapAddress() {
        return ezApprovalSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ezApprovalSoapWSDDServiceName = "ezApprovalSoap";

    public java.lang.String getezApprovalSoapWSDDServiceName() {
        return ezApprovalSoapWSDDServiceName;
    }

    public void setezApprovalSoapWSDDServiceName(java.lang.String name) {
        ezApprovalSoapWSDDServiceName = name;
    }

    public com.Kaoni.ezApproval.ezGateWay.EzApprovalSoap getezApprovalSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ezApprovalSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getezApprovalSoap(endpoint);
    }

    public com.Kaoni.ezApproval.ezGateWay.EzApprovalSoap getezApprovalSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.Kaoni.ezApproval.ezGateWay.EzApprovalSoapStub _stub = new com.Kaoni.ezApproval.ezGateWay.EzApprovalSoapStub(portAddress, this);
            _stub.setPortName(getezApprovalSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setezApprovalSoapEndpointAddress(java.lang.String address) {
        ezApprovalSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.Kaoni.ezApproval.ezGateWay.EzApprovalSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.Kaoni.ezApproval.ezGateWay.EzApprovalSoapStub _stub = new com.Kaoni.ezApproval.ezGateWay.EzApprovalSoapStub(new java.net.URL(ezApprovalSoap_address), this);
                _stub.setPortName(getezApprovalSoapWSDDServiceName());
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
        if ("ezApprovalSoap".equals(inputPortName)) {
            return getezApprovalSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ezGateWay.ezApproval.Kaoni.com/", "ezApproval");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ezGateWay.ezApproval.Kaoni.com/", "ezApprovalSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ezApprovalSoap".equals(portName)) {
            setezApprovalSoapEndpointAddress(address);
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
