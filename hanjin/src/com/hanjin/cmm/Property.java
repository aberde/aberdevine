package com.hanjin.cmm;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Property {

    private static Properties prop = null;
    
    /**
     * ������Ƽ ���� ����.
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