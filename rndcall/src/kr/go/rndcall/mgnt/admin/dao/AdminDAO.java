package kr.go.rndcall.mgnt.admin.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.CHAR;
import oracle.sql.CLOB;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import kr.go.rndcall.mgnt.common.StringUtil;
import kr.go.rndcall.mgnt.common.BaseSqlDAO;
import kr.go.rndcall.mgnt.common.AttachVO;
import kr.go.rndcall.mgnt.common.DAOBaseException;
import kr.go.rndcall.mgnt.admin.vo.AdminAttachVO;
import kr.go.rndcall.mgnt.admin.vo.AdminResultVO;
import kr.go.rndcall.mgnt.admin.vo.AdminSearchVO;
import kr.go.rndcall.mgnt.admin.vo.AdminVO;

public class AdminDAO extends BaseSqlDAO{

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private String dsname = "jdbc/rndcall";

    /**
     * RNDCALL_FILE_ID_SEQ ������ ������ȸ 
     */
    public String getFileIdSeq() throws SQLException, DAOBaseException {
        
        String seq = "";

        try {
            getConnection(dsname);
            
            //TCM_FILE_MAPPING data_id ������ ���� ��ȸ
            String sql_create1="SELECT RNDCALL_FILE_ID_SEQ.NEXT_VALUE AS SEQ" ;
            openPreparedStatementForR(sql_create1,   false);
            executeQueryForR(); //

            while (this.rs.next()) {
                seq = rs.getString("SEQ") ;
            }           
        } catch (Exception e) {
            throwDAOBaseException(e, "not");
            return null;
        } finally {
            close();
        }
        return seq;
    }
    
    public String getOfflineDataQuestionSeq() throws SQLException, DAOBaseException {
        
        String seq = "";

        try {
            getConnection(dsname);
            
            //RNDCALL_QUESTION_SEQ ������ ���� ��ȸ
            String sql_create1="SELECT RNDCALL_QUESTION_SEQ.NEXT_VALUE AS SEQ " ;
            openPreparedStatementForR(sql_create1,   false);
            executeQueryForR(); //

            while (this.rs.next()) {
                seq = rs.getString("SEQ") ;
            }           
        } catch (Exception e) {
            throwDAOBaseException(e, "not");
            return null;
        } finally {
            close();
        }
        return seq;
    }
    
    public String getOfflineDataAnswerSeq() throws SQLException, DAOBaseException {
        
        String seq = "";

        try {
            getConnection(dsname);
            
            //RNDCALL_BOARD_ANSWER ������ ���� ��ȸ
            String sql_create1="SELECT RNDCALL_ANSWER_SEQ.NEXT_VALUE AS SEQ " ;
            openPreparedStatementForR(sql_create1,   false);
            executeQueryForR(); //

            while (this.rs.next()) {
                seq = rs.getString("SEQ") ;
            }           
        } catch (Exception e) {
            throwDAOBaseException(e, "not");
            return null;
        } finally {
            close();
        }
        return seq;
    }
    
