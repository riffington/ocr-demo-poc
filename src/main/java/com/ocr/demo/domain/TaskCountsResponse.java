package com.ocr.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskCountsResponse {
	@JsonProperty
	private Long spellcheckTaskCount;
	@JsonProperty
	private Long labelTaskCount;

	public TaskCountsResponse() {}

	public TaskCountsResponse(Long spellcheckTaskCount, Long labelTaskCount) {
		this.spellcheckTaskCount = spellcheckTaskCount;
		this.labelTaskCount = labelTaskCount;
	}

	public Long getSpellcheckTaskCount() {
		return spellcheckTaskCount;
	}

	public void setSpellcheckTaskCount(Long spellcheckTaskCount) {
		this.spellcheckTaskCount = spellcheckTaskCount;
	}

	public Long getLabelTaskCount() {
		return labelTaskCount;
	}

	public void setLabelTaskCount(Long labelTaskCount) {
		this.labelTaskCount = labelTaskCount;
	}
}
