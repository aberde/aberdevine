// 2005.10.5 khnam

package kr.go.rndcall.mgnt.common;

import java.io.BufferedWriter;
import java.io.Reader;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.*;

import kr.go.rndcall.mgnt.login.LoginVO;


import oracle.sql.*;

import org.apache.log4j.*;
import org.apache.struts.action.ActionForm;


public class Util {

    static Logger logger = Logger.getLogger("kr.go.ntis.mgnt.cm.common.Util");

    public Util() {
    }

    public static ArrayList getYearList(int startYYYY, int endYYYY) {
        ArrayList yearList = new ArrayList();
        int i;
        for (i = startYYYY; i <= endYYYY; i++) {
            yearList.add(String.valueOf(i));
        }
        return yearList;

    }
    // 2007-11-03
    public static ArrayList getIa_yearList(int startYYYY, int endYYYY) {
        ArrayList yearList = new ArrayList();
        int i;
        for (i = endYYYY ; i >= startYYYY ; i--) {
            yearList.add(String.valueOf(i));
        }
        return yearList;

    }
    
    // 2012-01-02 박인선 추가
    public static ArrayList getMonList() {
        ArrayList monList = new ArrayList();
        int i;
        String mon = "";        
        for (i = 1 ; i <= 12 ; i++) {
        	if(i < 10){ 
        		mon = "0"+String.valueOf(i);
        	}else{
        		mon = String.valueOf(i);
        	}
        	monList.add(mon);
        }
        return monList;

    }
    
    public static ArrayList getBu_yearList(int startYYYY, int endYYYY) {
        ArrayList yearList = new ArrayList();
        int i;
        for (i = startYYYY; i <= endYYYY; i++) {
        	yearList.add(String.valueOf(i));         
        }
        return yearList;

    }
    

    public static ArrayList getHourList() {
        ArrayList hourList = new ArrayList();
        for (int i = 0; i < 24; i++) {
            hourList.add(String.valueOf(i));
        }
        return hourList;

    }

    public static String con(String s) {
        String s1 = null;
        try {
            s1 = new String(s.getBytes("8859_1"), "KSC5601");
        } catch (Exception ex) {
            // System.out.println("Exception:" + ex);
        }
        return s1;
    }

    public static String getCurrentYear() {
        return String.valueOf(((Calendar) Calendar.getInstance()).
                              get(Calendar.YEAR));
    }

    public static String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        StringBuffer result = new StringBuffer();
        result.append(cal.get(Calendar.YEAR));

        if ((cal.get(Calendar.MONTH) + 1) < 10) {
            result.append("-0" + (cal.get(Calendar.MONTH) + 1));
        } else {
            result.append("-" + (cal.get(Calendar.MONTH) + 1));
        }
        if (cal.get(Calendar.DATE) < 10) {
            result.append("-0" + cal.get(Calendar.DATE));
        } else {
            result.append("-" + cal.get(Calendar.DATE));
        }

