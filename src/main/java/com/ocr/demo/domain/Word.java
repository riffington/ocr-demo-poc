package com.ocr.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Word {
	@JsonProperty
	private Long id;
	@JsonProperty
	private String text;
	@JsonProperty
	private String correctedText;
	@JsonProperty
	private int confidence;
	@JsonProperty
	private BoundingBox boundingBox;

    public Word() {}
    
    public Word(Long id, int confidence, BoundingBox boundingBox) {
        this.id = id;
        this.confidence = confidence;
        this.boundingBox = boundingBox;
    }
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCorrectedText() {
		return correctedText;
	}

	public void setCorrectedText(String correctedText) {
		this.correctedText = correctedText;
	}

	public int getConfidence() {
		return confidence;
	}

	public void setConfidence(int confidence) {
		this.confidence = confidence;
	}

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}
}
