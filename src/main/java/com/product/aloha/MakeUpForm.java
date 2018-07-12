package com.product.aloha;
import lombok.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MakeUpForm {
	public byte getDivideNum() {
		return divideNum;
	}
	
	public void setDivideNum(byte divideNum) {
		this.divideNum = divideNum;
	}
	
	public String getMakeUpTableName() {
		return makeUpTableName;
	}
	
	public void setMakeUpTableName(String makeUpTableName) {
		this.makeUpTableName = makeUpTableName;
	}
	
	@Min(1)
	@Max(127)
	private byte divideNum;
	
	@NotEmpty
	@Size(min = 1, max = 128)
	private String makeUpTableName;
}
