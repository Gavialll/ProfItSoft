package com.example.block_6.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@ToString
@JsonAutoDetect
@EqualsAndHashCode
@Data
public class PopularName {
    String name;
    long count;
}
