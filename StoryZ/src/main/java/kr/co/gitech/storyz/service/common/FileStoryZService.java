package kr.co.gitech.storyz.service.common;

import java.io.File;

import kr.co.gitech.storyz.dto.common.FileMetadata;
import kr.co.gitech.storyz.dto.user.UserDTO;

import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 저장 서비스 인터페이스
 * 
 * @author 김동택
 */
public interface FileStoryZService {

	public String FILE_SEPARATOR = File.separator;

	public String FILE_TYPE_NORMAL = "file";

	public String FILE_TYPE_IMAGE = "image";

	public String FILE_TYPE_VIDEO = "video";

	public String FILE_TYPE_AUDIO = "audio";

	public String FILE_KIND_IMAGE = "10";

	public String FILE_KIND_VIDEO = "20";

	/**
	 * 프로필 파일 저장 위치
	 */
	public String LOC_PROFILE = "profile";

	/**
	 * 채팅 파일 저장 위치
	 */
	public String LOC_CHAT = "chat";

	/**
	 * 클럽 파일 저장 위치
	 */
	public String LOC_CLUB = "club";

	/**
	 * 이글아이 (Tip) 파일 저장 위치
	 */
	public String LOC_MAP = "map";

	/**
	 * 소셜허브 파일 저장 위치
	 */
	public String LOC_SOCIAL = "social";

	/**
	 * 클럽 프로필 파일 저장 위치
	 */
	public String LOC_CLUB_PROFILE = "club_profile";

	/**
	 * 이미지 혹은 동영상 파일 저장하기 <br/>
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public FileMetadata[] storeFile(MultipartFile[] file) throws Exception;

	/**
	 * 종류에 따라 이미지 저장하기
	 * 
	 * @param group
	 * @param file
	 * @param thumbnailSize
	 * @return
	 * @throws Exception
	 */
	public FileMetadata[] storeImage(String group, MultipartFile[] file, int thumbnailSize) throws Exception;

	/**
	 * 이미지 혹은 동영상 파일 저장하기 <br/>
	 * 
	 * @param srcFile
	 * @return
	 * @throws Exception
	 */
	public FileMetadata storeFile(MultipartFile srcFile) throws Exception;

	/**
	 * 여러 개의 이미지 저장하기
	 * 
	 * @param file
	 * @param thumbnailSize
	 * @return
	 * @throws Exception
	 */
	public FileMetadata[] storeImage(MultipartFile[] file, int thumbnailSize) throws Exception;

	/**
	 * 팁에서 여러 개의 이미지 저장하기
	 * 
	 * @param file
	 * @param thumbnailSize
	 * @return
	 * @throws Exception
	 */
	public FileMetadata[] storeImageTip(MultipartFile[] file, int thumbnailSize) throws Exception;

	/**
	 * 하나의 이미지 저장하기
	 * 
	 * @param srcFile
	 * @param thumbnailSize
	 * @return
	 * @throws Exception
	 */
	public FileMetadata storeImage(MultipartFile srcFile, int thumbnailSize) throws Exception;

	/**
	 * 채팅에서 파일 저장하기
	 * 
	 * @param srcFile
	 * @param thumbnailSize
	 * @return
	 * @throws Exception
	 */
	public FileMetadata storeChatFile(MultipartFile srcFile, int thumbnailSize) throws Exception;

	/**
	 * 클럽 대표 이미지 저장하기
	 * 
	 * @param srcFile
	 * @param thumbnailSize
	 * @return
	 * @throws Exception
	 */
	public FileMetadata storeClubProfile(MultipartFile srcFile, int thumbnailSize) throws Exception;

	/**
	 * 프로필 이미지 저장하기
	 * 
	 * @param srcFile
	 * @param thumbnailSize
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public FileMetadata storeProfile(MultipartFile srcFile, int thumbnailSize, UserDTO user) throws Exception;

	public String getFullPath(String filePath);

}
