package com.aps.rarp.co.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * CMS LoginSession Manager
 * @author  IITECH 정용진	
 * @since 2013. 04.11
 * <pre>
 * == 개정이력(Modification Information) ==
 *
 * 수정일           	      수정자 	 	수정내용
 * ------------		---------------		----------------
 * 2013. 04.11      IITECH 정용진		최초 생성
 *</pre>
 */
public class ScriptUtil {
	private static String jsStartTag = "<script language='javascript'>\n<!--\n";
	private static String jsEngTag =  "//--></script>"; 
	private static String contentType = "text/html; charset=euc-kr" ; 
	
	protected static final Logger LOGGER = Logger.getLogger(StringUtil.class);
	
	/**
	 * alert 을 출력 한후 이전 페이지 history.back 을 실행 한다 . 
	 * @param response
	 * @param message
	 */  
	public static void doAlertToHistoryGo(HttpServletResponse response,String message){
		try {
			StringBuffer sb = new StringBuffer(1024) ; 
			sb.append("alert('"+strToAlert(message)+"');") ;
			sb.append("history.back()") ;
			pDoPrintWriteFlush(response,sb.toString()) ; 
		} catch (Exception e) {
			//e.printStackTrace() ; 
			LOGGER.info(e.getMessage());
		}
	}
	
	/**
	 * alert 을 출력 한후 이전 페이지 history.back 을 실행 한다 . 
	 * @param response
	 * @param message
	 */  
	public static void doAlertToAlert(HttpServletResponse response,String message){
		try {
			StringBuffer sb = new StringBuffer(1024) ; 
			sb.append("alert('"+strToAlert(message)+"');") ;
			//sb.append("history.go("+i+")") ;
			pDoPrintWriteFlush(response,sb.toString()) ; 
		} catch (Exception e) {
			//e.printStackTrace() ; 
			LOGGER.info(e.getMessage());
		}
	}
	
	/**
	 * alert 출력 후 해당 페이지로 이동한다 location.href 
	 * @param response
	 * @param message
	 * @param locationHref
	 */
	public static void doAlertToLocation(HttpServletResponse response,String message,String locationHref){
		try {
			StringBuffer sb = new StringBuffer(1024) ; 
			sb.append("alert('"+strToAlert(message)+"');");
			sb.append("location.href='"+locationHref+"';") ; 
			pDoPrintWriteFlush(response,sb.toString()) ;
		} catch (Exception e) {
			//e.printStackTrace() ; 
			LOGGER.info(e.getMessage());
		}
	}
	
	/**
	 * confrim 메시지 출력후 OK 시 해당 되는 스크립트와 NO 시 해당 하는 스크립트를 처리 한다 
	 * @param response
	 * @param message
	 * @param okScMessage
	 * @param noScMessage
	 */
	public void doConfirm(HttpServletResponse response,String message,String okScMessage,String noScMessage){
		try {
			StringBuffer sb = new StringBuffer(1024) ; 
			sb.append("if(confirm('"+strToAlert(message)+"')){");
			sb.append(okScMessage) ;
			sb.append("}else{") ;
			sb.append(noScMessage) ;
			sb.append("}") ;
		   pDoPrintWriteFlush(response, sb.toString()) ; 
		} catch (Exception e) {
		   //e.printStackTrace() ; 
			LOGGER.info(e.getMessage());
		}
	}	
	
	/**
	 * 자신의 창을 닫는다 
	 * @param response
	 * @param openerReloadCheck 부모창을 리로드 할지 여부 true 리로드 
	 */
	public void doSelfCloseAndOpenerReload(HttpServletResponse response ,boolean openerReloadCheck){
		try {
			StringBuffer sb = new StringBuffer(1024) ; 
			String script = doSelfCloseAndOpenerReloadMessage(sb,openerReloadCheck) ; 
			pDoPrintWriteFlush(response, script) ; 
		} catch (Exception e) {
			//e.printStackTrace(); 
			LOGGER.info(e.getMessage());
		}
	}
	
