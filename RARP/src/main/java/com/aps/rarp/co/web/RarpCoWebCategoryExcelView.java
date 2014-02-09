package com.aps.rarp.co.web;

//import java.io.IOException;
//import java.net.URLEncoder;
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.aps.rarp.co.util.WebUtil;
/**
 * CMS CmsCoCategoryExcelView
 * @author 
 * @since 2012. 05.29
 * <pre>
 * == 개정이력(Modification Information) ==
 *
 * 수정일                		수정자 	 			수정내용
 * ------------		---------------		---------------------------
 * 2013. 04.22        	 최초 생성
 *</pre>
 */
public class RarpCoWebCategoryExcelView extends AbstractExcelView {
	
	protected static final Logger LOGGER = Logger.getLogger(RarpCoWebCategoryExcelView.class);
	
	@SuppressWarnings("deprecation")
	@Override
	protected void buildExcelDocument(
										Map model,
										HSSFWorkbook wb,
										HttpServletRequest request,
										HttpServletResponse response) throws Exception {
		
		

		
		//엑셀 Sheet 제목
		String ExcelTitle = (String) model.get("ExcelTitle");

		HSSFCell cell   = null;
		HSSFSheet sheet = wb.createSheet(ExcelTitle);
		sheet.setDefaultColumnWidth((short) 12);
 
//		HSSFColor hColor = new HSSFColor();
		
		
        //해더부분셀에 스타일을 주기위한 인스턴스 생성    
        HSSFCellStyle TcellStyle = wb.createCellStyle();             
        TcellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);                 //스타일인스턴스의 속성 ?V팅    
        //TcellStyle.setAlignment(HSSFCellStyle.VERTICAL_CENTER);
        TcellStyle.setFillPattern(HSSFColor.SKY_BLUE.index);
        TcellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);              //테두리 설정    
        TcellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);    
        TcellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);    
        TcellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);    
        HSSFFont Tfont = wb.createFont();                                    //폰트 조정 인스턴스 생성    
        //Tfont.setFontName(HSSFFont.FONT_ARIAL);
        Tfont.setFontHeightInPoints((short) 17);
        Tfont.setBoldweight((short)800);         
        TcellStyle.setFont(Tfont);  
        
        //해더부분셀에 스타일을 주기위한 인스턴스 생성    
        HSSFCellStyle cellStyle = wb.createCellStyle();             
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);                 //스타일인스턴스의 속성 ?V팅            
        //cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);    
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);              //테두리 설정    
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);    
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);    
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);    
        HSSFFont font = wb.createFont();                                    //폰트 조정 인스턴스 생성    
        //font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight((short)700);         
        cellStyle.setFont(font);  


        //가운데 정렬과 얇은 테두리를 위한 스타일 인스턴스 생성    
        HSSFCellStyle cellStyle0 = wb.createCellStyle();                            
        cellStyle0.setAlignment(HSSFCellStyle.ALIGN_CENTER);    
        cellStyle0.setBorderRight(HSSFCellStyle.BORDER_THIN);    
        cellStyle0.setBorderLeft(HSSFCellStyle.BORDER_THIN);    
        cellStyle0.setBorderTop(HSSFCellStyle.BORDER_THIN);    
        cellStyle0.setBorderBottom(HSSFCellStyle.BORDER_THIN);  

		int a = 0;
		int b = 0;
		
		//엑셀 넓이 설정
		LinkedHashMap<String, Object> WidthMap = (LinkedHashMap<String, Object>) model.get("categoryWidth");
		Iterator<String> WidthIt = WidthMap.keySet().iterator();
		
		a = 0;
		while(WidthIt.hasNext()){
			String key = WidthIt.next();
			sheet.setColumnWidth((short)a, (short)Short.valueOf(WidthMap.get(key)+""));
			a++;
		}
		
		
		//엑셀 header 부분
		LinkedHashMap<String, Object> HeadMap  = (LinkedHashMap<String, Object>) model.get("categoryHeader");
		Iterator<String> HeadIt = HeadMap.keySet().iterator();
		
		int count = HeadMap.size();
		//Region(int rowFrom, short colFrom, int rowTo, short colTo) 
		sheet.addMergedRegion(new Region(1,(short)1,1,(short)2)); 	   		// 가로병합
		sheet.addMergedRegion(new Region(0,(short)0,1,(short)(count-1)));   // 세로병합
		
		// put text in first cell
		cell = getCell(sheet, 0, 0);
		cell.setCellStyle(TcellStyle);
		setText(cell, ExcelTitle);
		
		a = 0;
		while(HeadIt.hasNext()){
			
			//이건 자동으로 조절 하면 너무 딱딱해 보여서 자동조정한 사이즈에 (short)512를 추가해 주니 한결 보기 나아졌다. to 명진
			//sheet.autoSizeColumn(a);
			//sheet.setColumnWidth(a, (sheet.getColumnWidth(a))+512 ); 
			
			String key = HeadIt.next();
			
			cell = getCell(sheet, 2, a);
			cell.setCellStyle(cellStyle);
			setText(cell, HeadMap.get(key)+"");
			a++;
		}
		
		//Map<String, Object> ListMap = (Map<String, Object>) model.get("categoryList");
		List<Object> categories = (List<Object>) model.get("categoryList");
 
		Iterator<String> ListIt;
		for (int i = 0; i < categories.size(); i++) {
 
			// Map
			Map<String, String> category = (Map<String, String>) categories.get(i);
			ListIt = HeadMap.keySet().iterator();
			
			b = 0;
			while(ListIt.hasNext()){
				String key = ListIt.next();
				cell = getCell(sheet, 3 + i, b);
				cell.setCellStyle(cellStyle0);
				setText(cell, category.get(key));
				b++;
			}
		}

		String fileName  = WebUtil.createFileName(ExcelTitle);
		WebUtil.setDisposition(fileName, request, response);
	}
}
