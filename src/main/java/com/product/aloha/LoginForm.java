package com.product.aloha;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginForm {
	public String getLoginUserName() {
		return loginUserName;
	}
	
	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}
	
	public String getLoginPassword() {
		return loginPassword;
	}
	
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	
	@NotEmpty
	@Size(min = 2, max = 32)
	private String loginUserName;
	
	@NotEmpty
	@Size(min = 4, max = 32)
	private String loginPassword;
}
