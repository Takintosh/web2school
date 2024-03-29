package com.takintosh.web2school.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "TB_PROFESSORS")

public class ProfessorModel extends PersonModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @ManyToOne
    @JoinColumn(name = "idCourse")
    private CourseModel course;

    private String title;
    private String department;
    @Column(nullable = false, length = 20)
    private Number siape;

    // Getters & Setters
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
    public Number getSiape() {
        return siape;
    }
    public void setSiape(Number siape) {
        this.siape = siape;
    }

}