package student.marks.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import student.marks.dto.StudentMarks;

@Repository
public interface StudentMarksDao  extends CrudRepository<StudentMarks, Integer>{

}
