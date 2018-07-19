package com.product.aloha.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class TimeTable {
	
	public void setDividedNum(Byte dividedNum) {
		this.dividedNum = dividedNum;
	}
	
	public byte getDividedNum() {
		return dividedNum;
	}
	
	public void setDividedNum(byte dividedNum) {
		this.dividedNum = dividedNum;
	}
	
	public TimeTable(List<List<Lesson>> lessonArray) {
		if (Objects.isNull(getLessonArrayWrap())) {
			addLessonArrayWrap();
		}
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public TimeTable() {
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, unique = true)
	@NotNull
	private Long id;
	
	private Byte dividedNum;
	
	@Size(min = 1, max = 128)
	private String tableName;
	
	
	public List<LessonArrayWrap> getLessonArrayWrap() {
		return lessonArray;
	}
	
	public void setLessonArrayWrap(List<LessonArrayWrap> lessonArray) {
		this.lessonArray = lessonArray;
	}
	
	public Data getData() {
		return data;
	}
	
	public void setData(Data data) {
		this.data = data;
	}
	
	@OneToMany(targetEntity = LessonArrayWrap.class, cascade = CascadeType.ALL)
	private List<LessonArrayWrap> lessonArray;
	
	public void addLessonArrayWrap() {
		if (Objects.isNull(getLessonArrayWrap())) {
			setLessonArrayWrap(new ArrayList<LessonArrayWrap>());
		}
		for (LessonArrayWrap lnList : getLessonArrayWrap()) {
			if (Objects.isNull(lnList)) {
				lnList = new LessonArrayWrap();
			}
			for (Lesson ln : lnList.getArray()) {
				if (Objects.isNull(ln)) {
					ln = new Lesson();
				}
			}
		}
	}
	
	@ManyToOne(targetEntity = Data.class)
	Data data;
	
}
