package egovframework.com.ext.jfile.sample.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import egovframework.com.ext.jfile.sample.service.SampleService;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;

/**
 *  클래스
 * @author 정호열
 * @since 2010.10.17
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일        수정자       수정내용
 *  -------       --------    ---------------------------
 *   2010.10.17   정호열       최초 생성
 *   2013.12.19	표준프레임워크	공통컴포넌트 추가 적용 (패키지 변경)
 * </pre>
 */
@Service
public class SampleServiceImpl extends AbstractServiceImpl implements SampleService {

	@Autowired
	private SampleDAO dao;
	
	public void testConnection() {
		dao.testConnection();
	}
}