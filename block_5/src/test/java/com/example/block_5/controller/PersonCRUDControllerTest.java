package com.example.block_5.controller;

import com.example.block_5.dto.PersonInfoDto;
import com.example.block_5.dto.PersonSaveDto;
import com.example.block_5.mapper.Map;
import com.example.block_5.model.Person;
import com.example.block_5.repository.PersonRepository;
import com.example.block_5.repository.ProfessionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Sql("schema.sql")
@Sql("Profession.sql")
@Sql("Person.sql")
@Transactional
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonCRUDControllerTest {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ProfessionRepository professionRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql("Profession.sql")
    @Order(1)
    @DisplayName("Add Person")
    void add() throws Exception {
        PersonSaveDto newPerson = new PersonSaveDto("Test", 45, "Doctor");

        String json = mockMvc.perform(post("/api/person")
                        .content(objectMapper.writeValueAsString(newPerson))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(json.contains("1"));


        Person expected = new Person(1L, "Test", 45, professionRepository.findByName("Doctor"));
        Person actual = personRepository.findById(1L).get();

        assertEquals(expected, actual);
    }

    @Test
    @Order(2)
    @DisplayName("Add Profession not found")
    void addThrow() throws Exception {
        PersonSaveDto newPerson = new PersonSaveDto("Test", 45, "Pam-Pam");

        String json = mockMvc.perform(post("/api/person")
                        .content(objectMapper.writeValueAsString(newPerson))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(json.contains("Profession with name: Pam-Pam not found"));
    }

    @Test
    @Order(3)
    @DisplayName("Get Person")
    void getPerson() throws Exception {
        Long personId = 1L;
        String json = mockMvc.perform(get("/api/person/{id}", personId))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PersonInfoDto actual = objectMapper.readValue(json, PersonInfoDto.class);

        PersonInfoDto expected = Map.mapToPersonInfo(personRepository.findById(personId).get());

        assertEquals(expected, actual);
    }

    @Test
    @Order(4)
    @DisplayName("Get Person not found")
    void getThrow() throws Exception {
        Long personId = 99999999L;
        String json = mockMvc.perform(get("/api/person/{id}", personId))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(json.contains("Person with id: 99999999 not found"));
    }

    @Test
    @Order(5)
    @DisplayName("Update Person")
    void update() throws Exception {
        //old person
        Person expected = new Person(1L, "Ivan", 25, professionRepository.findById(1L).get());
        Person actual = personRepository.findById(1L).get();

        assertEquals(expected, actual);

        //update person
        String json = mockMvc.perform(patch("/api/person/1")
                        .content(objectMapper.writeValueAsString(new PersonSaveDto("TestUpdate", 12, "Law")))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(json.contains("1"));

        //new person
        expected = new Person(1L, "TestUpdate", 12, professionRepository.findById(2L).get());
        actual = personRepository.findById(1L).get();
        assertEquals(expected, actual);
    }

    @Test
    @Order(6)
    @DisplayName("Update Person not found")
    void updateThrowPerson() throws Exception {
        String json = mockMvc.perform(patch("/api/person/99999999")
                        .content(objectMapper.writeValueAsString(new PersonSaveDto("TestUpdate", 12, "Law")))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertTrue(json.contains("Person with id: 99999999 not found"));
    }
    @Test
    @Order(7)
    @DisplayName("Update Profession not found")
    void updateThrowProfession() throws Exception {
        String json = mockMvc.perform(patch("/api/person/1")
                        .content(objectMapper.writeValueAsString(
                                        new PersonSaveDto(
                                                "TestUpdate",
                                                12,
                                                "Pam-Pam")
                                )
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertTrue(json.contains("Profession with name: Pam-Pam not found"));
    }

    @Test
    @Order(8)
    @DisplayName("Delete Person")
    void deletePerson() throws Exception {
        String json = mockMvc.perform(delete("/api/person/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(json.contains("1"));

        assertThrows(NoSuchElementException.class, ()-> personRepository.findById(1L).get());
    }

    @Test
    @Order(9)
    @DisplayName("Delete Person not found")
    void deleteThrowPerson() throws Exception {
        String json = mockMvc.perform(delete("/api/person/99999999"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(json.contains("Person with id: 99999999 not found"));
    }
}