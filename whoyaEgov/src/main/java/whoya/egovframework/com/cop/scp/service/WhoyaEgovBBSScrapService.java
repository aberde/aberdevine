package whoya.egovframework.com.cop.scp.service;

import java.util.Map;

import egovframework.com.cop.bbs.service.ScrapVO;


/**
 * 스크랩관리를 위한 서비스 인터페이스 클래스
 */
public interface WhoyaEgovBBSScrapService {
  
	/**
	 * 스크랩에 대한 목록을 조회 한다.
	 * 
	 * @param scrapVO
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectScrapList(ScrapVO scrapVO) throws Exception;
//    /**
//     * 스크랩 사용 가능 여부를 확인한다.
//     * 
//     * @return
//     * @throws Exception
//     */
//    public boolean canUseScrap() throws Exception;
//    
//    /**
//     * 스크랩을 등록한다.
//     * 
//     * @param scrap
//     * @throws Exception
//     */
//    public void insertScrap(Scrap scrap) throws Exception;
//    
//    /**
//     * 스크랩을 삭제한다.
//     * 
//     * @param scrapVO
//     * @throws Exception
//     */
//    public void deleteScrap(ScrapVO scrapVO) throws Exception;
//    
//    /**
//     * 스크랩에 대한 내용을 조회한다.
//     *      
//     * @param scrapVO
//     * @return
//     * @throws Exception
//     */
//    public Scrap selectScrap(ScrapVO scrapVO) throws Exception;
//    
//    /**
//     * 스크랩에 대한 내용을 수정한다.
//     * 
//     * @param scrap
//     * @throws Exception
//     */
//    public void updateScrap(Scrap scrap) throws Exception;
}
