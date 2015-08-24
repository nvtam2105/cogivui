package com.cogivui.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@Entity
@Table(name = "USER")
@AttributeOverride(name = "id", column = @Column(name = "USER_ID"))
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class User extends BaseEntity {

	private static final long serialVersionUID = 2688983911047713080L;

	private String username;
	private String password;

	@Column(name = "USERNAME", nullable = false, length = 30, unique = true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "PASSWORD", nullable = false, length = 255)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
