package geektime.spring.data.simplejdbcdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Repository
public class FooDto {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;

    /**
     * 插入
     */
    public void inserData(){
        //使用jdbcTemplate完成插入
        //将数组转换为字符值，并使用forEach遍历
        Arrays.asList("b","c").forEach(bar->{
            jdbcTemplate.update("INSERT INTO FOO(BAR) VALUES (?)",bar);
        });

        //使用simpleJdbcInsert完成插入
        HashMap<String,String> row = new HashMap<>();
        row.put("BAR","bar");
        Number id = simpleJdbcInsert.executeAndReturnKey(row);
        log.info("ID of d: {}",id.longValue());
    }

    public void listData(){
        //查询表中行数，并返回一个包装类Long
        log.info("Count:{}",jdbcTemplate.queryForObject("SELECT COUNT(*) FROM FOO",Long.class));
        //查询数据库中所有bar 列的数据
        List<String> list = jdbcTemplate.queryForList("SELECT BAR FROM FOO", String.class);
        list.forEach(s -> log.info("Bar：{}",s));

        List<Foo> fooList = jdbcTemplate.query("SELECT * FROM FOO", new RowMapper<Foo>() {
            @Override
            public Foo mapRow(ResultSet resultSet, int i) throws SQLException {
                return Foo.builder()
                        .id(resultSet.getLong(1))//返回第一列的值，返回值类型为Long
                        .bar(resultSet.getString(2))//返回第二列的值，返回值类型为String
                        .build();
            }
        });
        fooList.forEach(f->log.info("Foo：{}",f));




    }
}
