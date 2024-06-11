package com.rakbank.budgetms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class Auditable {

	@CreatedBy
	@Column(name = "created_by")
	private String created_by;

	@UpdateTimestamp
	@Column(name = "modified_on")
	private Timestamp modified_on;

	@CreationTimestamp
	@Column(name = "created_on")
	private Timestamp created_on;

	@LastModifiedBy
	@Column(name = "modified_by")
	private String modified_by;

	public String getCreated_by() {
		return created_by;
	}

	public Timestamp getModified_on() {
		return modified_on;
	}

	public void setModified_on(Timestamp modified_on) {
		this.modified_on = modified_on;
	}

	public Timestamp getCreated_on() {
		return created_on;
	}

	public String getModified_by() {
		return modified_by;
	}

}
