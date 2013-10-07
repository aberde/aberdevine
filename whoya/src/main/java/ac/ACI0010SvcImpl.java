package ac;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.whoyaList; 
import whoya.whoyaMap;
import common.commonDAO;

import ac.ACI0010Svc;

@Service("ACI0010Svc")
public class ACI0010SvcImpl implements ACI0010Svc {

	@Resource(name="whoya")
	private commonDAO whoya;
	
	public whoyaList ACI0010Rc_acCd(whoyaMap params) throws Exception {		
		return whoya.list("ACI0010.ACI0010Rc_acCd", params);
	}

	public whoyaList ACI0010Rs(whoyaMap params) throws Exception {		
		return whoya.list("ACI0010.ACI0010Rs", params);
	}
	public int ACI0010Ui(whoyaMap params) throws Exception {		
		return whoya.insert("ACI0010.ACI0010Ui", params);
	}
	public int ACI0010Uu(whoyaMap params) throws Exception {		
		return  whoya.update("COI0010.COI0010Uu", params);
	}
	public int ACI0010Ud(whoyaMap params) throws Exception {		
		return whoya.delete("ACI0010.ACI0010Ud", params);
	}
}
