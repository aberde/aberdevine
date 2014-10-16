package kr.go.rndcall.mgnt.inquire.dao;

import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import kr.go.rndcall.mgnt.common.AttachVO;
import kr.go.rndcall.mgnt.common.BaseSqlDAO;
import kr.go.rndcall.mgnt.common.DAOBaseException;
import kr.go.rndcall.mgnt.common.DesCipher;
import kr.go.rndcall.mgnt.common.MailSend;
import kr.go.rndcall.mgnt.common.SSOUtil;
import kr.go.rndcall.mgnt.common.SmsSend;
import kr.go.rndcall.mgnt.common.Util;
import kr.go.rndcall.mgnt.inquire.vo.InquireAttachVO;
import kr.go.rndcall.mgnt.inquire.vo.InquireResultVO;
import kr.go.rndcall.mgnt.inquire.vo.InquireSearchVO;
import kr.go.rndcall.mgnt.inquire.vo.InquireVO;
import kr.go.rndcall.mgnt.inquire.vo.SatiVO;
import kr.go.rndcall.mgnt.login.LoginVO;

import org.apache.log4j.Logger;

public class InquireDAO extends BaseSqlDAO{

	private Logger logger = Logger.getLogger(this.getClass().getName());

	private String dsname = "jdbc/rndcall";

