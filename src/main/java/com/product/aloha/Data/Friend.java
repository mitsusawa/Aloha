package com.product.aloha.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
public class Friend implements Cloneable, Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, unique = true)
	@NotNull
	private Long id;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable = true)
	private String name;
	
	@Column(nullable = false)
	private Boolean acceptable;
	
	@ManyToOne(targetEntity = Data.class)
	private Data data;
	
	public String getName() {
		return name;
	}
	
	public Boolean getAcceptable() {
		return acceptable;
	}
	
	public void setAcceptable(Boolean acceptable) {
		this.acceptable = acceptable;
	}
	
	public Data getData() {
		return data;
	}
	
	public void setData(Data data) {
		this.data = data;
	}
	
	public Boolean getStatus() {
		return status;
	}
	
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(nullable = false)
	private Boolean status;
	
	public Friend() {
		name = "";
		status = false;
		acceptable = false;
	}
}
