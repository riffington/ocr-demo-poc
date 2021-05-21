package com.ocr.demo.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpellcheckLineResponse {
	@JsonProperty
	private Long id;
	@JsonProperty
	private String imageRef;
	@JsonProperty
	private BoundingBox boundingBox;
	@JsonProperty
	private List<Word> words = new ArrayList<>();
	@JsonProperty
	private BoundingBox pageBoundingBox;

	public SpellcheckLineResponse() {}

	public SpellcheckLineResponse(Long id, BoundingBox boundingBox, List<Word> words, String imageRef, BoundingBox pageBoundingBox) {
		this.id = id;
		this.boundingBox = boundingBox;
		this.words = words;
		this.imageRef = imageRef;
		this.pageBoundingBox = pageBoundingBox;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImageRef() {
		return imageRef;
	}

	public void setImageRef(String imageRef) {
		this.imageRef = imageRef;
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

	public BoundingBox getPageBoundingBox() {
		return pageBoundingBox;
	}

	public void setPageBoundingBox(BoundingBox pageBoundingBox) {
		this.pageBoundingBox = pageBoundingBox;
	}
}
