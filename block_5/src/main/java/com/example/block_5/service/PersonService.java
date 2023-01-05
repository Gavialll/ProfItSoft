package com.example.block_5.service;

import com.example.block_5.dto.PersonSaveDto;
import com.example.block_5.model.Person;

import java.util.List;

public interface PersonService {
    Long add(PersonSaveDto personSaveDto);
    Person get(Long id);
    Long update(Long id, PersonSaveDto person);
    Long delete(Long id);
    List<Person> getAll(long from, long to);
}
