package com.product.aloha.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
public class LessonArrayWrap {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, unique = true)
	@NotNull
	private Long id;
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public TimeTable getTimeTable() {
		return timeTable;
	}
	
	public void setTimeTable(TimeTable timeTable) {
		this.timeTable = timeTable;
	}
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	@OneToMany(targetEntity = Lesson.class)
	@Column(nullable = false)
	private List<Lesson> array;
	
	public List<Lesson> getArray() {
		return array;
	}
	
	public void setArray(List<Lesson> array) {
		this.array = array;
	}
	
	@ManyToOne(targetEntity = TimeTable.class)
	TimeTable timeTable;
}
