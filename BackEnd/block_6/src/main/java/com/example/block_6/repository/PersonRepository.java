package com.example.block_6.repository;

import com.example.block_6.model.Person;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, String>, PersonCustomRepository {
    @Query("{\"multi_match\": {\"query\": \"?0\",\"type\": \"phrase\",\"fields\": [\"full_name_en\", \"full_name\"]}}")
    List<Person> findByNameUsingCustomQuery(String name);
}
