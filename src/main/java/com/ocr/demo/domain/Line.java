package com.ocr.demo.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Line {
	@JsonProperty
	private Long id;
	@JsonProperty
	private Baseline baseline;
	@JsonProperty
	private float x_size;
	@JsonProperty
	private float x_descenders;
	@JsonProperty
	private float x_ascenders;
	@JsonProperty
	private BoundingBox boundingBox;
	@JsonProperty
	private List<Word> words = new ArrayList<>();

	public Line() {}

	public Line(Long id, Baseline baseline, float x_size, float x_descenders, float x_ascenders,
			BoundingBox boundingBox) {
		this.id = id;
		this.baseline = baseline;
		this.x_size = x_size;
		this.x_descenders = x_descenders;
		this.x_ascenders = x_ascenders;
		this.boundingBox = boundingBox;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Baseline getBaseline() {
		return baseline;
	}

	public void setBaseline(Baseline baseline) {
		this.baseline = baseline;
	}

	public float getX_size() {
		return x_size;
	}

	public void setX_size(float x_size) {
		this.x_size = x_size;
	}

	public float getX_descenders() {
		return x_descenders;
	}

	public void setX_descenders(float x_descenders) {
		this.x_descenders = x_descenders;
	}

	public float getX_ascenders() {
		return x_ascenders;
	}

	public void setX_ascenders(float x_ascenders) {
		this.x_ascenders = x_ascenders;
	}

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}

	public List<Word> getWords() {
		return words;
	}

	public void addWord(Word word) {
		this.words.add(word);
	}

	public void setWords(List<Word> words) {
		this.words = words;
	}
}
