package com.product.aloha;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class LoginUserSession {
	public boolean isLoggedIn() {
		return loggedIn;
	}
	
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	boolean loggedIn;
	
	public String getLoggedInName() {
		return loggedInName;
	}
	
	public void setLoggedInName(String loggedInName) {
		this.loggedInName = loggedInName;
	}
	
	String loggedInName;
	@ModelAttribute("userSession")
	public LoginUserSession setLoginUserSession(LoginUserSession loginUserSession) {
		return loginUserSession;
	}
	public LoginUserSession(){
		loggedIn = false;
		loggedInName = "";
	}
}
