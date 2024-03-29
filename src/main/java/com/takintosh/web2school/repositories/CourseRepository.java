package com.takintosh.web2school.repositories;

import com.takintosh.web2school.models.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<CourseModel, UUID> {

}
