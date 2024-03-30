package com.takintosh.web2school.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ProfessorRecordDto(
        @NotBlank String name,
        @NotBlank String surname,
        @NotBlank String identification,
        @NotBlank String email,
        String phone,
        String address,
        String city,
        String state,
        String country,
        Integer zipCode,
        String title,
        String department,
        @NotNull Integer siape,
        UUID courseId

) {
}
