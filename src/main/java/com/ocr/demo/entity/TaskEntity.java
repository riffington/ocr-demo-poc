package com.ocr.demo.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="task")
public class TaskEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long id;
	
	@Column(name="task_type")
	private String type;

	@Column(name="related_id")
	private Long relatedId;
	
	@Column(name="image_ref")
	private String imageRef;

	@Column(name="is_complete", columnDefinition = "BIT", length = 1)
	private boolean isComplete;
	
	@ManyToOne
    @JoinColumn(name="document_id", nullable=false)
	private DocumentEntity document;

	@CreationTimestamp
	@Column(name="created")
	private Instant created;

	@Column(name="is_deleted", columnDefinition = "BIT", length = 1)
	private boolean isDeleted;
	
	protected TaskEntity() {}

	public TaskEntity(String type, DocumentEntity document, Long relatedId, String imageRef) {
		this.type = type;
		this.document = document;
		this.relatedId = relatedId;
		this.imageRef = imageRef;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(Long relatedId) {
		this.relatedId = relatedId;
	}

	public String getImageRef() {
		return imageRef;
	}

	public void setImageRef(String imageRef) {
		this.imageRef = imageRef;
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	public DocumentEntity getDocument() {
		return document;
	}

	public void setDocument(DocumentEntity document) {
		this.document = document;
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
