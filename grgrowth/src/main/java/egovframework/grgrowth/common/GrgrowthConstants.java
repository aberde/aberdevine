package egovframework.grgrowth.common;

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
	public static final String[] SEARCH_SECTION = {"1", "2", "3", "4", "5"};

	// 검색화면 글 목록 수
	public static final int SEARCH_PAGE_UNIT = 20;
	// ###########################################################
	
	// ###########################################################
	// ## 이미지 태그 추출 상수
	// ###########################################################
	public static final String GET_IMAGE_TAG_CONTENT = "content";

	public static final String GET_IMAGE_TAG_TAGYN = "tagyn";
	// ###########################################################
}
