package whoya.egovframework.com.uss.olp.mgt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import whoya.egovframework.com.uss.olp.mgt.service.WhoyaEgovMeetingManageService;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.uss.olp.mgt.service.EgovMeetingManageService;
import egovframework.rte.fdl.cmmn.AbstractServiceImpl;
/**
 * 회의관리를 처리하기 위한 ServiceImpl 구현 Class
 */
@Service("whoyaEgovMeetingManageService")
public class WhoyaEgovMeetingManageServiceImpl extends AbstractServiceImpl implements WhoyaEgovMeetingManageService{
  
    /** egovMeetingManageService Member Variable */
    @Resource(name = "egovMeetingManageService")
    private EgovMeetingManageService egovMeetingManageService;
    
    /**
     * 부서 목록을 조회한다. 
     * @param searchVO - 조회할 정보가 담긴 VO
     * @return List
     * @throws Exception
     */
    public List egovMeetingManageLisAuthorGroupPopup(ComDefaultVO searchVO) throws Exception {
        return egovMeetingManageService.egovMeetingManageLisAuthorGroupPopup(searchVO);
    }
  
    /**
     * 아이디 목록을 조회한다. 
     * @param searchVO - 조회할 정보가 담긴 VO
     * @return List
     * @throws Exception
     */
    public List egovMeetingManageLisEmpLyrPopup(ComDefaultVO searchVO) throws Exception {
        return egovMeetingManageService.egovMeetingManageLisEmpLyrPopup(searchVO);
    }
//	//final private Log log = LogFactory.getLog(this.getClass());
//	
//	@Resource(name="meetingManageDao")
//	private MeetingManageDao dao;
//	
//	@Resource(name="egovMgtIdGnrService")
//	private EgovIdGnrService idgenService;
//	
//    /**
//	 * 회의정보 목록을 조회한다. 
//	 * @param searchVO - 조회할 정보가 담긴 VO
//	 * @return List
//	 * @throws Exception
//	 */
//	public List selectMeetingManageList(ComDefaultVO searchVO) throws Exception{
//		return (List)dao.selectMeetingManageList(searchVO);
//	}
//	
//    /**
//	 * 회의정보를 상세조회 한다.
//	 * @param MeetingManageVO - 회정정보가 담김 VO
//	 * @return List
//	 * @throws Exception
//	 */
//	public List selectMeetingManageDetail(MeetingManageVO meetingManageVO) throws Exception{
//		return (List)dao.selectMeetingManageDetail(meetingManageVO);
//	}
//	
//    /**
//	 * 회의정보를 목록 전체 건수를 조회한다.
//	 * @param searchVO - 조회할 정보가 담긴 VO
//	 * @return int 
//	 * @throws Exception
//	 */
//	public int selectMeetingManageListCnt(ComDefaultVO searchVO) throws Exception{
//		return (Integer)dao.selectMeetingManageListCnt(searchVO);
//	}
//
//    /**
//	 * 회의정보를 등록한다.
//	 * @param searchVO - 조회할 정보가 담긴 VO
//	 * @throws Exception
//	 */
//	public void insertMeetingManage(MeetingManageVO meetingManageVO) throws Exception {
//		String sMakeId = idgenService.getNextStringId();
//		
//		meetingManageVO.setMtgId(sMakeId);
//
//		dao.insertMeetingManage(meetingManageVO);
//	}
//	
//    /**
//	 * 회의정보를 수정한다.
//	 * @param searchVO - 조회할 정보가 담긴 VO
//	 * @throws Exception
//	 */
//	public void updateMeetingManage(MeetingManageVO meetingManageVO){
//		dao.updateMeetingManage(meetingManageVO);
//	}
//	
//    /**
//	 * 회의정보를 삭제한다.
//	 * @param searchVO - 조회할 정보가 담긴 VO
//	 * @throws Exception
//	 */
//	public void deleteMeetingManage(MeetingManageVO meetingManageVO){
//		dao.deleteMeetingManage(meetingManageVO);
//	}
}
