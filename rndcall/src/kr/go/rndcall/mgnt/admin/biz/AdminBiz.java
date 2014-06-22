package kr.go.rndcall.mgnt.admin.biz;

import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import kr.go.rndcall.mgnt.common.Util;
import kr.go.rndcall.mgnt.common.FileVO;
import kr.go.rndcall.mgnt.common.Configuration;
import kr.go.rndcall.mgnt.common.AttachVO;
import kr.go.rndcall.mgnt.common.DAOBaseException;
import kr.go.rndcall.mgnt.admin.dao.AdminDAO;
import kr.go.rndcall.mgnt.admin.vo.AdminSearchVO;
import kr.go.rndcall.mgnt.admin.vo.AdminResultVO;
import kr.go.rndcall.mgnt.admin.vo.AdminVO;

public class AdminBiz {
    
    public void putAttach(AdminVO vo, AttachVO[] attachVO) throws Exception {
        AdminDAO dao = new AdminDAO();        
        dao.putAttach(vo, attachVO);
    }
    
    /*
     * RNDCALL_BOARD_QUESTION 에 INSERT
     * RNDCALL_BOARD_ANSWER 에 INSERT
     */
    public AdminResultVO getOfflineDataInsert(AdminVO vo) throws Exception {
        AdminResultVO reslutVO = new AdminResultVO();
        
        AdminDAO dao =  new AdminDAO();
        reslutVO =  dao.getOfflineDataInsert(vo);
        
        return reslutVO;
    }
    
    /*
     * 오프라인데이터 엑셀 일괄 업로드
     */
    public int getOfflineDataExcelUploadInsert(FileVO[] fileInfo, AdminSearchVO searchVO)  throws Exception {
        int result = -1;
        

        
        String contextPath = Configuration.getInstance().get("conf.upload.path");
        
        //FileVO fileVO  = new FileVO();
        
        HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(contextPath + fileInfo[0].getRelativePath()+ "/" + fileInfo[0].getOutputFileName()));
        HSSFSheet sheet = wb.getSheetAt(0);
        int max = sheet.getLastRowNum();
        int max_c = 0;
        HSSFRow row = null;
        HSSFCell cell = null;
        ArrayList list = new ArrayList();
        AdminVO vo = null;
        AdminDAO dao = new AdminDAO();
        
        try{
        //0. 엑셀 parsing
        row = sheet.getRow(0);
        max_c = row.getLastCellNum();
        System.out.println("max_c :" + max_c);
        
        int cellType = 0;
        String str = "";
        String question_id = "";
        String answer_id = "";
        int colSize = 9;
        System.out.println("max :" + max);
        //list
        if(max > 0){
        
            for(int i = 1 ; i <= max ; i++){
                row = sheet.getRow(i);
                
                System.out.println("row :" + row);
                
                vo = new AdminVO();
                
                for(int j = 0 ; j < max_c ; j++){
                    cell = row.getCell((short) j);
                    if(cell != null){
                        str = "";
                        cellType = cell.getCellType();
                        
                        switch(cellType){
                        case HSSFCell.CELL_TYPE_NUMERIC :
                            System.out.println(cell.getNumericCellValue());
                            str = String.valueOf(Double.valueOf(cell.getNumericCellValue()).intValue());
                            break;
                        case HSSFCell.CELL_TYPE_STRING :
                            System.out.println(cell.getRichStringCellValue());
                            str = cell.getRichStringCellValue().toString();
                            break;
                        }
                        
                        if (str != null && !str.equals("")) {
	                        switch(j){
	                        
	                        case 0 : //대분류
	                            vo.setCategory1(Integer.toString(Integer.parseInt(str)).trim());
	                            
	                            break;
	                        case 1 : //소분류
	                        	vo.setCategory2(Integer.toString(Integer.parseInt(str)).trim());
	                            
	                            break;
	                        case 2 : //이름
	                            vo.setName(str);
	                            
	                            break;
	                        case 3 : //휴대폰번호
	                            vo.setCell_number(str);
	                            break;
	                        case 4: //email주소
	
	                            vo.setEmail(str);
	                            break;
	                        case 5: //제목
	
	                            vo.setTitle(str);
	                            break;
	                        case 6: //질의내용
	
	                            vo.setQuestion_contents(str);
	                            break;
	                        case 7: //답변내용
	
	                            vo.setAnswer_contents(str);
	                            break;
	                        case 8: //등록일
	
	                            vo.setReg_dt(str);
	                            break;
	                        case 9: //등록시간
	
	                            vo.setReg_time(str);
	                            break;
	                        }
                        }
                    }
                }
                
                question_id = dao.getOfflineDataQuestionSeq();
                answer_id = dao.getOfflineDataAnswerSeq();
//                vo.setInsert_type("OFFLINE");
//                vo.setBoard_type("QNA");
                
                vo.setQuestion_id(question_id);
                vo.setAnswer_id(answer_id);
                vo.setReg_id(searchVO.getLoginId());
                
                list.add(vo);
                
            }
        
        }
        
        if(list != null){
            System.out.println("list size :: " + list.size());
            
        }
        
        /*
        AdminVO tesetVO = new AdminVO();
        for(int i = 0; i < list.size(); i++)
        {
            tesetVO = (AdminVO)list.get(i);
            System.out.println("seq->" + tesetVO.getSeq());
            System.out.println("item->" + tesetVO.getIndex_item_id());
            System.out.println("step->" + tesetVO.getIndex_step_id());
        }
        */
        
        if(list != null && list.size() > 0){
            dao.getOfflineDataExcelUploadInsert(list);        
        }
       
        result = 1;
        
        } catch(Exception e){
            throw e;
            
        } finally{
                     
        }
        
        return result;
    }
    
    /*
     * 대분류 코드를 가져온다.
     */
    public AdminResultVO retrieveCategory1Code(AdminSearchVO searchVO) throws SQLException, DAOBaseException {
        AdminDAO dao = new AdminDAO();
        return dao.retrieveCategory1Code(searchVO);
    }
    
    /*
     * 소분류 코드를 가져온다.
     */
    public AdminResultVO retrieveCategory2Code(AdminSearchVO searchVO) throws SQLException, DAOBaseException {
        AdminDAO dao = new AdminDAO();
        return dao.retrieveCategory2Code(searchVO);
    }
}