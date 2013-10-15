package com.hanjin.cmm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Property {

    private static Properties prop = null;
    
    /**
     * 프로퍼티 파일 셋팅.
    */
    public Properties getInstance() throws Exception {
        if ( prop == null ) {
            prop = new Properties();
            String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            prop.load(new BufferedReader(new InputStreamReader(new FileInputStream(path.substring(0, path.lastIndexOf("/")) + "/../../../DbInfo.properties"))));
        }
        
        return prop;
    }
}