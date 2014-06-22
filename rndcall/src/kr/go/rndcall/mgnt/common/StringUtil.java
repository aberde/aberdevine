package kr.go.rndcall.mgnt.common;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Clob;
import java.util.Calendar;
import java.util.StringTokenizer;

import oracle.sql.CLOB;

/**
 * 문자열과 관련된 일반적인 메쏘드를 정의한다
 */

public final class StringUtil {
	public  String[]  isObjNull(String s[])
	{
		String sReturn[] ={"No Param"};
		if(s != null)
			sReturn = s;

		return sReturn;

	}
//	HWP의 내용을 CLOB필드로 저장시 사용
	public static void bufToWrite(CLOB cl, String str)
	{

		try
		{
			// 스트림을 이용한 값 저장
			BufferedWriter writer = new BufferedWriter(cl.getCharacterOutputStream());
			writer.write(str);
			writer.close();

		}catch(Exception e)
		{
			System.out.println("Exception bufToWrite : " + e.toString());
		}
	}
	
	
	// DB의 CLOB필드를 Text로 가져올때 사용
	public static StringBuffer streamToBuf(Reader rd)
	{
		if (rd == null) return new StringBuffer();
		
		int readcnt;
		StringBuffer sb = new StringBuffer();
		char[] buf = new char[1024];
		try
		{
		
			while ((readcnt = rd.read(buf, 0, 1024)) != -1) 
			{
			 // 스트림으로부터 읽어서 스트링 버퍼에 넣는다.
			 sb.append(buf, 0, readcnt);
			}

			//rd.close();

		}catch(Exception e)
		{
			System.out.println("Exception streamToBuf Method: " + e.toString());
			sb = new StringBuffer();
		}finally
		{
			return sb;
		}
	}
	
	
	
	/**
	 * 주어진 길이보다 길이가 작은 문자열을 앞에 0을 붙여 패딩한다 <BR>
	 * 
	 * @param str
	 *            문자열
	 * @param len
	 *            길이
	 * @return 앞에 '0'으로 패딩된 문자열을 리턴한다. 단, 주어진 길이보다 크거나 같으면 원본문자열을 그대로 리턴한다
	 */

	public static String paddingZero(String str, int len) {
		int strLen = str.length();
		int cab = 0;
		String tmp = "";
		if (strLen >= len)
			return str;
		else
			cab = len - strLen;

		for (int ii = 0; ii < cab; ii++) {
			tmp = "0" + tmp;
		}

		return tmp + str;
	}

	/**
	 * 첫문자를 대문자로 바꿈 <BR>
	 * 
	 * @param str
	 *            입력문자열
	 * @return 변환된 문자열을 리턴한다
	 */

	public static String initCap(String str) {
		// A: 65, a: 97
		// 소문자일 경우 대문자로 변경
		String second = str.substring(1);
		char h = str.charAt(0);
		if (h >= 'a' && h <= 'z') {
			String first = String.valueOf((char) (h - 32));
			return first + second;
		} else
			return str;
	}

	/**
	 * 원본문자열(str)에서 캐릭터(ch)를 찾아 제거한다 <BR>
	 * 
	 * @param str
	 *            입력문자열
	 * @param 제거할
	 *            문자
	 * @return 변환된 문자열을 리턴한다
	 */

	public static String removeChar(String str, char ch) {
		return removeChar(str, String.valueOf(ch));
	}

	/**
	 * 원본문자열(str)에서 문자열(ch)을 찾아 제거한다 <BR>
	 * 
	 * @param str
	 *            입력문자열
	 * @param 제거할
	 *            문자열
	 * @return 변환된 문자열을 리턴한다
	 */

	public static String removeChar(String str, String ch) {
		StringBuffer buff = new StringBuffer();
		StringTokenizer token = new StringTokenizer(str, ch);
		while (token.hasMoreTokens()) {
			buff.append(token.nextToken());
		}

		return buff.toString();
	}

	/**
	 * 문자열을 정수로 변환한다 <br>
	 * 
	 * @param str
	 *            입력문자열
	 * @return 입력문자열이 NULL 일 경우에는 0, 그외는 변환된 정수를 리턴한다.
	 */