    /**
     * putAttach RNDCALL_FILE ���̺? ÷���������� ����
     *
     * @param vo
     */
    public void putAttach(AdminVO vo, AttachVO[] attachVO) throws SQLException, DAOBaseException {
        
//       CHAR[] fileNMChar = null;
        String[] fileNMChar = null;
        
        
        String[] filePathChar = null;
        String[] fileSizeChar = null;
        String[] saveFileNMChar = null;
        
        //fileNMChar = new CHAR[attachVO.length];
        fileNMChar = new String[attachVO.length];
        filePathChar = new String[attachVO.length];
        fileSizeChar = new String[attachVO.length];
        saveFileNMChar = new String[attachVO.length];
        String[] fileExt = {"jsp", "php", "asp", "exe"};
        
        for (int i = 0; i < attachVO.length; i++) {
			if (attachVO[i] != null) {
	            fileNMChar[i] =  attachVO[i].getFileNM();
	            filePathChar[i] = attachVO[i].getFilePath();
	            fileSizeChar[i] = attachVO[i].getFileSize();
	            saveFileNMChar[i] = attachVO[i].getSaveFileNM();
			}
        }
        
        try {  
            // Ŀ�ؼ��� �δ´�.
            getConnection(dsname);
            
            // Query ���Ͽ��� ������ �о�´�.         
            String query = loadQueryString("sql.offlineData.getAttachfileInsert");         
            openPreparedStatementForCUD(query);
            
            for(int i =0 ; i < attachVO.length; i ++){
                pstmt.setString(1, Integer.toString(i+1)); //seq
                pstmt.setString(2, vo.getReg_id());             
                pstmt.setString(3, filePathChar[i]+saveFileNMChar[i]);
                pstmt.setString(4, fileSizeChar[i] );
                pstmt.setString(5, fileNMChar[i]);  
                pstmt.setString(6, vo.getData_id() );
                
                executeQueryForCUD();
            }
        }
        catch (Exception e) {
            e.getStackTrace();
            throwDAOBaseException(e, "not");
            
            
        } finally {
            close();
        }
    }
    
    /*
     * RNDCALL_BOARD_QUESTION �� INSERT
     * RNDCALL_BOARD_ANSWER �� INSERT
     */
    public AdminResultVO getOfflineDataInsert(AdminVO vo) throws SQLException, DAOBaseException {
        
        ArrayList voList = new ArrayList();
        AdminResultVO resultVO = new AdminResultVO();
        String seq = "";
        String query =  "";
        String orderTp= "";
        int param =1;
        
        try {
            getConnection(dsname);
            
//          RNDCALL_BOARD_QUESTION insert����
            try{
                
                query = loadQueryString("sql.offlineData.getOfflineDataQuestionInsert");
  
                param =1;
    
                if (vo.getReg_dt().equals("")) vo.setReg_dt("1900-01-01");
                if (vo.getReg_time().equals("")) vo.setReg_time("00:00");
                query = query.replaceAll("REGDATETIME", vo.getReg_dt() + " " + vo.getReg_time());

                openPreparedStatementForCUD(query);
                
                pstmt.setString(param++, vo.getTitle());
                pstmt.setString(param++, vo.getQuestion_id());
                pstmt.setString(param++, vo.getName());
                pstmt.setString(param++, vo.getReg_id());
                pstmt.setString(param++, "OFFLINE");
                pstmt.setString(param++, "");
                
                if(vo.getEmail() == null || "".equals(vo.getEmail())){
                    pstmt.setString(param++, "N"); //email ���ſ���
                }else{
                    pstmt.setString(param++, "Y"); //email ���ſ���
                }
                
                pstmt.setString(param++, vo.getEmail()); //email �ּ�
                pstmt.setString(param++, vo.getQuestion_contents()); //email �ּ�
                if (vo.getCell_number().equals("")) vo.setCell_number("010--");
                pstmt.setString(param++, vo.getCell_number());
                pstmt.setString(param++, vo.getCategory1());
                pstmt.setString(param++, vo.getCategory2());
                pstmt.setString(param++, "QNA");
                
                executeQueryForCUD();
                
//                //��� ������ seq �� �ش��ϴ� contents �÷��� �����´�.
//                StringBuffer sbQuery = new StringBuffer();
//                sbQuery.append("SELECT CONTENTS FROM RNDCALL_BOARD_QUESTION WHERE seq = '").append(vo.getQuestion_id()).append("' for update");
//                pstmt = conn.prepareStatement(sbQuery.toString());
//                rs = pstmt.executeQuery();          
//                // contents �÷��� ������Ʈ �Ѵ�.
//                CLOB clobData = null;           
//                while (rs.next()) {
//                    clobData = ((OracleResultSet) rs).getCLOB("CONTENTS");
//                    StringUtil.bufToWrite(clobData,vo.getQuestion_contents().replaceAll("\n", ""));               
//                }
//                rs.close();
//                pstmt.close();
                
            }catch(SQLException e){
                e.printStackTrace();
            }
            
//          RNDCALL_BOARD_ANSWER insert����
            try{
                
                query = loadQueryString("sql.offlineData.getOfflineDataAnswerInsert");
  
                param =1;
    
                openPreparedStatementForCUD(query);

                pstmt.setString(param++, vo.getAnswer_id());
                pstmt.setString(param++, vo.getReg_id());
                pstmt.setString(param++, vo.getQuestion_id());
                pstmt.setString(param++, vo.getData_id());
                pstmt.setString(param++, vo.getAnswer_contents());
                
                executeQueryForCUD();
                
//                //��� ������ seq �� �ش��ϴ� contents �÷��� �����´�.
//                StringBuffer sbQuery = new StringBuffer();
//                sbQuery.append("SELECT ANSWER_CONT FROM RNDCALL_BOARD_ANSWER WHERE seq = '").append(vo.getAnswer_id()).append("' for update");
//                pstmt = conn.prepareStatement(sbQuery.toString());
//                rs = pstmt.executeQuery();          
//                // contents �÷��� ������Ʈ �Ѵ�.
//                CLOB clobData = null;           
//                while (rs.next()) {
//                    clobData = ((OracleResultSet) rs).getCLOB("ANSWER_CONT");
//                    StringUtil.bufToWrite(clobData,vo.getAnswer_contents().replaceAll("\n", ""));               
//                }
//                rs.close();
//                pstmt.close();
                
            }catch(SQLException e){
                e.printStackTrace();
            }
            
        } catch (Exception e) {
            throwDAOBaseException(e, "not");
            return null;
        } finally {
            close();
        }
        return resultVO;
    }
   
