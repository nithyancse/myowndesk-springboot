package com.myowndesk.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
//import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="user")
public class User extends AuditModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO, generator="nativeWay")
	@GenericGenerator(name = "nativeWay", strategy = "native")
	@Column(name="id")
	private long id;
	
	@Column(name="name", columnDefinition = "VARCHAR(45)", length = 45)
	private String name;
	
	@NotBlank(message="Please enter Email Id")
	@Email(message="Please enter valid Email Id")
	@Column(name="email_id", nullable = false, columnDefinition = "VARCHAR(45)", length = 45)
	private String emailId;
	
	@Size(min=6, max=24, message="Password length should be min {min} and max {max}")
	@Column(name="password", nullable = false, columnDefinition = "VARCHAR(25)", length = 24)
	private String password;
	
	@Size(min=6, max=24, message="Confirm Password length should be min {min} and max {max}")
	@Transient
	private String confirmPassword;

	@Column(name="status", columnDefinition = "VARCHAR(2)", length = 2)
	private String status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}