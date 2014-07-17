package kr.co.gitech.storyz.dto.common;

import java.io.File;

/**
 * 파일 정보
 * 
 * @author 김동택
 */
@SuppressWarnings("serial")
public class FileMetadata extends BaseObject {

	/**
	 * 홈 디렉토리
	 */
	private String homeDir = null;

	/**
	 * 파일_종류 <br/>
	 * 이미지 [10], 동영상 [20], 음성 [30]
	 */
	private String file_kind = null;

	/**
	 * 파일_경로
	 */
	private String file_path = null;

	/**
	 * 파일_미리보기_경로
	 */
	private String file_prvw_path = null;

	/**
	 * 파일_명
	 */
	private String file_name = null;

	public FileMetadata() {
	}

	public FileMetadata(String homeDir) {
		this();
		setHomeDir(homeDir);
	}

	/**
	 * @return the homeDir
	 */
	public String getHomeDir() {
		return homeDir;
	}

	/**
	 * @param homeDir
	 *            the homeDir to set
	 */
	public void setHomeDir(String homeDir) {
		this.homeDir = homeDir;
	}

	/**
	 * @return the file_kind 파일_종류 <br/>
	 *         이미지 [10], 동영상 [20], 음성 [30]
	 */
	public String getFile_kind() {
		return file_kind;
	}

	/**
	 * @param file_kind
	 *            the file_kind to set
	 */
	public void setFile_kind(String file_kind) {
		this.file_kind = file_kind;
	}

	/**
	 * @return the file_path
	 */
	public String getFile_path() {
		return file_path;
	}

	/**
	 * @param file_path
	 *            the file_path to set
	 */
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	/**
	 * @return the file_prvw_path
	 */
	public String getFile_prvw_path() {
		return file_prvw_path;
	}

	/**
	 * @param file_prvw_path
	 *            the file_prvw_path to set
	 */
	public void setFile_prvw_path(String file_prvw_path) {
		this.file_prvw_path = file_prvw_path;
	}

	/**
	 * @return the file_name
	 */
	public String getFile_name() {
		return file_name;
	}

	/**
	 * @param file_name
	 *            the file_name to set
	 */
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public void delete() {
		try {
			if (homeDir == null || "".equals(homeDir)) {
				return;
			}
			if (file_name == null || "".equals(file_name)) {
				return;
			}
			File file = new File(homeDir, file_name);
			if (file.isFile() && file.exists()) {
				file.delete();
			}
			if (file_prvw_path == null || "".equals(file_prvw_path))
				return;
			File pFile = new File(homeDir, file_prvw_path);
			if (pFile.isFile() && pFile.exists())
				pFile.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
