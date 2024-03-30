package com.takintosh.web2school.repositories;

import com.takintosh.web2school.models.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<StudentModel, UUID> {

}
