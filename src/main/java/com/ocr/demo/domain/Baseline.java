package com.ocr.demo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Baseline {
	@JsonProperty
	private float x;
	@JsonProperty
	private float y;
	
	public Baseline() {}
	
	public Baseline(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
