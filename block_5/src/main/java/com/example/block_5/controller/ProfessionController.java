package com.example.block_5.controller;

import com.example.block_5.dto.ProfessionInfoDto;
import com.example.block_5.service.ProfessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/profession")
@Tag(name = "Profession")
public class ProfessionController {
    @Autowired
    private ProfessionService professionService;

    @GetMapping
    @Operation(summary = "Get all Profession")
    public List<ProfessionInfoDto> getAll(){
        return professionService.getAll();
    }
}
