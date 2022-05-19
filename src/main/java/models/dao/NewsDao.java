package models.dao;

import models.News;

import java.util.List;

public interface NewsDao {
    //create
    void add(News news);

    //read
    List<News> allNews();
    News findById(int id);
    List<News> allNewsInDepartment(int department_id);




}
