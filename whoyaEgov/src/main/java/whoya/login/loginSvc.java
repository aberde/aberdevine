package whoya.login;

import whoya.whoyaList;
import whoya.whoyaMap; 

public interface loginSvc {

	public whoyaList menu(whoyaMap params) throws Exception;
	public whoyaMap login(whoyaMap params) throws Exception;
	public whoyaMap logout(whoyaMap params) throws Exception;
}
