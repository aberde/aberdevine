package com.aps.rarp.co.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


/**
* 환경변수 interface 구현 클래스
* <p><b>NOTE:</b>
* @author IITECH 개발팀 김명진
* @since 2013.06.20
* @see
*
* <pre>
* == 개정이력(Modification Information) ==
*
* 수정일       수정자    수정내용
* ---------- ------- ---------------------------
* 2013.06.20 
*
* Copyright (C) 2013 by APS. All right reserved.
*/

public class CommonUtil {
    
    protected static final Logger LOGGER = Logger.getLogger(CommonUtil.class);
 
    /**
     * 환경변수에 설정된 값 넣기
     * 
     * @param contsType 콘텐츠 타입
     * @return enviId   환경 변수 ID
     */
    public static String getContsEnviId(String contsType)
    {
		//환경변수에 설정된 값 넣기
		String enviId = "";
		if(StringUtil.equals(contsType,"P1000000")){
			enviId = "PROGRAM_DEL_DATE";
		}else if(StringUtil.equals(contsType,"P2000000")){
			enviId = "ADVERTISE_DEL_DATE";
		}else if(StringUtil.equals(contsType,"P3000000")){
			enviId = "VIDEO_DEL_DATE";
		}else if(StringUtil.equals(contsType,"P4000000")){
			enviId = "AUDIO_DEL_DATE";
		}else if(StringUtil.equals(contsType,"P5000000")){
			enviId = "IMAGE_DEL_DATE";
		}
		
		return enviId;
    }
    
    /**
     * 환경변수에 설정된 값 넣기
     * 
     * @param contsType 콘텐츠 타입
     * @return enviId   환경 변수 ID
     */
    public static String contetnLocationHref(String contsType){
    	
		String locationHref = "";
		
		if(StringUtil.equals(contsType,"P1000000")){
			locationHref = "/cms/cm/contRegPg.do";
		}else if(StringUtil.equals(contsType,"P2000000")){
			locationHref = "/cms/cm/contRegAd.do";
		}else if(StringUtil.equals(contsType,"P3000000")){
			locationHref = "/cms/cm/contRegMain.do";
		}else if(StringUtil.equals(contsType,"P4000000")){
			locationHref = "/cms/cm/contRegAud.do";
		}else if(StringUtil.equals(contsType,"P5000000")){
			locationHref = "/cms/cm/contRegImg.do";
		}
		
		return locationHref;
		
    }
    
    /**
     * 콘텐츠 타입 체크
     * 
     * @param detailType 			콘텐츠 타입
     * @return detailViewTitles     Tiles 값
     */
    public static String contetnDetailTypeCheck(String detailType){
    
    	String detailViewTitles = "";
    	
		if(StringUtil.equals(detailType,"PR") || StringUtil.equals(detailType,"AD") || StringUtil.equals(detailType,"VD") ){
			detailViewTitles = "contentDetailPr.tiles";
		//}else if(CmsCoStringUtil.equals(detailType,"AD") || CmsCoStringUtil.equals(detailType,"VD")){
		//	detailViewTitles = "contentDetailVd.tiles";
		}else if(StringUtil.equals(detailType,"AU")){
			detailViewTitles = "contentDetailAu.tiles";
		}else if(StringUtil.equals(detailType,"IM")){
			detailViewTitles = "contentDetailIm.tiles";
		}
	
		return detailViewTitles;
    }
    
    /**
     * 포맷별 storage 파일 체크 (비디오)
     * 
     * @param seq 			
     * @param fileMap 			
     * @return fileMap     
     */
    public static String contentFileExtCheckVideo(String seq , Map<String,String> fileMap){
    	
    	String reFileMap = "";
    	
		if(StringUtil.equals(seq,"N")){
			reFileMap = fileMap.get("MXF").toString();
		}else if(StringUtil.equals(seq,"Y")){
			reFileMap = fileMap.get("MP4").toString();
		}
	
		return reFileMap;
    }
    
    
    /**
     * 포맷별 storage 파일 체크 (오디오)
     * 
     * @param seq 			
     * @param fileMap 			
     * @return fileMap     
     */
    public static String contentFileExtCheckAudio(String seq , Map<String,String> fileMap){
    	
    	String reFileMap = "";
    	
		if(StringUtil.equals(seq,"N")){
			reFileMap = fileMap.get("WAV").toString();
		}else if(StringUtil.equals(seq,"Y")){
			reFileMap = fileMap.get("MP3").toString();
		}
	
		return reFileMap;
    }
    
    
    /**
     * 포맷별 storage 파일 체크 (이미지)
     * 
     * @param seq 			
     * @param fileMap 			
     * @return fileMap     
     */
    public static String contentFileExtCheckImage(String seq , Map<String,String> fileMap){
    	
    	String reFileMap = "";
    	
		if(StringUtil.equals(seq,"N")){
			reFileMap = fileMap.get("IMG").toString();
		}else if(StringUtil.equals(seq,"Y")){
			reFileMap = fileMap.get("JPG").toString();
		}
	
		return reFileMap;
    }
    
