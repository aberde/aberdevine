package kr.co.gitech.storyz.common.message.errorcode;

import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

/**
 * 에러코드 정의
 */
public enum ErrorCode {

	/** 성공 */
	SUCCESS(0, HttpStatus.OK, "common.success")

	/*
	 * Common errors (10x)
	 */

	/** 서버 내부 오류 */
	, INTERNAL_ERROR(100, HttpStatus.INTERNAL_SERVER_ERROR, "common.internal")

	/** 알 수 없는 오류 */
	, RUNTIME_ERROR(101, HttpStatus.INTERNAL_SERVER_ERROR, "common.unknown")

	/** 데이타베이스 접속 오류 */
	, DATABASE_ERROR(102, HttpStatus.INTERNAL_SERVER_ERROR, "common.database")

	/** 파라미터 오류 */
	, PARAMETER_ERROR(103, HttpStatus.BAD_REQUEST, "common.internal.parameter")

	/** 404 Not Found */
	, NOT_FOUND_ERROR(104, HttpStatus.NOT_FOUND, "common.not_found")

	/** 인증 오류 */
	, UNAUTHORIZED_ERROR(105, HttpStatus.UNAUTHORIZED, "common.unauthorized")

	/** 올바르지 않은 App-Agent */
	, INVALID_APP_AGENT_ERROR(106, HttpStatus.BAD_REQUEST, "common.invalid.app-agent")

	/** 데이타베이스 실행 오류 */
	, DATABASE_EXECUTE(107, HttpStatus.INTERNAL_SERVER_ERROR, "common.database.execute")

	/** 지원 않습니다. */
	, NOT_SUPPORTED(108, HttpStatus.HTTP_VERSION_NOT_SUPPORTED, "common.not_supported")

	/** 동일한 사용자의 정보가 존재 */
	, DUPLICATE_USER(109, HttpStatus.UNAUTHORIZED, "common.duplicate.user")
	
	/** 초성검색 파라미터 오류 */
	, ONSET_PARAMETER_ERROR(109, HttpStatus.BAD_REQUEST, "common.onset.parameter")

	/** 아이디, 비밀번호를 다시 한번 확인해 주세요. */
	, USER_NOT_MATCH_PASSWORD(2000, HttpStatus.UNAUTHORIZED, "user.not_match.password")

	/** 등록된 휴대폰 기기의 정보와 일치하지 않습니다. */
	, USER_WRONG_DEVICE(2001, HttpStatus.UNAUTHORIZED, "user.wrong.device")

	/** 등록된 휴대폰 번호와 일치하지 않습니다. */
	, USER_NOT_MATCH_PHONE(2002, HttpStatus.UNAUTHORIZED, "user.not_match.phone")

	/** 접근제한이 된 사용자 */
	, USER_BLOCKED(2003, HttpStatus.FORBIDDEN, "user.blocked")

	/** 지원하지않는 사용자 입니다. */
	, USER_NOT_SUPPORTED(2004, HttpStatus.UNAUTHORIZED, "user.not_supported")
	
	/** access token 생성 에러 */
	, USER_ACCESSTOKEN_NOT_GENERATED(2004, HttpStatus.UNAUTHORIZED, "user.accesstoken.not_generated")

	/** 사용자 정보가 없습니다. */
	, USER_NOT_EXIST(2101, HttpStatus.UNAUTHORIZED, "user.not_exist")

	/** 아이디 미존재 */
	, USER_NOT_EXIST_ID(2102, HttpStatus.UNAUTHORIZED, "user.not_exist.id")

	/** 이미 존재하는 ID */
	, USER_ALREADY_ID(2103, HttpStatus.UNAUTHORIZED, "user.already.id")

	/** 탈퇴한 사용자 입니다. */
	, USER_DELETED(2104, HttpStatus.FORBIDDEN, "user.deleted")

	/** 이미 등록된 사용자 */
	, USER_ALREADY_REGISTER(2105, HttpStatus.UNAUTHORIZED, "user.already.register")

	/** 이미 존재하는 전화번호 */
	, USER_ALREADY_MOBP(2106, HttpStatus.UNAUTHORIZED, "user.already.mobp_no")

	/** 이미 존재하는 이메일 */
	, USER_ALREADY_EMAIL(2107, HttpStatus.UNAUTHORIZED, "user.already.email")

	/** 사원이 아닙니다 */
	, USER_NOT_EMPLOYEE(2108, HttpStatus.UNAUTHORIZED, "user.not.employee")

	/** PNS Token 미존재 */
	, USER_NO_PNS_TOKEN(2109, HttpStatus.UNAUTHORIZED, "user.not_exist.pns_token")

	/** 디바이스 아이디 미존재 */
	, USER_NO_DEVICE_ID_V3(2200, HttpStatus.UNAUTHORIZED, "user.not_exist.device")
	
	/** 기기변경중 에러 */
	, USER_CHANGE_DEVICE_V3(2201, HttpStatus.UNAUTHORIZED, "user.change.device")
	
	/*
	 * File upload errors (16x)
	 */
	/** 파일 업로드 실패 */
	, FILE_UPLOAD_FAILED(161, HttpStatus.INTERNAL_SERVER_ERROR, "file.failed.save")

	/** 파일 찾기 실패 */
	, FILE_UPLOAD_FILE_NOT_FOUND(162, HttpStatus.BAD_REQUEST, "file.not_found")

	/** 파일 사이즈 초과 */
	, FILE_UPLOAD_SIZE_EXCEEDED(163, HttpStatus.BAD_REQUEST, "file.exceeded.size")

	/** 지원하지 않는 파일 유형 */
	, FILE_UPLOAD_UNSUPPORTED_FILE_TYPE(164, HttpStatus.BAD_REQUEST, "file.unsupported.file_type");
	
	/**
	 * 에러코드
	 */
	private final int value;

	/**
	 * HTTP 상태 코드
	 */
	private final HttpStatus httpStatus;

	/**
	 * 메시지 코드
	 */
	private final String message;

	/**
	 * @param value
	 *            에러코드
	 * @param httpStatus
	 *            HTTP 상태
	 * @param message
	 *            메시지 코드
	 */
	private ErrorCode(int value, HttpStatus httpStatus, String message) {
		Assert.notNull(httpStatus, "HttpStatus must not be null");
		Assert.notNull(message, "Message must not be null");

		this.value = value;
		this.httpStatus = httpStatus;
		this.message = message;
	}

	/**
	 * 에러코드
	 */
	public int value() {
		return this.value;
	}

	/**
	 * HTTP 상태
	 */
	public HttpStatus httpStatus() {
		return this.httpStatus;
	}

	/**
	 * 메시지 코드
	 */
	public String message() {
		return message;
	}

	/**
	 * @see java.lang.Enum#toString()
	 */
	public String toString() {
		return Integer.toString(value);
	}

	/**
	 * Return the enum constant of this type with the specified numeric value.
	 * 
	 * @param resultCode
	 *            the numeric value of the enum to be returned
	 * @return the enum constant with the specified numeric value
	 * @throws IllegalArgumentException
	 *             if this enum has no constant for the specified numeric value
	 */
	public static ErrorCode valueOf(int resultCode) {
		for (ErrorCode code : values()) {
			if (code.value == resultCode) {
				return code;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + resultCode + "]");
	}

}
