package com.takintosh.web2school.dtos;

import jakarta.validation.constraints.NotBlank;

public record CourseRecordDto(
        @NotBlank String name,
        @NotBlank String description
) {

}
