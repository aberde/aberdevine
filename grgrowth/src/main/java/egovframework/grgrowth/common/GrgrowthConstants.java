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
	
	// 검색구분(policy: 관련정책, news: 소식/동향, multimedia: 멀티미디어, edudata: 교육/자료실, page: 관련문서, participation: 참여마당)
	public static final String[] SEARCH_SECTION = {"policy", "news", "multimedia", "edudata", "page", "participation"};

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
