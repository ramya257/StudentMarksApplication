package student.marks.service;

import java.util.Optional;

import student.marks.dto.StudentMarks;
import student.marks.dto.StudentMarksRedis;

public interface StudentMarksService {
	
	
	public Optional<StudentMarks> getStudentMarksById(int studentId);
	
	public StudentMarksRedis getStudentMarksByIdFromRedis(String studentKey);

}
