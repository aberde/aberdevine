package kr.co.gitech.storyz.common.util;

import java.util.Map;

import kr.co.gitech.storyz.common.message.errorcode.ErrorCode;
import kr.co.gitech.storyz.exception.StoryZException;

public class CommonUtil {

	/**
	 * 필수 입력 확인
	 * @param obj 데이터가 들어가 있는 객체
	 * @param params 필수 입력 확인이 필요한 항목들
	 * @throws Exception
	 */
	public static void isRequire(Map<String, Object> map, String[] params) throws Exception {
		for ( String param : params ) {
			if ( map.get(param) == null || ((String)map.get(param)).isEmpty() ) {
				throw new StoryZException(ErrorCode.PARAMETER_ERROR);
			}
		}
	}
}
