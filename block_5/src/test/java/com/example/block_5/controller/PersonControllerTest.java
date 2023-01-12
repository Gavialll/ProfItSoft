package com.example.block_5.controller;

import com.example.block_5.dto.PersonInfoDto;
import com.example.block_5.dto.PersonSaveDto;
import com.example.block_5.dto.PersonSearchDto;
import com.example.block_5.mapper.Map;
import com.example.block_5.model.Person;
import com.example.block_5.repository.PersonRepository;
import com.example.block_5.repository.ProfessionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Sql("Profession.sql")
@Sql("Person.sql")
@Transactional
@AutoConfigureMockMvc
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
        List<PersonInfoDto> expected = List.of(
                Map.mapToPersonInfo(
                        personRepository.findById(1L).get()
                )
        );
        assertEquals(expected, actual);
    }

    @Test
    void getFirstPage() throws Exception {
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

    @Test void getPage() throws Exception {
        long page = 1L;
        long size = 3L;
        String json = mockMvc.perform(get("/api/person?from=%d&size=%d".formatted(page, size)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<PersonInfoDto> actual = objectMapper
                .readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, PersonInfoDto.class));

        List<PersonInfoDto> expected = Stream.of(
                personRepository.findById(2L).get(),
                personRepository.findById(3L).get(),
                personRepository.findById(4L).get()
        ).map(Map::mapToPersonInfo)
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    void getPageWithLessZeroThrowIllegalArgumentException() throws Exception {
        long from = -1L;
        long size = 1L;
        String json = mockMvc.perform(get("/api/person?from=%d&size=%d".formatted(from, size)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(json.contains("getPage.from: min value 0"));
        assertTrue(json.contains("Bad Request"));

        from = 1L;
        size = -1L;
        json = mockMvc.perform(get("/api/person?from=%d&size=%d".formatted(from, size)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(json.contains("getPage.size: min value 0"));
        assertTrue(json.contains("Bad Request"));
    }

    @Test
    @Sql("Profession.sql")
    void add() throws Exception {
        PersonSaveDto newPerson = new PersonSaveDto("Test", 45, 1L);

        String json = mockMvc.perform(post("/api/person")
                        .content(objectMapper.writeValueAsString(newPerson))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println(personRepository.findById(2L));
        System.out.println(personRepository.findAll());
        assertTrue(json.contains("1"));


        Person expected = new Person(1L, "Test", 45, professionRepository.findById(1L).get());
        Person actual = personRepository.findById(1L).get();

        assertEquals(expected, actual);
    }

    @Test
    void addThrowNoSuchElementException() throws Exception {
        PersonSaveDto newPerson = new PersonSaveDto("Test", 45, 999999L);

        String json = mockMvc.perform(post("/api/person")
                        .content(objectMapper.writeValueAsString(newPerson))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(json.contains("Profession with id: 999999 not found"));
    }

    @Test
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
    void getThrowNoSuchElementException() throws Exception {
        Long personId = 99999999L;
        String json = mockMvc.perform(get("/api/person/{id}", personId))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertTrue(json.contains("Person with id: 99999999 not found"));
    }

    @Test
    void update() throws Exception {
        //old person
        Person expected = new Person(1L, "Ivan", 25, professionRepository.findById(1L).get());
        Person actual = personRepository.findById(1L).get();

        assertEquals(expected, actual);

        //update person
        String json = mockMvc.perform(patch("/api/person/1")
                        .content(objectMapper.writeValueAsString(new PersonSaveDto("TestUpdate", 12, 2L)))
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
    void updateThrowNoSuchElementException() throws Exception {
        String json = mockMvc.perform(patch("/api/person/99999999")
                        .content(objectMapper.writeValueAsString(new PersonSaveDto("TestUpdate", 12, 2L)))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertTrue(json.contains("Person with id: 99999999 not found"));


        json = mockMvc.perform(patch("/api/person/1")
                        .content(objectMapper.writeValueAsString(
                                new PersonSaveDto(
                                    "TestUpdate",
                                    12,
                                        99999999L)
                                )
                        )
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertTrue(json.contains("Profession with id: 99999999 not found"));
    }

    @Test
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
    void deleteThrowEmptyResultDataAccessException() throws Exception {
        String json = mockMvc.perform(delete("/api/person/99999999"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println(json);

        System.out.println(json);

        assertTrue(json.contains("Person with id: 99999999 not found"));
    }
}