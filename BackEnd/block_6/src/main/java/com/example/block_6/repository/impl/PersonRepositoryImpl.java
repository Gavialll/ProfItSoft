package com.example.block_6.repository.impl;

import com.example.block_6.dto.PopularName;
import com.example.block_6.model.Person;
import com.example.block_6.repository.PersonCustomRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

@Repository
public class PersonRepositoryImpl implements PersonCustomRepository {
    @Autowired
    @Qualifier("elasticsearchOperations")
    private ElasticsearchOperations operations;
    @Override
    public List<PopularName> searchMostPopularNames() {
        Pageable pageable = PageRequest.of(0, 10);
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .addAggregation(terms("first_name").field("first_name.keyword").size(10))
                .withQuery(QueryBuilders.matchQuery("is_pep", true))
                .withPageable(pageable)
                .build();

        query.setMaxResults(0);

        SearchHits<Person> searchHits = operations.search(query, Person.class);

        SearchPage<Person> searchPage = SearchHitSupport.searchPageFor(searchHits, pageable);

        Aggregations aggregations = searchPage.getSearchHits().getAggregations();
        Terms names = aggregations.get("first_name");

        return names.getBuckets().stream()
                .map(item -> new PopularName(item.getKeyAsString(), item.getDocCount()))
                .toList();
    }
}