	public static int str2int(String str) {
		if (str == null)
			return 0;
		else
			return Integer.valueOf(str).intValue();
	}

	/**
	 * 정수를 문자열로 변환한다 <br>
	 * 
	 * @param i
	 *            입력정수
	 * @return 변환된 문자열을 리턴한다.
	 */

	public static String int2Str(int i) {
		return (new Integer(i)).toString();
	}

	/**
	 * 널인 문자열을 스페이스("")로 치환한다 <BR>
	 * 단, 좌우 공백이 있는 문자열은 trim 한다 <br>
	 * 
	 * @param s
	 *            입력문자열
	 * @return 치환된 문자열
	 */

	public static String null2space(String s) {
		if (s == null || s.length() == 0)
			return "";
		else
			return s.trim();
	}

	/**
	 * 널이거나 빈 문자열을 "&nbsp;"로 변환한다 <BR>
	 * 단, 좌우 공백이 있는 문자열은 trim 한다 <br>
	 * 
	 * @param s
	 *            입력문자열
	 * @return 치환된 문자열
	 */

	public static String null2nbsp(String org) {
		if (org == null || isEmptyWithoutWhitespace(org))
			return "&nbsp;";
		else
			return org.trim();
	}

	/**
	 * 널이거나 빈 문자열을 원하는 스트링으로 변환한다 <BR>
	 * 단, 좌우 공백이 있는 문자열은 trim 한다 <br>
	 * 
	 * @param s
	 *            입력문자열
	 * @return 치환된 문자열
	 */

	public static String null2str(String org, String converted) {
		if (org == null || isEmptyWithoutWhitespace(org))
			return converted;
		else
			return org.trim();
	}

	/**
	 * 문자열이 널이거나 빈 공백문자열인지 CHECK 한다 <br>
	 * 
	 * @param s
	 *            입력문자열
	 * @return 널이거나 공백일 경우 true, 아닐경우 false 를 리턴한다
	 */

	public static boolean isNull(String str) {
		if (str == null || isEmptyWithoutWhitespace(str))
			return true;
		else
			return false;
	}

	/**
	 * 문자열에서 주어진 separator 로 쪼개어 문자배열을 생성한다 <br>
	 * 
	 * @param str
	 *            원본문자열
	 * @param separator
	 *            원하는 token 문자열
	 * @return 스트링배열
	 */

	public static String[] split(String str, String separator) {
		StringTokenizer st = new StringTokenizer(str, separator);
		String[] values = new String[st.countTokens()];
		int pos = 0;
		while (st.hasMoreTokens()) {
			values[pos++] = st.nextToken();
		}

		return values;
	}

	public static String[] getParser(String str, String sep) {
		// System.out.println("getParser="+str);
		int count = 0;
		int index = -1;
		int index2 = 0;

		if (str == null)
			return null;

		do {
			++count;
			++index;
			index = str.indexOf(sep, index);
		} while (index != -1);
		// 마지막에 구분자가 있는지 check
		if (isNull(str.substring(index2))) {
			count--;
		}
		String[] substr = new String[count];
		index = 0;
		int endIndex = 0;
		for (int i = 0; i < (count); i++) {
			endIndex = str.indexOf(sep, index);
			if (endIndex == -1) {
				substr[i] = str.substring(index);
			} else {
				substr[i] = str.substring(index, endIndex);
			}
			index = endIndex + 1;
		}
		return substr;
	}

	/**
	 * 문자열 배열을 주어진 separator 로 연결하여 문자열을 생성한다 <br>
	 * 
	 * @param list
	 *            스트링배열
	 * @param separator
	 *            원하는 token 문자열
	 * @return 합쳐진 문자열을 리턴한다
	 */

	public static String join(String list[], String separator) {
		StringBuffer csv = new StringBuffer();
		for (int i = 0; i < list.length; i++) {
			if (i > 0)
				csv.append(separator);
			csv.append(list[i]);
		}
		return csv.toString();
	}

	/**
	 * Exception 객체로 에러메시지 문자열을 생성한다 <br>
	 * 
	 * @param e
	 *            Throwable 객체
	 * @return 에러문자열
	 */

