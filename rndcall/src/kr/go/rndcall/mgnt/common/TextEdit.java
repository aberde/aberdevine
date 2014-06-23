package kr.go.rndcall.mgnt.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class  TextEdit  {
	
	public static String removeHtml(String text){
		text.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
		return text;
	}

	public static String getdate(String pattern) throws Exception{
		
		if (pattern.equals("")){
			pattern = "yyyyMMdd";
		}
		
		String nowDate;
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, new Locale("ko","KOREA"));
		java.util.Date date = new java.util.Date();
		nowDate = formatter.format(date);
		
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(formatter.parse(nowDate));
		} catch (Exception e) {
			// TODO 자동 생성된 catch 블록
			throw new Exception();
		}
		calendar.add(Calendar.DATE, -1);  

		
		return (formatter.format(calendar.getTime()));
		
	}
	
	public static String getFileToString(long inxSize){
		
		DecimalFormat df = new DecimalFormat("##,###.##");
		
		double FileSize = 0;
		String unit = "";

		if (Long.toString(inxSize).length() > 9 ){
			FileSize = (((double)inxSize)/(1000000000)) ;
			unit = "GB";
		}else if (Long.toString(inxSize).length() > 6){
			FileSize = (((double)inxSize)/(1000000)) ;
			unit = "MB";
		}else if (Long.toString(inxSize).length() > 3){
			FileSize = ((double)inxSize/1000) ;
			unit = "KB";
		}else{
			FileSize = (inxSize);
			unit = "byte";
		}

		return (df.format(FileSize) + " " + unit);
	}
	

	public static String getURLText(String strURL){
		
		String tmp = "";
		StringBuffer text = new StringBuffer();

		BufferedReader in;
		
		try {			
			in = new BufferedReader(new InputStreamReader((new URL(strURL)).openStream()));			
			
			while((tmp = in.readLine())!=null){
				text.append(tmp  + '\n');
			}		
			
			in.close();
		} catch (Exception e) {
			text = new StringBuffer();
		}
		
		return text.toString();
	}
	
	public static String trim( Object value ) {
		
		return trim((String)value);
	}
	public static String trim( String value ) {
		if ( value == null ) {
			return "";
		}
		return value.trim();
	}

	public static String decode(String value ,String encoding) throws UnsupportedEncodingException{
		value = trim(value);		
		value = URLDecoder.decode(value,encoding);		
		return value;
	}
	
	public static String encode(String value ,String encoding) throws UnsupportedEncodingException{
		value = trim(value);		
		value = URLEncoder.encode(value,encoding);
		return value;
	}
	
	public static String substringBetween(String str, String open, String close) {
        if (str == null || open == null || close == null) {
            return "";
        }
        int start = str.indexOf(open);
        if (start != -1) {
            int end = str.indexOf(close, start + open.length());
            if (end != -1) {
                return str.substring(start + open.length(), end);
            }
        }
        return "";
    }
	
	public static String replaceString( String source, String oldStr, String newStr ) {

		String replaceStr = "";	// Retrun value 변경된 문자열
		int startPosition = 0;	 // 검색 시작위치
		int resultPosition;	// 검색된 위치

		if ( ( source == null ) || ( source.length() == 0 ) ) return source;

		while(true) {
			// 변경하고자하는 문자열의 위치를 검색
			 resultPosition = source.indexOf( oldStr, startPosition );	
			 // 변경하고자하는 문자열이 검색되지 않았을경우 Loop를 벗어난다.
			 if ( resultPosition == -1 ) break;	
			 // 문자열(source)에서 검색된 위치까지 문자열을 잘라낸다.
			 replaceStr += source.substring( startPosition, resultPosition );	
			 // 잘라낸 문자열에 변경할 문자열(replaceStr)을 붙힌다.
			 replaceStr += newStr;	 

			 // 검색할 위치를 새로붙힌(newStr)의 문자열 만큼 전진시킨다.
			 startPosition = resultPosition + oldStr.length();	 
		} 

		 // 변경한 문자열에 뒤에 남은 문자열을 붙힌다.
		 // 변경할 문자열이 검색되지 않았을경우는 처음 부터 
		replaceStr += source.substring( startPosition );	 

		return replaceStr;

	}

	public static String HightLight (String text, String keyword, String color) throws Exception{
        try {
        	//문장 검색 
        	
        	keyword = parseQuery(keyword);	        
        	String term[] = keyword.split(" ");
        	
        	for (int i = 0; i < term.length; i++) {
        		String query = term[i];
				StringBuffer sbuf = new StringBuffer();
	            if( query != null && query.length() > 0 ) {
	
					int start = 0;
					int lastEnd = 0;
	
					Pattern p = Pattern.compile( query , Pattern.CASE_INSENSITIVE );
					Matcher m = p.matcher( text );
					
					while( m.find() ) {
						
						start = m.start();
						sbuf.append( text.substring(lastEnd, start) )
							.append("<font color='"+color+"'>"+m.group()+"</font>" );
						lastEnd = m.end();
						
					} 
					text = sbuf.append(text.substring(lastEnd)).toString();
	            }           
	        	
			}            
        } catch (Exception e) {
        	throw new Exception(e.toString());
        }
        
        return text;
	}	
	
	public static String HightLight (String text, String keyword) throws Exception{
        return HightLight(text, keyword, "blue");
	}
	
	public static String parseQuery(String str){
		try {
			//[]?*+()
			//str = str.replaceAll("+","");
			//dt:[20010101 TO 20010110]
			str = str.replaceAll(":"," ");
			str = str.replaceAll("[(]"," ");
			str = str.replaceAll("[)]"," ");
			
        	str = replaceString(str ,"[", "\\[");
        	str = replaceString(str , "]", "\\]");
        	str = replaceString(str ,"(", "\\(");
        	str = replaceString(str , ")", "\\)");
        	
        	str = replaceString(str ,"\"","");
        	
        	str = replaceString(str , "AND", "");
        	str = replaceString(str , "OR", "");
        	str = replaceString(str , "NOT", "");
        	str = replaceString(str , "TO", "");


////			str = str.replaceAll("[0-9s]","");	//숫자제거
//			str = str.replaceAll("[/^]"," ");
//			str = str.replaceAll("-"," ");
//			str = str.replaceAll(":"," ");
//			str = str.replaceAll("^"," ");
//			str = str.replaceAll("&"," ");
//			str = str.replaceAll("~"," ");
//			str = str.replaceAll("[\\[]"," ");
//			str = str.replaceAll("[]]"," ");
//			str = str.replaceAll("[*]"," ");
//			str = str.replaceAll("[(]"," ");
//			str = str.replaceAll("[)]"," ");
//			str = str.replaceAll("[?]"," ");
//			str = str.replaceAll("@"," ");
//			//() ?*	

		} catch (RuntimeException e) {
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
		}		
		return str;
	}
	

	public static String showApart(String query,String text) {
		if (text.length() < 150){
			return text;
		}
		query = query.replaceAll("\"","");
		query = query.toLowerCase();
		text = text.toLowerCase();

		String returnTxt = "";
		
		String[] q_query = query.split(" ");
		String[] q_text = text.split(" ");
		
		
		int[] irr= new int[q_text.length];   
		for (int i = 0; i < irr.length; i++) {
			irr[i] = 100000;
		}
		//arraylist 넣기 
		int k = 0;
	
		for (int i = 0; i < q_query.length; i++) {
			if (!q_query[i].equals("")){
				for (int j = 0; j < q_text.length; j++) {

					if (q_text[j].indexOf(q_query[i]) > -1){
						irr[k] = j;
						k++;
					}
				}
			}
		}

		Arrays.sort(irr);

		for (int i = 0; i < irr.length; i++) {
			if (irr[i]!=100000){

				int start = irr[i];
				int close = 0;
		
				if ((irr[i+1]-irr[i]) < 15){
					close = irr[i+1] + 5;	//2th is show 5 word.
					i++;
				}
				
				returnTxt = returnTxt + getApart(q_text,start,close);
			}
		}
		returnTxt = returnTxt.trim();

		if (returnTxt.length() > 300){	//긴 데이터는 300자에서 자른다

			returnTxt = returnTxt.substring(0,300);
			int last = returnTxt.lastIndexOf("...");
			returnTxt = returnTxt.substring(0,last );
		}else{
			if (text.length() > 320){ //작은 데이터터중 원본이 긴것들
				int len = returnTxt.length() ;
				String start = returnTxt.substring(0,3);
				String end = returnTxt.substring(len-3,len);
				
				//처음인 경우 생략한다.
				//if ((!start.equals("..."))&&(end.equals("..."))){			//처음
				//	int txt_len = text.length();
				//	returnTxt = returnTxt + "<font color='red'>처음</font>";
				///	returnTxt = returnTxt + text.substring(txt_len-len,txt_len);
				//}
				//끝인 경우 생략한다
				//else if ((start.equals("..."))&&(!end.equals("..."))){	//끝
				//	returnTxt = returnTxt + "<font color='red'>끝</font>";
				//} 
				if ((start.equals("..."))&&(end.equals("..."))){		//중간
					//뒷처리
					//int last = returnTxt.lastIndexOf("...");
					//returnTxt = returnTxt.substring(last-3,last );
					//returnTxt = returnTxt + returnTxt.substring(last-200,last );
					if (len > 100){
						len = 0;
					}else if(len > 500){
						len = 10;
					}else{
						len = 20;
					}

					returnTxt = returnTxt + text.substring(text.length()-100,text.length() );
					//returnTxt = text;
				}
			}else{		//작은 데이터터중 원본도 짧은 것들
				returnTxt = text;
			}
			

			//int start = 0;
			//int end =  text.length();
			//returnTxt = returnTxt + text.substring(len-returnTxt,len);
		}
		//returnTxt = returnTxt + returnTxt.length();

		return returnTxt;
	}

	public static String getApart(String str[],int open,int close){
		String returnStr = "";
		String start = "";
		String end = "";
		
		if (open!=0) {
			start  = "...";
		}
				
		if (close==0) {
			close = open + 7;
		}
		
		if (open > 5 ){
			open = open - 3;
		}else{
			open = 0;
		}
		
		if (close > str.length){
			close = str.length;
		}else{
			end = "...";
			
		}
		
		for (int i = open; i < close; i++) {
			returnStr = returnStr + " " + str[i];
		}
		
		return start + returnStr + end;
	}
	
	public static String showText(String text,String query) throws Exception{
		//html 테그 제거
		text = RemoveTag.removeTag(text);
		//특수 문자 제거
		query = parseQuery(query);
		//해당 부분 나오기
		try{
			text = showApart(query,text);
		}catch(Exception e){
			int len = text.length();
			if(len>350){
				//text = text.substring(0,320) + "...<b>" + e.toString() + "</b>";
				text = text.substring(0,320) + "..." ;
			}else{
				//text = text + "<b>" + e.toString() + "</b>";
			}
		}
		//색깔 바꾸기
		//text = convertColor(query,text);

		return text;
	}
	
	public static ArrayList explorerDIR(String root){
		ArrayList arrayList = new ArrayList();
		File f = new File(root);
		if(f.exists()){

			String[] str = f.list();
			if (str != null){	
				for (int i = 0; i < str.length; i++) {
					String path = root + File.separator + str[i];
				
					File file = new File(path);
					if (file.isDirectory()) {
						explorerDIR(path);	
					}else{
						arrayList.add(path);
					}
				}
			}
		}	
		
		return arrayList;
	}
	
	public static String getTextData(String path) throws Exception{
		return getTextData(path,"");
	}
	
	public static String getTextData(String path,String encodeing) throws Exception{
		
		if (encodeing.equals("")){
			encodeing = "KSC5601";
		}
		StringBuffer fielData = new StringBuffer();
		try {
			File f = new File(path);
			FileInputStream fi = new FileInputStream(f);
			InputStreamReader ir = new InputStreamReader(fi,encodeing);
			BufferedReader br = new BufferedReader( ir ,80000);
				
			String line = "";
			while((line = br.readLine()) != null)		{
			    fielData.append(line);
			}

			br.close();
			ir.close();
			fi.close();
		} catch (Exception e) {
			throw new Exception();
		} 
		return trim(fielData.toString()).replaceAll("[ ]+", " ");
	}

	public static String getExt(String fileName) throws Exception {
		try {
			int pos = fileName.lastIndexOf(".");
			fileName = fileName.substring(pos,fileName.length());
			fileName = fileName.toLowerCase();
		} catch (RuntimeException e) {
			throw new Exception();
		}
		return fileName;
	}

	public static void copy(String sorucePath, String targetPath) throws Exception {
		try {
			String command = "";
	
			if(System.getProperty("os.name").startsWith("Windows")){
				sorucePath = sorucePath.replace('/','\\');
				targetPath = targetPath.replace('/','\\');
				command = "cmd /c copy " + sorucePath + " " + targetPath;
			}else{
				sorucePath = sorucePath.replace('\\','/');
				targetPath = targetPath.replace('\\','/');
				command = "cp " + sorucePath + " " + targetPath;
			}

			Process p = Runtime.getRuntime().exec(command);
//			System.err.println(command);
			p.waitFor();
//			String line = "";
//			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
//			while ((line = input.readLine()) != null) {
//				System.err.println(line);
//			}
//			input.close();
			
			System.gc();
		} catch (Exception err) {
			throw new Exception();
		}
	}

}


 