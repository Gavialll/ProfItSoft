package com.example.block_5.service;

import com.example.block_5.dto.ProfessionInfoDto;
import com.example.block_5.model.Profession;

import java.util.List;

public interface ProfessionService {
    Profession getByName(String id);
    List<ProfessionInfoDto> getAll();
}
