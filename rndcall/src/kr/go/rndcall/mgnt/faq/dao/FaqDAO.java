package kr.go.rndcall.mgnt.faq.dao;

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
import kr.go.rndcall.mgnt.common.Util;
import kr.go.rndcall.mgnt.faq.vo.FaqAttachVO;
import kr.go.rndcall.mgnt.faq.vo.FaqResultVO;
import kr.go.rndcall.mgnt.faq.vo.FaqSearchVO;
import kr.go.rndcall.mgnt.faq.vo.FaqVO;
import kr.go.rndcall.mgnt.faq.vo.SatiVO;

public class FaqDAO extends BaseSqlDAO{

	private Logger logger = Logger.getLogger(this.getClass().getName());

	private String dsname = "jdbc/rndcall";
	
	
	//자료실 첨부파일 등록
	public void putAttach(FaqVO vo, AttachVO[] attachVO) throws SQLException, DAOBaseException {
		
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
		
		for (int i = 0; i < attachVO.length; i++) {
			if (attachVO[i] != null) {
				fileNMChar[i] =  attachVO[i].getFileNM();
				filePathChar[i] = attachVO[i].getFilePath();
				fileSizeChar[i] = attachVO[i].getFileSize();
				saveFileNMChar[i] = attachVO[i].getSaveFileNM();
			}
		}

		try {  
			// 커넥션을 맺는다.
			getConnection(dsname);
			
			if(vo.getFile_id().equals("")){
				String sql = loadQueryString("sql.faq.getFileSeq");
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					vo.setFile_id(rs.getString("ID"));
				}
				
				System.out.println("1____자료실수정 파일아이디?"+vo.getFile_id());
				rs.close();
				pstmt.close();
			}else{
				String sql = "SELECT MAX(SEQ) AS F_SEQ FROM RNDCALL_FILE WHERE FILE_ID='" +vo.getFile_id()+ "'";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
	
				if (rs.next()) {
					f_seq = rs.getInt("F_SEQ");
				}
				System.out.println("2____자료실수정 파일아이디?"+vo.getFile_id());
				rs.close();
				pstmt.close();
			}
			
			// Query 파일에서 쿼리를 읽어온다.   
			String query = loadQueryString("sql.faq.getAttachfileInsert");         
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
	//첨부파일 삭제하는 메소드
	public void getFileDelete(FaqVO vo, FaqSearchVO searchVO) throws SQLException, DAOBaseException {
		try {
			getConnection(dsname);
			//첨부파일 삭제
			String query = loadQueryString("sql.faq.question.getFileDelete");
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
    //FAQ와 답변을 등록하는 메소드
	public boolean faqInsert(FaqVO vo, FaqSearchVO searchVO) throws SQLException, DAOBaseException {

		StringBuffer sbQuery = null;		
		boolean success = false;
		String seq = "";
		
		try {
			getConnection(dsname);
			conn.setAutoCommit(false);
			
/*			String sql = loadQueryString("sql.faq.getSeq");
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				seq = rs.getString("ID");
			}
			rs.close();
			pstmt.close();*/
			
			String insert_sql = loadQueryString("sql.faq.faqinsert");
			
			int param =1;
			
			openPreparedStatementForCUD(insert_sql);
			
			pstmt.setString(param++, vo.getFaqSeq());
			pstmt.setString(param++, "FAQ");
			pstmt.setString(param++, vo.getTitle().replaceAll("'","′"));
			pstmt.setString(param++, vo.getContents());
			pstmt.setString(param++, vo.getCall_receive_yn());
			pstmt.setString(param++, vo.getCell_number());
			pstmt.setString(param++, vo.getEmail_receive_yn());
			pstmt.setString(param++, vo.getEmail());
			pstmt.setString(param++, vo.getOpen_yn());
			pstmt.setString(param++, "");
			pstmt.setString(param++, "");
			pstmt.setString(param++, "WEB");
			pstmt.setString(param++, "");
			pstmt.setString(param++, vo.getFile_id());
			pstmt.setString(param++, "N");
			pstmt.setString(param++, searchVO.getLoginId());
			pstmt.setString(param++, searchVO.getName());
			pstmt.setString(param++, vo.getCategory1());
            pstmt.setString(param++, vo.getCategory2());
            pstmt.setString(param++, vo.getAnalysis_yn());
			
			executeQueryForCUD();
			pstmt.close();
			conn.commit();
			
			success= true;			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			System.out.println("Error e.toString() is " + e.toString());
			success = false;
		}
		String seq1 = "";
		
		try {
//			getConnection(dsname);
//			conn.setAutoCommit(false);
			
			String sql = loadQueryString("sql.faq.getFaqSeq");
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				seq1 = rs.getString("ID");
			}
			rs.close();
			pstmt.close();
			
			String insert_sql = loadQueryString("sql.faq.faqAnswerInsert");
			
			int param = 1;
			
			openPreparedStatementForCUD(insert_sql);
			
			pstmt.setString(param++, seq1);
			pstmt.setString(param++, vo.getFaqSeq());
			pstmt.setString(param++, vo.getTitle());
			pstmt.setString(param++, searchVO.getLoginId());
			pstmt.setString(param++, vo.getAnswer_cont());
			
			executeQueryForCUD();
			pstmt.close();
			conn.commit();
			
			success= true;			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			System.out.println("Error e.toString() is " + e.toString());
			success = false;
		} finally {
			conn.setAutoCommit(true);
			close();
		}
		System.out.println("success is " + success);
		
		return success;
	}
	//자료실 첨부파일을 조회하는 메소드
	public FaqResultVO getFileInfo(String file_id) throws SQLException, DAOBaseException {


		ArrayList voList = new ArrayList();

		FaqResultVO resultVO = new FaqResultVO();
		FaqAttachVO fileVo = null;
		DesCipher dc = new DesCipher();
		
		try {
			getConnection(dsname);
			
			String query = loadQueryString("sql.faq.question.fileinfo");
			openPreparedStatementForR(query, false);
			
			pstmt.setString(1, file_id) ;
			
			executeQueryForR();				
			
			while (this.rs.next()) {
				fileVo = new FaqAttachVO();
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
    //FAQ리스트를 조회하는 메소드
	public FaqResultVO faqList(FaqVO vo, FaqSearchVO searchVO) throws SQLException, DAOBaseException {
		FaqResultVO resultVO = new FaqResultVO();

		String boardType = "FAQ";
		ArrayList voList = new ArrayList();
//		Reader content_clob = null;
		String query = "";
		try {
			getConnection(dsname);
			query = loadQueryString("sql.faq.faqList");
			query += " AND A.BOARD_TYPE='" + boardType + "'";
			if(!searchVO.getSearchCategory().equals("") && !searchVO.getSearchCategory().equals("all")){
				query += " AND A.CATEGORY1 = '"+searchVO.getSearchCategory()+"' ";
			}else if(searchVO.getSearchCategory().equals("all")){
				query += " AND A.BOARD_TYPE='" + boardType + "'";
			}
			if(searchVO.getWhichSearch().equals("title") && !searchVO.getSearchTxt().equals("")){					
				query = new StringBuffer(query).append(" AND a.title LIKE '%").append(searchVO.getSearchTxt()).append("%' ").toString();
			} 
			else if(searchVO.getWhichSearch().equals("contents") && !searchVO.getSearchTxt().equals("")){ 
				query = new StringBuffer(query).append(" AND CLOB_TO_CHAR(A.CONTENTS) LIKE '%").append(searchVO.getSearchTxt()).append("%' ").toString();
			}
			else if(searchVO.getWhichSearch().equals("all") && !searchVO.getSearchTxt().equals("")){ 
				query = new StringBuffer(query).append(" AND (CLOB_TO_CHAR(A.CONTENTS) LIKE '%").append(searchVO.getSearchTxt()).append("%' ")
				.append(" or A.title LIKE '%").append(searchVO.getSearchTxt()).append("%' ")
			 	.append(" OR CLOB_TO_CHAR(B.ANSWER_CONT) LIKE '%").append(searchVO.getSearchTxt()).append("%' ) ")
			 	.toString();
			}
			
			query += " ORDER BY A.REG_DT DESC";
			openPreparedStatementForR(query, true);
			executeQueryForR(searchVO);
			while(rs.next()) {
				vo = new FaqVO();
				vo.setSeq(rs.getString("seq"));
				vo.setTitle(rs.getString("title"));
				vo.setContents(rs.getString("contents"));
				vo.setRead_count(rs.getInt("read_count"));
				vo.setCategory1(rs.getString("category1"));
				vo.setCategory2(rs.getString("category2"));
				vo.setStat(rs.getString("stat"));
				vo.setReg_dt(rs.getString("reg_dt"));
				vo.setReg_nm(rs.getString("reg_nm"));
				vo.setAnswer_cont(rs.getString("answer_cont"));
				vo.setAnalysis_yn(rs.getString("ANALYSIS_YN"));
				voList.add(vo);
			}
			resultVO.setTotRowCount(searchVO.getTotRowCount());
			resultVO.setVoList(voList);
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			return null;
		} finally {
			close();
		}		
		return resultVO;
	}
    //FAQ상세보기를 하는 메소드
	public FaqResultVO faqDetailView(FaqVO vo, FaqSearchVO searchVO) throws SQLException, DAOBaseException {
		FaqResultVO resultVO = new FaqResultVO();
		
//		Reader content_clob = null;
//		Reader answerContent_clob = null;
		String seq_temp = searchVO.getSeq();
		int seq = Integer.parseInt(seq_temp);
		ArrayList voList = new ArrayList();
		String query = "";
		try {
			getConnection(dsname);
			query = loadQueryString("sql.faq.faqDetailList");
			//query += " and a.seq = b.q_seq(+)";
			//query += " and A.FILE_ID = F.FILE_ID(+)";
			query += " and a.seq = '" + searchVO.getSeq() +"'";
			
			System.out.println("Query ::::::::::::: " + query);
			
			pstmt = conn.prepareStatement(query);
		    rs = pstmt.executeQuery();
			//query = new StringBuffer(query).append("order by reg_dt").toString();
			//openPreparedStatementForR(query, true);
			//pstmt.setInt(1, seq);
			//executeQueryForR(searchVO);
			if(rs.next()) {
				vo = new FaqVO();
				vo.setSeq(rs.getString("seq"));
				vo.setTitle(rs.getString("title"));
//				content_clob = rs.getCharacterStream("CONTENTS");
//				vo.setContents(Util.streamToBuf(content_clob).toString());
				vo.setContents(rs.getString("CONTENTS"));
				if (vo.getContents() != null && !vo.getContents().equals("")) {
					vo.setContents(vo.getContents().replaceAll("\n", "<br>"));
				}
				
				vo.setBoard_type(rs.getString("board_type"));
				vo.setReg_dt(rs.getString("reg_dt"));
				vo.setAnswer_cont(rs.getString("answer_cont"));
				
//				answerContent_clob = rs.getCharacterStream("answer_cont");
//				vo.setAnswer_cont(Util.streamToBuf(answerContent_clob).toString());
				if (vo.getAnswer_cont() != null && !vo.getAnswer_cont().equals("")) {
					vo.setAnswer_cont(vo.getAnswer_cont().replaceAll("\n", "<br>"));	
				}
				vo.setFile_id(rs.getString("FILE_ID"));
				vo.setCategory1(rs.getString("CATEGORY1"));
				vo.setCategory2(rs.getString("CATEGORY2"));
				vo.setAnalysis_yn(rs.getString("ANALYSIS_YN"));
				
			}
			resultVO.setVo(vo);

			String query1 = loadQueryString("sql.faq.faqReadcount");
			openPreparedStatementForCUD(query1);
				pstmt.setString(1, searchVO.getSeq());
				pstmt.execute();
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			return null;
		} finally {
			close();
		}
		return resultVO;
	}
    //만족도 평가를 조회하는 메소드
	public FaqResultVO getSatiInfo(String seq, String login_id) throws SQLException, DAOBaseException {


		FaqResultVO resultVO = new FaqResultVO();
		SatiVO satiVO = new SatiVO();
		
		System.out.println("seq::"+seq);
		System.out.println("login_id::"+login_id);
		
		
		try {
			getConnection(dsname);
			
			String query = loadQueryString("sql.faq.pointRetrieve");
			openPreparedStatementForR(query, false);
			
			pstmt.setString(1, seq) ;
			
			executeQueryForR();				
			
			if (this.rs.next()) {
				satiVO.setAvg_app_point(rs.getString("AVG_APP_POINT"));		
			}
			
			query = loadQueryString("sql.faq.CountRetrieve");
			openPreparedStatementForR(query, false);
			
			pstmt.setString(1, seq) ;
			
			executeQueryForR();				
			
			if (this.rs.next()) {
				satiVO.setSati_reg_cnt(rs.getString("SATI_REG_CNT"));		
			}
			
			
			query = loadQueryString("sql.faq.createYN");
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
    //만족도를 등록하는 메소드
	public boolean faqSatiInsert(FaqVO vo, FaqSearchVO searchVO, SatiVO satiVO) throws SQLException, DAOBaseException {
		boolean success = false;
		String seq= "";
		

		try {
			getConnection(dsname);
			
			String sql = loadQueryString("sql.faq.getSatiSeq");
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				seq = rs.getString("ID");
			}
			rs.close();
			pstmt.close();
			
			String insert_sql = loadQueryString("sql.faq.satiInsert");
			
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

    //만족도를 평가하는 메소드
	public boolean getSatiInsert(FaqSearchVO searchVO) throws SQLException, DAOBaseException {
		boolean success = false;

		try {
			
			success= true;
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			success = false;
		} finally {
			close();
		}

		return success;
	}
	
    //등록할시 대분류 코드를 가져오기위한 메소드
    public FaqResultVO retrieveCategory1Code(FaqSearchVO searchVO) throws SQLException, DAOBaseException {
    	FaqResultVO resultVO = new FaqResultVO();
    	FaqVO vo = null;
        ArrayList voList = new ArrayList();
        try {
            getConnection(dsname);
            String query = loadQueryString("sql.offlineData.retrieveCategory1Code");
            openPreparedStatementForR(query,  false);
            executeQueryForR();
            while(rs.next()) {
                vo = new FaqVO();
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
    
    //등록할시 소분류 코드를 가져오기위한 메소드
    public FaqResultVO retrieveCategory2Code(FaqSearchVO searchVO) throws SQLException, DAOBaseException {
    	FaqResultVO resultVO = new FaqResultVO();
    	FaqVO vo = null;
        int param = 1;
        ArrayList voList = new ArrayList();
        try {
            getConnection(dsname);
            String query = loadQueryString("sql.offlineData.retrieveCategory2Code");
            openPreparedStatementForR(query,  false);  

            executeQueryForR();
            while(rs.next()) {
                vo = new FaqVO();
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
    //SEQ_ID를 가져오기 위한 메소드
    public String getFaqSeqID() throws SQLException, DAOBaseException {
        
        String seq = "";

        try {
            getConnection(dsname);
            
            String sql_create1="SELECT RNDCALL_QUESTION_SEQ.NEXT_VALUE AS ID" ;
            openPreparedStatementForR(sql_create1,   false);
            executeQueryForR(); //

            while (this.rs.next()) {
                seq = rs.getString("ID") ;
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
    //FAQ정보를 수정하기위해 정보를 불러온다.
	public FaqResultVO faqContentConfirm(FaqVO vo, FaqSearchVO searchVO) throws SQLException, DAOBaseException {


		//FaqVO vo = new FaqVO();;
//		ArrayList voList = new ArrayList();
		FaqResultVO resultVO = new FaqResultVO();
//		Reader content_clob = null;
//		Reader answerContent_clob = null;
		
		try {
			getConnection(dsname);

			String query = loadQueryString("sql.faq.faqUpdateRetrieve");
			query += " where A.BOARD_TYPE = '" + searchVO.getBoard_type() +"'";
//			query += " AND A.SEQ = B.Q_SEQ(+) ";
//			query += " and A.FILE_ID = F.FILE_ID(+) ";
			query += " AND A.SEQ = '" + searchVO.getSeq() +"'";
			pstmt = conn.prepareStatement(query);
		    rs = pstmt.executeQuery();
			
		    if (rs.next()) {
				
				vo.setSeq(rs.getString("SEQ"));				
				vo.setTitle(rs.getString("TITLE"));
				vo.setRead_count(rs.getInt("read_count"));
				vo.setCategory1(rs.getString("CATEGORY1"));
				vo.setCategory2(rs.getString("CATEGORY2"));
				vo.setStat(rs.getString("STAT"));
				vo.setReg_dt(rs.getString("REG_DT"));
				vo.setReg_nm(rs.getString("REG_NM"));
				vo.setAnalysis_yn(rs.getString("ANALYSIS_YN"));
				vo.setBoard_type(rs.getString("board_type"));
//				content_clob = rs.getCharacterStream("CONTENTS");
//				vo.setContents(Util.streamToBuf(content_clob).toString());
				vo.setContents(rs.getString("CONTENTS"));
				vo.setAnswerContents(rs.getString("ANSWER_CONT"));
//				answerContent_clob = rs.getCharacterStream("ANSWER_CONT");
//				vo.setAnswer_cont(Util.streamToBuf(answerContent_clob).toString());	
				vo.setFile_id(rs.getString("FILE_ID"));
			}
			
			resultVO.setVo(vo);			
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
	//FAQ를 수정한다.
	public boolean faqUpdate(FaqVO vo, FaqSearchVO searchVO) throws SQLException, DAOBaseException {
//		StringBuffer sbQuery = null;		
		boolean success = false;
//		String seq = "";
		
		try {
			getConnection(dsname);
			conn.setAutoCommit(false);
	    		    	
	        int param = 1;
	    	String query = loadQueryString("sql.faq.faqUpdate");	    	
	    	
	    	System.out.println("query::"+query);
	    	pstmt = conn.prepareStatement(query);
	    	pstmt.setString(param++, vo.getTitle().replaceAll("'","′"));
			pstmt.setString(param++, vo.getContents().replaceAll("'","′"));
			pstmt.setString(param++, searchVO.getLoginId());
			pstmt.setString(param++, vo.getFile_id());
			pstmt.setString(param++, vo.getAnalysis_yn());
			pstmt.setString(param++, searchVO.getSeq());
			
			pstmt.executeUpdate();
			pstmt.close();
//			conn.commit();
			
			conn.commit();
			success= true;
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			System.out.println(e.getStackTrace());
			success = false;
		} 
		try {
//			getConnection(dsname);
//			conn.setAutoCommit(false);
			
			String insert_sql = loadQueryString("sql.faq.faqUpdateAnswer");
			
			int param = 1;
			
			openPreparedStatementForCUD(insert_sql);
			
			//pstmt.setString(param++, "FAQ");
	    	pstmt.setString(param++, vo.getTitle().replaceAll("'","′"));
	    	pstmt.setString(param++, vo.getAnswerContents().replaceAll("'","′"));
			pstmt.setString(param++, searchVO.getLoginId());
			pstmt.setString(param++, searchVO.getSeq());
			
			executeQueryForCUD();
			pstmt.close();
//			conn.commit();
			
			conn.commit();
			
			success= true;			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			System.out.println("Error e.toString() is " + e.toString());
			success = false;
		} finally {
			conn.setAutoCommit(true);
			close();
		}
		System.out.println("success is " + success);
		
		return success;
	}
	//FAQ를 삭제한다.
	public boolean faqDelete(FaqSearchVO searchVO) throws SQLException, DAOBaseException {
		boolean success = false;

		try {
			
			getConnection(dsname);
			String query = loadQueryString("sql.faq.faqDelete");	  
			pstmt = conn.prepareStatement(query);	
			pstmt.setString(1, searchVO.getSeq());
			pstmt.executeUpdate();
			
			success= true;
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			success = false;
		}
		try {
			
			getConnection(dsname);
			String query = loadQueryString("sql.faq.faqDeleteAnswer");	  
			
			pstmt = conn.prepareStatement(query);	
			pstmt.setString(1, searchVO.getSeq());
			pstmt.executeUpdate();
			
			success= true;
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			success = false;
		}
		
		finally {
			close();
		}

		return success;
	}
    //sms발송
	public boolean smsCommit(FaqVO vo, FaqSearchVO searchVO) throws SQLException, DAOBaseException {
		boolean success = false;
		String history_seq = "";
		
		List SmsList = new ArrayList();
		HashMap hm = new HashMap();
		String msgText = "";
		int i =0;
		try {
			getConnection(dsname);
			
			System.out.println("vo.getCellNum()::"+vo.getCellNum());
			
			
			String[] cell_num = vo.getCellNum().split("\n");
			System.out.println("cell_num.length::"+cell_num.length);
			
			for(int j=0; j < cell_num.length; j++){
			
				String sql = loadQueryString("sql.offer.question.getSendHistorySeq");
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
	
				if (rs.next()) {
					history_seq = rs.getString("ID");
				}
				rs.close();
				pstmt.close();
				
				String insert_sql = loadQueryString("sql.faq.question.getSendHistoryInsertCellPhone");
				
				int param =1;
				
				openPreparedStatementForCUD(insert_sql);
				
				pstmt.setString(param++, history_seq);
				pstmt.setString(param++, "SMS");
				pstmt.setString(param++, "FAQ");
				pstmt.setString(param++, "027248700");
				pstmt.setString(param++, cell_num[j]);
				pstmt.setString(param++, vo.getSmsCont());
				pstmt.setString(param++, searchVO.getLoginId());
				
				executeQueryForCUD();
				pstmt.close();
				
				hm = new HashMap();
				hm.put("MOBILE", cell_num[j].replaceAll("-",""));
				hm.put("USERNAME", "관리자");
				hm.put("LOGIN_ID", searchVO.getLoginId());
										
				SmsList.add(i, hm);
				i++;
			}
			
			
			msgText = vo.getSmsCont(); // 본문
			SmsSend smsSend = new SmsSend();
			success = smsSend.SmsSendMain(SmsList, msgText);
				
				
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			success = false;
		} finally {
			close();
		}

		return success;
	}
	//sms발송
	public boolean emailCommit(FaqVO vo, FaqSearchVO searchVO, String server_ip) throws SQLException, DAOBaseException {
		boolean success = false;
		String history_seq = "";
		
		List SmsList = new ArrayList();
		HashMap hm = new HashMap();
		String msgText = "";
		int i =0;
		try {
			getConnection(dsname);
			
			System.out.println("vo.getCellNum()::"+vo.getCellEmail());
			
			
			String[] cell_email = vo.getCellEmail().split("\n");
			System.out.println("cell_num.length::"+cell_email.length);
			
			for(int j=0; j < cell_email.length; j++){
				
				String sql = loadQueryString("sql.offer.question.getSendHistorySeq");
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					history_seq = rs.getString("ID");
				}
				rs.close();
				pstmt.close();
				
				String insert_sql = loadQueryString("sql.faq.question.getSendHistoryInsert");
				
				int param =1;
				
				openPreparedStatementForCUD(insert_sql);
				
				pstmt.setString(param++, history_seq);
				pstmt.setString(param++, "email");
				pstmt.setString(param++, "FAQ");
				pstmt.setString(param++, "mclaren225@naver.com");
				pstmt.setString(param++, cell_email[j]);
				pstmt.setString(param++, vo.getSmsCont());
				pstmt.setString(param++, searchVO.getLoginId());
				
				executeQueryForCUD();
				pstmt.close();
				
				hm = new HashMap();
				hm.put("EMAIL", cell_email[j].replaceAll("-",""));
				hm.put("USERNAME", "관리자");
				hm.put("LOGIN_ID", searchVO.getLoginId());
				
				SmsList.add(i, hm);
				i++;
			}
			msgText = this.mailForm(server_ip);
			
			//msgText = "유권해석이 등록되었습니다.\n";
			//msgText += "<a href='http://www.rndcall.go.kr'>도우미센터</a>에 접속하여 확인 가능합니다. \n -R&D도우미센터- "; // 본문
			
			//msgText = vo.getSmsCont(); // 본문
			MailSend MailSend = new MailSend();
			success = MailSend.MailSendMain(SmsList, msgText);
			
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			success = false;
		} finally {
			close();
		}
		
		return success;
	}
	/**
	 * 제안하기 SMS 발송하는 메소드
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
			query += " AND Q.SEQ = '" + seq + "' ";
			
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

			msgText = "문의하신 질의에  " +msg+ " 되었습니다.\n";
			msgText += "www.rndcall.go.kr \n -R&D도우미센터- "; // 본문
			
			if(send_yn.equals("Y")){
				SmsSend smsSend = new SmsSend();
				success = smsSend.SmsSendMain(SmsList, msgText);
			}
			
			// SMS 발송이력등록
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
			// SMS 발송이력등록
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			success = false;
		} finally {
			close();
		}

		return success;
	}
    //FAQ부처담당자 리스트 조회
	public FaqResultVO orgTelNum(FaqVO vo, FaqSearchVO searchVO) throws SQLException, DAOBaseException {
		FaqResultVO resultVO = new FaqResultVO();

		String boardType = "FAQ";
		ArrayList voList = new ArrayList();

		String query = "";
		try {
			getConnection(dsname);
			query = loadQueryString("sql.faq.question.orgUserinfo");
			
			query += " ORDER BY ORG_NM";
			openPreparedStatementForR(query, true);
			executeQueryForR(searchVO);
			while(rs.next()) {
				vo = new FaqVO();
				vo.setAuth_id(rs.getString("AUTH_ID"));
				vo.setUser_id(rs.getString("USER_ID"));
				vo.setOrg_nm(rs.getString("ORG_NM"));
				vo.setAuth(rs.getString("AUTH"));
				vo.setTel(rs.getString("TEL"));
				vo.setName(rs.getString("NAME"));
				vo.setEmail(rs.getString("EMAIL"));
				voList.add(vo);
			}
			resultVO.setTotRowCount(searchVO.getTotRowCount());
			resultVO.setVoList(voList);
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			return null;
		} finally {
			close();
		}		
		return resultVO;
	}
	public String mailForm(String server_ip) throws SQLException, DAOBaseException {

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
			mail_form += "<tr><td><img src='http://rndcall.go.kr/images/mail/mail_Header.gif' border='0' usemap='#Map' /></td></tr> \n";
			mail_form += "<tr><td style='font-size:13px; font-family:Gulim; line-height:20px; padding:20px 32px 20px 32px;'> \n";
			mail_form += "		유권해석이 등록되었습니다. <br /> \n";
			mail_form += "		<a href='http://www.rndcall.go.kr'>도우미센터</a>에 접속하여 확인하십시오.<br />";
			mail_form += "		- R&D도우미센터 -";
			mail_form += "	</td> \n";
			mail_form += "</tr> \n";
			mail_form += "<tr><td style='border-top:3px #e4e4e4 solid;'><img src='http://rndcall.go.kr/images/mail/mail_footer.gif' border='0' usemap='#Map2' /></td></tr> \n";
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