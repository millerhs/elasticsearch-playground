package millerhs.elastic.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
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

import millerhs.elastic.entities.TestEntity;
import millerhs.elastic.repositories.TestRepository;

@Service
public class TestService {
	
	@Autowired
	TestRepository testRepository;
	
	@Autowired
	ElasticsearchOperations elasticsearchOperations;

	public List<TestEntity> search(String searchString) {
		Query query = new NativeSearchQueryBuilder().withQuery(QueryBuilders.simpleQueryStringQuery(searchString)).build();
		
		SearchHits<TestEntity> response = elasticsearchOperations.search(query, TestEntity.class, IndexCoordinates.of("test-index"));
		
		return response.get().map(hit -> hit.getContent()).collect(Collectors.toList());
	}
	
	public void save(TestEntity testEntity) {
		try {
			testRepository.save(testEntity);
		} catch (NullPointerException npe) { }
	}
	
	public void saveFile(String id, MultipartFile file) throws IOException, SAXException, TikaException {
		Optional<TestEntity> oEntity = testRepository.findById(id);
		
		if (oEntity.isPresent()) {
			TestEntity entity = oEntity.get();
			
			if (file != null) {
				entity.setFileName(file.getName());
				entity.setFile(extractText(file.getBytes()));
				
				this.save(entity);
			}
		}
	}
	
	/**
	 * Extract plain text from files. This has been confirmed to work with TXT, DOCX, XLSX, and PDF.
	 */
	private String extractText(byte[] file) throws IOException, SAXException, TikaException {
		AutoDetectParser parser = new AutoDetectParser();
		BodyContentHandler handler = new BodyContentHandler();
	    Metadata metadata = new Metadata();
	    try (InputStream stream = new ByteArrayInputStream(file)) {
	        parser.parse(stream, handler, metadata);
	        return handler.toString();
	    }
	}
	
}
