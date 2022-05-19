package models.dao;

import models.Department;
import models.User;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oUserDao implements UserDao{


    private final Sql2o sql2o;
    public  Sql2oUserDao(Sql2o sql2o){
        this.sql2o=sql2o;
    }

    @Override
    public void add(User user) {
        String sql = "INSERT INTO users (name, position, role, department_id) VALUES (:name, :position, :role, :department_id)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(user)
                    .executeUpdate()
                    .getKey();
            user.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<User> getAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM users")
                    .executeAndFetch(User.class);
        }
    }

    @Override
    public User findById(int id) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM users WHERE id=:id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(User.class);
        }
    }

    @Override
    public List<User> getAllUsersByDepartment(int department_id) {
        String sql = "SELECT * FROM users WHERE department_id = :department_id";
        try (Connection connection = sql2o.open()){
            return connection.createQuery(sql)
                    .addParameter("department_id",department_id)
                    .executeAndFetch(User.class);
        }

//        List<User>departmentList=new ArrayList<>();
////        String sql = "SELECT department_id FROM users_departments WHERE user_id=:user_id";
//
//        try(Connection con = sql2o.open()){
////            List<Integer>department_ids = con.createQuery(sql)
////                    .addParameter("user_id", user_id)
////                    .executeAndFetch(Integer.class);
//            Integer id = department_id;
////            for (){
//                String usersDepartments = "SELECT * FROM departments WHERE id=:department_id";
//                departmentList.add(con.createQuery(usersDepartments)
//                        .addParameter("id", department_id)
//                        .executeAndFetchFirst(Department.class));
//            }
//            return  departmentList;
//        }
    }

//    @Override
//    public void update(int id, String name, String position, String role, int department_id) {
//
//    }
//
//    @Override
//    public void deleteById(int id) {
//
//    }
//
//    @Override
//    public void deleteAll() {
//
//    }

}
