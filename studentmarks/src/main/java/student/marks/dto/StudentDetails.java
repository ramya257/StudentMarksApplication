package student.marks.dto;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.data.redis.core.RedisHash;

@Entity
@RedisHash("student_details")
public class StudentDetails implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	private String	name;
	
	  @OneToMany(mappedBy = "student_details", cascade = CascadeType.ALL)
	    private Set<StudentMarks> studentMarks;
	  
	public StudentDetails() {
		
	}
	public StudentDetails(String name) {
		
		this.name=name;
	}

	public StudentDetails(int i, String string) {
		this.id=i;
		this.name=string;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
