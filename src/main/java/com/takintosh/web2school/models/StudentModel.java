package com.takintosh.web2school.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "TB_STUDENTS")

public class StudentModel extends PersonModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name = "idCourse")
    private CourseModel course;

    private String registration;
    private Number semester;
    private String status;

    // Getters & Setters
    public String getRegistration() {
        return registration;
    }
    public void setRegistration(String registration) {
        this.registration = registration;
    }
    public Number getSemester() {
        return semester;
    }
    public void setSemester(Number semester) {
        this.semester = semester;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}