	public static String stackTrace(Throwable e) {
		String str = "";
		try {
			ByteArrayOutputStream buff = new ByteArrayOutputStream();
			e.printStackTrace(new PrintWriter(buff, true));
			str = buff.toString();
		} catch (Exception f) {
		}

		return str;
	}

	/**
	 * 원본문자열에서 뒤로부터 주어진 문자열(ch)을 찾아 제거한다 <br>
	 * 
	 * @param str
	 *            원본문자열
	 * @param ch
	 *            제거할 문자열
	 * @return 제거된문자열을 리턴한다
	 * @exception 파싱에러시
	 *                발생
	 */

	public static String removeLastChar(String str, String ch) throws Exception {
		int pos = str.lastIndexOf(ch);
		if (pos == -1)
			return str;
		else
			return str.substring(0, pos) + str.substring(pos + 1);
	}

	/**
	 * 문자열중 특정문자를 치환한다
	 * 
	 * @param str
	 *            대상문자열
	 * @param src
	 *            치환당할 문자
	 * @param tgt
	 *            치환할 문자
	 * @return 완성된 문자열
	 */
	public static String replace(String str, String src, String tgt) {
		String buf = "";
		String ch = null;

		if (str == null || str.length() == 0)
			return "";

		int len = src.length();
		for (int i = 0; i < str.length(); i++) {

			ch = str.substring(i, i + len);
			if (ch.equals(src))
				buf = buf + tgt;
			else
				buf = buf + ch;
		}
		return buf;
	}

	/**
	 * 문자열중 특정문자열을 치환한다
	 * 
	 * @param str
	 *            대상문자열
	 * @param src
	 *            치환당할 문자열
	 * @param tgt
	 *            치환할 문자열
	 * @return 완성된 문자열
	 */
	public static String replaceString(String str, String src, String tgt) {
		StringBuffer ret = new StringBuffer();

		if (str == null)
			return null;
		if (str.equals(""))
			return "";

		int start = 0;
		int found = str.indexOf(src);
		while (found >= 0) {
			if (found > start)
				ret.append(str.substring(start, found));

			if (tgt != null)
				ret.append(tgt);

			start = found + src.length();
			found = str.indexOf(src, start);
		}

		if (str.length() > start)
			ret.append(str.substring(start, str.length()));

		return ret.toString();
	}

	/**
	 * 입력받은 String을 원하는 길이만큼 원하는 문자로 오른쪽을 채워주는 함수
	 * 
	 * @param calendar
	 *            입력받은 String
	 * @return 지정된 문자로 채워진 String
	 */
	public static String rpad(String str, int len, char pad) {
		String result = str;
		int templen = len - result.getBytes().length;

		for (int i = 0; i < templen; i++) {
			result = result + pad;
		}

		return result;
	}

	/**
	 * 입력받은 String을 원하는 길이만큼 원하는 문자로 왼쪽을 채워주는 함수
	 * 
	 * @param calendar
	 *            입력받은 String
	 * @return 지정된 문자로 채워진 String
	 */
	public static String lpad(String str, int len, char pad) {
		String result = str;
		int templen = len - result.getBytes().length;

		for (int i = 0; i < templen; i++)
			result = pad + result;

		return result;
	}

	/**
	 * 문자가 길경우에 특정 바이트 단위 길이로 자른다. (by ssoon 2005.03.28)
	 * 
	 * @param str
	 *            문자열
	 * @param byteSize
	 *            남길 문자열의 길이
	 * @return string 자르고 남은 문자열
	 * @throws Exception
	 */
	public static String getStrCut(String str, int byteSize) throws Exception {
		return getStrCut(str, byteSize, "...");
	}

	/**
	 * 문자가 길경우에 특정 바이트 단위 길이로 자른다. (by ssoon 2005.03.28)
	 * 
	 * @param str
	 *            문자열
	 * @param byteSize
	 *            남길 문자열의 길이
	 * @param str2
	 *            남길 문자열 뒤에 적어줄 문자열
	 * @return string 자르고 남은 문자열
	 * @throws Exception
	 */
	public static String getStrCut(String str, int byteSize, String str2)
			throws Exception {
		int rSize = 0;
		int len = 0;

		if (str.getBytes().length > byteSize) {
			for (; rSize < str.length(); rSize++) {
				if (str.charAt(rSize) > 0x007F)
					len += 2;
				else
					len++;

				if (len > byteSize)
					break;
			}
			str = str.substring(0, rSize) + str2;
		}
		return str;
	}
	

