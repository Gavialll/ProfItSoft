package com.example.block_5.controller;

import com.example.block_5.dto.PersonInfoDto;
import com.example.block_5.dto.PersonSaveDto;
import com.example.block_5.dto.Response;
import com.example.block_5.mapper.Map;
import com.example.block_5.model.Person;
import com.example.block_5.repository.PersonRepository;
import com.example.block_5.repository.ProfessionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    @Sql("schema.sql")
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

        Response.Ok actual = objectMapper.readValue(json, Response.Ok.class);
        Response.Ok expected = new Response.Ok(HttpStatus.CREATED, 1L);

        assertEquals(expected, actual);


        Person expectedP = new Person(1L, "Test", 45, professionRepository.findByName("Doctor"));
        Person actualP = personRepository.findById(1L).get();

        assertEquals(expectedP, actualP);
    }

    @Test
    @Order(2)
    @DisplayName("Add Profession not found")
    void addThrow() throws Exception {
        PersonSaveDto newPerson = new PersonSaveDto("Test", 45, "PamPam");

        String json = mockMvc.perform(post("/api/person")
                        .content(objectMapper.writeValueAsString(newPerson))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Response.Error actualResponse = objectMapper.readValue(json, Response.Error.class);
        Response.Error expectedResponse = new Response.Error(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                "Profession with name: PamPam not found"
                );

        assertEquals(expectedResponse, actualResponse);
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

        Response.Error actualResponse = objectMapper.readValue(json, Response.Error.class);
        Response.Error expectedResponse = new Response.Error(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                "Person with id: 99999999 not found"
        );

        assertEquals(expectedResponse, actualResponse);
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
        PersonSaveDto personSaveDto = new PersonSaveDto("TestUpdate", 12, "Law");

        String json = mockMvc.perform(patch("/api/person/1")
                        .content(objectMapper.writeValueAsString(personSaveDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Response.Ok actualResponse = objectMapper.readValue(json, Response.Ok.class);
        Response.Ok expectedResponse = new Response.Ok(HttpStatus.OK, 1L);

        assertEquals(expectedResponse, actualResponse);

        //new person
        expected = new Person(1L, "TestUpdate", 12, professionRepository.findById(2L).get());
        actual = personRepository.findById(1L).get();
        assertEquals(expected, actual);
    }

    @Test
    @Order(6)
    @DisplayName("Update Person not found")
    void updateThrowPerson() throws Exception {
        PersonSaveDto personSaveDto = new PersonSaveDto("TestUpdate", 12, "Law");

        String json = mockMvc.perform(patch("/api/person/99999999")
                        .content(objectMapper.writeValueAsString(personSaveDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Response.Error actualResponse = objectMapper.readValue(json, Response.Error.class);
        Response.Error expectedResponse = new Response.Error(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                "Person with id: 99999999 not found"
        );

        assertEquals(expectedResponse, actualResponse);
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
                                                "PamPam")
                                )
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Response.Error actualResponse = objectMapper.readValue(json, Response.Error.class);
        Response.Error expectedResponse = new Response.Error(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                "Profession with name: PamPam not found"
        );

        assertEquals(expectedResponse, actualResponse);
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

        Response.Ok actualResponse = objectMapper.readValue(json, Response.Ok.class);
        Response.Ok expectedResponse = new Response.Ok(HttpStatus.OK, 1L);

        assertEquals(expectedResponse, actualResponse);

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


        Response.Error actualResponse = objectMapper.readValue(json, Response.Error.class);
        Response.Error expectedResponse = new Response.Error(
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                "Person with id: 99999999 not found"
        );

        assertEquals(expectedResponse, actualResponse);
    }
}