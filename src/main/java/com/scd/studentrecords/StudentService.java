package com.scd.studentrecords;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    // All students, or only those whose name contains the search text
    public List<Student> getStudents(String name) {
        if (name == null || name.trim().isEmpty()) {
            return repository.findAll();
        }
        return repository.searchByName(name.trim());
    }

    // Returns null if the student does not exist
    public Student getStudent(String regNo) {
        return repository.findByRegNo(regNo);
    }

    // Returns an error message, or null if the student is valid
    public String validate(String regNo, String name, String department, Double cgpa) {
        if (regNo == null || regNo.trim().isEmpty()) {
            return "Registration number is required.";
        }
        if (name == null || name.trim().isEmpty()) {
            return "Name is required.";
        }
        if (department == null || department.trim().isEmpty()) {
            return "Department is required.";
        }
        if (cgpa == null) {
            return "CGPA is required.";
        }
        if (cgpa < 0.0 || cgpa > 4.0) {
            return "CGPA must be between 0.0 and 4.0.";
        }
        return null;
    }

    // Adds a new student. Returns an error message, or null on success.
    public String addStudent(Student s) {
        String error = validate(s.getRegNo(), s.getName(), s.getDepartment(), s.getCgpa());
        if (error != null) {
            return error;
        }
        s.setRegNo(s.getRegNo().trim());
        s.setName(s.getName().trim());
        s.setDepartment(s.getDepartment().trim());
        if (repository.existsByRegNo(s.getRegNo())) {
            return "A student with reg no " + s.getRegNo() + " already exists.";
        }
        repository.insert(s);
        return null;
    }

    // Updates an existing student. Returns an error message, or null on success.
    public String updateStudent(Student s) {
        String error = validate(s.getRegNo(), s.getName(), s.getDepartment(), s.getCgpa());
        if (error != null) {
            return error;
        }
        if (!repository.existsByRegNo(s.getRegNo())) {
            return "Student " + s.getRegNo() + " was not found.";
        }
        s.setName(s.getName().trim());
        s.setDepartment(s.getDepartment().trim());
        repository.update(s);
        return null;
    }

    // Deletes a student. Returns an error message, or null on success.
    public String deleteStudent(String regNo) {
        if (!repository.existsByRegNo(regNo)) {
            return "Student " + regNo + " was not found.";
        }
        repository.deleteByRegNo(regNo);
        return null;
    }
}
