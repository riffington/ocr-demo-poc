package com.ocr.demo.entity;

import java.time.Instant;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="document_word")
public class WordEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long id;
	
	// This is the "id" field provided by tesseract. It will NOT be unique across documents
	@Column(name="id_name")
	private String name;

	@Column(name="text_value")
	private String text;

	@Column(name="corrected_text_value")
	private String correctedText;

	@Column(name="confidence")
	private int confidence;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bounding_box_id", referencedColumnName = "id")
	private BoundingBoxEntity boundingBox;
	
	@ManyToOne
    @JoinColumn(name="line_id", nullable=false)
	private LineEntity line;

	@CreationTimestamp
	@Column(name="created")
	private Instant created;

	@Column(name="is_deleted", columnDefinition = "BIT", length = 1)
	private boolean isDeleted;
	
	protected WordEntity() {}

	public WordEntity(String name, int confidence, BoundingBoxEntity boundingBox) {
		this.name = name;
		this.confidence = confidence;
		this.boundingBox = boundingBox;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCorrectedText() {
		return correctedText;
	}

	public void setCorrectedText(String correctedText) {
		this.correctedText = correctedText;
	}

	public int getConfidence() {
		return confidence;
	}

	public void setConfidence(int confidence) {
		this.confidence = confidence;
	}

	public BoundingBoxEntity getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(BoundingBoxEntity boundingBox) {
		this.boundingBox = boundingBox;
	}

	public LineEntity getLine() {
		return line;
	}

	public void setLine(LineEntity line) {
		this.line = line;
	}

	public Instant getCreated() {
		return created;
	}

	public void setCreated(Instant created) {
		this.created = created;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
