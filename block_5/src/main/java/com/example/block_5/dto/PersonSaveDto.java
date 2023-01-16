package com.example.block_5.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class PersonSaveDto {
    @Pattern(regexp = "^([A-z][A-z]+)$", message = "Name is not valid")
    @NotBlank(message = "name: is required")
    @Schema(allowableValues = {"Andrii", "Ivan"})
    private String name;
    @Min(value = 1, message = "age: min value is 1")
    @Max(value = 100, message = "age: max value is 100")
    @Schema(allowableValues = {"1"})
    private int age;
    @Pattern(regexp = "^([A-z][A-z]+)$", message = "Profession name is not valid")
    @NotNull(message = "professionName: is required")
    @Schema(allowableValues = {"Law", "Doctor"})
    private String professionName;
}
