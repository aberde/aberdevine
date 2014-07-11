package kr.co.gitech.storyz.common.push;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 안드로이드, 아이폰 통합 메시지 객체
 */
public class MessageEntryImpl implements MessageEntry {

	private String user_key = null;

	private String mobp_no = null;

	private String platform_kind = null;

	private String device_id = null;

	private String pns_token = null;

	private Integer badge = 0;

	/**
	 * 글 종류
	 */
	private String type = null;

	/**
	 * 알림 메세지를 포함하는 추가 데이터 맵. 안드로이드 사용시 JSON 형태 <br/>
	 * 아이폰 사용시 현재 MAP 형태로 데이터를 전송하기 위한 용도.
	 */
	private Map<String, Object> custom = new HashMap<String, Object>();

	private String alert = null;

	/**
	 * 사운드(APNS용)
	 */
	private String sound = "default";

	private boolean status = false;

	private boolean initialized = false;

	public MessageEntryImpl() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.hsnc.sns.ext.pns.model.MessageContext#getUser_key()
	 */
	@Override
	public String getUser_key() {
		return user_key;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.hsnc.sns.ext.pns.model.MessageContext#setUser_key(java.lang.String)
	 */
	@Override
	public void setUser_key(String user_key) {
		this.user_key = user_key;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.hsnc.sns.ext.pns.model.MessageContext#getPlatform_kind()
	 */
	@Override
	public String getPlatform_kind() {
		return platform_kind;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.hsnc.sns.ext.pns.model.MessageContext#setPlatform_kind(java.lang.String)
	 */
	@Override
	public void setPlatform_kind(String platform_kind) {
		this.platform_kind = platform_kind;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.hsnc.sns.ext.pns.model.MessageContext#getPns_token()
	 */
	@Override
	public String getPns_token() {
		return pns_token;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.hsnc.sns.ext.pns.model.MessageContext#setPns_token(java.lang.String)
	 */
	@Override
	public void setPns_token(String pns_token) {
		this.pns_token = pns_token;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.hsnc.sns.ext.pns.model.MessageContext#getMobp_no()
	 */
	@Override
	public String getMobp_no() {
		return mobp_no;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.hsnc.sns.ext.pns.model.MessageContext#setMobp_no(java.lang.String)
	 */
	@Override
	public void setMobp_no(String mobp_no) {
		this.mobp_no = mobp_no;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.hsnc.sns.ext.pns.model.MessageContext#getDevice_id()
	 */
	@Override
	public String getDevice_id() {
		return device_id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.hsnc.sns.ext.pns.model.MessageContext#setDevice_id(java.lang.String)
	 */
	@Override
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	/**
	 * @return the alert
	 */
	public String getAlert() {
		return alert;
	}

	/**
	 * @param alert
	 *            the alert to set
	 */
	public void setAlert(String alert) {
		this.alert = alert;
	}

	/**
	 * @return the sound
	 */
	public String getSound() {
		return sound;
	}

	/**
	 * @param sound
	 *            the sound to set
	 */
	public void setSound(String sound) {
		this.sound = sound;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.hsnc.sns.ext.pns.model.MessageContext#getBadge()
	 */
	@Override
	public Integer getBadge() {
		return badge;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see kr.co.hsnc.sns.ext.pns.model.MessageContext#setBadge(java.lang.Integer)
	 */
	@Override
	public void setBadge(Integer badge) {
		this.badge = badge;
	}

	@Override
	public String toJson(String type, Map<String, Object> custom) {
		this.setCustom(type, custom);
		return toJson();
	}

	@Override
	public String toJson() {
		return JSONObject.fromObject(custom).toString();
	}

	@Override
	public void setCustom(String type, Map<String, Object> custom) {
		if (initialized)
			return;
		if (type != null && !"".equals(type)) {
			this.custom.put(MESSAGE_TYPE, type);
		}
		if (custom != null && !custom.isEmpty()) {
			this.custom.putAll(custom);
		}
		this.custom.put(BADGE, badge);
		this.custom.put(CONTENT, alert);
		initialized = true;
	}

	public <V> void putMessage(String key, V val) {
		custom.put(key, String.valueOf(val));
	}

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

}
