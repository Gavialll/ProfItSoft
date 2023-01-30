package com.example.block_5.repository;

import com.example.block_5.model.Person;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query(value = "SELECT * FROM person LIMIT ?1, ?2",
    nativeQuery = true)
    List<Person> getPage(Long from, Long size);
    List<Person> findAllByNameAndProfessionName(String name, String professionName, Pageable pageable);
}
