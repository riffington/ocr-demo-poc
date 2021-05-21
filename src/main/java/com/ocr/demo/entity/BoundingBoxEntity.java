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
@Table(name="bounding_box")
public class BoundingBoxEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private Long id;

	@Column(name="top_left_x")
    private int topLeftX;

	@Column(name="top_left_y")
	private int topLeftY;

	@Column(name="bottom_right_x")
	private int bottomRightX;

	@Column(name="bottom_right_y")
	private int bottomRightY;

	@CreationTimestamp
	@Column(name="created")
	private Instant created;

	@Column(name="is_deleted", columnDefinition = "BIT", length = 1)
	private boolean isDeleted;
	
	protected BoundingBoxEntity() {}
	
    public BoundingBoxEntity(int topLeftX, int topLeftY, int bottomRightX, int bottomRightY) {
        this.topLeftX = topLeftX;
        this.topLeftY = topLeftY;
        this.bottomRightX = bottomRightX;
        this.bottomRightY = bottomRightY;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getTopLeftX() {
		return topLeftX;
	}

	public void setTopLeftX(int topLeftX) {
		this.topLeftX = topLeftX;
	}

	public int getTopLeftY() {
		return topLeftY;
	}

	public void setTopLeftY(int topLeftY) {
		this.topLeftY = topLeftY;
	}

	public int getBottomRightX() {
		return bottomRightX;
	}

	public void setBottomRightX(int bottomRightX) {
		this.bottomRightX = bottomRightX;
	}

	public int getBottomRightY() {
		return bottomRightY;
	}

	public void setBottomRightY(int bottomRightY) {
		this.bottomRightY = bottomRightY;
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
