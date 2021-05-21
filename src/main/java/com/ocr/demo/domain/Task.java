package com.ocr.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Task {
	@JsonProperty
	private Long id;
	@JsonProperty
	private String type;
	@JsonProperty
	private Long relatedId;
	@JsonProperty
	private String imageRef;
	@JsonProperty
	private boolean isComplete;
	@JsonProperty
	private Long documentId;

	public Task() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Long relatedId) {
		this.relatedId = relatedId;
	}

	public String getImageRef() {
		return imageRef;
	}

	public void setImageRef(String imageRef) {
		this.imageRef = imageRef;
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}
}
