package com.product.aloha;

import javax.validation.constraints.NotEmpty;

public class SendForm {
	@NotEmpty
	private String sendFriendNum;
	
	public String getSendFriendNum() {
		return sendFriendNum;
	}
	
	public void setSendFriendNum(String sendFriendNum) {
		this.sendFriendNum = sendFriendNum;
	}
	
	public String getSendTableNum() {
		return sendTableNum;
	}
	
	public void setSendTableNum(String sendTableNum) {
		this.sendTableNum = sendTableNum;
	}
	
	@NotEmpty
	private String sendTableNum;
}
