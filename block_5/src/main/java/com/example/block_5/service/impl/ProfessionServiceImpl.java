package com.example.block_5.service.impl;

import com.example.block_5.model.Profession;
import com.example.block_5.repository.ProfessionRepository;
import com.example.block_5.service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProfessionServiceImpl implements ProfessionService {
    @Autowired
    private ProfessionRepository professionRepository;
    @Override
    public List<Profession> getAll() {
        return professionRepository.findAll();
    }

    @Override
    public Profession getById(Long id) {
        return professionRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("Profession with id: %d not found".formatted(id)));
    }


}
