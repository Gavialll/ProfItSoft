package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StatementController {
    @GetMapping("/statements")
    public List<String> getStatements(@RequestParam("count") int count){
        
        List<String> statements = new ArrayList<>();
        List<String> simblList = List.of("+","-","/", "*");
        for(int i = 0; i < count; i++) {
            int first = (int) (Math.random()*1000);
            int second = (int) (Math.random()*1000);
            String simbl = simblList.get((int) (Math.random() * simblList.size()));
            String statement = "%d %s %d".formatted(first, simbl, second);
            statements.add(statement);
        }
        return statements;
    }
}
