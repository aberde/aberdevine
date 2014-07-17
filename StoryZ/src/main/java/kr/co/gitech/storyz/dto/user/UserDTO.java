package kr.co.gitech.storyz.dto.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.ALWAYS)
@XmlAccessorType(XmlAccessType.FIELD)
public class UserDTO {
	
	/** 학생/사원 번호 */
	@XmlElement(name = "employee_no", nillable = true)
	private String employee_no;
	
	/** 사용자 아이디 */
	@XmlElement(name = "user_id", nillable = true)
	private String user_id;
	
	/** 사용자 일련번호 */
	@XmlElement(name = "user_key", nillable = true)
	private String user_key;
	
	/** 사용자 이름 */
	@XmlElement(name = "user_name")
	private String user_name;

	/** 사용자 닉네임 */
	@XmlElement(name = "user_nick")
	private String user_nick;
	
	/** 사용자 프로필 미리보기 이미지 URL */
	@XmlElement(name = "img_prvw_path")
	private String img_prvw_path;
	
	/** 시용자 프로필 이미지 URL */
	@XmlElement(name = "img_path")
	private String img_path;

	/** 사용자 이름 초성 */
	@XmlElement(name = "user_init")
	private String user_init;
	
	/** 학교/회사 아이디 */
	@XmlElement(name = "company_id")
	private String company_id;
	
	/** 학교/회사 이름 */
	@XmlElement(name = "company_name")
	private String company_name;
	
	/** 학교/회사 이름 */
	@XmlElement(name = "company_name")
	private String dept_id;

	/** 학과/부서 이름 */
	@XmlElement(name = "dept_name")
	private String dept_name;
	
	/** 성별(남자:M / 여자:F) */
	@XmlElement(name = "user_sex")
	private String user_sex;
	
	/** 사용자 이메일 */
	@XmlElement(name = "user_email")
	private String user_email;

	/** 사용자 전화번호 */
	@XmlElement(name = "mobp_no")
	private String mobp_no;
	
	/** 친구여부 */
	@XmlElement(name = "is_friend")
	private String is_friend;
	
	/** 한줄 메시지 */
	@XmlElement(name = "line_msg")
	private String line_msg;
	
	/** 탈퇴여부 */
	@XmlElement(name = "del_yn")
	private String del_yn;

	public String getEmployee_no() {
		return employee_no;
	}

	public void setEmployee_no(String employee_no) {
		this.employee_no = employee_no;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_key() {
		return user_key;
	}

	public void setUser_key(String user_key) {
		this.user_key = user_key;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_nick() {
		return user_nick;
	}

	public void setUser_nick(String user_nick) {
		this.user_nick = user_nick;
	}

	public String getImg_prvw_path() {
		return img_prvw_path;
	}

	public void setImg_prvw_path(String img_prvw_path) {
		this.img_prvw_path = img_prvw_path;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public String getUser_init() {
		return user_init;
	}

	public void setUser_init(String user_init) {
		this.user_init = user_init;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getUser_sex() {
		return user_sex;
	}

	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getMobp_no() {
		return mobp_no;
	}

	public void setMobp_no(String mobp_no) {
		this.mobp_no = mobp_no;
	}

	public String getIs_friend() {
		return is_friend;
	}

	public void setIs_friend(String is_friend) {
		this.is_friend = is_friend;
	}

	public String getLine_msg() {
		return line_msg;
	}

	public void setLine_msg(String line_msg) {
		this.line_msg = line_msg;
	}

	public String getDel_yn() {
		return del_yn;
	}

	public void setDel_yn(String del_yn) {
		this.del_yn = del_yn;
	}
}
