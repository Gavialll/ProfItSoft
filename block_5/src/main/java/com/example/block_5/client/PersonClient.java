package com.example.block_5.client;

import com.example.block_5.dto.PersonSaveDto;
import com.example.block_5.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

//@Component
@RequiredArgsConstructor
public class PersonClient {

    public static final String ENDPOINT_STUDENTS_CREATE = "/person/9";
    private final RestTemplate restTemplate;

//    @Value("${studentsService.baseUri:http://localhost:8080/api}")
    private String studentsServiceBaseUri ="http://localhost:8080/api";

    public String createStudent(Person dto) {
        ResponseEntity<Person> response = restTemplate.exchange(
                studentsServiceBaseUri + ENDPOINT_STUDENTS_CREATE,
                HttpMethod.GET,
                new HttpEntity<>(
                        dto
                ),
                ParameterizedTypeReference.forType(Person.class)
        );
        System.out.println(response.getBody());
        String id = response.getBody().getName();
        return id;
    }

//    public static void main(String[] args) {
////        System.out.println(new PersonClient(new RestTemplate()).createStudent(new Person()));
//
//        String url = "http://localhost:8080/api/person";
//        ResponseEntity<Long> response = new RestTemplate().exchange(
//                url,
//                HttpMethod.POST,
//                new HttpEntity<>(new PersonSaveDto("fggf",34,2L)),
//                ParameterizedTypeReference.forType(Long.class)
//        );
//
//        new RestTemplate().postForEntity(url, new PersonSaveDto("helloPost",34,2L),Long.class);
//
//        Long id = response.getBody();
//
//        System.out.println(id);
//
//         url = "http://localhost:8080/api/person/9";
//
//        Person p = new RestTemplate().getForEntity(url,Person.class).getBody();
//        System.out.println(p);
//
//        ResponseEntity<String>respons = new RestTemplate().exchange(
//                url,
//                HttpMethod.GET,
//                new HttpEntity<>(""),
//                ParameterizedTypeReference.forType(String.class)
//        );
//
//        String person = respons.getBody();
//
//        System.out.println(person);
//
//         url = "http://localhost:8080/api/person?from=0&size=30";
//        ResponseEntity<List<Person>> responseList = new RestTemplate().exchange(
//                url,
//                HttpMethod.GET,
//                new HttpEntity<>(new ArrayList<Person>()),
//                ParameterizedTypeReference.forType(ArrayList.class)
//        );
//
//        List<Person> personList = responseList.getBody();
//
//        System.out.println(personList);
//    }

}
