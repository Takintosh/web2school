package com.takintosh.web2school.controllers;

import com.takintosh.web2school.dtos.CourseRecordDto;
import com.takintosh.web2school.models.CourseModel;
import com.takintosh.web2school.repositories.CourseRepository;
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
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    // Create a new course
    @PostMapping("/courses")
    public ResponseEntity<CourseModel> saveCourse(@RequestBody @Valid CourseRecordDto courseRecordDto) {
        var courseModel = new CourseModel();
        BeanUtils.copyProperties(courseRecordDto, courseModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseRepository.save(courseModel));
    }

    // Get all courses+
    @GetMapping("/courses")
    public ResponseEntity<List<CourseModel>> getAllCourses() {
        List<CourseModel> coursesList = courseRepository.findAll();
        if(!coursesList.isEmpty()) {
            for(CourseModel course : coursesList) {
                UUID id = course.getIdCourse();
                course.add(linkTo(methodOn(CourseController.class).getOneCourse(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(coursesList);
    }

    // Get one course
    @GetMapping("/courses/{id}")
    public ResponseEntity<Object> getOneCourse(@PathVariable(value="id") UUID id) {
        Optional<CourseModel> courseO = courseRepository.findById(id);
        if(courseO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
        }
        courseO.get().add(linkTo(methodOn(CourseController.class).getAllCourses()).withRel("Courses List"));
        return ResponseEntity.status(HttpStatus.OK).body(courseO.get());
    }

    // Update a course
    @PutMapping("/courses/{id}")
    public ResponseEntity<Object>
    updateCourse(@PathVariable(value="id") UUID id, @RequestBody @Valid CourseRecordDto courseRecordDto) {
        Optional<CourseModel> courseO = courseRepository.findById(id);
        if(courseO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
        }
        CourseModel courseModel = courseO.get();
        BeanUtils.copyProperties(courseRecordDto, courseModel);
        return ResponseEntity.status(HttpStatus.OK).body(courseRepository.save(courseModel));
    }

    // Delete a course
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Object> deleteCourse(@PathVariable(value="id") UUID id) {
        Optional<CourseModel> courseO = courseRepository.findById(id);
        if(courseO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
        }
        courseRepository.delete(courseO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Course deleted.");
    }

}
