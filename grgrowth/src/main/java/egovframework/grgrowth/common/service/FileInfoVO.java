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


/**
 * 파일 정보를 저장하기 위한 VO클래스
 */
public class FileInfoVO {

	/** 첨부파일 일련번호 */
	private int file_seq;

	/** 순번 */
	private int file_sn;
	
	/** 파일저장경로 */
	private String file_stre_cours;
	
	/** 저장파일명 */
	private String stre_file_nm;
	
	/** 원파일명 */
	private String orignl_file_nm;
	
	/** 파일확장자 */
	private String file_extsn;
	
	/** 파일내용 */
	private String file_cn;
	
	/** 파일크기 */
	private long file_size;

    public int getFile_seq() {
        return file_seq;
    }

    public void setFile_seq(int file_seq) {
        this.file_seq = file_seq;
    }

    public int getFile_sn() {
        return file_sn;
    }

    public void setFile_sn(int file_sn) {
        this.file_sn = file_sn;
    }

    public String getFile_stre_cours() {
        return file_stre_cours;
    }

    public void setFile_stre_cours(String file_stre_cours) {
        this.file_stre_cours = file_stre_cours;
    }

    public String getStre_file_nm() {
        return stre_file_nm;
    }

    public void setStre_file_nm(String stre_file_nm) {
        this.stre_file_nm = stre_file_nm;
    }

    public String getOrignl_file_nm() {
        return orignl_file_nm;
    }

    public void setOrignl_file_nm(String orignl_file_nm) {
        this.orignl_file_nm = orignl_file_nm;
    }

    public String getFile_extsn() {
        return file_extsn;
    }

    public void setFile_extsn(String file_extsn) {
        this.file_extsn = file_extsn;
    }

    public String getFile_cn() {
        return file_cn;
    }

    public void setFile_cn(String file_cn) {
        this.file_cn = file_cn;
    }

    public long getFile_size() {
        return file_size;
    }

    public void setFile_size(long file_size) {
        this.file_size = file_size;
    }
	
}