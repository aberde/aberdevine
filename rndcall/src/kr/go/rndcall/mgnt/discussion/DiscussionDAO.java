package kr.go.rndcall.mgnt.discussion;

import java.io.CharArrayReader;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

//import oracle.jdbc.OracleResultSet;
//import oracle.sql.CLOB;

import org.apache.log4j.Logger;

//import kr.go.rndcall.mgnt.common.StringUtil;
import kr.go.rndcall.mgnt.common.Util;
import kr.go.rndcall.mgnt.common.BaseSearchVO;
import kr.go.rndcall.mgnt.common.BaseResultVO;
import kr.go.rndcall.mgnt.common.BaseVO;
import kr.go.rndcall.mgnt.common.DesCipher;
import kr.go.rndcall.mgnt.common.DAOBaseException;
import kr.go.rndcall.mgnt.common.FileVO;
import kr.go.rndcall.mgnt.discussion.DiscussionResultVO;
import kr.go.rndcall.mgnt.discussion.DiscussionSearchVO;
import kr.go.rndcall.mgnt.discussion.DiscussionVO;
import kr.go.rndcall.mgnt.common.BaseSqlDAO;

public class DiscussionDAO extends BaseSqlDAO {

	private Logger logger = Logger.getLogger(this.getClass().getName());
	private String dsname = "jdbc/rndcall";

	private String replaceSpChacter(String srcStr) {
		srcStr = srcStr.replaceAll("<", "&lt;");
		srcStr = srcStr.replaceAll(">", "&gt;");
		return srcStr;
	}
	
	private String replaceSpChacterRes(String srcStr) {
		srcStr = srcStr.replaceAll("&lt;", "<");
		srcStr = srcStr.replaceAll("&gt;", ">");
		return srcStr;
	}
	
	private String replaceSearchText(String srcStr) {
		srcStr = srcStr.replaceAll("'", "''");		
		return srcStr;
	}

