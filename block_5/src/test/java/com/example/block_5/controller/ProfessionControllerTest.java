package com.example.block_5.controller;

import com.example.block_5.dto.PersonInfoDto;
import com.example.block_5.dto.ProfessionInfoDto;
import com.example.block_5.mapper.Map;
import com.example.block_5.repository.ProfessionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("schema.sql")
@Sql("Profession.sql")
@Transactional
@AutoConfigureMockMvc
class ProfessionControllerTest {
    @Autowired
    private ProfessionRepository professionRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Test
    void getAll() throws Exception {
        String json = mockMvc.perform(get("/api/profession"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<ProfessionInfoDto> actual = objectMapper
                .readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, ProfessionInfoDto.class));

        List<ProfessionInfoDto> expected = professionRepository.findAll()
                .stream()
                .map(Map::mapToProfessionInfoDto)
                .collect(Collectors.toList());

        assertEquals(expected, actual);
    }
}