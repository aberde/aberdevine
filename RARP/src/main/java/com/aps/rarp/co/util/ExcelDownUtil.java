package com.aps.rarp.co.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

/**
 * 엑셀 다운로드 유틸
 * <p><b>NOTE:</b> [설명 및 주의사항]
 * 엑셀 다운로드는 총 4가지 경우의 수를 지원하고 있음.
 * 
 * 	1. 복수의 VO map을 가지고 저장 위치를 선택할 수 있는 팝업띄워 저장
 * 	2. 1개의 list를 가지고 저장 위치를 선택할 수 있는 팝업띄워 저장
 * 
 * 1-2, 3-4를 쌍으로 overloading되어 구현됨.
 * (매개변수를 list와 map으로 구분하여 동작함.)
 * 리스트로 뿌려줄 listVo나
 * 여러 VO들을 한데묶은 map을
 * 매개변수로 넘겨줘야 함.
 * 
 * @author 기술지원팀 박헌민
 * @since 2012. 7. 11 
 * @see
 * <pre>
 * == 개정이력(Modification Information) ==
 *
 * 수정일                		수정자 	 			수정내용
 * ------------		---------------		---------------------------
 * 2012. 7. 11.     기술지원팀 김정수	           최초 생성
 *</pre>
 */
@Component
public class ExcelDownUtil {
private static final Logger LOGGER = Logger.getLogger(ExcelDownUtil.class);
	
	

