package kr.go.rndcall.mgnt.data.biz;

import java.sql.SQLException;

import javax.naming.NamingException;

import kr.go.rndcall.mgnt.data.dao.DataDAO;
import kr.go.rndcall.mgnt.data.vo.DataAttachVO;
import kr.go.rndcall.mgnt.data.vo.DataResultVO;
import kr.go.rndcall.mgnt.data.vo.DataSearchVO;
import kr.go.rndcall.mgnt.data.vo.DataVO;
import kr.go.rndcall.mgnt.common.AttachVO;
import kr.go.rndcall.mgnt.common.DAOBaseException;

public class DataBiz {
	//첨부파일 등록
    public void putAttach(DataVO vo, AttachVO[] attachVO) throws Exception {
    	DataDAO dao = new DataDAO();        
        dao.putAttach(vo, attachVO);
    }
	//자료실에 등록한다.
	public DataResultVO dataInsert(DataVO vo, DataSearchVO searchVO) throws Exception {
		DataResultVO reslutVO = new DataResultVO();
		DataDAO dao =  new DataDAO();
        reslutVO =  dao.dataInsert(vo, searchVO);
        return reslutVO;
    }
	//자료실의 리스트를 가져온다.
	public DataResultVO dataList(DataSearchVO searchVO) throws DAOBaseException, SQLException, NamingException {
		DataDAO DataDAO = new DataDAO();
		return DataDAO.dataList(searchVO);
	}
	//자료실 상세보기를 한다.
	public DataResultVO dataDetailView(DataSearchVO searchVO) throws Exception {
		DataDAO DataDAO = new DataDAO();
		return DataDAO.dataDetailView(searchVO);
	}
	//자료실 첨부파일정보
	public DataResultVO getFileInfo(String file_id) throws Exception {
		DataDAO DataDAO = new DataDAO();
		return DataDAO.getFileInfo(file_id);
	}
	//자료실데이터를 삭제한다.
	public boolean dataDelete(DataSearchVO searchVO) throws Exception {
		DataDAO DataDAO = new DataDAO();
		return	DataDAO.dataDelete(searchVO);		
	}
	//첨부파일 삭제유무에따른 메소드
	public void getFileDelete(DataVO vo, DataSearchVO searchVO) throws Exception {
		DataDAO DataDAO = new DataDAO();
		DataDAO.getFileDelete(vo, searchVO);
	}
	//자료실을 수정한다.
	public boolean dataUpdate(DataVO vo, DataSearchVO searchVO) throws Exception {
		DataDAO DataDAO = new DataDAO();
		return DataDAO.dataUpdate(vo, searchVO);
	}
	//연구관리제도의 리스트를 가져온다.
    public DataResultVO dataSystemList(DataSearchVO searchVO) throws DAOBaseException, SQLException, NamingException {
        DataDAO DataDAO = new DataDAO();
        return DataDAO.dataSystemList(searchVO);
    }
    //연구관리제도에 등록한다.
    public DataResultVO dataSystemInsert(DataVO vo, DataSearchVO searchVO) throws Exception {
        DataResultVO reslutVO = new DataResultVO();
        DataDAO dao =  new DataDAO();
        reslutVO =  dao.dataSystemInsert(vo, searchVO);
        return reslutVO;
    }
}
