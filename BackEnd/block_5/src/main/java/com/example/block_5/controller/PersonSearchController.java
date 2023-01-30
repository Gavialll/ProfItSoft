package com.example.block_5.controller;

import com.example.block_5.dto.PersonInfoDto;
import com.example.block_5.dto.PersonSearchDto;
import com.example.block_5.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person")
@Validated
@Tag(name = "Person search")
public class PersonSearchController {
    @Autowired
    private PersonService personService;

    @GetMapping
    @Operation(summary = "Get page of People")
    public List<PersonInfoDto> getPage(
            @Min(value = 0, message = "min value 0")
            @RequestParam("from")
            long from,
            @Min(value = 0, message = "min value 0")
            @RequestParam("size")
            long size
    ){
        return personService.getAll(from, size);
    }

    @PostMapping("/_search")
    @Operation(summary = "Search person by name and profession name")
    public List<PersonInfoDto> search(
            @RequestBody
            PersonSearchDto personSearchDto,
            @Min(value = 0, message = "min value 0")
            @RequestParam("from")
            int from,
            @Min(value = 0, message = "min value 0")
            @RequestParam("size")
            int size
    ){
        return personService.search(
                personSearchDto.getName(),
                personSearchDto.getProfessionName(),
                from,
                size
        );
    }
}