	/**
	 * 메시지 출력 후 자신의 창을 닫는다 .
	 * @param response
	 * @param message	출력 메시지 alert 를 출력 한다 
	 * @param openerReloadCheck  부모창을 리로드 할지 여부 true 리로드 
	 */
	public void doSelfCloseAndOpenerReload(HttpServletResponse response,String message,boolean openerReloadCheck){
		try {
			StringBuffer sb = new StringBuffer(1024) ; 
			sb.append("alert('"+strToAlert(message)+"');");
			String script = doSelfCloseAndOpenerReloadMessage(sb,openerReloadCheck) ; 
			pDoPrintWriteFlush(response, script) ; 
		} catch (Exception e) {
			//e.printStackTrace(); 
			LOGGER.info(e.getMessage());
		}
	}
	
	private String doSelfCloseAndOpenerReloadMessage(StringBuffer sb,boolean reloadCheck){
		if(reloadCheck){
			sb.append("opener.location.reload();"); 
		}
		sb.append("if(/MSIE/.test(navigator.userAgent)){"); 
		sb.append("if(navigator.appVersion.indexOf('MSIE 7.0')>=0){");
		sb.append("window.open('about:blank','_self').close();");
		sb.append("}");
		sb.append("else{");
		sb.append("window.opener=self;");
		sb.append("self.close();");
		sb.append("}");
		sb.append("}") ;
		return sb.toString() ; 
	}
	
	
	/**
	 * 사용자 Javascript 를 출력한다 . 스크립트 여는 테그 <script></script> 는 필요 없이 alert('테스트'); 이런식으로 처리 할수 있다. ; 는 꼭첨부
	 * @param response
	 * @param sb
	 * @throws Exception
	 */
	public  void userScript(HttpServletResponse response,StringBuffer sb) {
		try {
			pDoPrintWriteFlush(response,sb.toString()); 
		} catch (Exception e) {
			//e.printStackTrace() ; 
			LOGGER.info(e.getMessage());
		}
	}
	
	/**
	 * 사용자 Javascript 를 출력한다 . 스크립트 여는 테그 <script></script> 는 필요 없이 alert('테스트'); 이런식으로 처리 할수 있다. ; 는 꼭첨부
	 * @param response
	 * @param sb
	 * @throws Exception
	 */
	public  void userScript(HttpServletResponse response,String str) {
		try {
			pDoPrintWriteFlush(response,str) ; 
		} catch (Exception e) {
			//e.printStackTrace() ; 
			LOGGER.info(e.getMessage());
		}
	}
	
	public  void userHtml(HttpServletResponse response,String str) {
		try {
			pDoPrintWriteFlushHTML(response,str) ; 
		} catch (Exception e) {
			//e.printStackTrace() ; 
			LOGGER.info(e.getMessage());
		}
	}
	
	/**
	 * print용 스트립을 정의 한다 
	 * @param response
	 * @param jsMessage
	 * @return
	 * @throws Exception
	 */
	private static void pDoPrintWriteFlush(HttpServletResponse response,String jsMessage)throws Exception{
		response.setContentType(contentType) ; 
		PrintWriter out = response.getWriter() ; 
			out.println(jsStartTag) ; 
			out.println(jsMessage) ; 
			out.println(jsEngTag) ; 
		out.flush() ; 
		out.close() ; 
	}
	
	/**
	 * print용 스트립을 정의 한다 
	 * @param response
	 * @param jsMessage
	 * @return
	 * @throws Exception
	 */
	private void pDoPrintWriteFlushHTML(HttpServletResponse response,String jsMessage)throws Exception{
		response.setContentType(contentType) ; 
		PrintWriter out = response.getWriter() ; 
			out.println(jsMessage) ; 
		out.flush() ; 
		out.close() ; 
	}
	
	
	/**
	 * 문자열 특수 문자를 변경 시킨다 
	 * @param str
	 * @return
	 */
	private static String strToAlert(String str){
		if(str == null){return null;}
		StringBuffer sb = new StringBuffer(1024); 
		char[] c = str.toCharArray() ; 
		for (int i = 0; i < c.length; i++) {
			if(c[i] == '\n'){
				sb.append("\\n");
			}else if(c[i] == '\t'){
				sb.append("\\t");
			}else if(c[i] == '"'){
				sb.append(" ");
			}else if(c[i] == '\''){
				sb.append("\\'");
			}else if(c[i] == '\r'){
				sb.append("\\r");
			}else{
				sb.append(c[i]);
			}
		}
		return sb.toString() ; 
	}
		
}