	/**
	 * formatMoney(by ssoon 2005.03.28)
	 * 
	 * @param str
	 * @return String
	 */
	public static String formatMoney(String str) {
		double iAmount = (new Double(str)).doubleValue();
		java.text.DecimalFormat df = new java.text.DecimalFormat(
				"###,###,###,###,###,###,###");
		return df.format(iAmount);
	}

	/**
	 * 대상 str가 null이거나 ""인경우 경우 "&nbsp;"을 return(by ssoon 2005.03.28)
	 * 
	 * @param str
	 *            대상 스트링
	 */
	public static String strToNbsp(String str) throws Exception {
		if (str == null || null2space(str).equals(""))
			return "&nbsp;";
		else
			return str;
	}

	/**
	 * html --> text 로 변환
	 * 
	 * @param strString
	 *            데이터베이스에 있는 데이터 문자열이다.
	 * @return 바뀌어진 값을 넘겨준다.
	 */
	public static String changeHtmlToText(String strString) {
		String strNew = "";

		try {
			StringBuffer strTxt = new StringBuffer("");
			char chrBuff;
			int len = 0;
			int i = 0;

			len = strString.length();

			for (i = 0; i < len; i++) {
				chrBuff = (char) strString.charAt(i);
				switch (chrBuff) {
				case '<':
					strTxt.append("&lt");
					break;
				case '>':
					strTxt.append("&gt");
					break;
				case 10:
					strTxt.append("<br>");
					break;
				case 13:
					// strTxt.append("<br>");
					break;
				case ' ':
					strTxt.append(" ");
					break;
				default:
					strTxt.append(chrBuff);
				}// switch
			}// for

			strNew = strTxt.toString();

		} catch (Exception ex) {
		}

		return strNew;
	}

	public static String toHtmlString(String str) {
		if (str == null)
			return "";
		if ("".equals(str))
			return "-";

		String tempStr = replace(str, "\n", "<br>");

		tempStr = StringUtil.replaceString(tempStr, "<a href", "<ahref");
		tempStr = StringUtil.replace(tempStr, " ", "&nbsp;");
		tempStr = StringUtil.replaceString(tempStr, "<ahref", "<a href");
		return tempStr;
	}

	public static String toHtmlString(String str, String clsName) {
		if (str == null)
			return "";
		if ("".equals(str))
			return "-";

		String tempStr = replace(str, "\n", "<br>");

		tempStr = StringUtil.replaceString(tempStr, "<a href", "<ahref");
		tempStr = StringUtil.replace(tempStr, " ", "&nbsp;");
		tempStr = StringUtil.replaceString(tempStr, "<ahref", "<a class='"
				+ clsName + "' href");
		return tempStr;
	}

	public static String tagRemove(String s) {
		StringBuffer strip_html = new StringBuffer();
		char[] buf = s.toCharArray();
		int j = 0;
		for (; j < buf.length; j++) {
			if (buf[j] == '<') {
				for (; j < buf.length; j++) {
					if (buf[j] == '>') {
						break;
					}
				}

			} else {
				strip_html.append(buf[j]);
			}
		}
		return strip_html.toString();
	}

	/**
	 * ' --> &#39; 변환 " --> &quot; 변환
	 * 
	 * @param strString
	 *            데이터베이스에 있는 데이터 문자열이다.
	 * @return 바뀌어진 값을 넘겨준다.
	 */
	public static String replaceDang(String str) {
		if (str != null && !str.equals("")) {
			str = StringUtil.null2space(str);
			str = StringUtil.replaceString(str, "'", "&#39;");
			str = StringUtil.replaceString(str, "\"", "&quot;");
		}
		return str;
	}

	/**
	 * ' <-- &#39; 변환 " <-- &quot; 변환
	 * 
	 * @param strString
	 *            데이터베이스에 있는 데이터 문자열이다.
	 * @return 바뀌어진 값을 넘겨준다.
	 */
	public static String replaceDangRev(String str) {
		if (str != null && !str.equals("")) {
			str = StringUtil.replaceString(str, "&#39;", "'");
			str = StringUtil.replaceString(str, "&quot;", "\"");
		}
		return str;
	}

