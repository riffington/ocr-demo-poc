package com.ocr.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DocumentCreationRequest {
	@JsonProperty
	private String guid;
	@JsonProperty
	private String contentType;
	
	public DocumentCreationRequest() {}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
}
