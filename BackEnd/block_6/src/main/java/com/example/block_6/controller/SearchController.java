package com.example.block_6.controller;

import com.example.block_6.dto.PersonShortInfo;
import com.example.block_6.dto.PopularName;
import com.example.block_6.dto.Response;
import com.example.block_6.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("/searchByFullName")
    public Response.Status<List<PersonShortInfo>> searchByFullName(@RequestParam String name){
        return new Response.Status<>(
                HttpStatus.OK,
                "People with name: " + name,
                searchService.searchByName(name));
    }

    @GetMapping("/searchMostPopularNames")
    public Response.Status<List<PopularName>> searchMostPopularNames(){
        return new Response.Status<>(
                HttpStatus.OK,
                "10 most popular names of people",
                searchService.searchMostPopularNames()
        );
    }
}
