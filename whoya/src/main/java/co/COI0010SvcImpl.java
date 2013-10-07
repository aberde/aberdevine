package co;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import common.commonDAO;

import whoya.whoyaList; 
import whoya.whoyaMap;

import co.COI0010Svc;

@Service("COI0010Svc")
public class COI0010SvcImpl implements COI0010Svc {

	@Resource(name="whoya")
	private commonDAO whoya;
	
	public whoyaList COI0010Rc_dptCd(whoyaMap params) throws Exception {		
		return whoya.list("COI0010.COI0010Rc_dptCd", params);
	}
	public whoyaList COI0010Rc_rolCd(whoyaMap params) throws Exception {		
		return whoya.list("COI0010.COI0010Rc_rolCd", params);
	}
	public whoyaList COI0010Rc_rolId(whoyaMap params) throws Exception {		
		return whoya.list("COI0010.COI0010Rc_rolId", params);
	}
	public whoyaList COI0010Rc_grpId(whoyaMap params) throws Exception {		
		return whoya.list("COI0010.COI0010Rc_grpId", params);
	}
	public whoyaList COI0010Rs(whoyaMap params) throws Exception {		
		return whoya.list("COI0010.COI0010Rs", params);
	}
	public int COI0010Is(whoyaMap params) throws Exception {		
		return whoya.insert("COI0010.COI0010Is", params);
	}
	public int COI0010Us(whoyaMap params) throws Exception {		
		return  whoya.update("COI0010.COI0010Us", params);
	}
	public int COI0010Ds(whoyaMap params) throws Exception {		
		return whoya.delete("COI0010.COI0010Ds", params);
	}
}
