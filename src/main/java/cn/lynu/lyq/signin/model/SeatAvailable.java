package cn.lynu.lyq.signin.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Seatavailable generated by MyEclipse Persistence Tools
 */
@Entity
public class SeatAvailable implements java.io.Serializable {
	private static final long serialVersionUID = -1364205516925287917L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Integer row;

	private Integer col;

	// Constructors

	/** default constructor */
	public SeatAvailable() {}

	/** full constructor */
	public SeatAvailable(Integer id, Integer row, Integer col) {
		this.id = id;
		this.row = row;
		this.col = col;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRow() {
		return this.row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Integer getCol() {
		return this.col;
	}

	public void setCol(Integer col) {
		this.col = col;
	}

}