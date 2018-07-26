package com.product.aloha.Data;

import org.hibernate.annotations.ListIndexBase;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "data")
public class Data  implements Cloneable, Serializable {
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
	
	public List<Friend> getFriendList() {
		return friendList;
	}
	
	public void setFriendList(List<Friend> friendList) {
		this.friendList = friendList;
	}
	
	@Column(length = 512, nullable = true)
	@NotEmpty
	private String password;
	
	@OneToMany(targetEntity = ToDo.class, cascade = CascadeType.ALL)
	@Column(nullable = false)
	@OrderBy
	private List<ToDo> toDoList;
	
	@OneToMany(targetEntity = Friend.class, cascade = CascadeType.ALL)
	@Column(nullable = false)
	@OrderBy
	private List<Friend> friendList;
	
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
			setToDoList(new ArrayList<ToDo>());
		}
	}
	
	@OneToMany(targetEntity = TimeTable.class, cascade = CascadeType.ALL)
	@Column(nullable = false)
	@OrderBy
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

