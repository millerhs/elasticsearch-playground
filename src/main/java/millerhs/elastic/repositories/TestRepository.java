package millerhs.elastic.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import millerhs.elastic.entities.TestEntity;

public interface TestRepository extends ElasticsearchRepository<TestEntity, String> {
	
}
