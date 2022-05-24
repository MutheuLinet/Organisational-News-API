import com.google.gson.Gson;
import exceptions.ApiException;
import models.Department;
import models.News;
import models.User;
import models.dao.Sql2oDepartmentDao;
import models.dao.Sql2oNewsDao;
import models.dao.Sql2oUserDao;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import response.ResponseArray;
import response.ResponseObject;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;

public class App {
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    public static void main(String[] args) {
        Sql2oDepartmentDao sql2oDepartmentDao;
        Sql2oUserDao sql2oUserDao;
        Sql2oNewsDao sql2oNewsDao;
        Connection con;
        Gson gson = new Gson();

        String connectionString = "jdbc:postgresql://localhost:5432/organisational_news_test";
        Sql2o sql2o = new Sql2o(connectionString, "postgres", "myPassword");

        sql2oDepartmentDao = new Sql2oDepartmentDao(sql2o);
        sql2oUserDao = new Sql2oUserDao(sql2o);
        sql2oNewsDao = new  Sql2oNewsDao(sql2o);
        con = sql2o.open();

        port(getHerokuAssignedPort());
        staticFileLocation("/public");

        //create new department
        post("/departments/new", "application/json", (req, res) -> {
            System.out.println(req.body());
            Department department = gson.fromJson(req.body(), Department.class);
            sql2oDepartmentDao.add(department);
            ResponseObject responseObject=new ResponseObject(201, "Success! Department added!");
            responseObject.setData(new Object());
            res.status(201);
            return gson.toJson(responseObject);
        });

        //get all departments
        get("/departments","application/json",(request, response) -> {
            if(sql2oDepartmentDao.getAll().size()>0){
                List<Department>departmentList=sql2oDepartmentDao.getAll();
                ResponseArray responseArray = new ResponseArray(200, "Success");
                responseArray.setData(Collections.singletonList(departmentList));
                System.out.println(departmentList.size());
                return gson.toJson(responseArray);
            }
            else {
                return "{\"message\":\"Sorry, there are no registered departments .\"}";
            }
        });

        //get a department by its id
        get("/departments/:id","application/json",(request, response) -> {
            int department_id = Integer.parseInt(request.params("id"));
            if(sql2oDepartmentDao.findById(department_id)!= null){
                ResponseObject responseObject=new ResponseObject(201, "Success! Department found!");
                responseObject.setData(sql2oDepartmentDao.findById(department_id));
                response.status(201);
                return gson.toJson(responseObject);
            }
            else {
                return "{\"message\":\"Sorry, there are no departments by that ID given .\"}";
            }
        });

        //create and add a user to a department
        post("departments/:departmentId/users/new","application/json", (request, response) -> {
            int departmentId = Integer.parseInt(request.params("departmentId"));
            User user = gson.fromJson(request.body(), User.class);
            user.setDepartment_id(departmentId);
            sql2oUserDao.add(user);
            response.status(201);
            return gson.toJson(user);
        });

        //get all users
        get("/users", "application/json", (request, response) -> {
            if(sql2oUserDao.getAll().size() > 0){
                return gson.toJson(sql2oUserDao.getAll());
            }
            else {
                return "{\"message\":\"Sorry, there is no user listed in the database currently.\"}";
            }
        });

        //get a user by their id
        get("/users/:id", "application/json", (request, response) -> {
            int id=Integer.parseInt(request.params("id"));
            try {
                if(sql2oUserDao.findById(id) == null){
                    return "{\"message\":\"Sorry,there is no user with that id.\"}";
                }
                else {
                    return gson.toJson(sql2oUserDao.findById(id));
                }
            } catch ( ApiException E) {
                throw new ApiException(404, String.format("No employee with the id: \"%s\" exists",
                        request.params("id")));
            }
        });

        //get all users in a department
        get("/departments/:id/users","application/json",(request, response) -> {
            int department_id=Integer.parseInt(request.params("id"));
            Department department = sql2oDepartmentDao.findById(department_id);
            List<User> usersInDepartment = sql2oUserDao.getAllUsersByDepartment(department_id);
                 return gson.toJson(usersInDepartment);

        });

        //Create general news object relating to all departments
        post("/news/new","application/json", (request, response) -> {
            News news = gson.fromJson(request.body(), News.class);
            sql2oNewsDao.add(news);
            response.status(201);
            return gson.toJson(news);
        });

        //Get all news postings
        get("/","application/json", (request, response) -> {
            return gson.toJson(sql2oNewsDao.allNews());
        });

        //READ: Display news by ID
        get("/news/:id","application/json",(request, response) -> {
            int newsId = Integer.parseInt(request.params("id"));
            return gson.toJson(sql2oNewsDao.findById(newsId));
        });

        //Create news relating to a department
        post("department/:departmentId/news/new", "application/json", (request, response) -> {
            int departmentId = Integer.parseInt(request.params("departmentId"));
            News news = gson.fromJson(request.body(), News.class);
            news.setDepartment_id(departmentId);
            sql2oNewsDao.add(news);
            response.status(201);
            return gson.toJson(news);
        });

        //View all news belonging to a department
        get("department/:id/news", "application/json", (request, response) -> {
            int departmentId = Integer.parseInt(request.params("id"));
            Department department = sql2oDepartmentDao.findById(departmentId);
            List<News> allNews = sql2oNewsDao.allNewsInDepartment(departmentId);
            return gson.toJson(allNews);
        });
        after((req, res) ->{
            res.type("application/json");
        });

    }
}
