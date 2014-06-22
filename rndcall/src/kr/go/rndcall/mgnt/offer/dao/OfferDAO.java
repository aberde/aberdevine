package kr.go.rndcall.mgnt.offer.dao;

import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import kr.go.rndcall.mgnt.common.MailSend;
import kr.go.rndcall.mgnt.common.SmsSend;
import kr.go.rndcall.mgnt.common.StringUtil;
import kr.go.rndcall.mgnt.common.Util;
import kr.go.rndcall.mgnt.inquire.vo.InquireVO;
import kr.go.rndcall.mgnt.offer.vo.OfferAttachVO;
import kr.go.rndcall.mgnt.offer.vo.OfferResultVO;
import kr.go.rndcall.mgnt.offer.vo.OfferSearchVO;
import kr.go.rndcall.mgnt.offer.vo.OfferVO;
import kr.go.rndcall.mgnt.offer.vo.SatiVO;

public class OfferDAO extends BaseSqlDAO{

	private Logger logger = Logger.getLogger(this.getClass().getName());

	private String dsname = "jdbc/rndcall";
	
	
	//�����ϱ� ����Ʈ�� ��ȸ�ϴ� �޼ҵ�
	public OfferResultVO offerList(OfferSearchVO searchVO) throws SQLException, DAOBaseException {


		OfferVO vo = null;
		OfferVO statVo = null;
		ArrayList voList = new ArrayList();

		OfferResultVO resultVO = new OfferResultVO();

		try {
			getConnection(dsname);
			
			String query = "";
			if(searchVO.getRoleCD().equals("0000C") ||  searchVO.getRoleCD().equals("0000Z")){
				query = loadQueryString("sql.offer.question.offerList");
				if(searchVO.getType().equals("1")){
//					query += " AND Q.SEQ = A.Q_SEQ(+) ";
					query += " AND NOT EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER AA WHERE Q.SEQ = AA.Q_SEQ) ";
				}else if(searchVO.getType().equals("2")){
//					query += " AND Q.SEQ = A.Q_SEQ(+) ";
					query += " AND EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER AA WHERE Q.SEQ = AA.Q_SEQ) ";
					query += " AND NVL(Q.PUBLIC_TRANS_YN,'N') != 'Y' ";
				}else if(searchVO.getType().equals("3")){
//					query += " AND Q.SEQ = A.Q_SEQ(+) ";
					query += " AND EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER AA WHERE Q.SEQ = AA.Q_SEQ) ";
					query += " AND NVL(Q.PUBLIC_TRANS_YN,'N') = 'Y' ";
//				}else{
//					query += " AND Q.SEQ = A.Q_SEQ(+) ";
				}
			}else{
				query = loadQueryString("sql.offer.question.offerList2");
				query += " AND Q.SEQ = A.Q_SEQ ";
			}

			query += " AND Q.BOARD_TYPE='" + searchVO.getBoard_type() + "'";
			
			if(!searchVO.getSearchCategory().equals("")){
				query += " AND Q.CATEGORY1 = '"+searchVO.getSearchCategory()+"' ";
			}
			if(!searchVO.getWhichSearch().equals("") && !searchVO.getSearchTxt().equals("")){
				if(searchVO.getWhichSearch().equals("title")){
					query += " AND Q.TITLE like '%"+searchVO.getSearchTxt()+"%' ";
				}else if(searchVO.getWhichSearch().equals("contents")){
					query += " AND CLOB_TO_CHAR(Q.CONTENTS) like '%"+searchVO.getSearchTxt()+"%' ";
				}else if(searchVO.getWhichSearch().equals("all") && !searchVO.getSearchTxt().equals("")){ 
					query = new StringBuffer(query).append(" AND (Q.CONTENTS LIKE '%").append(searchVO.getSearchTxt()).append("%' ")
					.append(" or Q.title LIKE '%").append(searchVO.getSearchTxt()).append("%') ")
				 	.toString();
				}
			}
			query += " ORDER BY Q.REG_DT DESC";
			
			openPreparedStatementForR(query, true);
			executeQueryForR(searchVO);
			
			while (rs.next()) {
				vo = new OfferVO();
				vo.setSeq(rs.getString("SEQ"));
				vo.setTitle(rs.getString("TITLE"));
				vo.setBoard_type(rs.getString("BOARD_TYPE"));
				vo.setCategory1(rs.getString("CATEGORY1"));
				vo.setReg_dt(rs.getString("REG_DT"));
				vo.setReg_nm(rs.getString("REG_NM"));
				vo.setRead_count(rs.getInt("READ_COUNT"));
				vo.setStat(rs.getString("STAT"));
				
				voList.add(vo);
			}
			
			resultVO.setTotRowCount(searchVO.getTotRowCount());
			resultVO.setVoList(voList);
			
			query = loadQueryString("sql.offer.question.qnaStatInfo");
			openPreparedStatementForR(query, false);
			executeQueryForR(searchVO);
			
			statVo = new OfferVO();
			if(rs.next()) {
				statVo.setStatCnt1(rs.getString("CNT1"));
				statVo.setStatCnt2(rs.getString("CNT2"));
				statVo.setStatCnt3(rs.getString("CNT3"));
			}
			
			query = loadQueryString("sql.offer.question.proposalStatInfo");
			openPreparedStatementForR(query, false);
			executeQueryForR(searchVO);

			if(rs.next()) {
				statVo.setStatCnt4(rs.getString("CNT1"));
				statVo.setStatCnt5(rs.getString("CNT2"));
				statVo.setStatCnt6(rs.getString("CNT3"));
			}
			
			resultVO.setVo(statVo);
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			System.out.println(e.getStackTrace());
			return null;
		} finally {
			close();
		}

		return resultVO;
	}
	//�����ϱ� �󼼺��⸦ ��ȸ
	public OfferResultVO offerDetailView(OfferSearchVO searchVO) throws SQLException, DAOBaseException {


		OfferVO vo = new OfferVO();;
		ArrayList voList = new ArrayList();
		OfferResultVO resultVO = new OfferResultVO();
		Reader content_clob = null;
		Reader answerContent_clob = null;
		
		try {
			getConnection(dsname);

			String query = loadQueryString("sql.offer.question.view");
			//�����ϱ� ����Ʈ ��ȸ
			query += " AND Q.BOARD_TYPE= '" + searchVO.getBoard_type() +"'";
//			query += " AND Q.SEQ = A.Q_SEQ(+) ";
			query += " AND Q.SEQ = '" + searchVO.getSeq() +"'";
			
			
			pstmt = conn.prepareStatement(query);
		    rs = pstmt.executeQuery();
			
		    
			if (rs.next()) {
				vo = new OfferVO();
				vo.setSeq(rs.getString("SEQ"));				
				vo.setTitle(rs.getString("TITLE"));
				vo.setCall_receive_yn(rs.getString("CELL_RECEIVE_YN"));
				vo.setCell_number(rs.getString("CELL_NUMBER"));
				vo.setEmail_receive_yn(rs.getString("EMAIL_RECEIVE_YN"));
				vo.setEmail(rs.getString("EMAIL"));
				vo.setOpen_yn(rs.getString("OPEN_YN"));
				vo.setBoard_type(rs.getString("BOARD_TYPE"));
				vo.setCategory1(rs.getString("CATEGORY1"));
				vo.setCategory2(rs.getString("CATEGORY2"));
				vo.setCategory1_nm(rs.getString("CATEGORY1_NM"));
				vo.setCategory2_nm(rs.getString("CATEGORY2_NM"));
				vo.setReg_dt(rs.getString("REG_DT"));
				vo.setReg_nm(rs.getString("REG_NM"));
				vo.setRead_count(rs.getInt("READ_COUNT"));
//				content_clob = rs.getCharacterStream("CONTENTS");
//				vo.setContents(Util.streamToBuf(content_clob).toString());
				vo.setContents(rs.getString("CONTENTS"));
				if(searchVO.getFlag().equals("U")){
					vo.setContents(vo.getContents());					
				}else if(searchVO.getFlag().equals("A")){
					if (vo.getContents() != null && !vo.getContents().equals("")) {
						vo.setContents(vo.getContents().replaceAll("\n", "<br>"));
					}
				}else{
					if (vo.getContents() != null && !vo.getContents().equals("")) {
						vo.setContents(vo.getContents().replaceAll("\n", "<br>"));
					}
				}
//				answerContent_clob = rs.getCharacterStream("ANSWER_CONT");
//				vo.setAnswerContents(Util.streamToBuf(answerContent_clob).toString());				
				vo.setAnswerContents(rs.getString("ANSWER_CONT"));
				
				if(searchVO.getFlag().equals("U")){
					vo.setAnswerContents(vo.getAnswerContents());		
				}else if(searchVO.getFlag().equals("A")){
					vo.setAnswerContents(vo.getAnswerContents());		
				}else{
					if (vo.getAnswerContents() != null && !vo.getAnswerContents().equals("")) {
						vo.setAnswerContents(vo.getAnswerContents().replaceAll("\n", "<br>"));
					}
				}
				vo.setQuestion_file_id(rs.getString("QUESTION_FILE_ID"));
				vo.setAnswer_file_id(rs.getString("ANSWER_FILE_ID"));
				vo.setUp_del_stat(rs.getString("UP_DEL_STAT"));
				vo.setStat(rs.getString("STAT"));
				vo.setAnswer_seq(rs.getString("ANSWER_SEQ"));
				vo.setPublic_trans_yn(rs.getString("PUBLIC_TRANS_YN"));
			}
			
			resultVO.setVo(vo);			
			pstmt.close();
			
			//if(searchVO.getRoleCD().equals("0000C") ||  searchVO.getRoleCD().equals("0000Z")){
				String insert_sql = loadQueryString("sql.offer.question.readCountUpdate");						
				openPreparedStatementForCUD(insert_sql);						
				pstmt.setString(1, searchVO.getSeq());
				executeQueryForCUD();
				pstmt.close();
			//}
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			System.out.println(e.getStackTrace());
			return null;
		} finally {
			close();
		}

		return resultVO;
	}
	//�����ϱ� ÷�������� ��ȸ�ϴ� �޼ҵ�
	public OfferResultVO getFileInfo(String file_id) throws SQLException, DAOBaseException {


		ArrayList voList = new ArrayList();

		OfferResultVO resultVO = new OfferResultVO();
		OfferAttachVO fileVo = null;
		DesCipher dc = new DesCipher();
		
		try {
			getConnection(dsname);
			
			String query = loadQueryString("sql.offer.question.fileinfo");
			openPreparedStatementForR(query, false);
			
			pstmt.setString(1, file_id) ;
			
			executeQueryForR();				
			
			while (this.rs.next()) {
				fileVo = new OfferAttachVO();
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
	//�����ϱ� �������� ��ȸ�ϴ� �޼ҵ�
	public OfferResultVO getSatiInfo(String seq, String login_id) throws SQLException, DAOBaseException {


		OfferResultVO resultVO = new OfferResultVO();
		SatiVO satiVO = new SatiVO();
		
		System.out.println("seq::"+seq);
		System.out.println("login_id::"+login_id);
		
		
		try {
			getConnection(dsname);
			
			String query = loadQueryString("sql.offer.question.satiInfo.avgAppPoint");
			openPreparedStatementForR(query, false);
			
			pstmt.setString(1, seq) ;
			
			executeQueryForR();				
			
			if (this.rs.next()) {
				satiVO.setAvg_app_point(rs.getString("AVG_APP_POINT"));		
			}
			
			query = loadQueryString("sql.offer.question.satiInfo.satiRegCnt");
			openPreparedStatementForR(query, false);
			
			pstmt.setString(1, seq) ;
			
			executeQueryForR();				
			
			if (this.rs.next()) {
				satiVO.setSati_reg_cnt(rs.getString("SATI_REG_CNT"));		
			}
			
			
			query = loadQueryString("sql.offer.question.satiInfo.satiRegYn");
			openPreparedStatementForR(query, false);
			
			pstmt.setString(1, seq) ;
			pstmt.setString(2, login_id) ;
			
			executeQueryForR();				
			
			if (this.rs.next()) {
				satiVO.setSati_reg_yn(rs.getString("SATI_REG_YN"));		
			}
						
			resultVO.setSatiVO(satiVO);
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			System.out.println(e.getStackTrace());
			return null;
		} finally {
			close();
		}

		return resultVO;
	}
	//�����ϱ� �������� ����ϴ� �޼ҵ�
	public boolean offerSatiInsert(OfferVO vo, OfferSearchVO searchVO, SatiVO satiVO) throws SQLException, DAOBaseException {
		boolean success = false;
		String seq= "";
		

		try {
			getConnection(dsname);
			
			String sql = loadQueryString("sql.offer.getSatiSeq");
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				seq = rs.getString("ID");
			}
			rs.close();
			pstmt.close();
			
			String insert_sql = loadQueryString("sql.offer.question.satiInsert");
			
			int param =1;
			
			openPreparedStatementForCUD(insert_sql);
						
			pstmt.setString(param++, seq);
			pstmt.setString(param++, searchVO.getSeq());
			pstmt.setString(param++, searchVO.getBoard_type());
			pstmt.setString(param++, searchVO.getLoginId());
			pstmt.setString(param++, satiVO.getApp_point());
			pstmt.setString(param++, satiVO.getReg_ip());
			
			executeQueryForCUD();
			pstmt.close();
			success= true;
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			success = false;
		} finally {
			close();
		}

		return success;
	}
    //��з� �ڵ带 �����´�.
    public OfferResultVO retrieveCategory1Code(OfferSearchVO searchVO) throws SQLException, DAOBaseException {
    	OfferResultVO resultVO = new OfferResultVO();
    	OfferVO vo = null;
        ArrayList voList = new ArrayList();
        try {
            getConnection(dsname);
            String query = loadQueryString("sql.faq.question.Category1Code");
            openPreparedStatementForR(query,  false);
            executeQueryForR();
            while(rs.next()) {
                vo = new OfferVO();
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
    
    //�Һз� �ڵ带 �����´�.
    public OfferResultVO retrieveCategory2Code(OfferSearchVO searchVO) throws SQLException, DAOBaseException {
    	OfferResultVO resultVO = new OfferResultVO();
    	OfferVO vo = null;
        int param = 1;
        ArrayList voList = new ArrayList();
        try {
            getConnection(dsname);
            String query = loadQueryString("sql.faq.question.Category2Code");
            openPreparedStatementForR(query,  false);  

            executeQueryForR();
            while(rs.next()) {
                vo = new OfferVO();
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
    //RNDCALL_FILE_ID_SEQ ������ ������ȸ 
    public String getFileIdSeq() throws SQLException, DAOBaseException {
        
        String seq = "";

        try {
            getConnection(dsname);
            
            //TCM_FILE_MAPPING data_id ������ ���� ��ȸ
            String sql_create1="SELECT RNDCALL_FILE_ID_SEQ.NEXT_VALUE AS SEQ " ;
            openPreparedStatementForR(sql_create1,   false);
            executeQueryForR(); //

            while (this.rs.next()) {
                seq = rs.getString("SEQ") ;
            }           
        } catch (Exception e) {
            throwDAOBaseException(e, "not");
            return null;
        } finally {
//            conn.commit();
            close();
        }
        return seq;
    }
    //������ id�� �������� �޼ҵ�
    public String getSequenceID() throws SQLException, DAOBaseException {
        
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
//            conn.commit();
            close();
        }
        return seq;
    }
	//÷������ ���
	public void putAttach(OfferVO vo, AttachVO[] attachVO) throws SQLException, DAOBaseException {
		
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
		
		try {  
			// Ŀ�ؼ��� �δ´�.
			getConnection(dsname);
			
			if(vo.getFile_id().equals("")){
				String sql = loadQueryString("sql.offer.getFileSeq");
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
	
				if (rs.next()) {
					vo.setFile_id(rs.getString("ID"));
				}
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
			String query = loadQueryString("sql.offer.question.getAttachfileInsert");         
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
    //÷���������� ���� �ӽ�
/*    public void putAttach(OfferVO vo, AttachVO[] attachVO) throws SQLException, DAOBaseException {
        
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
            fileNMChar[i] =  attachVO[i].getFileNM();
            filePathChar[i] = attachVO[i].getFilePath();
            fileSizeChar[i] = attachVO[i].getFileSize();
            saveFileNMChar[i] = attachVO[i].getSaveFileNM();
        }
        
        try {  
            // Ŀ�ؼ��� �δ´�.
            getConnection(dsname);
            
            // Query ���Ͽ��� ������ �о�´�.         
            String query = loadQueryString("sql.offer.getAttachfileInsert");         
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
    }*/
    public OfferResultVO offerInsert(OfferVO vo, OfferSearchVO searchVO) throws SQLException, DAOBaseException {
        
        ArrayList voList = new ArrayList();
        OfferResultVO resultVO = new OfferResultVO();
        String seq = "";
        String query =  "";
        String orderTp= "";
        int param =1;
        
        try {
            getConnection(dsname);
            
//          RNDCALL_BOARD_QUESTION insert����
            try{
                
                query = loadQueryString("sql.offer.offerInsert");
  
                param =1;
    
                openPreparedStatementForCUD(query);
                pstmt.setString(param++, vo.getTitle());
                pstmt.setString(param++, vo.getQuestion_id());
                System.out.println("fdsfsdfdsfsfs" + vo.getQuestion_id());
                pstmt.setString(param++, searchVO.getName());
                pstmt.setString(param++, vo.getReg_id());
                pstmt.setString(param++, vo.getInsert_type());
                pstmt.setString(param++, vo.getFile_id());
                
                if(vo.getEmail() == null || "".equals(vo.getEmail())){
                    pstmt.setString(param++, "N"); //email ���ſ���
                }else{
                    pstmt.setString(param++, "Y"); //email ���ſ���
                }
                
                pstmt.setString(param++, vo.getEmail()); //email �ּ�
                pstmt.setString(param++, vo.getContents());
                pstmt.setString(param++, vo.getCall_receive_yn());
                pstmt.setString(param++, vo.getCell_number());
                pstmt.setString(param++, vo.getCategory1());
                pstmt.setString(param++, vo.getCategory2());
                pstmt.setString(param++, vo.getBoard_type());
                pstmt.setString(param++, vo.getOpen_yn());
                
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
	//�����ϱ� �亯�ޱ�
	public boolean offerAnswerInsert(OfferVO vo, OfferSearchVO searchVO) throws SQLException, DAOBaseException {
		boolean success = false;
		String answer_seq = "";
		StringBuffer sbQuery = null;
		try {
			
			getConnection(dsname);
			conn.setAutoCommit(false);
			
			String sql = loadQueryString("sql.offer.getSeq");
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				answer_seq = rs.getString("ID");
			}
			rs.close();
			pstmt.close();
			String answer_sql = loadQueryString("sql.offer.question.getAnswerInsert");
			int param =1;
			
			openPreparedStatementForCUD(answer_sql);
						
			pstmt.setString(param++, answer_seq);
			pstmt.setString(param++, searchVO.getSeq());
			pstmt.setString(param++, "[답변]"+vo.getTitle().replaceAll("'","′"));
			pstmt.setString(param++, vo.getAnswerContents());
			pstmt.setString(param++, vo.getFile_id());
			pstmt.setString(param++, searchVO.getLoginId());
			
			executeQueryForCUD();
			pstmt.close();
			conn.commit();

			//�о����� �����̺? ������Ʈ
			String query = loadQueryString("sql.inquire.question.getCategoryUpdate");	 
			pstmt = conn.prepareStatement(query);
	    	pstmt.setString(1, vo.getCategory1());
	    	pstmt.setString(2, vo.getCategory2());
			pstmt.setString(3, searchVO.getSeq());
			
			pstmt.executeUpdate();
			pstmt.close();
			conn.commit();
			
			success= true;			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			success = false;
		} finally {
			conn.setAutoCommit(true);
			close();
		}

		return success;
	}
	//÷������ �����ϴ� �޼ҵ�
	public void getAnswerFileDelete(OfferVO vo,OfferSearchVO searchVO) throws SQLException, DAOBaseException {
		try {
			getConnection(dsname);
			//÷������ ����
			String query = loadQueryString("sql.offer.question.getFileDelete");
			String[] del_id = vo.getDel_file_id().split(",");
			String[] file_id = null;
			
			for(int i=0; i<del_id.length;i++){
				file_id = del_id[i].split("-");			
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, searchVO.getLoginId());
		    	pstmt.setString(2, file_id[0]);
		    	pstmt.setString(3, file_id[1]);
				pstmt.executeUpdate();
				pstmt.close();
			}
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {
			close();
		}

	}
	//�����ϱ� �亯 ����ϴ� �޼ҵ�
	public boolean offerAnswerUpdate(OfferVO vo,OfferSearchVO searchVO) throws SQLException, DAOBaseException {
		boolean success = false;
		String answer_seq = "";
		StringBuffer sbQuery = null;
		try {
			
			getConnection(dsname);
			conn.setAutoCommit(false);

			String answer_sql = loadQueryString("sql.offer.question.getAnswerUpdate");
			int param =1;
			
			openPreparedStatementForCUD(answer_sql);
			
			pstmt.setString(param++, vo.getAnswerContents());
			pstmt.setString(param++, vo.getFile_id());
			pstmt.setString(param++, searchVO.getLoginId());			
			pstmt.setString(param++, vo.getAnswer_seq());
			
			executeQueryForCUD();
			pstmt.close();
			conn.commit();
			
			//���ǳ��� clob�÷��� �����´�.			
//			try{
//				sbQuery = new StringBuffer();
//				sbQuery.append("SELECT ANSWER_CONT FROM RNDCALL_BOARD_ANSWER WHERE SEQ = '").append(vo.getAnswer_seq()).append("' for update");
//
//				pstmt = conn.prepareStatement(sbQuery.toString());
//				rs = pstmt.executeQuery();			
//				// contents �÷��� ������Ʈ �Ѵ�.
////				CLOB objClob = null;			
//				while (rs.next()) {
////					objClob = ((OracleResultSet) rs).getCLOB("ANSWER_CONT");
////					Util.bufToWrite(objClob,vo.getAnswerContents());
//					vo.
//				}
//				
//			}catch(Exception e){
//				e.printStackTrace();
//				logger.error("Error e.toString() is " + e.toString());
//				System.out.println("Error e.toString() is " + e.toString());
//			}
//			rs.close();
//			pstmt.close();
//			conn.commit();
			
			
			success= true;			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			success = false;
		} finally {
			conn.setAutoCommit(true);
			close();
		}

		return success;
	}
	//�����ϱ� ���ǳ������
	public boolean offerUpdate(OfferVO vo, OfferSearchVO searchVO) throws SQLException, DAOBaseException {
		StringBuffer sbQuery = null;		
		boolean success = false;
		String seq = "";
		
		try {
			getConnection(dsname);
			conn.setAutoCommit(false);
	    		    	
	        int param = 1;
	    	String query = loadQueryString("sql.offer.question.update");	    	
	    	
	    	System.out.println("query::"+query);
	    	pstmt = conn.prepareStatement(query);
	    	pstmt.setString(param++, vo.getTitle().replaceAll("'","′"));
			pstmt.setString(param++, vo.getContents());
			pstmt.setString(param++, vo.getCall_receive_yn());
			pstmt.setString(param++, vo.getCell_number());
			pstmt.setString(param++, vo.getEmail_receive_yn());
			pstmt.setString(param++, vo.getEmail());
			pstmt.setString(param++, vo.getOpen_yn());
			pstmt.setString(param++, vo.getFile_id());
			System.out.println("file______________id::::"+vo.getFile_id());
			pstmt.setString(param++, searchVO.getLoginId());
			pstmt.setString(param++, searchVO.getSeq());
			
			pstmt.executeUpdate();
			pstmt.close();
			conn.commit();
			
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
	//��id��������
    public String questionID() throws SQLException, DAOBaseException {
        
        String seq = "";

        try {
            getConnection(dsname);
            
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
//            conn.commit();
            close();
        }
        return seq;
    }
    //�亯id��������
    public String answerID() throws SQLException, DAOBaseException {
        
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
//            conn.commit();
            close();
        }
        return seq;
    }
	//�����ϱ� ����
	public boolean offerDelete(OfferSearchVO searchVO) throws SQLException, DAOBaseException {
		boolean success = false;

		try {
			
			getConnection(dsname);
			String query = loadQueryString("sql.offer.question.delete");	  

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
	/**
	 * �����ϱ� SMS �߼��ϴ� �޼ҵ�
	 */
	public boolean sendSms(String seq, String type, String login_id) throws SQLException, DAOBaseException {
		
		boolean success = false;
		String send_mobile= "";
		String send_yn= "";
		String send_reg_nm= "";
		String send_reg_id= "";
		
		List SmsList = new ArrayList();
		HashMap hm = new HashMap();
		String msgText = "";
		String msg = "";
		String history_seq = "";
		int i =0;
		try {
			getConnection(dsname);
			
			String query = loadQueryString("sql.offer.question.view");
//			query += " AND Q.SEQ = A.Q_SEQ(+) ";
			query += " AND Q.SEQ = " + seq + " ";
			
			pstmt = conn.prepareStatement(query);
		    rs = pstmt.executeQuery();
		    
			while (rs.next()) {
				send_yn = rs.getString("CELL_RECEIVE_YN");
				send_mobile = rs.getString("CELL_NUMBER");
				send_reg_nm = rs.getString("REG_NM");
				send_reg_id = rs.getString("REG_ID");
				
				hm = new HashMap();
				hm.put("MOBILE", send_mobile.replaceAll("-",""));
				hm.put("USERNAME", send_reg_nm);
				hm.put("LOGIN_ID", send_reg_id);
										
				SmsList.add(i, hm);
				i++;
			}
			
			if(type.equals("I")){
	        	msg = "답변이 등록";
	        }else{
	        	msg = "답변이 수정";
	        }

			msgText = "『제안해주신 내용에  " +msg+ " 되었습니다.\n";
			msgText += "www.rndcall.go.kr \n R&D도우미센터』 "; // 본문
			
			if(send_yn.equals("Y")){
				SmsSend smsSend = new SmsSend();
				success = smsSend.SmsSendMain(SmsList, msgText);
			}
			
			// SMS �߼��̷µ��
			String sql = loadQueryString("sql.offer.question.getSendHistorySeq");
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				history_seq = rs.getString("ID");
			}
			rs.close();
			pstmt.close();
			String insert_sql = loadQueryString("sql.offer.question.getSendHistoryInsert");
			
			int param =1;
			
			openPreparedStatementForCUD(insert_sql);
						
			pstmt.setString(param++, history_seq);
			pstmt.setString(param++, "SMS");
			pstmt.setString(param++, "OFFER");
			pstmt.setString(param++, seq);
			pstmt.setString(param++, send_mobile);
			pstmt.setString(param++, "027248700");
			pstmt.setString(param++, "");
			pstmt.setString(param++, "");
			pstmt.setString(param++, msgText);
			pstmt.setString(param++, String.valueOf(success));
			pstmt.setString(param++, login_id);
			
			executeQueryForCUD();
			pstmt.close();			
			// SMS �߼��̷µ��
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			success = false;
		} finally {
			close();
		}

		return success;
	}
	
	/**
	 * �����ϱ� �̸��� �߼��ϴ� �޼ҵ�
	 */
	public boolean sendEmail(String seq, String type, String login_id) throws SQLException, DAOBaseException {
		boolean success = false;
		String send_email= "";
		String send_yn= "";
		String send_reg_nm= "";
		String send_reg_id= "";
		
		List SmsList = new ArrayList();
		HashMap hm = new HashMap();
		String msgText = "";
		String msg = "";
		String history_seq = "";
		String question_title = "";
		String send_cont = "";
		String send_ans_cont = "";
		String send_reg_dt = "";
		
		int i =0;
		try {
			getConnection(dsname);
			
			String query = loadQueryString("sql.offer.question.view");
//			query += " AND Q.SEQ = A.Q_SEQ(+) ";
			query += " AND Q.SEQ = '" + seq + "' ";
			
			pstmt = conn.prepareStatement(query);
		    rs = pstmt.executeQuery();
		    
			while (rs.next()) {
				question_title = rs.getString("TITLE");
				send_yn = rs.getString("EMAIL_RECEIVE_YN");
				send_email = rs.getString("EMAIL");
				send_reg_nm = rs.getString("REG_NM");
				send_reg_id = rs.getString("REG_ID");
				send_reg_dt = rs.getString("REG_DT");
				send_cont = rs.getString("CONTENTS").replaceAll("\n", "<br>");
				send_ans_cont = rs.getString("ANSWER_CONT").replaceAll("\n", "<br>");
				
				hm = new HashMap();
				hm.put("EMAIL", send_email);
				hm.put("USERNAME", send_reg_nm);
				hm.put("LOGIN_ID", send_reg_id);
										
				SmsList.add(i, hm);
				i++;
			}
			
			if(type.equals("I")){
	        	msg = "답변이 등록";
	        }else{
	        	msg = "답변이 수정";
	        }

			msgText = this.mailForm();
			System.out.println("EmailMsgText::"+msgText);
			
			String email_msg = "『안녕하세요. R&D도우미센터입니다.귀하께서 R&D도우미센터에 문의하신 내용에 대한 " +msg+ " 되었습니다. <br/>문의 및 답변은 동 센터 '마이페이지'에서도 확인이 가능합니다. 감사합니다.』";
			msgText = msgText.replaceAll("#TITLE#", question_title);
			msgText = msgText.replaceAll("#MSG#", email_msg);
			msgText = msgText.replaceAll("#REG_DT#", send_reg_dt);
			msgText = msgText.replaceAll("#REG_NM#", send_reg_nm);
			msgText = msgText.replaceAll("#CONTENT#", send_cont);
			msgText = msgText.replaceAll("#ANSWER_CONT#", send_ans_cont);
//			EmailMsgText = EmailMsgText.replaceAll("#server_ip#", "http://"+server_ip+":9998");
			msgText = msgText.replaceAll("#server_ip#", "http://rndcall.go.kr");
			msgText = msgText.replaceAll("#button#", "<img src='http://rndcall.go.kr/images/mail/Btn_Rndcall.gif' alt='도우미센터' border='0' />");
			
			if(send_yn.equals("Y")){
				MailSend MailSend = new MailSend();
				success = MailSend.MailSendMain(SmsList, msgText);
			}
			
			// SMS 
			String sql = loadQueryString("sql.offer.question.getSendHistorySeq");
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				history_seq = rs.getString("ID");
			}
			rs.close();
			pstmt.close();
			String insert_sql = loadQueryString("sql.offer.question.getSendHistoryInsert");
			
			int param =1;
			
			openPreparedStatementForCUD(insert_sql);
						
			pstmt.setString(param++, history_seq);
			pstmt.setString(param++, "EMAIL");
			pstmt.setString(param++, "OFFER");
			pstmt.setString(param++, seq);
			pstmt.setString(param++, "");
			pstmt.setString(param++, "");
			pstmt.setString(param++, send_email);
			pstmt.setString(param++, "mclaren225@naver.com");
			pstmt.setString(param++, msgText);
			pstmt.setString(param++, String.valueOf(success));
			pstmt.setString(param++, login_id);
			
			executeQueryForCUD();
			pstmt.close();			
			// SMS �߼��̷µ��
			success= true;
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			success = false;
		} finally {
			close();
		}

		return success;
	}
	/**
	 * �����ϱ� �̸��� ��
	 */
	public String mailForm() throws SQLException, DAOBaseException {

		String mail_form="";
		
		try {
			mail_form  = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'> \n";
			mail_form += "<html xmlns='http://www.w3.org/1999/xhtml'> \n";
			mail_form += "<head> \n";
			mail_form += "<meta http-equiv='Content-Type' content='text/html; charset=utf-8' /> \n";
			mail_form += "<title>국가과학기술위원회 R&amp;D도우미센터 메일</title> \n";
			mail_form += "</head> \n";
			mail_form += "<body style='padding:0; margin:0;'> \n";
			mail_form += "<br /> \n";
			mail_form += "<table border='0' cellspacing='0' cellpadding='0' style=' margin:0 auto; width:751px; border:2px #dbdbdb solid; font-size:12px;'> \n";
			mail_form += "<tr><td><img src='#server_ip#/images/mail/mail_Header.gif' alt='국가과학기술위원회 R&amp;D도우미센터 ' border='0' usemap='#Map' /></td></tr> \n";
			mail_form += "<tr><td style='font-size:13px; font-family:Gulim; line-height:20px; padding:20px 32px 20px 32px;'> \n";
			mail_form += "		#MSG# <br /> \n";
			mail_form += "	</td> \n";
			mail_form += "</tr> \n";
			mail_form += "<tr><td> \n";
			mail_form += "	<div style='width:701px; margin:0 auto; padding:0; color:#666;'> \n";
			mail_form += "		<img src='#server_ip#/images/mail/mail_Content_Top.gif' alt='Top Line' align='absbottom'  /> \n";
			mail_form += "		<div style='padding:0 15px 0 15px; border-left:2px #a7b6e4 solid; border-right:2px #a7b6e4 solid; '> \n";
			mail_form += "			<ul style='margin:0; padding:0; list-style:none; height:44px; border-bottom:1px #a7b6e4 solid;'> \n";
			mail_form += "				<li style='float:left; padding:20px 15px 0 0;'><img src='#server_ip#/images/mail/mail_Titleicon.gif' align='absmiddle' alt='아이콘'  /> <strong>제목 :</strong> #TITLE#</li> \n";
			mail_form += "				<li style='float:right; padding:20px 15px 0 0;'><img src='#server_ip#/images/mail/mail_Titleicon.gif' align='absmiddle' alt='아이콘'  /> <strong>작성일 :</strong> #REG_DT#</li> \n";
			mail_form += "				<li style='float:right; padding:20px 15px 0 0;'><img src='#server_ip#/images/mail/mail_Titleicon.gif' align='absmiddle' alt='아이콘' /> <strong>질문자 :</strong> #REG_NM# </li> \n";
			mail_form += "			</ul> \n";
			mail_form += "		</div> \n";
			mail_form += "		<div style='padding:30px; border-left:2px #a7b6e4 solid; border-right:2px #a7b6e4 solid; '> \n";
			mail_form += "			<ul style='margin:0; padding:0; list-style:none; line-height:18px;'> \n";
			mail_form += "				<li><font color='green'>질의내용</font></li> \n";
			mail_form += "				<li>#CONTENT#</li> \n";
			mail_form += "			</ul> \n";
			mail_form += "		</div> \n";
			mail_form += "		<div style='padding:30px; border-left:2px #a7b6e4 solid; border-right:2px #a7b6e4 solid; '> \n";
			mail_form += "			<ul style='margin:0; padding:0; list-style:none; line-height:18px;'> \n";
			mail_form += "				<li><font color='blue'>답변내용</font></li> \n";
			mail_form += "				<li>#ANSWER_CONT#</li> \n";
			mail_form += "			</ul> \n";
			mail_form += "		</div> \n";
			mail_form += "		<img src='#server_ip#/images/mail/mail_Content_Btm.gif' alt='Bottom Line' /> \n";
			mail_form += "		<li>보내는사람 사람 주소 : noreply@nstc.go.kr</li> \n";
			mail_form += "		<li>본 메일은 발신전용으로 회신되지 않습니다. 위 메일의 내용과 관련하여 문의사항이 있으실경우,";
			mail_form += "		www.rndcall.go.kr을 방문해 주시거나 02-724-8700으로 연락주시기 바랍니다.</li><br/> \n";
			mail_form += "	</div> \n";
			mail_form += "	</td> \n";
			mail_form += "</tr> \n";
			mail_form += "<tr><td style='text-align:center; padding:10px 0 20px 0;'> <a href='#server_ip#' target='_blank'>#button#</a></td></tr> \n";
			mail_form += "<tr><td style='border-top:3px #e4e4e4 solid;'><img src='#server_ip#/images/mail/mail_footer.gif' border='0' usemap='#Map2' /></td></tr> \n";
			mail_form += "</table> \n";
			mail_form += "<map name='Map' id='Map'> \n";
			mail_form += "	<area shape='rect' coords='31,2,177,41' href='http://www.rndcall.go.kr'  target='_blank' alt='국가과학기술위원회 R&amp;D도우미센터' /> \n";
			mail_form += "</map> \n";
			mail_form += "<map name='Map2' id='Map2'> \n";
			mail_form += "	<area shape='rect' coords='40,5,183,58' href='http://www.nstc.go.kr/nstc/index.jsp' target='_blank' alt='국가과학기술위원회' /> \n";
			mail_form += "	<area shape='rect' coords='208,9,295,27' href='http://www.rndcall.go.kr/sitetip.do?cmd=info' target='_blank' alt='개인정보처리방침' /> \n";
			mail_form += "	<area shape='rect' coords='312,9,413,26' href='http://www.rndcall.go.kr/sitetip.do?cmd=email' target='_blank' alt='무단이메일수집거부' /> \n";
			mail_form += "</map> \n";
			mail_form += "</body> \n";
			mail_form += "</html> \n";
			
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			System.out.println(e.getStackTrace());
			return null;
		} finally {
		}

		return mail_form;
	}
}
