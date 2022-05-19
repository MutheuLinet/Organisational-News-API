package models.dao;

import models.Department;
import models.User;

import java.util.List;

public interface UserDao {

    //CRUD

    //create a user
    void add (User user);

    //read
   List<User> getAll();
   User findById(int id);
   List<User> getAllUsersByDepartment(int user_id);


    //update
//    void update(String name, String position, String role, int department_id);
//    //delete
//    void deleteById(int id);
//    void deleteAll();



}
