package whoya.egovframework.com.uat.uap.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import whoya.whoyaDataProcess;
import whoya.whoyaMap;
import whoya.common.Common;
import whoya.egovframework.com.uat.uap.service.WhoyaEgovLoginPolicyService;
import egovframework.com.uat.uap.service.EgovLoginPolicyService;
import egovframework.com.uat.uap.service.LoginPolicy;
import egovframework.com.uat.uap.service.LoginPolicyVO;


@Service("whoyaEgovLoginPolicyService")
public class WhoyaEgovLoginPolicyServiceImpl implements WhoyaEgovLoginPolicyService {

	@Resource(name="egovLoginPolicyService")
	EgovLoginPolicyService egovLoginPolicyService;
	
	/**
	 * 로그인정책관리 목록을 조회한다.
	 * @param loginPolicyVO - 로그인정책관리 VO
	 * @return List - 로그인정책관리 목록
	 */
	public List<LoginPolicyVO> selectLoginPolicyList(LoginPolicyVO loginPolicyVO) throws Exception {
		return egovLoginPolicyService.selectLoginPolicyList(loginPolicyVO);
	}
	
	/**
	 * 로그인정책관리 등록/수정/삭제한다.
	 * @param request
	 * @param response
	 */
	public void saveLoginPolicy(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String[] ids = request.getParameter("ids").split(",");
		
	    whoyaDataProcess  data = new whoyaDataProcess();
	    whoyaMap rows = new whoyaMap();
	    rows = data.dataProcess(request);
	    
		for (int i = 0; i < ids.length; i++) {
			whoyaMap cols = (whoyaMap) rows.get(ids[i]);
		    LoginPolicyVO loginPolicyVO = new LoginPolicyVO();
		    loginPolicyVO = (LoginPolicyVO)Common.convertWhoyaMapToObject(cols, loginPolicyVO);
		    // 중복허용여부 기본값으로 설정.
		    loginPolicyVO.setDplctPermAt("Y");
		    // 로그인정책관리에는 등록이 없음.
//		    if (cols.get("!nativeeditor_status").equals("inserted") ) { egovLoginPolicyService.insertLoginPolicy(loginPolicy); }
		    if (cols.get("!nativeeditor_status").equals("updated" ) ) {
		    	// 로그인정책관리가 등록되었는지 확인.
		    	LoginPolicyVO loginPolicyVO2 = egovLoginPolicyService.selectLoginPolicy(loginPolicyVO);
		    	if ( loginPolicyVO2.getRegYn().equals("N") ) {  // 로그인정책이 등록되어있지 않다면 등록 
		    		egovLoginPolicyService.insertLoginPolicy((LoginPolicy)loginPolicyVO);
		    	} else {  // 로그인정책관리가 등록되어 있다면 수정
		    		egovLoginPolicyService.updateLoginPolicy((LoginPolicy)loginPolicyVO);
		    	}
		    }
		    if (cols.get("!nativeeditor_status").equals("deleted" ) ) {
		    	egovLoginPolicyService.deleteLoginPolicy((LoginPolicy)loginPolicyVO);
		    }
		}
	}
}
