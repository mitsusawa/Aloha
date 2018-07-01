package com.product.aloha;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SignupForm {
	public String getSighupUserName() {
		return sighupUserName;
	}
	
	public void setSighupUserName(String sighupUserName) {
		this.sighupUserName = sighupUserName;
	}
	
	public String getSighupPassword() {
		return sighupPassword;
	}
	
	public void setSighupPassword(String sighupPassword) {
		this.sighupPassword = sighupPassword;
	}
	
	@NotNull
	@Size(min = 1, max = 32)
	private String sighupUserName;
	
	@NotNull
	@Size(min = 1, max = 32)
	private String sighupPassword;
}
