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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class StudentController {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;

    // Create a new student
    @PostMapping("/students")
    public ResponseEntity<StudentModel> saveStudent(@RequestBody @Valid StudentRecordDto studentRecordDto) {
        var studentModel = new StudentModel();
        BeanUtils.copyProperties(studentRecordDto, studentModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentRepository.save(studentModel));
    }

    // Get all students
    @GetMapping("/students")
    public ResponseEntity<List<StudentModel>> getAllStudents() {
        List<StudentModel> studentsList = studentRepository.findAll();
        if(!studentsList.isEmpty()) {
            for(StudentModel student : studentsList) {
                UUID id = student.getIdPerson();
                student.add(linkTo(methodOn(StudentController.class).getOneStudent(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(studentsList);
    }

    // Get one student
    @GetMapping("/students/{id}")
    public ResponseEntity<Object> getOneStudent(@PathVariable(value="id") UUID id) {
        var studentO = studentRepository.findById(id);
        if(studentO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }
        studentO.get().add(linkTo(methodOn(StudentController.class).getAllStudents()).withRel("Students List"));
        return ResponseEntity.status(HttpStatus.OK).body(studentO.get());
    }

    // Update a student
    @PutMapping("/students/{id}")
    public ResponseEntity<Object>
    updateStudent(@PathVariable(value="id") UUID id, @RequestBody @Valid StudentRecordDto studentRecordDto) {
        Optional<StudentModel> studentO = studentRepository.findById(id);
        if(studentO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }
        StudentModel studentModel = studentO.get();
        BeanUtils.copyProperties(studentRecordDto, studentModel);
        return ResponseEntity.status(HttpStatus.OK).body(studentRepository.save(studentModel));
    }

    // Delete a student
    @DeleteMapping("/students/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable(value = "id") UUID id) {
        Optional<StudentModel> studentO = studentRepository.findById(id);
        if(studentO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }
        studentRepository.delete(studentO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Student deleted.");
    }

    // Set a course to a student
    @PutMapping("/students/{id}/assign-course/{idCourse}")
    public ResponseEntity<Object> assignCourseToStudent(
            @PathVariable(value = "id") UUID idStudent,
            @PathVariable(value = "idCourse") UUID idCourse) {

        Optional<StudentModel> studentO = studentRepository.findById(idStudent);
        Optional<CourseModel> courseO = courseRepository.findById(idCourse);

        if(studentO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found.");
        }
        if(courseO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
        }

        StudentModel student = studentO.get();
        CourseModel course = courseO.get();

        student.setCourse(course);
        studentRepository.save(student);

        return ResponseEntity.status(HttpStatus.OK).body("Course assigned to student successfully.");
    }


}
