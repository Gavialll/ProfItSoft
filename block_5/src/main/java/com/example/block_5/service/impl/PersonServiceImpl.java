package com.example.block_5.service.impl;

import com.example.block_5.dto.PersonSaveDto;
import com.example.block_5.model.Person;
import com.example.block_5.model.Profession;
import com.example.block_5.repository.PersonRepository;
import com.example.block_5.service.PersonService;
import com.example.block_5.service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ProfessionService professionService;

    @Override
    public Long add(PersonSaveDto saveDto) {
        Profession profession = professionService.getById(saveDto.getProfessionId());
        Person person = Person.builder()
                .name(saveDto.getName())
                .age(saveDto.getAge())
                .profession(profession)
                .build();
        return personRepository.save(person).getId();
    }

    @Override
    public Person get(Long id) {
        return personRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Person with id: %d not found".formatted(id)));
    }

    @Override
    public Long update(Long id, PersonSaveDto saveDto) {
        Profession profession = professionService.getById(saveDto.getProfessionId());
        Person person = get(id);
        person.setName(saveDto.getName());
        person.setAge(saveDto.getAge());
        person.setProfession(profession);
        return personRepository.save(person).getId();
    }

    @Override
    public Long delete(Long id) {
        personRepository.deleteById(id);
        return id;
    }

    @Override
    public List<Person> getAll(long from, long size) {
        return personRepository.getPage(from, size);
    }
}
