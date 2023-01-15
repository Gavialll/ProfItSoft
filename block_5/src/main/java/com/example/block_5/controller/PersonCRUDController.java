package com.example.block_5.controller;

import com.example.block_5.dto.PersonInfoDto;
import com.example.block_5.dto.PersonSaveDto;
import com.example.block_5.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/person")
@Validated
@Tag(name = "Person CRUD")
public class PersonCRUDController {
    @Autowired
    private PersonService personService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create person")
    public Long add(@Valid @RequestBody PersonSaveDto saveDto){
        return personService.add(saveDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get person by id")
    public PersonInfoDto get(@PathVariable Long id){
        return personService.get(id);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Patch Person")
    public Long update(@PathVariable Long id, @Valid @RequestBody PersonSaveDto saveDto){
        return personService.update(id, saveDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Person")
    public Long delete(@PathVariable Long id){
        return personService.delete(id);
    }
}
