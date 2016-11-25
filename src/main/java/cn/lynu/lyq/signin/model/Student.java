package cn.lynu.lyq.signin.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Student implements java.io.Serializable {
	private static final long serialVersionUID = 8026956442900902464L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String regNo;
	
	private String name;
	
	private String className;
	
	private Boolean online=false;
	
	public Student(){}
	
	public Student(String regNo, String name, String className, Boolean online) {
		this.regNo = regNo;
		this.name = name;
		this.className = className;
		this.online = online;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Boolean isOnline() {
		return online;
	}
	public void setOnline(Boolean online) {
		this.online = online;
	}
	
	
}