	/**
	 * 리스트로 구성된 데이터를 사용자가 원하는 위치에 저장
	 * @param list
	 * @param templateName
	 * @param templatePath
	 * @param viewName
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	public static boolean freeLocation(List list, String templateName, String templatePath, String viewName,
			HttpServletRequest req, HttpServletResponse res) throws Exception{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("resultList", list);
		
		
		File createTempFile = File.createTempFile("aaa", ".tmp", new File("/weblogic/bea/user_projects/efrosdomain/webapp/TEMP-DIR/excelTmp/"));
		XLSTransformer transformer = new XLSTransformer();
		transformer.transformXLS(templatePath+templateName, map, createTempFile.getAbsolutePath());

		String mimetype = "application/x-msdownload";
		
		res.setBufferSize((int) createTempFile.length());

		res.setContentType(mimetype);
		setDisposition(viewName+".xls", req, res);
		
		BufferedInputStream in = null;
		BufferedOutputStream out = null;

		try {			
						
			in = new BufferedInputStream(new FileInputStream(createTempFile));
			out = new BufferedOutputStream(res.getOutputStream());
			FileCopyUtils.copy(in, out);
			out.flush();
		} catch (Exception ex) {
		    LOGGER.error(ex.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception ignore) {
				    LOGGER.error(ignore.getMessage());
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception ignore) {
				    LOGGER.error(ignore.getMessage());
				}
			}
			createTempFile.deleteOnExit();
		}
		return true;
	}
	
	
	/**
	 * 복수의 VO로 구성된 데이터를 사용자가 원하는 위치에 저장
	 * @param mapt
	 * @param templateName
	 * @param templatePath
	 * @param viewName
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public static boolean freeLocation(Map map, String templateName, String templatePath, String viewName,
			HttpServletRequest req, HttpServletResponse res) throws Exception{
		
		File createTempFile = File.createTempFile("aaa", ".tmp", new File("/TEMP-DIR/excelTmp"));
		XLSTransformer transformer = new XLSTransformer();
		transformer.transformXLS(templatePath+templateName, map, createTempFile.getAbsolutePath());

		String mimetype = "application/x-msdownload";
		
		res.setBufferSize((int) createTempFile.length());

		res.setContentType(mimetype);
		setDisposition(viewName+".xls", req, res);
		
		BufferedInputStream in = null;
		BufferedOutputStream out = null;

		try {			
						
			in = new BufferedInputStream(new FileInputStream(createTempFile));
			ServletOutputStream outputStream = res.getOutputStream();
			out = new BufferedOutputStream(outputStream);
			FileCopyUtils.copy(in, out);
			out.flush();
		} catch (Exception ex) {
		    LOGGER.error(ex.getMessage());
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception ignore) {
				    LOGGER.debug("IGNORED: " + ignore.getMessage());
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception ignore) {
				    LOGGER.debug("IGNORED: " + ignore.getMessage());
				}
			}
			createTempFile.deleteOnExit();
			
		}
		return true;
	}
	
	
	/* 아래의 메서드는 브라우저 구분얻기, Disposition 지정하기 메서드임  */
	/**
	 * 브라우저 구분 얻기.
	 * 
	 * @param request
	 * @return
	 */
	private static String getBrowser(HttpServletRequest request) {
	    String rtnStr = "";
		String header = request.getHeader("User-Agent");
		if (header.indexOf("MSIE") > -1) {
			rtnStr =  "MSIE";
		} else if (header.indexOf("Chrome") > -1) {
			rtnStr =  "Chrome";
		} else if (header.indexOf("Opera") > -1) {
			rtnStr =  "Opera";
		}else{
		    rtnStr = "Firefox";
		}
		return rtnStr;
	}
	/**
	 * Disposition 지정하기.
	 * 
	 * @param filename
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private static void setDisposition(String filename, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String browser = getBrowser(request);

		String dispositionPrefix = "attachment; filename=";
		String encodedFilename = null;

		if ("MSIE".equals(browser)) {
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll(
					"\\+", "%20");
		} else if ("Firefox".equals(browser)) {
			encodedFilename = "\""
					+ new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if ("Opera".equals(browser)) {
			encodedFilename = "\""
					+ new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if ("Chrome".equals(browser)) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < filename.length(); i++) {
				char c = filename.charAt(i);
				if (c > '~') {
				    String a = (String)Character.toString(c);
					sb.append(URLEncoder.encode(a, "UTF-8"));
				} else {
					sb.append(c);
				}
			}
			encodedFilename = sb.toString();
		} else {
			// throw new RuntimeException("Not supported browser");
			throw new IOException("Not supported browser");
		}

		response.setHeader("Content-Disposition", dispositionPrefix
				+ encodedFilename);

		if ("Opera".equals(browser)) {
			response.setContentType("application/octet-stream;charset=UTF-8");
		}
	}
	
	/**
	 * vo 변형
	 * @param vo
	 * @param formatPropertyMap 변형할 포맷프로퍼티맵
	 * @param codePropertyList 
	 * @return Object
	 */
	public static Object voTransform(Object vo, Map<String,String> formatPropertyMap, List<String> codePropertyList){
		
		Method[] methods = vo.getClass().getMethods();
		HashMap<String, Method> methodsMap = new HashMap<String, Method>();
		
		for (Method method : methods) {
			methodsMap.put(method.getName().toLowerCase(), method);
		}
		
		Method getMethod = null;
		Method setMethod = null;
		//String comcdId = null;
		//SnCo808VO snCo808VO = null;
		
		//코드명 변경
		for (String codeProperty : codePropertyList) {
		    
			getMethod = methodsMap.get("get"+codeProperty.toLowerCase());
			setMethod = methodsMap.get("set"+codeProperty.toLowerCase());
			/*
			try {
				//comcdId = (String) getMethod.invoke(vo);
				//snCo808VO = codeService.selectCdById(comcdId);
				//setMethod.invoke(vo, snCo808VO.getComcdNm());
			} catch (Exception e) {
			    LOGGER.error(e.getMessage());
			} 
			*/
		}
		
		//포멧팅 변경
		Set<String> keySet = formatPropertyMap.keySet();
		String value = null;
		for (String formatProperty : keySet) {
			getMethod = methodsMap.get("get"+formatProperty.toLowerCase());
			setMethod = methodsMap.get("set"+formatProperty.toLowerCase());
			String type = formatPropertyMap.get(formatProperty);
			try {
				value = (String) getMethod.invoke(vo).toString();
				setMethod.invoke(vo, FormatUtil.trnalateFormat(type, value));
			} catch (Exception e) {
			    LOGGER.error(e.getMessage());
			} 
		}	
	
		return vo;
	}
	
	/**
	 * vo 변형
	 * @param voList
	 * @param formatPropertyMap
	 * @param codePropertyList
	 * @return
	 */
	public static List<Object> voTransform(List voList, Map<String,String> formatPropertyMap, List<String> codePropertyList){
		
		List<Object> transformedVoList = new ArrayList<Object>();  
		for (int i = 0; i < voList.size(); i++) {
			Object transformedVO = voTransform(voList.get(i), formatPropertyMap, codePropertyList);
			transformedVoList.add(transformedVO);
		}
		return transformedVoList;
	}
}
