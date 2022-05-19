package models.dao;

import models.Department;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static org.junit.jupiter.api.Assertions.*;

public class Sql2oDepartmentDaoTest {
    private Connection conn;
    private Sql2oDepartmentDao departmentDao;

    @BeforeEach
    public void setUp() throws Exception {
//        Sql2o sql2o = new Sql2o ("jdbc:h2:~/test.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'", "", "");
        Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/organisational_news_test", "postgres", "myPassword");
        departmentDao = new Sql2oDepartmentDao(sql2o);
        conn = sql2o.open();
    }
    @AfterEach
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void noDepartmentsReturnsEmptyList() throws Exception {
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    public void add_addsDepartment_true() {
        Department testDepartment = new Department("HR", "Human Resource Managers", 5);
        departmentDao.add(testDepartment);
        assertTrue(departmentDao.getAll().contains(testDepartment));
    }

    @Test
    public void addedDepartmentsAreReturnedFromGetAll() throws Exception {
        assertEquals(1, departmentDao.getAll().size());
    }


}