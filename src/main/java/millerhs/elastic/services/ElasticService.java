package millerhs.elastic.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.tika.exception.TikaException;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import millerhs.elastic.documents.TestDocument;
import millerhs.elastic.repositories.ElasticRepository;

@Service
public class ElasticService {
	
	@Autowired
	TikaService tikaService;
	
	@Autowired
	ElasticRepository elasticRepository;
	
	@Autowired
	ElasticsearchOperations elasticsearchOperations;

	public List<TestDocument> search(String searchString) {
		Query query = new NativeSearchQueryBuilder().withQuery(QueryBuilders.simpleQueryStringQuery(searchString)).build();
		
		SearchHits<TestDocument> document = elasticsearchOperations.search(query, TestDocument.class, IndexCoordinates.of("test-index"));
		
		return document.get().map(hit -> hit.getContent()).collect(Collectors.toList());
	}
	
	public void save(TestDocument testDocument) {
		try {
			elasticRepository.save(testDocument);
		} catch (NullPointerException npe) { }
	}
	
	public void saveFile(String id, MultipartFile file) throws IOException, SAXException, TikaException {
		Optional<TestDocument> oEntity = elasticRepository.findById(id);
		
		if (oEntity.isPresent()) {
			TestDocument entity = oEntity.get();
			
			if (file != null) {
				entity.setFileName(file.getName());
				entity.setFile(tikaService.extractText(file.getBytes()));
				
				this.save(entity);
			}
		}
	}
}
