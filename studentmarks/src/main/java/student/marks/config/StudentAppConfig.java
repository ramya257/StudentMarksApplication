package student.marks.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;


import student.marks.dao.StudentMarksDao;
import student.marks.service.StudentMarksService;
import student.marks.service.StudentMarksServiceImpl;


@Configuration
@EnableRedisRepositories
public class StudentAppConfig {
	
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		return new JedisConnectionFactory();
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(){
		final RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
	    template.setConnectionFactory(jedisConnectionFactory());
	    template.setKeySerializer(new StringRedisSerializer()); 
	    template.setValueSerializer(new StringRedisSerializer());
	    return template;		
	}
	
	
}
