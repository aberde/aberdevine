package com.aps.rarp.co.util;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

//import egovframework.rte.fdl.property.EgovPropertyService;



/**
 *
 * Class Name  : EfrosVOUtil.java
 * Description : VO 관련 유틸 클래스
 * 
 * @author: SDS
 * @version: 1.0
 * @since: 2012. 5. 30.
 */
@Service("voUtilService")
public class VOUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    //@Resource(name = "egovPropertiesService")
    //private EgovPropertyService propertiesService;

    // LOGGER 설정
    protected static final Logger LOGGER = Logger.getLogger(VOUtil.class);

    /**
     * @Method Name  : fromRequestToVO
     * @param request
     * @param response
     * @param targetVO
     */
    public void fromRequestToVO(HttpServletRequest request,HttpServletResponse response, Object targetVO)
    {

//        //1. Initech 송수신 암호화 초기화
//        String propertiesFullPath = propertiesService.getString("initechFullPath");
//        
//        IniPlugin mIP = new IniPlugin(request,response,propertiesFullPath);
//        try{
//            mIP.init();
//        }catch(Exception e){
//            LOGGER.debug(e); //LOGGER.debug(e); //e.printStackTrace();
//        }
//
//        //2. Request to VO 처리
//        Field[] allFields=targetVO.getClass().getDeclaredFields();
//        try
//        {
//            String fieldName = null;
//            for(Field field : allFields)
//            {
//            	fieldName = field.getName();
//                if(mIP.getParameter(fieldName) != null)
//                {
//                	field.setAccessible(true);
//                	field.set(targetVO, mIP.getParameter(fieldName));
//                }
//            } 
//        } 
//        catch(Exception e)
//        {
//            LOGGER.error(e); //e.printStackTrace();
//        }
    }


    /**
     * 해당 VO에 정의된 method들을 인식되는 순서대로 화면에 출력해준다.
     * debugging용으로, method들이 의도하는 순서대로 다 인식의 되어 있는지 확인한다.
     *  
     * @param sourceVO    확인할 대상 VO
     */
    public void showDeclaredMethods(Object sourceVO)
    {
        try
        {
            Method[] allMethods=sourceVO.getClass().getDeclaredMethods();

            LOGGER.debug(sourceVO.getClass().getName()+"  클래스의 인식된 메소드 리스트  ");
            for (int i=0;i<allMethods.length;i++)
            {
                LOGGER.debug(i+":"+allMethods[i].getName());
            }
        } 
        catch(Exception e)
        {
            LOGGER.error(e); //e.printStackTrace();
        }
    }

    /**
     * 해당 VO에 정의된 field들을 인식되는 순서대로 화면에 출력해준다.
     * debugging용으로, field들이 의도하는 순서대로 다 인식의 되어 있는지 확인한다.
     * 
     * @param sourceVO    확인할 대상 VO
     */
    public void showDeclaredFields(Object sourceVO)
    {
        try
        {

            Field[] allFields=sourceVO.getClass().getDeclaredFields();

            LOGGER.debug(sourceVO.getClass().getName()+"  클래스의 인식된 메소드 리스트  ");
            for (int i=0;i<allFields.length;i++)
            {
                LOGGER.debug(i+":"+allFields[i].getName());
            }
        } 
        catch(Exception e)
        {
            LOGGER.error(e); //e.printStackTrace();
        }
    }

    /**
     * Collection 객체에 담긴 값들을 Collection VO에 넣어준다.
     * 
     * @param Collection 값을 추출할 대상
     * @param targetVO   request에서 추출된 값을 넣을 VO
     * @return Collection 추출된 값이 담긴 targetVO의 집합 
     */
    @SuppressWarnings("unchecked")
    public static Collection fromCollectionToColVO(Collection col, Object targetVO)
    {        
        Field[] allFields=targetVO.getClass().getDeclaredFields();        
        Collection rtn = null;        
        try
        {
            String fieldName = null;
            String tempName = null; 
            Class  fieldType = null;           
            String value = null;

            ArrayList al = new ArrayList();
            if(col != null)
            {
                Iterator it = (Iterator)col.iterator();

                while(it.hasNext())
                {
                    HashMap hm = (HashMap)it.next();
                    /*targetVO new 로생성*/                
                    Constructor con = targetVO.getClass().getConstructor();
                    Object tempVO = con.newInstance();

                    for(Field field : allFields)
                    {
                        fieldName = (String) field.getName();                        

                        if((hm.get(splitUpperIns(fieldName,"_"))) instanceof java.lang.String)
                        {                            
                            tempName = (String)hm.get(splitUpperIns(fieldName,"_"));
                        }
                        else if((hm.get(splitUpperIns(fieldName,"_"))) instanceof java.math.BigDecimal)
                        {                            
                            java.math.BigDecimal bd =(BigDecimal)hm.get(splitUpperIns(fieldName,"_"));                            
                            tempName = bd.toString();
                        }
                        else
                        {
                            tempName = (String)hm.get(splitUpperIns(fieldName,"_"));                            
                        }

                        fieldType     = tempVO.getClass().getDeclaredField(fieldName).getType();

                        //파일 type String 만 체크
                        if(fieldType.getName().equals("java.lang.String"))
                        {
                        	field.setAccessible(true);
                            value         = (tempName == null) ? "" : tempName;
                            value = value.trim();
                            field.set(tempVO, value);
                        }                    
                    }                 
                    al.add(tempVO);            
                }
            }

            rtn = (Collection)al;
            //return rtn;

        } 
        catch(Exception e)
        {
            LOGGER.error(e); //e.printStackTrace();
            Collection tmp = null;
            rtn = tmp;
        }
        
        return rtn;
    }

    /**
     *  소문자를 대문자로 변경후 특정문자를 앞에 삽입한다.
     * 
     * @param String   값 
     * @param String   특정문자
     * @return 변경 후 값 
     */
    public static String splitUpperIns(String str, String separator) 
    {       

        StringBuilder value = new StringBuilder();
        char[] temp = str.toCharArray();//스트링을 캐릭터 배열로 형변환
        for(int i=0; i<str.length(); i++) 
        {           
            // 각 인덱스의 문자가 대문자라면 소문자로 변환
            if(Character.isUpperCase(temp[i]))
            {                
                value.append("_").append(temp[i]);
            }
            else if(Character.isLowerCase(temp[i]))
            {
                value.append(Character.toUpperCase(temp[i]));
            }
            else
            {
                value.append(Character.toUpperCase(temp[i]));
            }
        }        

        return value.toString();        
    }

    /**
     *  특정문자를 제거후 뒤메 문자를 대문자로 변환한다.
     * 
     * @param String   값 
     * @param String   특정문자
     * @return String  변환된 값
     */
    public static String splitUpperDel(String str, String separator) 
    {
        StringBuffer value = new StringBuffer();
        char[] temp = str.toCharArray();//스트링을 캐릭터 배열로 형변환
        char[] sepa    = separator.toCharArray();
        for(int i=0; i<str.length(); i++) 
        {           
            // 각 인덱스의 문자가 소문자라면 대문자로 변환            
            if(temp[i]== sepa[0])
            {
                value.append(Character.toLowerCase(temp[i+1]));
                i++;
            }
            else 
            {
                value.append(temp[i]);
            }

        }
        return value.toString();     
    }


    /**
     *  특정 VO 의 필드이름을 이용하여 Map에 담는다.
     * 
     * @param Object 대상 VO 객체
     * @return Map  매핑된 Map 객체
     */
    public Map<String, Object> fromVOToMap(Object targetVO){
        
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        Field[] allFields=targetVO.getClass().getDeclaredFields();
        Object value = null;
        
        try{
           for(Field field : allFields){
                field.setAccessible(true);
                value = field.get(targetVO);

                if(value != null){
                    rtnMap.put(field.getName(), value);
                }
            }
        }catch(Exception e){

            LOGGER.error(e);
        }
        return rtnMap;

    }

}
