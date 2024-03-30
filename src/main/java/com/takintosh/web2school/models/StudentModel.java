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

    @Column(nullable = false, length = 100)
    private String registration;
    private Integer semester;
    @Column(nullable = false, length = 20)
    private String status;

    // Getters & Setters
    public String getRegistration() {
        return registration;
    }
    public void setRegistration(String registration) {
        this.registration = registration;
    }
    public Integer getSemester() {
        return semester;
    }
    public void setSemester(Integer semester) {
        this.semester = semester;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public CourseModel getCourse() {
        return course;
    }
    public void setCourse(CourseModel course) {
        this.course = course;
    }

}