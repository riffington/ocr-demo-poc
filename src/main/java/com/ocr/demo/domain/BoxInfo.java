package com.ocr.demo.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BoxInfo {
	@JsonProperty
	private Long boxId;
	@JsonProperty
	private List<Integer> coordinates;

    public BoxInfo() {}
    
    public BoxInfo(Long boxId, List<Integer> coordinates) {
        this.boxId = boxId;
        this.coordinates = coordinates;
    }

	public Long getBoxId() {
		return boxId;
	}

	public void setBoxId(Long boxId) {
		this.boxId = boxId;
	}

	public List<Integer> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<Integer> coordinates) {
		this.coordinates = coordinates;
	}
}
