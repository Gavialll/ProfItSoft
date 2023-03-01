package com.example.block_6.dto;

import com.example.block_6.model.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PersonShortInfo {
    private String id;
    private String fullName;
    private String typeOfOfficial;
    private String dateOfBirth;
    private String fullNameEN;
    private boolean died;

    public static PersonShortInfo create(Person person) {
        return new PersonShortInfo(
                person.getId(),
                person.getFull_name(),
                person.getType_of_official(),
                person.getDate_of_birth(),
                person.getFull_name_en(),
                person.isDied()
        );
    }
}
