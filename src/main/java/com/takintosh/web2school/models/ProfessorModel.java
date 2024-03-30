package com.takintosh.web2school.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_PROFESSORS")

public class ProfessorModel extends PersonModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name = "idCourse")

    private CourseModel course;
    private UUID idPerson;
    private String title;
    private String department;
    @Column(nullable = false, unique = true)
    private Integer siape;

    // Getters & Setters
    public UUID getIdPerson() {
        return idPerson;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public Integer getSiape() {
        return siape;
    }
    public void setSiape(Integer siape) {
        this.siape = siape;
    }
    public CourseModel getCourse() {
        return course;
    }
    public void setCourse(CourseModel course) {
        this.course = course;
    }

}