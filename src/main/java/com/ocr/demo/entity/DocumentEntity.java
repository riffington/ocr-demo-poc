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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="document")
public class DocumentEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long id;
	
	@Column(name="guid")
	private String guid;
	
	@Column(name="content_type")
	private String contentType;

	@OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
	private List<PageEntity> pages = new ArrayList<>();

	@OneToMany(mappedBy = "document")
	private List<TaskEntity> tasks = new ArrayList<>();

	@CreationTimestamp
	@Column(name="created")
	private Instant created;

	@Column(name="is_deleted", columnDefinition = "BIT", length = 1)
	private boolean isDeleted;
	
	protected DocumentEntity() {}

	public DocumentEntity(String guid, String contentType) {
		this.guid = guid;
		this.contentType = contentType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public List<PageEntity> getPages() {
		return pages;
	}

	public void setPages(List<PageEntity> pages) {
		this.pages = pages;
	}

	public void addPage(PageEntity page) {
		this.pages.add(page);
	}

	public List<TaskEntity> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskEntity> tasks) {
		this.tasks = tasks;
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
