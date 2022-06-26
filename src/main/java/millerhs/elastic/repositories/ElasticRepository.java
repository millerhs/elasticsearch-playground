package millerhs.elastic.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import millerhs.elastic.documents.TestDocument;

public interface ElasticRepository extends ElasticsearchRepository<TestDocument, String> {
	
}
