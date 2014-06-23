package kr.go.rndcall.mgnt.statistic.biz;

import java.sql.SQLException;

import javax.naming.NamingException;

import kr.go.rndcall.mgnt.statistic.dao.StatisticDAO;
import kr.go.rndcall.mgnt.statistic.vo.StatisticResultVO;
import kr.go.rndcall.mgnt.statistic.vo.StatisticSearchVO;
import kr.go.rndcall.mgnt.statistic.vo.StatisticVO;
import kr.go.rndcall.mgnt.common.DAOBaseException;

public class StatisticBiz {
	public StatisticResultVO getStatCategory(StatisticSearchVO searchVO) throws Exception {
		  
		StatisticDAO StatisticDAO = new StatisticDAO();
		
		return StatisticDAO.getStatCategory(searchVO);
	}
	
	public StatisticResultVO getStatDate(StatisticSearchVO searchVO) throws Exception {
		  
		StatisticDAO StatisticDAO = new StatisticDAO();
		
		return StatisticDAO.getStatDate(searchVO);
	}
	
	public StatisticResultVO getStatDataList(StatisticSearchVO searchVO) throws Exception {
		  
		StatisticDAO StatisticDAO = new StatisticDAO();
		
		return StatisticDAO.getStatDataList(searchVO);
	}
	
	public StatisticResultVO getStatDataExcel(StatisticSearchVO searchVO) throws Exception {
		  
		StatisticDAO StatisticDAO = new StatisticDAO();
		
		return StatisticDAO.getStatDataExcel(searchVO);
	}
	
	
	public StatisticResultVO getStatVisit(StatisticSearchVO searchVO) throws Exception {
		  
		StatisticDAO StatisticDAO = new StatisticDAO();
		
		return StatisticDAO.getStatVisit(searchVO);
	}
	
	
	public StatisticResultVO getStatList(StatisticSearchVO searchVO) throws Exception {
		  
		StatisticDAO StatisticDAO = new StatisticDAO();
		
		return StatisticDAO.getStatList(searchVO);
	}
	
	public StatisticResultVO getStatView(StatisticSearchVO searchVO) throws Exception {
		  
		StatisticDAO StatisticDAO = new StatisticDAO();
		
		return StatisticDAO.getStatView(searchVO);
	}
	
	public StatisticResultVO getFileInfo(String file_id) throws Exception {
		  
		StatisticDAO StatisticDAO = new StatisticDAO();
		
		return StatisticDAO.getFileInfo(file_id);
	}
	
	public StatisticResultVO getStatExcelList(StatisticSearchVO searchVO) throws Exception {
		  
		StatisticDAO StatisticDAO = new StatisticDAO();
		
		return StatisticDAO.getStatExcelList(searchVO);
	}
	
}
