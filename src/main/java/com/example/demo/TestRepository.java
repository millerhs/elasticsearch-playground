package com.example.demo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TestRepository extends ElasticsearchRepository<TestEntity, String> {
	
}