	/**
	 * & --> &amp; 변환 트림처리
	 * 
	 * @param strString
	 *            데이터베이스에 있는 데이터 문자열이다.
	 * @return 바뀌어진 값을 넘겨준다.
	 */
	public static String replaceAmp(String str) {
		if (str != null && !str.equals("")) {
			str = StringUtil.null2space(str);
			str = StringUtil.replaceString(str, "&", "&amp;");
		}
		return str;
	}

	public static String replaceAmpRev(String str) {
		if (str != null && !str.equals("")) {
			str = StringUtil.replaceString(str, "&amp;", "&");

		}
		return str;
	}

	public static String checkHtmlView(String strString) {
		String strNew = "";

		try {
			StringBuffer strTxt = new StringBuffer("");

			char chrBuff;
			int len = strString.length();

			for (int i = 0; i < len; i++) {
				chrBuff = (char) strString.charAt(i);

				switch (chrBuff) {
				case '<':
					strTxt.append("&lt;");
					break;
				case '>':
					strTxt.append("&gt;");
					break;
				case '"':
					strTxt.append("&quot;");
					break;
				case 10:
					strTxt.append("<br>");
					break;
				// case 13 :
				// strTxt.append("<br>");
				// break;
				case ' ':
					strTxt.append("&nbsp;");
					break;
				// case '&' :
				// strTxt.append("&amp;");
				// break;
				default:
					strTxt.append(chrBuff);
				}
			}

			strNew = strTxt.toString();

		} catch (Exception ex) {
		}

		return strNew;
	}

	public static String checkHtmlEdit(String strString) {
		String strNew = "";

		try {
			StringBuffer strTxt = new StringBuffer("");

			char chrBuff;
			int len = strString.length();

			for (int i = 0; i < len; i++) {
				chrBuff = (char) strString.charAt(i);

				switch (chrBuff) {
				case '<':
					strTxt.append("&lt;");
					break;
				case '>':
					strTxt.append("&gt;");
					break;
				case '"':
					strTxt.append("&quot;");
					break;
				// case '&' :
				// strTxt.append("&amp;");
				// break;
				default:
					strTxt.append(chrBuff);
				}
			}

			strNew = strTxt.toString();

		} catch (Exception ex) {
		}

		return strNew;
	}

	public static String byteCutLine(String str, int cut, String pushChar) {
		if (str == null)
			return null;

		int kk = str.lastIndexOf("&nbsp;");
		String nbstr = "";
		if (kk != (-1)) {
			str.substring(0, kk + 6);
			str = str.substring(kk + 6);
		}

		byte[] bl = str.getBytes();
		if (bl.length <= cut)
			return str; // 한줄이면 리턴

		int size = (int) (Math.ceil((float) bl.length / (float) cut)); // 총 라인수

		StringBuffer reVal = new StringBuffer();
		String tempStr = str;
		for (int i = 0; i < size - 1; i++) { // 마지막 라인만 남기고 더한다.

			byte[] temp1 = new byte[cut];
			System.arraycopy(tempStr.getBytes(), 0, temp1, 0, cut);

			String val = new String(temp1);
			int idx = val.length();

			reVal.append(tempStr.substring(0, idx + 1) + "<br>" + pushChar);
			tempStr = tempStr.substring(idx + 1, tempStr.length());
		}

		reVal.append(tempStr); // 마지막 라인을 추가한다.

		return nbstr + reVal.toString();

	}
	
	public static String Clob2String(Clob cl) {
		if (cl == null) {
			return "";
		} else {
			StringBuffer strOut = new StringBuffer();
			String aux;
			
			try {
				BufferedReader br = new BufferedReader(cl.getCharacterStream());
				
				while ((aux = br.readLine())!=null)
					strOut.append(aux);
				
				return strOut.toString();
				
			} catch (Exception e1) {
				return "";
			}
		}
	}
	
