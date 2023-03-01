package com.example.block_6.controller;

import com.example.block_6.configuration.Config;
import com.example.block_6.dto.Response;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@RequestMapping("/api")
@RestController
@Slf4j
public class FileController {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Config client;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/upload")
    public Response.Status uploadFile(@RequestParam("file") MultipartFile zipFile) throws IOException {

        RestHighLevelClient elasticsearchClient = client.elasticsearchClient();

            boolean status = elasticsearchClient
                    .indices()
                    .delete(
                            new DeleteIndexRequest("my_index"),
                            RequestOptions.DEFAULT)
                    .isAcknowledged();

            if(status) {
                ZipInputStream zipInputStream = new ZipInputStream(zipFile.getInputStream());
                ZipEntry entry;
                while((entry = zipInputStream.getNextEntry()) != null) {
                    if(entry.getName().equals("pep.json")) {
                        parseJson(zipInputStream, elasticsearchClient);
                    }
                }
            }else {
                throw new RuntimeException("Elasticsearch problem with delete index: \"my_index\" status: " + status);
            }
        return new Response.Status<>(HttpStatus.CREATED, "File is save");

    }

    private void parseJson(InputStream inputStream, RestHighLevelClient elasticsearchClient) throws IOException {
        JsonParser jsonParser = objectMapper.getFactory().createParser(inputStream.readAllBytes());

        List<JsonNode> jsonNodes = new ArrayList<>();
        while(jsonParser.nextToken() != JsonToken.END_ARRAY) {
            if(jsonParser.currentToken() == JsonToken.START_OBJECT) {

                JsonNode object = objectMapper.readTree(jsonParser);
                jsonNodes.add(object);

                if(jsonNodes.size() == 100) {
                    save(jsonNodes, elasticsearchClient);
                    jsonNodes.clear();
                }
            }
        }
    }

    private void save(List<JsonNode> jsonNodes, RestHighLevelClient elasticsearchClient) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();

        for(JsonNode jsonNode : jsonNodes) {
            bulkRequest.add(new IndexRequest("my_index").source(jsonNode.toString(), XContentType.JSON));
        }

        elasticsearchClient.bulk(bulkRequest, RequestOptions.DEFAULT);
    }
}


