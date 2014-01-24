package whoya.hr;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;



import whoya.whoyaList; 
import whoya.whoyaMap;
import whoya.common.commonDAO;
import whoya.hr.HRI0010Svc;

@Service("HRI0010Svc")
public class HRI0010SvcImpl implements HRI0010Svc {

	@Resource(name="whoya")
	private commonDAO whoya;
	
	public whoyaList HRI0010Rc_dptCd(whoyaMap params) throws Exception {		
		return whoya.list("HRI0010.HRI0010Rc_dptCd", params);
	}
	public whoyaList HRI0010Rc_jgicCd(whoyaMap params) throws Exception {
		return whoya.list("HRI0010.HRI0010Rc_jgicCd", params);
	}
	public whoyaList HRI0010Rc_rolCd(whoyaMap params) throws Exception {		
		return whoya.list("HRI0010.HRI0010Rc_rolCd", params);
	}
	public whoyaList HRI0010Rc_rolId(whoyaMap params) throws Exception {		
		return whoya.list("COI0010.COI0010Rc_rolId", params);
	}
	public whoyaList HRI0010Rc_grpId(whoyaMap params) throws Exception {		
		return whoya.list("COI0010.COI0010Rc_grpId", params);
	}
	public whoyaList HRI0010Rs(whoyaMap params) throws Exception {		
		return whoya.list("HRI0010.HRI0010Rs", params);
	}
	public int HRI0010Is(whoyaMap params) throws Exception {		
		return whoya.insert("HRI0010.HRI0010Is", params);
	}
	public int HRI0010Us(whoyaMap params) throws Exception {		
		return  whoya.update("HRI0010.HRI0010Us", params);
	}
	public int HRI0010Ds(whoyaMap params) throws Exception {		
		return whoya.delete("HRI0010.HRI0010Ds", params);
	}
}
