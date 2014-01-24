package whoya.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class commonEncrypt {

	public static String encryptMD5(String data){
		String strEncData = "";
		
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			
			byte[] byteData = data.getBytes();
			
			md.update(byteData);
			
			byte[] digest = md.digest();
			
			for(int i=0; i < digest.length; i++){
				strEncData = strEncData + Integer.toHexString(digest[i] & 0xFF).toUpperCase();
			}
			
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}
		
		return strEncData;
	}
}

