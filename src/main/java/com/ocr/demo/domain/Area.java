package com.ocr.demo.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Area {
	@JsonProperty
	private Long id;
	@JsonProperty
	private BoundingBox boundingBox;
	@JsonProperty
	private List<Paragraph> paragraphs = new ArrayList<>();

	public Area() {}
	
	public Area(Long id, BoundingBox boundingBox) {
		this.id = id;
		this.boundingBox = boundingBox;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}
	
	public List<Paragraph> getParagraphs() {
		return paragraphs;
	}
	
	public void addParagraph(Paragraph paragraph) {
		this.paragraphs.add(paragraph);
	}
	
	public void setParagraphs(List<Paragraph> paragraphs) {
		this.paragraphs = paragraphs;
	}
}
