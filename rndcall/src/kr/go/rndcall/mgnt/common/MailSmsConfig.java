package kr.go.rndcall.mgnt.common;

import java.io.File;

public class MailSmsConfig {

    public static String getTrinityRoot() {
     	String path = "";
     	String env = TextEdit.trim(System.getProperty("trinity_home"));
    	if (env.equals("")){
    		if(System.getProperty("os.name").startsWith("Windows")){
        		path = "c:\\ntis2_kistep_itrinity";
         	}else{
        		path = "/FILE/itrinity";
        	}	
    	}else{
    		path = env;
    	}
    	
    	return path;
    }
    public static boolean getTrinitySySinfoWindows(){
	    boolean SYSinfoWindows ;
	    //파일카피하는 시스템이 윈도우인가 체크한다,
	    if(System.getProperty("os.name").startsWith("Windows")){
	    	SYSinfoWindows =true ;
	 
	    }else{
	    	SYSinfoWindows =false ;
	 
	    }	
	    return SYSinfoWindows;
    }
    
    
    public static String getTrinityConfRoot() {
    	String path = "";
    	if(System.getProperty("os.name").startsWith("Windows")){
    		//System.out.println(" os.name " + System.getProperty("os.name").startsWith("Windows")); 
    	   path = MailSmsConfig.getTrinityRoot() + File.separator + "conf" + File.separator ;
    	}else{
    	   path = MailSmsConfig.getTrinityRoot() + File.separator + "conf2" + File.separator ;
    	}
    	
    	
    	return path;
    	
	}

    public static String getTrinityInxDBRoot() {
    	String path = MailSmsConfig.getTrinityRoot() + File.separator + "inxDB" + File.separator ;
    	return path;
	}
    
    public static String getTrinityLogsRoot() {
    	String path = MailSmsConfig.getTrinityRoot() + File.separator + "logs" + File.separator ;
    	return path;
	}    
    
    public static String getTrinityTmpRoot() {
    	//개발서버   /DEV/ntis/ntisdev/file/
    	String path = "";
     	if(System.getProperty("os.name").startsWith("Windows")){
    	  // path = MailSmsConfig.getTrinityRoot() + File.separator + "tmp" + File.separator ;
     		//path = "C:\\upload" + File.separator ;
     		path = "C:\\";
    	}else{
    		//path = "/DEV/ntis/ntisdev/file" + File.separator ;
    		path = "/FILE" + File.separator ;
    	   // path = MailSmsConfig.getTrinityRoot() + File.separator + "tmp" + File.separator ;
    	}
     	return path;
	}
    public static String getTrinityTxtRoot() {//추가된 첨부파일
    	String path = MailSmsConfig.getTrinityRoot() + File.separator + "txtDB" + File.separator ;
    	return path;
	}
    public static String getTrinityDicRoot() {
    	String path = MailSmsConfig.getTrinityRoot() + File.separator + "dic" + File.separator ;
    	return path;
	}
    
    public static String getTrinityBinRoot() {
    	String path = MailSmsConfig.getTrinityRoot() + File.separator + "bin" + File.separator ;
    	return path;
	}    
    
    public static void main(String args[]){
    	System.err.println(MailSmsConfig.getTrinityRoot());
    	File file = new File(MailSmsConfig.getTrinityRoot());
    	System.out.println(Boolean.toString(file.exists()));
    	
    }
    
}