    /*
     * �������� ������ ���� ���ε� ���� 
     */
   public void getOfflineDataExcelUploadInsert(ArrayList list) throws SQLException, DAOBaseException {
        
        try {
            AdminVO vo = new AdminVO();

            for(int i = 0; i < list.size(); i++){
                vo = (AdminVO)list.get(i);
                
                this.getOfflineDataInsert(vo);
            } 

        } catch (Exception e) {
            throwDAOBaseException(e, "not");
        } finally {
            close();
        }
    }
   
    /*
     * ��з� �ڵ带 �����´�.
     */
    public AdminResultVO retrieveCategory1Code(AdminSearchVO searchVO) throws SQLException, DAOBaseException {
        AdminResultVO resultVO = new AdminResultVO();
        AdminVO vo = null;
        ArrayList voList = new ArrayList();
        try {
            getConnection(dsname);
            String query = loadQueryString("sql.offlineData.retrieveCategory1Code");
            openPreparedStatementForR(query,  false);
            executeQueryForR();
            while(rs.next()) {
                vo = new AdminVO();
                vo.setCode(rs.getString("code"));
                vo.setCode_nm(rs.getString("code_nm"));
                voList.add(vo);
            }
        } catch (Exception e) {
            throwDAOBaseException(e, "not");
        } finally {
            close();
        }
        resultVO.setVoList(voList);
        return resultVO;
    }
    
    /*
     * �Һз� �ڵ带 �����´�.
     */
    public AdminResultVO retrieveCategory2Code(AdminSearchVO searchVO) throws SQLException, DAOBaseException {
        AdminResultVO resultVO = new AdminResultVO();
        AdminVO vo = null;
        int param = 1;
        ArrayList voList = new ArrayList();
        try {
            getConnection(dsname);
            String query = loadQueryString("sql.offlineData.retrieveCategory2Code");
            openPreparedStatementForR(query,  false);  

            executeQueryForR();
            while(rs.next()) {
                vo = new AdminVO();
                vo.setCode(rs.getString("code"));
                vo.setCode_nm(rs.getString("code_nm"));
                vo.setP_code(rs.getString("p_code"));
                
                voList.add(vo);
            }
        } catch (Exception e) {
            throwDAOBaseException(e, "not");
        } finally {
            close();
        }
        resultVO.setVoList(voList);
        return resultVO;
    }
    
}
