package kr.co.gitech.storyz.common.push;

/**
 * PNS 메시지 타입
 */
public enum PushMessageType {

	/** 무시 */
	IGNORE(0, null)

	/** 일반 메시지 */
	, NORMAL(1, "010")

	/** 시스템 메시지 */
	, SYSTEM(2, "011")

	/** 일반 메시지 */
	, CREATE_CHAT(3, "041")

	/** 글 쓰기 */
	, WRITE_TOPIC(4, "051")

	/** 댓글 쓰기 */
	, REPLY_TOPIC(5, "")

	/** 시샵 글 쓰기 */
	, WRITE_TOPIC_SYSOP(6, "")

	/** 관리자 글쓰기 */
	, WRITE_TOPIC_ADMIN(7, "")

	/** 클럽 초대 요청 */
	, INVITE_CLUB(8, "052")

	/** 클럽 초대 요청 */
	, JOIN_CLUB(9, "053")

	/** 클럽 시샵 권한 양도 요청 */
	, REQUEST_SYSOP_AUTH(10, "054")

	/** 클럽 폐쇄 */
	, DELETE_CLUB(11, "055")

	/** 클럽 시샵 권한 양도 승인 */
	, APPROVE_SYSOP_AUTH(12, "056")

	;

	private int value;

	private String kind = null;

	private PushMessageType(int value, String kind) {
		this.value = value;
		this.kind = kind;
	}

	public int value() {
		return this.value;
	}

	public String kind() {
		return this.kind;
	}

}
