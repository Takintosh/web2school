package com.takintosh.web2school.controllers;

import com.takintosh.web2school.dtos.ProfessorRecordDto;
import com.takintosh.web2school.dtos.StudentRecordDto;
import com.takintosh.web2school.models.CourseModel;
import com.takintosh.web2school.models.ProfessorModel;
import com.takintosh.web2school.models.StudentModel;
import com.takintosh.web2school.repositories.CourseRepository;
import com.takintosh.web2school.repositories.ProfessorRepository;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping("/professors")
public class ProfessorController {

    @Autowired
    ProfessorRepository professorRepository;
    @Autowired
    CourseRepository courseRepository;

    // Create a new student form
    @GetMapping("/create")
    public ModelAndView professorCreateGet() {
        ModelAndView mv = new ModelAndView("professors/Create");
        List<CourseModel> courses = courseRepository.findAll();
        mv.addObject("courses", courses);
        return mv;
    }

    // Create a new professor
    @PostMapping("/create")
    public String professorCreatePost(@Valid @ModelAttribute ProfessorRecordDto professorRecordDto, BindingResult result) {
        if (result.hasErrors()) {
            return "professors/Create";
        }

        ProfessorModel professorModel = new ProfessorModel();
        BeanUtils.copyProperties(professorRecordDto, professorModel);

        CourseModel course = courseRepository.findById(professorRecordDto.courseId()).get();
        professorModel.setCourse(course);

        professorRepository.save(professorModel);
        return "redirect:/professors/";
    }

    // Get all professors
    @GetMapping("/")
    public ModelAndView professorsList() {
        ModelAndView mv = new ModelAndView("professors/ReadAll");
        List<ProfessorModel> professors = professorRepository.findAll();
        mv.addObject("professors", professors);
        return mv;
    }

    // Get one professor
    @GetMapping("/show/{id}")
    public ModelAndView getOneProfessor(@PathVariable(value="id") UUID id) {
        ModelAndView mv = new ModelAndView("professors/ReadOne");
        Optional<ProfessorModel> professorO = professorRepository.findById(id);
        mv.addObject("name", professorO.get().getName());
        mv.addObject("surname", professorO.get().getSurname());
        mv.addObject("identification", professorO.get().getIdentification());
        mv.addObject("email", professorO.get().getEmail());
        mv.addObject("phone", professorO.get().getPhone());
        mv.addObject("address", professorO.get().getAddress());
        mv.addObject("city", professorO.get().getCity());
        mv.addObject("state", professorO.get().getState());
        mv.addObject("country", professorO.get().getCountry());
        mv.addObject("zipCode", professorO.get().getZipCode());
        mv.addObject("title", professorO.get().getTitle());
        mv.addObject("department", professorO.get().getDepartment());
        mv.addObject("siape", professorO.get().getSiape());
        mv.addObject("course", professorO.get().getCourse());
        return mv;
    }

    // Update a student form
    @GetMapping("/update/{id}")
    public ModelAndView updateProfessorGet(@PathVariable(value="id") UUID id) {
        ModelAndView mv = new ModelAndView("professors/Update");
        Optional<ProfessorModel> professorO = professorRepository.findById(id);
        List<CourseModel> courses = courseRepository.findAll();
        mv.addObject("name", professorO.get().getName());
        mv.addObject("surname", professorO.get().getSurname());
        mv.addObject("identification", professorO.get().getIdentification());
        mv.addObject("email", professorO.get().getEmail());
        mv.addObject("phone", professorO.get().getPhone());
        mv.addObject("address", professorO.get().getAddress());
        mv.addObject("city", professorO.get().getCity());
        mv.addObject("state", professorO.get().getState());
        mv.addObject("country", professorO.get().getCountry());
        mv.addObject("zipCode", professorO.get().getZipCode());
        mv.addObject("title", professorO.get().getTitle());
        mv.addObject("department", professorO.get().getDepartment());
        mv.addObject("siape", professorO.get().getSiape());
        mv.addObject("courseAssigned", professorO.get().getCourse());
        mv.addObject("courses", courses);
        return mv;
    }

    // Update a professor
    @PostMapping("/update/{id}")
    public String updateStudentPost(@Valid @ModelAttribute ProfessorRecordDto professorRecordDto, BindingResult result, @PathVariable(value="id") UUID id) {

        Optional<ProfessorModel> professorO = professorRepository.findById(id);
        if(professorO.isEmpty()) {
            return "redirect:/professors/";
        }
        if(result.hasErrors()) {
            return "professors/Update";
        }
        ProfessorModel professorModel = professorO.get();
        BeanUtils.copyProperties(professorRecordDto, professorModel);

        CourseModel course = courseRepository.findById(professorRecordDto.courseId()).get();
        professorModel.setCourse(course);

        professorRepository.save(professorModel);
        return "redirect:/professors/";
    }

    // Delete a professor
    @GetMapping("/delete/{id}")
    public String deleteProfessor(@PathVariable(value="id") UUID id) {
        Optional<ProfessorModel> professorO = professorRepository.findById(id);
        if(professorO.isEmpty()) {
            return "redirect:/professors/";
        }
        professorRepository.delete(professorO.get());
        return "redirect:/professors/";
    }

}