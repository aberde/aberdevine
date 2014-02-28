package whoya.egovframework.com.cop.smt.wmr.service.impl;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.egovframework.com.cop.smt.wmr.service.WhoyaEgovWikMnthngReprtService;
import egovframework.com.cop.smt.wmr.service.EgovWikMnthngReprtService;
import egovframework.com.cop.smt.wmr.service.ReportrVO;
import egovframework.com.cop.smt.wmr.service.WikMnthngReprt;
import egovframework.com.cop.smt.wmr.service.WikMnthngReprtVO;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;

/**
 * 개요
 * 주간월간보고에 대한 ServiceImpl 클래스를 정의한다.
 * 
 * 상세내용
 * - 주간월간보고에 대한 등록, 수정, 삭제, 조회, 승인기능을 제공한다.
 * - 주간월간보고의 조회기능은 목록조회, 상세조회로 구분된다.
 */
@Service("WhoyaEgovWikMnthngReprtService")
public class WhoyaEgovWikMnthngReprtServiceImpl extends AbstractServiceImpl implements WhoyaEgovWikMnthngReprtService {
	
	@Resource(name="EgovWikMnthngReprtService")
	protected EgovWikMnthngReprtService wikMnthngReprtService;
	
	/**
	 * 주간월간보고 목록을 조회한다.
	 * @param WikMnthngReprtVO - 주간월간보고 VO
	 * @return  List<WikMnthngReprtVO> - 주간월간보고 List
	 * 
	 * @param wikMnthngReprtVO
	 */
	public Map<String, Object> selectWikMnthngReprtList(WikMnthngReprtVO wikMnthngReprtVO) throws Exception{
		return wikMnthngReprtService.selectWikMnthngReprtList(wikMnthngReprtVO);
	}
	
	/**
	 * 보고자 목록을 조회한다.
	 * @param ReportrVO
	 * @return  Map<String, Object>
	 * 
	 * @param reportrVO
	 */
	public Map<String, Object> selectReportrList(ReportrVO reportrVO) throws Exception{
		return wikMnthngReprtService.selectReportrList(reportrVO);
	}
	
	/**
	 * 사용자 직위명 정보를 조회한다.
	 * @param String
	 * @return  String
	 * 
	 * @param String
	 */
	public String selectWrterClsfNm(String wrterId) throws Exception{
		return wikMnthngReprtService.selectWrterClsfNm(wrterId);
	}
	
	/**
	 * 주간월간보고 정보를 등록한다.
	 * @param WikMnthngReprt - 주간월간보고 model
	 * 
	 * @param wikMnthngReprt
	 */
	public void insertWikMnthngReprt(WikMnthngReprt wikMnthngReprt) throws Exception{
		wikMnthngReprtService.insertWikMnthngReprt(wikMnthngReprt);
	}

	/**
	 * 주간월간보고 정보를 조회한다.
	 * @param WikMnthngReprtVO - 주간월간보고 VO
	 * @return  WikMnthngReprtVO - 주간월간보고 VO
	 * 
	 * @param wikMnthngReprtVO
	 */
	public WikMnthngReprtVO selectWikMnthngReprt(WikMnthngReprtVO wikMnthngReprtVO) throws Exception{
		return wikMnthngReprtService.selectWikMnthngReprt(wikMnthngReprtVO);
	}
	
	/**
	 * 주간월간보고 정보를 삭제한다.
	 * @param WikMnthngReprt - 주간월간보고 model
	 * 
	 * @param wikMnthngReprt
	 */
	public void deleteWikMnthngReprt(WikMnthngReprt wikMnthngReprt) throws Exception{
		wikMnthngReprtService.deleteWikMnthngReprt(wikMnthngReprt);
	}
	
	/**
	 * 주간월간보고 정보를 수정한다.
	 * @param WikMnthngReprt - 주간월간보고 model
	 * 
	 * @param wikMnthngReprt
	 */
	public void updateWikMnthngReprt(WikMnthngReprt wikMnthngReprt) throws Exception{
		wikMnthngReprtService.updateWikMnthngReprt(wikMnthngReprt);
	}
//	@Resource(name = "WikMnthngReprtDAO")
//    private WikMnthngReprtDAO wikMnthngReprtDAO;
//	
//	@Resource(name="egovWikMnthngReprtIdGnrService")
//	private EgovIdGnrService idgenServiceWikMnthngReprt;
//
//	/**
//	 * 주간월간보고 정보를 승인한다.
//	 * @param WikMnthngReprt - 주간월간보고 model
//	 * 
//	 * @param wikMnthngReprt
//	 */
//	public void confirmWikMnthngReprt(WikMnthngReprt wikMnthngReprt) throws Exception{
//		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss", java.util.Locale.KOREA);
//		wikMnthngReprt.setConfmDt(formatter.format(new java.util.Date()));
//		wikMnthngReprtDAO.confirmWikMnthngReprt(wikMnthngReprt);
//	}
}