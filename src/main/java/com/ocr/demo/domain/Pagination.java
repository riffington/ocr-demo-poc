package com.ocr.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pagination {
	@JsonProperty
	private long pageNumber;
	@JsonProperty
	private long pageSize;
	@JsonProperty
	private long totalItemCount;

	public Pagination() {}
	
	public Pagination(long pageNumber, long pageSize, long totalPages) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalItemCount = totalPages;
	}

	public long getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(long pageNumber) {
		this.pageNumber = pageNumber;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalItemCount() {
		return totalItemCount;
	}

	public void setTotalItemCount(long totalItemCount) {
		this.totalItemCount = totalItemCount;
	};
}
