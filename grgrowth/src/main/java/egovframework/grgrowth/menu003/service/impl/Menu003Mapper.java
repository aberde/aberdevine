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
package egovframework.grgrowth.menu003.service.impl;

import java.util.List;

import egovframework.grgrowth.common.service.CommonBoardVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

/**
 * 정보에 관한 데이터처리 매퍼 클래스
 */
@Mapper("menu003Mapper")
public interface Menu003Mapper {
    
    /**
     * 용어사전 목록조회 요청을 처리하기 위해 데이터처리를 요청한다.
     * 
     * @param vo
     * @return List
     * @throws Exception
     */
    public List<CommonBoardVO> dicList(CommonBoardVO vo) throws Exception;
    
    /**
     * 용어사전 총 레코드 수 조회 요청을 처리하기 위해 데이터처리를 요청한다.
     * 
     * @param vo
     * @return int
     * @throws Exception
     */
    public int dicListTotCnt(CommonBoardVO vo) throws Exception;
}
