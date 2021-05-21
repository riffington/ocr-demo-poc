package com.ocr.demo.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PageListResponse {
	@JsonProperty
	private Pagination pagination;
	@JsonProperty
	private List<Page> pages;
	
	public PageListResponse() {}

	public PageListResponse(Pagination pagination, List<Page> pages) {
		this.pagination = pagination;
		this.pages = pages;
	}
	
	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List<Page> getPages() {
		return pages;
	}

	public void setPages(List<Page> pages) {
		this.pages = pages;
	};
}
