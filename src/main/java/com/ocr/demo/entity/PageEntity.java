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
@Table(name="document_page")
public class PageEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long id;
	
	// This is the "id" field provided by tesseract. It will NOT be unique across documents
	@Column(name="id_name")
	private String name;
	
	@Column(name="title")
	private String imageName;
	
	@Column(name="page_number")
	private int pageNumber;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bounding_box_id", referencedColumnName = "id")
	private BoundingBoxEntity boundingBox;
	
	@ManyToOne
    @JoinColumn(name="document_id", nullable=false)
	private DocumentEntity document;

	@OneToMany(mappedBy = "page", cascade = CascadeType.ALL)
	private List<AreaEntity> areas = new ArrayList<>();

	@CreationTimestamp
	@Column(name="created")
	private Instant created;

	@Column(name="is_deleted", columnDefinition = "BIT", length = 1)
	private boolean isDeleted;
	
	protected PageEntity() {}

	public PageEntity(String name, String imageName, int pageNumber, BoundingBoxEntity boundingBox) {
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public BoundingBoxEntity getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(BoundingBoxEntity boundingBox) {
		this.boundingBox = boundingBox;
	}

	public DocumentEntity getDocument() {
		return document;
	}

	public void setDocument(DocumentEntity document) {
		this.document = document;
	}

	public List<AreaEntity> getAreas() {
		return areas;
	}

	public void setAreas(List<AreaEntity> areas) {
		this.areas = areas;
	}

	public void addArea(AreaEntity area) {
		area.setPage(this); // bidirectional relationship
		this.areas.add(area);
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
