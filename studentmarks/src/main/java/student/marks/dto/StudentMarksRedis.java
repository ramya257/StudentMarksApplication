package student.marks.dto;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;

import com.fasterxml.jackson.annotation.JsonProperty;

@RedisHash("student_marks")
public class StudentMarksRedis implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private int paper3_marks;
	
	private int paper4_marks;
	
	private int student_details_id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getPaper3_marks() {
		return paper3_marks;
	}
	public void setPaper3_marks(int paper3_marks) {
		this.paper3_marks = paper3_marks;
	}
	public int getPaper4_marks() {
		return paper4_marks;
	}
	public void setPaper4_marks(int paper4_marks) {
		this.paper4_marks = paper4_marks;
	}
	public int getStudent_details_id() {
		return student_details_id;
	}
	public void setStudent_details_id(int student_details_id) {
		this.student_details_id = student_details_id;
	}
	
}