    /**
     * 포맷별 storage 파일 체크 (이미지)
     * 
     * @param seq 			
     * @param fileMap 			
     * @return fileMap     
     */
    public static int contentGetImgWidth(BufferedImage bi){
    	
		double imgW 	 = Double.parseDouble(bi.getWidth()+"");
		double imgH 	 = Double.parseDouble(bi.getHeight()+"");
		int smallH 		 = 0;
		int smallW	 	 = 0;
		int thumWidth 	 = 400;
		//int thumHeight = 185;
		
		if(imgW > 400 && imgH > 185){
			double stWidth  = (400 / imgW)* 100 ;
			double stHeight = (185 / imgH)* 100 ; 
			
			smallW =  (int)stWidth;
			smallH =  (int)stHeight;
			
			if(smallH > smallW){
				
				thumWidth  = (int)(imgW * (stWidth/100));
				//thumHeight = (int)(imgH * (stWidth/100));
				
			}else{
				thumWidth  = (int)(imgW * (stHeight/100));
				//thumHeight = (int)(imgH * (stHeight/100));
			}
			
		}
		
		return thumWidth;
    }
    
    /**
     * 포맷별 storage 파일 체크 (이미지)
     * 
     * @param seq 			
     * @param fileMap 			
     * @return fileMap     
     */
    public static int contentGetImgHeight(BufferedImage bi){
    	
		double imgW 	 = Double.parseDouble(bi.getWidth()+"");
		double imgH 	 = Double.parseDouble(bi.getHeight()+"");
		int smallH 		 = 0;
		int smallW	 	 = 0;
		//int thumWidth 	 = 400;
		int thumHeight 	 = 185;
		
		if(imgW > 400 && imgH > 185){
			double stWidth  = (400 / imgW)* 100 ;
			double stHeight = (185 / imgH)* 100 ; 
			
			smallW =  (int)stWidth;
			smallH =  (int)stHeight;
			
			if(smallH > smallW){
				
				//thumWidth  = (int)(imgW * (stWidth/100));
				thumHeight = (int)(imgH * (stWidth/100));
				
			}else{
				//thumWidth  = (int)(imgW * (stHeight/100));
				thumHeight = (int)(imgH * (stHeight/100));
			}
			
		}
		
		return thumHeight;
    }
    
    
    /**
     * 파일 업로드 (콘텐츠 수정)
     * 
     * @param fileMap 			
     * @param attFiledel 			
     * @param i 			
     * @param mptRequest 			
     * @param uploadPath 			
     * @param fileUploadPath 			
     * @return fileMap     
     */
    public static Map<String,String> contentUpdateFile(	  Map<String,String> fileMap 
			    										, String attFiledel
			    										, int i
			    										, MultipartHttpServletRequest mptRequest
			    										, String uploadPath
			    										, String fileUploadPath
			    										)throws Exception{
    	
   
			if(fileMap != null){
				//삭제할 파일이 있는지 검사한다..
				if(attFiledel != null && attFiledel.equals("1")){
					
					String attFileNm 	= fileMap.get("attFile"+i+"Nm")+"";
					String attFilePath  = fileMap.get("attFile"+i+"Path")+"";
					
					
					fileMap.put("attFile"+i+"Nm"   ,"");
					fileMap.put("attFile"+i+"Path" ,"");
					
					// 파일 삭제
					FileMngUtil.removeFile(attFilePath, attFileNm);
				}
			}
			
			//업로드할 파일이 있는지 검사한다..
			MultipartFile mFile = mptRequest.getFile("attFile"+i+"Path");
		
				if (mFile.getSize() > 0) {
		
					String filePath = uploadPath; // + "/"; //+ file.getOriginalFilename();
				FileMngUtil.writeFile(mFile,mFile.getOriginalFilename(),fileUploadPath);
				//mFile.transferTo(new File(filePath));
				
				fileMap.put("attFile"+i+"Nm"   , mFile.getOriginalFilename());
				fileMap.put("attFile"+i+"Path" , filePath);
			}
		
				
		return fileMap;
    }	
    
    /**
     * CONTENT IMAGE 파일 업로드
     * 
     * @param file 					
     * @param fileup 					
     * @return void     
     */
    public static void contetnFileTransferTo(MultipartFile file , String fileup)throws Exception{
    	file.transferTo(new File(fileup));
	}

 
    /**
     * CONTENT IMAGE 파일 업로드
     * 
     * @param file 					
     * @param fileup 					
     * @return void     
     */
    public static File getObjFile(Map<String,String> map , String mapKey){
    	return new File(((String) map.get(mapKey)));
	}
    
    
}