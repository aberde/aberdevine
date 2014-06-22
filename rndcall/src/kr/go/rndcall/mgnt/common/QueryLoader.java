package kr.go.rndcall.mgnt.common;

import java.io.*;
import java.util.*; 

import kr.go.rndcall.mgnt.common.Configuration;

public class QueryLoader {
    
    /** 모든 query 스크립트를 담고 있는 Hashtable */ 
    private Hashtable qTable = new Hashtable();

    /**  QueryLoader의 Singleton Instance */
    private static QueryLoader qLoader = null;
    
    /** private 생성자 */
    private QueryLoader() {
        init();
    }

    /**
     * QueryLoader의 Singleton Instance를 얻는다.
     *@return    QueryLoader의 Singleton Instance
     */
    public static QueryLoader getInstance() {
        if (qLoader == null) {
            synchronized (QueryLoader.class) {
                if (qLoader == null)
                    qLoader = new QueryLoader();
            }
        }
        return qLoader;
    }

    /**
     * 초기화 작업을 수행한다.
     */
    private void init() {
        qTable.clear();
        try {
            loadSqlFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
    
    /**
     * SQL propertie 파일을 로딩하는 메쏘드
     * @exception IOException 프로퍼티파일을 찾지 못하는 경우 발생한다.
     */
    private synchronized void loadSqlFiles() throws Exception {
             
        File configDir = new File(Configuration.getInstance().get("conf.query.folder"));
        if (!configDir.exists()) {
            System.out.println("file not exist");
        } 
                
        File[] configFiles = configDir.listFiles(); 
        for (int i = 0; i < configFiles.length; i++) {
            if (configFiles[i].isFile() && configFiles[i].getName().endsWith(Configuration.getInstance().get("conf.query.fileext"))) {  
                ConfigParse(configFiles[i]);
            } 
        } 
    }
    

    /**
     * script file을 읽어서 parsing하고 Properties에 저장한다.
     * @param pm_sScriptFile script file의 절대 경로
     * @throws IOException script file을 loading할 때 발생
     */
    private void ConfigParse(File configFiles) throws Exception {
        BufferedReader oReader = new BufferedReader(new InputStreamReader(
                new FileInputStream(configFiles)));
        boolean flag = false ;
        String line = null;
        String qkey = null;
        StringBuffer qvalue = new StringBuffer();

        while ((line = oReader.readLine()) != null) {

            if(line.startsWith("@")) {//query name 
                qkey = line.substring(1).trim(); 
                qvalue = new StringBuffer();
                flag = true ;    //query parse
            }
            else if(line.startsWith("#")) { 
                if(flag){ 
                    qTable.put(qkey, qvalue.toString());
                    flag = false ;
                }
            }
            else{    //query
                qvalue.append(line+"\n");
            }
        } 

        if(flag && !qvalue.toString().equals("")){ 
            qTable.put(qkey, qvalue.toString());
        }
    }
    

    
    /**
     * 사용할 SQL query를 프로퍼티파일로부터 얻는다.
     * @param querykey 사용할 SQL query에 해당하는 프로퍼티 키값
     * @return String SQL query stirng
     */
    public String getQueryString(String sKey) {
        String query =  (String) qTable.get(sKey);
        if(query==null)
            System.out.println(sKey + " query not fined");
        return (String) qTable.get(sKey);
    }

    /**
     * 사용할 SQL query를 프로퍼티파일로부터 얻는다.
     * @param querykey 사용할 SQL query에 해당하는 프로퍼티 키값
     * @return String SQL query stirng
     */
    public String getQueryString(String querykey, Hashtable hash) {
        int index = -1;
        int keylen = 0;
        String stringkey = null; 
        String value = null;
        String query = getQueryString(querykey);
        StringBuffer buffer = new StringBuffer(query);
        Hashtable param = hash;
        Enumeration enum1 = hash.keys();
        
        while (enum1.hasMoreElements()) {
            stringkey = (String) enum1.nextElement();
            value = (String) param.get(stringkey);
            keylen = stringkey.length();
            while ((index = query.indexOf(stringkey)) != -1) {
                buffer = buffer.replace(index, index + keylen, value);
                query = buffer.toString();
            }
        } 
        return query;
    }


    /**
     * SQL Properties 파일에 대한 리로드 처리를 수행한다.
     *@throws    Exception    SQL Properties 파일이 발견되지 않거나 읽기 작업중 IOException이 발생한 경우
     */
    public void Reload()  throws Exception {
        //1. 쿼리목록에 대한 초기화 처리를 수행한다.
        qTable.clear();

        //2. Properties 에 대한 읽기 작업 수행
        try {
            loadSqlFiles();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
}
