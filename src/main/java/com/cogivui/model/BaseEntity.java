package com.cogivui.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@MappedSuperclass
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = -2653928468289453004L;
	private long id;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "EVENT_SEQ")
	@SequenceGenerator(name = "EVENT_SEQ", sequenceName = "EVENT_SEQ")
	@Column(name = "ID", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
