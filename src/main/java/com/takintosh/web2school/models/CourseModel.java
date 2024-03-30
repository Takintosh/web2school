package com.takintosh.web2school.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_COURSES")
public class CourseModel extends RepresentationModel<CourseModel> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idCourse;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 1000)
    private String description;

    // Getters & Setters
    public UUID getIdCourse() {
        return idCourse;
    }
    public void setIdCourse(UUID idCourse) {
        this.idCourse = idCourse;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

}