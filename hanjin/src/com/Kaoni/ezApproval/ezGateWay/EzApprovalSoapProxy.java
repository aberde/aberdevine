package com.Kaoni.ezApproval.ezGateWay;

public class EzApprovalSoapProxy implements com.Kaoni.ezApproval.ezGateWay.EzApprovalSoap {
  private String _endpoint = null;
  private com.Kaoni.ezApproval.ezGateWay.EzApprovalSoap ezApprovalSoap = null;
  
  public EzApprovalSoapProxy() {
    _initEzApprovalSoapProxy();
  }
  
  public EzApprovalSoapProxy(String endpoint) {
    _endpoint = endpoint;
    _initEzApprovalSoapProxy();
  }
  
  private void _initEzApprovalSoapProxy() {
    try {
      ezApprovalSoap = (new com.Kaoni.ezApproval.ezGateWay.EzApprovalLocator()).getezApprovalSoap();
      if (ezApprovalSoap != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)ezApprovalSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)ezApprovalSoap)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (ezApprovalSoap != null)
      ((javax.xml.rpc.Stub)ezApprovalSoap)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.Kaoni.ezApproval.ezGateWay.EzApprovalSoap getEzApprovalSoap() {
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap;
  }
  
  public java.lang.String helloWorld() throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.helloWorld();
  }
  
  public java.lang.String getContDocList3(java.lang.String contID, java.lang.String userID, java.lang.String pageSize, java.lang.String pageNum, java.lang.String pOrderCell, java.lang.String sortOption, java.lang.String companyID, java.lang.String strLang, java.lang.String pStartdate, java.lang.String pEnddate, java.lang.String selectType) throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.getContDocList3(contID, userID, pageSize, pageNum, pOrderCell, sortOption, companyID, strLang, pStartdate, pEnddate, selectType);
  }
  
  public java.lang.String getAprDocList(java.lang.String pListType, java.lang.String userID, java.lang.String userDeptID, java.lang.String pageSize, java.lang.String pageNum, java.lang.String sortHeader, java.lang.String sortOption, java.lang.String companyID, java.lang.String pSubQuery, java.lang.String strLang) throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.getAprDocList(pListType, userID, userDeptID, pageSize, pageNum, sortHeader, sortOption, companyID, pSubQuery, strLang);
  }
  
  public java.lang.String getApproveDocInfo(java.lang.String pDocID, java.lang.String pCompanyID, java.lang.String strLang) throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.getApproveDocInfo(pDocID, pCompanyID, strLang);
  }
  
  public java.lang.String getLineInfo(java.lang.String strDocID, java.lang.String strMode, java.lang.String strCompanyID, java.lang.String strLang) throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.getLineInfo(strDocID, strMode, strCompanyID, strLang);
  }
  
  public java.lang.String getWebPartList(java.lang.String pListType, java.lang.String userID, java.lang.String userDeptID, java.lang.String listCount, java.lang.String mode, java.lang.String userFlag, java.lang.String companyID, java.lang.String pSubQuery, java.lang.String strLang) throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.getWebPartList(pListType, userID, userDeptID, listCount, mode, userFlag, companyID, pSubQuery, strLang);
  }
  
  public java.lang.String getDocInfo(java.lang.String pDocID, java.lang.String mode, java.lang.String selected, java.lang.String companyID, java.lang.String strLang) throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.getDocInfo(pDocID, mode, selected, companyID, strLang);
  }
  
  public java.lang.String getAttachInfo(java.lang.String pDocID, java.lang.String mode, java.lang.String sortHeader, java.lang.String sortOption, java.lang.String companyID, java.lang.String strLang) throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.getAttachInfo(pDocID, mode, sortHeader, sortOption, companyID, strLang);
  }
  
  public java.lang.String getOpinionCount(java.lang.String pDocID, java.lang.String pUserID, java.lang.String pIngFlag, java.lang.String companyID, java.lang.String strLang) throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.getOpinionCount(pDocID, pUserID, pIngFlag, companyID, strLang);
  }
  
  public java.lang.String getOpinionInfo(java.lang.String pDocID, java.lang.String mode, java.lang.String sortHeader, java.lang.String sortOption, java.lang.String companyID, java.lang.String strLang) throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.getOpinionInfo(pDocID, mode, sortHeader, sortOption, companyID, strLang);
  }
  
  public java.lang.String updateOpinionInfo(java.lang.String docID, java.lang.String userID, java.lang.String userName, java.lang.String title, java.lang.String deptID, java.lang.String deptName, java.lang.String writeDate, java.lang.String opinionGB, java.lang.String content, java.lang.String companyID, java.lang.String strLang) throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.updateOpinionInfo(docID, userID, userName, title, deptID, deptName, writeDate, opinionGB, content, companyID, strLang);
  }
  
  public java.lang.String deleteOpinion(java.lang.String pUserID, java.lang.String docID, java.lang.String pOpinionSN, java.lang.String companyID, java.lang.String strLang) throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.deleteOpinion(pUserID, docID, pOpinionSN, companyID, strLang);
  }
  
  public java.lang.String getListCntTotal(java.lang.String pListType, java.lang.String pUserID, java.lang.String strLang, java.lang.String pSubQuery, java.lang.String pContID) throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.getListCntTotal(pListType, pUserID, strLang, pSubQuery, pContID);
  }
  
  public java.lang.String doSendRefDoc(java.lang.String docid, java.lang.String companyID, java.lang.String strLang) throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.doSendRefDoc(docid, companyID, strLang);
  }
  
  public java.lang.String completeMail(java.lang.String docId, java.lang.String docTitle, java.lang.String userId, java.lang.String title, java.lang.String deptName, java.lang.String companyName, java.lang.String displayName, java.lang.String compID, java.lang.String email) throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.completeMail(docId, docTitle, userId, title, deptName, companyName, displayName, compID, email);
  }
  
  public java.lang.String rejectMail(java.lang.String docId, java.lang.String docTitle, java.lang.String userId, java.lang.String title, java.lang.String deptName, java.lang.String companyName, java.lang.String displayName, java.lang.String compID, java.lang.String email) throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.rejectMail(docId, docTitle, userId, title, deptName, companyName, displayName, compID, email);
  }
  
  public java.lang.String refLineInsert(java.lang.String SA_RefLine, java.lang.String pDocID, java.lang.String pCompanyID) throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.refLineInsert(SA_RefLine, pDocID, pCompanyID);
  }
  
  public java.lang.String upDateRefDate(java.lang.String pDocID, java.lang.String pUserID, java.lang.String pCompanyID) throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.upDateRefDate(pDocID, pUserID, pCompanyID);
  }
  
  public byte[] bytetoFileApproval(java.lang.String pDocID, java.lang.String mode, java.lang.String companyID, java.lang.String strLang) throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.bytetoFileApproval(pDocID, mode, companyID, strLang);
  }
  
  public java.lang.String filetoApproval(java.lang.String pDocID, java.lang.String mode, java.lang.String companyID, java.lang.String strLang) throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.filetoApproval(pDocID, mode, companyID, strLang);
  }
  
  public byte[] bytetoFileApprovalAttach(java.lang.String pDocID, java.lang.String mode, java.lang.String companyID, java.lang.String strLang, java.lang.String ATTACHTYPE, java.lang.String ATTACHSN) throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.bytetoFileApprovalAttach(pDocID, mode, companyID, strLang, ATTACHTYPE, ATTACHSN);
  }
  
  public java.lang.String filetoApprovalAttach(java.lang.String pDocID, java.lang.String mode, java.lang.String companyID, java.lang.String strLang, java.lang.String ATTACHTYPE, java.lang.String ATTACHSN) throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.filetoApprovalAttach(pDocID, mode, companyID, strLang, ATTACHTYPE, ATTACHSN);
  }
  
  public java.lang.String getBodyHtml(java.lang.String strMhtPath) throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.getBodyHtml(strMhtPath);
  }
  
  public java.lang.String mobileSrvConn(java.lang.String pUID, java.lang.String pResult, java.lang.String pFormID, java.lang.String pKeyval, java.lang.String pDocid, java.lang.String pOrgUID, java.lang.String strLang, java.lang.String pCompanyID) throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.mobileSrvConn(pUID, pResult, pFormID, pKeyval, pDocid, pOrgUID, strLang, pCompanyID);
  }
  
  public java.lang.String getSearchDocList2(java.lang.String pUserID, java.lang.String pDocTitle, java.lang.String pPageSize, java.lang.String pPageNum, java.lang.String companyID, java.lang.String strLang) throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.getSearchDocList2(pUserID, pDocTitle, pPageSize, pPageNum, companyID, strLang);
  }
  
  public java.lang.String test() throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.test();
  }
  
  public java.lang.String mhttest() throws java.rmi.RemoteException{
    if (ezApprovalSoap == null)
      _initEzApprovalSoapProxy();
    return ezApprovalSoap.mhttest();
  }
  
  
}