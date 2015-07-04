/*
 * Copyright 2011 MOPAS(Ministry of Public Administration and Security).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframework.grgrowth.main.service.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import egovframework.grgrowth.common.GrgrowthConstants;
import egovframework.grgrowth.common.service.CommonBoardVO;
import egovframework.grgrowth.common.service.CommonService;
import egovframework.grgrowth.main.service.MainService;
import egovframework.grgrowth.main.service.WeatherVO;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/**
 * 메인
 */
@Controller
public class MainController {

    @Resource(name = "commonService")
    private CommonService commonService;
    
	@Resource(name = "mainService")
	private MainService mainService;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** 
	 * 메인화면 호출.
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/main/home.do")
	public String home(ModelMap model) throws Exception {
	    // ####################################################################
        // ## 최근목록 5개 가져오기
        // ####################################################################
	    CommonBoardVO vo = new CommonBoardVO();
        vo.setFirstIndex(0);
        vo.setRecordCountPerPage(5);
        // ####################################################################

        // ####################################################################
        // ## 주요소식 목록
        // ####################################################################
        vo.setCategory_seq(2);
        List<CommonBoardVO> boardList = commonService.boardList(vo);
        model.addAttribute("boardList", boardList);
        // ####################################################################
	    
		return "main/home";
	}
	
	/** 
     * 지역날씨 호출.
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/main/weatherTicker.do")
    public String weatherTicker(ModelMap model) throws Exception {
        List<WeatherVO> weatherList = new ArrayList<WeatherVO>();
        
        URL url = new URL(GrgrowthConstants.WEATHER_URL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        InputSource is = new InputSource(br);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder db = factory.newDocumentBuilder();
        
        Document doc = db.parse(is);
        Element root = doc.getDocumentElement();
        
        //결과물 출력 되었는지 체크
        NodeList locals = root.getElementsByTagName("local");
        
        //결과물이 있을 경우
        if ( locals.getLength() > 0 ) {
            String nodeValue = null;
         
            for ( int i = 0; i < locals.getLength(); i++ ) {
                Element n = (Element)locals.item(i);
          
                nodeValue = n.getFirstChild().getNodeValue();
          
                WeatherVO vo = new WeatherVO();
                vo.setLocal(nodeValue);
                vo.setStn_id(n.getAttribute("stn_id"));
                vo.setIcon(n.getAttribute("icon"));
                vo.setDesc(n.getAttribute("desc"));
                vo.setTa(n.getAttribute("ta"));
                vo.setRn_hr1(n.getAttribute("rn_hr1"));

                weatherList.add(vo);
            }
        }
        
        model.addAttribute("weatherList", weatherList);
        
        return "main/weatherTicker";
    }
    
    /** 
     * 검색
     * @param vo
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/main/search.do")
    public String search(@ModelAttribute("vo") CommonBoardVO vo, ModelMap model) throws Exception {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        
        // 검색구분
        model.addAttribute("search_section", GrgrowthConstants.SEARCH_SECTION);
        
        if ( vo.getSearchCondition() == null || vo.getSearchCondition().isEmpty() ) {  // 검색구분이 없을때
            CommonBoardVO commonBoardVO = new CommonBoardVO();
            commonBoardVO.setSearchKeyword(vo.getSearchKeyword());
            
            for ( int i = 0; i < GrgrowthConstants.SEARCH_SECTION.size(); i++ ) {
                // 검색구분입력
                commonBoardVO.setSearchCondition((String)GrgrowthConstants.SEARCH_SECTION.keySet().toArray()[i]);
                
                // ####################################################################
                // ## 최근목록 5개 가져오기
                // ####################################################################
                commonBoardVO.setFirstIndex(0);
                commonBoardVO.setRecordCountPerPage(5);
                // ####################################################################
                
                // ####################################################################
                // ## 주요소식 목록
                // ####################################################################
                List<CommonBoardVO> searchList = mainService.searchList(commonBoardVO);
                // ####################################################################
                
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("searchCondition", (String)GrgrowthConstants.SEARCH_SECTION.keySet().toArray()[i]);
                map.put("searchList", searchList);
                
                list.add(map);
            }
            
            model.addAttribute("list", list);
        } else {
            // ####################################################################
            // ## 페이징 설정
            // ####################################################################
            // currentPageNo : 현재 페이지 번호
            // recordCountPerPage : 한 페이지당 게시되는 게시물 건 수 (=pageUnit)
            // pageSize : 페이지 리스트에 게시되는 페이지 건수
            // totalRecordCount : 전체 게시물 건 수
            // pageUnit과 pageSize는 context-properties.xml에서 설정
            vo.setPageUnit(GrgrowthConstants.SEARCH_PAGE_UNIT);
            vo.setPageSize(propertiesService.getInt("pageSize"));

            PaginationInfo paginationInfo = new PaginationInfo();

            paginationInfo.setCurrentPageNo(vo.getPageIndex());
            paginationInfo.setRecordCountPerPage(vo.getPageUnit());
            paginationInfo.setPageSize(vo.getPageSize());

            vo.setFirstIndex(paginationInfo.getFirstRecordIndex());
            vo.setLastIndex(paginationInfo.getLastRecordIndex());
            vo.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
            // ####################################################################

            // ####################################################################
            // ## 게시판 페이징
            // ####################################################################
            int totCnt = mainService.searchListTotCnt(vo);
            paginationInfo.setTotalRecordCount(totCnt);

            model.addAttribute("paginationInfo", paginationInfo);
            // ####################################################################

            // ####################################################################
            // ## 게시판 목록
            // ####################################################################
            List<CommonBoardVO> searchList = mainService.searchList(vo);
            // ####################################################################
            
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("searchCondition", vo.getSearchCondition());
            map.put("searchList", searchList);
            
            list.add(map);
            
            model.addAttribute("list", list);
        }
        
        
        return "/main/search";
    }
}
