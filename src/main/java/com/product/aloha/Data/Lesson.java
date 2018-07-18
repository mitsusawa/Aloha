package com.product.aloha.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.Duration;

@Embeddable
public class Lesson {
	@Column(nullable = true)
	private String name;
	
	@Column(nullable = true)
	private Duration startTime;
	@Column(nullable = true)
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
	
	public Lesson() {
		name = "未入力";
	}
}
