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

import egovframework.grgrowth.common.SearchVO;

/**
 * 카테고리 정보를 저장하기 위한 VO클래스
 */
public class CommonCategoryVO extends SearchVO {

	private static final long serialVersionUID = 1L;
	
	/** 카테고리 일련번호 */
	private int category_seq;
	
	/** 카테고리명 */
	private String category_nm;
	
	/** 등록일 */
	private String regdate;

	/** 사용여부 */
	private String use_yn;

    public int getCategory_seq() {
        return category_seq;
    }

    public void setCategory_seq(int category_seq) {
        this.category_seq = category_seq;
    }

    public String getCategory_nm() {
        return category_nm;
    }

    public void setCategory_nm(String category_nm) {
        this.category_nm = category_nm;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getUse_yn() {
        return use_yn;
    }

    public void setUse_yn(String use_yn) {
        this.use_yn = use_yn;
    }

}