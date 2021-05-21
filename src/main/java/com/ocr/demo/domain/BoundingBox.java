package com.ocr.demo.domain;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BoundingBox {
	@JsonProperty
    public int topLeftX;
	@JsonProperty
    public int topLeftY;
	@JsonProperty
    public int bottomRightX;
	@JsonProperty
    public int bottomRightY;
	
	public BoundingBox() {}
	
    public BoundingBox(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.bottomRightX = bottomRightX;
        this.bottomRightY = bottomRightY;
    }

	public int getTopLeftX() {
		return topLeftX;
	}

	public void setTopLeftX(int x) {
		this.topLeftX = x;
	}

	public int getTopLeftY() {
		return topLeftY;
	}

	public void setTopLeftY(int topLeftY) {
		this.topLeftY = topLeftY;
	}

	public int getBottomRightX() {
		return bottomRightX;
	}

	public void setBottomRightX(int bottomRightX) {
		this.bottomRightX = bottomRightX;
	}

	public int getBottomRightY() {
		return bottomRightY;
	}

	public void setBottomRightY(int bottomRightY) {
		this.bottomRightY = bottomRightY;
	}
	/**
	 * @return data as [top-left-X, top-left-Y, bottom-right-X, bottom-right-Y]
	 */
	@JsonIgnore
	public List<Integer> getBoundingBoxCoordinates()
	{
		return Arrays.asList(getTopLeftX(), getTopLeftY(), getBottomRightX(), getBottomRightY());
	}
}
