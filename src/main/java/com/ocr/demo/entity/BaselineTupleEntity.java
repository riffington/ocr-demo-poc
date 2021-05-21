package com.ocr.demo.entity;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="baseline_tuple")
public class BaselineTupleEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long id;

	@Column(name="value_a")
    private float valueA;

	@Column(name="value_b")
	private float valueB;

	@CreationTimestamp
	@Column(name="created")
	private Instant created;

	@Column(name="is_deleted", columnDefinition = "BIT", length = 1)
	private boolean isDeleted;
	
	protected BaselineTupleEntity() {}
	
    public BaselineTupleEntity(float valueA, float valueB) {
        this.valueA = valueA;
        this.valueB = valueB;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public float getValueA() {
		return valueA;
	}

	public void setValueA(float valueA) {
		this.valueA = valueA;
	}

	public float getValueB() {
		return valueB;
	}

	public void setValueB(float valueB) {
		this.valueB = valueB;
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
