# Organisational News API

## Author

Linet Mutheu

### Description

This is a REST API for querying and retrieving scoped news, users/employees and departments information. There is news available to all users without navigating into any department, and news classified within departments..

### Prerequisites

You need to have the following installed on your machine

1. Java JDK
2. Gradle
3. JDK
4. Maven
5. Java IDE (Intellij)
6. Postman
7. Postgres

### Setup and Installation
To access this project on your local files, you can clone it using these steps

1. Open your terminal
2. Use this command to clone `$ git clone
   https://github.com/MutheuLinet/Organisational-News-API.git
3. This will clone the repository into your local folder
4. Navigate to the folder you cloned into, within src/main/java/App.java and open it with intellij.
5. Run App on localhost:4567
6. Via postman, use localhost url to navigate different routes accordingly either using post and get to test the api.

### HTTP Methods
### End Points
| URL(http://localhost:4567{URL Below}) | HTTP VERB | DESCRIPTION                            |
|---------------------------------------|-----------|----------------------------------------|
| /department/new                       | POST      | Add a new department                   |
| /departments                          | GET       | Get all departments                    |
| /departments/:id                      | GET       | Get specific department by its id      |
| /users/new                            | POST      | Add a user                             |
| /users                                | GET       | Get all users                          |
| /users/:id                            | GET       | Get a user by their id                 |
| /departments/:id/users                | GET       | Get all users in a specific department |
| /news/new                             | POST      | Add news                               |
| /                                     | GET       | Get all news                           |
| /news/:id                             | GET       | Get news by id                         |
| /department/:departmentId/news/new    | POST      | Post department news by department id  |
| /department/:departmentId/news        | GET       | Get department news by department id   |


### API Documentation
1. Creating Department 

{  "name":"HR",
   "description":"Editing of books",
   "size": 10 }

2. Creating User

{  "name":"Stacy",
   "position":"Assistant HR",
   "role":"Recruit employees"
}

3. Creating News
  
{  "title":"How to Motivate Employees",
   "content":"Revise employee hierarchy of needs"
}

### Contribution
You may also want to contribute to enhance a functionality:
1. Fork the repository to your GitHub account
2. Create a new branch (git switch -c ft-development)
3. Make the changes you intend
4. Add changes to reflect the changes made
5. Commit your changes (git commit -m 'additional info')
6. Push to the branch (git push origin ft-develop)
7. Create a Pull Request.

### Behavior Driven Development
The user is able to;
1. Run the App on your terminal
2. Add Department, User or News object.
3. View Department, User or News object details.
   (It also allows a user to Test the output before actual running of the App)

### Running Tests
1. Navigate to the folder you cloned into, within src/test/java/models and open it with intellij. Select the DepartmentTests, UserTests or NewsTest
2. Right click within the open test file and run the tests on your terminal.

### DATABASE
1. Launch postgres
2. Type in psql
   Run these commands
3. CREATE DATABASE organisational_news;
   \c organisational_news
4. CREATE TABLE IF NOT EXISTS departments (id serial PRIMARY KEY, name varchar, description varchar, size varchar);
5. CREATE TABLE IF NOT EXISTS users(id serial PRIMARY KEY, name varchar, position varchar, role varchar, department_id int);
6. CREATE TABLE IF NOT EXISTS news(id serial PRIMARY KEY, title varchar, content varchar, department_id int);


### Technologies Used
1. Java
2. Gradle
3. Spark
4. Junit

### License
This project is under the [MIT](LICENSE) licence

### Copyright
Copyright (c) 2022 Linet Mutheu
