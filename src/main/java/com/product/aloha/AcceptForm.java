package com.product.aloha;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AcceptForm {
	@NotEmpty
	@Size(min = 1, max = 128)
	private String acceptFlag;
	
	@NotEmpty
	@Size(min = 0)
	private String friendAcceptNum;
	
	public String getAcceptFlag() {
		return acceptFlag;
	}
	
	public String getFriendAcceptNum() {
		return friendAcceptNum;
	}
	
	public void setFriendAcceptNum(String friendAcceptNum) {
		this.friendAcceptNum = friendAcceptNum;
	}
	
	public void setAcceptFlag(String acceptFlag) {
		this.acceptFlag = acceptFlag;
	}
}
