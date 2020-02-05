package com.sydml.transaction;

import com.sydml.transaction.service.User1ServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionApplicationTests {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static String[] usernames = {"tom", "jan", "rose", "John", "James", "Chase", "Eric", "Justin"};

    @Test
    public void contextLoads() {
        User1ServiceImpl bean = applicationContext.getBean(User1ServiceImpl.class);
        System.out.println();
    }
    
    @Test
    public void insert(){
        long start = System.currentTimeMillis();
        Random random = new Random();
        jdbcTemplate.batchUpdate("insert into user (username,loginTime) values(?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                for (int j = 0; j < 100000; j++) {
                    preparedStatement.setString(1, usernames[random.nextInt(8)]);
                    preparedStatement.setObject(2, LocalDateTime.now().plusDays(-random.nextInt(10)));
                }
            }

            @Override
            public int getBatchSize() {
                return 100000;
            }
        });
        System.out.println("每批耗时：" + (System.currentTimeMillis() - start));
    }
}
