package com.ocr.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PageListRequest {
	@JsonProperty
	private int pageNumber;
	@JsonProperty
	private int pageSize;
	@JsonProperty
	private String coordinatesType;
	
	@JsonIgnore
	private Long documentId;
	
	public PageListRequest() {}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public CoordinatesType getCoordinatesType() {
		return CoordinatesType.valueOf(coordinatesType);
	}

	public void setCoordinatesType(String coordinatesType) {
		this.coordinatesType = coordinatesType;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}
}
