package student.marks.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class StudentMarks {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private int paper1_marks;
	private int paper2_marks;
	@ManyToOne
	@JoinColumn(name="student_id")
	private StudentDetails student_details;
	
	public StudentMarks() {
		
	}
	
	public StudentMarks(int paper1_marks, int paper2_marks,StudentDetails student_details) {
		this.paper1_marks=paper1_marks;
		this.paper2_marks=paper2_marks;
		this.student_details=student_details;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public StudentDetails getStudent_id() {
		return student_details;
	}
	public void setStudent_id(StudentDetails student_details) {
		this.student_details = student_details;
	}

	public int getPaper1_marks() {
		return paper1_marks;
	}

	public void setPaper1_marks(int paper1_marks) {
		this.paper1_marks = paper1_marks;
	}

	public int getPaper2_marks() {
		return paper2_marks;
	}

	public void setPaper2_marks(int paper2_marks) {
		this.paper2_marks = paper2_marks;
	}
}
