package com.product.aloha.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "data_time_table")
public class TimeTable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	@NotNull
	private Long id;
	public Long getId() {
		return id;
	}
	
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
	
	@Column(nullable = true)
	private Byte dividedNum;
	
	public List<Lesson[]> getTable() {
		return table;
		
	}
	
	public void setTable(List<Lesson[]> table) {
		this.table = table;
	}
	
	@Column(nullable = true)
	@Size(min = 1, max = 128)
	private String TableName;
	
	@OneToMany(targetEntity = Lesson.class)
	private List<Lesson[]> table;
	
	@ManyToOne(targetEntity = Data.class)
	private Data data;
}
