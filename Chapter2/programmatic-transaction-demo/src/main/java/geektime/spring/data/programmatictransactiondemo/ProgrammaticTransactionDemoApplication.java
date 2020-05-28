package geektime.spring.data.programmatictransactiondemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@SpringBootApplication
@Slf4j
public class ProgrammaticTransactionDemoApplication implements CommandLineRunner {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private TransactionTemplate transactionTemplate;

	public static void main(String[] args) {
		SpringApplication.run(ProgrammaticTransactionDemoApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		//查看数据库中的行数 0
		log.info("COUNT BEFORE TRANSACTION：{}",getCount());
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				//执行一条插入语句
				jdbcTemplate.update("INSERT INTO FOO(ID,BAR) VALUES (1,'aaa')");
				//查看数据库中的行数 1
				log.info("COUNT IN TRANSACTION：{}",getCount());
				//执行回滚
				transactionStatus.setRollbackOnly();
			}
		});
		//查看数据库中的行数 0
		log.info("COUNT AFTER TRANSACTION：{}",getCount());
	}

	/**
	 * 获取当前数据库中行数
	 * @return
	 */
	private long getCount(){
		return (long)jdbcTemplate.queryForList("SELECT COUNT(*) AS CNT FROM FOO").get(0).get("CNT");
	}
}
