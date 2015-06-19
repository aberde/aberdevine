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
package egovframework.grgrowth.common.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import egovframework.grgrowth.common.service.CommonBoardVO;
import egovframework.grgrowth.common.service.CommonCategoryVO;
import egovframework.grgrowth.common.service.FileInfoVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 공통에 관한 데이터처리 매퍼 클래스
 */
@Mapper("commonMapper")
public interface CommonMapper {
    
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
    public void boardInsert(CommonBoardVO vo) throws Exception;

    /**
     * 게시판 수정 요청을 처리하기 위해 데이터처리를 요청한다.
     * 
     * @param vo
     * @throws Exception
     */
    public void boardUpdate(CommonBoardVO vo) throws Exception;
    

    /**
     * 게시판 삭제 요청을 처리하기 위해 데이터처리를 요청한다.
     * 
     * @param vo
     * @throws Exception
     */
    public void boardDelete(CommonBoardVO vo) throws Exception;
}
