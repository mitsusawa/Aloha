package com.product.aloha;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "data")
public class Data {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	@NotNull
	private long id;
	public Long getId() {
		return id;
	}
	@Column(length = 32, nullable = true)
	@NotEmpty
	private String userName;
	
	@Column(length = 512, nullable = true)
	@NotEmpty
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
