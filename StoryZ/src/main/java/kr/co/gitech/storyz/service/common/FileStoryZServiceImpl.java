package kr.co.gitech.storyz.service.common;

import java.io.File;
import java.security.MessageDigest;
import java.util.UUID;

import javax.annotation.PostConstruct;

import kr.co.gitech.storyz.common.message.errorcode.ErrorCode;
import kr.co.gitech.storyz.common.util.DateTimeUtil;
import kr.co.gitech.storyz.common.util.ImageFileUtil;
import kr.co.gitech.storyz.dto.common.FileMetadata;
import kr.co.gitech.storyz.dto.user.UserDTO;
import kr.co.gitech.storyz.exception.StoryZException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 저장 서비스 <br/>
 * 
 * @author 김동택
 */
public class FileStoryZServiceImpl implements ApplicationContextAware, FileStoryZService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ApplicationContext context = null;

	private String fileHomeDir;

	private String[] imageFileExtensions;

	private String[] videoFileExtensions;

	@PostConstruct
	public void initialize() {
		makeDirectory(fileHomeDir, null);
		if (context == null) {
			logger.warn("application context is null");
		}
	}

	/**
	 * 종류에 따라 이미지 저장하기
	 * 
	 * @param group
	 * @param file
	 * @param thumbnailSize
	 * @return
	 * @throws Exception
	 */
	public FileMetadata[] storeImage(String group, MultipartFile[] file, int thumbnailSize) throws Exception {
		Assert.notNull(file, "File must not be null");

		FileMetadata[] metadata = new FileMetadata[file.length];
		for (int i = 0, n = file.length; i < n; i++) {
			FileMetadata fileMetadata = null;
			if (group == null || "".equals(group))
				fileMetadata = storeFile(LOC_CLUB, FILE_TYPE_IMAGE, file[i], thumbnailSize);
			else
				fileMetadata = storeFile(group, FILE_TYPE_IMAGE, file[i], thumbnailSize);
			metadata[i] = fileMetadata;
		}
		return metadata;
	}

	/**
	 * 클럽의 글 쓰기에 사용
	 * 
	 * @param file
	 * @param thumbnailSize
	 * @return
	 * @throws Exception
	 */
	public FileMetadata[] storeImage(MultipartFile[] file, int thumbnailSize) throws Exception {
		return storeImage(LOC_CLUB, file, thumbnailSize);
	}

	/**
	 * 이글아이 (Tip)에 글 쓰기할 때 사용
	 * 
	 * @see kr.co.hsnc.sns.common.service.FileStoreService#storeImageFromTip(org.springframework.web.multipart.MultipartFile[], int)
	 */
	public FileMetadata[] storeImageTip(MultipartFile[] file, int thumbnailSize) throws Exception {
		return storeImage(LOC_MAP, file, thumbnailSize);
	}

	/**
	 * 클럽 이미지 <br/>
	 * 
	 * @see kr.co.hsnc.sns.common.service.FileStoreService#storeImage(org.springframework.web.multipart.MultipartFile, int)
	 */
	@Override
	public FileMetadata storeImage(MultipartFile srcFile, int thumbnailSize) throws Exception {
		return storeFile(LOC_CLUB, FILE_TYPE_IMAGE, srcFile, thumbnailSize);
	}

	@Override
	public FileMetadata storeChatFile(MultipartFile srcFile, int thumbnailSize) throws Exception {
		return storeFile(LOC_CHAT, FILE_TYPE_NORMAL, srcFile, thumbnailSize);
	}

	/**
	 * 프로필 이미지
	 * 
	 * @see kr.co.hsnc.sns.common.service.FileStoreService#storeProfile(org.springframework.web.multipart.MultipartFile, int, TbUser)
	 */
	@Override
	public FileMetadata storeProfile(MultipartFile srcFile, int thumbnailSize, UserDTO user) throws Exception {
		// TODO 프로필 파일의 이미지 이름을 변경되지 않게 common 폴더 이하에 위치
//		return storeProfileFile(LOC_PROFILE, FILE_TYPE_IMAGE, srcFile, thumbnailSize, user);
		return storeFile(LOC_PROFILE, FILE_TYPE_IMAGE, srcFile, thumbnailSize);
	}

	@Override
	public FileMetadata storeClubProfile(MultipartFile srcFile, int thumbnailSize) throws Exception {
		return storeFile(LOC_CLUB_PROFILE, FILE_TYPE_IMAGE, srcFile, thumbnailSize);
	}

	@Override
	public FileMetadata[] storeFile(MultipartFile[] file) throws Exception {
		Assert.notNull(file, "File must not be null");
		FileMetadata[] metadata = new FileMetadata[file.length];
		for (int i = 0, n = file.length; i < n; i++) {
			FileMetadata fileMetadata = storeFile(file[i]);
			metadata[i] = fileMetadata;
		}
		return metadata;
	}

	@Override
	public FileMetadata storeFile(MultipartFile srcFile) throws Exception {
		return storeFile(null, FILE_TYPE_NORMAL, srcFile, 0);
	}

	/**
	 * 파일 확장자 및 종류에 따라 이미지 저장
	 * 
	 * @param group
	 * @param type
	 * @param srcFile
	 * @param thumbnailSize
	 * @return
	 * @throws Exception
	 */
	public FileMetadata storeFile(String group, String type, MultipartFile srcFile, int thumbnailSize) throws Exception {
		Assert.notNull(srcFile, "File must not be null");

		FileMetadata metadata = new FileMetadata(fileHomeDir);
		try {
			String filename = srcFile.getOriginalFilename();
			String ext = FilenameUtils.getExtension(filename);
			String file_kind = lookupFileType(ext);
			metadata.setFile_kind(file_kind);
			metadata.setFile_name(filename);

			if (FILE_KIND_IMAGE.equals(file_kind)) {
				type = FILE_TYPE_IMAGE;
			} else {
				type = FILE_TYPE_NORMAL;
			}
			String baseDir = (group == null ? "" : group + FILE_SEPARATOR) + getTodayWithDirName() + FILE_SEPARATOR + type;
			String destDir = makeDirectory(fileHomeDir, baseDir);
			// 파일 이름 변경
			String destFilename = uuid() + "." + ext;
			String file_path = FilenameUtils.separatorsToUnix(baseDir + FILE_SEPARATOR + destFilename);
			String absoluteFilePath = destDir + FILE_SEPARATOR + destFilename;
			File destFile = new File(absoluteFilePath);
			logger.debug("destFilename <{}>, destFile <{}>", destFilename, (destFile != null ? destFile.getAbsolutePath() : ""));
			// 실제 저장 위치로 파일 이동
			srcFile.transferTo(destFile);

			metadata.setFile_path(file_path);
			if (FILE_KIND_IMAGE.equals(file_kind) && thumbnailSize > 0) {
				String previewPath = makeThumbnailImage(thumbnailSize, absoluteFilePath, baseDir);
				metadata.setFile_prvw_path(previewPath);
			}
			logger.debug("image <{}>, thumbnail <{}>", metadata.getFile_path(), metadata.getFile_prvw_path());
		} catch (Exception e) {
			logger.error("Error: {}", e.getMessage(), e);
			// 예외 발생시 저장된 파일 삭제
			if (metadata != null)
				metadata.delete();
			throw new StoryZException(ErrorCode.FILE_UPLOAD_FAILED, e.getMessage());
		}
		return metadata;
	}

	/**
	 * @param group
	 * @param type
	 * @param srcFile
	 * @param thumbnailSize
	 * @return
	 * @throws Exception
	 */
	public FileMetadata storeProfileFile(String group, String type, MultipartFile srcFile, int thumbnailSize, UserDTO user) throws Exception {
		Assert.notNull(srcFile, "File must not be null");

		FileMetadata metadata = new FileMetadata(fileHomeDir);
		try {
			String filename = srcFile.getOriginalFilename();
			String ext = FilenameUtils.getExtension(filename);
			String file_kind = lookupFileType(ext);
			metadata.setFile_kind(file_kind);
			metadata.setFile_name(filename);

			if (FILE_KIND_IMAGE.equals(file_kind)) {
				type = FILE_TYPE_IMAGE;
			} else {
				type = FILE_TYPE_NORMAL;
			}

			// String baseDir = (group == null ? "" : group + FILE_SEPARATOR) + (user == null || user.getCompany_id() == null ? "common" : user.getCompany_id()) + FILE_SEPARATOR +
			// type;
			String baseDir = (group == null ? "" : group + FILE_SEPARATOR) + "common" + FILE_SEPARATOR + type;
			String destDir = makeDirectory(fileHomeDir, baseDir);

			// 파일 이름 변경
			String destFilenameSubfix = null;
			try {
				String employeeNo = null;
				if (null == user) {
					employeeNo = FILE_TYPE_IMAGE;
				} else {
					employeeNo = user.getEmployee_no();
					if (null == employeeNo || "".equals(employeeNo)) {
						employeeNo = FILE_TYPE_IMAGE;
					}
				}
				byte[] data = (employeeNo).getBytes();
				MessageDigest md = MessageDigest.getInstance("SHA1");
				md.update(data);
				destFilenameSubfix = new String(Hex.encodeHex(md.digest()));
			} finally {
				if (null == destFilenameSubfix || "".equals(destFilenameSubfix)) {
					destFilenameSubfix = FILE_TYPE_IMAGE;
				}
			}

			String destFilename = new StringBuilder().append(user.getUser_key()).append(destFilenameSubfix).append(".").append(ext).toString();
			String file_path = FilenameUtils.separatorsToUnix(baseDir + FILE_SEPARATOR + destFilename);
			String absoluteFilePath = destDir + FILE_SEPARATOR + destFilename;
			File destFile = new File(absoluteFilePath);
			logger.debug("destFilename <{}>, destFile <{}>", destFilename, (destFile != null ? destFile.getAbsolutePath() : ""));
			// 실제 저장 위치로 파일 이동
			srcFile.transferTo(destFile);

			metadata.setFile_path(file_path);
			if (FILE_KIND_IMAGE.equals(file_kind) && thumbnailSize > 0) {
				String previewPath = makeThumbnailImage(thumbnailSize, absoluteFilePath, baseDir, true);
				metadata.setFile_prvw_path(previewPath);
			}
			logger.debug("image <{}>, thumbnail <{}>", metadata.getFile_path(), metadata.getFile_prvw_path());
		} catch (Exception e) {
			logger.error("Error: {}", e.getMessage(), e);
			// 예외 발생시 저장된 파일 삭제
			if (metadata != null)
				metadata.delete();
			throw new StoryZException(ErrorCode.FILE_UPLOAD_FAILED, e.getMessage());
		}
		return metadata;
	}

	private String lookupFileType(String ext) throws Exception {
		String fileType = null;
		// 파일 확장자 체크
		String strExt = ext == null ? "" : ext.toLowerCase();
		if (ArrayUtils.contains(imageFileExtensions, strExt)) {
			fileType = FILE_KIND_IMAGE;
		} else if (ArrayUtils.contains(videoFileExtensions, strExt)) {
			fileType = FILE_KIND_VIDEO;
		} else {
			throw new StoryZException(ErrorCode.FILE_UPLOAD_UNSUPPORTED_FILE_TYPE, new String[] { ext }, "지원하지 않는 파일 타입 [{0}] 입니다. ");
		}
		return fileType;
	}

	private String makeThumbnailImage(int thumbnailSize, String absoluteFilePath, String baseDir) throws Exception {
		return makeThumbnailImage(thumbnailSize, absoluteFilePath, baseDir, false);
	}

	private String makeThumbnailImage(int thumbnailSize, String absoluteFilePath, String baseDir, boolean forced) throws Exception {
		// 썸네일 이미지 경로
		String previewPath = ImageFileUtil.makeThumbnailImage(thumbnailSize, absoluteFilePath, String.valueOf(thumbnailSize), forced);
		previewPath = FilenameUtils.separatorsToUnix(previewPath);
		// 루트 경로를 제외한 썸네일 파일이 저장된 위치
		String path = "/".equals(FILE_SEPARATOR) ? fileHomeDir : new File(fileHomeDir).getAbsolutePath();
		previewPath = StringUtils.removeStart(previewPath, FilenameUtils.separatorsToUnix(path + FILE_SEPARATOR));
		return previewPath;
	}

	@Override
	public String getFullPath(String filePath) {
		if (StringUtils.isEmpty(filePath))
			return "";

		filePath = FilenameUtils.separatorsToSystem(filePath);
		final String uploadHomeDir = FilenameUtils.separatorsToSystem(fileHomeDir) + FILE_SEPARATOR;
		if (!filePath.startsWith(uploadHomeDir)) {
			filePath = uploadHomeDir + filePath;
		}

		logger.debug("uploadHomeDir <{}>, filePath <{}>", uploadHomeDir, filePath);
		return filePath;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	/**
	 * @param fileHomeDir
	 *            the fileHomeDir to set
	 */
	public void setFileHomeDir(String fileHomeDir) {
		this.fileHomeDir = fileHomeDir;
	}

	/**
	 * @param imageFileExtensions
	 *            the imageFileExtensions to set
	 */
	public void setImageFileExtensions(String[] imageFileExtensions) {
		this.imageFileExtensions = imageFileExtensions;
	}

	/**
	 * @param videoFileExtensions
	 *            the videoFileExtensions to set
	 */
	public void setVideoFileExtensions(String[] videoFileExtensions) {
		this.videoFileExtensions = videoFileExtensions;
	}

	private String getTodayWithDirName() {
		return DateTimeUtil.getDateToString(null, DateTimeUtil.DIRECTORY_DATE_FORMAT);
	}

	private String makeDirectory(String home, String path) {
		String b = null;
		try {
			// make home directory
			File file = new File(home, path == null ? "." : path);
			if (!file.isDirectory())
				file.mkdirs();
			b = file.getAbsolutePath();
		} catch (Exception e) {
			logger.error("Error: {}", e.getMessage(), e);
		}
		return b;
	}

	public static final String uuid() {
		UUID uuid = UUID.randomUUID();
		long msb = uuid.getMostSignificantBits();
		long lsb = uuid.getLeastSignificantBits();
		byte[] buffer = new byte[16];
		for (int i = 0; i < 8; i++) {
			buffer[i] = (byte) (msb >>> 8 * (7 - i));
		}
		for (int i = 8; i < 16; i++) {
			buffer[i] = (byte) (lsb >>> 8 * (7 - i));
		}
		return new String(Hex.encodeHex(buffer));
	}

}
