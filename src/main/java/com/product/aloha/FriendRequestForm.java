package com.product.aloha;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class FriendRequestForm {
	@NotEmpty
	@Size(min = 1, max = 128)
	private String friendRequestName;
	
	public String getFriendRequestName() {
		return friendRequestName;
	}
	
	public void setFriendRequestName(String friendRequestName) {
		this.friendRequestName = friendRequestName;
	}
}
