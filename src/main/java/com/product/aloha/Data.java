package com.product.aloha;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	
	public void setId(long id) {
		this.id = id;
	}
	
	public List<TimeTable> getTimeTableArray() {
		return timeTableArray;
	}
	
	public void setTimeTableArray(ArrayList<TimeTable> timeTableArray) {
		this.timeTableArray = timeTableArray;
	}
	
	@Column(nullable = true)
	@Lob
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

class TimeTable{
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int id;
	
	public byte getDividedNum() {
		return dividedNum;
	}
	
	public void setDividedNum(byte dividedNum) {
		this.dividedNum = dividedNum;
	}
	
	public String getTableName() {
		return TableName;
	}
	
	public void setTableName(String tableName) {
		TableName = tableName;
		
	}
	
	@Min(1)
	@Max(127)
	private byte dividedNum;
	
	@NotEmpty
	@Size(min = 1, max = 32)
	private String TableName;
	
	public List<String[]> getTable() {
		return table;
	}
	
	public void setTable(List<String[]> table) {
		this.table = table;
	}
	
	private List<String[]> table;
}