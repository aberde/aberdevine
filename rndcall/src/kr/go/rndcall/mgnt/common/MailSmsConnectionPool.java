package kr.go.rndcall.mgnt.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.log4j.Logger;


public class MailSmsConnectionPool {
	
	static Logger logger = Logger.getLogger(MailSmsConnectionPool.class);
	
	public static Connection getConnection() throws Exception {
		return getConnection("");
	}

	public static Connection getConnection(String name) throws Exception {
		
		Connection conn = null;

		String driver = "";
		String url = "";
		String id = "";
		String password = "";

		if (name.equals("")) {
			name = "default";
		}

		Properties properties = new Properties();
		try {
			String path = MailSmsConfig.getTrinityConfRoot() + "db.properties";
			properties.load(new FileInputStream(path));

			driver = properties.getProperty(name + ".driver");
			url = properties.getProperty(name + ".url");
			id = properties.getProperty(name + ".id");
			password = properties.getProperty(name + ".password");
			
//			logger.info("-----------------------------------------");
//			logger.info("class : ConnectionPool.ConnectionPool()");
//			logger.info("-----------------------------------------");
			
			 			
			logger.info("path : " + path);
     		logger.info("url : " + url);
 			logger.info("id : " + id);
 			System.out.println("path : " + path);
 			System.out.println("url : " + url);
//			logger.info("password : " + password);
//			logger.info("driver : " + driver);				
//			logger.info("-----------------------------------------");
 			//logger.info("properties.config.file : "+ System.getProperty("properties.config.file"));
 			// properties.config.file ===> C:\ntis2_kistep\ntis\web\WEB-INF\properties\conf.properties
 			
 			
 			/*
 			 java.util.Enumeration e = System.getProperties().propertyNames(); 
         	while(e.hasMoreElements()){ 
 			String obj = (String)e.nextElement(); 

 			System.out.print(obj + " ===> "); 
 			System.out.println(System.getProperty(obj)); 

 			}
 			*/
 			
		} catch (IOException e) {
			throw new Exception("db.properties 파일 로드 실패"+e.toString());
		}

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, password);
		} catch (Exception e) {
			throw new Exception(name + " Connection 객체 실패 "+e.toString());
		}

		return conn;
	}
}
