package geektime.spring.data.simplejdbcdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

@SpringBootApplication
public class SimpleJdbcDemoApplication implements CommandLineRunner {

	@Autowired
	private FooDto fooDto;

	@Autowired
	private BatchFooDao batchFooDao;

	public static void main(String[] args) {
		SpringApplication.run(SimpleJdbcDemoApplication.class, args);
	}

	@Bean
	/**
	 * 配置SimpleJdbcInsert
	 */
	public SimpleJdbcInsert simpleJdbcInsert(JdbcTemplate jdbcTemplate){
		return new SimpleJdbcInsert(jdbcTemplate)
				//设置表的名称和主键ID
				.withTableName("FOO").usingGeneratedKeyColumns("ID");
	}
	@Override
	public void run(String... args) throws Exception {
		//fooDto.inserData();
		batchFooDao.batchInsert();
		fooDto.listData();
	}
}
