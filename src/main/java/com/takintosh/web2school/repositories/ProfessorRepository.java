package com.takintosh.web2school.repositories;

import com.takintosh.web2school.models.ProfessorModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProfessorRepository extends JpaRepository<ProfessorModel, UUID> {

}
