package com.product.aloha.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Duration;

@Entity
public class Lesson implements Cloneable, Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, unique = true)
	@NotNull
	private Long id;
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable = true)
	private String name;
	
	@Column(nullable = true)
	private Duration startTime;
	@Column(nullable = true)
	private Duration endTime;
	
	@ManyToOne
	private LessonArrayWrap lessonArrayWrap;
	
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
	
	@Override
	public Lesson clone(){
		Lesson lesson = new Lesson();
		lesson.setName(this.getName());
		lesson.setEndTime(this.getEndTime());
		lesson.setStartTime(this.getEndTime());
		return lesson;
	}
}
