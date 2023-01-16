package com.example.block_5.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class PersonSearchDto {
    @NotBlank(message = "name: is required")
    @Pattern(regexp = "^([A-z][A-z]+)$", message = "Name is not valid")
    @Schema(allowableValues = {"Andrii", "Ivan"})
    private String name;
    @NotBlank(message = "professionName: is required")
    @Pattern(regexp = "^([A-z][A-z]+)$", message = "Profession name is not valid")
    @Schema(allowableValues = {"Law", "Doctor"})
    private String professionName;
}
