package models.dao;

import models.Department;
import models.News;
import models.User;

import java.util.List;

public interface DepartmentDao {

    //CRUD

    //create a department
    void add (Department department);
    void addUserToDepartment(User user, Department department);

    //read
    List<Department> getAll();
    List<User> getAllUsersForADepartment(int id);
    Department findById(int id);


    //delete
    void deleteById(int id);
    void clearAll();


}
