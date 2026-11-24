package com.scd.studentrecords;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository {

    private final JdbcTemplate jdbc;

    // Spring creates the JdbcTemplate from application.properties
    public StudentRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Turns ONE row of the result into ONE Student object
    private final RowMapper<Student> studentRowMapper = (rs, rowNum) -> new Student(
            rs.getInt("id"),
            rs.getString("reg_no"),
            rs.getString("name"),
            rs.getString("department"),
            rs.getDouble("cgpa"));

    public List<Student> findAll() {
        return jdbc.query("SELECT * FROM students ORDER BY name", studentRowMapper);
    }

    // names that CONTAIN the text, ignoring upper/lower case
    public List<Student> searchByName(String name) {
        return jdbc.query(
                "SELECT * FROM students WHERE LOWER(name) LIKE LOWER(?) ORDER BY name",
                studentRowMapper,
                "%" + name + "%");
    }

    // the student with this reg no, or null if there is none
    public Student findByRegNo(String regNo) {
        List<Student> list = jdbc.query(
                "SELECT * FROM students WHERE reg_no = ?",
                studentRowMapper,
                regNo);
        return list.isEmpty() ? null : list.get(0);
    }

    // true if a student with this reg no already exists
    public boolean existsByRegNo(String regNo) {
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM students WHERE reg_no = ?",
                Integer.class,
                regNo);
        return count != null && count > 0;
    }

    // insert a new row; return the number of rows inserted
    public int insert(Student s) {
        return jdbc.update(
                "INSERT INTO students (reg_no, name, department, cgpa) VALUES (?, ?, ?, ?)",
                s.getRegNo(), s.getName(), s.getDepartment(), s.getCgpa());
    }

    // change name, department and cgpa of the row with s.getRegNo()
    public int update(Student s) {
        return jdbc.update(
                "UPDATE students SET name = ?, department = ?, cgpa = ? WHERE reg_no = ?",
                s.getName(), s.getDepartment(), s.getCgpa(), s.getRegNo());
    }

    // delete the row with this reg no
    public int deleteByRegNo(String regNo) {
        return jdbc.update("DELETE FROM students WHERE reg_no = ?", regNo);
    }
}
