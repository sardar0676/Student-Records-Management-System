package com.scd.studentrecords;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    // 1. List all students, or search by name
    @GetMapping("/students")
    public String list(@RequestParam(required = false) String name, Model model) {
        model.addAttribute("students", service.getStudents(name));
        model.addAttribute("name", name);
        return "students";
    }

    // 3. Empty form (declared before /{regNo} so "new" is not read as a reg no)
    @GetMapping("/students/new")
    public String newForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("editing", false);
        return "student-form";
    }

    // 2. One student, or a "not found" message
    @GetMapping("/students/{regNo}")
    public String details(@PathVariable String regNo, Model model) {
        Student student = service.getStudent(regNo);
        if (student == null) {
            model.addAttribute("message", "Student " + regNo + " was not found.");
        } else {
            model.addAttribute("student", student);
        }
        return "student-details";
    }

    // 4. Save a new student
    @PostMapping("/students")
    public String add(@RequestParam String regNo,
                      @RequestParam String name,
                      @RequestParam String department,
                      @RequestParam(required = false) Double cgpa,
                      Model model) {
        Student s = new Student(0, regNo, name, department, cgpa);
        String error = service.addStudent(s);
        if (error != null) {
            model.addAttribute("student", s);
            model.addAttribute("editing", false);
            model.addAttribute("error", error);
            return "student-form";
        }
        return "redirect:/students";
    }

    // 5. Form filled with the student's data
    @GetMapping("/students/{regNo}/edit")
    public String editForm(@PathVariable String regNo, Model model) {
        Student student = service.getStudent(regNo);
        if (student == null) {
            model.addAttribute("message", "Student " + regNo + " was not found.");
            return "student-details";
        }
        model.addAttribute("student", student);
        model.addAttribute("editing", true);
        return "student-form";
    }

    // 6. Save the changes
    @PostMapping("/students/{regNo}/update")
    public String update(@PathVariable String regNo,
                         @RequestParam String name,
                         @RequestParam String department,
                         @RequestParam(required = false) Double cgpa,
                         Model model) {
        Student s = new Student(0, regNo, name, department, cgpa);
        String error = service.updateStudent(s);
        if (error != null) {
            model.addAttribute("student", s);
            model.addAttribute("editing", true);
            model.addAttribute("error", error);
            return "student-form";
        }
        return "redirect:/students";
    }

    // 7. Delete a student
    @PostMapping("/students/{regNo}/delete")
    public String delete(@PathVariable String regNo) {
        service.deleteStudent(regNo);
        return "redirect:/students";
    }
}
