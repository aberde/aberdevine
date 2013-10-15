package com.Kaoni.ezBoardSTD.ezGateWay;

public class EzBoardSTDSoapProxy implements com.Kaoni.ezBoardSTD.ezGateWay.EzBoardSTDSoap {
  private String _endpoint = null;
  private com.Kaoni.ezBoardSTD.ezGateWay.EzBoardSTDSoap ezBoardSTDSoap = null;
  
  public EzBoardSTDSoapProxy() {
    _initEzBoardSTDSoapProxy();
  }
  
  public EzBoardSTDSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initEzBoardSTDSoapProxy();
  }
  
  private void _initEzBoardSTDSoapProxy() {
    try {
      ezBoardSTDSoap = (new com.Kaoni.ezBoardSTD.ezGateWay.EzBoardSTDLocator()).getezBoardSTDSoap();
      if (ezBoardSTDSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)ezBoardSTDSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)ezBoardSTDSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (ezBoardSTDSoap != null)
      ((javax.xml.rpc.Stub)ezBoardSTDSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.Kaoni.ezBoardSTD.ezGateWay.EzBoardSTDSoap getEzBoardSTDSoap() {
    if (ezBoardSTDSoap == null)
      _initEzBoardSTDSoapProxy();
    return ezBoardSTDSoap;
  }
  
  public java.lang.String getBoardListItemXML(java.lang.String pUserID, java.lang.String pBoardID, int pStartRow, int pEndRow, java.lang.String pSortBy) throws java.rmi.RemoteException{
    if (ezBoardSTDSoap == null)
      _initEzBoardSTDSoapProxy();
    return ezBoardSTDSoap.getBoardListItemXML(pUserID, pBoardID, pStartRow, pEndRow, pSortBy);
  }
  
  public java.lang.String setAsRead(java.lang.String pUserID, java.lang.String pUserName, java.lang.String pUserDeptName, java.lang.String pUserCompanyName, java.lang.String pUserTitle, java.lang.String pBoardID, java.lang.String pItemID) throws java.rmi.RemoteException{
    if (ezBoardSTDSoap == null)
      _initEzBoardSTDSoapProxy();
    return ezBoardSTDSoap.setAsRead(pUserID, pUserName, pUserDeptName, pUserCompanyName, pUserTitle, pBoardID, pItemID);
  }
  
  public java.lang.String getItemAttachmentXML(java.lang.String pItemID, java.lang.String pUserID) throws java.rmi.RemoteException{
    if (ezBoardSTDSoap == null)
      _initEzBoardSTDSoapProxy();
    return ezBoardSTDSoap.getItemAttachmentXML(pItemID, pUserID);
  }
  
  public java.lang.String getNewItemListXML(java.lang.String pUserID, int pStartRow, int pEndRow, java.lang.String pSortBy, java.lang.String pReadFlag) throws java.rmi.RemoteException{
    if (ezBoardSTDSoap == null)
      _initEzBoardSTDSoapProxy();
    return ezBoardSTDSoap.getNewItemListXML(pUserID, pStartRow, pEndRow, pSortBy, pReadFlag);
  }
  
  public java.lang.String getNewItemListCount(java.lang.String pUserID, java.lang.String pReadFlag) throws java.rmi.RemoteException{
    if (ezBoardSTDSoap == null)
      _initEzBoardSTDSoapProxy();
    return ezBoardSTDSoap.getNewItemListCount(pUserID, pReadFlag);
  }
  
  public java.lang.String getBoardTotalItemCount(java.lang.String pBoardID, java.lang.String pUserID) throws java.rmi.RemoteException{
    if (ezBoardSTDSoap == null)
      _initEzBoardSTDSoapProxy();
    return ezBoardSTDSoap.getBoardTotalItemCount(pBoardID, pUserID);
  }
  
  public java.lang.String searchItemXML(java.lang.String pUserID, java.lang.String pDeptID, java.lang.String pBoardID, java.lang.String pTitle, java.lang.String pWriterName, java.lang.String pPIC, java.lang.String pAbstract, java.lang.String pStartDate, java.lang.String pEndDate, long pStartRow, long pEndRow, java.lang.String pSearchSub) throws java.rmi.RemoteException{
    if (ezBoardSTDSoap == null)
      _initEzBoardSTDSoapProxy();
    return ezBoardSTDSoap.searchItemXML(pUserID, pDeptID, pBoardID, pTitle, pWriterName, pPIC, pAbstract, pStartDate, pEndDate, pStartRow, pEndRow, pSearchSub);
  }
  
  public java.lang.String searchItemCount(java.lang.String pUserID, java.lang.String pDeptID, java.lang.String pBoardID, java.lang.String pTitle, java.lang.String pWriterName, java.lang.String pPIC, java.lang.String pAbstract, java.lang.String pStartDate, java.lang.String pEndDate, java.lang.String pSearchSub) throws java.rmi.RemoteException{
    if (ezBoardSTDSoap == null)
      _initEzBoardSTDSoapProxy();
    return ezBoardSTDSoap.searchItemCount(pUserID, pDeptID, pBoardID, pTitle, pWriterName, pPIC, pAbstract, pStartDate, pEndDate, pSearchSub);
  }
  
  public java.lang.String getItemXML(java.lang.String pBoardID, java.lang.String pItemID) throws java.rmi.RemoteException{
    if (ezBoardSTDSoap == null)
      _initEzBoardSTDSoapProxy();
    return ezBoardSTDSoap.getItemXML(pBoardID, pItemID);
  }
  
  public byte[] bytetoFileBoardMht(java.lang.String pBoardID, java.lang.String pItemID) throws java.rmi.RemoteException{
    if (ezBoardSTDSoap == null)
      _initEzBoardSTDSoapProxy();
    return ezBoardSTDSoap.bytetoFileBoardMht(pBoardID, pItemID);
  }
  
  public java.lang.String filetoBoard(java.lang.String pBoardID, java.lang.String pItemID) throws java.rmi.RemoteException{
    if (ezBoardSTDSoap == null)
      _initEzBoardSTDSoapProxy();
    return ezBoardSTDSoap.filetoBoard(pBoardID, pItemID);
  }
  
  public byte[] bytetoFileBoardAttach(java.lang.String pUserID, java.lang.String pBoardID, java.lang.String pItemID, java.lang.String objectID) throws java.rmi.RemoteException{
    if (ezBoardSTDSoap == null)
      _initEzBoardSTDSoapProxy();
    return ezBoardSTDSoap.bytetoFileBoardAttach(pUserID, pBoardID, pItemID, objectID);
  }
  
  public java.lang.String filetoBoardAttach(java.lang.String pUserID, java.lang.String pBoardID, java.lang.String pItemID, java.lang.String objectID) throws java.rmi.RemoteException{
    if (ezBoardSTDSoap == null)
      _initEzBoardSTDSoapProxy();
    return ezBoardSTDSoap.filetoBoardAttach(pUserID, pBoardID, pItemID, objectID);
  }
  
  public java.lang.String getUnreadItemsCount(java.lang.String pUserID, java.lang.String pBoardID) throws java.rmi.RemoteException{
    if (ezBoardSTDSoap == null)
      _initEzBoardSTDSoapProxy();
    return ezBoardSTDSoap.getUnreadItemsCount(pUserID, pBoardID);
  }
  
  public java.lang.String getAdjacentItems(java.lang.String pUserID, java.lang.String pItemID, java.lang.String pBoardID) throws java.rmi.RemoteException{
    if (ezBoardSTDSoap == null)
      _initEzBoardSTDSoapProxy();
    return ezBoardSTDSoap.getAdjacentItems(pUserID, pItemID, pBoardID);
  }
  
  
}