	public static Clob String2Clob(String str) {
		long l=0;
		oracle.sql.CLOB clobdocument = null;
		
		try
		{
			BufferedWriter br = new BufferedWriter(clobdocument.setCharacterStream(l));
			StringReader strrd=new StringReader(str);
			char aux;
			
			do{
				aux=(char)strrd.read();
				br.write(aux);
			} while(aux != -1);
			
			return clobdocument;
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public static boolean isEmptyWithoutWhitespace(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isWhitespace(s.charAt(i))) {
				return false;
			}
		}		
		return true;
	}
	
	public static boolean equalCheck(String s, String [] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (s.equals(arr[i])) return true;
		}
		return false;
	}
	
    public static String Rmspace(String Tmp) {
        char flag = ',';
        StringBuffer res = new StringBuffer();
        char[] del = {'.', '\'','<','>', '/', '_', '-', ':', '#', ')', '(', '=', '~', '+',
                     '\n', '\t', '\"', '&', '|', '!', ',', '?'};

        for (int i = 0; i < Tmp.length(); i++) {
            for (int j = 0; j < del.length; j++) {

                if (Tmp.charAt(i) == del[j]) {
                    flag = Tmp.charAt(i);
                }
            }
            if (flag != Tmp.charAt(i)) {
                res.append(Tmp.charAt(i));
            }
        }
        return res.toString();
    }
    
    public static void printTime() {
    	Calendar calendar = Calendar.getInstance();
    	           System.out.println("------------------------------------------------------------");
    		   System.out.print("현재 시간 :");
    		   System.out.print(calendar.get(Calendar.HOUR) + "시 ");
    		   System.out.print(calendar.get(Calendar.MINUTE) + "분 ");
    		   System.out.println(calendar.get(Calendar.SECOND) + "초 ");
    		   System.out.println("------------------------------------------------------------");
    	    calendar = null;
    }

    public static String removeHighlight(String str) {
    	return str.replaceAll("", "").replaceAll("32723m", "").replaceAll("32703m", "").replace('[', ' ').replaceAll("32723", "");
    }
    
    
    /**
	* 숫자값에 3 자리마다 , 를 붙힌다.
	* @param(int num)
	* @return String 변환된 문자열
	*/		
    public static String setComma(int num){        //이런식으로 300,000,000 표시

        java.text.NumberFormat nf = java.text.NumberFormat.getNumberInstance();
        String r_str = nf.format(num);

        return r_str;
    }    

    public static String escape(String src) {   
        int i;   
        char j;   
        StringBuffer tmp = new StringBuffer();   
        tmp.ensureCapacity(src.length() * 6);   
        for (i = 0; i < src.length(); i++) {   
            j = src.charAt(i);   
            if (Character.isDigit(j) || Character.isLowerCase(j)   
                    || Character.isUpperCase(j))   
                tmp.append(j);   
            else if (j < 256) {   
                tmp.append("%");   
                if (j < 16)   
                    tmp.append("0");   
                tmp.append(Integer.toString(j, 16));   
            } else {   
                tmp.append("%u");   
                tmp.append(Integer.toString(j, 16));   
            }   
        }   
        return tmp.toString();   
    }   

	public static String unescape(String src) {   
	    StringBuffer tmp = new StringBuffer();   
	    tmp.ensureCapacity(src.length());   
	    int lastPos = 0, pos = 0;   
	    char ch;   
	    while (lastPos < src.length()) {   
	        pos = src.indexOf("%", lastPos);   
	        if (pos == lastPos) {   
	            if (src.charAt(pos + 1) == 'u') {   
	                ch = (char) Integer.parseInt(src   
	                        .substring(pos + 2, pos + 6), 16);   
	                tmp.append(ch);   
	                lastPos = pos + 6;   
	            } else {   
	                ch = (char) Integer.parseInt(src   
	                        .substring(pos + 1, pos + 3), 16);   
	                tmp.append(ch);   
	                lastPos = pos + 3;   
	            }   
	        } else {   
	            if (pos == -1) {   
	                tmp.append(src.substring(lastPos));   
	                lastPos = src.length();   
	            } else {   
	                tmp.append(src.substring(lastPos, pos));   
	                lastPos = pos;   
	            }   
	        }   
	    }   
	    return tmp.toString();   
	}   


}
