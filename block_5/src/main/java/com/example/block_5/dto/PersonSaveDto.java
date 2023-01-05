package com.example.block_5.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@AllArgsConstructor
@Data
public class PersonSaveDto {

    @NotBlank(message = "name: is required")
    private String name;
    @Min(value = 1, message = "age: min value is 1")
    @Max(value = 100, message = "age: max value is 100")
    private int age;
    @NotNull(message = "professionId: cannot be 'null'")
    private Long professionId;

}
