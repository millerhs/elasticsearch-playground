package com.example.demo;

import java.time.ZonedDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Data;

@Data
@Document(indexName = "test-index")
public class TestEntity {

	// ID
	
	@Id
	private String id;
	
	public String getId() { 
		return id; 
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	// Text
	
	private String text;
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	// File Name
	
	private String fileName;
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	// File
	
	private String file;
	
	public String getFile() {
		return file;
	}
	
	public void setFile(String file) {
		this.file = file;
	}
	
	// Start date
	
	private ZonedDateTime startDate;
	
	public ZonedDateTime getStartDate() {
		return startDate;
	}
	
	public void setStartDate(ZonedDateTime startDate) {
		this.startDate = startDate;
	}
	
	// End date 
	
	private ZonedDateTime endDate;
	
	public ZonedDateTime getEndDate() {
		return endDate;
	}
	
	public void setEndDate(ZonedDateTime endDate) {
		this.endDate = endDate;
	}
	
}
