package whoya.co;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import whoya.whoyaList; 
import whoya.whoyaMap;
import whoya.co.COI0040Svc;
import whoya.common.commonDAO;


@Service("COI0040Svc")
public class COI0040SvcImpl implements COI0040Svc {

	@Resource(name="whoya")
	private commonDAO whoya;
	
	public whoyaList COI0040Rc_sysDv(whoyaMap params) throws Exception {		
		return whoya.list("COI0040.COI0040Rc_sysDv", params);
	}
	public whoyaList COI0040Rt(whoyaMap params) throws Exception {		
		return whoya.list("COI0040.COI0040Rt", params);
	}
	public whoyaList COI0040Rs(whoyaMap params) throws Exception {		
		return whoya.list("COI0040.COI0040Rs", params);
	}
}
