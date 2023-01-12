package com.example.block_5.service.impl;

import com.example.block_5.dto.PersonInfoDto;
import com.example.block_5.dto.PersonSaveDto;
import com.example.block_5.mapper.Map;
import com.example.block_5.model.Person;
import com.example.block_5.model.Profession;
import com.example.block_5.repository.PersonRepository;
import com.example.block_5.service.PersonService;
import com.example.block_5.service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
    public PersonInfoDto get(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Person with id: %d not found".formatted(id)));
        return Map.mapToPersonInfo(person);
    }

    @Override
    public Long update(Long id, PersonSaveDto saveDto) {
        Profession profession = professionService.getById(saveDto.getProfessionId());
        System.out.println(saveDto);

        Person person = personRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Person with id: %d not found".formatted(id)));

        person.setName(saveDto.getName());
        person.setAge(saveDto.getAge());
        person.setProfession(profession);

        return personRepository.save(person).getId();
    }

    @Override
    public Long delete(Long id) {
        try {
            personRepository.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("Person with id: %d not found".formatted(id),1);
        }
        return id;
    }

    @Override
    public List<PersonInfoDto> getAll(long from, long size) {
        return personRepository.getPage(from, size)
                .stream()
                .map(Map::mapToPersonInfo)
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonInfoDto> search(String name, String professionName, int from, int size) {
        return personRepository
                .findAllByNameAndProfessionName(name,professionName, PageRequest.of(from,size))
                .stream()
                .map(Map::mapToPersonInfo)
                .collect(Collectors.toList());
    }
}
