package com.takintosh.web2school.controllers;

import com.takintosh.web2school.dtos.StudentRecordDto;
import com.takintosh.web2school.models.CourseModel;
import com.takintosh.web2school.models.StudentModel;
import com.takintosh.web2school.repositories.CourseRepository;
import com.takintosh.web2school.repositories.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;

    // Create a new student form
    @GetMapping("/create")
    public ModelAndView studentCreateGet() {
        ModelAndView mv = new ModelAndView("students/Create");
        List<CourseModel> courses = courseRepository.findAll();
        mv.addObject("courses", courses);
        return mv;
    }

    // Create a new student
    @PostMapping("/create")
    public String studentCreatePost(@Valid @ModelAttribute StudentRecordDto studentRecordDto, BindingResult result) {
        if (result.hasErrors()) {
            return "students/Create";
        }

        StudentModel studentModel = new StudentModel();
        BeanUtils.copyProperties(studentRecordDto, studentModel);

        CourseModel course = courseRepository.findById(studentRecordDto.idCourse()).get();
        studentModel.setCourse(course);

        studentRepository.save(studentModel);
        return "redirect:/students/";
    }

    // Get all students
    @GetMapping("/")
    public ModelAndView studentsList() {
        ModelAndView mv = new ModelAndView("students/ReadAll");
        List<StudentModel> students = studentRepository.findAll();
        mv.addObject("students", students);
        return mv;
    }

    // Get one student
    @GetMapping("/show/{id}")
    public ModelAndView getOneStudent(@PathVariable(value="id") UUID id) {
        ModelAndView mv = new ModelAndView("students/ReadOne");
        Optional<StudentModel> studentO = studentRepository.findById(id);
        mv.addObject("name", studentO.get().getName());
        mv.addObject("surname", studentO.get().getSurname());
        mv.addObject("identification", studentO.get().getIdentification());
        mv.addObject("email", studentO.get().getEmail());
        mv.addObject("phone", studentO.get().getPhone());
        mv.addObject("address", studentO.get().getAddress());
        mv.addObject("city", studentO.get().getCity());
        mv.addObject("state", studentO.get().getState());
        mv.addObject("country", studentO.get().getCountry());
        mv.addObject("zipCode", studentO.get().getZipCode());
        mv.addObject("registration", studentO.get().getRegistration());
        mv.addObject("semester", studentO.get().getSemester());
        mv.addObject("status", studentO.get().getStatus());
        mv.addObject("course", studentO.get().getCourse());
        return mv;
    }

    // Update a student form
    @GetMapping("/update/{id}")
    public ModelAndView updateStudentGet(@PathVariable(value="id") UUID id) {
        ModelAndView mv = new ModelAndView("students/Update");
        Optional<StudentModel> studentO = studentRepository.findById(id);
        List<CourseModel> courses = courseRepository.findAll();
        mv.addObject("name", studentO.get().getName());
        mv.addObject("surname", studentO.get().getSurname());
        mv.addObject("identification", studentO.get().getIdentification());
        mv.addObject("email", studentO.get().getEmail());
        mv.addObject("phone", studentO.get().getPhone());
        mv.addObject("address", studentO.get().getAddress());
        mv.addObject("city", studentO.get().getCity());
        mv.addObject("state", studentO.get().getState());
        mv.addObject("country", studentO.get().getCountry());
        mv.addObject("zipCode", studentO.get().getZipCode());
        mv.addObject("registration", studentO.get().getRegistration());
        mv.addObject("semester", studentO.get().getSemester());
        mv.addObject("status", studentO.get().getStatus());
        mv.addObject("courseAssigned", studentO.get().getCourse());
        mv.addObject("courses", courses);
        return mv;
    }
    // Update a student
    @PostMapping("/update/{id}")
    public String updateStudentPost(@Valid @ModelAttribute StudentRecordDto studentRecordDto, BindingResult result, @PathVariable(value="id") UUID id) {

        Optional<StudentModel> studentO = studentRepository.findById(id);
        if(studentO.isEmpty()) {
            return "redirect:/students/";
        }
        if(result.hasErrors()) {
            return "students/Update";
        }
        StudentModel studentModel = studentO.get();
        BeanUtils.copyProperties(studentRecordDto, studentModel);

        CourseModel course = courseRepository.findById(studentRecordDto.idCourse()).get();
        studentModel.setCourse(course);

        studentRepository.save(studentModel);
        return "redirect:/students/";
    }

    // Delete a student
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable(value="id") UUID id) {

        Optional<StudentModel> studentO = studentRepository.findById(id);
        if(studentO.isEmpty()) {
            return "redirect:/students/";
        }
        studentRepository.delete(studentO.get());
        return "redirect:/students/";
    }


}