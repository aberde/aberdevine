package whoya.co;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.whoyaList;
import whoya.whoyaMap;
import whoya.common.commonDAO;
import whoya.common.commonSession;
import whoya.common.commonSessionUtil;


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
	public int COI0010Save(String[] ids, whoyaMap rows) throws Exception {
		int cnt = 0;
		
		for (int i = 0; i < ids.length; i++) {
			whoyaMap cols = (whoyaMap) rows.get(ids[i]);
			whoyaMap params = new whoyaMap();

			commonSession session = commonSessionUtil.getCommonSession();
			params.put("coId", session.getCoId());
			
		    params.put("usrId", cols.get("USRID"));
		    params.put("usrNm", cols.get("USRNM"));
		    params.put("pwd"  , cols.get("PWD"));
		    params.put("rolCd", cols.get("ROLCD"));
		    params.put("rolId", cols.get("ROLID"));
		    params.put("grpId", cols.get("GRPID"));
		    params.put("dptCd", cols.get("DPTCD"));
		    params.put("telNo", cols.get("TELNO"));
		    params.put("hpnNo", cols.get("HPNNO"));
		    params.put("useYn", cols.get("USEYN"));
		    System.out.println("===================================");
		    System.out.println(ids[i]+":"+cols.get("USRID")+":"+cols.get("!nativeeditor_status"));
		    System.out.println("===================================");
		    if (cols.get("!nativeeditor_status").equals("inserted") ) { cnt += whoya.insert("COI0010.COI0010Is", params); }   
		    if (cols.get("!nativeeditor_status").equals("updated" ) ) { cnt += whoya.update("COI0010.COI0010Us", params); }   
		    if (cols.get("!nativeeditor_status").equals("deleted" ) ) { cnt += whoya.delete("COI0010.COI0010Ds", params); }  
	    }
		
		return cnt;
	}
}
