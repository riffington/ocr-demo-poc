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
@Table(name="document_paragraph")
public class ParagraphEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long id;
	
	// This is the "id" field provided by tesseract. It will NOT be unique across documents
	@Column(name="id_name")
	private String name;

	@Column(name="lang")
	private String lang;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bounding_box_id", referencedColumnName = "id")
	private BoundingBoxEntity boundingBox;
	
	@ManyToOne
    @JoinColumn(name="area_id", nullable=false)
	private AreaEntity area;

	@OneToMany(mappedBy = "paragraph", cascade = CascadeType.ALL)
	private List<LineEntity> lines = new ArrayList<>();

	@CreationTimestamp
	@Column(name="created")
	private Instant created;

	@Column(name="is_deleted", columnDefinition = "BIT", length = 1)
	private boolean isDeleted;
	
	protected ParagraphEntity() {}

	public ParagraphEntity(String name, String lang, BoundingBoxEntity boundingBox) {
		this.name = name;
		this.lang = lang;
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

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public BoundingBoxEntity getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(BoundingBoxEntity boundingBox) {
		this.boundingBox = boundingBox;
	}

	public AreaEntity getArea() {
		return area;
	}

	public void setArea(AreaEntity area) {
		this.area = area;
	}

	public List<LineEntity> getLines() {
		return lines;
	}

	public void setLines(List<LineEntity> lines) {
		this.lines = lines;
	}

	public void addLine(LineEntity line) {
		line.setParagraph(this); // bidirectional relationship
		this.lines.add(line);
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
