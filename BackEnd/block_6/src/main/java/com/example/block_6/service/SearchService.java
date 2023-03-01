package com.example.block_6.service;

import com.example.block_6.dto.PersonShortInfo;
import com.example.block_6.dto.PopularName;

import java.util.List;

public interface SearchService {
    List<PersonShortInfo> searchByName(String name);
    List<PopularName> searchMostPopularNames();
}
