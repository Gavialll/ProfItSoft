package com.example.block_5.controller;

import com.example.block_5.dto.ProfessionInfoDto;
import com.example.block_5.service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/profession")
public class ProfessionController {
    @Autowired
    private ProfessionService professionService;

    @GetMapping
    public List<ProfessionInfoDto> getAll(){
        return professionService.getAll();
    }
}
