package com.cogivui.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class EventDto implements Serializable {

	private static final long serialVersionUID = 5077037732706877073L;
	
//	private long id;

	private String name;

	private String venueName;

	private Time time;

	private String description;

	private String except;

	private String poster;

	private Set<Category> categories = new HashSet<Category>(0);

	private Set<Tag> tags = new HashSet<Tag>(0);

	private OrganizerContact organizerContact;

	private Place place;

	private float price;
	
//	private long distance;

	private String hotline;

	private String posterUrl;

	private int status;

	private String originalUrl;

	//private Date startDate;

	//private String placeAddress;
	
	private String websiteUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVenueName() {
		return venueName;
	}

	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public OrganizerContact getOrganizerContact() {
		return organizerContact;
	}

	public void setOrganizerContact(OrganizerContact organizerContact) {
		this.organizerContact = organizerContact;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getHotline() {
		return hotline;
	}

	public void setHotline(String hotline) {
		this.hotline = hotline;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public String getExcept() {
		return except;
	}

	public void setExcept(String except) {
		this.except = except;
	}
	
	public String getWebsiteUrl() {
	    return websiteUrl;
    }

	public void setWebsiteUrl(String websiteUrl) {
	    this.websiteUrl = websiteUrl;
    }


//	public Date getStartDate() {
//		return startDate;
//	}
//
//	public void setStartDate(Date startDate) {
//		this.startDate = startDate;
//	}
//
//	public String getPlaceAddress() {
//		return placeAddress;
//	}
//
//	public void setPlaceAddress(String placeAddress) {
//		this.placeAddress = placeAddress;
//	}

//	public long getId() {
//	    return id;
//    }
//
//	public void setId(long id) {
//	    this.id = id;
//    }
//
//	public long getDistance() {
//	    return distance;
//    }
//
//	public void setDistance(long distance) {
//	    this.distance = distance;
//    }

	
}
