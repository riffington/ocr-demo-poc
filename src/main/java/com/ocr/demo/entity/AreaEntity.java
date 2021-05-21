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
@Table(name="document_area")
public class AreaEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long id;
	
	// This is the "id" field provided by tesseract. It will NOT be unique across documents
	@Column(name="id_name")
	private String name;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bounding_box_id", referencedColumnName = "id")
	private BoundingBoxEntity boundingBox;
	
	@ManyToOne
    @JoinColumn(name="page_id", nullable=false)
	private PageEntity page;

	@OneToMany(mappedBy = "area", cascade = CascadeType.ALL)
	private List<ParagraphEntity> paragraphs = new ArrayList<>();

	@CreationTimestamp
	@Column(name="created")
	private Instant created;

	@Column(name="is_deleted", columnDefinition = "BIT", length = 1)
	private boolean isDeleted;
	
	protected AreaEntity() {}

	public AreaEntity(String name, BoundingBoxEntity boundingBox) {
		this.name = name;
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

	public BoundingBoxEntity getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(BoundingBoxEntity boundingBox) {
		this.boundingBox = boundingBox;
	}

	public PageEntity getPage() {
		return page;
	}

	public void setPage(PageEntity page) {
		this.page = page;
	}

	public List<ParagraphEntity> getParagraphs() {
		return paragraphs;
	}

	public void setParagraphs(List<ParagraphEntity> paragraphs) {
		this.paragraphs = paragraphs;
	}

	public void addParagraph(ParagraphEntity paragraph) {
		paragraph.setArea(this); // bidirectional relationship
		this.paragraphs.add(paragraph);
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
