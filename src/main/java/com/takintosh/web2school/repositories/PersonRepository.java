package com.takintosh.web2school.repositories;

import com.takintosh.web2school.models.PersonModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PersonRepository extends JpaRepository<PersonModel, UUID> {

}