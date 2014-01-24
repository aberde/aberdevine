package egovframework.com.ext.jfile.exception;

/**
 *  클래스
 * @author 정호열
 * @since 2010.10.17
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일        수정자       수정내용
 *  -------       --------    ---------------------------
 *   2010.10.17   정호열       최초 생성
 *   2013.12.19	표준프레임워크	공통컴포넌트 추가 적용 (패키지 변경)
 *
 * </pre>
 */
public class JFileException extends RuntimeException {

	private static final long serialVersionUID = -2657233597993703588L;
	
	public JFileException() {
		super();
	}
	
	public JFileException(String msg) {
		super(msg);
	}
	
	public JFileException(Throwable throwable) {
		super(throwable);
	}
	
	public JFileException(String msg, Throwable throwable) {
		super(msg, throwable);
	}
	
	public Throwable getRootCause() {
		Throwable rootCause = null;
		Throwable cause = getCause();
		while (cause != null && cause != rootCause) {
			rootCause = cause;
			cause = cause.getCause();
		}
		return rootCause;
	}
}
