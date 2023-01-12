package com.example.block_5.mapper;

import com.example.block_5.dto.PersonInfoDto;
import com.example.block_5.dto.ProfessionInfoDto;
import com.example.block_5.model.Person;
import com.example.block_5.model.Profession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Map {
    public static PersonInfoDto mapToPersonInfo(Person person){
        return PersonInfoDto.builder()
                .name(person.getName())
                .id(person.getId())
                .age(person.getAge())
                .professionName(person.getProfession().getName())
                .build();
    }

    public static ProfessionInfoDto mapToProfessionInfoDto(Profession profession){
        return ProfessionInfoDto.builder()
                .name(profession.getName())
                .id(profession.getId())
                .build();
    }

}
