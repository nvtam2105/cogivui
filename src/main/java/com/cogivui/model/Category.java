package com.cogivui.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@Entity
@Table(name = "CATEGORY")
@AttributeOverride(name = "id", column = @Column(name = "CATEGORY_ID"))
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Category extends BaseReferenceEntity {

	private static final long serialVersionUID = -2235309598666613939L;

	private String picture;

	private Set<Event> events = new HashSet<Event>();

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
	@JsonIgnore
	@JsonBackReference
	public Set<Event> getEvents() {
		return events;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	@Column(name = "PICTURE")
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
}
