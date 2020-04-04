package student.marks;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import student.marks.config.StudentAppConfig;
import student.marks.dto.StudentMarks;
import student.marks.dto.StudentMarksRedis;
import student.marks.dto.StudentMarksSum;
import student.marks.service.StudentMarksService;


@SpringBootApplication
public class StudentMarksMainApplication{
	
	@Autowired
	StudentMarksService studentMarksService;
	
	 public static void main(String[] args) throws InterruptedException, ExecutionException {
		 ApplicationContext applicationContext = SpringApplication.run(StudentMarksMainApplication.class, args);
		 StudentMarksMainApplication myApp = (StudentMarksMainApplication) applicationContext.getBean(StudentMarksMainApplication.class);
		 System.out.println("Main thread start");
		 
		 ExecutorService es = Executors.newSingleThreadExecutor();
		 List<Future<StudentMarksSum>> stFutures= new ArrayList<>();
		 for(int i=1;i<=10;i++) {
			System.out.println("submitted callable task to calculate marks from SQL of student"+i); 
			Future<StudentMarks> mysqlResult = es.submit(myApp.new SqlTask(i)); 
			
			System.out.println("submitted callable task to calculate marks from REDIS of student"+i); 
			Future<StudentMarksRedis> redisResult = es.submit(myApp.new RedisTask("student_"+i));
			
			StudentMarks studentMarksMysql = mysqlResult.get();
			StudentMarksRedis studentMarksRedis=redisResult.get();
			
			if(mysqlResult.isDone() && redisResult.isDone()) {
				if(studentMarksMysql!=null && studentMarksRedis!=null) {
					stFutures.add(es.submit(myApp.new CalculateSumTasks(studentMarksMysql.getPaper1_marks(),studentMarksMysql.getPaper2_marks(),studentMarksRedis.getPaper3_marks(),studentMarksRedis.getPaper4_marks(), studentMarksMysql.getStudent_id().getId())));
				}	
			}
		}
		 es.shutdown();
		 System.out.println("Average of the class is:  "+ calculateAverage(stFutures,10));
		 System.out.println("Exiting the Main thread");
	        
	    }

	private static int calculateAverage(List<Future<StudentMarksSum>> stFuturesList, int studentsCount) throws InterruptedException, ExecutionException {
		int average = 0;
		for( Future<StudentMarksSum> future:stFuturesList) {
			average+=future.get().getTotalMarks();
		}
		average=average/studentsCount;
		return average;
	}
	
	public class SqlTask implements Callable<StudentMarks>{
		
		private int studentId;

		
		public  SqlTask(int studentId) {
		this.studentId=studentId;
		}
		@Override
		public StudentMarks call() throws Exception {
			Optional<StudentMarks> studentMarksSql=studentMarksService.getStudentMarksById(this.studentId);
			if(studentMarksSql.isPresent()) {
				return studentMarksSql.get();
			}
			return null;
		}
	}
	public class RedisTask implements Callable<StudentMarksRedis>
	{
	
		
		String studentKey;
		
		public RedisTask(String studentKey) {
				this.studentKey=studentKey;
		}


		@Override
		public StudentMarksRedis call() throws Exception {
			return studentMarksService.getStudentMarksByIdFromRedis(this.studentKey);
		}
	}
	
	public class CalculateSumTasks implements Callable<StudentMarksSum>{
		
		int sum;
		int paper1_marks;
		int paper2_marks;
		int paper3_marks;
		int paper4_marks;
		int studentId;

		public CalculateSumTasks(int paper1_marks, int paper2_marks, int paper3_marks, int paper4_marks, int studentId) {
			// TODO Auto-generated constructor stub
			this.paper1_marks=paper1_marks;
			this.paper2_marks=paper2_marks;
			this.paper3_marks=paper3_marks;
			this.paper4_marks=paper4_marks;
			this.studentId=studentId;
		}

		@Override
		public StudentMarksSum call() throws Exception {
			StudentMarksSum studentMarksSum= new StudentMarksSum();
			System.out.println("Paper 1: "+paper1_marks);
			System.out.println("Paper 2: "+paper2_marks);
			System.out.println("Paper 3: "+paper3_marks);
			System.out.println("Paper 4: "+paper4_marks);
			sum=paper1_marks+paper2_marks+paper3_marks+paper4_marks;
			studentMarksSum.setTotalMarks(sum);
			studentMarksSum.setStudentId(studentId);
			return studentMarksSum;
		}
		
		
		
	}

}
