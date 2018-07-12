package com.product.aloha;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignUpForm {
	public String getSignUpUserName() {
		return signUpUserName;
	}
	
	public void setSignUpUserName(String signUpUserName) {
		this.signUpUserName = signUpUserName;
	}
	
	public String getSignUpPassword() {
		return signUpPassword;
	}
	
	public void setSignUpPassword(String signUpPassword) {
		this.signUpPassword = signUpPassword;
	}
	
	@NotEmpty
	@Size(min = 2, max = 32)
	private String signUpUserName;
	
	@NotEmpty
	@Size(min = 4, max = 32)
	private String signUpPassword;
}
