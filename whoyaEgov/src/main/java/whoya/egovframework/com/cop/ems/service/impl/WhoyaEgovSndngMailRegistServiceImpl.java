package whoya.egovframework.com.cop.ems.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.egovframework.com.cop.ems.service.WhoyaEgovSndngMailRegistService;
import egovframework.com.cop.ems.service.EgovSndngMailRegistService;
import egovframework.com.cop.ems.service.SndngMailVO;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;

/**
 * 발송메일등록, 발송요청XML파일 생성하는 비즈니스 구현 클래스
 */
@Service("whoyaEgovSndngMailRegistService")
public class WhoyaEgovSndngMailRegistServiceImpl extends AbstractServiceImpl implements WhoyaEgovSndngMailRegistService {
	
	/** EgovSndngMailRegistService */
	@Resource(name = "sndngMailRegistService")
    private EgovSndngMailRegistService sndngMailRegistService;
	
	/**
	 * 발송할 메일을 등록한다
	 * @param vo SndngMailVO
	 * @return boolean
	 * @exception Exception
	 */
	public boolean insertSndngMail(SndngMailVO vo) throws Exception {
		return sndngMailRegistService.insertSndngMail(vo);
	}

//	/** SndngMailRegistDAO */
//    @Resource(name="sndngMailRegistDAO")
//    private SndngMailRegistDAO sndngMailRegistDAO;
//    
//    /** Message ID Generation */
//    @Resource(name="egovMailMsgIdGnrService")    
//    private EgovIdGnrService egovMailMsgIdGnrService;
//    
//    @Resource(name = "egovSndngMailService")
//    private EgovSndngMailService egovSndngMailService;
//    
//    /**
//	 * 발송할 메일을 XML파일로 만들어 저장한다.
//	 * @param vo SndngMailVO
//	 * @return boolean
//	 * @exception Exception
//	 */
//    public boolean trnsmitXmlData(SndngMailVO vo) throws Exception {
//    	
//    	// 1. 첨부파일 목록 (원파일명, 저장파일명)
//    	String orignlFileList = "";
//    	String streFileList = "";
//    	List atchmnFileList = sndngMailRegistDAO.selectAtchmnFileList(vo);
//    	for (int i = 0; i < atchmnFileList.size(); i++) {
//			AtchmnFileVO fileVO = (AtchmnFileVO)atchmnFileList.get(i);
//			String orignlFile = fileVO.getOrignlFileNm();
//			String streFile = fileVO.getFileStreCours() + fileVO.getStreFileNm();
//			orignlFileList += orignlFile + ";";
//			streFileList += streFile + ";";
//		}    	
//    	
//    	// 2. XML데이터를 만든다.
//    	SndngMailDocument mailDoc;
//    	SndngMailDocument.SndngMail mailElement;
//    	mailDoc = SndngMailDocument.Factory.newInstance();
//    	mailElement = mailDoc.addNewSndngMail();
//    	mailElement.setMssageId(vo.getMssageId());
//    	mailElement.setDsptchPerson(vo.getDsptchPerson());
//    	mailElement.setRecptnPerson(vo.getRecptnPerson());
//    	mailElement.setSj(vo.getSj());
//    	mailElement.setEmailCn(vo.getEmailCn());
//    	mailElement.setSndngResultCode(vo.getSndngResultCode());
//    	mailElement.setOrignlFileList(orignlFileList);
//    	mailElement.setStreFileList(streFileList);
//
//    	// 2. XML파일로 저장한다.
//    	String xmlFile = Globals.MAIL_REQUEST_PATH + vo.getMssageId() + ".xml";
//        boolean result = EgovXMLDoc.getClassToXML(mailDoc, xmlFile);
//        if (result == true) {
//        	recptnXmlData(xmlFile);
//        }
//    	return result;
//    }
//
//    /**
//	 * 발송메일 발송결과 XML파일을 읽어 발송결과코드에 수정한다.
//	 * @param xml String
//	 * @return boolean
//	 * @exception Exception
//	 */
//    public boolean recptnXmlData(String xmlFile) throws Exception {
//    	
//    	// 1. XML파일에서 발송결과코드를 가져온다.
//    	SndngMailDocument mailDoc = EgovXMLDoc.getXMLToClass(xmlFile);
//    	SndngMailDocument.SndngMail mailElement = mailDoc.getSndngMail();
//    	SndngMailVO sndngMailVO = new SndngMailVO();
//    	sndngMailVO.setMssageId(mailElement.getMssageId());
//    	sndngMailVO.setSndngResultCode("C");	// 발송결과 완료
//    	
//    	// 2. DB에 업데이트 한다.
//    	sndngMailRegistDAO.updateSndngMail(sndngMailVO);
//    	
//    	return true;
//    }
}
