package co;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import common.commonDAO;

import whoya.whoyaList; 
import whoya.whoyaMap;

import co.COI0020Svc;

@Service("COI0020Svc")
public class COI0020SvcImpl implements COI0020Svc {

	@Resource(name="whoya")
	private commonDAO whoya;
	
	public whoyaList COI0020Rc_mnuDv(whoyaMap params) throws Exception {		
		return whoya.list("COI0020.COI0020Rc_mnuDv", params);
	}
	public whoyaList COI0020Rc_mnuFnc(whoyaMap params) throws Exception {		
		return whoya.list("COI0020.COI0020Rc_mnuFnc", params);
	}
	public whoyaList COI0020Rs(whoyaMap params) throws Exception {		
		return whoya.list("COI0020.COI0020Rs", params);
	}
}
