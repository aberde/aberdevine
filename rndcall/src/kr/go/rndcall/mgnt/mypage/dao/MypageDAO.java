package kr.go.rndcall.mgnt.mypage.dao;

import java.io.Reader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;
import org.apache.log4j.Logger;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.CHAR;
import oracle.sql.CLOB;


import kr.go.rndcall.mgnt.common.BaseSqlDAO;
import kr.go.rndcall.mgnt.common.DAOBaseException;
import kr.go.rndcall.mgnt.common.Util;
import kr.go.rndcall.mgnt.common.DesCipher;

import kr.go.rndcall.mgnt.mypage.vo.SatiVO;
import kr.go.rndcall.mgnt.mypage.vo.MypageAttachVO;
import kr.go.rndcall.mgnt.mypage.vo.MypageResultVO;
import kr.go.rndcall.mgnt.mypage.vo.MypageSearchVO;
import kr.go.rndcall.mgnt.mypage.vo.MypageVO;
import kr.go.rndcall.mgnt.common.AttachVO;

public class MypageDAO extends BaseSqlDAO{

	private Logger logger = Logger.getLogger(this.getClass().getName());

	private String dsname = "jdbc/oracleRND";
	
	/**
	 * 공지사항 리스트를 조회하는 메소드
	 */
	public MypageResultVO getMypageList(MypageSearchVO searchVO) throws SQLException, DAOBaseException {


		MypageVO vo = null;
		MypageVO statVo = null;
		ArrayList voList = new ArrayList();

		MypageResultVO resultVO = new MypageResultVO();
		try {
			getConnection(dsname);
			
			String query = "";
			//리스트 조회
			if(searchVO.getType().equals("1")){ //전체
				query = loadQueryString("sql.mypage.list2");
			}else if(searchVO.getType().equals("2")){ //미답변
				query = loadQueryString("sql.mypage.list2");
				query += " AND NOT EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER AA WHERE Q.SEQ = AA.Q_SEQ) ";
			}else if(searchVO.getType().equals("3")){ //답변완료
				query = loadQueryString("sql.mypage.list");
				query += " AND Q.SEQ = A.Q_SEQ ";
			}else if(searchVO.getType().equals("4")){ //스크랩
				query = loadQueryString("sql.mypage.listscrap");
			}
			
			if(searchVO.getType().equals("4")){
				query += " AND S.REG_ID='" + searchVO.getLoginId() + "'";
			}else{
				query += " AND Q.REG_ID='" + searchVO.getLoginId() + "'";
			}
			
			if(searchVO.getBoard_type().equals("")){
				query += " AND Q.BOARD_TYPE in ('QNA','OFFER')";
			}else{
				query += " AND Q.BOARD_TYPE = '" + searchVO.getBoard_type() + "'";
			}
			
			if(!searchVO.getWhichSearch().equals("") && !searchVO.getSearchTxt().equals("")){
				if(searchVO.getWhichSearch().equals("title")){
					query += " AND Q.TITLE like '%"+searchVO.getSearchTxt()+"%' ";
				}else if(searchVO.getWhichSearch().equals("contents")){
					query += " AND CLOB_TO_CHAR(Q.CONTENTS) like '%"+searchVO.getSearchTxt()+"%' ";
					
				}
			}
			
			query += " ORDER BY Q.REG_DT DESC";
			
			openPreparedStatementForR(query, true);
			executeQueryForR(searchVO);
			
			while (rs.next()) {
				vo = new MypageVO();
				vo.setSeq(rs.getString("SEQ"));
				vo.setTitle(rs.getString("TITLE"));
				vo.setBoard_type(rs.getString("BOARD_TYPE"));
				vo.setCategory1(rs.getString("CATEGORY1"));
				vo.setReg_dt(rs.getString("REG_DT1"));
				vo.setReg_nm(rs.getString("REG_NM"));
				vo.setRead_count(rs.getInt("READ_COUNT"));
				vo.setStat(rs.getString("STAT"));
				vo.setAnswer_reg_dt(rs.getString("ANSWER_REG_DT"));
				
				voList.add(vo);
			}
			
			resultVO.setTotRowCount(searchVO.getTotRowCount());
			resultVO.setVoList(voList);
			
			
			// 마이페이지  Q&A 현황조회 정보
			query = loadQueryString("sql.mypage.qnaStatInfo");			
			openPreparedStatementForR(query, false);
			
			pstmt.setString(1, searchVO.getLoginId()) ;
			pstmt.setString(2, searchVO.getLoginId()) ;
			pstmt.setString(3, searchVO.getLoginId()) ;
			pstmt.setString(4, searchVO.getLoginId()) ;
			executeQueryForR();

			statVo = new MypageVO();
			if(rs.next()) {
				statVo.setStatCnt1(rs.getString("CNT1"));
				statVo.setStatCnt2(rs.getString("CNT2"));
				statVo.setStatCnt3(rs.getString("CNT3"));
				statVo.setStatCnt7(rs.getString("CNT4"));
			}
			
			// 마이페이지 제안하기 현황조회 정보
			query = loadQueryString("sql.mypage.proposalStatInfo");
			openPreparedStatementForR(query, false);
			
			pstmt.setString(1, searchVO.getLoginId()) ;
			pstmt.setString(2, searchVO.getLoginId()) ;
			pstmt.setString(3, searchVO.getLoginId()) ;			
			executeQueryForR();

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
	
	
	/**
	 * 공지사항 상세정보를 조회하는 메소드
	 */
	public MypageResultVO getMypageView(MypageSearchVO searchVO) throws SQLException, DAOBaseException {


		MypageVO vo = null;
		ArrayList voList = new ArrayList();
		Reader content_clob = null;
		Reader answerContent_clob = null;
		MypageResultVO resultVO = new MypageResultVO();
		String query = "";

		try {
			getConnection(dsname);

			if(searchVO.getScrap().equals("SCRAP")){
				query = loadQueryString("sql.mypage.viewscrap");
				query += " AND S.REG_ID= '" + searchVO.getLoginId() +"'";
				query += " AND Q.SEQ = '" + searchVO.getSeq() +"'";
			}else{
				query = loadQueryString("sql.mypage.view");
				query += " AND Q.BOARD_TYPE= '" + searchVO.getBoard_type() +"'";
				query += " AND Q.SEQ = '" + searchVO.getSeq() +"'";
			}
			System.out.println("상세조회 실행 쿼리문::"+query);
				
			pstmt = conn.prepareStatement(query);
		    rs = pstmt.executeQuery();
				
			    
			if (rs.next()) {
				vo = new MypageVO();
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
				vo.setReg_dt(rs.getString("REG_DT1"));
				vo.setReg_nm(rs.getString("REG_NM"));
				vo.setRead_count(rs.getInt("READ_COUNT"));
//				content_clob = rs.getCharacterStream("CONTENTS");
//				vo.setContents(Util.streamToBuf(content_clob).toString());
				vo.setContents(rs.getString("CONTENTS"));					
				System.out.println(">>>>>>>>>>>>>>>>>>" + rs.getString("CONTENTS"));
				if(searchVO.getFlag().equals("U")){
					vo.setContents(rs.getString("CONTENTS"));					
				}else{
					vo.setContents(vo.getContents().replaceAll("\n", "<br>"));					
				}
//				answerContent_clob = rs.getCharacterStream("ANSWER_CONT");
//				vo.setAnswerContents(Util.streamToBuf(answerContent_clob).toString());
				vo.setAnswerContents(rs.getString("ANSWER_CONT"));
				
				if(searchVO.getFlag().equals("U")){
					vo.setAnswerContents(vo.getAnswerContents());		
				}else{
					if (vo.getAnswerContents() != null && vo.getAnswerContents().equals("")) {
						vo.setAnswerContents(vo.getAnswerContents().replaceAll("\n", "<br>"));
					}
				}
				vo.setQuestion_file_id(rs.getString("QUESTION_FILE_ID"));
				vo.setUp_del_stat(rs.getString("UP_DEL_STAT"));
				vo.setStat(rs.getString("STAT"));
				vo.setAnswer_seq(rs.getString("ANSWER_SEQ"));
				vo.setPublic_trans_yn(rs.getString("PUBLIC_TRANS_YN"));
				vo.setAnswer_file_id(rs.getString("ANSWER_FILE_ID"));
				if(searchVO.getScrap().equals("SCRAP")){
					vo.setScrap_id(rs.getString("SCRAP_ID"));
					vo.setRegdt_scrap(rs.getString("REGDT_SCRAP"));
				}
			}
			
			System.out.println("DAO seq ="+vo.getSeq());
			resultVO.setVo(vo);	
			pstmt.close();
			conn.close();
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			System.out.println(e.getStackTrace());
			return null;
		} finally {
			close();
		}

		return resultVO;
	}
	
	/**
	 * Q&A 첨부파일을 조회하는 메소드
	 */
	public MypageResultVO getFileInfo(String file_id) throws SQLException, DAOBaseException {


		ArrayList voList = new ArrayList();

		MypageResultVO resultVO = new MypageResultVO();
		MypageAttachVO fileVo = null;
		DesCipher dc = new DesCipher();
		
		try {
			getConnection(dsname);
			
			String query = loadQueryString("sql.mypage.fileinfo");
			openPreparedStatementForR(query, false);
			
			pstmt.setString(1, file_id) ;
			
			executeQueryForR();				
			
			while (this.rs.next()) {
				fileVo = new MypageAttachVO();
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

	/**
	 * Q&A 수정하는 메소드
	 */
	public boolean getMypageUpdate(MypageVO vo, MypageSearchVO searchVO) throws SQLException, DAOBaseException {
		StringBuffer sbQuery = null;		
		boolean success = false;
		String seq = "";
		
		try {
			getConnection(dsname);
			conn.setAutoCommit(false);
	    		    	
	        int param = 1;
	    	String query = loadQueryString("sql.mypage.update");	    	
	    	
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
			pstmt.setString(param++, searchVO.getLoginId());
			pstmt.setString(param++, searchVO.getSeq());
			
			pstmt.executeUpdate();
			pstmt.close();
//			conn.commit();
			
			// 질의내용 clob컬럼을 가져온다.			
//			try{
//				sbQuery = new StringBuffer();
//				sbQuery.append("SELECT CONTENTS FROM RNDCALL_BOARD_QUESTION WHERE SEQ = '").append(searchVO.getSeq()).append("' for update");
//
//				pstmt = conn.prepareStatement(sbQuery.toString());
//				rs = pstmt.executeQuery();			
//				// contents 컬럼을 업데이트 한다.
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
	
	/**
	 * 마이페이지 삭제하는 메소드
	 */
	public boolean getMypageDelete(MypageSearchVO searchVO) throws SQLException, DAOBaseException {
		boolean success = false;

		try {
			
			getConnection(dsname);
			String query = loadQueryString("sql.mypage.delete");	  

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
	 * Q&A 만족도평가 조회하는 메소드
	 */
	public MypageResultVO getSatiInfo(String seq, String login_id) throws SQLException, DAOBaseException {


		MypageResultVO resultVO = new MypageResultVO();
		SatiVO satiVO = new SatiVO();
				
		try {
			getConnection(dsname);
			
			System.out.println("seq is " + seq);
			System.out.println("login_id " + login_id);
			
			String query = loadQueryString("sql.inquire.question.satiInfo.avgAppPoint");
			openPreparedStatementForR(query, false);
			
			pstmt.setString(1, seq) ;
			
			executeQueryForR();				
			
			if (this.rs.next()) {
				satiVO.setAvg_app_point(rs.getString("AVG_APP_POINT"));		
			}
			
			query = loadQueryString("sql.inquire.question.satiInfo.satiRegCnt");
			openPreparedStatementForR(query, false);
			
			pstmt.setString(1, seq) ;
			
			executeQueryForR();				
			
			if (this.rs.next()) {
				satiVO.setSati_reg_cnt(rs.getString("SATI_REG_CNT"));		
			}
			
			
			query = loadQueryString("sql.inquire.question.satiInfo.satiRegYn");
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
	
	/**
	 * Q&A 만족도평가 등록하는 메소드
	 */
	public boolean getSatiInsert(MypageVO vo, MypageSearchVO searchVO, SatiVO satiVO) throws SQLException, DAOBaseException {
		boolean success = false;
		String seq= "";
		

		try {
			getConnection(dsname);
			
			String sql = loadQueryString("sql.inquire.getSatiSeq");
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				seq = rs.getString("ID");
			}
			rs.close();
			pstmt.close();
			
			String insert_sql = loadQueryString("sql.inquire.question.satiInsert");
			
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
	
//	첨부파일 등록
	public void putAttach(MypageVO vo, AttachVO[] attachVO) throws SQLException, DAOBaseException {
		
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
			// 커넥션을 맺는다.
			getConnection(dsname);
			
			if(vo.getFile_id().equals("")){
				String sql = loadQueryString("sql.inquire.getFileSeq");
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
			
			// Query 파일에서 쿼리를 읽어온다.    
			String query = loadQueryString("sql.inquire.question.getAttachfileInsert");         
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
	
	/**
	 * Q&A 첨부파일 삭제 메소드
	 */
	public void getFileDelete(MypageVO vo,MypageSearchVO searchVO) throws SQLException, DAOBaseException {
		try {
			getConnection(dsname);
			//첨부파일 삭제
			String query = loadQueryString("sql.Mypage.question.getFileDelete");
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

	/**
	 * Q&A 스크랩 삭제
	 */
	public boolean getMypageScrapDelete(MypageSearchVO searchVO) throws SQLException, DAOBaseException {
		boolean success = false;
		
		try {
			getConnection(dsname);	
	    		    	
	        int param = 1;
	    	String query = loadQueryString("sql.mypage.scrapDelete");	    	
	    	
	    	System.out.println("searchVO.getLoginId()::"+searchVO.getLoginId());
	    	System.out.println("searchVO.getScrap_id()::"+searchVO.getScrap_id());
	    	System.out.println("query::"+query);
	    	pstmt = conn.prepareStatement(query);
			pstmt.setString(param++, searchVO.getLoginId());
			pstmt.setString(param++, searchVO.getScrap_id());
			pstmt.executeUpdate();
			pstmt.close();
			
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
