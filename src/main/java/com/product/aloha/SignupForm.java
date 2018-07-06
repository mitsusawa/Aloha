package com.product.aloha;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignupForm {
	public String getSignupUserName() {
		return signupUserName;
	}
	
	public void setSignupUserName(String signupUserName) {
		this.signupUserName = signupUserName;
	}
	
	public String getSignupPassword() {
		return signupPassword;
	}
	
	public void setSignupPassword(String signupPassword) {
		this.signupPassword = signupPassword;
	}
	
	@NotNull
	@Size(min = 1, max = 32)
	private String signupUserName;
	
	@NotNull
	@Size(min = 1, max = 32)
	private String signupPassword;
}
