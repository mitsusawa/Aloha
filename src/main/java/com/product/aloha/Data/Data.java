package com.product.aloha.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "data")
public class Data {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, unique = true)
	@NotNull
	private Long id;
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	@Column(length = 32, nullable = true)
	@NotEmpty
	private String userName;
	
	@Column(length = 512, nullable = true)
	@NotEmpty
	private String password;
	
	@OneToMany(targetEntity = ToDo.class)
	private List<ToDo> toDoList;
	
	public List<TimeTable> getTimeTableArray() {
		return timeTableArray;
	}
	
	public void setTimeTableArray(ArrayList<TimeTable> timeTableArray) {
		this.timeTableArray = timeTableArray;
	}
	
	public void setTimeTableArray(List<TimeTable> timeTableArray) {
		this.timeTableArray = timeTableArray;
	}
	
	public Data() {
		if (Objects.isNull(getTimeTableArray())){
			setTimeTableArray(new ArrayList<TimeTable>());
		}
		if (Objects.isNull(getToDoList())){
			setToDoList(new ArrayList<>());
		}
	}
	
	@OneToMany(targetEntity = TimeTable.class, cascade = CascadeType.ALL)
	private List<TimeTable> timeTableArray;
	
	public String getUserName() {
		return userName;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public List<ToDo> getToDoList() {
		return toDoList;
	}
	
	public void setToDoList(List<ToDo> toDoList) {
		this.toDoList = toDoList;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setUserName(String name) {
		userName = name;
		return;
	}
	
	public void setPassword(String pass) {
		password = pass;
		return;
	}
	
	public void addTimeTable(){
		getTimeTableArray().add(new TimeTable());
	}
}

