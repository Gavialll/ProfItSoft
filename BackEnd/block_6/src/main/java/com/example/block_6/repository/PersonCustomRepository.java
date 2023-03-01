package com.example.block_6.repository;

import com.example.block_6.dto.PopularName;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonCustomRepository{
   List<PopularName> searchMostPopularNames();
}
