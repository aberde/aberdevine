package login;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.whoyaList; 
import whoya.whoyaMap;
import common.commonDAO;

import login.loginSvc;

@Service("loginSvc")
public class loginSvcImpl implements loginSvc {

	@Resource(name="whoya")
	private commonDAO whoya;
	
	public whoyaList menu(whoyaMap params) throws Exception {		
		return whoya.list("login.menuR", params);
	}

	public whoyaMap login(whoyaMap params) throws Exception {
		return whoya.select("login.loginR", params);
	}

	public whoyaMap logout(whoyaMap params) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}	
}
