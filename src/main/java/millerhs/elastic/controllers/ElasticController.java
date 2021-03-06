package millerhs.elastic.controllers;

import java.io.IOException;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import millerhs.elastic.documents.TestDocument;
import millerhs.elastic.services.ElasticService;
import millerhs.elastic.services.TikaService;

@RestController
public class ElasticController {

	@Autowired
	ElasticService elasticService;
	
	@Autowired
	TikaService tikaService;

	/**
	 * Performs a basic text search on all fields of the TestDocument index. This supports Lucene's query syntax.
	 */
	@GetMapping("/search")
	public List<TestDocument> search(@RequestParam String search) {
		return elasticService.search(search);
	}

	/**
	 * Updates the given document.
	 */
	@PostMapping("/save")
	public void save(@RequestBody TestDocument testDocument) {
		elasticService.save(testDocument);
	}
	
	/**
	 * Updates the corresponding ES document with the filename and parsed text of the given MultipartFile.
	 */
	@PostMapping(path = "/save/{id}/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void saveFile(@PathVariable("id") String id, @RequestPart MultipartFile file) throws IOException, SAXException, TikaException {
		elasticService.saveFile(id, file);
	}
	
	/** 
	 * Returns parsed text from a given file.
	 * This isn't really creating anything; I just don't know how to include a MultipartFile in a GET request. 
	 * **/
	@PostMapping(path = "/tika", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String tikaRead(@RequestPart MultipartFile file) throws IOException, SAXException, TikaException {
		return tikaService.extractText(file.getBytes());
	}
	
}
