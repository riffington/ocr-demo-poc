package com.ocr.demo.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Page {
	@JsonProperty
	private Long id;
	@JsonProperty
	private String imageName;
	@JsonProperty
	private int pageNumber;
	@JsonProperty
	private BoundingBox boundingBox; // want this for the overall page coordinates (i.e. size)
	@JsonProperty
	private List<BoxInfo> boundingBoxCoordinates;
	@JsonProperty
	private String coordinatesType;
	
	// Currently NOT returning all the rest of these details because we only need some bounding box and some coordinates info
	@JsonIgnore
	private List<Area> areas = new ArrayList<>();

	protected Page() {}

	public Page(Long id, String imageName, int pageNumber, BoundingBox boundingBox) {
		this.id = id;
		this.imageName = imageName;
		this.pageNumber = pageNumber;
		this.boundingBox = boundingBox;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}
	
	public List<Area> getAreas() {
		return areas;
	}
	
	public void addArea(Area area) {
		this.areas.add(area);
	}
	
	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public List<BoxInfo> getBoundingBoxCoordinates() {
		return boundingBoxCoordinates;
	}

	public void setBoundingBoxCoordinates(List<BoxInfo> boundingBoxCoordinates) {
		this.boundingBoxCoordinates = boundingBoxCoordinates;
	}

	public String getCoordinatesType() {
		return coordinatesType;
	}

	public void setCoordinatesType(CoordinatesType coordinatesType) {
		this.coordinatesType = coordinatesType.name();
	}
}
