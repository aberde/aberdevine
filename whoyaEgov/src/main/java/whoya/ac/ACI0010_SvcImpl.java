package whoya.ac;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


@Service("ACI0010_Svc")
public class ACI0010_SvcImpl implements ACI0010_Svc {

	@Resource(name="ACI0010_DAO")
	private ACI0010_DAO whoya;
	
	public List<Map<String, String>> ACI0010_Rc_acCd(Map<String, String> params) throws Exception {		
		return whoya.list("ACI0010_.ACI0010_Rc_acCd", params);
	}

	public List<Map<String, String>> ACI0010_Rs(Map<String, String> params) throws Exception {		
		return whoya.list("ACI0010_.ACI0010_Rs", params);
	}
	public int ACI0010_Ui(Map<String, String> params) throws Exception {		
		return whoya.insert("ACI0010_.ACI0010_Ui", params);
	}
	public int ACI0010_Uu(Map<String, String> params) throws Exception {		
		return  whoya.update("ACI0010_.ACI0010_Uu", params);
	}
	public int ACI0010_Ud(Map<String, String> params) throws Exception {		
		return whoya.delete("ACI0010_.ACI0010_Ud", params);
	}
}
