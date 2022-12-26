package com.example.block_4.service;

import com.example.block_4.database.model.Person;

import java.util.List;

public interface PersonService {
    boolean add(Person person);
    Person getById(Long id);
    Person getByEmail(String email);
    List<Person> getAll();
    boolean delete(Long id);
}
