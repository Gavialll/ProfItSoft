package com.example.block_5.controller;

import com.example.block_5.dto.PersonSaveDto;
import com.example.block_5.dto.PersonSearchDto;
import com.example.block_5.model.Person;
import com.example.block_5.repository.PersonRepository;
import com.example.block_5.service.PersonService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person")
@Validated
public class PersonController {
    @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public List<Person> getPage(
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
    public List<Person> search(
            @RequestBody PersonSearchDto personSearchDto,
            @Min(value = 0, message = "min value 0")
            @RequestParam("page")
            int page,
            @Min(value = 0, message = "min value 0")
            @RequestParam("size")
            int size
    ){
        return personRepository
                .findAllByNameAndProfessionName
                        (personSearchDto.getName(),
                         personSearchDto.getProfessionName(),
                         PageRequest.of(page,size));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long add(@Valid @RequestBody PersonSaveDto saveDto){
        return personService.add(saveDto);
    }

    @GetMapping("/{id}")
    public Person get(@PathVariable Long id){
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
