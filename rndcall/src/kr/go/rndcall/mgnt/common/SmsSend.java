package kr.go.rndcall.mgnt.common;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.text.DecimalFormat;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.Calendar;

import kr.go.rndcall.mgnt.common.DesCipher;
import kr.go.rndcall.mgnt.common.Util;
import kr.go.rndcall.mgnt.common.BaseSqlDAO;
import kr.go.rndcall.mgnt.common.DAOBaseException;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.CHAR;
import oracle.sql.CLOB;

public class SmsSend extends BaseSqlDAO{
	
	static Logger logger = Logger.getLogger(SmsSend.class);
	private String dsname = "jdbc/rndcall";
	
	public static String callURL(String url) throws Exception {
		int len;
		InputStream input = (new URL(url)).openStream();
		byte b[] = new byte[64000];
		StringBuffer sb = new StringBuffer();
		while ((len = input.read(b, 0, b.length)) > 0)
			sb.append(new String(b, 0, len));
		input.close();
		return sb.toString();
	}

	public String NowdateF(String str) {
		DecimalFormat df = new DecimalFormat("00");
		Calendar cal = Calendar.getInstance();

		int years = cal.get(Calendar.YEAR);
		String months = df.format(cal.get(Calendar.MONTH) + 1);
		String strDay = df.format(cal.get(Calendar.DATE));
		String strDate = years + "." + months + "." + strDay;

		return strDate;

	}

	// ====================================================

	public boolean SmsSendMain(List getSmsList, String msgText) throws Exception {

//		PropertyConfigurator.configure(MailSmsConfig.getTrinityConfRoot()+ "JEUSlog.properties");
		URL url = new URL("http://www.ntis.go.kr/ThSmsSendCon.do");
		URLConnection httpConn = null;
		BufferedReader in = null;
		PrintWriter out = null;
		
		HashMap getHm = new HashMap();
		HashMap hm = new HashMap();
		List smsList = new ArrayList();
		SmsSend smsSend = new SmsSend();
		
		DecimalFormat df = new DecimalFormat("00");
		Calendar cal = Calendar.getInstance();
		int years ;
		
		String months = null;
		String strDay = null;
		String strDate = null;
		String time = null;
		
		String username = "";
		String mobile = "";
		String listCount = "";		
		
		String login_id = null;
		boolean success =false;
		String msg = "";
		
		try {
			//List getSmsList = smsSend.getSmsList(seq);
			
			System.out.println("getSmsList.size() :: "+getSmsList.size());
			for (int i = 0; i < getSmsList.size(); i++) {							

				httpConn = url.openConnection();
				httpConn.setDoOutput(true);
				httpConn.setUseCaches(false);
				out = new PrintWriter(httpConn.getOutputStream());

				smsList.clear();
				getHm = (HashMap) getSmsList.get(i);

				login_id = (String) getHm.get("LOGIN_ID");
				username = (String) getHm.get("USERNAME");
				mobile = (String) getHm.get("MOBILE");		
								
				smsList.add(hm);
					
					
				System.out.println("수신번호====>" + msgText+"::"+ username + ":" + mobile + ":" + getCurrentTime());
					
					
				java.util.Hashtable data = new java.util.Hashtable();
				data.put("sys_cd", "UN"); // 서비스코드
				data.put("user_id", "kistep34"); // 로그인 사용자 ID
				data.put("fromhp", "027248700"); // 보내는 사람 핸드폰번호
				data.put("tohp", mobile); // 받는사람 핸드폰번호(복수)					
				data.put("send_msg", msgText); // 80 바이트 이내로 작성
				data.put("res_time", getCurrentTime()); // 즉시 보낼 경우에는 널값으로 전송
				out.print(url_encoding(data));
				out.flush();
	
				InputStream is = httpConn.getInputStream();
				in = new BufferedReader(new InputStreamReader(is), 8 * 1024);
	
				String line = null;
				while ((line = in.readLine()) != null) {
					System.out.println(line);
				}
				logger.info(mobile + " SMS send....ok.");
				success = true;
			}
		} catch (Exception e2) {
			System.out.println("Exception : " + e2.toString());
			logger.error("Exception : " + e2.toString());
			success = false;
		} finally {

			if ( out != null ) try { out.close(); } catch ( Exception e ) {}
			if ( in != null ) try { in.close(); } catch ( Exception e ) {}
			
		}
		return success;
	}
	
	// 현재 날짜시간을 반환한다.
	public String getCurrentTime() {
		DecimalFormat df = new DecimalFormat("00");
		Calendar cal = Calendar.getInstance();
		int years ;
		String months = null;
		String strDay = null;
		String strDate = null;
		String time = null;
		years = cal.get(Calendar.YEAR);
		months = df.format(cal.get(Calendar.MONTH) + 1);
		strDay = df.format(cal.get(Calendar.DATE));
		time = "000000";
		strDate = years + months + strDay + time;
		return strDate;
	}
	
	// ==================== SMS 전송  ====================
	public static String url_encoding(java.util.Hashtable hash) {
		if (hash == null)
			throw new IllegalArgumentException("argument is null");
		java.util.Enumeration enum1 = hash.keys();
		StringBuffer buf = new StringBuffer();
		boolean isFirst = true;
		while (enum1.hasMoreElements()) {
			if (isFirst) {
				isFirst = false;
			} else {
				buf.append('&');
				String key = (String) enum1.nextElement();
				String value = (String) hash.get(key);
				try {
					buf.append(java.net.URLEncoder.encode(key));
					buf.append('=');
					buf.append(java.net.URLEncoder.encode(value, "EUC-KR"));
				} catch (Exception e) {
					System.out.println("Exception : " + e.toString());
				}
			}
		}
		return buf.toString();
	}
	
}