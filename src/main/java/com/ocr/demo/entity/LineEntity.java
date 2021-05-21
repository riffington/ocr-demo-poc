package com.ocr.demo.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="document_line")
public class LineEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long id;
	
	// This is the "id" field provided by tesseract. It will NOT be unique across documents
	@Column(name="id_name")
	private String name;

	@Column(name="x_size")
	private float xSize;
	
	@Column(name="x_descenders")
	private float xDescenders;

	@Column(name="x_ascenders")
	private float xAscenders;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "baseline_tuple_id", referencedColumnName = "id")
	private BaselineTupleEntity baseline;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bounding_box_id", referencedColumnName = "id")
	private BoundingBoxEntity boundingBox;
	
	@ManyToOne
    @JoinColumn(name="paragraph_id", nullable=false)
	private ParagraphEntity paragraph;

	@OneToMany(mappedBy = "line", cascade = CascadeType.ALL)
	private List<WordEntity> words = new ArrayList<>();

	@CreationTimestamp
	@Column(name="created")
	private Instant created;

	@Column(name="is_deleted", columnDefinition = "BIT", length = 1)
	private boolean isDeleted;
	
	protected LineEntity() {}

	public LineEntity(String name, BaselineTupleEntity baseline, float xSize, float xDescenders, float xAscenders, BoundingBoxEntity boundingBox) {
		this.name = name;
		this.baseline = baseline;
		this.xSize = xSize;
		this.xDescenders = xDescenders;
		this.xAscenders = xAscenders;
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

	public float getxSize() {
		return xSize;
	}

	public void setxSize(float xSize) {
		this.xSize = xSize;
	}

	public float getxDescenders() {
		return xDescenders;
	}

	public void setxDescenders(float xDescenders) {
		this.xDescenders = xDescenders;
	}

	public float getxAscenders() {
		return xAscenders;
	}

	public void setxAscenders(float xAscenders) {
		this.xAscenders = xAscenders;
	}

	public BaselineTupleEntity getBaseline() {
		return baseline;
	}

	public void setBaseline(BaselineTupleEntity baseline) {
		this.baseline = baseline;
	}

	public BoundingBoxEntity getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(BoundingBoxEntity boundingBox) {
		this.boundingBox = boundingBox;
	}

	public ParagraphEntity getParagraph() {
		return paragraph;
	}

	public void setParagraph(ParagraphEntity paragraph) {
		this.paragraph = paragraph;
	}

	public List<WordEntity> getWords() {
		return words;
	}

	public void setWords(List<WordEntity> words) {
		this.words = words;
	}

	public void addWord(WordEntity word) {
		word.setLine(this); // bidirectional relationship
		this.words.add(word);
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
