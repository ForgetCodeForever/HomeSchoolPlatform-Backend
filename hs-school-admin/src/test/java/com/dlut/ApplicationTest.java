package com.dlut;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Objects;

@SpringBootTest(classes = HsSchoolAdminApplication.class)
public class ApplicationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void getDruidSourceTest() {
        System.out.println(Objects.requireNonNull(jdbcTemplate.getDataSource()).getClass());
    }

}

