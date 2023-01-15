package com.example.block_5.controller;

import com.example.block_5.dto.PersonInfoDto;
import com.example.block_5.dto.PersonSaveDto;
import com.example.block_5.dto.PersonSearchDto;
import com.example.block_5.service.PersonService;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping("/api/person")
@Validated
//@Api(tags = "Контролер для управління публікаціями користувача")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping
//    @ApiOperation("Всі публікації користувача")
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long add(@Valid @RequestBody PersonSaveDto saveDto){
        return personService.add(saveDto);
    }

    @GetMapping("/{id}")
    public PersonInfoDto get(@PathVariable Long id){
        return personService.get(id);
    }

    @PatchMapping("/{id}")
    public Long update(@PathVariable Long id, @Valid @RequestBody PersonSaveDto saveDto){
        return personService.update(id, saveDto);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id){
        return personService.delete(id);
    }
}
