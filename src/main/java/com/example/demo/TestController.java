package com.example.demo;

import java.io.IOException;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

@RestController
public class TestController {

	@Autowired
	TestService testService;
	
	@GetMapping("/search")
	public List<TestEntity> search(@RequestParam String search) {
		return testService.search(search);
	}
	
	@PostMapping("/save")
	public void save(@RequestBody TestEntity testEntity) {
		testService.save(testEntity);
	}
	
	@PostMapping(path = "/save/{id}/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void saveFile(@PathVariable("id") String id, @RequestPart MultipartFile file) throws IOException, SAXException, TikaException {
		testService.saveFile(id, file);
	}
	
}
