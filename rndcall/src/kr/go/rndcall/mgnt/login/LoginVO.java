package kr.go.rndcall.mgnt.login;

import java.lang.reflect.Field;
import java.util.Arrays;

import kr.go.rndcall.mgnt.common.BaseVO;

public class LoginVO extends BaseVO {
	private String auth; // 권한
	private String auth_id;
	private String login_id;  //아이디
	private String name;  //이름
	private String password;  //비밀번호 
	private String org_cd;   //부처코드
	private String org_nm;    //부처명
	private String attached_nm; //소속
	private String email;    //이메일
	private String mobile; //연락처
	
	private String roleCD = "";
	private String roleNM = "";
	private String username = "";
	/**
	 * BuAdNotiLVO 생성자
	 */
	public LoginVO(){
		
		this.auth = ""; // 권한
		this.login_id = "";  //아이디
		this.auth_id = ""; // 순번
		this.name = "";  //이름
		this.password = "";  //비밀번호 
		this.org_cd = "";   //부처코드
		this.org_nm = "";    //부처명
		this.attached_nm = ""; //소속
		this.email = "";    //이메일
		this.mobile = ""; //연락처
		
	}
	
	
	
	
	/**
	 * toString 메소드
	 */
	public String toString() {
        StringBuffer tempBuffer = new StringBuffer();
        tempBuffer.append("\n [" + this.getClass().getName() + "]");
        Field[] myField = this.getClass().getDeclaredFields();

        for (int i = 0; i < myField.length; i++) {
            try {
                if (myField[i].getType().isArray()) {
                    String className = myField[i].getType().getName();
                    className = className.substring(2, className.length() - 1);

                    tempBuffer.append("\n " + className + "[] ").append(myField[i].getName()).append(" = "
                            + Arrays.asList((Object[]) myField[i].get(this)));

                } else if (myField[i].getType().isPrimitive() || myField[i].getType() == String.class) {

                    String typeName = myField[i].getType().getName();
                    typeName = ((myField[i].getType() == String.class) ? "String" : typeName);
                    tempBuffer.append("\n " + typeName + " ").append(myField[i].getName()).append(" = ["
                            + myField[i].get(this) + "]");
                } else if (myField[i].getType() == Class.class) {
                    // ignore
                } else {

                    String className = myField[i].getType().getName();
                    tempBuffer.append("\n " + className + " ").append(myField[i].getName()).append(" = ["
                            + myField[i].get(this) + "]");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return tempBuffer.toString();
    }

	public String getAttached_nm() {
		return attached_nm;
	}

	public void setAttached_nm(String attached_nm) {
		this.attached_nm = attached_nm;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin_id() {
		return login_id;
	}

	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrg_cd() {
		return org_cd;
	}

	public void setOrg_cd(String org_cd) {
		this.org_cd = org_cd;
	}

	public String getOrg_nm() {
		return org_nm;
	}

	public void setOrg_nm(String org_nm) {
		this.org_nm = org_nm;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}




	/**
	 * @return the roleCD
	 */
	public String getRoleCD() {
		return roleCD;
	}




	/**
	 * @return the roleNM
	 */
	public String getRoleNM() {
		return roleNM;
	}




	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}




	/**
	 * @param roleCD the roleCD to set
	 */
	public void setRoleCD(String roleCD) {
		this.roleCD = roleCD;
	}




	/**
	 * @param roleNM the roleNM to set
	 */
	public void setRoleNM(String roleNM) {
		this.roleNM = roleNM;
	}




	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}




	/**
	 * @return the auth_id
	 */
	public String getAuth_id() {
		return auth_id;
	}




	/**
	 * @param auth_id the auth_id to set
	 */
	public void setAuth_id(String auth_id) {
		this.auth_id = auth_id;
	}
}