package com.cogivui.model;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@Entity
@Table(name = "CITY")
@AttributeOverride(name = "id", column = @Column(name = "CITY_ID"))
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class City extends BaseReferenceEntity {

	private static final long serialVersionUID = 3753325337891143976L;

}
