package com.takintosh.web2school.dtos;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record StudentRecordDto(
        @NotBlank String name,
        @NotBlank String surname,
        @NotBlank String identification,
        @NotBlank String email,
        String phone,
        String address,
        String city,
        String state,
        String country,
        Number zipCode,
        @NotBlank String registration,
        Number semester,
        @NotBlank String status,
        UUID idCourse
) {

}
