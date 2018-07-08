package com.product.aloha;
import javax.validation.constraints.NotEmpty;
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
	
	@NotEmpty
	@Size(min = 2, max = 32)
	private String signupUserName;
	
	@NotEmpty
	@Size(min = 4, max = 32)
	private String signupPassword;
}
