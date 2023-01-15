package com.example.block_5.controller;

import com.example.block_5.dto.PersonInfoDto;
import com.example.block_5.dto.PersonSaveDto;
import com.example.block_5.dto.PersonSearchDto;
import com.example.block_5.mapper.Map;
import com.example.block_5.model.Person;
import com.example.block_5.repository.PersonRepository;
import com.example.block_5.repository.ProfessionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Sql("schema.sql")
@Sql("Profession.sql")
@Sql("Person.sql")
@Transactional
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerTest {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ProfessionRepository professionRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    @DisplayName("Search 'Person' by 'Name' and 'Profession'")
    void search() throws Exception {
        String json = mockMvc.perform(post("/api/person/_search?from=0&size=1")
                        .content(objectMapper.writeValueAsString(new PersonSearchDto("Ivan", "Doctor")))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<PersonInfoDto> actual = objectMapper
                .readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, PersonInfoDto.class));
        @SuppressWarnings("all")
        List<PersonInfoDto> expected = List.of(
                Map.mapToPersonInfo(
                        personRepository.findById(1L).get()
                )
        );
        assertEquals(expected, actual);
    }

    @Test
    @Order(2)
    @DisplayName("Get first page of 'Person'")
    void getPageOne() throws Exception {
        // TODO: 12.01.2023 You must change it. You must get first page
        long page = 0L;
        long size = 10L;
        String json = mockMvc.perform(get("/api/person?from=%d&size=%d".formatted(page, size)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<PersonInfoDto> actual = objectMapper
                .readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, PersonInfoDto.class));
        List<PersonInfoDto> expected = personRepository.findAll()
                .stream()
                .map(Map::mapToPersonInfo)
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @Order(3)
    @DisplayName("Get second page of 'Person'")
    void getPageTwo() throws Exception {
        // TODO: 12.01.2023 You must change it. You must get second page
        long page = 1L;
        long size = 3L;
        String json = mockMvc.perform(get("/api/person?from=%d&size=%d".formatted(page, size)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<PersonInfoDto> actual = objectMapper
                .readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, PersonInfoDto.class));

        @SuppressWarnings("all")
        List<PersonInfoDto> expected = Stream.of(
                personRepository.findById(2L).get(),
                personRepository.findById(3L).get(),
                personRepository.findById(4L).get()
        ).map(Map::mapToPersonInfo)
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @Order(4)
    @DisplayName("Get page param: from = -1")
    void getPageWithLessZeroThrowIllegalArgumentException() throws Exception {
        long from = -1L;
        long size = 1L;
        String json = mockMvc.perform(get("/api/person?from=%d&size=%d".formatted(from, size)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(json.contains("getPage.from: min value 0"));
    }

    @Test
    @Order(5)
    @DisplayName("Get page param: size = -1")
    void getPageSizeThrow() throws Exception {
        Long from = 1L;
        Long size = -1L;
        String json = mockMvc.perform(get("/api/person?from=%d&size=%d".formatted(from, size)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(json.contains("getPage.size: min value 0"));
    }

    @Test
    @Sql("Profession.sql")
    @Order(6)
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
    @Order(7)
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
    @Order(8)
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
    @Order(9)
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
    @Order(10)
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
    @Order(11)
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
    @Order(12)
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
    @Order(13)
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
    @Order(14)
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