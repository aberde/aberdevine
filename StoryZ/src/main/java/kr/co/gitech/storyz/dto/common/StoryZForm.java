package kr.co.gitech.storyz.dto.common;

import kr.co.gitech.storyz.common.push.MessageContext;

import org.springframework.web.multipart.MultipartFile;

/**
 * 클라이언트 요청에 대한 공통 폼 정의
 */
@SuppressWarnings("serial")
public class StoryZForm extends BaseObject {

	private String session_id = null;

	private Search search = null;

	private MultipartFile[] file = null;

	/**
	 * 삭제여부
	 */
	private String del_yn = null;

	private MessageContext messageContext = null;

	private String prefixImage = null;

	public StoryZForm() {
	}

	/**
	 * @return the session_id
	 */
	public String getSession_id() {
		return session_id;
	}

	/**
	 * @param session_id
	 *            the session_id to set
	 */
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	/**
	 * @return the search
	 */
	public Search getSearch() {
		return search;
	}

	/**
	 * @param search
	 *            the search to set
	 */
	public void setSearch(Search search) {
		this.search = search;
	}

	/**
	 * @return the file
	 */
	public MultipartFile[] getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(MultipartFile[] file) {
		this.file = file;
	}

	/**
	 * @return the del_yn
	 */
	public String getDel_yn() {
		return del_yn;
	}

	/**
	 * @param del_yn
	 *            the del_yn to set
	 */
	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}

	/**
	 * @return the messageContext
	 */
	public MessageContext getMessageContext() {
		return messageContext;
	}

	/**
	 * @param messageContext
	 *            the messageContext to set
	 */
	public void setMessageContext(MessageContext messageContext) {
		this.messageContext = messageContext;
	}

	/**
	 * @return the prefixImage
	 */
	public String getPrefixImage() {
		return prefixImage;
	}

	/**
	 * @param prefixImage
	 *            the prefixImage to set
	 */
	public void setPrefixImage(String prefixImage) {
		this.prefixImage = prefixImage;
	}

}
