package whoya.egovframework.com.cop.ems.service;

import egovframework.com.cop.ems.service.SndngMailVO;


/**
 * 발송메일을 상세 조회하는 비즈니스 인터페이스 클래스
 */
public interface WhoyaEgovSndngMailDetailService {
	
	/**
	 * 발송메일을 상세 조회한다.
	 * @param vo SndngMailVO
	 * @return SndngMailVO
	 * @exception Exception
	 */
	SndngMailVO selectSndngMail(SndngMailVO vo) throws Exception;
	
	/**
	 * 발송메일을 삭제한다.
	 * @param vo SndngMailVO
	 * @exception
	 */
	void deleteSndngMail(SndngMailVO vo) throws Exception;
	
	/**
	 * 첨부파일을 삭제한다.
	 * @param vo SndngMailVO
	 * @exception
	 */
	void deleteAtchmnFile(SndngMailVO vo) throws Exception;
}
