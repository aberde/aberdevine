package kr.co.gitech.storyz.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import kr.co.gitech.storyz.common.message.errorcode.ErrorCode;
import kr.co.gitech.storyz.exception.StoryZException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 썸네일 이미지 저장 유틸
 */
public class ImageFileUtil {

	public static final String IMAGE_DEFAULT_EXT = "jpg";

	public static final int THUMBNAIL_SIZE_DEFAULT = 93;

	public static final int THUMBNAIL_SIZE_DETAIL = 292;

	/**
	 * 해당 경로에 파일이 있는지 여부를 판단해 있을 경우 파일이름에 (idx)를 반환한다
	 * 
	 * @param absoluteFilePath
	 *            c:/documents/images/good.jpg
	 * @return c:/documents/images/good_1.jpg
	 */
	public static final String renameImage(String absoluteFilePath) {
		if (absoluteFilePath == null)
			return "";

		String name = null;
		String extension = null;
		if (absoluteFilePath.indexOf(".") != -1) {
			name = absoluteFilePath.substring(0, absoluteFilePath.indexOf("."));
			extension = FilenameUtils.getExtension(absoluteFilePath);
		} else {
			name = absoluteFilePath;
		}

		File file = new File(absoluteFilePath);
		if (!file.exists())
			return absoluteFilePath;

		int idx = 1;
		String rename = null;
		while (file.exists()) {
			rename = name + "_" + idx++ + "." + extension;
			file = new File(rename);
		}

		return rename;
	}

	public static final String rename(String filePath, String fileName) {
		if (filePath.lastIndexOf(File.separator) == filePath.length() - 1)
			return renameImage(filePath + fileName);
		else
			return renameImage(filePath + File.separator + fileName);
	}

	/**
	 * 썸네일 이미지를 만들고 만들어진 이미지 파일명을 반환한다.
	 * 
	 * @param size
	 *            썸네일 이미지 사이즈
	 * @param srcDir
	 * @param filename
	 * @param imageSize
	 * @throws IOException
	 */
	public static final String makeThumbnailImage(int size, String srcDir, String filename, String imageSize, boolean forced) throws Exception {
		if (filename == null || filename.indexOf(".") == -1)
			return "";
		if (imageSize == null || "".equals(imageSize))
			imageSize = String.valueOf(THUMBNAIL_SIZE_DEFAULT);

		// 원본파일 명, 확장자
		String name = filename.substring(0, filename.indexOf("."));
		String extension = filename.substring(filename.indexOf("."), filename.length());
		File orgFile = new File(srcDir + File.separator + filename);
		if (!orgFile.exists())
			throw new FileNotFoundException("File <" + filename + "> is not found");

		EasyImage orgImage = new EasyImage(orgFile);
		if (!orgImage.isSuppoprtedImageFormat())
			throw new StoryZException(ErrorCode.FILE_UPLOAD_UNSUPPORTED_FILE_TYPE);

		double originalWidth = orgImage.getWidth();
		double originalHeight = orgImage.getHeight();
		double thumbnailWidth = size;
		double thumbnailHeight = size;

		EasyImage thumbnailImage = null;
		if (size == THUMBNAIL_SIZE_DETAIL) {
			if (originalWidth > originalHeight) {
				thumbnailHeight = originalHeight * size / originalWidth;
				thumbnailWidth = size;
			} else {
				thumbnailWidth = originalWidth * size / originalHeight;
				thumbnailHeight = size;
			}

			thumbnailImage = orgImage.resize((int) thumbnailWidth, (int) thumbnailHeight);
		} else {
			int x = 0, y = 0;
			if (originalWidth > originalHeight) {
				thumbnailWidth = Math.floor((originalWidth * size) / originalHeight);
				x = (int) Math.floor((thumbnailWidth - size) / 2);
			} else {
				thumbnailHeight = Math.floor((originalHeight * size) / originalWidth);
				y = (int) Math.floor((thumbnailHeight - size) / 2);
			}

			thumbnailImage = orgImage.resize((int) thumbnailWidth, (int) thumbnailHeight);
			thumbnailImage = thumbnailImage.crop(x, y, size, size);
		}

		StringBuilder destFilename = new StringBuilder(srcDir);
		destFilename.append(File.separator);
		destFilename.append(imageSize);
		destFilename.append(File.separator);

		File destDir = new File(destFilename.toString());
		if (!destDir.exists())
			destDir.mkdirs();

		destFilename.append(name);
		destFilename.append(extension);

		File thumbnailFile = new File(destFilename.toString());
		if (thumbnailFile.exists()) {
			thumbnailFile.delete();
		}
		if (thumbnailFile.exists()) {
			destFilename = new StringBuilder(renameImage(destFilename.toString()));
			thumbnailFile = new File(destFilename.toString());
		}

		FileOutputStream out = new FileOutputStream(thumbnailFile);
		try {
			thumbnailImage.writeTo(out, IMAGE_DEFAULT_EXT);
		} catch (IOException e) {
			throw e;
		} finally {
			out.close();
		}
		return thumbnailFile.toString();
	}

	public static final String makeThumbnailImage(int size, String fileFullPath, String thumbnailPath, boolean forced) throws Exception {
		if (size == 0 || fileFullPath == null)
			return "";

		changeOsFileSeperator(fileFullPath);
		String filePath = fileFullPath.substring(0, fileFullPath.lastIndexOf(File.separator));
		String filename = fileFullPath.substring(fileFullPath.lastIndexOf(File.separator) + 1, fileFullPath.length());
		return makeThumbnailImage(size, filePath, filename, thumbnailPath, forced);
	}

	/**
	 * 주어진 파일 경로를 온라인에서 접근 가능한 URL로 만들어서 반환한다.
	 * 
	 * @param path
	 *            파일 경로
	 * @return 온라인에서 접근 가능한 URL
	 */
	public static final String makeFileUrl(String path, String serverUrl) {
		if (StringUtils.isEmpty(path))
			return "";

		String result = null;
		if (path.charAt(0) == '/') {
			result = serverUrl + path;
		} else {
			result = serverUrl + "/" + path;
		}
		return result;
	}

	/**
	 * 파일 경로 구분자를 OS에 맞게 변경한다
	 * 
	 * @param path
	 *            파일명
	 * @return 확장자
	 */
	public static final String changeOsFileSeperator(String path) {
		if (path == null)
			return null;

		path = path.replace("/", File.separator);
		path = path.replace("\\", File.separator);

		return path;
	}

}
