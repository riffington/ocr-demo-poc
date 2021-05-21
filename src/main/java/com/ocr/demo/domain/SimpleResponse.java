package com.ocr.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleResponse {
	@JsonProperty
	private String status;

	public SimpleResponse() {};
	
	public SimpleResponse(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
