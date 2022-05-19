package models.dao;

import models.News;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oNewsDao implements NewsDao {

    private final Sql2o sql2o;

    public Sql2oNewsDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void add(News news) {
        String sql = "INSERT INTO news (title, content, department_id) VALUES (:title, :content, :department_id)";
        try (Connection connection = sql2o.open()) {
            int id = (int) connection.createQuery(sql, true)
                    .bind(news)
                    .executeUpdate()
                    .getKey();
            news.setId(id);
        } catch (Sql2oException ex) {
        }
    }

    @Override
    public List<News> allNews() {
        String sql = "SELECT * FROM news";
        try(Connection connection = sql2o.open()) {
            return connection.createQuery(sql)
                    .executeAndFetch(News.class);
        }
    }

    @Override
    public News findById(int id) {
        String sql = "SELECT * FROM news WHERE id = :id";
        try(Connection connection = sql2o.open()) {
            return connection.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(News.class);
        }
    }

    @Override
    public List<News> allNewsInDepartment(int department_id) {
        String sql = "SELECT * FROM news WHERE department_id = :department_id";
        try (Connection connection = sql2o.open()){
            return connection.createQuery(sql)
                    .addParameter("departmentId", department_id)
                    .executeAndFetch(News.class);
        }
    }
}