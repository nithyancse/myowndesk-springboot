package com.myowndesk.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="topic")
public class Topic extends AuditModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO, generator="nativeWay")
	@GenericGenerator(name = "nativeWay", strategy = "native")
	@Column(name="id")
	private long id;
	
	@NotBlank(message="Title should not be empty")
	@Column(name="title", nullable = false, columnDefinition = "VARCHAR(500)", length = 500)
	private String title;
	
	@NotBlank(message="Description name should not be empty")
	@Size(max=5000, message="Description length should be less than 20000 chars")
	@Column(name="description", nullable = false, columnDefinition = "VARCHAR(20000)", length = 20000)
	private String description;
	
	@NotBlank
	@Column(name="type", nullable = false, columnDefinition = "VARCHAR(20)", length = 20)
	private String type;
	
	@Column(name="status", columnDefinition = "VARCHAR(2)", length = 2)
	private String status;

	@Column(name="menu_id")
	private long menuId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getMenuId() {
		return menuId;
	}

	public void setMenuId(long menuId) {
		this.menuId = menuId;
	}


}