	/**
	 * Q&A 리스트를 조회하는 메소드
	 */
	public InquireResultVO getInquireMainList(InquireSearchVO searchVO) throws SQLException, DAOBaseException {


		InquireVO vo = null;
		ArrayList voList = new ArrayList();
		ArrayList voList1 = new ArrayList();
		ArrayList voList2 = new ArrayList();
		InquireVO statVo = null;

		InquireResultVO resultVO = new InquireResultVO();

		try {
			getConnection(dsname);
			
			String query = loadQueryString("sql.inquire.question.list");
			//Q&A리스트 조회
			if(!searchVO.getRoleCD().equals("0000C") &&  !searchVO.getRoleCD().equals("0000Z")){
				query += " AND Q.OPEN_YN = 'Y'";
			}
			query += " AND Q.DEL_YN='N'";
			query += " AND Q.BOARD_TYPE='QNA'";
			query += " AND Q.INSERT_TYPE='ONLINE'";
			query += " AND Q.SEQ = A.Q_SEQ ";
			query += " ORDER BY Q.READ_COUNT DESC";
			
			openPreparedStatementForR(query, true);
			executeQueryForR(searchVO);
			
			while (rs.next()) {
				vo = new InquireVO();
				vo.setSeq(rs.getString("SEQ"));
				vo.setTitle(rs.getString("TITLE"));
				vo.setBoard_type(rs.getString("BOARD_TYPE"));
				vo.setCategory1(rs.getString("CATEGORY1"));
			
				voList.add(vo);
			}
			resultVO.setVoList(voList);
			
			
			query = loadQueryString("sql.inquire.question.list");
			//자주하는 질문 리스트 조회
			if(!searchVO.getRoleCD().equals("0000C") &&  !searchVO.getRoleCD().equals("0000Z")){
//				query += " AND Q.OPEN_YN = 'Y'";
			}
			query += " AND Q.DEL_YN='N'";
			query += " AND Q.BOARD_TYPE='FAQ'";
			query += " AND Q.SEQ = A.Q_SEQ ";
			query += " ORDER BY Q.READ_COUNT DESC";
				
			openPreparedStatementForR(query, true);
			executeQueryForR(searchVO);
				
			while (rs.next()) {
				vo = new InquireVO();
				vo.setSeq(rs.getString("SEQ"));
				vo.setTitle(rs.getString("TITLE"));
				vo.setBoard_type(rs.getString("BOARD_TYPE"));
				vo.setCategory1(rs.getString("CATEGORY1"));
				
				voList1.add(vo);
			}	
			resultVO.setVoList1(voList1);
			
			
			if(searchVO.getRoleCD().equals("0000C") || searchVO.getRoleCD().equals("0000Z")){
				//관리자 Q&A 현황조회 정보
				query = loadQueryString("sql.inquire.question.qnaStatInfo");
				openPreparedStatementForR(query, false);
				executeQueryForR(searchVO);
	
				statVo = new InquireVO();
				if(rs.next()) {
					statVo.setStatCnt1(rs.getString("CNT1"));
					statVo.setStatCnt2(rs.getString("CNT2"));
					statVo.setStatCnt3(rs.getString("CNT3"));
				}
				
				//관리자 제안하기 현황조회 정보
				query = loadQueryString("sql.inquire.question.offerStatInfo");
				openPreparedStatementForR(query, false);
				executeQueryForR(searchVO);
	
				if(rs.next()) {
					statVo.setStatCnt4(rs.getString("CNT1"));
					statVo.setStatCnt5(rs.getString("CNT2"));
					statVo.setStatCnt6(rs.getString("CNT3"));
				}
				
				resultVO.setVo(statVo);
			}			
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
	 * Q&A 리스트를 조회하는 메소드
	 */
	public InquireResultVO getInquireList(InquireSearchVO searchVO) throws SQLException, DAOBaseException {


		InquireVO vo = null;
		InquireVO statVo = new InquireVO();
		ArrayList voList = new ArrayList();

		InquireResultVO resultVO = new InquireResultVO();
		String start_yymm ="";
		String end_yymm ="";
		
		try {
			getConnection(dsname);
			
			String query = "";
			String countQuery = "";
			//리스트 조회
//			if(searchVO.getRoleCD().equals("0000C") ||  searchVO.getRoleCD().equals("0000Z")){
				query = loadQueryString("sql.inquire.question.list");
				countQuery = loadQueryString("sql.inquire.question.list.count");
				if(searchVO.getType().equals("1")){
//					query += " AND Q.SEQ = A.Q_SEQ(+) ";
					query += " AND NOT EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER AA WHERE Q.SEQ = AA.Q_SEQ) ";
					countQuery += " AND NOT EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER AA WHERE Q.SEQ = AA.Q_SEQ) ";
				}else if(searchVO.getType().equals("2")){
//					query += " AND Q.SEQ = A.Q_SEQ(+) ";
					query += " AND EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER AA WHERE Q.SEQ = AA.Q_SEQ) ";
					query += " AND NVL(Q.PUBLIC_TRANS_YN,'N') != 'Y' ";
					countQuery += " AND EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER AA WHERE Q.SEQ = AA.Q_SEQ) ";
					countQuery += " AND NVL(Q.PUBLIC_TRANS_YN,'N') != 'Y' ";
				}else if(searchVO.getType().equals("3")){
//					query += " AND Q.SEQ = A.Q_SEQ(+) ";
					query += " AND EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER AA WHERE Q.SEQ = AA.Q_SEQ) ";
					query += " AND NVL(Q.PUBLIC_TRANS_YN,'N') = 'Y' ";
					countQuery += " AND EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER AA WHERE Q.SEQ = AA.Q_SEQ) ";
					countQuery += " AND NVL(Q.PUBLIC_TRANS_YN,'N') = 'Y' ";
//				}else{
//					query += " AND Q.SEQ = A.Q_SEQ(+) ";
				}
				if (searchVO.getInsert_type() != null && !searchVO.getInsert_type().equals("")) {
					query += " AND Q.INSERT_TYPE = '" + searchVO.getInsert_type() + "' \n";
					countQuery += " AND Q.INSERT_TYPE = '" + searchVO.getInsert_type() + "' \n";
				}
				if ( !searchVO.getRoleCD().equals("0000Z") && !searchVO.getRoleCD().equals("0000C") ) {
				    query += " AND Q.INSERT_TYPE <> 'OFFLINE' \n";
                    countQuery += " AND Q.INSERT_TYPE <> 'OFFLINE' \n";
				}
				if(searchVO.getPublic_trans_yn() != null && !searchVO.getPublic_trans_yn().equals("")){
					query += " AND Q.PUBLIC_TRANS_YN='" + searchVO.getPublic_trans_yn() + "'";
					countQuery += " AND Q.PUBLIC_TRANS_YN='" + searchVO.getPublic_trans_yn() + "'";
				}
//			}else{
//				query = loadQueryString("sql.inquire.question.list2");
//				countQuery = loadQueryString("sql.inquire.question.list2.count");
//				query += " AND Q.OPEN_YN = 'Y'";
//				query += " AND Q.INSERT_TYPE='ONLINE'";
//				countQuery += " AND Q.OPEN_YN = 'Y'";
//				countQuery += " AND Q.INSERT_TYPE='ONLINE'";
//			}
			
			if ( searchVO.getBoard_type() != null && !searchVO.getBoard_type().isEmpty() ) {
			    query += " AND Q.BOARD_TYPE='" + searchVO.getBoard_type() + "'";
			}
			query += " AND Q.DEL_YN='N'";
			if ( searchVO.getBoard_type() != null && !searchVO.getBoard_type().isEmpty() ) {
			    countQuery += " AND Q.BOARD_TYPE='" + searchVO.getBoard_type() + "'";
			}
			countQuery += " AND Q.DEL_YN='N'";
			
			if(!searchVO.getSearchCategory().equals("")){
				query += " AND Q.CATEGORY1 = '"+searchVO.getSearchCategory()+"' ";
				countQuery += " AND Q.CATEGORY1 = '"+searchVO.getSearchCategory()+"' ";
			}
			
			if(!searchVO.getWhichSearch().equals("") && !searchVO.getSearchTxt().equals("")){
				if(searchVO.getWhichSearch().equals("all")){
					query += " AND ( CLOB_TO_CHAR(Q.CONTENTS) like '%"+searchVO.getSearchTxt()+"%' ";
					query += "       OR CLOB_TO_CHAR(A.ANSWER_CONT) like '%"+searchVO.getSearchTxt()+"%' ";
					query += "       OR Q.TITLE like '%"+searchVO.getSearchTxt()+"%' )";
					countQuery += " AND ( CLOB_TO_CHAR(Q.CONTENTS) like '%"+searchVO.getSearchTxt()+"%' ";
					countQuery += "       OR CLOB_TO_CHAR(A.ANSWER_CONT) like '%"+searchVO.getSearchTxt()+"%' ";
					countQuery += "       OR Q.TITLE like '%"+searchVO.getSearchTxt()+"%' )";
				}else if(searchVO.getWhichSearch().equals("title")){
					query += " AND Q.TITLE like '%"+searchVO.getSearchTxt()+"%' ";
					countQuery += " AND Q.TITLE like '%"+searchVO.getSearchTxt()+"%' ";
				}else if(searchVO.getWhichSearch().equals("contents")){
					query += " AND ( CLOB_TO_CHAR(Q.CONTENTS) like '%"+searchVO.getSearchTxt()+"%' ";
					query += "       OR CLOB_TO_CHAR(A.ANSWER_CONT) like '%"+searchVO.getSearchTxt()+"%' )";
					countQuery += " AND ( CLOB_TO_CHAR(Q.CONTENTS) like '%"+searchVO.getSearchTxt()+"%' ";
					countQuery += "       OR CLOB_TO_CHAR(A.ANSWER_CONT) like '%"+searchVO.getSearchTxt()+"%' )";
				}else if(searchVO.getWhichSearch().equals("name")){
					query += "       AND Q.REG_NM like '%"+searchVO.getSearchTxt()+"%' ";
					countQuery += " AND Q.REG_NM like '%"+searchVO.getSearchTxt()+"%' ";
				}else if(searchVO.getWhichSearch().equals("reg_id")){
				    query += "       AND DECODE(Q.REG_ID, NULL, '비회원', '', '비회원') like '%"+searchVO.getSearchTxt()+"%' ";
				    countQuery += " AND DECODE(Q.REG_ID, NULL, '비회원', '', '비회원') like '%"+searchVO.getSearchTxt()+"%' ";
				}
			}
			
			if ( searchVO.getStat() != null && !"".equals(searchVO.getStat()) ) {
			    query += "       AND DECODE(A.SEQ,null,'N','Y') = '"+searchVO.getStat()+"' ";
                countQuery += "       AND DECODE(A.SEQ,null,'N','Y') = '"+searchVO.getStat()+"' ";
			}
			
			Calendar cal = Calendar.getInstance();
			if(searchVO.getStart_yy().equals("")) searchVO.setStart_yy("2007");
			if(searchVO.getStart_mm().equals("")) searchVO.setStart_mm("01");
			if(searchVO.getEnd_yy().equals("")) searchVO.setEnd_yy(Integer.toString(cal.get(Calendar.YEAR)));
			String mm =Integer.toString(cal.get(Calendar.MONTH)+1);
			if(mm.length() < 2 ) mm = "0"+mm;
			System.out.println("mm::"+mm);
			if(searchVO.getEnd_mm().equals("")) {
				searchVO.setEnd_mm(mm);
			}else{
				if(searchVO.getEnd_mm().length() < 2 ) searchVO.setEnd_mm("0"+searchVO.getEnd_mm());
			}
			
			start_yymm =searchVO.getStart_yy()+""+searchVO.getStart_mm();
			end_yymm =searchVO.getEnd_yy()+""+searchVO.getEnd_mm();
			
			query += " AND Q.REG_DT >= CAST('"+start_yymm +"01000000.000' AS DATETIME) AND Q.REG_DT <= CAST(TO_CHAR(LAST_DAY(CAST('"+end_yymm+"01' AS DATE)), 'YYYYMMDD') || '235959.999'  AS DATETIME)";
			countQuery += " AND Q.REG_DT >= CAST('"+start_yymm +"01000000.000' AS DATETIME) AND Q.REG_DT <= CAST(TO_CHAR(LAST_DAY(CAST('"+end_yymm+"01' AS DATE)), 'YYYYMMDD') || '235959.999'  AS DATETIME)";
			
			query += " ORDER BY Q.REG_DT DESC, Q.SEQ DESC";
			
			openPreparedStatementForR(query, true);
			executeQueryForRC(searchVO, 1);
			
			while (rs.next()) {
				vo = new InquireVO();
				vo.setSeq(rs.getString("SEQ"));
				vo.setTitle(rs.getString("TITLE"));
				vo.setBoard_type(rs.getString("BOARD_TYPE"));
				vo.setCategory1(rs.getString("CATEGORY1"));
				vo.setReg_dt(rs.getString("REG_DT1"));
				vo.setReg_nm(rs.getString("REG_NM"));
				vo.setRead_count(rs.getInt("READ_COUNT"));
				vo.setStat(rs.getString("STAT"));
				vo.setTrans_id(rs.getString("TRANS_ID"));
				vo.setInsert_type(rs.getString("INSERT_TYPE"));
				vo.setTrans_nm(rs.getString("TRANS_NM"));
				vo.setTrans_org_nm(rs.getString("TRANS_ORG_NM"));
				vo.setTrans_attached_nm(rs.getString("TRANS_ATTACHED_NM"));
				vo.setReg_id(rs.getString("REG_ID"));
				vo.setOpen_yn(rs.getString("OPEN_YN"));
				
				voList.add(vo);
			}
			resultVO.setVoList(voList);
						
			rs.close();
			pstmt.close();
			
			pstmt = conn.prepareStatement(countQuery);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				resultVO.setTotRowCount(Integer.valueOf(rs.getString("CNT")));
			}
			
			if(searchVO.getRoleCD().equals("0000C") || searchVO.getRoleCD().equals("0000Z")){
				// 관리자 Q&A 현황조회 정보
				query = loadQueryString("sql.inquire.question.qnaStatInfo");
				openPreparedStatementForR(query, false);
				executeQueryForR(searchVO);
	
				if(rs.next()) {
					statVo.setStatCnt1(rs.getString("CNT1"));
					statVo.setStatCnt2(rs.getString("CNT2"));
					statVo.setStatCnt3(rs.getString("CNT3"));
				}
				
				// 관리자 제안하기 현황조회 정보
				query = loadQueryString("sql.inquire.question.offerStatInfo");
				openPreparedStatementForR(query, false);
				executeQueryForR(searchVO);
	
				if(rs.next()) {
					statVo.setStatCnt4(rs.getString("CNT1"));
					statVo.setStatCnt5(rs.getString("CNT2"));
					statVo.setStatCnt6(rs.getString("CNT3"));
				}
				
				resultVO.setVo(statVo);
			}			
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
	 * Q&A 리스트를 조회하는 메소드
	 */
	public String getSeq() throws SQLException, DAOBaseException {


		String seq ="";
		try {
			getConnection(dsname);
			
			String sql = loadQueryString("sql.inquire.getSeq");
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				seq = rs.getString("ID");
			}
			rs.close();
			pstmt.close();
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			System.out.println(e.getStackTrace());
			return null;
		} finally {
			close();
		}

		return seq;
	}
	
	/**
	 * Q&A 상세정보를 조회하는 메소드
	 */
	public InquireResultVO getInquireView(InquireSearchVO searchVO) throws SQLException, DAOBaseException {


		InquireVO vo = null;
//		ArrayList voList = new ArrayList();
		InquireResultVO resultVO = new InquireResultVO();
//		Reader content_clob = null;
//		Reader answerContent_clob = null;
		
		try {
			getConnection(dsname);

			System.out.println("상세조회 실행 쿼리문");
			String query = loadQueryString("sql.inquire.question.view");
			//
			query += " AND Q.BOARD_TYPE= '" + searchVO.getBoard_type() +"'";
//			query += " AND Q.SEQ = A.Q_SEQ(+) ";
			query += " AND Q.SEQ = '" + searchVO.getSeq() +"'";
			
			
			System.out.println("상세조회 실행 쿼리문::"+query);
			
			pstmt = conn.prepareStatement(query);
		    rs = pstmt.executeQuery();
			
		    
			if (rs.next()) {
				vo = new InquireVO();
				vo.setSeq(rs.getString("SEQ"));				
				vo.setTitle(rs.getString("TITLE"));
				vo.setCall_receive_yn(rs.getString("CELL_RECEIVE_YN"));
				vo.setCell_number(rs.getString("CELL_NUMBER"));
				vo.setEmail_receive_yn(rs.getString("EMAIL_RECEIVE_YN"));
				vo.setEmail(rs.getString("EMAIL"));
				vo.setOpen_yn(rs.getString("OPEN_YN"));
				vo.setBoard_type(rs.getString("BOARD_TYPE"));
				vo.setInsert_type(rs.getString("INSERT_TYPE"));
				vo.setCategory1(rs.getString("CATEGORY1"));
				vo.setCategory2(rs.getString("CATEGORY2"));
				vo.setCategory1_nm(rs.getString("CATEGORY1_NM"));
				vo.setCategory2_nm(rs.getString("CATEGORY2_NM"));
				vo.setReg_dt(rs.getString("REG_DT1"));
				vo.setReg_nm(rs.getString("REG_NM"));
				vo.setRead_count(rs.getInt("READ_COUNT"));
				vo.setContents(rs.getString("CONTENTS"));
				vo.setQuery_user_info(rs.getString("QUERY_USER_INFO"));
//				content_clob = rs.getCharacterStream("CONTENTS");
//				vo.setContents(Util.streamToBuf(content_clob).toString());
				if(searchVO.getFlag().equals("U")){
					vo.setContents(vo.getContents());					
				}else if(searchVO.getFlag().equals("A")){
					vo.setContents(Util.checkNull(vo.getContents(), "").replaceAll("\n", "<br>"));
				}else{
					vo.setContents(Util.checkNull(vo.getContents(), "").replaceAll("\n", "<br>"));					
				}
//				answerContent_clob = rs.getCharacterStream("ANSWER_CONT");
//				vo.setAnswerContents(Util.streamToBuf(answerContent_clob).toString());
				vo.setAnswerContents(rs.getString("ANSWER_CONT"));
				
				if(searchVO.getFlag().equals("U")){
					vo.setAnswerContents(vo.getAnswerContents());		
				}else if(searchVO.getFlag().equals("A")){
					vo.setAnswerContents(vo.getAnswerContents());		
				}else{
					if (vo.getAnswerContents() != null && !vo.getAnswerContents().equals(""))
					vo.setAnswerContents(vo.getAnswerContents().replaceAll("\n", "<br>"));
				}
				vo.setQuestion_file_id(rs.getString("QUESTION_FILE_ID"));
				vo.setAnswer_file_id(rs.getString("ANSWER_FILE_ID"));
				vo.setUp_del_stat(rs.getString("UP_DEL_STAT"));
				vo.setStat(rs.getString("STAT"));
				vo.setAnswer_seq(rs.getString("ANSWER_SEQ"));
				vo.setPublic_trans_yn(rs.getString("PUBLIC_TRANS_YN"));
				vo.setGov_answer(rs.getString("GOV_ANSWER"));
				vo.setTrans_id(rs.getString("TRANS_ID"));
				vo.setTrans_nm(rs.getString("TRANS_NM"));
				vo.setTrans_org_nm(rs.getString("TRANS_ORG_NM"));
				vo.setTel(rs.getString("TEL"));
				vo.setReg_id(rs.getString("REG_ID"));
			}
			
			resultVO.setVo(vo);	
			pstmt.close();
			
			//조회수 업데이트(관리자는 제외)
			if(searchVO.getRoleCD().equals("0000C") ||  searchVO.getRoleCD().equals("0000Z")){
				String insert_sql = loadQueryString("sql.inquire.question.readCountUpdate");						
				pstmt = conn.prepareStatement(insert_sql);					
				pstmt.setString(1, searchVO.getSeq());
				pstmt.execute();
				pstmt.close();
			}
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		} finally {
			close();
		}

		return resultVO;
	}

	
	/**
	 * Q&A 첨부파일을 조회하는 메소드
	 */
	public InquireResultVO getFileInfo(String file_id) throws SQLException, DAOBaseException {


		ArrayList voList = new ArrayList();

		InquireResultVO resultVO = new InquireResultVO();
		InquireAttachVO fileVo = null;
		DesCipher dc = new DesCipher();
		
		try {
			getConnection(dsname);
			
			String query = loadQueryString("sql.inquire.question.fileinfo");
			openPreparedStatementForR(query, false);
			
			pstmt.setString(1, file_id) ;
			
			executeQueryForR();				
			
			while (this.rs.next()) {
				fileVo = new InquireAttachVO();
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
	 * Q&A 등록하는 메소드
	 */
	public boolean getInquireInsert(InquireVO vo, InquireSearchVO searchVO) throws SQLException, DAOBaseException {

		StringBuffer sbQuery = null;		
		boolean success = false;
		String seq = "";
		
		try {
			getConnection(dsname);
			conn.setAutoCommit(false);
			
			String sql = loadQueryString("sql.inquire.getSeq");
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				seq = rs.getString("ID");
			}
			rs.close();
			pstmt.close();
			
			System.out.println("SEQ ::::::::::: " + seq);
			
			String insert_sql = loadQueryString("sql.inquire.question.insert");
			
			int param =1;
			
			openPreparedStatementForCUD(insert_sql);
						
			pstmt.setString(param++, seq);
			pstmt.setString(param++, "QNA");
			pstmt.setString(param++, vo.getTitle().replaceAll("'","′"));
			pstmt.setString(param++, vo.getContents());
			pstmt.setString(param++, vo.getCall_receive_yn());
			pstmt.setString(param++, vo.getCell_number());
			pstmt.setString(param++, vo.getEmail_receive_yn());
			pstmt.setString(param++, vo.getEmail());
			pstmt.setString(param++, vo.getOpen_yn());
			pstmt.setString(param++, "N");
			pstmt.setString(param++, "N");
			pstmt.setString(param++, "ONLINE");
			pstmt.setString(param++, "N");
			pstmt.setString(param++, vo.getFile_id());
			pstmt.setString(param++, "N");
			pstmt.setString(param++, searchVO.getLoginId());
			pstmt.setString(param++, searchVO.getName());
			pstmt.setString(param++, vo.getCategory1());
			pstmt.setString(param++, vo.getCategory2());
			SSOUtil ssoUtil = new SSOUtil();
			if ( vo.getPassword() != null && !vo.getPassword().isEmpty() ) {
			    pstmt.setString(param++, ssoUtil.encrypt(vo.getPassword()));
			} else {
			    pstmt.setString(param++, "");
			}
			pstmt.setString(param++, vo.getQuery_user_info());
			
			System.out.println("query ::::::::::: " + insert_sql);

			executeQueryForCUD();
			System.out.println("success ::::::::::: ");
			pstmt.close();
			conn.commit();
			
			//질의내용 clob컬럼을 가져온다.				
//			try{
//				sbQuery = new StringBuffer();
////				sbQuery.append("SELECT CONTENTS FROM RNDCALL_BOARD_QUESTION WHERE SEQ = '").append(seq).append("' for update");
//
////				pstmt = conn.prepareStatement(sbQuery.toString());
////				rs = pstmt.executeQuery();			
//				// contents 컬럼을 업데이트 한다.
////				CLOB objClob = null;			
////				while (rs.next()) {
////					objClob = ((OracleResultSet) rs).getCLOB("CONTENTS");
////					Util.bufToWrite(objClob,vo.getContents()); 				
////				}
////				rs.close();
////				pstmt.close();
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
			System.out.println("Error e.toString() is " + e.toString());
			success = false;
		} finally {
			conn.setAutoCommit(true);
			close();
		}		
		return success;
	}
	
	
	/**
	 * Q&A 만족도평가 조회하는 메소드
	 */
	public InquireResultVO getSatiInfo(String seq, String login_id) throws SQLException, DAOBaseException {


		InquireResultVO resultVO = new InquireResultVO();
		SatiVO satiVO = new SatiVO();
		
		System.out.println("seq::"+seq);
		System.out.println("login_id::"+login_id);
		
		
		try {
			getConnection(dsname);
			
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
	public boolean getSatiInsert(InquireVO vo, InquireSearchVO searchVO, SatiVO satiVO) throws SQLException, DAOBaseException {
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
	
	/**
	 * Q&A 수정하는 메소드
	 */
	public boolean getInquireUpdate(InquireVO vo, InquireSearchVO searchVO) throws SQLException, DAOBaseException {
		StringBuffer sbQuery = null;		
		boolean success = false;
		String seq = "";
		
		try {
			getConnection(dsname);
			conn.setAutoCommit(false);
	    		    	
	        int param = 1;
	    	String query = loadQueryString("sql.inquire.question.update");	    	
	    	
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
			pstmt.setString(param++, vo.getQuery_user_info());
			pstmt.setString(param++, searchVO.getSeq());
			
			pstmt.executeUpdate();
			pstmt.close();
			conn.commit();
			
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
	 * Q&A 삭제하는 메소드
	 */
	public boolean getInquireDelete(InquireSearchVO searchVO) throws SQLException, DAOBaseException {
		boolean success = false;

		try {
			
			getConnection(dsname);
			String query = loadQueryString("sql.inquire.question.delete");	  

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
	 * 대분야정보 조회
	 */
	public InquireResultVO getCodeInfo() throws SQLException, DAOBaseException {

		ArrayList voList = new ArrayList();
		InquireResultVO resultVO = new InquireResultVO();
		InquireVO vo = null;		
		try {			
			getConnection(dsname);
            String query = loadQueryString("sql.inquire.question.Category1Code");
            openPreparedStatementForR(query,  false);
            executeQueryForR();
            while(rs.next()) {
				vo = new InquireVO();
				vo.setCode(rs.getString("code"));
	            vo.setCode_nm(rs.getString("code_nm"));
	            voList.add(vo);
			}
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
	 * 소분야정보 조회
	 */
	public InquireResultVO getCodeInfo1() throws SQLException, DAOBaseException {

		ArrayList voList = new ArrayList();
		InquireResultVO resultVO = new InquireResultVO();
		InquireVO vo = null;		
		try {			
			getConnection(dsname);
            String query = loadQueryString("sql.inquire.question.Category2Code");
            openPreparedStatementForR(query,  false);
            executeQueryForR();
            while(rs.next()) {
				vo = new InquireVO();
				vo.setCode(rs.getString("code"));
	            vo.setCode_nm(rs.getString("code_nm"));
	            vo.setP_code(rs.getString("p_code"));
	            voList.add(vo);
			}
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
	 * Q&A 답변 등록하는 메소드
	 */
	public boolean getAnswerInsert(InquireVO vo,InquireSearchVO searchVO) throws SQLException, DAOBaseException {
		boolean success = false;
		String answer_seq = "";
		StringBuffer sbQuery = null;
		try {
			
			getConnection(dsname);
			conn.setAutoCommit(false);
			
			String sql = loadQueryString("sql.inquire.getSeq");
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				answer_seq = rs.getString("ID");
			}
			rs.close();
			pstmt.close();
			String answer_sql = loadQueryString("sql.inquire.question.getAnswerInsert");
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
			
			//질의내용 clob컬럼을 가져온다.	
//			try{
//				sbQuery = new StringBuffer();
//				sbQuery.append("SELECT ANSWER_CONT FROM RNDCALL_BOARD_ANSWER WHERE SEQ = '").append(answer_seq).append("' for update");
//
//				pstmt = conn.prepareStatement(sbQuery.toString());
//				rs = pstmt.executeQuery();			
//				// contents 컬럼을 업데이트 한다.
//				CLOB objClob = null;			
//				while (rs.next()) {
//					objClob = ((OracleResultSet) rs).getCLOB("ANSWER_CONT");
//					Util.bufToWrite(objClob,vo.getAnswerContents()); 				
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
			
			
			//분야정보 질문테이블에 업데이트
			String query = loadQueryString("sql.inquire.question.getCategoryUpdate");	 
			pstmt = conn.prepareStatement(query);
	    	pstmt.setString(1, vo.getCategory1());
	    	pstmt.setString(2, vo.getCategory2());
	    	pstmt.setString(3, vo.getOpen_yn());
			pstmt.setString(4, searchVO.getSeq());
			
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
	
	/**
	 * Q&A 답변 등록하는 메소드
	 */
	public boolean getAnswerUpdate(InquireVO vo,InquireSearchVO searchVO) throws SQLException, DAOBaseException {
		boolean success = false;
		String answer_seq = "";
		StringBuffer sbQuery = null;
		try {
			
			getConnection(dsname);
			conn.setAutoCommit(false);

			String answer_sql = loadQueryString("sql.inquire.question.getAnswerUpdate");
			int param =1;
			
			openPreparedStatementForCUD(answer_sql);
			
			pstmt.setString(param++, vo.getAnswerContents());
			pstmt.setString(param++, vo.getFile_id());
			pstmt.setString(param++, searchVO.getLoginId());			
			pstmt.setString(param++, vo.getAnswer_seq());
			
			executeQueryForCUD();
			pstmt.close();
			conn.commit();
			
			//질의내용 clob컬럼을 가져온다.			
//			try{
//				sbQuery = new StringBuffer();
//				sbQuery.append("SELECT ANSWER_CONT FROM RNDCALL_BOARD_ANSWER WHERE SEQ = '").append(vo.getAnswer_seq()).append("' for update");
//
//				pstmt = conn.prepareStatement(sbQuery.toString());
//				rs = pstmt.executeQuery();			
//				// contents 컬럼을 업데이트 한다.
//				CLOB objClob = null;			
//				while (rs.next()) {
//					objClob = ((OracleResultSet) rs).getCLOB("ANSWER_CONT");
//					Util.bufToWrite(objClob,vo.getAnswerContents());
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
			
			
			//분야정보 질문테이블에 업데이트
			String query = loadQueryString("sql.inquire.question.getCategoryUpdate");	 
			pstmt = conn.prepareStatement(query);
	    	pstmt.setString(1, vo.getCategory1());
	    	pstmt.setString(2, vo.getCategory2());
	    	pstmt.setString(3, vo.getOpen_yn());
			pstmt.setString(4, searchVO.getSeq());
			
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
   
	//첨부파일 등록
	public void putAttach(InquireVO vo, AttachVO[] attachVO) throws SQLException, DAOBaseException {
		
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
	public void getFileDelete(InquireVO vo,InquireSearchVO searchVO) throws SQLException, DAOBaseException {
		try {
			getConnection(dsname);
			//첨부파일 삭제
			String query = loadQueryString("sql.inquire.question.getFileDelete");
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
	 * Q&A 타기관담당자 지정하는 메소드
	 */
	public InquireResultVO getOrgTransList() throws SQLException, DAOBaseException {
		
		ArrayList voList = new ArrayList();
		InquireResultVO resultVO = new InquireResultVO();
		LoginVO loginVO = null;
		try {
			//0000B
			getConnection(dsname);
			//부처담당자 정보조회
			String query = loadQueryString("sql.inquire.question.getOrgAuthList");
			query += " WHERE AUTH = '0000B'";
			openPreparedStatementForR(query, false);
			executeQueryForR();
			
			while (rs.next()) {
				loginVO = new LoginVO();
				loginVO.setLogin_id(rs.getString("USER_ID"));
				loginVO.setName(rs.getString("NAME"));
				loginVO.setOrg_cd(rs.getString("ORG_CD"));
				loginVO.setOrg_nm(rs.getString("ORG_NM"));
				loginVO.setAttached_nm(rs.getString("ATTACHED_NM"));
				loginVO.setEmail(rs.getString("EMAIL"));
				loginVO.setMobile(rs.getString("MOBILE"));
				voList.add(loginVO);
			}
			resultVO.setVoList(voList);
			
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {
			close();
		}

		return resultVO;
	}
   
	
	/**
	 * Q&A 타기관담당자 지정하는 메소드
	 */
	public boolean getOrgTransInsert(InquireVO vo, InquireSearchVO searchVO, String server_ip) throws SQLException, DAOBaseException {
		boolean success = false;
		List SmsList = new ArrayList();
		HashMap SmsHm = new HashMap();
		
		List EmailList = new ArrayList();
		HashMap emailHm = new HashMap();
		String SmsMsgText = "";
		String EmailMsgText = "";
		int i =0;
		
		String trans_userId= ""; //ID
		String trans_name = "";  //이름
		String trans_orgCd = ""; //부처코드
		String trans_orgNm = ""; //부처코드명
		String trans_attachedNm = ""; //소속부서명
		String trans_email = "";  //이메일
		String trans_mobile = "";  //핸드폰번호
		String history_seq = "";
		
		try {
			getConnection(dsname);
			
			String sql = loadQueryString("sql.inquire.question.getOrgTransInsert");
							
			openPreparedStatementForCUD(sql);
			pstmt.setString(1, vo.getPublic_trans_id());			
			pstmt.setString(2, searchVO.getSeq());
				
			executeQueryForCUD();
			pstmt.close();
			
			String query = loadQueryString("sql.inquire.question.getOrgAuthList");
			query += " WHERE AUTH = '0000B'";
			query += "       AND USER_ID= '"+vo.getPublic_trans_id()+"'";
				
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			    
			if (rs.next()) {
				trans_userId= rs.getString("USER_ID"); 
				trans_name= rs.getString("NAME"); 
				trans_orgCd= rs.getString("ORG_CD"); 
				trans_orgNm= rs.getString("ORG_NM"); 
				trans_attachedNm= rs.getString("ATTACHED_NM"); 
				trans_email= rs.getString("EMAIL");
				trans_mobile= rs.getString("MOBILE");
				
				SmsHm = new HashMap();
				SmsHm.put("MOBILE", searchVO.getTrans_phone().replaceAll("-",""));
				SmsHm.put("USERNAME", trans_name);
				SmsHm.put("LOGIN_ID", "nstc03");
				
				SmsList.add(i, SmsHm);
				i++;
			}
			
			System.out.println("getOrgTransInsert query =["+query+"]");
			System.out.println("getOrgTransInsert trans_email =["+searchVO.getTrans_email()+"]");
			System.out.println("getOrgTransInsert trans_mobile =["+searchVO.getTrans_phone()+"]");
			System.out.println("getOrgTransInsert searchVO.getSeq() =["+searchVO.getSeq()+"]");
			System.out.println("이메일 발송 ::" +searchVO.getTrans_email());
			System.out.println("SMS 발송 ::" +searchVO.getTrans_phone());
			
			
			/*
			 * SMS 발송 및 이력등록
			 */
			String MsgText = "도우미센터에 문의에 답변등록이 지정되었습니다.\n";
			MsgText += "www.rndcall.go.kr \n -R&D도우미센터- "; // 본문
			
			SmsSend smsSend = new SmsSend();
			success = smsSend.SmsSendMain(SmsList, MsgText);
			
			String sms_sql = loadQueryString("sql.inquire.question.getSendHistorySeq");
			pstmt = conn.prepareStatement(sms_sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				history_seq = rs.getString("ID");
			}
			rs.close();
			pstmt.close();
			String insert_sql = loadQueryString("sql.inquire.question.getSendHistoryInsert");
			
			int param =1;
			
			openPreparedStatementForCUD(insert_sql);
						
			pstmt.setString(param++, history_seq);
			pstmt.setString(param++, "SMS");
			pstmt.setString(param++, "QNA");
			pstmt.setString(param++, searchVO.getSeq());
			pstmt.setString(param++, searchVO.getTrans_phone());
			pstmt.setString(param++, "027248700");
			pstmt.setString(param++, "");
			pstmt.setString(param++, "");
			pstmt.setString(param++, SmsMsgText);
			pstmt.setString(param++, String.valueOf(success));
			pstmt.setString(param++, searchVO.getLoginId());
			
			executeQueryForCUD();
			pstmt.close();			
			// SMS 발송 및 이력등록
			
			/*
			 * EMAIL 발송 및 이력등록
			 */
			//EmailMsgText = "R&D도우미센터에 문의에 답변등록이 지정되었습니다.\n";
			//EmailMsgText += "<a href='http://www.rndcall.go.kr'>도우미센터</a>에 확인하여 답변등록이 가능합니다. \n -R&D도우미센터- "; // 본문
			i=0;
			query = loadQueryString("sql.inquire.question.getOrgAuthList");
			query += " WHERE AUTH = '0000B'";
			query += "       AND USER_ID= '"+vo.getPublic_trans_id()+"'";
				
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			    
			if (rs.next()) {
				trans_userId= rs.getString("USER_ID"); 
				trans_name= rs.getString("NAME"); 
				trans_orgCd= rs.getString("ORG_CD"); 
				trans_orgNm= rs.getString("ORG_NM"); 
				trans_attachedNm= rs.getString("ATTACHED_NM"); 
				trans_email= rs.getString("EMAIL");
				trans_mobile= rs.getString("MOBILE");
				
				emailHm = new HashMap();
				emailHm.put("EMAIL", searchVO.getTrans_email());
				emailHm.put("USERNAME", trans_name);
				emailHm.put("LOGIN_ID",  "nstc03");
															
				EmailList.add(i, emailHm);				
				i++;
			}
			String cont_query = loadQueryString("sql.inquire.question.view");;
			       cont_query += " AND Q.SEQ= '"+searchVO.getSeq()+"'";
//			       cont_query += " AND Q.SEQ = A.Q_SEQ(+) ";
			System.out.println("cont_query::"+cont_query);
			pstmt = conn.prepareStatement(cont_query);
			rs = pstmt.executeQuery();
			String title= "";
			String contents= "";
			String reg_nm = "";
			String reg_dt = "";
			String cont = "";
			if (rs.next()) {
				title = rs.getString("TITLE");
//				cont = Util.streamToBuf(rs.getCharacterStream("CONTENTS")).toString().replaceAll("\n", "<br>");
				cont = rs.getString("CONTENTS");
				reg_nm = rs.getString("REG_NM");
				reg_dt = rs.getString("REG_DT1");
			}
			rs.close();
			pstmt.close();
			
			
			EmailMsgText = this.mailForm();
			System.out.println("EmailMsgText::"+EmailMsgText);
			
			String msg = "R&D 도우미센터에 등록된 문의에 답변 바랍니다. 로그인 후 답변 등록이 가능합니다.";
			EmailMsgText = EmailMsgText.replaceAll("#TITLE#", title);
			EmailMsgText = EmailMsgText.replaceAll("#MSG#", msg);
			EmailMsgText = EmailMsgText.replaceAll("#REG_DT#", reg_dt);
			EmailMsgText = EmailMsgText.replaceAll("#REG_NM#", reg_nm);
			EmailMsgText = EmailMsgText.replaceAll("#CONTENT#", cont);
			//EmailMsgText = EmailMsgText.replaceAll("#server_ip#", "http://"+server_ip+":9998");
			EmailMsgText = EmailMsgText.replaceAll("#server_ip#", "http://rndcall.go.kr");
			EmailMsgText = EmailMsgText.replaceAll("#button#", "<img src='http://rndcall.go.kr/images/mail/Btn_Write.gif' alt='답변등록' border='0' />");
			
			System.out.println("EmailMsgText::"+EmailMsgText);
			
			MailSend MailSend = new MailSend();
			success = MailSend.MailSendMain(EmailList, EmailMsgText);
			
			String email_sql = loadQueryString("sql.inquire.question.getSendHistorySeq");
			pstmt = conn.prepareStatement(email_sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				history_seq = rs.getString("ID");
			}
			rs.close();
			pstmt.close();
			String insert_sql1 = loadQueryString("sql.inquire.question.getSendHistoryInsert");
			
			int param1 =1;
			
			openPreparedStatementForCUD(insert_sql1);
						
			pstmt.setString(param1++, history_seq);
			pstmt.setString(param1++, "EMAIL");
			pstmt.setString(param1++, "QNA");
			pstmt.setString(param1++, searchVO.getSeq());
			pstmt.setString(param1++, "");
			pstmt.setString(param1++, "");
			pstmt.setString(param1++, searchVO.getTrans_email());
			pstmt.setString(param1++, "noreply@nstc.go.kr");
			pstmt.setString(param1++, "이메일발송");
			pstmt.setString(param1++, String.valueOf(success));
			pstmt.setString(param1++, searchVO.getLoginId());
			
			executeQueryForCUD();
			pstmt.close();			
			// EMAIL 발송 및 이력등록
			success= true;			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			e.printStackTrace();
			success = false;
		} finally {
			success= true;
			close();
		}
		System.out.println("InquireDAO success::"+success);
		return success;
	}
   
		
	/**
	 * Q&A 리스트(부처담당자)를 조회하는 메소드
	 */
	public InquireResultVO getInquireOrgList(InquireSearchVO searchVO) throws SQLException, DAOBaseException {


		InquireVO vo = null;
		InquireVO statVo = null;
		ArrayList voList = new ArrayList();

		InquireResultVO resultVO = new InquireResultVO();
		String start_yymm ="";
		String end_yymm ="";
		try {
			getConnection(dsname);
			
			String query = loadQueryString("sql.inquire.question.list");
			//리스트 조회
			query += " AND Q.BOARD_TYPE='" + searchVO.getBoard_type() + "'";
//			query += " AND Q.SEQ = A.Q_SEQ(+) ";
			//query += " AND NOT EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER AA WHERE Q.SEQ = AA.Q_SEQ) ";
			query += " AND NVL(Q.PUBLIC_TRANS_YN,'N') = 'Y' ";
			query += " AND Q.DEL_YN = 'N' ";
			query += " AND Q.PUBLIC_TRANS_ID = '" + searchVO.getLoginId() + "' ";
			
			
			if(!searchVO.getSearchCategory().equals("")){
				query += " AND Q.CATEGORY1 = '"+searchVO.getSearchCategory()+"' ";
			}
			
			if(!searchVO.getWhichSearch().equals("") && !searchVO.getSearchTxt().equals("")){
				if(searchVO.getWhichSearch().equals("title")){
					query += " AND Q.TITLE like '%"+searchVO.getSearchTxt()+"%' ";
				}else if(searchVO.getWhichSearch().equals("contents")){
					query += " AND Q.CONTENTS like '%"+searchVO.getSearchTxt()+"%' ";
					
				}
			}
			
			Calendar cal = Calendar.getInstance();
			if(searchVO.getStart_yy().equals("")) searchVO.setStart_yy("2007");
			if(searchVO.getStart_mm().equals("")) searchVO.setStart_mm("01");
			if(searchVO.getEnd_yy().equals("")) searchVO.setEnd_yy(Integer.toString(cal.get(Calendar.YEAR)));
			String mm =Integer.toString(cal.get(Calendar.MONTH)+1);
			if(mm.length() <2 ) mm = "0"+mm;
			if(searchVO.getEnd_mm().equals("")) {
				searchVO.setEnd_mm(mm);
			}else{
				if(searchVO.getEnd_mm().length() < 2 ) searchVO.setEnd_mm("0"+searchVO.getEnd_mm());
			}
			start_yymm =searchVO.getStart_yy()+""+searchVO.getStart_mm();
			end_yymm =searchVO.getEnd_yy()+""+searchVO.getEnd_mm();
			query += " AND to_char(Q.REG_DT,'yyyymm') BETWEEN '"+start_yymm+"' AND '"+end_yymm+"'";
			
			query += " ORDER BY Q.REG_DT DESC";
			
			openPreparedStatementForR(query, true);
			executeQueryForR(searchVO);
			
			while (rs.next()) {
				vo = new InquireVO();
				vo.setSeq(rs.getString("SEQ"));
				vo.setTitle(rs.getString("TITLE"));
				vo.setBoard_type(rs.getString("BOARD_TYPE"));
				vo.setCategory1(rs.getString("CATEGORY1"));
				vo.setReg_dt(rs.getString("REG_DT1"));
				vo.setReg_nm(rs.getString("REG_NM"));
				vo.setRead_count(rs.getInt("READ_COUNT"));
				vo.setStat(rs.getString("STAT"));
				
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
	
	/**
	 * Q&A SMS 발송하는 메소드
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
			
			String query = loadQueryString("sql.inquire.question.view");
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
			String sql = loadQueryString("sql.inquire.question.getSendHistorySeq");
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				history_seq = rs.getString("ID");
			}
			rs.close();
			pstmt.close();
			String insert_sql = loadQueryString("sql.inquire.question.getSendHistoryInsert");
			
			int param =1;
			
			openPreparedStatementForCUD(insert_sql);
						
			pstmt.setString(param++, history_seq);
			pstmt.setString(param++, "SMS");
			pstmt.setString(param++, "QNA");
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
	
	/**
	 * Q&A 이메일 발송하는 메소드
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
		String send_reg_dt = "";
		
		int i =0;
		try {
			getConnection(dsname);
			
			String query = loadQueryString("sql.inquire.question.view");
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
				send_reg_dt = rs.getString("REG_DT1");
				send_cont = Util.streamToBuf(rs.getCharacterStream("CONTENTS")).toString().replaceAll("\n", "<br>");
			
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
			//EmailMsgText = EmailMsgText.replaceAll("#server_ip#", "http://"+server_ip+":9998");
			msgText = msgText.replaceAll("#server_ip#", "http://rndcall.go.kr");
			msgText = msgText.replaceAll("#button#", "<img src='http://rndcall.go.kr/images/mail/Btn_Rndcall.gif' alt='도우미센터' border='0' />");
			
			
			if(send_yn.equals("Y")){
				MailSend MailSend = new MailSend();
				success = MailSend.MailSendMain(SmsList, msgText);
			}
			
			// SMS 발송이력등록
			String sql = loadQueryString("sql.inquire.question.getSendHistorySeq");
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				history_seq = rs.getString("ID");
			}
			rs.close();
			pstmt.close();
			String insert_sql = loadQueryString("sql.inquire.question.getSendHistoryInsert");
			
			int param =1;
			
			openPreparedStatementForCUD(insert_sql);
						
			pstmt.setString(param++, history_seq);
			pstmt.setString(param++, "EMAIL");
			pstmt.setString(param++, "QNA");
			pstmt.setString(param++, seq);
			pstmt.setString(param++, "");
			pstmt.setString(param++, "");
			pstmt.setString(param++, send_email);
			pstmt.setString(param++, "noreply@nstc.go.kr");
			pstmt.setString(param++, "발송");
			pstmt.setString(param++, String.valueOf(success));
			pstmt.setString(param++, login_id);
			
			executeQueryForCUD();
			pstmt.close();			
			// SMS 발송이력등록
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
	 * Q&A 조회 및 엑셀파일 생성메소드
	 */
	public InquireResultVO getInquireExcelDown(InquireSearchVO searchVO) throws SQLException, DAOBaseException {
		String file_nm = "";
		InquireVO vo = null;
		ArrayList voList = new ArrayList();

		InquireResultVO resultVO = new InquireResultVO();
		String start_yymm ="";
		String end_yymm ="";
		Reader content_clob = null;
		Reader answerContent_clob = null;
		
		try {
			
			getConnection(dsname);
			
			String query = loadQueryString("sql.inquire.question.getInquireExcelDown");
			//리스트 조회
			if(searchVO.getType().equals("1")){
				query += " AND NOT EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER AA WHERE Q.SEQ = AA.Q_SEQ) ";
			}else if(searchVO.getType().equals("2")){
				query += " AND EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER AA WHERE Q.SEQ = AA.Q_SEQ) ";
				query += " AND NVL(Q.PUBLIC_TRANS_YN,'N') != 'Y' ";
			}else if(searchVO.getType().equals("3")){
				query += " AND EXISTS (SELECT 1 FROM RNDCALL_BOARD_ANSWER AA WHERE Q.SEQ = AA.Q_SEQ) ";
				query += " AND NVL(Q.PUBLIC_TRANS_YN,'N') = 'Y' ";
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
					
				}
			}
			if(searchVO.getInsert_type() != null && !searchVO.getInsert_type().equals("")){
				query += " AND Q.INSERT_TYPE='" + searchVO.getInsert_type() + "'";
			}
			if(searchVO.getPublic_trans_yn() != null && !searchVO.getPublic_trans_yn().equals("")){
				query += " AND Q.PUBLIC_TRANS_YN='" + searchVO.getPublic_trans_yn() + "'";
			}
			Calendar cal = Calendar.getInstance();
			if(searchVO.getStart_yy().equals("")) searchVO.setStart_yy("2007");
			if(searchVO.getStart_mm().equals("")) searchVO.setStart_mm("01");
			if(searchVO.getEnd_yy().equals("")) searchVO.setEnd_yy(Integer.toString(cal.get(Calendar.YEAR)));
			String mm =Integer.toString(cal.get(Calendar.MONTH)+1);
			if(mm.length() <2 ) mm = "0"+mm;
			if(searchVO.getEnd_mm().equals("")) {
				searchVO.setEnd_mm(mm);
			}else{
				if(searchVO.getEnd_mm().length() < 2 ) searchVO.setEnd_mm("0"+searchVO.getEnd_mm());
			}
			
			start_yymm =searchVO.getStart_yy()+""+searchVO.getStart_mm();
			end_yymm =searchVO.getEnd_yy()+""+searchVO.getEnd_mm();
			query += " AND Q.REG_DT >= CAST('"+start_yymm +"01000000.000' AS DATETIME) AND Q.REG_DT <= CAST(TO_CHAR(LAST_DAY(CAST('"+end_yymm+"01' AS DATE)), 'YYYYMMDD') || '235959.999'  AS DATETIME)";
						
			query += " ORDER BY Q.REG_DT DESC";
			
			openPreparedStatementForR(query, false);
			executeQueryForR();
			
			while (rs.next()) {
				vo = new InquireVO();
				vo.setBoard_type(rs.getString("BOARD_TYPE_NM"));
				
				vo.setTitle(rs.getString("TITLE"));
//				content_clob = rs.getCharacterStream("CONTENTS");
//				vo.setContents(Util.streamToBuf(content_clob).toString());
				vo.setContents(rs.getString("CONTENTS"));
				vo.setCall_receive_yn(rs.getString("CELL_RECEIVE_YN_NM"));
				vo.setCell_number(rs.getString("CELL_NUMBER"));
				vo.setEmail_receive_yn(rs.getString("EMAIL_RECEIVE_YN_NM"));
				vo.setEmail(rs.getString("EMAIL"));
				vo.setOpen_yn(rs.getString("OPEN_YN"));
			
				vo.setPublic_trans_yn(rs.getString("PUBLIC_TRANS_YN_NM"));
				
				System.out.println("vo.getPublic_trans_yn()::"+vo.getPublic_trans_yn());
				
				vo.setPublic_trans_id(rs.getString("PUBLIC_TRANS_NM"));
				vo.setPublic_trans_org_nm(rs.getString("PUBLIC_TRANS_ORG_NM"));
				vo.setInsert_type(rs.getString("INSERT_TYPE"));
				vo.setNtis_reg_id(rs.getString("NTIS_REG_ID"));
				vo.setFile_id(rs.getString("FILE_ID"));
				vo.setDel_yn(rs.getString("DEL_YN"));
				vo.setRead_count(rs.getInt("READ_COUNT"));
				vo.setReg_id(rs.getString("REG_ID"));
				vo.setReg_dt(rs.getString("REG_DT1"));
				vo.setEdit_id(rs.getString("EDIT_ID"));
				vo.setEdit_dt(rs.getString("EDIT_DT"));
				vo.setReg_nm(rs.getString("REG_NM"));
				vo.setCategory1(rs.getString("CATEGORY1"));
				vo.setCategory2(rs.getString("CATEGORY2"));
//				answerContent_clob = rs.getCharacterStream("ANSWER_CONT");
//				vo.setAnswerContents(Util.streamToBuf(answerContent_clob).toString());
				vo.setAnswerContents(rs.getString("ANSWER_CONT"));
				vo.setAnswer_file_id(rs.getString("ANSWER_FILE_ID"));
				vo.setAnswer_del_yn(rs.getString("ANSWER_DEL_YN"));
//				vo.setAnswer_reg_nm(rs.getString("ANSWER_REG_NM"));
				vo.setAnswer_reg_dt(rs.getString("ANSWER_REG_DT"));
//				vo.setAnswer_edit_nm(rs.getString("ANSWER_EDIT_NM"));
				vo.setAnswer_edit_dt(rs.getString("ANSWER_EDIT_DT"));
				vo.setStat(rs.getString("STAT"));				
								
				voList.add(vo);
			}
			System.out.println("searchVO.getTotRowCount()::"+searchVO.getTotRowCount());
			System.out.println("voList.size()::"+voList.size());
			resultVO.setTotRowCount(searchVO.getTotRowCount());
			resultVO.setVoList(voList);
						
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			e.printStackTrace();
			System.out.println("getInquireExcelDown ERROR ::" +e.getMessage());
		} finally {
			close();
		}

		return resultVO;
	}
	
	/**
	 * Q&A 로그인 정보 조회하는 메소드
	 */
	public InquireResultVO getUserInfo(String id) throws SQLException, DAOBaseException {


		ArrayList voList = new ArrayList();

		InquireResultVO resultVO = new InquireResultVO();
		LoginVO loginVO = null;
		DesCipher dc = new DesCipher();
		
		try {
			getConnection(dsname);
			
			String query = loadQueryString("sql.inquire.question.userinfo");
			openPreparedStatementForR(query, false);
			
			pstmt.setString(1, id) ;
			
			executeQueryForR();				
			
			if (rs.next()) {
				loginVO = new LoginVO();
				
				loginVO.setMobile(rs.getString("CELL_NUMBER"));
				loginVO.setEmail(rs.getString("EMAIL"));
				loginVO.setName(rs.getString("NAME"));
				loginVO.setUsername(rs.getString("USER_ID"));
				loginVO.setOrg_nm(rs.getString("ORG_NM"));
				loginVO.setAttached_nm(rs.getString("ATTACHED_NM"));
			}
			resultVO.setLoginVO(loginVO);
			System.out.println(" 로그인 정보 조회하는 메소드 voList.size()::"+voList.size());
			
			
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
	 * Q&A 로그인 정보 조회하는 메소드
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
			mail_form += "<tr><td><img src='#server_ip#/images/mail/mail_Header.gif' alt='국가과학기술위원회 R&amp;D도우미센터' border='0' usemap='#Map' /></td></tr> \n";
			mail_form += "<tr><td style='font-size:13px; font-family:Gulim; line-height:20px; padding:20px 32px 20px 32px;'> \n";
			//mail_form += "		안녕하세요 R&D 도우미센터 운영자입니다. <br /> \n";
			mail_form += "		#MSG# <br /> \n";
			//mail_form += "		감사합니다 \n";
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

	/**
	 * Q&A 스크랩 등록하는 메소드
	 */
	public InquireResultVO getInquireScrap(InquireSearchVO searchVO) throws SQLException, DAOBaseException {

		boolean success = false;
		InquireResultVO resultVO = new InquireResultVO();
		InquireVO vo = null;
		String seq = "";
		
    	String mem_uid =  searchVO.getLoginId(); 
    	int mem_table_uid = Integer.parseInt(searchVO.getSeq());
    	String mem_table_nm = searchVO.getBoard_type();	
    	System.out.println("mem_uid"+mem_uid);
    	System.out.println("mem_table_uid"+mem_table_uid);
    	System.out.println("mem_table_nm"+mem_table_nm);
    	try {
	    	getConnection(dsname);	
	   		String query = "select board_seq from rndcall_scrap "
	   			+" where board_seq = ? " +
	   			" and board_type = ?" +
	   			" and reg_id = ? "; 
	   		openPreparedStatementForR(query, false);
	   		pstmt.setInt(1,mem_table_uid);
			pstmt.setString(2,mem_table_nm);
	  		pstmt.setString(3,mem_uid);
	  		//System.out.println("스크랩실행쿼리?"+query);
	  		
	   		executeQueryForR();
	   		if (this.rs.next()) {
	   		    vo = new InquireVO();	
	   		  	vo.setMem_table_nm("err");  
	   		    resultVO.setVo(vo);
	   		    //System.out.println("resultvo---------------------"+resultVO);
	   		   // System.out.println("보드타입?====="+resultVO.getVo().getMem_table_nm());
	   		} 
    	}catch (Exception e) {
			throwDAOBaseException(e, "not");
			return null;
		}finally {
			close();
		}
		String tbl_nm = resultVO.getVo().getMem_table_nm();
		//System.out.println("==============="+tbl_nm);
		
		if(tbl_nm.equals("err")){
			return resultVO;
		}
			
		try {
			//System.out.println("PASS===============");
			getConnection(dsname);	
			String sql = loadQueryString("sql.inquire.getScrapSeq");
			pstmt = conn.prepareStatement(sql);
			//System.out.println("실행==============="+sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				seq = rs.getString("ID");
			}
			rs.close();
			pstmt.close();
			
			String insert_sql = loadQueryString("sql.inquire.question.scrapinsert");
			openPreparedStatementForCUD(insert_sql);
						
			pstmt.setString(1, seq);
			pstmt.setString(2, mem_table_nm);
			pstmt.setInt(3, mem_table_uid);
			pstmt.setString(4, mem_uid);
			//pstmt.setString(5, "Y");
			System.out.println("searchVO.getLoginId()"+mem_table_nm);
			System.out.println("searchVO.getBoard_type()"+mem_table_uid);
			System.out.println("searchVO.getSeq()"+mem_uid);
			executeQueryForCUD();
			vo = new InquireVO();	
			vo.setMem_table_nm("pass"); 
			resultVO.setVo(vo);
			pstmt.close();
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
			return null;
		} finally {
			close();
		}
		return resultVO;
    }
	
	/**
	 * 비회원 비밀번호 확인
	 */
	public boolean getPasswordCheck(InquireVO vo) throws SQLException, DAOBaseException {
	    boolean result = false; 
	    try {
            getConnection(dsname);  
            String sql = loadQueryString("sql.inquire.getPasswordCheck");
            openPreparedStatementForR(sql, false);
            
            SSOUtil ssoUtil = new SSOUtil();
            pstmt.setString(1, vo.getSeq());
            pstmt.setString(2, ssoUtil.encrypt(vo.getPassword()));
            
            executeQueryForR();

            if (rs.next()) {
                result = true;
            }
            rs.close();
            pstmt.close();
            pstmt.close();
        } catch (Exception e) {
            throwDAOBaseException(e, "not");
        } finally {
            close();
        }
	    
	    return result;
	}
}

