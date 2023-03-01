package com.example.block_6.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true, value = {"related_persons"})

@Document(indexName = "my_index", type = "my_type")
public class Person {
    @Id
    private String id;
    private String type_of_official;
    private String first_name;
    private String last_name;
    private List<String> related_persons;
    @JsonProperty("is_pep")
    private boolean pep;
    private String photo;
    private String full_name_en;
    private String first_name_en;
    private String last_name_en;
    private String url;
    private String date_of_birth;
    private String type_of_official_en;
    private String full_name;
    private String patronymic;
    private String patronymic_en;
    private boolean died;
    private String also_known_as_en;
    private String names;

    @Override
    public String toString() {
        return "Person{" + "type_of_official='" + type_of_official + '\'' + ", first_name='" + first_name + '\'' + ", last_name='" + last_name + '\'' + ", related_persons=" + related_persons + ", is_pep=" + pep + ", photo='" + photo + '\'' + ", full_name_en='" + full_name_en + '\'' + ", first_name_en='" + first_name_en + '\'' + ", last_name_en='" + last_name_en + '\'' + ", url='" + url + '\'' + ", date_of_birth='" + date_of_birth + '\'' + ", type_of_official_en='" + type_of_official_en + '\'' + ", full_name='" + full_name + '\'' + ", patronymic='" + patronymic + '\'' + ", patronymic_en='" + patronymic_en + '\'' + ", died='" + died + '\'' + ", also_known_as_en='" + also_known_as_en + '\'' + ", names='" + names + '\'' + '}';
    }
}
