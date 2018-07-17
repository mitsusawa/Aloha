package com.product.aloha.Data;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Embeddable
public class TimeTable {
	
	public void setDividedNum(Byte dividedNum) {
		this.dividedNum = dividedNum;
	}
	
	public byte getDividedNum() {
		return dividedNum;
	}
	
	public void setDividedNum(byte dividedNum) {
		this.dividedNum = dividedNum;
	}
	
	public String getTableName() {
		return TableName;
	}
	
	public void setTableName(String tableName) {
		TableName = tableName;
	}
	
	public TimeTable() {
		if(Objects.isNull(getLessonArray())){
			new ArrayList<Lesson>();
		}
	}
	
	private Byte dividedNum;
	
	@Size(min = 1, max = 128)
	private String TableName;
	
	public List<List<Lesson>> getLessonArray() {
		return lessonArray;
	}
	
	public void setLessonArray(List<List<Lesson>> lessonArray) {
		this.lessonArray = lessonArray;
	}
	
	@Embedded
	@CollectionTable(joinColumns = @JoinColumn(name = "data_time_table_lesson_array_id", nullable = true))
	@Column(nullable = true)
	private List<List<Lesson>> lessonArray;
	
	public void addLessonArray() {
		int size = getLessonArray().size();
		getLessonArray().add(new ArrayList<>());
		for (Lesson lesson: getLessonArray().get(size)) {
			lesson = new Lesson();
		}
	}
}
