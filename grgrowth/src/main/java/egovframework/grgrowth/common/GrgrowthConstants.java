package egovframework.grgrowth.common;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 공통 상수 <br/>
 */
public class GrgrowthConstants {

	// ###########################################################
	// ## 기타 상수
	// ###########################################################
	public static final String YES = "Y";

	public static final String NO = "N";

	// 기상청 지역날씨 URL
	public static final String WEATHER_URL = "http://www.kma.go.kr/XML/weather/sfc_web_map.xml";
	
	// 검색구분(1: 위원회활동, 2: 주요소식, 3: 회의자료(2013.03~), 4: 회의자료(~2013.02), 5: 용어사전)
	public static final Map<String, String> SEARCH_SECTION;
	static {
        Map<String, String> dataMap = new LinkedHashMap<String, String>();
        dataMap.put("1", "위원회활동");
        dataMap.put("2", "주요소식");
        dataMap.put("3", "회의자료(2013.03~)");
        dataMap.put("4", "회의자료(~2013.02)");
        dataMap.put("5", "용어사전");
        SEARCH_SECTION = Collections.unmodifiableMap(dataMap);
    }
	
	// 검색화면 글 목록 수
	public static final int SEARCH_PAGE_UNIT = 20;
	
	// 세션 사용자 키
	public static final String SESSION_USER_ID = "SESSION_USER_ID";
	// ###########################################################
	
	// ###########################################################
	// ## 이미지 태그 추출 상수
	// ###########################################################
	public static final String GET_IMAGE_TAG_CONTENT = "content";

	public static final String GET_IMAGE_TAG_TAGYN = "tagyn";
	// ###########################################################
	
	// ###########################################################
	// ## 관리자 로그인 정보
	// ###########################################################
	// 관리자 아이디
	public static final String ADMIN_USER_ID = "admin";

	// 관리자 비밀번호
	public static final String ADMIN_USER_PW = "1234";
	
	// 관리자 접근 가능 MAC ADDRESS
    public static final Map<String, String> ADMIN_ACCESS_MAC_ADDRESS;
    static {
        Map<String, String> dataMap = new LinkedHashMap<String, String>();
        dataMap.put("10-78-D2-94-00-F5", "");
        dataMap.put("10-78-D2-94-00-86", "");
        dataMap.put("D0-50-99-70-BA-A7", "");  // 테스트용
        ADMIN_ACCESS_MAC_ADDRESS = Collections.unmodifiableMap(dataMap);
    }
	// ###########################################################
	
	// ###########################################################
	// ## 에러메세지
	// ###########################################################
	// 관리자 로그인 정보 미일치
	public static final String ERR_LOGIN_FALSE = "관리자 로그인정보가 일치하지 않습니다.";
	// 관리자 접근 불가능 MAC ADDRESS
	public static final String ERR_ADMIN_ACCESS_FALSE = "접근 불가능한 장치입니다.";
	// ###########################################################
}
