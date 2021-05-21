package com.ocr.demo.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Paragraph {
	@JsonProperty
	private Long id;
	@JsonProperty
	private String lang;
	@JsonProperty
	private BoundingBox boundingBox;
	@JsonProperty
	private List<Line> lines = new ArrayList<>();
	
	public Paragraph() {}
	
	public Paragraph(Long id, String lang, BoundingBox boundingBox) {
		this.id = id;
		this.lang = lang;
		this.boundingBox = boundingBox;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLang() {
		return lang;
	}
	
	public void setLang(String lang) {
		this.lang = lang;
	}
	
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}
	
	public List<Line> getLines() {
		return lines;
	}
	
	public void addLine(Line line) {
		this.lines.add(line);
	}
	
	public void setLines(List<Line> lines) {
		this.lines = lines;
	}
}
