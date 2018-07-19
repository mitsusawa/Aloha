package com.product.aloha;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class EditForm {
	@NotEmpty
	private String wrapNum;
	
	@NotEmpty
	private String editLessonNum;
	
	@NotEmpty
	private String tableNum;
	
	public String getTableNum() {
		return tableNum;
	}
	
	public void setTableNum(String tableNum) {
		this.tableNum = tableNum;
	}
	
	public String getWrapNum() {
		return wrapNum;
	}
	
	public void setWrapNum(String wrapNum) {
		this.wrapNum = wrapNum;
	}
	
	public String getEditLessonNum() {
		return editLessonNum;
	}
	
	public void setEditLessonNum(String editLessonNum) {
		this.editLessonNum = editLessonNum;
	}
	
	public String getEditLessonName() {
		return editLessonName;
	}
	
	public void setEditLessonName(String editLessonName) {
		this.editLessonName = editLessonName;
	}
	
	@NotEmpty
	
	@Size(min = 1, max = 128)
	private String editLessonName;
}
