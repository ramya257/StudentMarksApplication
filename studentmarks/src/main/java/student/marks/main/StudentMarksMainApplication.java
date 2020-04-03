package student.marks.main;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.core.RedisTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import student.marks.config.StudentRedis;
import student.marks.dao.StudentDetailsDao;
import student.marks.dao.StudentMarksDao;
import student.marks.dto.StudentDetails;
import student.marks.dto.StudentMarks;
import student.marks.dto.StudentMarksRedis;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories("student.marks.dao")
@EntityScan("student.marks.dto")
public class StudentMarksMainApplication implements CommandLineRunner {

	
	@Autowired
	StudentDetailsDao studentDetailsDao;
	
	@Autowired
	StudentMarksDao studentMarksDao;
	
	ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(StudentRedis.class);
	@SuppressWarnings("unchecked")
	RedisTemplate<String, Object> redisTemplate = (RedisTemplate<String, Object>) ctx.getBean("redisTemplate");
	
	static AnnotationConfigApplicationContext context=null;
	 public static void main(String[] args) {
		 
           
	        SpringApplication.run(StudentMarksMainApplication.class, args);
	    }
	@Override
	public void run(String... args) throws Exception {
		

		Iterable<StudentDetails> studentDetails=studentDetailsDao.findAll();
		
		//finding total list of students
		for ( StudentDetails item:studentDetails) {
			
			//for each student pulling paper 1 and paper 2 marks from mysql
			Iterable<StudentMarks> studentMarks=studentMarksDao.findAll();
			studentMarks.forEach(item1-> System.out.println(item1.getPaper1_marks()));
			
			//for each student pulling paper 3 and paper 4 marks from redis
			ObjectMapper mapper= new ObjectMapper();
			StudentMarksRedis studentMarksFromRedis=mapper.readValue(this.redisTemplate.opsForValue().get("student_"+item.getId()).toString(), StudentMarksRedis.class);
			
			
		}
		
		
		
		
		
	}
}
