package cn.lynu.lyq.signin.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Entity
public class AbsentRequest implements java.io.Serializable {
	private static final long serialVersionUID = 4755221586493429503L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="stu_id")
	private Student student;

	private Date reqDate;

	public AbsentRequest() {}

	/** minimal constructor */
	public AbsentRequest(Student student) {
		this.student = student;
	}

	/** full constructor */
	public AbsentRequest(Student student, Date reqDate) {
		this.student = student;
		this.reqDate = reqDate;
	}

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

	public Date getReqDate() {
		return this.reqDate;
	}

	public void setReqDate(Date reqDate) {
		this.reqDate = reqDate;
	}

}