package student.marks.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import student.marks.dao.StudentMarksDao;
import student.marks.dto.StudentMarks;
import student.marks.dto.StudentMarksRedis;


@Service
public class StudentMarksServiceImpl implements StudentMarksService{

	@Autowired
	StudentMarksDao studentMarksDao;
	
	@Autowired
	RedisTemplate<String, Object> redisTemplate;
	
	@Override
	public Optional<StudentMarks> getStudentMarksById(int studentId) {
		return studentMarksDao.findById(studentId);
	}

	@Override
	public StudentMarksRedis getStudentMarksByIdFromRedis(String studentKey) {
		
		//for each student pulling paper 3 and paper 4 marks from redis
		ObjectMapper mapper= new ObjectMapper();
		StudentMarksRedis studentMarksFromRedis=null;
		try {
			studentMarksFromRedis=mapper.readValue(redisTemplate.opsForValue().get(studentKey).toString(), StudentMarksRedis.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return studentMarksFromRedis;
	}

}
