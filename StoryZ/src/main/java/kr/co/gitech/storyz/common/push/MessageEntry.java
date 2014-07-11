package kr.co.gitech.storyz.common.push;

import java.util.Map;

/**
 * 안드로이드, 아이폰 통합 메시지 객체
 */
public interface MessageEntry {

	/**
	 * 뱃지
	 */
	public String BADGE = "badge";

	/**
	 * 메시지 타입
	 */
	public String MESSAGE_TYPE = "type";

	/**
	 * 메시지 내용
	 */
	public String CONTENT = "contents";

	/**
	 * @return the user_key
	 */
	public String getUser_key();

	/**
	 * @param user_key
	 *            the user_key to set
	 */
	public void setUser_key(String user_key);

	/**
	 * @return the mobp_no
	 */
	public String getMobp_no();

	/**
	 * @param mobp_no
	 *            the mobp_no to set
	 */
	public void setMobp_no(String mobp_no);

	/**
	 * @return the device_id
	 */
	public String getDevice_id();

	/**
	 * @param device_id
	 *            the device_id to set
	 */
	public void setDevice_id(String device_id);

	/**
	 * @return the platform_kind
	 */
	public String getPlatform_kind();

	/**
	 * @param platform_kind
	 *            the platform_kind to set
	 */
	public void setPlatform_kind(String platform_kind);

	/**
	 * @return the pns_token
	 */
	public String getPns_token();

	/**
	 * @param pns_token
	 *            the pns_token to set
	 */
	public void setPns_token(String pns_token);

	/**
	 * 기본 메세지를 가져온다.
	 * 
	 * @return 메세지
	 */
	public String getAlert();

	/**
	 * 기본 메세지를 세팅한다.
	 * 
	 * @param alert
	 *            메세지
	 */
	public void setAlert(String alert);

	/**
	 * 아이폰 Push 시에 사용될 사운드를 가져온다.
	 * 
	 * @return 사운드 설정
	 */
	public String getSound();

	/**
	 * 아이폰 Push 시에 사용될 사운드를 입력한다.
	 * 
	 * @param sound
	 *            사운드
	 */
	public void setSound(String sound);

	/**
	 * @return the badge
	 */
	public Integer getBadge();

	/**
	 * @param badge
	 *            the badge to set
	 */
	public void setBadge(Integer badge);

	public String toJson(String type, Map<String, Object> custom);

	/**
	 * JSON 형식으로 변환. put() 메소드를 이용하여 저장한 데이터들을 JSON 형식으로 반환한다. 안드로이드 메세지 Push 에 이 메소드를 이용함.
	 * 
	 * @return JSON String
	 */
	public String toJson();

	public void setCustom(String type, Map<String, Object> custom);

	/**
	 * @return the type
	 */
	public String getType();

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type);

	/**
	 * @return 발송 상태
	 */
	public boolean isStatus();

	/**
	 * @param status
	 *            발송 상태
	 */
	public void setStatus(boolean status);

}
