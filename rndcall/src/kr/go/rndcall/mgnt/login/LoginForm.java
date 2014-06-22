package kr.go.rndcall.mgnt.login;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.upload.FormFile;

import kr.go.rndcall.mgnt.common.BaseForm;
import kr.go.rndcall.mgnt.login.LoginVO;

public class LoginForm extends BaseForm{
	
	private LoginVO vo = new LoginVO();

	public LoginVO getVo() {
		return vo;
	}

	public void setVo(LoginVO vo) {
		this.vo = vo;
	}
}
