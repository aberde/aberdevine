package whoya.egovframework.com.cop.ems.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.egovframework.com.cop.ems.service.WhoyaEgovSndngMailDetailService;
import egovframework.com.cop.ems.service.EgovSndngMailDetailService;
import egovframework.com.cop.ems.service.SndngMailVO;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;

/**
 * 발송메일을 상세 조회하는 비즈니스 구현 클래스
 */
@Service("WhoyaEgovSndngMailDetailService")
public class WhoyaEgovSndngMailDetailServiceImpl extends AbstractServiceImpl implements WhoyaEgovSndngMailDetailService {
    
	/** EgovSndngMailDetailService */
	@Resource(name = "sndngMailDetailService")
    private EgovSndngMailDetailService sndngMailDetailService;
	
    /**
	 * 발송메일을 상세 조회한다.
	 * @param vo SndngMailVO
	 * @return SndngMailVO
	 * @exception Exception
	 */
    public SndngMailVO selectSndngMail(SndngMailVO vo) throws Exception {
    	return sndngMailDetailService.selectSndngMail(vo);
	}
  
    /**
	 * 발송메일을 삭제한다.
	 * @param vo SndngMailVO
	 * @exception
	 */
    public void deleteSndngMail(SndngMailVO vo) throws Exception {
    	sndngMailDetailService.deleteSndngMail(vo);
	}
    
    /**
	 * 첨부파일을 삭제한다.
	 * @param vo SndngMailVO
	 * @exception
	 */
    public void deleteAtchmnFile(SndngMailVO vo) throws Exception {
    	sndngMailDetailService.deleteAtchmnFile(vo);
    }
}
