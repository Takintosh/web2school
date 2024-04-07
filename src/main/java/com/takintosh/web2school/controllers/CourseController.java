package com.takintosh.web2school.controllers;

import com.takintosh.web2school.dtos.CourseRecordDto;
import com.takintosh.web2school.models.CourseModel;
import com.takintosh.web2school.repositories.CourseRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    // Get all courses
    @GetMapping("/")
    public ModelAndView coursesList() {
        ModelAndView mv = new ModelAndView("courses/ReadAll");
        List<CourseModel> courses = courseRepository.findAll();
        mv.addObject("courses", courses);
        return mv;
    }

    // Create a new course form
    @GetMapping("/create")
    public String courseCreateGet() {
        return "courses/Create";
    }

    // Create a new course
    @PostMapping("/create")
    public String courseCreatePost(@Valid @ModelAttribute CourseRecordDto courseRecordDto, BindingResult result) {
        if (result.hasErrors()) {
            return "courses/Create";
        }
        CourseModel courseModel = new CourseModel();
        BeanUtils.copyProperties(courseRecordDto, courseModel);
        courseRepository.save(courseModel);
        return "redirect:/courses/";
    }

    // Get one course
    @GetMapping("/show/{id}")
    public ModelAndView getOneCourse(@PathVariable(value="id") UUID id) {
        ModelAndView mv = new ModelAndView("courses/ReadOne");
        Optional<CourseModel> courseO = courseRepository.findById(id);
        mv.addObject("name", courseO.get().getName());
        mv.addObject("description", courseO.get().getDescription());
        return mv;
    }

    // Update a course form
    @GetMapping("/update/{id}")
    public ModelAndView updateCourseGet(@PathVariable(value="id") UUID id) {
        ModelAndView mv = new ModelAndView("courses/Update");
        Optional<CourseModel> courseO = courseRepository.findById(id);
        mv.addObject("name", courseO.get().getName());
        mv.addObject("description", courseO.get().getDescription());
        return mv;
    }

    // Update a course
    @PostMapping("/update/{id}")
    public String updateCoursePost(@Valid @ModelAttribute CourseRecordDto courseRecordDto, BindingResult result, @PathVariable(value="id") UUID id) {

        Optional<CourseModel> courseO = courseRepository.findById(id);
        if(courseO.isEmpty()) {
            return "redirect:/courses/";
        }
        if(result.hasErrors()) {
            return "courses/Update";
        }
        CourseModel courseModel = courseO.get();
        BeanUtils.copyProperties(courseRecordDto, courseModel);
        courseRepository.save(courseModel);
        return "redirect:/courses/";
    }

    // Delete a course
    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable(value="id") UUID id) {

        Optional<CourseModel> courseO = courseRepository.findById(id);
        if(courseO.isEmpty()) {
            return "redirect:/courses/";
        }
        courseRepository.delete(courseO.get());
        return "redirect:/courses/";
    }

}