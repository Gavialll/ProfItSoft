package com.example.block_5.controller;

import com.example.block_5.dto.PersonInfoDto;
import com.example.block_5.dto.PersonSearchDto;
import com.example.block_5.mapper.Map;
import com.example.block_5.repository.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Sql("schema.sql")
@Sql("Profession.sql")
@Sql("Person.sql")
@Transactional
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonSearchControllerTest {
    @Autowired
    private PersonRepository personRepository;
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
    void getPageFirst() throws Exception {
        long from = 0L;
        long size = 3L;
        String json = mockMvc.perform(get("/api/person?from=%d&size=%d".formatted(from, size)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<PersonInfoDto> actual = objectMapper
                .readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, PersonInfoDto.class));
        List<PersonInfoDto> expected = Stream.of(
                        personRepository.findById(1L).get(),
                        personRepository.findById(2L).get(),
                        personRepository.findById(3L).get()
                ).map(Map::mapToPersonInfo)
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }

    @Test
    @Order(3)
    @DisplayName("Get second page of 'Person'")
    void getPageSecond() throws Exception {
        long from = 3L;
        long size = 3L;
        String json = mockMvc.perform(get("/api/person?from=%d&size=%d".formatted(from, size)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<PersonInfoDto> actual = objectMapper
                .readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, PersonInfoDto.class));

        @SuppressWarnings("all")
        List<PersonInfoDto> expected = Stream.of(
                        personRepository.findById(4L).get(),
                        personRepository.findById(5L).get(),
                        personRepository.findById(6L).get()
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
}
