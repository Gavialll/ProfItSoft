package com.example.block_5.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PersonInfoDto {
    private Long id;
    private String name;
    private int age;
    private String professionName;
}
