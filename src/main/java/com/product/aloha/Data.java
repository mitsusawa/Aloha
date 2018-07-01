package com.product.aloha;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "data")
public class Data {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private long id;
	public Long getId() {
		return id;
	}
	@Column(length = 32, nullable = true)
	private String userName;
	
	@Column(length = 1024, nullable = true)
	private String password;
	
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
