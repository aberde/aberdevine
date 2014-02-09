package com.aps.rarp.co.util;

import java.text.DecimalFormat;

import org.springframework.stereotype.Component;

@Component
public class FormatUtil {
	
	private static final String NUMBER = "number";
	private static final String EXTNUMBER = "extnumber";				//음수, 소숫점 포함 숫자 포맷
	private static final String DATE = "date";							//날짜
	private static final String HDATE = "hdate";                          //한글 날짜
	private static final String TIME = "time";							//날짜 시간
	private static final int TIME_LEN = 12;
	private static final int DATE_LEN_YYYYMMDD = 8;
	private static final int DATE_LEN_YYYYMM = 6;
	private static final String RRN = "rrn";							//주민등록번호
	private static final int RRN_LEN = 13;
	private static final String POST_NUM = "post";						//우편번호
	private static final int POST_LEN = 6;								//우편번호 자리수
	
	
	
	
	/**
	 * 번호의 타입을 찾아서 형식을 그에 맞게 바꾼다.
	 * @param type
	 * @param value
	 * @return String
	 */
	public static String trnalateFormat(String type, String value){
		
		String result = null;
		if(DATE.equals(type)){ 				//일자 : ####-##-##
			result = FormatUtil.translateDate(value);		
		}else if(TIME.equals(type)){ 				//시간 : ####.##.## ##:##
			result = FormatUtil.translateTime(value);
		}else if(RRN.equals(type)){ 				//주민등록번호 : ######-#######
			result = FormatUtil.translateRrn(value);
		}else if(NUMBER.equals(type)){				//금액 : ###,###,### ...
			result = FormatUtil.translateNumber(value);		
		}else if(EXTNUMBER.equals(type)){				//금액 : ###,###,### ...
			result = FormatUtil.translateExtNumber(value);		
		}else if(POST_NUM.equals(type)){				//우편번호 : ###-###
			result = FormatUtil.translatePostNum(value);		
		}else if(HDATE.equals(type)){                //일자 : ####년 ##월 ##일
            result = FormatUtil.translateHdate(value);      
        }
		return result;
	}

	
	/**
	 * 우편번호를 인수로 받아 특정 Format(###-###)으로 변환한다. 
	 * @param value 
	 * @return
	 * @throws JspException
	 */
	public static String translatePostNum(String value) {
	    String rtrStr = "";
		if(value.length() == POST_LEN){
			StringBuilder post = new StringBuilder();
			post.append(value.substring(0, 3))
				.append("-")
				.append(value.substring(3, 6));
			rtrStr = post.toString();
		}else{
		    rtrStr = value;
		}
		return rtrStr;
	}
	

	/**
	 * 금액을 인수로 받아 특정 Format(###,###,### ...)으로 변환한다. 
	 * @param value 
	 * @return
	 * @throws JspException
	 */
	public static String translateNumber(String value) {
		
		String result = "";
		int length = value.length();
		
		if ( length <= 3 ){
		    result = value;
		}
		else
		{
		    StringBuffer resultBuffer = new StringBuffer("");
            for( int i = 0 ; i < length ; i++ )
            {
                if ( i != 0 && ( i % 3 == length % 3) ){
                    resultBuffer.append(','); 
                }
        
                if ( i < length ){
                    resultBuffer.append(value.charAt(i));
                }
            }
            result = resultBuffer.toString();
		}
        return result;
	}
	
	/**
	 * 음수, 소숫점 포함 Sring을 인수로 받아 특정 Format(###,###,### ...)으로 변환한다. 
	 * 소숫점 2자리까지 처리됨
	 * @param value 
	 * @return
	 * @throws JspException
	 */
	public static String translateExtNumber(String value) {
		
		String result = "";
		DecimalFormat df;
		
		if(value.indexOf('.') > 0){
			df = new DecimalFormat("#,##0.00");			
			double tempDouble = Double.parseDouble(value);			
			result = df.format(tempDouble);			
		}else{
			df = new DecimalFormat("#,##0");			
			int tempInt = Integer.parseInt(value);			
			result = df.format(tempInt);
		}
        
        return result;
	}

	

	/**
	 * 시간을 인수로 받아 특정 Format(YYYY.MM.DD HH:MM)으로 변환한다. 
	 * @param value 
	 * @return
	 * @throws JspException
	 */
	public static String translateTime(String value) {
	    String rtrStr = "";
		if(value.length() == TIME_LEN){
			StringBuilder time = new StringBuilder();
			time.append(value.substring(0, 4))
				.append(".")
				.append(value.substring(4, 6))
				.append(".")
				.append(value.substring(6, 8))
				.append(" ")
				.append(value.substring(8, 10))
				.append(":")
				.append(value.substring(10, 12));
			rtrStr = time.toString();
		}else{
		    rtrStr = value;
		}
		return rtrStr;
	}

	/**
	 * 주민등록번호를 인수로 받아 특정 Format(######-#######)으로 변환한다. 
	 * @param value 
	 * @return
	 * @throws JspException
	 */
	public static String translateRrn(String value) {
	    String rtrStr = "";
	    if(value.length() == RRN_LEN){
			StringBuilder rrn = new StringBuilder();
			rrn.append(value.substring(0, 6))
				.append("-")
				.append(value.substring(6, 13));
			rtrStr =  rrn.toString();
		}else{
		    rtrStr =  value;
		}
	    return rtrStr;
	}

	

	/**
	 * 날짜를 인수로 받아 특정 Format으로 변환한다.
	 * @param value 
	 * @return
	 * @throws JspException
	 */
	public static String translateDate(String value)  {
	    String rtrStr = "";
		if(value.length() == DATE_LEN_YYYYMMDD){	
			StringBuilder date = new StringBuilder();
			date.append(value.substring(0, 4))
				.append("-")
				.append(value.substring(4, 6))
				.append("-")
				.append(value.substring(6, 8));
			rtrStr = date.toString();
		}
		else if(value.length() == DATE_LEN_YYYYMM){
		    rtrStr = value.substring(0, 4)
				+ "-"
				+ value.substring(4, 6);
		}
		else{
		    rtrStr = value;
		}
		return rtrStr;
	}
	
	   /**
     * 날짜를 인수로 받아 특정 Format으로 변환한다.
     * @param value 
     * @return
     * @throws JspException
     */
    public static String translateHdate(String value)  {
        String rtrStr = "";
        if(value.length() == DATE_LEN_YYYYMMDD){    
            StringBuilder date = new StringBuilder();
            date.append(value.substring(0, 4))
                .append("년 ")
                .append(value.substring(4, 6))
                .append("월 ")
                .append(value.substring(6, 8))
                .append("일");
            rtrStr = date.toString();
        }
        else if(value.length() == DATE_LEN_YYYYMM){
            rtrStr = value.substring(0, 4)
                + "년 "
                + value.substring(4, 6)
                + "월";
        }
        else{
            rtrStr = value;
        }
        return rtrStr;
    }
	
	
	
}
