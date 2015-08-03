package egovframework.grgrowth.common;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class Util {

    /**
     * 내용중 이미지 태그 내용만 추출하기.
     * @param content : 내용
     * @param tagYN : 이미지태그 출력 여부(Y: 출력, N: 미출력)
     * @return
     * @throws Exception
     */
    public static List<String> getImageTag(String content, String tagYN) throws Exception {
        List<String> retList = new ArrayList<String>();
        
        // 이미지 태그를 추출하기 위한 정규식.
        Pattern pattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");
        
        // 내용중에서 이미지 태그를 찾기
        Matcher match = pattern.matcher(content);
        
        while ( match.find() ) {  // 이미지 태그를 찾았다면
            if ( GrgrowthConstants.YES.equals(tagYN) ) {  // 이미지태그 포함일 경우 
                retList.add(match.group()); // 검색된 글 전체
            } else {  // 이미지태그 미포함일 경우
                retList.add(match.group(1)); // 검색된 글에서 첫번째"()" 내용
            }
        }
        
        return retList;
    }
    
    /**
     * 모든 HTML 태그를 제거하고 반환한다.
     * @param html
     * @return
     * @throws Exception
     */
    public static String removeTag(String html) throws Exception {
        return html.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
    }
    
    /**
     * 문자열 Byte 잘라내기.  
     * @param str
     * @param byteLength
     * @return
     */
    public static String subStringBytes(String str, int byteLength) {
        // String 을 byte 길이 만큼 자르기.
        int length = str.length();
        int retLength = 0;
        int tempSize = 0;
        int asc;
        for ( int i = 1; i <= length; i++ ) {
            asc = (int)str.charAt(i - 1);
            if ( asc > 127 ) {
                if ( byteLength >= tempSize + 2 ) {
                    tempSize += 2;
                    retLength++;
                } else {
                    if ( length > retLength ) {
                        return str.substring(0, retLength) + "…";
                    } else {
                        return str.substring(0, retLength);
                    }
                }
            } else {
                if ( byteLength > tempSize ) {
                    tempSize++;
                    retLength++;
                }
            }
        }
        
        if ( length > retLength ) {
            return str.substring(0, retLength) + "…";
        } else {
            return str.substring(0, retLength);
        }
    }
    
    /**
     * 줄마다 <p> 태그 추가.
     * @param html
     * @return
     * @throws Exception
     */
    public static String rowInsertPTag(String html) throws Exception {
        return html.replaceAll("(.*)", "<p>$1</p>");
    }
    
    /**
     * 패턴에 해당하는 현재 날짜 가져오기.
     * @param pattern 날짜 패턴
     * @return
     */
    public static String getDatePattern(String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        
        return dateFormat.format(new Date());
    }
    
    /**
     * 세션에서 사용자 아이디 가져오기.
     * @param request
     * @return
     */
    public static String getSessionUser_id(HttpServletRequest request) {
        return (String)request.getSession().getAttribute(GrgrowthConstants.SESSION_USER_ID);
    }
    
    /**
     * MAC ADDRESS 가져오기
     * @param ip MAC ADDRESS 가져올 IP
     * @return
     */
    public static String getMACAddress(String ip) throws Exception {
        String macAddress = "";
        InetAddress address = InetAddress.getByName(ip);
        System.out.println("ip : " + ip);
        /*
         * Get NetworkInterface for the current host and then read the
         * hardware address.
         */
        NetworkInterface ni = NetworkInterface.getByInetAddress(address);
        if (ni != null) {
            byte[] mac = ni.getHardwareAddress();
            if (mac != null) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));        
                }
            }
        }
        System.out.println("macAddress : " + macAddress);
        
        return macAddress;
    }    
}
