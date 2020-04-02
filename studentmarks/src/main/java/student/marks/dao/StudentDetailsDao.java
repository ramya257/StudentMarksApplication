package student.marks.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import student.marks.dto.StudentDetails;

@Repository
public interface StudentDetailsDao extends CrudRepository<StudentDetails, Integer>{
	
	

}
