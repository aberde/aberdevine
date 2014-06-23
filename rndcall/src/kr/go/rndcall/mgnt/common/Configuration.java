package kr.go.rndcall.mgnt.common;

import java.io.File;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *  Configuration 정보를 제공하는 클래스 
 */
public class Configuration extends Observable {	
    private static Configuration conf_ins;
    private Properties props = null;
    private String conf_file_name = null;
    private long last_modified = 0;

    /**
     * configuration파일 위치를 셋팅하고 refresh()를 호출한다.
     *
     * @throws Exception configuration error가 발생할 경우.
     */
    private Configuration() throws Exception {
    	conf_file_name = getConfFileLocation();   		
        refresh();
    }

    /**
     * getString()을 호출한다.
     *
     * @param string value의 key.
     * @return 해당 key의 String value.
     * @throws Exception configuration error가 발생할 경우.
     */
    public String get(String key) throws Exception {
        return getString(key);
    }

    /**
     * configuration파일에서 boolean Type의 value를 얻을 경우 사용한다.
     *
     * @param boolean value의 key.
     * @return 해당 key의 boolean value.
     * @throws Exception configuration error가 발생할 경우.
     */
    public boolean getBoolean(String key) throws Exception {
        try {
            return (new Boolean(props.getProperty(key))).booleanValue();
        } catch (Exception e) {
            throw new Exception(
                "Check the frame.conf File : Illegal Boolean Key : " + key, e);
        }
    }

    /**
     * 환경파일 위치 검색
     *
     * @return 셋팅된 파일위치.
     */
    public static String getConfFileLocation() {
    	
    	/*
    	if (System.getProperty("properties.config.file") == null || "".equals(System.getProperty("properties.config.file"))) {
	    	InitialContext context;	    	
			try {
				context = new InitialContext();
				String hostName = InetAddress.getLocalHost().getHostName().toString();
				
				
				if ("rndgate.ntis.go.kr".equalsIgnoreCase(InetAddress.getLocalHost().getCanonicalHostName())) {
					System.out.println("운영도메인 일 경우");
					return context.lookup("java:comp/env/properties.config.file." + hostName).toString() + "_op";
				} else {
					System.out.println("운영도메인이 아닐 경우");
					return context.lookup("java:comp/env/properties.config.file." + hostName).toString();
				}
			} catch (NamingException e) {
				System.out.println("NamingException!!!!!");
				return System.getProperty("user.home") + "/properties.conf";
			} catch (UnknownHostException e) {
				System.out.println("UnknownHostException!!!!");
				return System.getProperty("user.home") + "/properties.conf";
			}	
    	} else {
    		return System.getProperty("properties.config.file");
    	}
    	*/
    	
//    	return "C:/rndcall_kistep/rndcall/web/WEB-INF/properties/conf.properties";
    	return "/NCIA/WebApp/deploy/rndcall.war/WEB-INF/properties/conf.properties";
    }

    /**
     * instance가 null일 경우에만 Configuration를 new하여 return한다.
     *
    * @return Configuration의 instance.
     * @throws Exception configuration error가 발생할 경우.
    */
    public static synchronized Configuration getInstance()
        throws Exception {
        if (conf_ins == null) {
            conf_ins = new Configuration();
        }

        return conf_ins;
    }

    /**
     * configuration파일에서 int Type의 value를 얻을 경우 사용한다.
     *
     * @param int value의 key.
     * @return 해당 key의 boolean value.
     * @throws Exception configuration error가 발생할 경우.
     */
    public int getInt(String key) throws Exception {
        try {
            return Integer.parseInt(props.getProperty(key));
        } catch (Exception e) {
            throw new Exception(
                "Check the properties.conf File : Illegal Integer Key : " + key, e);
        }
    }

    /**
     * configuration파일에서 long Type의 value를 얻을 경우 사용한다.
     *
     * @param long value의 key.
     * @return 해당 key의 long value.
     * @throws Exception configuration error가 발생할 경우.
     */
    public long getLong(String key) throws Exception {
        try {
            return Long.parseLong(props.getProperty(key));
        } catch (Exception e) {
            throw new Exception(
                "Check the properties.conf File : Illegal Long Key : " + key, e);
        }
    }

    /**
     * configuration파일의 properties를 얻을 경우 사용한다.
     *
     * @return configuration파일의 properties.
     */
    public Properties getProperties() {
        return props;
    }

    /**
     * configuration파일에서 String Type의 value를 얻을 경우 사용한다.
     *
     * @param String value의 key.
     * @return 해당 key의 String value.
     * @throws Exception configuration error가 발생할 경우.
     */
    public String getString(String key) throws Exception {
        try {
            String tmp = props.getProperty(key);

            if (tmp == null) {
                throw new Exception("value of key(" + key + ") is null");
            }

            return tmp;
        } catch (Exception e) {
            throw new Exception(
                "Check the properties.conf File : Illegal String Key : " + key, e);
        }
    }

    /**
     * configuration파일의 변경여부를 체크하여 변경된 파일의 property를
     * load한다.
     *
     * @throws Exception configuration error가 발생할 경우.
     */
    public synchronized void refresh() throws Exception {
        File conf_file = new File(conf_file_name);

        if (!conf_file.canRead()) {
            throw new Exception(conf_file_name + " is not readable");
        }

        try {
            if ((last_modified != conf_file.lastModified()) || (props == null)) {
                last_modified = conf_file.lastModified();
                props = new Properties();

                FileInputStream laf_fin = new FileInputStream(conf_file);
                props.load(new java.io.BufferedInputStream(laf_fin));
                laf_fin.close();
                setChanged();
                notifyObservers();
                
            }
        } catch (Exception e) {
            throw new Exception("Can't load configuration file", e);
        }
    }
}
