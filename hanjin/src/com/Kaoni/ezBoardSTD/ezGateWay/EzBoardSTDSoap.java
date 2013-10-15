/**
 * EzBoardSTDSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.Kaoni.ezBoardSTD.ezGateWay;

public interface EzBoardSTDSoap extends java.rmi.Remote {
    public java.lang.String getBoardListItemXML(java.lang.String pUserID, java.lang.String pBoardID, int pStartRow, int pEndRow, java.lang.String pSortBy) throws java.rmi.RemoteException;
    public java.lang.String setAsRead(java.lang.String pUserID, java.lang.String pUserName, java.lang.String pUserDeptName, java.lang.String pUserCompanyName, java.lang.String pUserTitle, java.lang.String pBoardID, java.lang.String pItemID) throws java.rmi.RemoteException;
    public java.lang.String getItemAttachmentXML(java.lang.String pItemID, java.lang.String pUserID) throws java.rmi.RemoteException;
    public java.lang.String getNewItemListXML(java.lang.String pUserID, int pStartRow, int pEndRow, java.lang.String pSortBy, java.lang.String pReadFlag) throws java.rmi.RemoteException;
    public java.lang.String getNewItemListCount(java.lang.String pUserID, java.lang.String pReadFlag) throws java.rmi.RemoteException;
    public java.lang.String getBoardTotalItemCount(java.lang.String pBoardID, java.lang.String pUserID) throws java.rmi.RemoteException;
    public java.lang.String searchItemXML(java.lang.String pUserID, java.lang.String pDeptID, java.lang.String pBoardID, java.lang.String pTitle, java.lang.String pWriterName, java.lang.String pPIC, java.lang.String pAbstract, java.lang.String pStartDate, java.lang.String pEndDate, long pStartRow, long pEndRow, java.lang.String pSearchSub) throws java.rmi.RemoteException;
    public java.lang.String searchItemCount(java.lang.String pUserID, java.lang.String pDeptID, java.lang.String pBoardID, java.lang.String pTitle, java.lang.String pWriterName, java.lang.String pPIC, java.lang.String pAbstract, java.lang.String pStartDate, java.lang.String pEndDate, java.lang.String pSearchSub) throws java.rmi.RemoteException;
    public java.lang.String getItemXML(java.lang.String pBoardID, java.lang.String pItemID) throws java.rmi.RemoteException;
    public byte[] bytetoFileBoardMht(java.lang.String pBoardID, java.lang.String pItemID) throws java.rmi.RemoteException;
    public java.lang.String filetoBoard(java.lang.String pBoardID, java.lang.String pItemID) throws java.rmi.RemoteException;
    public byte[] bytetoFileBoardAttach(java.lang.String pUserID, java.lang.String pBoardID, java.lang.String pItemID, java.lang.String objectID) throws java.rmi.RemoteException;
    public java.lang.String filetoBoardAttach(java.lang.String pUserID, java.lang.String pBoardID, java.lang.String pItemID, java.lang.String objectID) throws java.rmi.RemoteException;
    public java.lang.String getUnreadItemsCount(java.lang.String pUserID, java.lang.String pBoardID) throws java.rmi.RemoteException;
    public java.lang.String getAdjacentItems(java.lang.String pUserID, java.lang.String pItemID, java.lang.String pBoardID) throws java.rmi.RemoteException;
}
