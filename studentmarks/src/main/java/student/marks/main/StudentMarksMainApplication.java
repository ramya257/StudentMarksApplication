package student.marks.main;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import student.marks.dao.StudentDetailsDao;
import student.marks.dao.StudentMarksDao;
import student.marks.dto.StudentDetails;
import student.marks.dto.StudentMarks;

@SpringBootApplication

@EnableJpaRepositories("student.marks.dao")
@EntityScan("student.marks.dto")
public class StudentMarksMainApplication implements CommandLineRunner {

	
	@Autowired
	StudentDetailsDao studentDetailsDao;
	
	@Autowired
	StudentMarksDao studentMarksDao;
	
	 public static void main(String[] args) {
	        SpringApplication.run(StudentMarksMainApplication.class, args);
	    }
	@Override
	public void run(String... args) throws Exception {
		
		Iterable<StudentDetails> studentDetails=studentDetailsDao.findAll();
		
		for ( StudentDetails item:studentDetails) {
			System.out.println(item.getId() + item.getName());
			//studentMarksDao.save(new StudentMarks(10,20,item));
			Iterable<StudentMarks> studentMarks=studentMarksDao.findAll();
			studentMarks.forEach(item1-> System.out.println(item1.getPaper1_marks()));
		}
		
		
		
		
		
	}
}
