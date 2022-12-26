package com.example.block_4.service.impl;

import com.example.block_4.database.model.Person;
import com.example.block_4.controller.dto.PersonDto;
import com.example.block_4.database.repository.PersonRepository;
import com.example.block_4.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Override
    public boolean add(Person person) {
        personRepository.save(person);
        return true;
    }

    @Override
    public Person getById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> { throw new NoSuchElementException(
                    String.format("Person with id = %d, not found", id)
                );
        });
    }

    @Override
    public Person getByEmail(String email) {
        return personRepository.findByEmail(email.trim())
                .orElseThrow(() -> {throw new NoSuchElementException(
                    String.format("Person with email = %s, not found", email)
                );
        });
    }

    @Override
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public List<PersonDto> getAllDto() {
        return getAll().stream()
                .map(Person::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(Long id) {
        personRepository.deleteById(id);
        return true;
    }
}
