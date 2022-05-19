package models.dao;

import models.Department;
import models.News;
import models.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oDepartmentDao implements DepartmentDao {

    private final Sql2o sql2o;
    public  Sql2oDepartmentDao(Sql2o sql2o){
        this.sql2o=sql2o;
    }


    @Override
    public void add(Department department) {
        String sql = "INSERT INTO departments (name, description, size) VALUES (:name, :description, :size)";
        try (Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(department)
                    .executeUpdate()
                    .getKey();
            department.setId(id);
        } catch (Sql2oException exception){
            System.out.println(exception);
        }
    }

    @Override
    public void addUserToDepartment(User user, Department department) {
        String sql = "INSERT INTO users_departments (user_id, department_id) VALUES (:user_id, :department_id)";
        try (Connection connection=sql2o.open()){
            connection.createQuery(sql)
                    .addParameter("user_id", user.getId())
                    .addParameter("department_id", department.getId())
                    .executeUpdate();
            String users= "SELECT user_id FROM users_departments";
            List<Integer> size = connection.createQuery(users)
                    .executeAndFetch(Integer.class);
            String updateDepartmentSize="UPDATE departments SET size=:size WHERE id=:id";
            connection.createQuery(updateDepartmentSize).addParameter("id",department.getId())
                    .addParameter("size",size.size())
                    .executeUpdate();
        } catch (Sql2oException exception){
            System.out.println(exception);
        }
    }

    @Override
    public List<Department> getAll() {
        try (Connection con=sql2o.open()){
            String sql= "SELECT * FROM departments";
            return con.createQuery(sql)
                    .executeAndFetch(Department.class);
        }    }

    @Override
    public List<User> getAllUsersForADepartment(int department_id) {
        List<User> users = new ArrayList<>();
        try (Connection con=sql2o.open()){
            String sql= "SELECT user_id FROM users_departments WHERE department_id=:department_id";
            List<Integer> user_ids=con.createQuery(sql)
                    .addParameter("department_id",department_id)
                    .executeAndFetch(Integer.class);
            for(Integer id : user_ids){
                String employeeResults="SELECT * FROM users WHERE id=:id";
                users.add(con.createQuery(employeeResults)
                        .addParameter("id",id)
                        .executeAndFetchFirst(User.class));

            }
            return users;
        }
    }

    @Override
    public Department findById(int id) {
        try (Connection con=sql2o.open()){
            String sql= "SELECT * FROM departments WHERE id=:id";
            return con.createQuery(sql)
                    .addParameter("id",id)
                    .executeAndFetchFirst(Department.class);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from departments WHERE id=:id";
        String deleteJoin = "DELETE from employees_departments WHERE user_id = :user_id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();

            con.createQuery(deleteJoin)
                    .addParameter("user_id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAll() {
        try (Connection con=sql2o.open()){
            String sql="DELETE FROM departments";
            String sqlEmployeesDepartments="DELETE FROM employees_departments";
            con.createQuery(sql).executeUpdate();
            con.createQuery(sqlEmployeesDepartments).executeUpdate();
        } catch (Sql2oException e){
            System.out.println(e);
        }
    }

}
