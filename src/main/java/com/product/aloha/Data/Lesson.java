package com.product.aloha.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.Duration;

@Embeddable
public class Lesson {
	private String name;
	
	private Duration startTime;
	private Duration endTime;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Duration getStartTime() {
		return startTime;
	}
	
	public void setStartTime(Duration startTime) {
		this.startTime = startTime;
	}
	
	public Duration getEndTime() {
		return endTime;
	}
	
	public void setEndTime(Duration endTime) {
		this.endTime = endTime;
	}
	
	public Lesson newLesson(){
		return new Lesson();
	}
	
	public Lesson() {
		name = "未入力";
	}
}
