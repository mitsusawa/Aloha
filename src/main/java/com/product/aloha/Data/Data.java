package com.product.aloha.Data;

import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "data")
public class Data {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	@NotNull
	private Long id;
	public Long getId() {
		return id;
	}
	@Column(length = 32, nullable = true)
	@NotEmpty
	private String userName;
	
	@Column(length = 512, nullable = true)
	@NotEmpty
	
	private String password;
	
	public void setId(long id) {
		this.id = id;
	}
	
	public List<TimeTable> getTimeTableArray() {
		return timeTableArray;
	}
	
	public void setTimeTableArray(ArrayList<TimeTable> timeTableArray) {
		this.timeTableArray = timeTableArray;
	}
	
	@OneToMany(targetEntity = TimeTable.class)
	private List<TimeTable> timeTableArray;
	
	public String getUserName(){
		return userName;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setUserName(String name){
		userName = name;
		return;
	}
	
	public void setPassword(String pass){
		password = pass;
		return;
	}
}

