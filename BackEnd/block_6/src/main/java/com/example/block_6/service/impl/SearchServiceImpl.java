package com.example.block_6.service.impl;

import com.example.block_6.dto.PersonShortInfo;
import com.example.block_6.dto.PopularName;
import com.example.block_6.repository.PersonRepository;
import com.example.block_6.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<PersonShortInfo> searchByName(String name) {
        return personRepository.findByNameUsingCustomQuery(name)
                .stream()
                .map(PersonShortInfo::create)
                .toList();
    }

    @Override
    public List<PopularName> searchMostPopularNames() {
        return personRepository.searchMostPopularNames().stream()
                .sorted(Comparator.comparing(PopularName::getCount))
                .toList();
    }
}
