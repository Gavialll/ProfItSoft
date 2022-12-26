package com.example.block_4.database.model;

import com.example.block_4.controller.dto.PersonDto;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String email;
    private String password;

    public Person() {}

    public PersonDto mapToDto(){
        return new PersonDto(id, name, email);
    }
}
