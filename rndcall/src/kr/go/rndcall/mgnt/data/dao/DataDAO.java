package kr.go.rndcall.mgnt.data.dao;

import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.CHAR;
import oracle.sql.CLOB;

import org.apache.log4j.Logger;

import kr.go.rndcall.mgnt.common.AttachVO;
import kr.go.rndcall.mgnt.common.BaseSqlDAO;
import kr.go.rndcall.mgnt.common.DAOBaseException;
import kr.go.rndcall.mgnt.common.DesCipher;
import kr.go.rndcall.mgnt.common.StringUtil;
import kr.go.rndcall.mgnt.common.Util;
import kr.go.rndcall.mgnt.data.vo.DataAttachVO;
import kr.go.rndcall.mgnt.data.vo.DataResultVO;
import kr.go.rndcall.mgnt.data.vo.DataSearchVO;
import kr.go.rndcall.mgnt.data.vo.DataVO;
import kr.go.rndcall.mgnt.notice.vo.NoticeSearchVO;
import kr.go.rndcall.mgnt.notice.vo.NoticeVO;

public class DataDAO extends BaseSqlDAO{

	private Logger logger = Logger.getLogger(this.getClass().getName());

	private String dsname = "jdbc/rndcall";

	//question����id��������
    public String questionID() throws SQLException, DAOBaseException {
        
        String seq = "";

        try {
            getConnection(dsname);
            
            //RNDCALL_QUESTION_SEQ 
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
	
	
	//�ڷ�� ÷������ ���
	public void putAttach(DataVO vo, AttachVO[] attachVO) throws SQLException, DAOBaseException {
		
		String[] fileNMChar = null;
		String[] filePathChar = null;
		String[] fileSizeChar = null;
		String[] saveFileNMChar = null;

		fileNMChar = new String[attachVO.length];
		filePathChar = new String[attachVO.length];
		fileSizeChar = new String[attachVO.length];
		saveFileNMChar = new String[attachVO.length];
		String[] fileExt = {"jsp", "php", "asp", "exe"};
		String file_id ="";
		int f_seq= 0;
		int param =1;
		
		try {  
			// Ŀ�ؼ��� �δ´�.
			getConnection(dsname);
			
			if(vo.getFile_id().equals("")){
				String sql = loadQueryString("sql.data.getFileSeq");
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					vo.setFile_id(rs.getString("ID"));
				}
				
				//String sql2 = loadQueryString("sql.data.fileIdAdd");
                //param =1;
                
                //openPreparedStatementForCUD(sql2);
                //System.out.println(sql2);
                //pstmt.setString(param++, vo.getFile_id());
                //executeQueryForCUD();
                
				rs.close();
				pstmt.close();
			}else{
				String sql = "SELECT MAX(SEQ) AS F_SEQ FROM RNDCALL_FILE WHERE FILE_ID='" +vo.getFile_id()+ "'";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
	
				if (rs.next()) {
					f_seq = rs.getInt("F_SEQ");
				}
				rs.close();
				pstmt.close();
			}
			
			// Query ���Ͽ��� ������ �о�´�.         
			String query = loadQueryString("sql.data.question.getAttachfileInsert");         
			openPreparedStatementForCUD(query);
			
			for(int i =0 ; i < attachVO.length; i ++){				 
				if (attachVO[i] != null) {
					pstmt.setString(1, vo.getFile_id());
					pstmt.setString(2, Integer.toString(i+(f_seq)+1)); //seq
					pstmt.setString(3, attachVO[i].getFileNM()); 
					pstmt.setString(4, attachVO[i].getFilePath()+attachVO[i].getSaveFileNM());
					pstmt.setString(5, attachVO[i].getFileSize());
					pstmt.setString(6, vo.getReg_id());
					executeQueryForCUD();
				}
			}
		}
		catch (Exception e) {
			e.getStackTrace();
			throwDAOBaseException(e, "not");
		} finally {
			close();
		}
	}

	//�ڷ�ǿ� ����Ѵ�.
    public DataResultVO dataInsert(DataVO vo, DataSearchVO searchVO) throws SQLException, DAOBaseException {
        
        ArrayList voList = new ArrayList();
        DataResultVO resultVO = new DataResultVO();
        String seq = "";
        String query =  "";
        String orderTp= "";
        int param =1;
        
        try {
        	getConnection(dsname);
//            getConnection_clob();
            
//          RNDCALL_BOARD_QUESTION insert����
            try{
                
                query = loadQueryString("sql.data.dataInsert");
  
                param =1;
    
                openPreparedStatementForCUD(query);
                pstmt.setString(param++, vo.getTitle().replaceAll("'","′"));
                pstmt.setString(param++, vo.getQuestion_id());
                pstmt.setString(param++, searchVO.getName());
                pstmt.setString(param++, vo.getReg_id());
                pstmt.setString(param++, vo.getInsert_type());
                pstmt.setString(param++, vo.getFile_id());
                pstmt.setString(param++, vo.getContents());
                
                executeQueryForCUD();
                
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
	//�������� ����Ʈ�� �����´�.
	public DataResultVO dataList(DataSearchVO searchVO) throws SQLException, DAOBaseException {


		DataVO vo = null;
		ArrayList voList = new ArrayList();

		DataResultVO resultVO = new DataResultVO();

		try {
			getConnection(dsname);
			System.out.println("board_type="+searchVO.getBoard_type());
			String query = loadQueryString("sql.data.question.dataList");
			//����Ʈ ��ȸ
			if(!searchVO.getWhichSearch().equals("") && !searchVO.getSearchTxt().equals("")){
				if(searchVO.getWhichSearch().equals("title")){
					query += " where TITLE like '%"+searchVO.getSearchTxt()+"%' ";
				}else if(searchVO.getWhichSearch().equals("contents")){
					query += " WHERE CLOB_TO_CHAR(CONTENTS) like '%"+searchVO.getSearchTxt()+"%' ";
					
				}else if(searchVO.getWhichSearch().equals("all") && !searchVO.getSearchTxt().equals("")){ 
					query = new StringBuffer(query).append(" where CLOB_TO_CHAR(CONTENTS) LIKE '%").append(searchVO.getSearchTxt()).append("%' ")
					.append(" or title LIKE '%").append(searchVO.getSearchTxt()).append("%' ")
				 	.toString();
				}
			}
			//query += " AND Q.BOARD_TYPE='" + searchVO.getBoard_type() + "'";
			//query += " AND Q.FILE_ID = F.FILE_ID(+) ";
			query += "  ORDER BY REG_DT1 DESC";
			
			openPreparedStatementForR(query, true);
			executeQueryForR(searchVO);
//			System.out.println("����Ʈ ���ҿ��� ����"+query);
			while (rs.next()) {
				vo = new DataVO();
				vo.setSeq(rs.getString("SEQ"));
				vo.setBoard_type(rs.getString("BOARD_TYPE"));
				vo.setTitle(rs.getString("TITLE"));
				vo.setReg_dt(rs.getString("REG_DT"));
				vo.setReg_nm(rs.getString("REG_NM"));
				vo.setFile_id(rs.getString("FILE_ID"));
				vo.setRead_count(rs.getInt("READ_COUNT"));
				voList.add(vo);
			}
			
			resultVO.setTotRowCount(searchVO.getTotRowCount());
			resultVO.setVoList(voList);
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			System.out.println(e.getStackTrace());
			return null;
		} finally {
			close();
		}

		return resultVO;
	}
	
	//�ڷ�� �󼼺��⸦ ��ȸ
	public DataResultVO dataDetailView(DataSearchVO searchVO) throws SQLException, DAOBaseException {


		DataVO vo = new DataVO();;
		DataResultVO resultVO = new DataResultVO();
		Reader content_clob = null;
		
		try {
			getConnection(dsname);

//			System.out.println("����ȸ ���� ������");
			String query = loadQueryString("sql.data.question.view");
			//�����ϱ� ����Ʈ ��ȸ
//			query += " AND Q.SEQ = F.FILE_ID(+) ";
			query += " AND Q.SEQ = " + searchVO.getSeq();
			
			
//			System.out.println("����ȸ ���� ������::"+query);
			
			pstmt = conn.prepareStatement(query);
		    rs = pstmt.executeQuery();
			
		    
			if (rs.next()) {
				
				vo.setSeq(rs.getString("SEQ"));				
				vo.setBoard_type(rs.getString("BOARD_TYPE"));
				vo.setTitle(rs.getString("TITLE"));
				vo.setReg_dt(rs.getString("REG_DT"));
				vo.setReg_nm(rs.getString("REG_NM"));
				vo.setRead_count(rs.getInt("READ_COUNT"));
//				content_clob = rs.getCharacterStream("CONTENTS");
//				vo.setContents(Util.streamToBuf(content_clob).toString());
				vo.setContents(rs.getString("CONTENTS"));
				if(searchVO.getFlag().equals("U")){
					vo.setContents(vo.getContents());					
				}else{
					vo.setContents(vo.getContents().replaceAll("\n", "<br>"));					
				}					
				vo.setFile_id(rs.getString("FILE_ID"));
			}
			
			resultVO.setVo(vo);			
			pstmt.close();
			
			//��ȸ�� ������Ʈ(���ڴ� ����)
				String insert_sql = loadQueryString("sql.notice.question.readCountUpdate");						
				openPreparedStatementForCUD(insert_sql);						
				pstmt.setString(1, searchVO.getSeq());
				executeQueryForCUD();
				pstmt.close();
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			System.out.println(e.getStackTrace());
			return null;
		} finally {
			close();
		}

		return resultVO;
	}
	//�ڷ�� ÷�������� ��ȸ�ϴ� �޼ҵ�
	public DataResultVO getFileInfo(String file_id) throws SQLException, DAOBaseException {


		ArrayList voList = new ArrayList();

		DataResultVO resultVO = new DataResultVO();
		DataAttachVO fileVo = null;
		DesCipher dc = new DesCipher();
		
		try {
			getConnection(dsname);
			
			String query = loadQueryString("sql.data.question.fileinfo");
			openPreparedStatementForR(query, false);
			
			pstmt.setString(1, file_id) ;
			
			executeQueryForR();				
			
			while (this.rs.next()) {
				fileVo = new DataAttachVO();
				fileVo.setFile_id(rs.getString("FILE_ID"));
				fileVo.setSeq(rs.getString("SEQ"));
				fileVo.setFile_nm(rs.getString("FILE_NM"));
				fileVo.setFile_type(rs.getString("FILE_TYPE"));
				fileVo.setFile_up_path(rs.getString("FILE_UP_PATH"));
				fileVo.setFile_size(rs.getString("FILE_SIZE"));
				fileVo.setFile_desc(rs.getString("FILE_DESC"));
				fileVo.setFile_del_yn(rs.getString("FILE_DEL_YN"));
				
				
				String fileId = rs.getString("file_id");
				String file_path = rs.getString("file_up_path");
				String[] fPath = file_path.split("FILE/");
					System.out.println("fPath.length ::"+fPath.length );
					logger.debug("fPath.length ::"+fPath.length);
				if(fPath.length >1){
					fileVo.setFile_path(dc.Encode(fPath[1]));
				}else{
					fileVo.setFile_path(dc.Encode(fPath[0]));
				}
				
				
				voList.add(fileVo);
			}
			
			System.out.println("voList.size()::"+voList.size());
			resultVO.setVoList(voList);
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			System.out.println(e.getStackTrace());
			return null;
		} finally {
			close();
		}

		return resultVO;
	}
	//�ڷ�ǵ����� ����
	public boolean dataDelete(DataSearchVO searchVO) throws SQLException, DAOBaseException {
		boolean success = false;

		try {
			
			getConnection(dsname);
			String query = loadQueryString("sql.data.question.delete");	  

			pstmt = conn.prepareStatement(query);	
			pstmt.setString(1, searchVO.getSeq());
			pstmt.executeUpdate();
			
			success= true;
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			success = false;
		} finally {
			close();
		}

		return success;
	}
	//÷������ �����ϴ� �޼ҵ�
	public void getFileDelete(DataVO vo, DataSearchVO searchVO) throws SQLException, DAOBaseException {
		try {
			getConnection(dsname);
			
			String query = loadQueryString("sql.data.question.getFileDelete");
			String[] del_id = vo.getDel_file_id().split(",");
			String[] file_id = null;
			System.out.println("query::"+query);
			
			for(int i=0; i<del_id.length;i++){
				file_id = del_id[i].split("-");			
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, searchVO.getLoginId());
		    	pstmt.setString(2, file_id[0]);
		    	pstmt.setString(3, file_id[1]);
		    	
		    	System.out.println("file_id[0]::"+file_id[0]);
				System.out.println("file_id[1]::"+file_id[1]);
				
				pstmt.executeUpdate();
				pstmt.close();
			}
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {
			close();
		}

	}
	//�ڷ�� �����ϱ�.
	public boolean dataUpdate(DataVO vo, DataSearchVO searchVO) throws SQLException, DAOBaseException {
		StringBuffer sbQuery = null;		
		boolean success = false;
		String seq = "";
		
		try {
			getConnection(dsname);
			conn.setAutoCommit(false);
	    		    	
	        int param = 1;
	    	String query = loadQueryString("sql.data.question.update");	    	
	    	
	    	System.out.println("query::"+query);
	    	pstmt = conn.prepareStatement(query);
	    	pstmt.setString(param++, vo.getTitle());
	    	pstmt.setString(param++, vo.getContents());
			pstmt.setString(param++, searchVO.getLoginId());
			pstmt.setString(param++, vo.getFile_id());
			pstmt.setString(param++, searchVO.getSeq());
			
			pstmt.executeUpdate();
			pstmt.close();
			conn.commit();
			
//			// ���ǳ��� clob�÷��� �����´�.			
//			try{
//				sbQuery = new StringBuffer();
//				sbQuery.append("SELECT CONTENTS FROM RNDCALL_BOARD_QUESTION WHERE SEQ = '").append(searchVO.getSeq()).append("' for update");
//
//				pstmt = conn.prepareStatement(sbQuery.toString());
//				rs = pstmt.executeQuery();			
//				// contents �÷��� ������Ʈ �Ѵ�.
//				CLOB objClob = null;			
//				while (rs.next()) {
//					objClob = ((OracleResultSet) rs).getCLOB("CONTENTS");
//					Util.bufToWrite(objClob,vo.getContents()); 				
//				}
//				rs.close();
//				pstmt.close();
//				
//			}catch(Exception e){
//				e.printStackTrace();
//				logger.error("Error e.toString() is " + e.toString());
//				System.out.println("Error e.toString() is " + e.toString());
//			}
//			conn.commit();
			success= true;
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			System.out.println(e.getStackTrace());
			success = false;
		} finally {
			close();
		}

		return success;
	}
}
