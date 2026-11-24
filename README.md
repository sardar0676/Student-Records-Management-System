# Student Records (Assignment 2, SE_A)

Spring Boot + Thymeleaf + JdbcTemplate + MySQL.
Browser -> StudentController -> StudentService -> StudentRepository -> MySQL

## Run
1. Run `sql/01_create_database.sql`, then `sql/02_sample_data.sql` in MySQL (8 rows expected).
2. Put your MySQL password in `src/main/resources/application.properties`.
3. `./mvnw compile`  (Windows: `.\mvnw compile`)  -> BUILD SUCCESS
4. `./mvnw spring-boot:run`  -> "Tomcat started on port 8080"
5. Open http://localhost:8080/students

## Endpoints
| # | Method and URL | Page |
|---|----------------|------|
| 1 | GET /students (?name=) | students.html |
| 2 | GET /students/{regNo} | student-details.html |
| 3 | GET /students/new | student-form.html |
| 4 | POST /students | redirect /students |
| 5 | GET /students/{regNo}/edit | student-form.html |
| 6 | POST /students/{regNo}/update | redirect /students |
| 7 | POST /students/{regNo}/delete | redirect /students |
