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
package egovframework.grgrowth.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;


/**
 * 공통 처리하는 비즈니스 인터페이스
 */
public interface CommonService {
    
    /**
     * 카테고리 목록조회 요청을 처리하기 위해 데이터처리를 요청한다.
     * 
     * @return List<CommonCategoryVO>
     * @throws Exception
     */
    public List<CommonCategoryVO> categoryList() throws Exception;
    
    /**
     * 카테고리 상세조회 요청을 처리하기 위해 데이터처리를 요청한다.
     * 
     * @param vo
     * @return CommonCategoryVO
     * @throws Exception
     */
    public CommonCategoryVO categoryView(CommonCategoryVO vo) throws Exception;

    /**
     * 게시판 목록조회 요청을 처리하기 위해 데이터처리를 요청한다.
     * 
     * @param vo
     * @return List
     * @throws Exception
     */
    public List<CommonBoardVO> boardList(CommonBoardVO vo) throws Exception;
    
    /**
     * 게시판 총 레코드 수 조회 요청을 처리하기 위해 데이터처리를 요청한다.
     * 
     * @param vo
     * @return int
     * @throws Exception
     */
    public int boardListTotCnt(CommonBoardVO vo) throws Exception;
    
    /**
     * 게시판 상세조회 요청을 처리하기 위해 데이터처리를 요청한다.
     * 
     * @param vo
     * @return CommonBoardVO
     * @throws Exception
     */
    public CommonBoardVO boardView(CommonBoardVO vo) throws Exception;

    /**
     * 첨부파일 상세조회 요청을 처리하기 위해 데이터처리를 요청한다.
     * 
     * @param vo
     * @return FileInfoVO
     * @throws Exception
     */
    public FileInfoVO fileInfoView(FileInfoVO vo) throws Exception;
    
    /**
     * 게시판 입력 요청을 처리하기 위해 데이터처리를 요청한다.
     * 
     * @param vo
     * @throws Exception
     */
    @Transactional
    public void boardInsert(CommonBoardVO vo) throws Exception;

    /**
     * 게시판 수정 요청을 처리하기 위해 데이터처리를 요청한다.
     * 
     * @param vo
     * @throws Exception
     */
    @Transactional
    public void boardUpdate(CommonBoardVO vo) throws Exception;
    
    /**
     * 게시판 삭제 요청을 처리하기 위해 데이터처리를 요청한다.
     * 
     * @param vo
     * @throws Exception
     */
    @Transactional
    public void boardDelete(CommonBoardVO vo) throws Exception;
    
    /**
     * 파일정보 입력 요청을 처리하기 위해 데이터처리를 요청한다.
     * @param dataMap
     * @return
     * @throws Exception
     */
    @Transactional
    public int fileInfoInsert(Map<String, Object> dataMap) throws Exception;

    /**
     * 파일 입력 요청을 처리하기 위해 데이터처리를 요청한다.
     * 
     * @param fileInfoList
     * @throws Exception
     */
    @Transactional
    public void fileInsert(List<FileInfoVO> fileInfoList) throws Exception;
    
    /**
     * 파일 삭제 요청을 처리하기 위해 데이터처리를 요청한다.
     * 
     * @param mapData
     *   -- file_seq 파일 일련번호
     *   -- file_sn 파일 순번
     * @throws Exception
     */
    @Transactional
    public void fileDelete(Map<String, String> mapData) throws Exception;
}
