package com.ocr.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FilterRequest {
	@JsonProperty
	private String coordinatesType;
	
	public FilterRequest() {}

	public CoordinatesType getCoordinatesType() {
		return CoordinatesType.valueOf(coordinatesType);
	}

	public void setCoordinatesType(String coordinatesType) {
		this.coordinatesType = coordinatesType;
	}
}
