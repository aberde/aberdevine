package kr.go.rndcall.mgnt.common;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.commons.beanutils.BeanUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.*;
import org.apache.struts.action.ActionForm;

import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.text.*;
import java.util.*;

import org.apache.log4j.Logger;
import org.apache.commons.lang.exception.NestableException;

public class MakeExcelFile {
	
	private Logger logger = Logger.getLogger(this.getClass().getName());
	
	public MakeExcelFile() {
	}
	
	public void makeExcel(String target, HttpServletRequest request,
			OutputStream servletoutputstream) throws NamingException,
			SQLException, NamingException, IOException, SQLException,
			NamingException, NestableException {	
		
		if (target.equals("targerName")) { // 타켓명
			// MakeExcelFile makeExcelFile = new
			// kr.go.ntis.mgnt.va.conf.excel.TargetBizViewExcel();
			// makeExcelFile.service(request, servletoutputstream);
		}		
	}
	
	public void service(HttpServletRequest request,
			OutputStream servletoutputstream) throws NamingException,
			SQLException, IOException {
		
	}
	
	public void service(ActionForm form, HttpServletRequest request,
			OutputStream servletoutputstream) throws NamingException,
			SQLException, IOException {
		
	}
	
	// // 평가위원회위원신용도목록
	// private void cmitEstmCreditList(HttpServletRequest request,
	// OutputStream servletoutputstream) throws
	// NamingException,
	// SQLException, IOException {
	//
	// kr.or.krf.bnc.bk21.rnd.estm.credit.DAO dao = new
	// kr.or.krf.bnc.bk21.rnd.estm.credit.DAO();
	// // kr.or.krf.bnc.bk21.rnd.estm.conf.Form fm = new
	// kr.or.krf.bnc.bk21.rnd.estm.conf.Form();
	// // kr.or.krf.bnc.bk21.rnd.estm.conf.SearchVO searchVO = fm.getSearchVO();
	// String cmit_id = request.getParameter("cmit_id");
	// ArrayList voList = new ArrayList();
	//
	//
	// try {
	// voList = dao.getCmitMbrList(cmit_id);
	// } catch (Exception e) {
	// logger.error("### Exception: " + e.getMessage());
	// }
	// HSSFWorkbook wb = new HSSFWorkbook();
	// HSSFSheet sheet = wb.createSheet();
	// wb.setSheetName(0, "평가위원신용도", HSSFWorkbook.ENCODING_UTF_16);
	//
	// // 분과명 병합
	// sheet.addMergedRegion(new Region(0, (short) 0, 1, (short) 0));
	// // 위원구분
	// sheet.addMergedRegion(new Region(0, (short) 1, 1, (short) 1));
	// // 이름
	// sheet.addMergedRegion(new Region(0, (short) 2, 1, (short) 2));
	// // 소속기관
	// sheet.addMergedRegion(new Region(0, (short) 3, 1, (short) 3));
	// // 의뢰결과
	// sheet.addMergedRegion(new Region(0, (short) 4, 0, (short) 6));
	// // 확정결과
	// sheet.addMergedRegion(new Region(0, (short) 7, 1, (short) 7));
	// // 메일발송여부
	// sheet.addMergedRegion(new Region(0, (short) 8, 1, (short) 8));
	// // 참석여부
	// sheet.addMergedRegion(new Region(0, (short) 9, 1, (short) 9));
	//
	// sheet.setColumnWidth((short)0, (short)((20)*256));
	// sheet.setColumnWidth((short)1, (short)((10)*256));
	// sheet.setColumnWidth((short)2, (short)((10)*256));
	// sheet.setColumnWidth((short)3, (short)((25)*256));
	// sheet.setColumnWidth((short)4, (short)((15)*256));
	// sheet.setColumnWidth((short)5, (short)((15)*256));
	// sheet.setColumnWidth((short)6, (short)((20)*256));
	// sheet.setColumnWidth((short)7, (short)((15)*256));
	// sheet.setColumnWidth((short)8, (short)((15)*256));
	// sheet.setColumnWidth((short)9, (short)((15)*256));
	//
	//
	// String title1[] = {"분과명", "위원구분", "위원명", "소속기관", "성실성", "", "",
	// "전문성\n(-20 ~ +20)", "공정성\n(-20 ~ +20)", "합계"};
	// String title2[] = {"", "", "", "", "평가\n불참\n(-15)", "지각/\n자리이석\n(-10)",
	// "성실한 평가의견\n제시 및 질의여부\n/위원장 등(+10)", "", "", ""};
	//
	// String Value[] = {"brch_nm", "evalMbrClsfNM", "nameKO", "officeName",
	// "trust1001Point", "trust1002Point", "trust1003Point", "trust2001Point",
	// "trust2002Point", "trustPoint"};
	//
	// Vector title = new Vector();
	// title.add(title1);
	// title.add(title2);
	//
	// // 내용 생성
	// MakeExcelDetail(wb, sheet, voList, title, Value);
	// wb.write(servletoutputstream);
	// }
	//
	//
	// // 위원회 평가위원 목록
	// private void cmitEstmList(HttpServletRequest request,
	// OutputStream servletoutputstream) throws
	// NamingException,
	// SQLException, IOException {
	//
	// kr.or.krf.bnc.bk21.rnd.estm.conf.DAO dao = new
	// kr.or.krf.bnc.bk21.rnd.estm.conf.DAO();
	// // kr.or.krf.bnc.bk21.rnd.estm.conf.Form fm = new
	// kr.or.krf.bnc.bk21.rnd.estm.conf.Form();
	// // kr.or.krf.bnc.bk21.rnd.estm.conf.SearchVO searchVO = fm.getSearchVO();
	// String cmit_id = request.getParameter("cmit_id");
	// ArrayList voList = new ArrayList();
	//
	//
	// try {
	// voList = dao.getCmitEstmList(cmit_id);
	// } catch (Exception e) {
	// logger.error("### Exception: " + e.getMessage());
	// }
	// HSSFWorkbook wb = new HSSFWorkbook();
	// HSSFSheet sheet = wb.createSheet();
	// wb.setSheetName(0, "평가위원목록", HSSFWorkbook.ENCODING_UTF_16);
	//
	// // 분과명 병합
	// sheet.addMergedRegion(new Region(0, (short) 0, 1, (short) 0));
	// // 순번병합
	// sheet.addMergedRegion(new Region(0, (short) 1, 1, (short) 1));
	// // 위원구분
	// sheet.addMergedRegion(new Region(0, (short) 2, 1, (short) 2));
	// // 이름
	// sheet.addMergedRegion(new Region(0, (short) 3, 1, (short) 3));
	// // 소속기관
	// sheet.addMergedRegion(new Region(0, (short) 4, 1, (short) 4));
	// // 의뢰결과
	// sheet.addMergedRegion(new Region(0, (short) 6, 1, (short) 6));
	// // 확정결과
	// sheet.addMergedRegion(new Region(0, (short) 7, 1, (short) 7));
	// // 메일발송여부
	// sheet.addMergedRegion(new Region(0, (short) 8, 0, (short) 9));
	// // 참석여부
	// sheet.addMergedRegion(new Region(0, (short) 10, 1, (short) 10));
	//
	// sheet.setColumnWidth((short)0, (short)((20)*256));
	// sheet.setColumnWidth((short)1, (short)((5)*256));
	// sheet.setColumnWidth((short)2, (short)((10)*256));
	// sheet.setColumnWidth((short)3, (short)((10)*256));
	// sheet.setColumnWidth((short)4, (short)((25)*256));
	// sheet.setColumnWidth((short)5, (short)((30)*256));
	// sheet.setColumnWidth((short)6, (short)((10)*256));
	// sheet.setColumnWidth((short)7, (short)((10)*256));
	// sheet.setColumnWidth((short)8, (short)((10)*256));
	// sheet.setColumnWidth((short)9, (short)((10)*256));
	// sheet.setColumnWidth((short)10, (short)((10)*256));
	//
	// String title1[] = {"분과명", "순번", "위원구분", "이름", "소속기관", "전화번호 / 휴대폰번호",
	// "의뢰결과", "확정결과", "메일발송여부", "", "참석여부"};
	// String title2[] = {"", "", "", "", "", "전자우편", "", "", "의뢰메일", "확정메일",
	// ""};
	//
	// String Value[] = {"brch_nm", "brch_rnum", "eval_mbr_clsf_nm", "nameKO",
	// "officeName2", "contactAddr", "agree_st_nm", "confirm_st_nm",
	// "reqmail_snd_yn", "cnfmail_snd_yn", "attend_yn_nm"};
	//
	// Vector title = new Vector();
	// title.add(title1);
	// title.add(title2);
	//
	// // 내용 생성
	// MakeExcelDetail(wb, sheet, voList, title, Value);
	// wb.write(servletoutputstream);
	// }
	//
	//
	//
	//
	// // 세부 엑셀파일 내용 기록
	// private void MakeExcelDetail(HSSFWorkbook wb, HSSFSheet sheet, ArrayList
	// voList, Vector title, String[] Value) throws
	// NamingException,
	// SQLException, IOException {
	//
	// HSSFRow row;
	// HSSFCell cell;
	//
	// // Title Cell Style 정의
	// HSSFCellStyle cellStyleTitle = wb.createCellStyle();
	// cellStyleTitle.setWrapText(true);
	// cellStyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	// cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	// cellStyleTitle.setFillPattern(HSSFCellStyle.SQUARES);
	// cellStyleTitle.setFillBackgroundColor(HSSFColor.LIGHT_YELLOW.index);
	// cellStyleTitle.setFillForegroundColor(HSSFColor.YELLOW.index);
	// cellStyleTitle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	// cellStyleTitle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	// cellStyleTitle.setBorderTop(HSSFCellStyle.BORDER_THIN);
	// cellStyleTitle.setBorderRight(HSSFCellStyle.BORDER_THIN);
	//
	// // Year Cell Style 정의
	// HSSFCellStyle cellStyleYear = wb.createCellStyle();
	// cellStyleYear.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	// cellStyleYear.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	//
	// cellStyleYear.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	// cellStyleYear.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	// cellStyleYear.setBorderTop(HSSFCellStyle.BORDER_THIN);
	// cellStyleYear.setBorderRight(HSSFCellStyle.BORDER_THIN);
	//
	// // Value Cell Style 정의
	// HSSFCellStyle cellStyleValue = wb.createCellStyle();
	// cellStyleValue.setWrapText(true);
	// cellStyleValue.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	// cellStyleValue.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
	// cellStyleValue.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	// cellStyleValue.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	// cellStyleValue.setBorderTop(HSSFCellStyle.BORDER_THIN);
	// cellStyleValue.setBorderRight(HSSFCellStyle.BORDER_THIN);
	//
	// // Title1 생성
	// for (int i = 0; i < title.size(); i++) {
	// String t[] = (String[]) title.get(i);
	// row = sheet.createRow((short) i); //행추가
	// for (int j = 0; j < t.length; j++) {
	// cell = row.createCell((short) j);
	//
	// cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
	// cell.setCellValue(t[j]);
	// cell.setCellStyle(cellStyleTitle);
	// }
	// }
	//
	// // 내용 생성
	// try {
	// for (int i = 0; i < voList.size(); i++) {
	// HSSFRow rowOut = sheet.createRow((short) (i + title.size()));
	//
	// for (int j = 0; j < Value.length; j++) {
	// cell = rowOut.createCell((short) j);
	// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
	// cell.setCellValue(Util.makeExcelMutiLine(BeanUtils.getProperty(voList.get(i),
	// Value[j])));
	// cell.setCellStyle(cellStyleValue);
	// }
	// }
	// } catch (Exception e) {
	// logger.error("### Exception: " + e.getMessage());
	// }
	// }
	// // 세부 엑셀파일 내용 기록 - end
}
