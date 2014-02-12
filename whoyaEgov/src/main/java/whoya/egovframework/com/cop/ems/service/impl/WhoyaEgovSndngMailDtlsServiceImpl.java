package whoya.egovframework.com.cop.ems.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.egovframework.com.cop.ems.service.WhoyaEgovSndngMailDtlsService;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cop.ems.service.EgovSndngMailDtlsService;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;

/**
 * 발송메일 내역을 조회하는 비즈니스 구현 클래스
 */
@Service("WhoyaEgovSndngMailDtlsService")
public class WhoyaEgovSndngMailDtlsServiceImpl extends AbstractServiceImpl implements WhoyaEgovSndngMailDtlsService {
    
	/** EgovSndngMailDtlsService */
	@Resource(name = "sndngMailDtlsService")
    private EgovSndngMailDtlsService sndngMailDtlsService;
	
	/**
	 * 발송메일 목록을 조회한다.
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */
    public List selectSndngMailList(ComDefaultVO vo) throws Exception {
        return sndngMailDtlsService.selectSndngMailList(vo);
	}

//    @Resource(name="sndngMailDtlsDAO")
//    private SndngMailDtlsDAO sndngMailDtlsDAO;
//    
//	@Resource(name = "sndngMailDetailService")
//    private EgovSndngMailDetailService sndngMailDetailService;
//    
//    /**
//	 * 발송메일 총건수를 조회한다.
//	 * @param vo ComDefaultVO
//	 * @return int
//	 * @exception
//	 */
//    public int selectSndngMailListTotCnt(ComDefaultVO vo) throws Exception {
//        return sndngMailDtlsDAO.selectSndngMailListTotCnt(vo);
//	}
//    
//    /**
//	 * 발송메일을 삭제한다.
//	 * @param vo SndngMailVO
//	 * @exception
//	 */
//    public void deleteSndngMailList(SndngMailVO vo) throws Exception {
//        
//    	// 1. 발송메일을 삭제한다.
//    	String [] sbuf = EgovStringUtil.split(vo.getMssageId(), ",");
//    	for (int i = 0; i < sbuf.length; i++) {
//    		SndngMailVO sndngMailVO = new SndngMailVO();
//    		sndngMailVO.setMssageId(sbuf[i]);
//    		sndngMailDetailService.deleteSndngMail(sndngMailVO);
//    	}
//    	
//    	// 2. 첨부파일을 삭제한다.
//    	String [] fbuf = EgovStringUtil.split(vo.getAtchFileIdList(), ",");
//    	for (int i = 0; i < fbuf.length; i++) {
//    		SndngMailVO sndngMailVO = new SndngMailVO();
//    		sndngMailVO.setAtchFileId(fbuf[i]);
//    		sndngMailDetailService.deleteAtchmnFile(sndngMailVO);
//    	}
//	}
}
