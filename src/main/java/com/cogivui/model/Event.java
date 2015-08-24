package com.cogivui.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.cogivui.utils.ControllerUtils;

@Entity
@Table(name = "EVENT")
@AttributeOverride(name = "id", column = @Column(name = "EVENT_ID"))
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Event extends BaseEntity {

	private static final long serialVersionUID = 641905622740690038L;

	private String name;

	private String venueName;

	private Time time;

	private String description;

	private String poster;

	private Set<Category> categories = new HashSet<Category>(0);

	private Set<Tag> tags = new HashSet<Tag>(0);

	private OrganizerContact organizerContact;

	private Place place;

	private float price;

	private String hotline;

	private int status;

	private long distance;

	private String source;

	private String originalUrl;

	@Column(name = "NAME", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "VENUE_NAME")
	public String getVenueName() {
		return venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "TIME_ID", nullable = false, unique = false)
	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "EVENT_CATEGORY", joinColumns = { @JoinColumn(name = "EVENT_ID") }, inverseJoinColumns = { @JoinColumn(name = "CATEGORY_ID") })
	@JsonManagedReference
	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "EVENT_TAG", joinColumns = { @JoinColumn(name = "EVENT_ID") }, inverseJoinColumns = { @JoinColumn(name = "TAG_ID") })
	@JsonManagedReference
	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ORGANIZER_CONTACT_ID", nullable = true, unique = false)
	public OrganizerContact getOrganizerContact() {
		return organizerContact;
	}

	public void setOrganizerContact(OrganizerContact organizerContact) {
		this.organizerContact = organizerContact;
	}

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PLACE_ID", nullable = false, unique = false)
	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	@Column(name = "POSTER")
	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	@Column(name = "PRICE")
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Column(name = "HOT_LINE")
	public String getHotline() {
		return hotline;
	}

	public void setHotline(String hotline) {
		this.hotline = hotline;
	}

	@Transient
	public long getDistance() {
		return distance;
	}

	public void setDistance(long distance) {
		this.distance = distance;
	}

	@Column(name = "STATUS")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "SOURCE")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "ORIGINAL_URL")
	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public static Event clone(EventDto eventDto) {
		Event e = new Event();
		e.setName(eventDto.getName());
		e.setHotline(eventDto.getHotline());
		e.setVenueName(eventDto.getVenueName());
		e.setTime(eventDto.getTime());
		e.setDescription(ControllerUtils.br2nl(eventDto.getDescription()));
		e.setPoster(eventDto.getPoster());
		e.setPlace(eventDto.getPlace());
		e.setPrice(eventDto.getPrice());
		e.setSource(eventDto.getSource());
		e.setStatus(eventDto.getStatus());
		e.setOriginalUrl(eventDto.getOriginalUrl());
		e.setOrganizerContact(eventDto.getOrganizerContact());

		e.setCategories(eventDto.getCategories());
		e.setTags(eventDto.getTags());

		return e;
	}
}
