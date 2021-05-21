package com.ocr.demo.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SpellcheckLineUpdateRequest {
	@JsonProperty
	private Long id;
	@JsonProperty
	private List<Word> words = new ArrayList<>();

	public SpellcheckLineUpdateRequest() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
