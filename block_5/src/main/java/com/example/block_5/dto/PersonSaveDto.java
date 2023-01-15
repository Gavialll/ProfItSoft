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
public class PersonSaveDto {
    @NotBlank(message = "name: is required")
    private String name;
    @Min(value = 1, message = "age: min value is 1")
    @Max(value = 100, message = "age: max value is 100")
    private int age;
    @NotNull(message = "professionName: is required")
    private String professionName;

    // TODO: 12.01.2023 add name validation
    // TODO: 12.01.2023 add age validation
    // TODO: 12.01.2023 add profession validation
    // TODO: 12.01.2023 write test for validation
    // TODO: 12.01.2023 create class GoodResponce for and send it with add, update, delete

}
