package com.takintosh.web2school.controllers;

import com.takintosh.web2school.dtos.ProfessorRecordDto;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProfessorController {

    @Autowired
    ProfessorRepository professorRepository;
    @Autowired
    CourseRepository courseRepository;

    // Create a new professor
    @PostMapping("/professors")
    public ResponseEntity<ProfessorModel> saveProfessor(@RequestBody @Valid ProfessorRecordDto professorRecordDto) {
        var professorModel = new ProfessorModel();
        BeanUtils.copyProperties(professorRecordDto, professorModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(professorRepository.save(professorModel));
    }

    // Get all professors
    @GetMapping("/professors")
    public ResponseEntity<List<ProfessorModel>> getAllProfessors() {
        List<ProfessorModel> professorsList = professorRepository.findAll();
        if(!professorsList.isEmpty()) {
            for(ProfessorModel professor : professorsList) {
                UUID id = professor.getIdPerson();
                professor.add(linkTo(methodOn(ProfessorController.class).getOneProfessor(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(professorsList);
    }

    // Get one professor
    @GetMapping("/professors/{id}")
    public ResponseEntity<Object> getOneProfessor(@PathVariable(value="id") UUID id) {
        Optional<ProfessorModel> professorO = professorRepository.findById(id);
        if(professorO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor not found.");
        }
        professorO.get().add(linkTo(methodOn(ProfessorController.class).getAllProfessors()).withRel("Professors List"));
        return ResponseEntity.status(HttpStatus.OK).body(professorO.get());
    }

    // Update a professor
    @PutMapping("/professors/{id}")
    public ResponseEntity<Object>
    updateProfessor(@PathVariable(value="id") UUID id, @RequestBody @Valid ProfessorRecordDto professorRecordDto) {
        Optional<ProfessorModel> professorO = professorRepository.findById(id);
        if(professorO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor not found.");
        }
        ProfessorModel professorModel = professorO.get();
        BeanUtils.copyProperties(professorRecordDto, professorModel);
        return ResponseEntity.status(HttpStatus.OK).body(professorRepository.save(professorModel));
    }

    // Delete a professor
    @DeleteMapping("/professors/{id}")
    public ResponseEntity<Object> deleteProfessor(@PathVariable(value = "id") UUID id) {
        Optional<ProfessorModel> professorO = professorRepository.findById(id);
        if (professorO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor not found.");
        }
        professorRepository.delete(professorO.get());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Professor deleted.");
    }

    // Set a course to a professor
    @PutMapping("/professors/{idProfessor}/assign-course/{idCourse}")
    public ResponseEntity<Object> assignCourseToProfessor(
            @PathVariable UUID idProfessor,
            @PathVariable UUID idCourse) {

        Optional<ProfessorModel> professor0 = professorRepository.findById(idProfessor);
        Optional<CourseModel> courseO = courseRepository.findById(idCourse);

        if (professor0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor not found.");
        }
        if(courseO.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
        }

        ProfessorModel professor = professor0.get();
        CourseModel course = courseO.get();

        professor.setCourse(course);
        professorRepository.save(professor);

        return ResponseEntity.status(HttpStatus.OK).body("Course assigned to professor successfully.");
    }



}
