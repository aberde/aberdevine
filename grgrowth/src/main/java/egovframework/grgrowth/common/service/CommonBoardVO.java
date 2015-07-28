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

import org.springframework.web.multipart.MultipartFile;

import egovframework.grgrowth.common.SearchVO;
import egovframework.grgrowth.common.Util;

/**
 * 게시판 정보를 저장하기 위한 VO클래스
 */
public class CommonBoardVO extends SearchVO {

	private static final long serialVersionUID = 1L;
	
	/** 게시판 일련번호 */
	private int board_seq;
	
	/** 제목 */
	private String title;
	
	/** 내용 */
	private String content;
	
	/** 카테고리 일련번호 */
	private int category_seq;

	/** 카테고리명 */
	private String category_nm;
	
	/** 등록일 */
	private String regdate;

	/** 첨부파일 일련번호 */
	private int file_seq;
	
	/** 첨부파일 목록 */
	private List<FileInfoVO> fileInfoList;
	
	/** 업로드 파일 정보 */
	private List<MultipartFile> uploadFileInfo;
	
	/** 보안임시키 */
	private String tmpSecureID;

    public int getBoard_seq() {
        return board_seq;
    }

    public void setBoard_seq(int board_seq) {
        this.board_seq = board_seq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public int getFile_seq() {
        return file_seq;
    }

    public void setFile_seq(int file_seq) {
        this.file_seq = file_seq;
    }

    public List<FileInfoVO> getFileInfoList() {
        return fileInfoList;
    }

    public void setFileInfoList(List<FileInfoVO> fileInfoList) {
        this.fileInfoList = fileInfoList;
    }

    public List<MultipartFile> getUploadFileInfo() {
        return uploadFileInfo;
    }

    public void setUploadFileInfo(List<MultipartFile> uploadFileInfo) {
        this.uploadFileInfo = uploadFileInfo;
    }

    public String getTmpSecureID() {
        return tmpSecureID = Util.getDatePattern("yyyyMMddHHmmssSSS");
    }

    public void setTmpSecureID(String tmpSecureID) {
        this.tmpSecureID = tmpSecureID;
    }
    
}