	/**
	 * 
	 * CLOB �Է�
	 * 
	 * @param clob
	 * @param text
	 * @throws Exception
	 */
	public void writeClob(Clob clob, String text) throws Exception {
		Writer writer = null;
		Reader reader = null;
		try {

			writer = ((oracle.sql.CLOB) clob).getCharacterOutputStream();
			reader = new CharArrayReader(text.toCharArray());

			char[] buffer = new char[1024];
			int read = 0;
			while ((read = reader.read(buffer, 0, 1024)) != -1) {
				writer.write(buffer, 0, read);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			throw exception;
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (Exception exception) {
				}
			if (writer != null)
				try {
					writer.close();
				} catch (Exception exception) {
				}
		}
	}

	/*
	 * file_path �� ��ȣȭ�Ѵ�.
	 */
	private DiscussionResultVO encodeFilePath(DiscussionResultVO resultVO) {
		if(resultVO != null && resultVO.getVoList() != null) {
			ArrayList ar = resultVO.getVoList();
			ArrayList tmp = new ArrayList();
			System.out.println("###### : " + ar.size());
			for(int i=0; i<ar.size(); i++) {				
				DiscussionVO vo = (DiscussionVO)ar.get(i);
				System.out.println(vo.getFile_path());
				DesCipher dc = new DesCipher();
				vo.setFile_path(dc.Encode(vo.getFile_path()));
				tmp.add(vo);
			}
			resultVO.setVoList(tmp);
		}
		return resultVO;
	}
	
    /*
	 * 조회한 ResultSet 으로부터 결과를 가져와 자동으로 vo의 set메소드를 호출해서 세팅한 뒤, resultVO에 넣어 반환해준다.
	 * @param bVO : 결과를 담는vo 
	 * @param resultVO : bVO 와 조회된 총갯수를 담아 반환할 resultVO
	 * @param searchVO : getTotRowCount 로 총 row갯수를 알아내기위해 변수로 받았음.
	 * @return : resultVO
	 * @author 배재현
	 */
	public BaseResultVO retrieveResultVO(BaseVO bVO, BaseResultVO resultVO, BaseSearchVO searchVO) throws DAOBaseException {
		ArrayList voList = new ArrayList(); 	   	
		HashMap methodMap = new HashMap();
		java.lang.reflect.Method allMethod[] = bVO.getClass().getDeclaredMethods();
		for(int i=0; i<allMethod.length; i++ ) {
			String methodType = allMethod[i].getName().substring(0, 3);
			if(methodType.equals("set")) {
				String methodNm = allMethod[i].getName().substring(3,allMethod[i].getName().length()).toUpperCase();
//				System.out.println("mehtodNm : " + methodNm);
				methodMap.put(methodNm, allMethod[i]);
			}
		}
				
		try {
			ResultSetMetaData rsmd = rs.getMetaData(); 
			while (rs.next()) {			
				java.lang.reflect.Method setMethod = null;
				Object obj = bVO.getClass().forName(bVO.getClass().getName()).newInstance();
				for(int i=1; i<=rsmd.getColumnCount(); i++) {
					setMethod = (java.lang.reflect.Method)methodMap.get(rsmd.getColumnName(i).toUpperCase());
//					System.out.println("## setMethod Name : " + setMethod.getName());
					if(setMethod != null && !setMethod.equals("")) {
//						System.out.println("## value : " + rs.getString(rsmd.getColumnName(i)));
						// CLOB 데이터 처리
//						Clob clobData = null;
//						Class clobClass = null;
						String strValue = "";
//						StringBuffer contentsBuffer = null;
						try {
//							clobData = rs.getClob(rsmd.getColumnName(i));
//							Reader reader = rs.getCharacterStream(rsmd.getColumnName(i));
//							if (reader != null){
//								contentsBuffer = new StringBuffer();
//								char[] buf = new char[1024];
//								int k;
//								while ((k = reader.read(buf, 0, 2048)) != -1) {
//									contentsBuffer.append(buf, 0, k);
//								}
//								strValue = contentsBuffer.toString();
//							} else {
//								strValue = "";
//							}
//							reader.close();
							strValue = rs.getString(rsmd.getColumnName(i));
						} catch(Exception err) { 
							strValue = rs.getString(rsmd.getColumnName(i));
						}
						String[] dbResultValue = {strValue};						
						setMethod.invoke(obj, dbResultValue);
					}
				}
				voList.add(obj);				
			}
			if(voList.isEmpty()) voList = null;
			// paging 인 경우에만 totRowCount 가 존재함.
			int totRowCount = searchVO.getTotRowCount().intValue();
			if(searchVO.getTotRowCount() != null && totRowCount != 0) {
				resultVO.setTotRowCount(searchVO.getTotRowCount());
			}
			resultVO.setVoList(voList);
		// Exception 하나로 다 잡을수 있지만, 이러이러한 Exception이 발생할수 있음을 명시하게위해 여러개로 catch함.
		} catch(SQLException se) {
			se.printStackTrace();			
		} catch(IllegalAccessException ie) {			
			ie.printStackTrace();			
		} catch(InvocationTargetException ite) {			
			ite.printStackTrace();			
		} catch(Exception e) {			
			e.printStackTrace();			
		}
				
		return resultVO;
	}
	
	/*
	 * 조회한 ResultSet 으로부터 결과를 가져와 자동으로 vo의 set메소드를 호출해서 세팅.
	 * 상세내용 조회와 같이 List 형 데이터가 아닌 vo 형 데이터를 반환할 때 사용
	 * @param bVO : 결과를 담는vo	
	 * @return : Object - 각 VO객체 형태로 형변환하여 사용할것. 
	 * @author 배재현
	 */
	public BaseResultVO retrieveVO(BaseVO bVO, BaseResultVO resultVO) throws DAOBaseException {
		Object voObj = null;
		Object resultVoObj = null;
		BaseResultVO baseResultVO = null;
		boolean isEmptyResult = true;
		HashMap methodMap = new HashMap();
		java.lang.reflect.Method allMethod[] = bVO.getClass().getDeclaredMethods();
		for(int i=0; i<allMethod.length; i++ ) {
			String methodType = allMethod[i].getName().substring(0, 3);
			if(methodType.equals("set")) {
				String methodNm = allMethod[i].getName().substring(3,allMethod[i].getName().length()).toUpperCase();
				methodMap.put(methodNm, allMethod[i]);				
			}
		}
				
		try {
			ResultSetMetaData rsmd = rs.getMetaData();			
			while (rs.next()) {
				isEmptyResult = false;
				java.lang.reflect.Method setMethod = null;
				voObj = bVO.getClass().forName(bVO.getClass().getName()).newInstance();
				for(int i=1; i<=rsmd.getColumnCount(); i++) {
					setMethod = (java.lang.reflect.Method)methodMap.get(rsmd.getColumnName(i));
					if(setMethod != null && !setMethod.equals("")) {

						Clob clobData = null;
						Class clobClass = null;
						String strValue = "";
						StringBuffer contentsBuffer = null;
						try {
							clobData = rs.getClob(rsmd.getColumnName(i));
							Reader reader = rs.getCharacterStream(rsmd.getColumnName(i));
							if (reader != null){
								contentsBuffer = new StringBuffer();
								char[] buf = new char[1024];
								int k;
								while ((k = reader.read(buf, 0, 2048)) != -1) {
									contentsBuffer.append(buf, 0, k);
								}
								strValue = contentsBuffer.toString();
							} else {
								strValue = "";
							}
							reader.close();
						} catch(Exception err) { 
							strValue = rs.getString(rsmd.getColumnName(i));
						}
						String[] dbResultValue = {strValue};						
						setMethod.invoke(voObj, dbResultValue);
					}
				}
				resultVoObj = resultVO.getClass().forName(resultVO.getClass().getName()).newInstance();				
				java.lang.reflect.Method method = resultVoObj.getClass().getDeclaredMethod("setVo", new Class[]{bVO.getClass()});
				Object[] voObjValue = {voObj};
				method.invoke(resultVoObj, voObjValue);
			}
			if(isEmptyResult) {
				baseResultVO = null;
			} else {
				baseResultVO = (BaseResultVO)resultVoObj;
			}
		// Exception 하나로 다 잡을수 있지만, 이러이러한 Exception이 발생할수 있음을 명시하게위해 여러개로 catch함.
		} catch(SQLException se) {
			se.printStackTrace();			
		} catch(IllegalAccessException ie) {			
			ie.printStackTrace();			
		} catch(InvocationTargetException ite) {			
			ite.printStackTrace();			
		} catch(Exception e) {			
			e.printStackTrace();			
		}
			
		return baseResultVO;
	}

	/*
	 * 조회수를 올린다.
	 */
	public void updateDiscussCount(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {				
		try {
			getConnection(dsname);			
			String query = loadQueryString("sql.discussion.updateDiscussCount");			
			openPreparedStatementForCUD(query);
			int param = 1;
			pstmt.setString(param++, searchVO.getDiscuss_id());
			pstmt.executeQuery();
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {			
			close();
		}
	}
	
	/*
	 * 토론을 생성한다.
	 */
	public void createDiscuss(DiscussionVO vo, DiscussionSearchVO searchVO, FileVO[] fileInfo) throws SQLException, DAOBaseException {				
		try {
			getConnection(dsname);
//			getConnection_clob(); //getConnection(dsname); //getConnection_clob();
			conn.setAutoCommit(false);
			
			// rndcall_discuss 의 discuss_id를 따온다.
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select rndcall_discuss_seq.next_value as next_discuss_id ");
			pstmt = conn.prepareStatement(sbQuery.toString());
			rs = pstmt.executeQuery();
			int next_discuss_id = 0;
			if(rs.next()) {
				next_discuss_id = rs.getInt("next_discuss_id") ;
			}
			
			String query = loadQueryString("sql.discussion.createDiscussion");
			openPreparedStatementForCUD(query);
			int param = 1;	
			pstmt.setInt(param++, next_discuss_id);
			pstmt.setString(param++, vo.getCategory_id());
			pstmt.setString(param++, vo.getDis_title());			
			pstmt.setString(param++, replaceSpChacter(vo.getDis_contents()));
			pstmt.setString(param++, searchVO.getUsername());
			pstmt.setString(param++, searchVO.getUsername());
			logger.debug("pstmt.execute()...");
			executeQueryForCUD();
			
			if(fileInfo != null && fileInfo.length != 0) {
				for(int i=0; i<fileInfo.length; i++) {
					if (fileInfo[i] != null) {
						// 파일_id 시퀀스 번호를 따온다.
						sbQuery = new StringBuffer();
						sbQuery.append("select rndcall_file_id_seq.next_value as next_file_id ");
						pstmt = conn.prepareStatement(sbQuery.toString());
						rs = pstmt.executeQuery();
						int next_file_id = 0;
						if(rs.next()) {
							next_file_id = rs.getInt("next_file_id") ;
						}
						// rndcall_file 테이블에 저장한다.
						query = loadQueryString("sql.discussion.createTcoFile");
						openPreparedStatementForCUD(query);
						param = 1;	
						pstmt.setInt(param++, next_file_id);
						pstmt.setString(param++, fileInfo[i].getInputFileName());
						pstmt.setString(param++, fileInfo[i].getAbsolutePath() + fileInfo[i].getOutputFileName());
						pstmt.setString(param++, fileInfo[i].getSize());					
						pstmt.setString(param++, searchVO.getUsername());
						pstmt.setString(param++, searchVO.getUsername());
						logger.debug("pstmt.execute()...");
						executeQueryForCUD();
						
						// rndcall_file 과 토론ID 매핑파일을 저장한다.
						query = loadQueryString("sql.discussion.createDiscussFileMapping");
						openPreparedStatementForCUD(query);
						param = 1;	
						pstmt.setInt(param++, next_file_id);
						pstmt.setInt(param++, next_discuss_id);					
						logger.debug("pstmt.execute()...");
						executeQueryForCUD();
					}
				}
				
			}
			// 첨부파일 ID를 따온다.
			conn.commit();
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {
			try { conn.setAutoCommit(true); } catch(Exception e1) {}
			close();
		}
	}
	
	/*
	 * 분야별 토론 목록을 가져온다.
	 */
	public DiscussionResultVO retrieveDiscussList(DiscussionVO vo, DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {
		DiscussionResultVO resultVO = null;
		DiscussionResultVO rpResultVO = null;
		DiscussionResultVO result = new DiscussionResultVO();
		ArrayList resultList = null;
		try {
			getConnection(dsname);
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(loadQueryString("sql.discussion.retrieveDiscussList"));
			sbQuery.append(" order by e.discuss_id desc");
			openPreparedStatementForR(sbQuery.toString(),  false);
			int param = 1;	
			pstmt.setString(param++, searchVO.getCategory_id());			
			logger.debug("pstmt.execute()...");
			executeQueryForR();
			resultVO = (DiscussionResultVO)retrieveResultVO(new DiscussionVO(), new DiscussionResultVO(), searchVO);
			ArrayList discussList = resultVO.getVoList();
			if(discussList != null && !discussList.isEmpty()) {
				resultList = new ArrayList();
				for(int i=0; i<discussList.size(); i++) {
					DiscussionVO disVO = (DiscussionVO)discussList.get(i);
					String discuss_id = disVO.getDiscuss_id();
					sbQuery = new StringBuffer();
					sbQuery.append(loadQueryString("sql.discussion.retrieveDiscussReplyList"));
					openPreparedStatementForR(sbQuery.toString(),  false);
					System.out.println(sbQuery.toString());
					param = 1;	
					pstmt.setString(param++, discuss_id);
					logger.debug("pstmt.execute()...");
					executeQueryForR();
					rpResultVO = (DiscussionResultVO)retrieveResultVO(new DiscussionVO(), new DiscussionResultVO(), searchVO);
					
					if(rpResultVO != null) {
						if(rpResultVO.getVoList() != null) {
							ArrayList list = rpResultVO.getVoList();
							ArrayList wList = new ArrayList();
							for(int j=0; j<list.size(); j++) {
								DiscussionVO tmpVo = (DiscussionVO)list.get(j);
								String cmt = tmpVo.getDis_comment();
								cmt = cmt.replaceAll("<P>", "")
								         .replaceAll("</P>", " ")
								         .replaceAll("<P align=center>", "")
								         .replaceAll("<P align=right>", "")
								         .replaceAll("<P align=left>", "")
								         .replaceAll("\\n", "")
								         .replaceAll("\\r", "");
								
								tmpVo.setDis_comment(cmt);
								wList.add(tmpVo);
							}
							rpResultVO.setVoList(wList);
						}
						disVO.setReply_list(rpResultVO.getVoList());
					}
					resultList.add(disVO);
				}				
				result.setVoList(resultList);
			} else {
				result = null;
			}
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {
			close();
		}
		return result;
	}

	/*
	 * 토론 상세화면
	 */
	public DiscussionResultVO retrieveDiscussDetail(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {
		DiscussionResultVO resultVO = null;
		try {
			getConnection(dsname);
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(loadQueryString("sql.discussion.retrieveDiscussDetail"));
			openPreparedStatementForR(sbQuery.toString(),  false);
			int param = 1;	
			pstmt.setString(param++, "1");
			logger.debug("pstmt.execute()...");
			executeQueryForR();
			resultVO = (DiscussionResultVO) retrieveVO(new DiscussionVO(), new DiscussionResultVO());
			System.out.println("### searchVO.getDiscuss_id() : " + searchVO.getDiscuss_id());
			System.out.println("### vo.discuss_id() : " + resultVO.getVo().getDiscuss_id());
			System.out.println("### vo.dis_contents() : " + resultVO.getVo().getDis_contents());
			String query = " select count(discuss_op_id) as dis_op_cnt " +
					"from rndcall_discuss_opin a " +
					"where is_del = 'N' " +
					"and p_discuss_op_id is null " +
					"and discuss_id = ?";
			if(searchVO.getSearch_text() != null && !searchVO.getSearch_text().equals("")) {
				if(searchVO.getSearch_type() != null && searchVO.getSearch_type().equals("10601")) {
					query += " and a.reg_id like '%"+replaceSearchText(searchVO.getSearch_text())+"%'";
				}
				if(searchVO.getSearch_type() != null && searchVO.getSearch_type().equals("10602")) {
					query += " and clob_to_char(a.dis_comment) like '%"+replaceSearchText(searchVO.getSearch_text())+"%' ";
				}
			}
			openPreparedStatementForR(query,  false);
			param = 1;	
			pstmt.setString(param++, searchVO.getDiscuss_id());
			executeQueryForR();
			while(rs.next()) {
				resultVO.getVo().setDis_op_cnt(rs.getString("dis_op_cnt"));
			}
			
			query = "select count(discuss_op_id) as dis_reply_cnt "
					+" from rndcall_discuss_opin a"
					+" where discuss_id = ? "
					+" and is_del = 'N' "
					+" and p_discuss_op_id is not null ";
			if(searchVO.getSearch_text() != null && !searchVO.getSearch_text().equals("")) {
				if(searchVO.getSearch_type() != null && searchVO.getSearch_type().equals("10601")) {
					query += " and a.reg_id like '%"+replaceSearchText(searchVO.getSearch_text())+"%'";
				}
				if(searchVO.getSearch_type() != null && searchVO.getSearch_type().equals("10602")) {
					query += " and CLOB_TO_CHAR(a.dis_comment) like '%"+replaceSearchText(searchVO.getSearch_text())+"%' ";
				}
			}
			openPreparedStatementForR(query,  false);
			param = 1;	
			pstmt.setString(param++, searchVO.getDiscuss_id());
			executeQueryForR();
			while(rs.next()) {
				resultVO.getVo().setDis_reply_cnt(rs.getString("dis_reply_cnt"));
			}
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {
			close();
		}
		return resultVO;
	}
	
	/*
	 * 토론 상세의 본문을 가져온다.
	 */
	public DiscussionResultVO retrieveDiscussContents(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {
		DiscussionResultVO listResultVO = null;
		DiscussionResultVO fileResultVO = null;
		DiscussionResultVO resultVO = new DiscussionResultVO();
		ArrayList resultList = null;
		DiscussionVO vo1 = null;
		ArrayList voList = new ArrayList();
		try {
			getConnection(dsname);
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(loadQueryString("sql.discussion.retrieveDiscussContents"));
			openPreparedStatementForR(sbQuery.toString(),  false);
			int param = 1;	
			pstmt.setString(param++, searchVO.getDiscuss_id());
			logger.debug("pstmt.execute()...");
			executeQueryForR();
			
			
//			listResultVO = (DiscussionResultVO)retrieveResultVO(new DiscussionVO(), new DiscussionResultVO(), searchVO);
			
			while (rs.next()) {
				vo1 = new DiscussionVO();
				
				vo1.setDiscuss_id(rs.getString("discuss_id"));
				System.out.println("getDiscuss_id1" + vo1.getDiscuss_id());
				vo1.setDis_cont_id(rs.getString("dis_cont_id"));
				vo1.setDis_contents(rs.getString("dis_contents"));
				vo1.setReg_dt(rs.getString("Reg_dt"));
				voList.add(vo1);
			}
			listResultVO = new DiscussionResultVO();
			listResultVO.setVoList(voList);
			
			if(listResultVO.getVoList() != null) {
				resultList = new ArrayList();
				ArrayList contentList = listResultVO.getVoList();
				for(int i=0; i<contentList.size(); i++) {
					DiscussionVO vo = (DiscussionVO)contentList.get(i);
					String query = loadQueryString("sql.discussion.retrieveDiscussContentsFile");
					openPreparedStatementForR(query,  false);
					param = 1;	
					pstmt.setString(param++, vo.getDiscuss_id());
					pstmt.setString(param++, vo.getDis_cont_id());
					executeQueryForR();
					fileResultVO = (DiscussionResultVO)retrieveResultVO(new DiscussionVO(), new DiscussionResultVO(), searchVO);
					fileResultVO = encodeFilePath(fileResultVO);
					vo.setContent_file_list(fileResultVO.getVoList());
					resultList.add(vo);
				}
				resultVO.setVoList(resultList);
			}
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {
			close();
		}
		return resultVO;
	}
	
	/*
	 * 토론 상세화면 첨부파일을 가져온다.
	 */
	public DiscussionResultVO retrieveDiscussDetailFile(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {
		DiscussionResultVO resultVO = null;
		try {
			getConnection(dsname);
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(loadQueryString("sql.discussion.retrieveDiscussDetailFile"));
			openPreparedStatementForR(sbQuery.toString(),  false);
			int param = 1;	
			pstmt.setString(param++, searchVO.getDiscuss_id());
			logger.debug("pstmt.execute()...");
			executeQueryForR();
			resultVO = (DiscussionResultVO)retrieveResultVO(new DiscussionVO(), new DiscussionResultVO(), searchVO);
			resultVO = encodeFilePath(resultVO);
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {
			close();
		}
		return resultVO;
	}
	
	/*
	 * 토론 상세화면 댓글과 첨부파일을 가져온다.
	 */
	public DiscussionResultVO retrieveDiscDtlReplyFile(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {
		DiscussionResultVO resultVO = null;
		DiscussionResultVO fResultVO = null;
		ArrayList arrList = null;
		DiscussionResultVO result = new DiscussionResultVO();
		DiscussionVO dVo = null;
		try {
			getConnection(dsname);
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(loadQueryString("sql.discussion.retrieveDiscussDetailReply"));
			sbQuery.append(" and a.discuss_id = '").append(searchVO.getDiscuss_id()).append("' ")
			       .append(" and a.discuss_id = b.discuss_id ")
			       .append(" and b.is_del = 'N' ");
			System.out.println("######## : " + searchVO.getSearch_type());			
			if(searchVO.getSearch_type() != null && searchVO.getSearch_type().equals("10601")) {
				sbQuery.append(" and b.reg_id like '%").append(replaceSearchText(searchVO.getSearch_text())).append("%' ");
			}
			if(searchVO.getSearch_type() != null && searchVO.getSearch_type().equals("10602")) {
				sbQuery.append(" and clob_to_char(b.dis_comment like) '%").append(replaceSearchText(searchVO.getSearch_text())).append("%' ");
			}
		    sbQuery.append("    start with b.p_discuss_op_id is null ")
				   .append(" 	  connect by prior b.discuss_op_id = b.p_discuss_op_id ")
				   .append(" 	  order siblings by reg_dt desc ");
			openPreparedStatementForR(sbQuery.toString(),  false);			
			logger.debug("pstmt.execute()...");
			executeQueryForR();
			//resultVO = (DiscussionResultVO)retrieveResultVO(new DiscussionVO(), new DiscussionResultVO(), searchVO);
			ArrayList tmpList = new ArrayList();
			Stack st = new Stack();			
			while(rs.next()) {
				dVo = new DiscussionVO();
				if(rs.getString("level").equals("1")) {
					// 스택이 비어있는지 검사
					if(!st.isEmpty()) {
						// 비어있지 않다면 pop
						//System.out.println("######## stack size : " + st.size());
						int stSize = st.size();
						for(int i=0; i<stSize; i++) {
							//System.out.println("!!! i : " + i);
							DiscussionVO v = (DiscussionVO)st.pop();
							//System.out.println(v.getDis_comment());
							tmpList.add(v);
						}												
					}
				} 
				dVo.setLevel(rs.getString("level"));
				dVo.setDiscuss_op_id(rs.getString("discuss_op_id"));
				dVo.setDis_comment(rs.getString("dis_comment"));
				dVo.setReg_nm(rs.getString("reg_nm"));
				dVo.setReg_id(rs.getString("reg_id"));
				dVo.setReg_dt(rs.getString("reg_dt"));
				dVo.setRecom_cnt(rs.getString("recom_cnt"));
				dVo.setOpp_cnt(rs.getString("opp_cnt"));
				dVo.setP_discuss_op_id(rs.getString("p_discuss_op_id"));
				dVo.setMan_type_nm(rs.getString("man_type_nm"));
				// 답글은 일단 스택에 넣는다.
				if(rs.getString("level").equals("2")) {
					st.push(dVo);
				} else {
					tmpList.add(dVo);
				}
			}
			// 여기서 스택이 비어있지 않으면 의견이 하나밖에 없었고, 밑에 답글이 있었다는말임.
			// 스택에서 꺼내준다.
			if(!st.isEmpty()) {
				// 비어있지 않다면 pop			
				int stSize = st.size();
				for(int i=0; i<stSize; i++) {					
					DiscussionVO v = (DiscussionVO)st.pop();					
					tmpList.add(v);
				}												
			}
			resultVO = new DiscussionResultVO();
			resultVO.setVoList(tmpList);
			ArrayList replyList = resultVO.getVoList();
			if(replyList != null && !replyList.isEmpty()) {
				arrList = new ArrayList();
				for(int i=0; i<replyList.size(); i++) {
					DiscussionVO disOpVO = (DiscussionVO)replyList.get(i);
					
					String cmt = disOpVO.getDis_comment();
					//System.out.println("before : " + cmt);
					cmt = cmt.replaceAll("\r\n", "<br/>");
					//System.out.println("after : " + cmt);
					disOpVO.setDis_comment(cmt);
					
					String discuss_op_id = disOpVO.getDiscuss_op_id();
					sbQuery = new StringBuffer();
					sbQuery.append(loadQueryString("sql.discussion.retrieveDiscussDetailReplyFile"));
					openPreparedStatementForR(sbQuery.toString(),  false);					
					int param = 1;	
					pstmt.setString(param++, discuss_op_id);
					logger.debug("pstmt.execute()...");
					executeQueryForR();
					fResultVO = (DiscussionResultVO)retrieveResultVO(new DiscussionVO(), new DiscussionResultVO(), searchVO);
					
					if(fResultVO != null) {
						fResultVO = encodeFilePath(fResultVO);
						disOpVO.setReply_file_list(fResultVO.getVoList());
					}
					arrList.add(disOpVO);
				}
				result.setVoList(arrList);
			} else {
				result = null;
			}
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {
			close();
		}
		return result;
	}

	/*
	 * 토론방내용을 가져온다.
	 */
	public DiscussionResultVO retrieveDiscussListMain(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {
		DiscussionResultVO resultVO = new DiscussionResultVO();
		ArrayList voList = new ArrayList();
		DiscussionVO vo = null;
		DiscussionVO fileVO = null;
		DiscussionVO replyVO = null;
		PreparedStatement pst = null;
		ResultSet rst = null;
		try {
			getConnection(dsname);
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append(loadQueryString("sql.discussion.retrieveDiscussDetailList"));			
			if(searchVO.getSearch_type() != null && searchVO.getSearch_type().equals("10601")) {
				sbQuery.append(" and b.reg_id like '%").append(replaceSearchText(searchVO.getSearch_text())).append("%' ");
			}
			if(searchVO.getSearch_type() != null && searchVO.getSearch_type().equals("10602")) {
				sbQuery.append(" and CLOB_TO_CHAR(b.dis_comment) like '%").append(replaceSearchText(searchVO.getSearch_text())).append("%' ");
			}
		    sbQuery.append(" order by b.reg_dt desc ");
		    String qry = sbQuery.toString();
		    qry = qry.replaceFirst("\\[1\\]", searchVO.getDiscuss_id());
			openPreparedStatementForR(qry,  true);
			logger.debug("pstmt.execute()...");
			executeQueryForR(searchVO);
			while(rs.next()) {
				vo = new DiscussionVO();
				vo.setDiscuss_op_id(rs.getString("discuss_op_id"));
				vo.setDis_comment(rs.getString("dis_comment"));
				vo.setReply_cnt(rs.getString("reply_cnt"));
				vo.setReg_nm(rs.getString("reg_nm"));
				vo.setReg_id(rs.getString("reg_id"));
				vo.setReg_dt(rs.getString("reg_dt"));
				vo.setRecom_cnt(rs.getString("recom_cnt"));
				vo.setOpp_cnt(rs.getString("opp_cnt"));
				vo.setP_discuss_op_id(rs.getString("p_discuss_op_id"));
				vo.setYmd_reg_dt(rs.getString("ymd_reg_dt"));
				voList.add(vo);
			}
			// 첨부파일을 가져온다.				
			for(int i=0; i<voList.size(); i++) {				
				DiscussionVO childForVO = (DiscussionVO)voList.get(i);
				String discuss_op_id = childForVO.getDiscuss_op_id();
				
				ArrayList fileVoList = new ArrayList();
				sbQuery = new StringBuffer();
				sbQuery.append(loadQueryString("sql.discussion.retrieveDiscussAttchFile"));
				openPreparedStatementForR(sbQuery.toString(),  false);
				pstmt.setString(1, discuss_op_id);
				logger.debug("pstmt.execute()...");
				executeQueryForR();
				while(rs.next()) {
					fileVO = new DiscussionVO();
					fileVO.setFile_id(rs.getString("file_id"));
					fileVO.setFile_nm(rs.getString("file_nm"));
					DesCipher dc = new DesCipher();					
					fileVO.setFile_path(dc.Encode(rs.getString("file_path")));
					fileVO.setFile_size(rs.getString("file_size"));
					fileVoList.add(fileVO);
				}
				if(!fileVoList.isEmpty()) {
					childForVO.setReply_file_list(fileVoList);
				}
				
				// 답글을 가져온다.
				ArrayList replyVoList = new ArrayList();
				sbQuery = new StringBuffer();
				sbQuery.append(loadQueryString("sql.discussion.retrieveDiscussReply"));
				openPreparedStatementForR(sbQuery.toString(),  false);
				System.out.println("$$$$$$ 2 :" + discuss_op_id);
				System.out.println("$$$$$$ 1 :" + searchVO.getDiscuss_id());
				pstmt.setString(1, searchVO.getDiscuss_id());
				pstmt.setString(2, discuss_op_id);				
				logger.debug("pstmt.execute()...");
				executeQueryForR();
				while(rs.next()) {
					replyVO = new DiscussionVO();
					replyVO.setDiscuss_op_id(rs.getString("discuss_op_id"));
					replyVO.setDis_comment(rs.getString("dis_comment"));
					replyVO.setReg_nm(rs.getString("reg_nm"));
					replyVO.setReg_id(rs.getString("reg_id"));
					replyVO.setReg_dt(rs.getString("reg_dt"));
					replyVO.setRecom_cnt(rs.getString("recom_cnt"));
					replyVO.setOpp_cnt(rs.getString("opp_cnt"));
					replyVO.setP_discuss_op_id(rs.getString("p_discuss_op_id"));
					// 답글의 첨부파일을 가져온다.
					ArrayList repFileList = new ArrayList();
					StringBuffer query = new StringBuffer();
					DiscussionVO repFileVO = null;
					query.append(loadQueryString("sql.discussion.retrieveDiscussAttchFile"));
					pst = conn.prepareStatement(query.toString());
					pst.setString(1, replyVO.getDiscuss_op_id());
					rst = pst.executeQuery();
					while(rst.next()) {
						repFileVO = new DiscussionVO();
						repFileVO.setFile_id(rst.getString("file_id"));
						repFileVO.setFile_nm(rst.getString("file_nm"));
						DesCipher dc = new DesCipher();		
						repFileVO.setFile_path(dc.Encode(rst.getString("file_path")));
						repFileVO.setFile_size(rst.getString("file_size"));
						repFileList.add(repFileVO);
					}
					if(!repFileList.isEmpty()) {
						replyVO.setReply_reply_file_list(repFileList);
					}
					
					replyVoList.add(replyVO);
				}
				if(!replyVoList.isEmpty()) {
					childForVO.setReply_list(replyVoList);
				}
			}
			resultVO.setVoList(voList);
			resultVO.setTotRowCount(searchVO.getTotRowCount());
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {
			if(pst != null) pst.close();
			if(rst != null) rst.close();
			close();
		}
		return resultVO;
	}

	/*
	 * 토론 댓글을 저장한다.
	 */
	public String createDiscussOpin(DiscussionVO vo, DiscussionSearchVO searchVO, FileVO[] fileInfo) throws SQLException, DAOBaseException {
		String new_discuss_op_id = "";
		try {
			getConnection(dsname);
//			getConnection_clob();
			conn.setAutoCommit(false);
			System.out.println("@@@@@@@@@@@@ : "+ vo.getDis_comment());
			// 댓글 카운트를 1 올린다.
			String repQuery = loadQueryString("sql.discussion.updateDiscussReplyCount");
			openPreparedStatementForCUD(repQuery);			
			int param1 = 1;			
			pstmt.setString(param1++, vo.getDiscuss_id());
			executeQueryForCUD();
			
			// rndcall_discuss 의 discuss_id를 따온다.
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select rndcall_discuss_opin_seq.next_value as next_discuss_opin_id ");
			pstmt = conn.prepareStatement(sbQuery.toString());
			rs = pstmt.executeQuery();
			int next_discuss_opin_id = 0;
			if(rs.next()) {
				next_discuss_opin_id = rs.getInt("next_discuss_opin_id") ;
			}
			System.out.println("next : " +next_discuss_opin_id);
			System.out.println("discuss_id : " +vo.getDiscuss_id());

			String query = "insert into rndcall_discuss_opin ( "
						  +"   discuss_op_id  "
						  +"  ,discuss_id     "   
						  +" ,reg_id     "
						  +" ,reg_dt     "
						  +" ,dis_comment "
						  +" ,man_type "
						  +" ,outcom_use_cd "
						  +" ,edit_id"
						  +" ,edit_dt"
						  +"	) values ( "
						  +"   '"+next_discuss_opin_id+"'"
						  +" ,'"+vo.getDiscuss_id()+"'"
						  +" ,'"+searchVO.getUsername()+"'"
						  +" ,sysdatetime"
						  +" ,CHAR_TO_CLOB('" + replaceSpChacter(vo.getDis_comment()) + "') "
						  +" ,''"
						  +" ,''"
						  +" ,'"+searchVO.getUsername()+"'"
						  +" ,sysdatetime"
						  +"	) ";	
			//penPreparedStatementForCUD(query);
			Statement stmt = conn.createStatement(); 
			stmt.executeUpdate(query);
			
			
			// 첨부파일이 있다면 첨부파일을 저장한다.
			if(fileInfo != null && fileInfo.length != 0) {
				for(int i=0; i<fileInfo.length; i++) {
					if (fileInfo[i] != null) {
						// 파일_id 시퀀스 번호를 따온다.
						sbQuery = new StringBuffer();
						sbQuery.append("select rndcall_file_id_seq.next_value as next_file_id ");
						pstmt = conn.prepareStatement(sbQuery.toString());
						rs = pstmt.executeQuery();
						int next_file_id = 0;
						if(rs.next()) {
							next_file_id = rs.getInt("next_file_id") ;
						}
						// rndcall_file 테이블에 저장한다.
						query = loadQueryString("sql.discussion.createTcoFile");
						openPreparedStatementForCUD(query);
						int param = 1;	
						pstmt.setInt(param++, next_file_id);
						pstmt.setString(param++, fileInfo[i].getInputFileName());
						pstmt.setString(param++, fileInfo[i].getAbsolutePath() + fileInfo[i].getOutputFileName());
						pstmt.setString(param++, fileInfo[i].getSize());					
						pstmt.setString(param++, searchVO.getUsername());
						pstmt.setString(param++, searchVO.getUsername());
						logger.debug("pstmt.execute()...");
						executeQueryForCUD();
						
						// rndcall_file 과 토론ID 매핑파일을 저장한다.
						query = loadQueryString("sql.discussion.createDisReplyFileMapping");
						openPreparedStatementForCUD(query);
						param = 1;	
						pstmt.setInt(param++, next_file_id);
						pstmt.setInt(param++, next_discuss_opin_id);					
						logger.debug("pstmt.execute()...");
						executeQueryForCUD();
					}
				}
				
			}
			
			// 마일리지를 저장한다.
			String cate_id = "";
			String dis_id = vo.getDiscuss_id();
			if(dis_id.equals("8") || dis_id.equals("9") || dis_id.equals("11") || dis_id.equals("12")|| dis_id.equals("13")) {
				cate_id = "1";
			} else {
				cate_id = "2";
			}
//			MileageLog.saveMileageOpin(searchVO.getUsername(), String.valueOf(next_discuss_opin_id), cate_id);
			new_discuss_op_id = Integer.toString(next_discuss_opin_id);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throwDAOBaseException(e, "not");
		} finally {
			try { conn.setAutoCommit(true); } catch(Exception e1) {}
			close();
		}
		return new_discuss_op_id;
	}
	
	/*
	 * 토론 댓글의 댓글을 저장한다.
	 */
	public void createDiscussOpinOpin(DiscussionVO vo, DiscussionSearchVO searchVO, FileVO[] fileInfo) throws SQLException, DAOBaseException {				
		try {
//			getConnection_clob();
			getConnection(dsname);
			conn.setAutoCommit(false);
			
			// rndcall_discuss �� discuss_id�� ��´�.
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select rndcall_discuss_opin_seq.next_value as next_discuss_opin_id ");
			pstmt = conn.prepareStatement(sbQuery.toString());
			rs = pstmt.executeQuery();
			int next_discuss_opin_id = 0;
			if(rs.next()) {
				next_discuss_opin_id = rs.getInt("next_discuss_opin_id") ;
			}
			System.out.println("###### next_discuss_opin_id : " + next_discuss_opin_id);
			String query = "insert into rndcall_discuss_opin ( "
						 +"     discuss_op_id   "
						 +"	    ,discuss_id    "
						 +"	    ,p_discuss_op_id  "
						 +"	    ,reg_id   "
						 +"	    ,reg_dt   "
						 +"	    ,dis_comment  " 
						 +"     ,outcom_use_cd "
						 +"     ,edit_id "
						 +"     ,edit_dt "
						 +"	) values (  "
						 +"	     '"+next_discuss_opin_id+"'"
						 +"	    ,'"+vo.getDiscuss_id()+"'"
						 +"	    ,'"+vo.getDiscuss_op_id()+"'"
						 +"	    ,'"+searchVO.getUsername()+"'"
						 +"     ,sysdatetime "
						 +"	    ,CHAR_TO_CLOB('" + replaceSpChacter(vo.getDis_comment_reply()) + "')" 
						 +"     ,''"
						 +"	    ,'"+searchVO.getUsername()+"'"
						 +"     ,sysdatetime "
						+"	)";
			//openPreparedStatementForCUD(query);
			//executeQueryForCUD();
			System.out.println(">>>>>>>>  " + query);
			Statement stmt = conn.createStatement(); 
			stmt.executeUpdate(query);
			
			// 첨부파일이 있다면 첨부파일을 저장한다.
			if(fileInfo != null && fileInfo.length != 0) {
				for(int i=0; i<fileInfo.length; i++) {
					if (fileInfo[i] != null) {
						// 파일_id 시퀀스 번호를 따온다.
						sbQuery = new StringBuffer();
						sbQuery.append("select rndcall_file_id_seq.next_value as next_file_id ");
						pstmt = conn.prepareStatement(sbQuery.toString());
						rs = pstmt.executeQuery();
						int next_file_id = 0;
						if(rs.next()) {
							next_file_id = rs.getInt("next_file_id") ;
						}
						// rndcall_file 테이블에 저장한다.
						query = loadQueryString("sql.discussion.createTcoFile");
						openPreparedStatementForCUD(query);
						int param = 1;	
						pstmt.setInt(param++, next_file_id);
						pstmt.setString(param++, fileInfo[i].getInputFileName());
						pstmt.setString(param++, fileInfo[i].getAbsolutePath() + fileInfo[i].getOutputFileName());
						pstmt.setString(param++, fileInfo[i].getSize());					
						pstmt.setString(param++, searchVO.getUsername());
						pstmt.setString(param++, searchVO.getUsername());
						logger.debug("pstmt.execute()...");
						executeQueryForCUD();
						
						// rndcall_file 과 토론ID 매핑파일을 저장한다.
						query = loadQueryString("sql.discussion.createDisReplyFileMapping");
						openPreparedStatementForCUD(query);
						param = 1;	
						pstmt.setInt(param++, next_file_id);
						pstmt.setInt(param++, next_discuss_opin_id);					
						logger.debug("pstmt.execute()...");
						executeQueryForCUD();
					}
				}
				
			}
			conn.commit();
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {
			try { conn.setAutoCommit(true); } catch(Exception e1) {}
			close();
		}
	}
	
	/*
	 * 토론에 대해 찬성반대를 입력한다.
	 */
	public void createDisApop(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {		
		try {
			getConnection(dsname);
			String query = loadQueryString("sql.discussion.createDisscussApOp");
			openPreparedStatementForCUD(query);
			int param = 1;	
			pstmt.setString(param++, searchVO.getDiscuss_op_id());
			pstmt.setString(param++, searchVO.getAp_op());
			pstmt.setString(param++, searchVO.getUsername());			
			logger.debug("pstmt.execute()...");
			executeQueryForCUD();
			
			query = "update rndcall_discuss_opin ";
			if(searchVO.getAp_op().equals("10501")) {
			   query += " set recom_cnt = recom_cnt + 1 ";
			} else {
				query += " set opp_cnt = opp_cnt + 1 ";
			}
			query += " where discuss_op_id = '" + searchVO.getDiscuss_op_id() + "'";
			openPreparedStatementForCUD(query);
			logger.debug("pstmt.execute()...");
			executeQueryForCUD();
			
			// discuss_op_id 에 해당하는 discuss_id 가 뭔지 조회해 온다.
			query = "select discuss_id from rndcall_discuss_opin where discuss_op_id = ?";
			openPreparedStatementForR(query,  false);
			param = 1;	
			pstmt.setString(param++, searchVO.getDiscuss_op_id());
			executeQueryForR();
			rs.next();
			
			// 마일리지를 저장한다.
			String cate_id = "";
			String dis_id = rs.getString("discuss_id");
			if(dis_id.equals("8") || dis_id.equals("9") || dis_id.equals("11") || dis_id.equals("12")|| dis_id.equals("13")) {
				cate_id = "1";
			} else {
				cate_id = "2";
			}
//			MileageLog.saveMileageApOp(searchVO.getUsername(), searchVO.getDiscuss_op_id(), cate_id);
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {
			close();
		}		
	}
	
	/*
	 * id에 해당하는 찬성반대 내역이 있는지 조회한다.
	 */
	public String isApOp(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {
		String result = "N";
		try {
			getConnection(dsname);
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select ap_op from rndcall_dis_apop ")
			       .append(" where discuss_op_id = '").append(searchVO.getDiscuss_op_id()).append("'")
                   .append(" and reg_id = '").append(searchVO.getUsername()).append("'");
			openPreparedStatementForR(sbQuery.toString(),  false);			
			executeQueryForR();
			if(rs.next()) result = "Y";
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {
			close();
		}
		return result;
	}
	
	/*
	 * 본인글인지 확인한다.
	 */
	public String isSelfDiscussOpin(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {
		String result = "N";
		try {
			getConnection(dsname);
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select reg_id ")
                   .append(" from rndcall_discuss_opin ")
                   .append(" where discuss_op_id = '").append(searchVO.getDiscuss_op_id()).append("'");
			openPreparedStatementForR(sbQuery.toString(),  false);			
			executeQueryForR();
			rs.next();
			if(rs.getString("reg_id").equals(searchVO.getUsername())) {
				result = "Y";
			}
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {
			close();
		}
		return result;
	}
	
	/*
	 * 토론 댓글을 삭제한다.
	 */
	public void deleteDiscussOpin(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {				
		try {
			getConnection(dsname);
			conn.setAutoCommit(false);
			
			// 현 댓글에 부모 댓글이 있는지 파악한다. 없다면 댓글 카운트를 1 내린다.
			String query = loadQueryString("sql.discussion.retrieveReplyFirst");
			//System.out.println("1:부모댓글있나:" + query + ",##" + searchVO.getDiscuss_op_id());
			openPreparedStatementForR(query, false);			
			int param = 1;			
			pstmt.setString(param++, searchVO.getDiscuss_op_id());
			executeQueryForR();
			if(rs.next()) {
				String tmp = rs.getString("p_discuss_op_id");
				if(tmp != null && tmp.equals("")) {
					query = loadQueryString("sql.discussion.updateDiscussReplyCountMinus");
					openPreparedStatementForCUD(query);			
					param = 1;			
					pstmt.setString(param++, searchVO.getDiscuss_id());
					executeQueryForCUD();
				}
			}
			
			query = loadQueryString("sql.discussion.retrieveDiscOpId");
			//System.out.println("2:자식들확인:" + query + ",##" + searchVO.getDiscuss_op_id());
			openPreparedStatementForR(query, false);
			param = 1;	
			pstmt.setString(param++, searchVO.getDiscuss_op_id());
			executeQueryForR();
			while(rs.next()) {
				String dis_op_id = rs.getString("discuss_op_id");
				query = loadQueryString("sql.discussion.deleteDiscussOpin");
				//System.out.println("2:자식삭제:" + query + ",##" + dis_op_id);
				PreparedStatement pstmt1 = conn.prepareStatement(query);
				param = 1;
				pstmt1.setString(param++, searchVO.getUsername());
				pstmt1.setString(param++, dis_op_id);
				pstmt1.executeUpdate();
				
				// 글 등록자 ID를 가져온다.
				query = loadQueryString("sql.discussion.retrieveOpinRegId");
				//System.out.println("2-1:등록자확인:" + query + ",##" + dis_op_id);
				PreparedStatement pstmt2 = conn.prepareStatement(query);
				param = 1;
				pstmt2.setString(param++, dis_op_id);
				ResultSet rs1 = pstmt2.executeQuery();
				rs1.next();
				// 마일리지를 저장한다.(삭제)
				String cate_id = "";
				String dis_id = searchVO.getDiscuss_id();
				if(dis_id.equals("8") || dis_id.equals("9") || dis_id.equals("11") || dis_id.equals("12")|| dis_id.equals("13")) {
					cate_id = "1";
				} else {
					cate_id = "2";
				}
//				MileageLog.deleteMileageOpin(rs1.getString("reg_id"), dis_op_id, cate_id);
				pstmt1.close();
				pstmt2.close();
				rs1.close();
			}
			System.out.println("@@@@@@@ searchVO.getDiscuss_op_id():" + searchVO.getDiscuss_op_id());
			query = loadQueryString("sql.discussion.deleteDiscussOpin");
			openPreparedStatementForCUD(query);
			param = 1;
			pstmt.setString(param++, searchVO.getUsername());
			pstmt.setString(param++, searchVO.getDiscuss_op_id());
			executeQueryForCUD();
			//System.out.println("3:본인글삭제:" + query + ",##" + searchVO.getDiscuss_op_id());
			// 글 등록자 ID를 가져온다.
			query = loadQueryString("sql.discussion.retrieveOpinRegId");
			System.out.println("$$$$$$$$ query : " + query);
			PreparedStatement pstmt3 = conn.prepareStatement(query);
			param = 1;
			pstmt3.setString(param++, searchVO.getDiscuss_op_id());
			ResultSet rs2 = pstmt3.executeQuery();
			rs2.next();
			// 마일리지를 저장한다.(삭제)
			String cate_id = "";
			String dis_id = searchVO.getDiscuss_id();
			if(dis_id.equals("8") || dis_id.equals("9") || dis_id.equals("11") || dis_id.equals("12")|| dis_id.equals("13")) {
				cate_id = "1";
			} else {
				cate_id = "2";
			}
			// 의견인지 답글인지 구분해야함. rndcall_discuss_opin 을 조회해서 부모ID가 없으면 의견, 있으면 답글.
			String query1 = "select p_discuss_op_id from rndcall_discuss_opin where discuss_op_id = ?";
			PreparedStatement pstmt4 = conn.prepareStatement(query1);
			param = 1;
			pstmt4.setString(param++, searchVO.getDiscuss_op_id());
			ResultSet rs3 = pstmt4.executeQuery();
			int opin_div = 0; // 초기값 의견
			if(rs3.next()) {
				if(rs3.getString("p_discuss_op_id") == null || rs3.getString("p_discuss_op_id").equals("")) opin_div = 0;
				else opin_div = 1; // 답글
			}
//			if(opin_div == 0) {
//				MileageLog.deleteMileageOpin(rs2.getString("reg_id"), searchVO.getDiscuss_op_id(), cate_id);
//			} else {
//				MileageLog.deleteMileageOpinOpin(rs2.getString("reg_id"), searchVO.getDiscuss_op_id(), cate_id);
//			}
			rs2.close();
			pstmt3.close();
			pstmt4.close();
			rs3.close();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throwDAOBaseException(e, "not");
		} finally {
			try { conn.setAutoCommit(true); } catch(Exception e1) {}
			close();
		}
	}
	
	/*
	 * 토론을 삭제한다.
	 */
	public void deleteDiscuss(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {				
		try {
			getConnection(dsname);			
			String query = loadQueryString("sql.discussion.deleteDiscuss");			
			openPreparedStatementForCUD(query);
			int param = 1;	
			pstmt.setString(param++, searchVO.getDiscuss_id());
			executeQueryForCUD();			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {			
			close();
		}
	}
	
	/*
	 * 토론 본문을 입력한다.
	 */
	public void createDiscussContents(DiscussionVO vo, DiscussionSearchVO searchVO, FileVO[] fileInfo) throws SQLException, DAOBaseException {				
		try {
//			getConnection_clob();
			getConnection(dsname);
			conn.setAutoCommit(false);
			
			// rndcall_discuss_contents 의 dis_cont_id를 따온다.
			StringBuffer sbQuery = new StringBuffer();
			sbQuery.append("select rndcall_discuss_cont_seq.next_value as next_id ");
			pstmt = conn.prepareStatement(sbQuery.toString());
			rs = pstmt.executeQuery();
			int next_id = 0;
			if(rs.next()) {
				next_id = rs.getInt("next_id") ;
			}
			
			String query = "insert into rndcall_discuss_contents ( "
						+"	    dis_cont_id  "
						+"	   ,discuss_id    "
						+"	   ,dis_contents "
						+"	   ,reg_id  "
						+"	   ,edit_id    "
						+"     ,open_cd    "
						+"	) values (  "
						+"	    '"+next_id+"'"
						+"	   ,'"+searchVO.getDiscuss_id()+"'"
						+"	   ,CHAR_TO_CLOB('" + replaceSpChacter(vo.getDis_contents()) + "')  "
						+"	   ,'"+searchVO.getUsername()+"'"
						+"	   ,'"+searchVO.getUsername()+"'"
						+"     ,'' "
						+"	)";
			//openPreparedStatementForCUD(query);			
			//executeQueryForCUD();
			Statement stmt = conn.createStatement(); 
			stmt.executeUpdate(query);
			
			// 첨부파일이 있다면 첨부파일을 저장한다.
			if(fileInfo != null && fileInfo.length != 0) {
				for(int i=0; i<fileInfo.length; i++) {
					if (fileInfo[i] != null) {
						// 파일_id 시퀀스 번호를 따온다.
						sbQuery = new StringBuffer();
						sbQuery.append("select rndcall_file_id_seq.next_value as next_file_id ");
						pstmt = conn.prepareStatement(sbQuery.toString());
						rs = pstmt.executeQuery();
						int next_file_id = 0;
						if(rs.next()) {
							next_file_id = rs.getInt("next_file_id") ;
						}
						// rndcall_file 테이블에 저장한다.
						query = loadQueryString("sql.discussion.createTcoFile");
						openPreparedStatementForCUD(query);
						int param = 1;	
						pstmt.setInt(param++, next_file_id);
						pstmt.setString(param++, fileInfo[i].getInputFileName());
						pstmt.setString(param++, fileInfo[i].getAbsolutePath() + fileInfo[i].getOutputFileName());
						pstmt.setString(param++, fileInfo[i].getSize());					
						pstmt.setString(param++, searchVO.getUsername());
						pstmt.setString(param++, searchVO.getUsername());
						logger.debug("pstmt.execute()...");
						executeQueryForCUD();
						
						// rndcall_file 과 토론ID 매핑파일을 저장한다.
						query = loadQueryString("sql.discussion.createDiscussContentsFile");
						openPreparedStatementForCUD(query);
						param = 1;	
						pstmt.setInt(param++, next_file_id);
						pstmt.setInt(param++, next_id);
						pstmt.setString(param++, searchVO.getDiscuss_id());
						logger.debug("pstmt.execute()...");
						executeQueryForCUD();
					}
				}				
			}
			
			conn.commit();
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {
			try { conn.setAutoCommit(true); } catch(Exception e1) {}
			close();
		}
	}
	
	/*
	 * 토론본문을 삭제한다.
	 */
	public void deleteDiscussContents(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {				
		try {
			getConnection(dsname);			
			String query = loadQueryString("sql.discussion.deleteDiscussContent");			
			openPreparedStatementForCUD(query);
			int param = 1;	
			pstmt.setString(param++, searchVO.getDis_cont_id());
			executeQueryForCUD();
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {			
			close();
		}
	}
	
	public DiscussionResultVO updateDiscussContentsForm(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {
		DiscussionResultVO resultVO = new DiscussionResultVO();
		ArrayList voList = new ArrayList();
		DiscussionVO vo = null;
		String query = "";
		int param = 0;		
		try {
			getConnection(dsname);
//			getConnection_clob();
			query = "SELECT DISCUSS_OP_ID, DISCUSS_ID,  DIS_COMMENT \n"
				  + "FROM rndcall_DISCUSS_OPIN WHERE DISCUSS_OP_ID = '"+searchVO.getDiscuss_op_id()+"'";
			System.out.println("@@@@@@@@@@@@@@ :" + query);
			openPreparedStatementForR(query, false);
			executeQueryForR();
			
			if(rs.next()) {
				vo = new DiscussionVO();
				vo.setDiscuss_op_id(rs.getString("DISCUSS_OP_ID"));
				vo.setDiscuss_id(rs.getString("DISCUSS_ID"));
				
				Reader dis_comment = rs.getCharacterStream("DIS_COMMENT");
				String comment = Util.streamToBuf(dis_comment).toString();
				comment = replaceSpChacterRes(comment);
				vo.setDis_comment(comment);
			}
			
			resultVO.setVo(vo);
			
			query = loadQueryString("sql.discussion.retrieveDiscussDetailReplyFile");
			
			openPreparedStatementForR(query, false);
			
			param = 0;
			pstmt.setString(++param, searchVO.getDiscuss_op_id());
			
			executeQueryForR();
			
			while(rs.next()) {
				vo = new DiscussionVO();
				
				vo.setFile_nm(rs.getString("file_nm"));
				vo.setFile_path(rs.getString("file_path"));
				vo.setFile_size(rs.getString("file_size"));
				vo.setFile_id(rs.getString("file_id"));
				
				voList.add(vo);
				
			}
			resultVO.setVoList(voList);
			resultVO = encodeFilePath(resultVO);
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {			
			close();
		}
		
		return resultVO; 
	}

	public void updateDiscussContents(DiscussionSearchVO searchVO, DiscussionVO vo, FileVO[] fileInfo) throws SQLException, DAOBaseException {
		int param = 0;
		String query = "";
		StringBuffer sbQuery = new StringBuffer();
		try {	
			getConnection(dsname);
//			getConnection_clob();
			//System.out.println("+++++++++++++++++++++++++++++++++" + vo.getDis_comment());
			if(vo.getDel_files() != null) {
				for(int i=0; vo.getDel_files().length>i; i++) {			
					query = "update rndcall_file set is_del = 'Y' where file_id = '"+vo.getDel_files()[i] +"'";			
						openPreparedStatementForCUD(query);			
					executeQueryForCUD();
				}
			}
			
			query = loadQueryString("sql.discussion.updateDiscussContents");
			
			openPreparedStatementForCUD(query);
			param = 0;
			pstmt.setString(++param, replaceSpChacter(vo.getDis_comment()));
			pstmt.setString(++param, searchVO.getUsername());
			pstmt.setString(++param, searchVO.getDiscuss_op_id());
			executeQueryForCUD();
			
//			String clobQuery = " SELECT DIS_COMMENT FROM rndcall_DISCUSS_OPIN "
//				+ " WHERE DISCUSS_OP_ID = ? FOR UPDATE ";
//			
//			openPreparedStatementForR(clobQuery, false);
// 			
// 			pstmt.setString(1, searchVO.getDiscuss_op_id());
// 			
//			executeQueryForR();
//			
//			if (rs.next()) {
//				writeClob(((OracleResultSet) rs).getCLOB(1), replaceSpChacter(vo.getDis_comment()));
//			}
			
			// 첨부파일이 있다면 첨부파일을 저장한다.
			if(fileInfo != null && fileInfo.length != 0) {
				for(int i=0; i<fileInfo.length; i++) {
					if (fileInfo[i] != null) {
						// 파일_id 시퀀스 번호를 따온다.
						sbQuery = new StringBuffer();
						sbQuery.append("select rndcall_file_id_seq.next_value as next_file_id ");
						pstmt = conn.prepareStatement(sbQuery.toString());
						rs = pstmt.executeQuery();
						int next_file_id = 0;
						if(rs.next()) {
							next_file_id = rs.getInt("next_file_id") ;
						}
						// rndcall_file 테이블에 저장한다.
						query = loadQueryString("sql.discussion.createTcoFile");
						openPreparedStatementForCUD(query);
						param = 1;	
						pstmt.setInt(param++, next_file_id);
						pstmt.setString(param++, fileInfo[i].getInputFileName());
						pstmt.setString(param++, fileInfo[i].getAbsolutePath() + fileInfo[i].getOutputFileName());
						pstmt.setString(param++, fileInfo[i].getSize());					
						pstmt.setString(param++, searchVO.getUsername());
						pstmt.setString(param++, searchVO.getUsername());
						logger.debug("pstmt.execute()...");
						executeQueryForCUD();					
	//					 rndcall_file 과 토론ID 매핑파일을 저장한다.
						query = loadQueryString("sql.discussion.createDisReplyFileMapping");
						openPreparedStatementForCUD(query);
						param = 1;	
						pstmt.setInt(param++, next_file_id);
						pstmt.setInt(param++, Integer.parseInt(searchVO.getDiscuss_op_id()));					
						logger.debug("pstmt.execute()...");
						executeQueryForCUD();
					}
				}
			}
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {			
			close();
		}
		
	}
	
	public DiscussionResultVO updateIssueDetailsForm(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {
		DiscussionResultVO resultVO = new DiscussionResultVO();
		ArrayList voList = new ArrayList();
		DiscussionVO vo = null;
		String query = "";
		int param = 0;
		
		try {
			getConnection(dsname);
			query = "select discuss_id, CLOB_TO_CHAR(dis_contents) as dis_contents, dis_cont_id, open_cd from  \n" +
					"rndcall_discuss_contents where dis_cont_id = '"+searchVO.getDis_cont_id()+"'";
			openPreparedStatementForR(query, false);
			executeQueryForR();
			if(rs.next()) {
				vo = new DiscussionVO();
				
				vo.setDiscuss_id(rs.getString("discuss_id"));
				vo.setDis_cont_id(rs.getString("dis_cont_id"));
				vo.setDis_contents(rs.getString("dis_contents"));
				
			}
			
			resultVO.setVo(vo);
			
			query = loadQueryString("sql.discussion.retrieveDiscussContentsFile");
			
			openPreparedStatementForR(query, false);
			
			param = 0;
			pstmt.setString(++param, searchVO.getDiscuss_id());
			pstmt.setString(++param, searchVO.getDis_cont_id());
			
			executeQueryForR();
			
			while(rs.next()) {
				vo = new DiscussionVO();
				
				vo.setFile_nm(rs.getString("file_nm"));
				vo.setFile_path(rs.getString("file_path"));
				vo.setFile_size(rs.getString("file_size"));
				vo.setFile_id(rs.getString("file_id"));
				
				voList.add(vo);
				
			}
			
			resultVO.setVoList(voList);
			resultVO = encodeFilePath(resultVO);			
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {			
			close();
		}
		
		return resultVO;
	}
	
	public void updateIssueDetailsContents(DiscussionSearchVO searchVO, DiscussionVO vo, FileVO[] fileInfo) throws SQLException, DAOBaseException {
		int param = 0;
		String query = "";
		StringBuffer sbQuery = new StringBuffer();
		try {
			//getConnection(dsname);
			getConnection_clob();
			
			if(vo.getDel_files() != null) {
				for(int i=0; vo.getDel_files().length>i; i++) {			
					query = "update rndcall_file set is_del = 'Y' where file_id = '"+vo.getDel_files()[i] +"'";			
						openPreparedStatementForCUD(query);			
					executeQueryForCUD();
				}
			}
			
			query = loadQueryString("sql.discussion.updateIssueDetailsContents");			
			openPreparedStatementForCUD(query);
			param = 0;
			pstmt.setString(++param, replaceSpChacter(vo.getDis_contents()));
			pstmt.setString(++param, searchVO.getDis_cont_id());
			executeQueryForCUD();
			
//			String clobQuery = " SELECT dis_contents FROM rndcall_discuss_contents "
//				+ " WHERE dis_cont_id = ? FOR UPDATE ";
//			
//			openPreparedStatementForR(clobQuery, false);
// 			
// 			pstmt.setString(1, searchVO.getDis_cont_id());
// 			
//			executeQueryForR();
//			
//			if (rs.next()) {
//				writeClob(((OracleResultSet) rs).getCLOB(1), replaceSpChacter(vo.getDis_contents()));
//			}
			

			if(fileInfo != null && fileInfo.length != 0) {
				for(int i=0; i<fileInfo.length; i++) {
					if (fileInfo[i] != null) {
						sbQuery = new StringBuffer();
						sbQuery.append("select rndcall_file_id_seq.next_value as next_file_id ");
						pstmt = conn.prepareStatement(sbQuery.toString());
						rs = pstmt.executeQuery();
						int next_file_id = 0;
						if(rs.next()) {
							next_file_id = rs.getInt("next_file_id") ;
						}
	
						query = loadQueryString("sql.discussion.createTcoFile");
						openPreparedStatementForCUD(query);
						param = 1;	
						pstmt.setInt(param++, next_file_id);
						pstmt.setString(param++, fileInfo[i].getInputFileName());
						pstmt.setString(param++, fileInfo[i].getAbsolutePath() + fileInfo[i].getOutputFileName());
						pstmt.setString(param++, fileInfo[i].getSize());					
						pstmt.setString(param++, searchVO.getUsername());
						pstmt.setString(param++, searchVO.getUsername());
						logger.debug("pstmt.execute()...");
						executeQueryForCUD();					
						// rndcall_file �� ���ID ���������� �����Ѵ�.
						query = loadQueryString("sql.discussion.createDiscussFileMapping2");
						openPreparedStatementForCUD(query);
						param = 1;	
						pstmt.setInt(param++, next_file_id);
						pstmt.setInt(param++, Integer.parseInt(searchVO.getDiscuss_id()));					
						pstmt.setInt(param++, Integer.parseInt(searchVO.getDis_cont_id()));
						logger.debug("pstmt.execute()...");
						executeQueryForCUD();
					}
				}
			}
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {			
			close();
		}
	}

	public DiscussionResultVO updateCommentContentsForm(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {
		DiscussionResultVO resultVO = new DiscussionResultVO();
		ArrayList voList = new ArrayList();
		DiscussionVO vo = null;
		String query = "";
		int param = 0;
		
		try {
			//getConnection(dsname);
			getConnection_clob();
			query = "SELECT discuss_id, discuss_op_id, p_discuss_op_id, recom_cnt, opp_cnt, dis_comment \n" +
					"FROM rndcall_discuss_opin where discuss_op_id = '"+searchVO.getDiscuss_op_id()+"'";
			openPreparedStatementForR(query, false);
			executeQueryForR();
			if(rs.next()) {
				vo = new DiscussionVO();
				
				vo.setDiscuss_id(rs.getString("discuss_id"));
				vo.setDiscuss_op_id(rs.getString("discuss_op_id"));
				vo.setP_discuss_op_id(rs.getString("p_discuss_op_id"));
				vo.setRecom_cnt(rs.getString("recom_cnt"));
				vo.setOpp_cnt(rs.getString("opp_cnt"));
				
				Reader dis_comment = rs.getCharacterStream("dis_comment");
				String comment = replaceSpChacterRes(Util.streamToBuf(dis_comment).toString());				
				vo.setDis_comment(comment);
				
			}
			
			resultVO.setVo(vo);
			
			query = loadQueryString("sql.discussion.retrieveDiscussDetailReplyFile");
			
			openPreparedStatementForR(query, false);
			
			param = 0;
			pstmt.setString(++param, searchVO.getDiscuss_op_id());			
			
			executeQueryForR();
			
			while(rs.next()) {
				vo = new DiscussionVO();
				
				vo.setFile_nm(rs.getString("file_nm"));
				vo.setFile_path(rs.getString("file_path"));
				vo.setFile_size(rs.getString("file_size"));
				vo.setFile_id(rs.getString("file_id"));
				
				voList.add(vo);
				
			}
			
			resultVO.setVoList(voList);
			resultVO = encodeFilePath(resultVO);			
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {			
			close();
		}
		
		return resultVO;
	}

	public void updateCommentContents(DiscussionSearchVO searchVO, DiscussionVO vo, FileVO[] fileInfo) throws SQLException, DAOBaseException {
		int param = 0;
		String query = "";
		StringBuffer sbQuery = new StringBuffer();
		try {
			getConnection(dsname);
//			getConnection_clob();
			
			if(vo.getDel_files() != null) {
				for(int i=0; vo.getDel_files().length>i; i++) {			
					query = "update rndcall_file set is_del = 'Y' where file_id = '"+vo.getDel_files()[i] +"'";			
						openPreparedStatementForCUD(query);			
					executeQueryForCUD();
				}
			}
			
			query = loadQueryString("sql.discussion.updateDiscussContents");
			
			openPreparedStatementForCUD(query);
			param = 0;
			pstmt.setString(++param, replaceSpChacter(vo.getDis_comment()));
			pstmt.setString(++param, searchVO.getUsername());
			pstmt.setString(++param, searchVO.getDiscuss_op_id());
			executeQueryForCUD();
			
//			String clobQuery = " SELECT DIS_COMMENT FROM rndcall_DISCUSS_OPIN "
//				+ " WHERE DISCUSS_OP_ID = ? FOR UPDATE ";
//			
//			openPreparedStatementForR(clobQuery, false);
// 			
// 			pstmt.setString(1, searchVO.getDiscuss_op_id());
// 			
//			executeQueryForR();
//			
//			if (rs.next()) {
//				writeClob(((OracleResultSet) rs).getCLOB(1), replaceSpChacter(vo.getDis_comment()));
//			}
			
			// ÷�������� �ִٸ� ÷�������� �����Ѵ�.
			if(fileInfo != null && fileInfo.length != 0) {
				for(int i=0; i<fileInfo.length; i++) {
					if (fileInfo[i] != null) {
						sbQuery = new StringBuffer();
						sbQuery.append("select rndcall_file_id_seq.next_value as next_file_id ");
						pstmt = conn.prepareStatement(sbQuery.toString());
						rs = pstmt.executeQuery();
						int next_file_id = 0;
						if(rs.next()) {
							next_file_id = rs.getInt("next_file_id") ;
						}
	
						query = loadQueryString("sql.discussion.createTcoFile");
						openPreparedStatementForCUD(query);
						param = 1;	
						pstmt.setInt(param++, next_file_id);
						pstmt.setString(param++, fileInfo[i].getInputFileName());
						pstmt.setString(param++, fileInfo[i].getAbsolutePath() + fileInfo[i].getOutputFileName());
						pstmt.setString(param++, fileInfo[i].getSize());					
						pstmt.setString(param++, searchVO.getUsername());
						pstmt.setString(param++, searchVO.getUsername());
						logger.debug("pstmt.execute()...");
						executeQueryForCUD();					
	
						query = loadQueryString("sql.discussion.createDisReplyFileMapping");
						openPreparedStatementForCUD(query);
						param = 1;	
						pstmt.setInt(param++, next_file_id);
						pstmt.setInt(param++, Integer.parseInt(searchVO.getDiscuss_op_id()));					
						logger.debug("pstmt.execute()...");
						executeQueryForCUD();
					}
				}
			}
			
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {			
			close();
		}
		
	}
	
	/*
	 * ����� �����Ѵ�.
	 */
	public DiscussionResultVO updateDiscuss(DiscussionSearchVO searchVO, DiscussionVO vo, FileVO[] fileInfo) throws SQLException, DAOBaseException {
		DiscussionResultVO resultVO = null;
		try {
			getConnection_clob();//getConnection(dsname);
			//getConnection_clob();//getConnection(dsname); //getConnection_clob();
			conn.setAutoCommit(false);
			// ������Ʈ �Ѵ�.
			String query = loadQueryString("sql.discussion.updateDiscuss");
			openPreparedStatementForCUD(query);
			int param = 1;	
			pstmt.setString(param++, vo.getDis_title());
			pstmt.setString(param++, replaceSpChacter(vo.getDis_contents()));
			pstmt.setString(param++, vo.getDiscuss_id());
			executeQueryForCUD();
			
//			// ��� ������ discuss_id �� �ش��ϴ� contents �÷��� �����´�.
			StringBuffer sbQuery = new StringBuffer();
//			sbQuery.append("select dis_contents from rndcall_discuss where discuss_id = '").append(vo.getDiscuss_id()).append("'");
//			pstmt = conn.prepareStatement(sbQuery.toString());
//			rs = pstmt.executeQuery();			
//			// contents �÷��� ������Ʈ �Ѵ�.
//			CLOB clobData = null;			
//			while (rs.next()) {
//				clobData = ((OracleResultSet) rs).getCLOB("dis_contents");
// 				StringUtil.bufToWrite(clobData,replaceSpChacter(vo.getDis_contents())); 				
// 			}
			
			// ÷�������� �ִٸ� ÷�������� �����Ѵ�.
			if(fileInfo != null && fileInfo.length != 0) {
				for(int i=0; i<fileInfo.length; i++) {
					if (fileInfo[i] != null) {
						sbQuery = new StringBuffer();
						sbQuery.append("select rndcall_file_id_seq.next_value as next_file_id ");
						pstmt = conn.prepareStatement(sbQuery.toString());
						rs = pstmt.executeQuery();
						int next_file_id = 0;
						if(rs.next()) {
							next_file_id = rs.getInt("next_file_id") ;
						}
						// rndcall_file ���̺? �����Ѵ�.
						query = loadQueryString("sql.discussion.createTcoFile");
						openPreparedStatementForCUD(query);
						param = 1;	
						pstmt.setInt(param++, next_file_id);
						pstmt.setString(param++, fileInfo[i].getInputFileName());
						pstmt.setString(param++, fileInfo[i].getAbsolutePath() + fileInfo[i].getOutputFileName());
						pstmt.setString(param++, fileInfo[i].getSize());					
						pstmt.setString(param++, searchVO.getUsername());
						pstmt.setString(param++, searchVO.getUsername());
						logger.debug("pstmt.execute()...");
						executeQueryForCUD();
						
						// rndcall_file �� ���ID ���������� �����Ѵ�.
						query = loadQueryString("sql.discussion.createDiscussFileMapping");
						openPreparedStatementForCUD(query);
						param = 1;	
						pstmt.setInt(param++, next_file_id);
						pstmt.setString(param++, vo.getDiscuss_id());					
						logger.debug("pstmt.execute()...");
						executeQueryForCUD();
					}
				}
				
			}
			
			conn.commit();
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {
			try { conn.setAutoCommit(true); } catch(Exception e1) {}
			close();
		}
		return resultVO;
	}

	/*
	 * ����� ÷�������� �����Ѵ�.
	 */
	public void deleteDiscussAttach(DiscussionSearchVO searchVO) throws SQLException, DAOBaseException {				
		try {
			getConnection(dsname);			
			String query = loadQueryString("sql.discussion.deleteDiscussAttachMapping");			
			openPreparedStatementForCUD(query);
			int param = 1;	
			pstmt.setString(param++, searchVO.getFile_id());
			pstmt.setString(param++, searchVO.getDiscuss_id());
			executeQueryForCUD();
			
			query = loadQueryString("sql.discussion.deleteTcoFile");			
			openPreparedStatementForCUD(query);
			param = 1;	
			pstmt.setString(param++, searchVO.getFile_id());
			executeQueryForCUD();
		} catch (Exception e) {
			throwDAOBaseException(e, "not");
		} finally {			
			close();
		}
	}

}
