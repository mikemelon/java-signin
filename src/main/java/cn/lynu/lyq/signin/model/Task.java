package cn.lynu.lyq.signin.model;
// default package

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class Task implements java.io.Serializable {
	private static final long serialVersionUID = -4605992634574561122L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="stu_id")
	private Student student;
	
	private String title;
	
	private String content;
	
	private String webaddress;
	
	private Boolean isselect;
	
	private Date selectdate;
	
	private String className;

	// Constructors

	/** default constructor */
	public Task() {}

	/** full constructor */
	public Task(Student student, String title, String content, String webaddress, Boolean isselect, Date selectdate,
			String className) {
		this.student = student;
		this.title = title;
		this.content = content;
		this.webaddress = webaddress;
		this.isselect = isselect;
		this.selectdate = selectdate;
		this.className = className;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getWebaddress() {
		return this.webaddress;
	}

	public void setWebaddress(String webaddress) {
		this.webaddress = webaddress;
	}

	public Boolean getIsselect() {
		return this.isselect;
	}

	public void setIsselect(Boolean isselect) {
		this.isselect = isselect;
	}

	public Date getSelectdate() {
		return this.selectdate;
	}

	public void setSelectdate(Date selectdate) {
		this.selectdate = selectdate;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public String toString() {
		return "Task [id=" + id + ", student=" + student + ", title=" + title + ", content=" + content + ", webaddress="
				+ webaddress + ", isselect=" + isselect + ", selectdate=" + selectdate + ", className=" + className
				+ "]";
	}

}