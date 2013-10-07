package common;

import whoya.whoyaMap;

import java.util.Map;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import common.HttpServletRequestHolder;

/* 페이징 처리를 위한 유틸리티 클래스 */
public class PagingUtil {
	
	public static final String PAGE_MAP = "pageMap";
	public static final String START = "start";
	public static final String END = "end";
	public static final String PAGE = "page";
	public static final String ROWS_PER_PAGE = "rowsPerPage";
	public static final int DEFAULT_PAGE = 1;
	public static final int DEFAULT_ROWS_PER_PAGE = 10;
	public static final int DEFAULT_PAGE_SIZE = 10;
	
	/**
	 * 페이징 처리를 수행하기 위한 값을 가진 맵을 반환한다.<br/>
	 * url, page, rowsPerPage 값은 request객체로부터 가져온다.
	 * @param totalRow 총 ROW 수
	 * @param pageSize 화면당 페이지 수
	 * @param optionParam 추가 옵션(검색이나 기타 옵션이 추가 될경우 사용). Map 이나 URL Query String 형태의 String.
	 * @return
	 */
	public static whoyaMap pageSetting(int totalRow, int pageSize, Object optionParam) {
		
		String url = HttpServletRequestHolder.getRequestURI();
		String _page = 			HttpServletRequestHolder.get().getParameter(PAGE);
		String _rowsPerPage = 	HttpServletRequestHolder.get().getParameter(ROWS_PER_PAGE);
		
		Integer page = _page == null ? null : Integer.valueOf(_page);
		Integer rowsPerPage = _rowsPerPage == null ? null : Integer.valueOf(_rowsPerPage);
		
		return pageSetting(url, page, totalRow, rowsPerPage, pageSize, optionParam);
	}
	
	/**
	 * 페이징 처리를 수행하기 위한 값을 가진 맵을 반환한다.
	 * @param url 대상 URL
	 * @param page 현재 페이지
	 * @param totalRow 총 ROW 수
	 * @param rowsPerPage 페이지당 ROW 수
	 * @param pageSize 화면당 페이지 수
	 * @param optionParam 추가 옵션(검색이나 기타 옵션이 추가 될경우 사용). Map 이나 URL Query String 형태의 String.
	 * @return
	 */
	public static whoyaMap pageSetting(String url, Integer page, Integer totalRow, Integer rowsPerPage, Integer pageSize, Object optionParam) {
		
		whoyaMap pageMap = new whoyaMap();

		if (page == null || page == 0) 					page = DEFAULT_PAGE;
		if (rowsPerPage == null || rowsPerPage == 0) 	rowsPerPage = DEFAULT_ROWS_PER_PAGE;
		if (pageSize == null || pageSize == 0)			pageSize = DEFAULT_PAGE_SIZE;
		if (totalRow == null) totalRow = 0;
		
		int totalPage = 	Math.max((int)Math.ceil((double)totalRow / (double)rowsPerPage), 1);
		int totalBlock = 	(int)Math.ceil((double)totalPage / (double)pageSize);
		int block = 		(int)Math.ceil((double)page / (double)pageSize);
		
	    int startPage = 	((block - 1) * pageSize) + 1;
	    int endPage = 		block * pageSize > totalPage ? totalPage : block * pageSize;
	    
	    int nextBlockPage = totalBlock == block ? totalPage : (block * pageSize) + 1;
	    int preBlockPage = block == 1 ? block : (block - 1) * pageSize;
		
		pageMap.put("url", 			url);
		pageMap.put("totalRow", 	totalRow);
		pageMap.put("page", 		page);
		pageMap.put("rowsPerpage", 	rowsPerPage);
		pageMap.put("pageSize", 	pageSize);
		pageMap.put("totalPage", 	totalPage);
		pageMap.put("totalBlock", 	totalBlock);
		pageMap.put("block", 		block);
		pageMap.put("startPage", 	startPage);
		pageMap.put("endPage", 		endPage);
		pageMap.put("nextBlockPage",nextBlockPage);
		pageMap.put("preBlockPage",	preBlockPage);
		
		pageMap.put("pagingURL", 	buildURL(url, null, rowsPerPage, optionParam));
		pageMap.put("currentURL", 	buildURL(url, page, rowsPerPage, optionParam));
		pageMap.put("firstPageURL", page == 1 ? null : buildURL(url, 1, rowsPerPage, optionParam));
		pageMap.put("lastPageURL", 	page == totalPage ? null : buildURL(url, totalPage, rowsPerPage, optionParam));
		pageMap.put("preBlockURL", 	block == 1 ? null : buildURL(url, preBlockPage, rowsPerPage, optionParam));
		pageMap.put("nextBlockURL", block == totalBlock ? null : buildURL(url, nextBlockPage, rowsPerPage, optionParam));
	
		return pageMap;
	}
	
	@SuppressWarnings("unchecked")
	public static String buildURL(String url, Integer page, Integer rowsPerPage, Object optionParam) {
		
		StringBuilder osb = new StringBuilder();
		if (optionParam != null) {
			if (optionParam instanceof Map) {
				for (Map.Entry<String, Object> v : ((Map<String, Object>)optionParam).entrySet()) {
					osb.append("&")
						.append(v.getKey())
						.append("=")
						.append(v.getValue().toString());
				}
			} else if (optionParam instanceof String) {
				if (optionParam.toString().startsWith("&")) {
					osb.append(optionParam);
				} else {
					osb.append("&")
						.append(optionParam);
				}
			}
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(url)
			.append("?")
			.append(PAGE)
			.append("=")
			.append(page != null ? page : "_page_")
			.append("&")
			.append(ROWS_PER_PAGE)
			.append("=")
			.append(rowsPerPage)
			.append(osb);
		
		return sb.toString();
	}
}
