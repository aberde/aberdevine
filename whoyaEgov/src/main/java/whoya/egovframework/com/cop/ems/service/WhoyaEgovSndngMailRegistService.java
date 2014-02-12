package whoya.egovframework.com.cop.ems.service;

import egovframework.com.cop.ems.service.SndngMailVO;

/**
 * 발송메일등록, 발송요청XML파일 생성하는 비즈니스 인터페이스 클래스
 */
public interface WhoyaEgovSndngMailRegistService {
	
	/**
	 * 발송할 메일을 등록한다
	 * @param vo SndngMailVO
	 * @return boolean
	 * @exception Exception
	 */
	boolean insertSndngMail(SndngMailVO vo) throws Exception;
//	
//	/**
//	 * 발송할 메일을 XML파일로 만들어 저장한다.
//	 * @param vo SndngMailVO
//	 * @return boolean
//	 * @exception Exception
//	 */
//	public boolean trnsmitXmlData(SndngMailVO vo) throws Exception;
//	
//	/**
//	 * 발송메일 발송결과 XML파일을 읽어 발송결과코드에 수정한다.
//	 * @param xml String
//	 * @return boolean
//	 * @exception Exception
//	 */
//	public boolean recptnXmlData(String xml) throws Exception;
}
