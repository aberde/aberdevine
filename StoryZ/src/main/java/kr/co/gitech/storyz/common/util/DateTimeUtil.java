package kr.co.gitech.storyz.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * date & time 유틸리티 <br/>
 */
public class DateTimeUtil {

	public static final String SHORT_DATE_FORMAT = new String("yyyy-MM-dd");

	public static final String TIMESTAMP_PATTERN = "yyyyMMddHHmmss";

	public static final String SIMPLE_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static final String DIRECTORY_DATE_FORMAT = new String("yyyy/MM/dd");

	public static final String MAINTAINANCE_DATE_FORMAT = new String("yy-MM-dd HH:mm");

	public static final SimpleDateFormat SIMPLE_FORMAT = new SimpleDateFormat(SIMPLE_PATTERN);

	/**
	 * @param date
	 * @param df
	 * @return
	 */
	public static final String getToday() {
		return getDateToString(null, SHORT_DATE_FORMAT);
	}

	/**
	 * @return 현재시간을 "yyyyMMddHHmmss" 형식으로 리턴
	 */
	public static final String getTimestamp() {
		String now = getDateToString(0, TIMESTAMP_PATTERN);
		return now;
	}

	public static final String getDateToString(long timestamp, String pattern) {
		if (timestamp == 0) {
			timestamp = System.currentTimeMillis();
		}
		if (pattern == null || "".equals(pattern)) {
			pattern = SIMPLE_PATTERN;
		}
		SimpleDateFormat fmt = new SimpleDateFormat(pattern);
		return fmt.format(new Date(timestamp));
	}

	public static final String getDateToString(Date date, String pattern) {
		if (date == null) {
			date = new Date();
		}
		if (pattern == null || "".equals(pattern)) {
			pattern = SIMPLE_PATTERN;
		}
		SimpleDateFormat fmt = new SimpleDateFormat(pattern);
		return fmt.format(date);
	}

	public static final Date parseStringToDate(String dateStr, String pattern) throws ParseException {
		if (pattern == null || "".equals(pattern)) {
			pattern = SIMPLE_PATTERN;
		}
		SimpleDateFormat fmt = new SimpleDateFormat(pattern);
		return fmt.parse(dateStr);
	}

}
