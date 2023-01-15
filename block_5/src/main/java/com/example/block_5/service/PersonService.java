package com.example.block_5.service;

import com.example.block_5.dto.PersonInfoDto;
import com.example.block_5.dto.PersonSaveDto;

import java.util.List;

public interface PersonService {
    Long add(PersonSaveDto personSaveDto);
    PersonInfoDto get(Long id);
    Long update(Long id, PersonSaveDto person);
    Long delete(Long id);
    List<PersonInfoDto> getAll(long from, long to);
    List<PersonInfoDto> search(String name, String professionName, int from, int size);
}
