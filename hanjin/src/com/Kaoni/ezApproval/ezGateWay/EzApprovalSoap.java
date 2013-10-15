/**
 * EzApprovalSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.Kaoni.ezApproval.ezGateWay;

public interface EzApprovalSoap extends java.rmi.Remote {
    public java.lang.String helloWorld() throws java.rmi.RemoteException;
    public java.lang.String getContDocList3(java.lang.String contID, java.lang.String userID, java.lang.String pageSize, java.lang.String pageNum, java.lang.String pOrderCell, java.lang.String sortOption, java.lang.String companyID, java.lang.String strLang, java.lang.String pStartdate, java.lang.String pEnddate, java.lang.String selectType) throws java.rmi.RemoteException;
    public java.lang.String getAprDocList(java.lang.String pListType, java.lang.String userID, java.lang.String userDeptID, java.lang.String pageSize, java.lang.String pageNum, java.lang.String sortHeader, java.lang.String sortOption, java.lang.String companyID, java.lang.String pSubQuery, java.lang.String strLang) throws java.rmi.RemoteException;
    public java.lang.String getApproveDocInfo(java.lang.String pDocID, java.lang.String pCompanyID, java.lang.String strLang) throws java.rmi.RemoteException;
    public java.lang.String getLineInfo(java.lang.String strDocID, java.lang.String strMode, java.lang.String strCompanyID, java.lang.String strLang) throws java.rmi.RemoteException;
    public java.lang.String getWebPartList(java.lang.String pListType, java.lang.String userID, java.lang.String userDeptID, java.lang.String listCount, java.lang.String mode, java.lang.String userFlag, java.lang.String companyID, java.lang.String pSubQuery, java.lang.String strLang) throws java.rmi.RemoteException;
    public java.lang.String getDocInfo(java.lang.String pDocID, java.lang.String mode, java.lang.String selected, java.lang.String companyID, java.lang.String strLang) throws java.rmi.RemoteException;
    public java.lang.String getAttachInfo(java.lang.String pDocID, java.lang.String mode, java.lang.String sortHeader, java.lang.String sortOption, java.lang.String companyID, java.lang.String strLang) throws java.rmi.RemoteException;
    public java.lang.String getOpinionCount(java.lang.String pDocID, java.lang.String pUserID, java.lang.String pIngFlag, java.lang.String companyID, java.lang.String strLang) throws java.rmi.RemoteException;
    public java.lang.String getOpinionInfo(java.lang.String pDocID, java.lang.String mode, java.lang.String sortHeader, java.lang.String sortOption, java.lang.String companyID, java.lang.String strLang) throws java.rmi.RemoteException;
    public java.lang.String updateOpinionInfo(java.lang.String docID, java.lang.String userID, java.lang.String userName, java.lang.String title, java.lang.String deptID, java.lang.String deptName, java.lang.String writeDate, java.lang.String opinionGB, java.lang.String content, java.lang.String companyID, java.lang.String strLang) throws java.rmi.RemoteException;
    public java.lang.String deleteOpinion(java.lang.String pUserID, java.lang.String docID, java.lang.String pOpinionSN, java.lang.String companyID, java.lang.String strLang) throws java.rmi.RemoteException;
    public java.lang.String getListCntTotal(java.lang.String pListType, java.lang.String pUserID, java.lang.String strLang, java.lang.String pSubQuery, java.lang.String pContID) throws java.rmi.RemoteException;
    public java.lang.String doSendRefDoc(java.lang.String docid, java.lang.String companyID, java.lang.String strLang) throws java.rmi.RemoteException;
    public java.lang.String completeMail(java.lang.String docId, java.lang.String docTitle, java.lang.String userId, java.lang.String title, java.lang.String deptName, java.lang.String companyName, java.lang.String displayName, java.lang.String compID, java.lang.String email) throws java.rmi.RemoteException;
    public java.lang.String rejectMail(java.lang.String docId, java.lang.String docTitle, java.lang.String userId, java.lang.String title, java.lang.String deptName, java.lang.String companyName, java.lang.String displayName, java.lang.String compID, java.lang.String email) throws java.rmi.RemoteException;
    public java.lang.String refLineInsert(java.lang.String SA_RefLine, java.lang.String pDocID, java.lang.String pCompanyID) throws java.rmi.RemoteException;
    public java.lang.String upDateRefDate(java.lang.String pDocID, java.lang.String pUserID, java.lang.String pCompanyID) throws java.rmi.RemoteException;
    public byte[] bytetoFileApproval(java.lang.String pDocID, java.lang.String mode, java.lang.String companyID, java.lang.String strLang) throws java.rmi.RemoteException;
    public java.lang.String filetoApproval(java.lang.String pDocID, java.lang.String mode, java.lang.String companyID, java.lang.String strLang) throws java.rmi.RemoteException;
    public byte[] bytetoFileApprovalAttach(java.lang.String pDocID, java.lang.String mode, java.lang.String companyID, java.lang.String strLang, java.lang.String ATTACHTYPE, java.lang.String ATTACHSN) throws java.rmi.RemoteException;
    public java.lang.String filetoApprovalAttach(java.lang.String pDocID, java.lang.String mode, java.lang.String companyID, java.lang.String strLang, java.lang.String ATTACHTYPE, java.lang.String ATTACHSN) throws java.rmi.RemoteException;
    public java.lang.String getBodyHtml(java.lang.String strMhtPath) throws java.rmi.RemoteException;
    public java.lang.String mobileSrvConn(java.lang.String pUID, java.lang.String pResult, java.lang.String pFormID, java.lang.String pKeyval, java.lang.String pDocid, java.lang.String pOrgUID, java.lang.String strLang, java.lang.String pCompanyID) throws java.rmi.RemoteException;
    public java.lang.String getSearchDocList2(java.lang.String pUserID, java.lang.String pDocTitle, java.lang.String pPageSize, java.lang.String pPageNum, java.lang.String companyID, java.lang.String strLang) throws java.rmi.RemoteException;
    public java.lang.String test() throws java.rmi.RemoteException;
    public java.lang.String mhttest() throws java.rmi.RemoteException;
}
