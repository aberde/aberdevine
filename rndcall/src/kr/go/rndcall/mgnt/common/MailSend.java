package kr.go.rndcall.mgnt.common;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class MailSend {

	public static String callURL(String url) throws Exception {
		
		System.out.println("callURL = "+url);
		
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

	//메일 설정 ==================================================
	private String daters = "[" + NowdateF("") + "]";

	private String msgSubj = daters + " 자료요청 관련"; //메일 제목

	private String from = "noreply@nstc.go.kr";

	private String host = "10.175.34.180";
//	private String host = "210.219.36.22";

	//==========================================================

	private static int Count = 0;

	public static int getCount() {
		return Count;
	}

	public static void setCount(int Count) {
		MailSend.Count = Count;
	}

	// ====================================================
	public boolean MailSendMain(List getMailList, String msgText) throws Exception {

		HashMap getHm = new HashMap();
		HashMap hm = new HashMap();
		List mailList = new ArrayList();

//		PropertyConfigurator.configure(MailSmsConfig.getTrinityConfRoot()+ "JEUSlog.properties");
//		logger.info("0.JEUSlog.properties ");

		// ==========================================================		
		String strurl = ""; // 본문내용URL
		String listCount = ""; // 본문내용 갯수
		boolean success =false;
		MailSend mailIRS = new MailSend();
		try {

			mailList.clear();
			for (int i = 0; i < getMailList.size(); i++) {
					
				getHm = (HashMap) getMailList.get(i);

				hm = new HashMap();
				hm.put("MEM_EMAIL", getHm.get("EMAIL"));
				hm.put("MEM_NAME", getHm.get("USERNAME"));
				hm.put("LOGIN_ID",  getHm.get("LOGIN_ID"));
//				logger.info("MEM_NAME : " + (String)hm.get("MEM_NAME") + ", MEM_EMAIL : " + (String)hm.get("MEM_EMAIL"));
				mailList.add(hm);
					
				//strurl = "http://rndgate.ntis.go.kr/un/mail/mailbody2_Req_Data.jsp?login_id="+ login_id + "&req_seq=" + req_seq + "&state_cd=" + state_cd;
				//msgText = callURL(strurl); // 본문 내용을 가져옴
				
				success = mailIRS.SendMAilAgent(mailList, msgText);
//				logger.info("send .... ok !");
			}

		} catch (Exception e2) {
			System.out.println("Exception : " + e2.toString());
//			logger.error("Exception : " + e2.toString());
			success =false;
		}
		
		return success;
	}

	// ==================== 대상이 여러명인 경우 ====================
	public boolean SendMAilAgent(List MailList, String msgText) {
		boolean success =false;
		try {

			// 아래 인코딩 부분은 환경에 따라 제목의 한글이 깨질 경우 주석을 제거하세요.
			// msgSubj = new String(msgSubj.getBytes("utf-8"),"8859_1");

			System.out.println("SendMAilAgent start~~~~~~~~~~~~~~");
			Properties props = new Properties();
			props.put("mail.smtp.host", host);
			Session sess = Session.getDefaultInstance(props, null);

			HashMap getHm = new HashMap();
			String mem_email = null;
			String mem_name = null;
			String to = ""; // mailALLTo; //받는이 ...
			Count = 0;
			// /////////////////////////////////
			for (int i = 0; i < MailList.size(); i++) {
				getHm = (HashMap) MailList.get(i);
				mem_email = (String) getHm.get("MEM_EMAIL");
				mem_name = (String) getHm.get("MEM_NAME");

				try {
					// create a message
					msgSubj = daters + " R&D도우미센터 운영자입니다.";
					
					to = mem_email;
					Message msg = new MimeMessage(sess);
					msg.setFrom(new InternetAddress(from));
					InternetAddress[] address = { new InternetAddress(to) };
 					msg.setRecipients(Message.RecipientType.TO, address);
					msg.setSubject(msgSubj/* + " (" + mem_name + " 님께..)"*/);
					msg.setSentDate(new Date());
					msg.setContent(msgText, "text/html; charset=utf-8"); // HTML
					// 형식
					// msg.setText(msgText); // TEXT 형식
					Transport.send(msg);
					// System.out.println("msgSubj : " + msgSubj );
//					System.out.println(mem_name + ":" + to + "  ....ok ");
					Count++;
					success =true;
					
				} catch (MessagingException mex) {
					System.out.println(mex.getMessage() + " ");
					System.out.println(mem_name + ":" + to + "  .... Failed");
					System.out.println(host + " 접속에 문제가 생겼네요. 발송하지 못했습니다.");
					success =false;
				} catch (Exception e) {
					success =false;
				}

			}
			System.out.println("  " + Count + "명에게 발송이 완료 됐습니다 .mailsendok ");
			// ////////////////////////////
		} catch (Exception ignore) {
			System.out.println("발송중 에러  .mailsendfail ");
			success =false;
		}
		return success;
	}

	// ===================== 개인에개 발송 경우 =====================
	public void SendMAilAgent(String mailALLTo, String msgText) {
		try {

			// 아래 인코딩 부분은 환경에 따라 제목의 한글이 깨질 경우 주석을 제거하세요.
			// msgSubj = new String(msgSubj.getBytes("utf-8"),"8859_1");
			String to = mailALLTo; // 받는이 ...

			// create some properties and get the default Session
			Properties props = new Properties();
			props.put("mail.smtp.host", host);
			Session sess = Session.getDefaultInstance(props, null);

			try {
				// create a message
				Message msg = new MimeMessage(sess);
				msg.setFrom(new InternetAddress(from));
				InternetAddress[] address = { new InternetAddress(to) };
				msg.setRecipients(Message.RecipientType.TO, address);
				msg.setSubject(msgSubj);
				msg.setSentDate(new Date());
				msg.setContent(msgText, "text/html; charset=utf-8"); // HTML

				Transport.send(msg);

				System.out.println(host + ":" + to + " mailsendok ....");
				Count++;
			} catch (MessagingException mex) {
				System.out.println(mex.getMessage() + " ");
				System.out.println(host + ":" + to + " mailsendfail ....");
				System.out.println(host + " 접속에 문제가 생겼네요. 발송하지 못했습니다.");
			}

		} catch (Exception ignore) {
		}

	}
}
