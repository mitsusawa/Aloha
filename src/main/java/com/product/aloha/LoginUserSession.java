package com.product.aloha;

import com.product.aloha.Data.Data;
import com.product.aloha.Data.LessonArrayWrap;
import com.product.aloha.Data.TimeTable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class LoginUserSession {
	public Data getData() {
		return data;
	}
	
	public void setData(Data data) {
		this.data = data;
	}
	
	public Data data;
	
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
