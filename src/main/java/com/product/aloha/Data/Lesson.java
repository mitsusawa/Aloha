package com.product.aloha.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.Duration;

@Entity
public class Lesson{
	public Long getId() {
		return id;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	@NotNull
	private Long id;
	
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
	
	public Lesson(){
		name = "";
	}
	
	@ManyToOne(targetEntity = TimeTable.class)
	private TimeTable timeTable;
}
