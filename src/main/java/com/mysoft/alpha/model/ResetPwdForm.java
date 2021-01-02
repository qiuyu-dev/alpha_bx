package com.mysoft.alpha.model;

import java.io.Serializable;

public class ResetPwdForm  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String username;
	private String oldPwd;
	private String password;
	private String password2;
	
	public ResetPwdForm() {
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	@Override
	public String toString() {
		return "ResetPwdForm [username=" + username + ", oldPwd=" + oldPwd + ", password=" + password + ", password2="
				+ password2 + "]";
	}
	
	
	
}
