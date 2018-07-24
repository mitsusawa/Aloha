package com.product.aloha;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class MakeUpToDoForm {
	@NotEmpty
	@Size(min = 1, max = 128)
	private String newToDoName;
	
	public String getNewToDoName() {
		return newToDoName;
	}
	
	public void setNewToDoName(String newToDoName) {
		this.newToDoName = newToDoName;
	}
	
	public String getNewToDoInfo() {
		return newToDoInfo;
	}
	
	public void setNewToDoInfo(String newToDoInfo) {
		this.newToDoInfo = newToDoInfo;
	}
	
	@NotEmpty
	@Size(min = 1, max = 4096)
	
	private String newToDoInfo;
}
