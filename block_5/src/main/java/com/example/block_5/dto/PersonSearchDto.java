package com.example.block_5.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class PersonSearchDto {
    @NotBlank(message = "name: is required")
    private String name;
    @NotBlank(message = "professionName: is required")
    private String professionName;
}