        return result.toString();
    }

    public static String getCurrentDate(String s) {
        Calendar cal = Calendar.getInstance();
        StringBuffer result = new StringBuffer();
        result.append(cal.get(Calendar.YEAR));

        if ((cal.get(Calendar.MONTH) + 1) < 10) {
            result.append(s + "0" + (cal.get(Calendar.MONTH) + 1));
        } else {
            result.append(s + (cal.get(Calendar.MONTH) + 1));
        }
        if (cal.get(Calendar.DATE) < 10) {
            result.append(s + "0" + cal.get(Calendar.DATE));
        } else {
            result.append(s + cal.get(Calendar.DATE));
        }

        return result.toString();
    }

    public static String strToDateStr(String date, String div) {
	    if (date == null) return date;
	    if (date != null && (date.length() == 8) && div == null) {
		      date = date.substring(0, 4) + "년 " + date.substring(4, 6) + "월 " +
	          date.substring(6, 8) + "일 ";
	    } else if (date != null && (date.length() == 6) && div == null) {
	      date = date.substring(0, 4) + "년 " + date.substring(4, 6) + "월";
	    } else if (date != null && (date.length() == 8)) {
	      date = date.substring(0, 4) + div + date.substring(4, 6) + div +
	          date.substring(6, 8);
	    } else if (date != null && (date.length() == 6)) {
	      date = date.substring(0, 4) + div + date.substring(4, 6);
	    } else if (date != null && div.equals("-")) {
	      date = date.substring(0, 4) + "-" + date.substring(5, 6) + "-" +
	          date.substring(8, 9);
	    } else if (date == null) {
	      date = "";
	    }
	    return date;
	}


    public static String getCurrentDateHour() {
        Calendar cal = Calendar.getInstance();
        StringBuffer result = new StringBuffer();
        result.append(cal.get(Calendar.YEAR));

        if ((cal.get(Calendar.MONTH) + 1) < 10) {
            result.append("-0" + (cal.get(Calendar.MONTH) + 1));
        } else {
            result.append("-" + (cal.get(Calendar.MONTH) + 1));
        }
        if (cal.get(Calendar.DATE) < 10) {
            result.append("-0" + cal.get(Calendar.DATE));
        } else {
            result.append("-" + cal.get(Calendar.DATE));
        }
        result.append("-" + cal.get(Calendar.HOUR_OF_DAY));
        return result.toString();
    }

    public static String makeMutiLine(String in) {
        if (in == null || in.trim().equals("")) {
            return in;
        }
        String out = in.replaceAll("\r\n", "<br/>");
        return out;
    }

    public static String replaceNewLine(String in) {
        if (in == null || in.trim().equals("")) {
            return in;
        }
        String out = in.replaceAll("\r\n", "<br/>").replaceAll("\n", "<br/>");;
        return out;
    }

    public static String makeScriptMutiLine(String in) {
        if (in == null || in.trim().equals("")) {
            return in;
        }
        String out = in.replaceAll("\r\n", "\\\\n");
        return out;
    }

    public static String makeExcelMutiLine(String in) {
        if (in == null || in.trim().equals("")) {
            return in;
        }
        String out = in.replaceAll("\r\n", "\n");
        return out;
    }

    public static CHAR makeCHAR(String input) throws SQLException {
        CHAR result = null;
        int oracleId = CharacterSet.WE8ISO8859P1_CHARSET;
        //int oracleId = CharacterSet.KO16MSWIN949_CHARSET;
        CharacterSet charset = CharacterSet.make(oracleId);
        result = new CHAR(toDB(input), charset);
        return result;
    }

    public static String toDB(String str) {

        if (str == null) {
            return "";
        }

        try {
        	// 2011.09.16 한글깨짐현상으로 변경
            //str = new String(str.getBytes("KSC5601"), "8859_1");
        	str = new String(str.getBytes("KSC5601"), "UTF-8");
        } catch (Exception ex) {
            // System.out.println("Exception:" + ex);
        }
        return str;
    }

    public static String getContextPath(HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("/");
    }

    public static String makeImgDesc(HttpServletRequest request) {
        StringBuffer result = new StringBuffer();
        result.append("<img name='orderByDESC0' src='");
//        result.append(getContextPath(request));
        result.append(request.getContextPath());
        result.append(
                "/image/orderByDESC0.gif' width='11' height='11' border='0' align='absmiddle'>");
        return result.toString();
    }

    public static String makeImgAsc(HttpServletRequest request) {
        StringBuffer result = new StringBuffer();
        result.append("<img name='orderByASC0' src='");
//        result.append(getContextPath(request));
        result.append(request.getContextPath());
        result.append(
                "/image/orderByASC0.gif' width='11' height='11' border='0' align='absmiddle'>");
        return result.toString();
    }

    public static String getParam(HttpServletRequest request, String name,
                                  String defaultValue) {
        String param = request.getParameter(name);
        return (param != null) ? param : defaultValue;
    }

    public static int getParam(HttpServletRequest request, String name,
                               int defaultValue) {
        String param = request.getParameter(name);
        int value = defaultValue;
        if (param != null) {
            try {
                value = Integer.parseInt(param);
            } catch (NumberFormatException ignore) {}
        }
        return value;
    }

    public static String checkNull(String inValue, String defaultValue) {
        String returnStr = null;
        if (inValue == null) {
            returnStr = defaultValue;
        } else if (inValue.equals("")) {
            returnStr = defaultValue;
        } else {
            returnStr = inValue;
        }
        return returnStr;
    }

    public static String checkNull(String inValue, String defaultValue, String makeValue) {
        String returnStr = null;
        if (inValue == null) {
            returnStr = defaultValue;
        } else if (inValue.equals("")) {
            returnStr = defaultValue;
        } else {
            returnStr = makeValue;
        }
        return returnStr;
    }


    public static int checkNull(String inValue, int defaultValue) {
        int value = 0;
        if (inValue == null) {
            value = defaultValue;
        } else if (inValue.equals("")) {
            value = defaultValue;
        } else {
            try {
                value = Integer.parseInt(inValue);
            } catch (NumberFormatException ignore) {}
        }
        return value;
    }  

    public static String replaceNull(String inValue, String defaultValue) {
        String returnStr = null;
        if (inValue == null) {
            returnStr = defaultValue;
        } else if (inValue.equals("")) {
            returnStr = defaultValue;
        } else {
            returnStr = inValue;
        }
        return returnStr;
    }

    public static String replaceNull(String inValue, String defaultValue, String outValue) {
        String returnStr = null;
        if (inValue == null) {
            returnStr = defaultValue;
        } else if (inValue.equals("")) {
            returnStr = defaultValue;
        } else {
            returnStr = outValue;
        }
        return returnStr;
    }

    public static int replaceNull(String inValue, int defaultValue) {
        int value = 0;
        if (inValue == null) {
            value = defaultValue;
        } else if (inValue.equals("")) {
            value = defaultValue;
        } else {
            try {
                value = Integer.parseInt(inValue);
            } catch (NumberFormatException ignore) {}
        }
        return value;
    }
    
    public static String[] replaceNull(String[] inValue, String[] defaultValue) {
    	String[] returnStr = null;
        if (inValue == null) {
            returnStr = defaultValue;
        } else if (inValue.length == 0) {
            returnStr = defaultValue;
        } else {
            returnStr = inValue;
        }
        return returnStr;
    }

    public static boolean isValidSsoSession(HttpServletRequest request, HttpServletResponse response) {
    	String confPath = "";
    	try {
			confPath = Configuration.getInstance().get("conf.sso.property.file");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	//SSOApiManager ssoApi = new SSOApiManager(confPath);
    	
    	return true;
    }
    
    public static boolean isValidSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUserVO") == null) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidSession(HttpServletRequest request, HttpServletResponse response) {
    	boolean ssoApply = false;
    	try {
			ssoApply = Configuration.getInstance().getBoolean("conf.sso.apply");
		} catch (Exception e) {
			ssoApply = false;
		}
    	
    	if (ssoApply) {		// SSO
    		return isValidSsoSession(request,response);
    	} else {
    		return isValidSession(request);
    	}
    }

    public static boolean isNull(String inValue) {
        if (inValue == null) {
            return true;
        } else if (inValue.equals("")) {
            return true;
        } else if (inValue.equals(" ")) {
            return true;
        } else if (inValue.equals("null")) {
        	return true;
        } else {
            return false;
        }
    }
    
    public static String nullCheck(String inValue) {
        if (inValue == null) {
            return "";
        } else if (inValue.equals("")) {
            return "";
        } else if (inValue.equals("null")) {
        	return "";
        } else {
            return inValue;
        }
    }
    
    public static boolean isDecimal(String str){
		boolean result = false;
		Pattern pattern = Pattern.compile("^[0-9]+(.[0-a]*)?$");
		Matcher matcher = pattern.matcher(str);
		result = matcher.matches();
		
		return result;
	}
    
    private static boolean isYearFormat(String str){
		boolean result = false;
		
		Pattern pattern = Pattern.compile("[0-9]{4}");
		Matcher matcher = pattern.matcher(str);
		result = matcher.matches();
		
		return result;
	}

    // 접수상태 별 페이지 이동 - 정책지정과제
    public static String setPath(String subjectStatusCD, String subjectSN, String progStatusCD) {
        String pageUrl = null;

        if (subjectStatusCD == null || subjectSN == null) {
            return "";
        }

        try {
            if (Integer.parseInt(progStatusCD) == 1440) { // 기관
                pageUrl = "switch.do?prefix=/accept&page=/info.do?method=putForm&subjectSN=" + subjectSN +
                          "&subjectStatusCD=" + subjectStatusCD;
            } else if (Integer.parseInt(subjectStatusCD) == 1) { // 기관
                pageUrl = "switch.do?prefix=/accept&page=/org.do?method=getList&subjectSN=" + subjectSN +
                          "&subjectStatusCD=" + subjectStatusCD;
            } else if (Integer.parseInt(subjectStatusCD) == 2) { // 연구책임자
                pageUrl = "switch.do?prefix=/accept&page=/humanResource.do?method=getList&subjectSN=" + subjectSN +
                          "&subjectStatusCD=" + subjectStatusCD;
            } else if (Integer.parseInt(subjectStatusCD) == 3) { // 참여연구원
                pageUrl = "switch.do?prefix=/accept&page=/researcher.do?method=getList&subjectSN=" + subjectSN +
                          "&subjectStatusCD=" + subjectStatusCD;
            } else if (Integer.parseInt(subjectStatusCD) == 4) { // 비목별연구비
                pageUrl = "switch.do?prefix=/accept&page=/itemReschCost.do?method=getList&subjectSN=" + subjectSN +
                          "&subjectStatusCD=" + subjectStatusCD;
            } else if (Integer.parseInt(subjectStatusCD) == 5) { // 목표 및 내용
                pageUrl = "switch.do?prefix=/accept&page=/organGoal.do?method=getList&subjectSN=" + subjectSN +
                          "&subjectStatusCD=" + subjectStatusCD;
            } else if (Integer.parseInt(subjectStatusCD) == 6) { // 사업계획서
                pageUrl = "switch.do?prefix=/accept&page=/doc.do?method=getDocList&subjectSN=" + subjectSN +
                          "&subjectStatusCD=" + subjectStatusCD;
            } else if (Integer.parseInt(subjectStatusCD) == 99) { // 접수완료
                pageUrl = "javascript:getWin('orgWin','accept','info.do','getWin','subjectSN=" + subjectSN +
                          "','900','600')";

            }

            logger.debug(">>>pageUrl : " + pageUrl);
        } catch (Exception ex) {
            logger.error("Exception : " + ex);
        }
        return pageUrl;
    }

    //  특정문자 치환
    public static String StrReplaceAll(String oldString, String from, String to) {
        String newString = oldString;
        int offset = 0;

        while ((offset = newString.indexOf(from, offset)) > -1) {
            StringBuffer temp = new StringBuffer(newString.substring(0, offset));
            temp.append(to);
            temp.append(newString.substring(offset + from.length()));
            newString = temp.toString();
        }
        return newString;
    }

    public static String[] filedDiv(String strValue, String f) {
        StringTokenizer token = new StringTokenizer(strValue, f);
        String s[] = new String[token.countTokens()];
        int i = 0;

        while (token.hasMoreTokens()) {
            s[i] = token.nextToken();
            i++;
        }
        return s;
    }

    // 엔터값 처리
    public static String textToHtml(String strText) {

        try {
            strText = textToHtmlIndex(strText, '\n', "<br>");
            strText += "<br>";
        } catch (StringIndexOutOfBoundsException se) {

        }
        return strText;
    } // textToHtml end

    public static String textToHtmlIndex(String strText, char delim, String changeStr) {
        int fromIndex = 0;
        int start = 0;
        int end = 0;
        String strTmp = null;
        StringBuffer sb = new StringBuffer();

        try {
            while (strText.indexOf(delim, fromIndex) != -1) {
                strTmp = strText.substring(fromIndex);
                start = fromIndex;
                end = (strTmp.indexOf(delim) != -1) ? strTmp.indexOf(delim) : strTmp.length();
                sb.append(strTmp.substring(0, end - 1)).append(changeStr);
                end += start;
                fromIndex = end + 1;
            }
            strTmp = strText.substring(fromIndex);
            end = strTmp.length();
            sb.append(strTmp.substring(0, end));
        } catch (StringIndexOutOfBoundsException se) {

        }
        return sb.toString();
    } // textToHtmlIndex end

 
    public static void bufToWrite(CLOB cl, String str) {
    	try {
    		// 스트림을 이용한 값 저장
    		BufferedWriter writer = new BufferedWriter(cl.getCharacterOutputStream());
    		writer.write(str);
    		writer.close();

    	} catch(Exception e) {
    		e.printStackTrace();
    		System.out.println("Exception bufToWrite : " + e.toString());
    	}
    }
    
    public static StringBuffer streamToBuf(Reader rd) {
    	int readcnt;
    	StringBuffer sb = new StringBuffer();
    	char[] buf = new char[1024];
    	try {
    		while ((readcnt = rd.read(buf, 0, 1024)) != -1) {
    		    // 스트림으로부터 읽어서 스트링 버퍼에 넣는다.
    		    sb.append(buf, 0, readcnt);
    		}
    	} catch(Exception e) {
    		//System.out.println("Exception streamToBuf Method: " + e.toString());
    		sb = new StringBuffer();
    	} finally {
    		return sb;
    	}
    }
    
    public static boolean getLoginState() {
    	boolean ret = false;
    	
    	
    	
    	return ret;
    }

    public static String getTodayFormat(String todayFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(todayFormat);
        Date date = new Date();
        return sdf.format(date);
    } //getToday end

    public static String getTomorrowFormat(String todayFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(todayFormat);
        Date date = new Date();
        int day = date.getDate();
        date.setDate(day+1);
        return sdf.format(date);
    } //getToday end

    public static LoginVO getLoginUserVO(HttpServletRequest request) {
        HttpSession session = request.getSession();
        LoginVO loginUserVO = (LoginVO) session.
                getAttribute("loginUserVO");
        return loginUserVO;
    }
    
    // 로그인 사용자의 아이디
    public static String getLoginId(HttpServletRequest request) {
        String result = null;
        HttpSession session = request.getSession();
        LoginVO loginUserVO = (LoginVO) session.
                getAttribute("loginUserVO");
        result = loginUserVO.getLogin_id();
        return result;
    }
    
    // 로그인 사용자의 이름
    public static String getName(HttpServletRequest request) {
        String result = null;
        HttpSession session = request.getSession();
        LoginVO loginUserVO = (LoginVO) session.
                getAttribute("loginUserVO");
        result = loginUserVO.getName();
        return result;
    }

    public static boolean isValidRole(HttpServletRequest request
		            , ServletContext context
		            , ActionForm form) throws Exception {
		HttpSession session = request.getSession();
		if (session.getAttribute("loginUserVO") == null) {
			return false;
		} else {
			Vector vect = new Vector();
			String requestURI = request.getRequestURI();
			StringTokenizer st = new StringTokenizer(requestURI,"/");
			while (st.hasMoreTokens()) {
				for(int i = 0 ; i < st.countTokens() ; i++){
					vect.addElement(st.nextToken());
				}
			}
			String page = (String)vect.lastElement();
			int idx = requestURI.indexOf(page);
			String prefix = requestURI.substring(0, idx-1);
			
			StringBuffer sbCurrentURI = new StringBuffer();
			sbCurrentURI.append("/switch.do?prefix=").append(prefix)
						.append("&page=/").append(page).append("?method=").append(request.getParameter("method"));
			String currentURI = sbCurrentURI.toString();
			System.out.println("########## currentURI : "+ currentURI);
			HashMap menuRoleMap = (HashMap)context.getAttribute("menuRoleMap");
			if(!menuRoleMap.containsKey(currentURI)) return false;
			ArrayList authList = (ArrayList)menuRoleMap.get(currentURI);
			
			LoginVO vo = (LoginVO) request.getSession().getAttribute("loginUserVO");
			String userRole = vo.getRoleCD();
			System.out.println("######## userRole : " + userRole);
			for(int i=0; i<authList.size(); i++) {
				HashMap roleAccessAuth = (HashMap)authList.get(i);
				String roleCd = (String)roleAccessAuth.get("role_cd");
				System.out.println("######### menu RoleCd : " + roleCd);
				if(roleCd.equals(userRole)) return true;
			}
			return false;
		}
	}

    public static void setAccessAuthByRole(HttpServletRequest request, ActionForm form, ServletContext context) throws Exception {
    	HttpSession session = request.getSession();
    	if (session.getAttribute("loginUserVO") == null) {
    		throw new Exception();
    	} else {    		
    		Vector vect = new Vector();
    		String requestURI = request.getRequestURI();
			StringTokenizer st = new StringTokenizer(requestURI,"/");
			while (st.hasMoreTokens()) {
				for(int i = 0 ; i < st.countTokens() ; i++){
					vect.addElement(st.nextToken());
				}
			}
			String page = (String)vect.lastElement();
			int idx = requestURI.indexOf(page);
			String prefix = requestURI.substring(0, idx-1);
			
			StringBuffer sbCurrentURI = new StringBuffer();
			sbCurrentURI.append("/switch.do?prefix=").append(prefix)
			          .append("&page=/").append(page).append("?method=").append(request.getParameter("method"));
			String currentURI = sbCurrentURI.toString();
			HashMap menuRoleMap = (HashMap)context.getAttribute("menuRoleMap");
			if(!menuRoleMap.containsKey(currentURI)) throw new Exception("Not Matched MenuID!!!");
			ArrayList authList = (ArrayList)menuRoleMap.get(currentURI);
			
			LoginVO vo = (LoginVO) request.getSession().getAttribute("loginUserVO");
			String userRole = vo.getRoleCD();
			for(int i=0; i<authList.size(); i++) {
				HashMap roleAccessAuth = (HashMap)authList.get(i);
				String roleCd = (String)roleAccessAuth.get("role_cd");
				if(roleCd.equals(userRole)) {
					BaseForm fm = (BaseForm)form;
		    		fm.setIs_del((String)roleAccessAuth.get("is_del"));
		    		fm.setIs_list((String)roleAccessAuth.get("is_list"));
		    		fm.setIs_put((String)roleAccessAuth.get("is_put"));
		    		fm.setIs_update((String)roleAccessAuth.get("is_update"));
		    		fm.setIs_view((String)roleAccessAuth.get("is_view"));
				}
			}
    	}    	
    }
    
    
    public static String getNumberFormat(Object target) {
        String result = "";
        
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        result = decimalFormat.format(target);
        
        return result;
    }
}
